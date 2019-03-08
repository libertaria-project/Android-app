package com.stocksexchange.android.ui.transactions.fragment

import com.stocksexchange.android.api.model.Currency
import com.stocksexchange.android.api.model.Transaction
import com.stocksexchange.android.api.model.TransactionParameters
import com.stocksexchange.android.mappings.mapToNameMarketMap
import com.stocksexchange.android.model.DataLoadingSources
import com.stocksexchange.android.model.DataTypes
import com.stocksexchange.android.model.TransactionModes
import com.stocksexchange.android.model.utils.*
import com.stocksexchange.android.repositories.currencies.CurrenciesRepository
import com.stocksexchange.android.repositories.transactions.TransactionsRepository
import com.stocksexchange.android.ui.base.mvp.model.BaseDataLoadingModel
import com.stocksexchange.android.ui.transactions.fragment.TransactionsModel.ActionListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.standalone.inject

class TransactionsModel : BaseDataLoadingModel<
    List<Transaction>,
    TransactionParameters,
    ActionListener
>() {


    private val mTransactionsRepository: TransactionsRepository by inject()
    private val mCurrenciesRepository: CurrenciesRepository by inject()




    override fun canLoadData(params: TransactionParameters, dataType: DataTypes,
                             dataLoadingSource: DataLoadingSources): Boolean {
        val transactionMode = params.mode
        val searchQuery = params.searchQuery

        val isTransactionSearch = (transactionMode == TransactionModes.SEARCH)
        val isNewData = (dataType == DataTypes.NEW_DATA)

        val isTransactionSearchWithNoQuery = (isTransactionSearch && searchQuery.isBlank())
        val isTransactionSearchNewData = (isTransactionSearch && isNewData)
        val isNewDataWithIntervalNotApplied = (isNewData && !isDataLoadingIntervalApplied())
        val isNetworkConnectivitySource = (dataLoadingSource == DataLoadingSources.NETWORK_CONNECTIVITY)

        return (!isTransactionSearchWithNoQuery
                && !isTransactionSearchNewData
                && (!isNewDataWithIntervalNotApplied || isNetworkConnectivitySource))
    }


    override fun refreshData(params: TransactionParameters) {
        mTransactionsRepository.refresh(params)
    }


    override suspend fun performDataLoading(params: TransactionParameters) {
        when(params.mode) {
            TransactionModes.STANDARD -> mTransactionsRepository.get(params)
            TransactionModes.SEARCH -> mTransactionsRepository.search(params)
        }.log("transactionsRepository.get(params: $params)")
        .onSuccess { transactions ->
            val currenciesRepositoryResult = mCurrenciesRepository.getAll().apply {
                log("currenciesRepository.getAll()")
            }

            withContext(Dispatchers.Main) {
                currenciesRepositoryResult
                    .onSuccess { currencies ->
                        handleSuccessfulResponse(adjustTransactions(
                            transactions,
                            currencies.mapToNameMarketMap()
                        ))
                    }
                    .onFailure { handleUnsuccessfulResponse(it) }
            }
        }
        .onFailure { withContext(Dispatchers.Main) { handleUnsuccessfulResponse(it) } }
    }


    private fun adjustTransactions(transactions: List<Transaction>,
                                   currencyMap: Map<String, Currency>): List<Transaction> {
        for(transaction in transactions) {
            transaction.blockExplorerUrl = currencyMap[transaction.currency]?.blockExplorerUrl ?: ""
        }

        return transactions
    }


    interface ActionListener: BaseDataLoadingActionListener<List<Transaction>>


}
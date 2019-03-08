package com.stocksexchange.android.ui.wallets.fragment

import com.stocksexchange.android.api.model.Currency
import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.api.model.WalletParameters
import com.stocksexchange.android.model.*
import com.stocksexchange.android.model.utils.*
import com.stocksexchange.android.repositories.currencies.CurrenciesRepository
import com.stocksexchange.android.repositories.users.UsersRepository
import com.stocksexchange.android.ui.base.mvp.model.BaseDataLoadingModel
import com.stocksexchange.android.utils.extensions.getWithDefault
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.standalone.inject

class WalletsModel : BaseDataLoadingModel<
    List<Wallet>,
    WalletParameters,
    WalletsModel.ActionListener
>() {


    private val mCurrenciesRepository: CurrenciesRepository by inject()
    private val mUsersRepository: UsersRepository by inject()




    override fun canLoadData(params: WalletParameters, dataType: DataTypes,
                             dataLoadingSource: DataLoadingSources): Boolean {
        val walletMode = params.walletMode
        val searchQuery = params.searchQuery

        val isWalletSearch = (walletMode == WalletModes.SEARCH)
        val isNewData = (dataType == DataTypes.NEW_DATA)

        val isWalletSearchWithNoQuery = (isWalletSearch && searchQuery.isBlank())
        val isWalletSearchNewData = (isWalletSearch && isNewData)
        val isNewDataWithIntervalNotApplied = (isNewData && !isDataLoadingIntervalApplied())
        val isNetworkConnectivitySource = (dataLoadingSource == DataLoadingSources.NETWORK_CONNECTIVITY)

        return (!isWalletSearchWithNoQuery
                && !isWalletSearchNewData
                && (!isNewDataWithIntervalNotApplied || isNetworkConnectivitySource))
    }


    override fun refreshData(params: WalletParameters) {
        mUsersRepository.refresh()
        mCurrenciesRepository.refresh()
    }


    override suspend fun performDataLoading(params: WalletParameters) {
        mUsersRepository.getSignedInUser()
            .log("usersRepository.getSignedInUser()")
            .onSuccess { mActionListener?.onUserLoaded(it)  }
            .onFailure { withContext(Dispatchers.Main) { handleUnsuccessfulResponse(it) } }
    }


    suspend fun loadCurrencies(user: User, params: WalletParameters) {
        val repositoryResult = when(params.walletMode) {
            WalletModes.STANDARD -> mCurrenciesRepository.getAll()
            WalletModes.SEARCH -> mCurrenciesRepository.search(params.lowercasedSearchQuery)
        }.log("currenciesRepository.get(params: $params)")

        withContext(Dispatchers.Main) {
            repositoryResult
                .onSuccess { handleSuccessfulResponse(generateWallets(it, user, params.shouldShowEmptyWallets)) }
                .onFailure { handleUnsuccessfulResponse(it) }
        }
    }


    private fun generateWallets(currencies: List<Currency>, user: User,
                                shouldShowEmptyWallets: Boolean): List<Wallet> {
        val wallets = mutableListOf<Wallet>()
        var availableBalance: Double
        var balanceInOrders: Double

        // Filling out wallets with information
        for(currency in currencies) {
            availableBalance = user.funds.getWithDefault(currency.name, "0").toDouble()
            balanceInOrders = user.holdFunds.getWithDefault(currency.name, "0").toDouble()

            if(!shouldShowEmptyWallets && (availableBalance == 0.0) && (balanceInOrders == 0.0)) {
                continue
            }

            wallets.add(Wallet(currency, availableBalance, balanceInOrders))
        }

        // Sorting wallets
        return wallets.sortedWith(
            compareByDescending<Wallet> { it.availableBalance }
            .thenByDescending { it.balanceInOrders }
        )
    }


    interface ActionListener : BaseDataLoadingActionListener<List<Wallet>> {

        suspend fun onUserLoaded(user: User)

    }


}
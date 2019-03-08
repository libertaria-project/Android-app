package com.stocksexchange.android.data.repositories.withdrawals

import com.stocksexchange.android.api.model.newapi.TransactionParameters
import com.stocksexchange.android.api.model.newapi.Withdrawal
import com.stocksexchange.android.data.stores.withdrawals.WithdrawalsDataStore
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.repositories.base.repository.BaseRepository
import com.stocksexchange.android.repositories.utils.freshdatahandlers.concrete.SimpleFreshDataHandlerImpl
import com.stocksexchange.android.repositories.utils.freshdatahandlers.interfaces.FreshDataHandler
import com.stocksexchange.android.utils.providers.ConnectionProvider

class WithdrawalsRepositoryImpl(
    private val serverDataStore: WithdrawalsDataStore,
    private val databaseDataStore: WithdrawalsDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepository(), WithdrawalsRepository {


    override val freshDataHandler: FreshDataHandler = SimpleFreshDataHandlerImpl()




    override fun refresh() {
        freshDataHandler.refresh()
    }


    override suspend fun save(withdrawals: List<Withdrawal>) {
        databaseDataStore.save(withdrawals)
    }


    override suspend fun deleteAll() {
        databaseDataStore.deleteAll()
    }


    override suspend fun search(params: TransactionParameters): RepositoryResult<List<Withdrawal>> {
        val result = get(params)

        // Making sure that the data is present since the search is performed
        // solely on database records
        return if(result.isSuccessful()) {
            RepositoryResult(databaseResult = databaseDataStore.search(params))
        } else {
            result
        }
    }


    override suspend fun get(params: TransactionParameters): RepositoryResult<List<Withdrawal>> {
        val result = RepositoryResult<List<Withdrawal>>()

        if(freshDataHandler.shouldLoadFreshData(connectionProvider)) {
            result.serverResult = serverDataStore.get(params)

            if(result.isServerResultSuccessful()) {
                save(result.getSuccessfulResultValue())
            }
        }

        if(result.isServerResultErroneous(true)) {
            result.databaseResult = databaseDataStore.get(params)
        }

        return result
    }


}
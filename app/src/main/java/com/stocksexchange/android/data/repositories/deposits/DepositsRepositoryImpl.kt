package com.stocksexchange.android.data.repositories.deposits

import com.stocksexchange.android.api.model.newapi.Deposit
import com.stocksexchange.android.api.model.newapi.TransactionParameters
import com.stocksexchange.android.data.stores.deposits.DepositsDataStore
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.repositories.base.repository.BaseRepository
import com.stocksexchange.android.repositories.utils.freshdatahandlers.concrete.SimpleFreshDataHandlerImpl
import com.stocksexchange.android.repositories.utils.freshdatahandlers.interfaces.FreshDataHandler
import com.stocksexchange.android.utils.providers.ConnectionProvider

class DepositsRepositoryImpl(
    private val serverDataStore: DepositsDataStore,
    private val databaseDataStore: DepositsDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepository(), DepositsRepository {


    override val freshDataHandler: FreshDataHandler = SimpleFreshDataHandlerImpl()




    override fun refresh() {
        freshDataHandler.refresh()
    }


    override suspend fun save(deposits: List<Deposit>) {
        databaseDataStore.save(deposits)
    }


    override suspend fun deleteAll() {
        databaseDataStore.deleteAll()
    }


    override suspend fun search(params: TransactionParameters): RepositoryResult<List<Deposit>> {
        val result = get(params)

        // Making sure that the data is present since the search is performed
        // solely on database records
        return if(result.isSuccessful()) {
            RepositoryResult(databaseResult = databaseDataStore.search(params))
        } else {
            result
        }
    }


    override suspend fun get(params: TransactionParameters): RepositoryResult<List<Deposit>> {
        val result = RepositoryResult<List<Deposit>>()

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
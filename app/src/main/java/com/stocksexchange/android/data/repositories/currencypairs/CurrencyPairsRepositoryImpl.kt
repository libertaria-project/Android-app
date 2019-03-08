package com.stocksexchange.android.data.repositories.currencypairs

import com.stocksexchange.android.api.model.newapi.CurrencyPair
import com.stocksexchange.android.data.stores.currencypairs.CurrencyPairsDataStore
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.repositories.base.repository.BaseRepository
import com.stocksexchange.android.repositories.utils.freshdatahandlers.concrete.SimpleFreshDataHandlerImpl
import com.stocksexchange.android.repositories.utils.freshdatahandlers.interfaces.FreshDataHandler
import com.stocksexchange.android.utils.providers.ConnectionProvider

class CurrencyPairsRepositoryImpl(
    private val serverDataStore: CurrencyPairsDataStore,
    private val databaseDataStore: CurrencyPairsDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepository(), CurrencyPairsRepository {


    override val freshDataHandler: FreshDataHandler = SimpleFreshDataHandlerImpl()




    override fun refresh() {
        freshDataHandler.refresh()
    }


    override suspend fun save(currencyPairs: List<CurrencyPair>) {
        databaseDataStore.save(currencyPairs)
    }


    override suspend fun deleteAll() {
        databaseDataStore.deleteAll()
    }


    override suspend fun getAll(): RepositoryResult<List<CurrencyPair>> {
        val result = RepositoryResult<List<CurrencyPair>>()

        if(freshDataHandler.shouldLoadFreshData(connectionProvider)) {
            result.serverResult = serverDataStore.getAll()

            if(result.isServerResultSuccessful()) {
                deleteAll()
                save(result.getSuccessfulResultValue())
            }
        }

        if(result.isServerResultErroneous(true)) {
            result.databaseResult = databaseDataStore.getAll()
        }

        return result
    }


}
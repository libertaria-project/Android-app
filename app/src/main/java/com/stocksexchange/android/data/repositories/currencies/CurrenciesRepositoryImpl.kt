package com.stocksexchange.android.data.repositories.currencies

import com.stocksexchange.android.api.model.newapi.Currency
import com.stocksexchange.android.data.stores.currencies.CurrenciesDataStore
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.repositories.base.repository.BaseRepository
import com.stocksexchange.android.repositories.utils.freshdatahandlers.concrete.SimpleFreshDataHandlerImpl
import com.stocksexchange.android.repositories.utils.freshdatahandlers.interfaces.FreshDataHandler
import com.stocksexchange.android.utils.providers.ConnectionProvider

class CurrenciesRepositoryImpl(
    private val serverDataStore: CurrenciesDataStore,
    private val databaseDataStore: CurrenciesDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepository(), CurrenciesRepository {


    override val freshDataHandler: FreshDataHandler = SimpleFreshDataHandlerImpl()




    override fun refresh() {
        freshDataHandler.refresh()
    }


    override suspend fun save(currencies: List<Currency>) {
        databaseDataStore.save(currencies)
    }


    override suspend fun deleteAll() {
        databaseDataStore.deleteAll()
    }


    override suspend fun getAll(): RepositoryResult<List<Currency>> {
        val result = RepositoryResult<List<Currency>>()

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
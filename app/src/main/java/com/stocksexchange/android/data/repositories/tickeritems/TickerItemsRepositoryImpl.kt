package com.stocksexchange.android.data.repositories.tickeritems

import com.stocksexchange.android.api.model.newapi.TickerItem
import com.stocksexchange.android.data.stores.tickeritems.TickerItemsDataStore
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.repositories.base.repository.BaseRepository
import com.stocksexchange.android.repositories.utils.freshdatahandlers.concrete.SimpleFreshDataHandlerImpl
import com.stocksexchange.android.repositories.utils.freshdatahandlers.interfaces.FreshDataHandler
import com.stocksexchange.android.utils.providers.ConnectionProvider

class TickerItemsRepositoryImpl(
    private val serverDataStore: TickerItemsDataStore,
    private val databaseDataStore: TickerItemsDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepository(), TickerItemsRepository {


    override val freshDataHandler: FreshDataHandler = SimpleFreshDataHandlerImpl()




    override fun refresh() {
        freshDataHandler.refresh()
    }


    override suspend fun save(tickerItems: List<TickerItem>) {
        databaseDataStore.save(tickerItems)
    }


    override suspend fun deleteAll() {
        databaseDataStore.deleteAll()
    }


    override suspend fun getAll(): RepositoryResult<List<TickerItem>> {
        val result = RepositoryResult<List<TickerItem>>()

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
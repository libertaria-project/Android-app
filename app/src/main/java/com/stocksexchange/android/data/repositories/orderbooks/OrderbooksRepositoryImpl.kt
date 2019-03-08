package com.stocksexchange.android.data.repositories.orderbooks

import com.stocksexchange.android.api.model.newapi.Orderbook
import com.stocksexchange.android.api.model.newapi.OrderbookParameters
import com.stocksexchange.android.data.stores.orderbooks.OrderbooksDataStore
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.repositories.base.repository.BaseRepository
import com.stocksexchange.android.repositories.utils.freshdatahandlers.interfaces.FreshDataHandler
import com.stocksexchange.android.utils.providers.ConnectionProvider

class OrderbooksRepositoryImpl(
    private val serverDataStore: OrderbooksDataStore,
    private val databaseDataStore: OrderbooksDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepository(), OrderbooksRepository {


    override val freshDataHandler: FreshDataHandler = OrderbooksFreshDataHandler()




    override fun refresh(params: OrderbookParameters) {
        freshDataHandler.refresh(params)
    }


    override suspend fun save(params: OrderbookParameters, orderbook: Orderbook) {
        databaseDataStore.save(params, orderbook)
    }


    override suspend fun get(params: OrderbookParameters): RepositoryResult<Orderbook> {
        val result = RepositoryResult<Orderbook>()

        if(freshDataHandler.shouldLoadFreshData(connectionProvider, params)) {
            result.serverResult = serverDataStore.get(params)

            if(result.isServerResultSuccessful()) {
                save(params, result.getSuccessfulResultValue())
            }
        }

        if(result.isServerResultErroneous(true)) {
            result.databaseResult = databaseDataStore.get(params)
        }

        return result
    }


}
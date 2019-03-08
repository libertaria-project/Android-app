package com.stocksexchange.android.datastores.orderbook

import com.stocksexchange.android.api.StocksExchangeApi
import com.stocksexchange.android.api.model.Orderbook
import com.stocksexchange.android.api.model.OrderbookParameters
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation
import java.lang.UnsupportedOperationException

class OrderbookServerDataStore(
    private val stocksExchangeApi: StocksExchangeApi
) : OrderbookDataStore {


    override suspend fun save(params: OrderbookParameters, orderbook: Orderbook) {
        throw UnsupportedOperationException()
    }


    override suspend fun get(params: OrderbookParameters): Result<Orderbook> {
        return performBackgroundOperation {
            stocksExchangeApi.getOrderbook(params)
        }
    }


}
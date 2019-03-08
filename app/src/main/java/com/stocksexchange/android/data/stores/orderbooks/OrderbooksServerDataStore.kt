package com.stocksexchange.android.data.stores.orderbooks

import com.stocksexchange.android.api.StexApi
import com.stocksexchange.android.api.model.newapi.Orderbook
import com.stocksexchange.android.api.model.newapi.OrderbookParameters
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class OrderbooksServerDataStore(
    private val stexApi: StexApi
) : OrderbooksDataStore {


    override suspend fun save(params: OrderbookParameters, orderbook: Orderbook) {
        throw UnsupportedOperationException()
    }


    override suspend fun get(params: OrderbookParameters): Result<Orderbook> {
        return performBackgroundOperation {
            stexApi.getOrderbook(params)
        }
    }


}
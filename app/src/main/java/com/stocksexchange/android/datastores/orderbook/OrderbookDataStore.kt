package com.stocksexchange.android.datastores.orderbook

import com.stocksexchange.android.api.model.Orderbook
import com.stocksexchange.android.api.model.OrderbookParameters
import com.stocksexchange.android.model.Result

interface OrderbookDataStore {

    suspend fun save(params: OrderbookParameters, orderbook: Orderbook)

    suspend fun get(params: OrderbookParameters): Result<Orderbook>

}
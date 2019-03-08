package com.stocksexchange.android.data.base

import com.stocksexchange.android.api.model.newapi.Orderbook
import com.stocksexchange.android.api.model.newapi.OrderbookParameters

interface OrderbooksData<OrderbookFetchingResult> {

    suspend fun save(params: OrderbookParameters, orderbook: Orderbook)

    suspend fun get(params: OrderbookParameters): OrderbookFetchingResult

}
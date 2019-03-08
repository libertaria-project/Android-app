package com.stocksexchange.android.mappings

import com.stocksexchange.android.api.model.Orderbook
import com.stocksexchange.android.database.model.DatabaseOrderbook

fun Orderbook.mapToDatabaseOrderbook(marketName: String): DatabaseOrderbook {
    return DatabaseOrderbook(
        marketName = marketName,
        buyOrders = buyOrders,
        sellOrders = sellOrders
    )
}


fun DatabaseOrderbook.mapToOrderbook(): Orderbook {
    return Orderbook(
        buyOrders = buyOrders,
        sellOrders = sellOrders
    )
}
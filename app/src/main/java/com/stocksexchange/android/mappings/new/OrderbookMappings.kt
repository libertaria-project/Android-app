package com.stocksexchange.android.mappings.new

import com.stocksexchange.android.api.model.newapi.Orderbook
import com.stocksexchange.android.api.model.newapi.OrderbookParameters
import com.stocksexchange.android.database.model.newd.DatabaseOrderbook

fun Orderbook.mapToDatabaseOrderbook(
    parameters: OrderbookParameters
): DatabaseOrderbook {
    return DatabaseOrderbook(
        currencyPairId = parameters.currencyPairId,
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
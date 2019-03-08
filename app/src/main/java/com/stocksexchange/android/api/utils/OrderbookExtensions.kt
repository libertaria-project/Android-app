package com.stocksexchange.android.api.utils

import com.stocksexchange.android.api.model.Orderbook

/**
 * Truncates the orderbook's orders (buy and sell) to the
 * specified size.
 *
 * @param size The size to truncate to
 *
 * @return The truncated orderbook
 */
fun Orderbook?.truncate(size: Int): Orderbook? {
    if(this == null) {
        return null
    }

    val shouldTruncateBuyOrders = (buyOrders.size > size)
    val shouldTruncateSellOrders = (sellOrders.size > size)

    if(!shouldTruncateBuyOrders && !shouldTruncateSellOrders) {
        return this
    }

    val buyOrders = if(shouldTruncateBuyOrders) {
        buyOrders.take(size)
    } else {
        buyOrders
    }

    val sellOrders = if(shouldTruncateSellOrders) {
        sellOrders.take(size)
    } else {
        sellOrders
    }

    return Orderbook(buyOrders, sellOrders)
}
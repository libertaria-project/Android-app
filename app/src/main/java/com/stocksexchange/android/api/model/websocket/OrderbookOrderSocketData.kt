package com.stocksexchange.android.api.model.websocket

import com.google.gson.annotations.SerializedName
import com.stocksexchange.android.api.model.OrderbookOrder

/**
 * A wrapper model class holding data for a changed
 * orderbook order passed through socket.
 */
data class OrderbookOrderSocketData(
    @SerializedName("currency_pair_id") val marketId: Long,
    @SerializedName("amount") val amount: Double,
    @SerializedName("price") val price: Double
) {


    /**
     * Converts this socket data into a complete instance of [OrderbookOrder] class.
     *
     * @return The instance of OrderbookOrder class
     */
    fun toOrderbookOrder(): OrderbookOrder {
        return OrderbookOrder(
            amount = amount,
            price = price
        )
    }


}
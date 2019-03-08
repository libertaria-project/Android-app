package com.stocksexchange.android.api.model.websocket

import com.google.gson.annotations.SerializedName
import com.stocksexchange.android.api.model.Trade

/**
 * A wrapper model class holding data for a newly
 * created trade passed through socket.
 */
data class TradeHistorySocketData(
    @SerializedName("id") val id: Long,
    @SerializedName("currency_pair_id") val marketId: Long,
    @SerializedName("price") val price: Double,
    @SerializedName("amount") val amount: Double,
    @SerializedName("order_type") val type: String,
    @SerializedName("timestamp") val timestamp: Long
) {


    /**
     * Converts this socket data into a complete instance of [Trade] class.
     *
     * @return The instance of Trade class
     */
    fun toTrade(): Trade {
        return Trade(
            id = id,
            timestamp = timestamp,
            price = price,
            amount = amount,
            type = type
        )
    }


}
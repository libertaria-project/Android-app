package com.stocksexchange.android.api.model.websocket

import com.google.gson.annotations.SerializedName

/**
 * A wrapper model class holding data for a changed
 * currency market passed through socket.
 */
data class CurrencyMarketSocketData(
    @SerializedName("id") val id: Long,
    @SerializedName("lastPrice") val lastPrice: Double,
    @SerializedName("lastPriceDayAgo") val lastPriceDayAgo: Double,
    @SerializedName("minSell") val dailyMinPrice: Double,
    @SerializedName("maxBuy") val dailyMaxPrice: Double,
    @SerializedName("spread") val dailyPriceChange: Double,
    @SerializedName("volumeSum") val dailyVolume: Double
)
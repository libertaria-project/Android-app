package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing an item of the ticker.
 */
@Parcelize
data class TickerItem(
    @SerializedName("id") val id: Int,
    @SerializedName("currency_code") val baseCurrencyCode: String,
    @SerializedName("currency_name") val baseCurrencyName: String,
    @SerializedName("market_code") val quoteCurrencyCode: String,
    @SerializedName("market_name") val quoteCurrencyName: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("ask") val askPrice: Double,
    @SerializedName("bid") val bidPrice: Double,
    @SerializedName("last") val lastPrice: Double?,
    @SerializedName("open") val openPrice: Double?,
    @SerializedName("low") val lowPrice: Double?,
    @SerializedName("high") val highPrice: Double?,
    @SerializedName("volume") val volume: Double?,
    @SerializedName("volumeQuote") val volumeQuote: Double?,
    @SerializedName("timestamp") val timestamp: Long
) : Parcelable {


    /**
     * A field that returns a boolean flag indicating
     * whether this ticker item has a last price.
     */
    val hasLastPrice: Boolean
        get() = (lastPrice != null)


    /**
     * A field that returns a boolean flag indicating
     * whether this ticker item has an open price.
     */
    val hasOpenPrice: Boolean
        get() = (openPrice != null)


    /**
     * A field that returns a boolean flag indicating
     * whether this ticker item has a low price.
     */
    val hasLowPrice: Boolean
        get() = (lowPrice != null)


    /**
     * A field that returns a boolean flag indicating
     * whether this ticker item has a high price.
     */
    val hasHighPrice: Boolean
        get() = (highPrice != null)


    /**
     * A field that returns a boolean flag indicating
     * whether this ticker item has a volume.
     */
    val hasVolume: Boolean
        get() = (volume != null)


    /**
     * A field that returns a boolean flag indicating
     * whether this ticker item has a volume quote.
     */
    val hasVolumeQuote: Boolean
        get() = (volumeQuote != null)


}
package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing the data for a candlestick chart.
 */
@Parcelize
data class CandleStick(
    @SerializedName("open") val openPrice: Double,
    @SerializedName("high") val highPrice: Double,
    @SerializedName("low") val lowPrice: Double,
    @SerializedName("close") val closePrice: Double,
    @SerializedName("volume") val volume: Double,
    @SerializedName("time") val timestamp: Long
) : Parcelable
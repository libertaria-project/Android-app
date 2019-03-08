package com.stocksexchange.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing the data for a candlestick chart.
 */
@Parcelize
data class CandleStick(
    @SerializedName("open") val openPrice: Double,
    @SerializedName("close") val closePrice: Double,
    @SerializedName("low") val lowPrice: Double,
    @SerializedName("high") val highPrice: Double,
    @SerializedName("volume") val volume: Double,
    @SerializedName("date") val date: String
) : Parcelable
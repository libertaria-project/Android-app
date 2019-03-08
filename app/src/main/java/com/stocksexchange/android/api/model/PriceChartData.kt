package com.stocksexchange.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.stocksexchange.android.api.services.StocksExchangeService
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing the [StocksExchangeService.getPriceChartData] call response.
 */
@Parcelize
data class PriceChartData(
    @SerializedName("pair") val marketName: String,
    @SerializedName("interval") val interval: String = "",
    @SerializedName("order") val order: String = "",
    @SerializedName("since") val startDate: String = "",
    @SerializedName("end") val endDate: String = "",
    @SerializedName("count") val count: Int,
    @SerializedName("count_pages") val pagesCount: Int,
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("graf") val candleSticks: List<CandleStick>
) : Parcelable
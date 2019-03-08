package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.stocksexchange.android.api.model.newapi.utils.HasUniqueKey
import kotlinx.android.parcel.Parcelize

/**
 * A model class holding price chart data related parameters.
 */
@Parcelize
data class PriceChartDataParameters(
    val currencyPairId: Int,
    val interval: PriceChartDataIntervals,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val count: Int
) : Parcelable, HasUniqueKey {


    companion object {


        /**
         * Retrieves a default set of parameters.
         *
         * @return The default price chart data parameters
         */
        fun getDefaultParameters(): PriceChartDataParameters {
            return PriceChartDataParameters(
                currencyPairId = -1,
                interval = PriceChartDataIntervals.FIVE_MINUTES,
                startTimestamp = (System.currentTimeMillis() - PriceChartDataIntervals.FIVE_MINUTES.milliseconds),
                endTimestamp = System.currentTimeMillis(),
                count = Integer.MAX_VALUE
            )
        }


    }


    override val uniqueKey: String
        get() = "${currencyPairId}_$interval"


}
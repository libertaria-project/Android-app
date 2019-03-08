package com.stocksexchange.android.api.model

import android.os.Parcelable
import com.stocksexchange.android.model.PriceChartModes
import com.stocksexchange.android.model.SortTypes
import kotlinx.android.parcel.Parcelize

/**
 * Parameters storing price chart related data.
 */
@Parcelize
data class PriceChartParameters(
    val mode: PriceChartModes,
    val marketName: String,
    val interval: PriceChartDataIntervals,
    val sortType: SortTypes,
    val count: Int,
    val page: Int
) : Parcelable {


    companion object {

        /**
         * Retrieves default price chart parameters.
         *
         * @return The default price chart parameters
         */
        fun getDefaultParameters(): PriceChartParameters {
            return PriceChartParameters(
                mode = PriceChartModes.STANDARD,
                marketName = "Invalid",
                interval = PriceChartDataIntervals.ONE_DAY,
                sortType = SortTypes.ASC,
                count = 100,
                page = 1
            )
        }

    }


    /**
     * A class used for storing fields of a cache key.
     */
    data class CacheKey(
        val marketName: String,
        val interval: String,
        val sortType: String,
        val count: String,
        val page: String
    ) {

        override fun toString(): String {
            return "${marketName}_${interval}_${sortType}_${count}_$page"
        }

    }


    /**
     * Returns a cache key in a string representation.
     *
     * @return The cache key string
     */
    fun getCacheKeyString(): String {
        return getCacheKey().toString()
    }


    /**
     * Generates a cache key from unique and important fields.
     *
     * @return The cache key
     */
    fun getCacheKey(): CacheKey {
        return CacheKey(
            marketName,
            interval.toString(),
            sortType.toString(),
            count.toString(),
            page.toString()
        )
    }


}
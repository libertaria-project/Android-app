package com.stocksexchange.android.api.model

import android.os.Parcelable
import com.stocksexchange.android.model.SortTypes
import kotlinx.android.parcel.Parcelize

/**
 * Parameters for storing trade related data.
 */
@Parcelize
data class TradeParameters(
    val marketName: String,
    val count: Int,
    val sortType: SortTypes
) : Parcelable {


    companion object {


        /**
         * Retrieves a default set of parameters.
         *
         * @return The default trade parameters
         */
        fun getDefaultParameters(): TradeParameters {
            return TradeParameters(
                marketName = "",
                count = 100,
                sortType = SortTypes.DESC
            )
        }


        /**
         * Retrieves a default set of parameters.
         *
         * @param marketName The name of the market
         *
         * @return The default trade parameters
         */
        fun getDefaultParameters(marketName: String): TradeParameters {
            return getDefaultParameters().copy(marketName = marketName)
        }


    }


    /**
     * Generates a cache key from unique and important fields.
     *
     * @return The cache key
     */
    fun getCacheKey(): String {
        return marketName
    }


}
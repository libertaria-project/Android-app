package com.stocksexchange.android.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Parameters storing orderbook related data.
 */
@Parcelize
data class OrderbookParameters(
    val marketName: String
) : Parcelable {


    companion object {

        /**
         * Retrieves default orderbook parameters.
         *
         * @return The default orderbook parameters
         */
        fun getDefaultParameters(): OrderbookParameters {
            return OrderbookParameters(
                marketName = "Invalid"
            )
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
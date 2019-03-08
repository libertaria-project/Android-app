package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.stocksexchange.android.api.model.newapi.utils.HasUniqueKey
import kotlinx.android.parcel.Parcelize

/**
 * A model class holding orderbook related parameters.
 */
@Parcelize
data class OrderbookParameters(
    val currencyPairId: Int,
    val bidOrdersCount: Int,
    val askOrdersCount: Int
) : Parcelable, HasUniqueKey {


    companion object {


        /**
         * Retrieves a default set of parameters.
         *
         * @return The default orderbook parameters
         */
        fun getDefaultParameters(): OrderbookParameters {
            return OrderbookParameters(
                currencyPairId = -1,
                bidOrdersCount = Integer.MAX_VALUE,
                askOrdersCount = Integer.MAX_VALUE
            )
        }


        /**
         * Retrieves a default set of parameters.
         *
         * @param currencyPairId The ID of the currency pair of an orderbook
         *
         * @return The default orderbook parameters
         */
        fun getDefaultParameters(currencyPairId: Int): OrderbookParameters {
            return getDefaultParameters().copy(currencyPairId = currencyPairId)
        }


    }


    override val uniqueKey: String
        get() = currencyPairId.toString()


}
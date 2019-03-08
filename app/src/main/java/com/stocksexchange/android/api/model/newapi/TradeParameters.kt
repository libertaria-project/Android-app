package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.stocksexchange.android.api.model.newapi.utils.HasUniqueKey
import kotlinx.android.parcel.Parcelize

/**
 * A model class holding trade related parameters.
 */
@Parcelize
data class TradeParameters(
    val currencyPairId: Int,
    val count: Int,
    val sortOrder: SortOrders
) : Parcelable, HasUniqueKey {


    companion object {


        /**
         * Retrieves a default set of parameters.
         *
         * @return The default trade parameters
         */
        fun getDefaultParameters(): TradeParameters {
            return TradeParameters(
                currencyPairId = -1,
                count = 100,
                sortOrder = SortOrders.DESC
            )
        }


        /**
         * Retrieves a default set of parameters.
         *
         * @param currencyPairId The ID of the currency pair of trades
         *
         * @return The default trade parameters
         */
        fun getDefaultParameters(currencyPairId: Int): TradeParameters {
            return getDefaultParameters().copy(currencyPairId = currencyPairId)
        }


    }


    override val uniqueKey: String
        get() = currencyPairId.toString()


}
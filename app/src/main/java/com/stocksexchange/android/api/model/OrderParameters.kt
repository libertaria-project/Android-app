package com.stocksexchange.android.api.model

import android.os.Parcelable
import com.stocksexchange.android.model.OrderModes
import com.stocksexchange.android.model.SortTypes
import kotlinx.android.parcel.Parcelize

/**
 * Parameters storing order related data.
 */
@Parcelize
data class OrderParameters(
    val marketName: String,
    val searchQuery: String,
    val mode: OrderModes,
    val type: OrderTypes,
    val status: OrderStatuses,
    val tradeType: OrderTradeTypes,
    val ownerType: OrderOwnerTypes,
    val sortType: SortTypes,
    val count: Int
) : Parcelable {


    companion object {

        const val DEFAULT_MARKET_NAME = "ALL"

        const val DEFAULT_ORDERS_COUNT = 50


        /**
         * Retrieves parameters for loading active orders.
         *
         * @return The parameters for loading active orders
         */
        fun getActiveOrdersParameters(): OrderParameters {
            return getDefaultParameters().copy(
                marketName = OrderParameters.DEFAULT_MARKET_NAME,
                type = OrderTypes.ACTIVE,
                status = OrderStatuses.PENDING,
                mode = OrderModes.STANDARD
            )
        }


        /**
         * Retrieves parameters for loading completed orders.
         *
         * @return The parameters for loading completed orders
         */
        fun getCompletedOrdersParameters(): OrderParameters {
            return getDefaultParameters().copy(
                marketName = OrderParameters.DEFAULT_MARKET_NAME,
                type = OrderTypes.COMPLETED,
                status = OrderStatuses.FINISHED,
                mode = OrderModes.STANDARD
            )
        }


        /**
         * Retrieves parameters for loading cancelled orders.
         *
         * @return The parameters for loading cancelled orders
         */
        fun getCancelledOrdersParameters(): OrderParameters {
            return getDefaultParameters().copy(
                marketName = OrderParameters.DEFAULT_MARKET_NAME,
                type = OrderTypes.CANCELLED,
                status = OrderStatuses.CANCELLED,
                mode = OrderModes.STANDARD
            )
        }


        /**
         * Retrieves parameters for loading search orders.
         *
         * @param type The type of orders to search
         *
         * @return The parameters for loading search orders
         */
        fun getSearchOrdersParameters(type: OrderTypes): OrderParameters {
            return getDefaultParameters().copy(
                marketName = OrderParameters.DEFAULT_MARKET_NAME,
                type = type,
                status = OrderStatuses.INVALID,
                mode = OrderModes.SEARCH
            )
        }


        /**
         * Retrieves a default set of parameters.
         *
         * @return The default order parameters
         */
        fun getDefaultParameters(): OrderParameters {
            return OrderParameters(
                marketName = DEFAULT_MARKET_NAME,
                searchQuery = "",
                mode = OrderModes.STANDARD,
                type = OrderTypes.ACTIVE,
                status = OrderStatuses.INVALID,
                tradeType = OrderTradeTypes.ALL,
                ownerType = OrderOwnerTypes.OWN,
                sortType = SortTypes.DESC,
                count = DEFAULT_ORDERS_COUNT
            )
        }

    }


    /**
     * A field that returns lowercase version of the search query.
     */
    val lowercasedSearchQuery: String
        get() = searchQuery.toLowerCase()


}
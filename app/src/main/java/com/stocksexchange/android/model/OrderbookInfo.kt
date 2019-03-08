package com.stocksexchange.android.model

import android.os.Parcelable
import com.stocksexchange.android.api.model.Orderbook
import kotlinx.android.parcel.Parcelize

/**
 * A model class holding data about an orderbook.
 */
@Parcelize
data class OrderbookInfo(
    val highestBid: Double,
    val lowestAsk: Double,
    val buyVolume: Double,
    val sellVolume: Double
) : Parcelable {


    companion object {


        /**
         * Returns an instance of this class by fetching all necessary information
         * from the passed in orderbook.
         *
         * @param orderbook The orderbook to fetch data from
         *
         * @return The instance of [OrderbookInfo]
         */
        fun newInstance(orderbook: Orderbook): OrderbookInfo {
            return OrderbookInfo(
                highestBid = if(orderbook.isBuyOrdersEmpty()) 0.0 else orderbook.buyOrders.first().price,
                lowestAsk = if(orderbook.isSellOrdersEmpty()) 0.0 else orderbook.sellOrders.first().price,
                buyVolume = orderbook.buyOrdersVolume,
                sellVolume = orderbook.sellOrdersVolume
            )
        }


    }




    /**
     * Checks whether this instance has a valid highest bid.
     *
     * @return true if has; false otherwise
     */
    fun hasHighestBid(): Boolean {
        return (highestBid > 0.0)
    }


    /**
     * Checks whether this instance has a valid lowest ask.
     *
     * @return true if has; false otherwise
     */
    fun hasLowestAsk(): Boolean {
        return (lowestAsk > 0.0)
    }


    /**
     * Checks whether this instance has a valid buy volume.
     *
     * @return true if has; false otherwise
     */
    fun hasBuyVolume(): Boolean {
        return (buyVolume > 0.0)
    }


    /**
     * Checks whether this instance has a valid sell volume.
     *
     * @return true if has; false otherwise
     */
    fun hasSellVolume(): Boolean {
        return (sellVolume > 0.0)
    }


}
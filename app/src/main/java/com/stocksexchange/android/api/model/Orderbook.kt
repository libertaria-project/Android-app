package com.stocksexchange.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.stocksexchange.android.api.services.StocksExchangeService
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing the [StocksExchangeService.getOrderbook] call response.
 */
@Parcelize
data class Orderbook(
    @SerializedName("buy") val buyOrders: List<OrderbookOrder>,
    @SerializedName("sell") val sellOrders: List<OrderbookOrder>
) : Parcelable {


    /**
     * Returns a volume of the buy orders.
     */
    @IgnoredOnParcel
    var buyOrdersVolume: Double = getOrderbookOrdersVolume(buyOrders)
        private set
        get() {
            if((field == 0.0) && !isBuyOrdersEmpty()) {
                field = getOrderbookOrdersVolume(buyOrders)
            }

            return field
        }


    /**
     * Retrieves a volume of the sell orders.
     */
    @IgnoredOnParcel
    var sellOrdersVolume: Double = getOrderbookOrdersVolume(sellOrders)
        private set
        get() {
            if((field == 0.0) && !isSellOrdersEmpty()) {
                field = getOrderbookOrdersVolume(sellOrders)
            }

            return field
        }




    private fun getOrderbookOrdersVolume(orderbookOrders: List<OrderbookOrder>): Double {
        var volume = 0.0

        for(orderbookOrder in orderbookOrders) {
            volume += orderbookOrder.amount
        }

        return volume
    }


    /**
     * Checks whether the buy orders is empty or not.
     *
     * @return true if empty; false otherwise
     */
    fun isBuyOrdersEmpty(): Boolean {
        return buyOrders.isEmpty()
    }


    /**
     * Checks whether the sell orders is empty or not.
     *
     * @return true if empty; false otherwise
     */
    fun isSellOrdersEmpty(): Boolean {
        return sellOrders.isEmpty()
    }


    /**
     * Checks whether this orderbook instance is empty.
     *
     * @return true if empty; false otherwise
     */
    fun isEmpty(): Boolean {
        return (isBuyOrdersEmpty() && isSellOrdersEmpty())
    }


    /**
     * Returns the largest orders count.
     *
     * @return The largest orders count
     */
    fun getLargestOrdersCount(): Int {
        return if(buyOrders.size > sellOrders.size) {
            buyOrders.size
        } else {
            sellOrders.size
        }
    }


}
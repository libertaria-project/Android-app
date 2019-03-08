package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing an orderbook.
 */
@Parcelize
data class Orderbook(
    @SerializedName("bid") val buyOrders: List<OrderbookOrder>,
    @SerializedName("ask") val sellOrders: List<OrderbookOrder>
) : Parcelable {


    /**
     * A field that returns a boolean flag indicating
     * whether the buy orders are empty or not.
     */
    val isBuyOrdersEmpty: Boolean
        get() = buyOrders.isEmpty()


    /**
     * A field that returns a boolean flag indicating
     * whether the sell orders are empty or not.
     */
    val isSellOrdersEmpty: Boolean
        get() = sellOrders.isEmpty()


    /**
     * A field that returns a boolean flag indicating
     * whether the orders are empty or not.
     */
    val isEmpty: Boolean
        get() = (isBuyOrdersEmpty && isSellOrdersEmpty)


    /**
     * A field that returns a number indicating
     * the largest count of orders by type (BUY vs SELL).
     */
    val largestOrdersCount: Int
        get() {
            return if(buyOrders.size > sellOrders.size) {
                buyOrders.size
            } else {
                sellOrders.size
            }
        }


    /**
     * Returns a volume of the buy orders.
     */
    @IgnoredOnParcel
    var buyOrdersVolume: Double = getOrdersVolume(buyOrders)
        private set
        get() {
            if((field == 0.0) && !isBuyOrdersEmpty) {
                field = getOrdersVolume(buyOrders)
            }

            return field
        }


    /**
     * Returns a volume of the sell orders.
     */
    @IgnoredOnParcel
    var sellOrdersVolume: Double = getOrdersVolume(sellOrders)
        private set
        get() {
            if((field == 0.0) && !isSellOrdersEmpty) {
                field = getOrdersVolume(sellOrders)
            }

            return field
        }




    private fun getOrdersVolume(orders: List<OrderbookOrder>): Double {
        var volume = 0.0

        for(order in orders) {
            volume += order.baseCurrencyAmount
        }

        return volume
    }


}
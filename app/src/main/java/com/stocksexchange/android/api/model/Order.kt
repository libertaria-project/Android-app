package com.stocksexchange.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.stocksexchange.android.utils.helpers.extractBaseCurrencySymbol
import com.stocksexchange.android.utils.helpers.extractQuoteCurrencySymbol
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing user's orders.
 *
 * Note (28 April, 2018): Need to provide @SerializedName
 * for the "type" property since the GSON deserializer will
 * throw an exception saying "class Order declares multiple
 * JSON fields named type".
 */
@Parcelize
data class Order(
    @SerializedName("pair") var marketName: String = "",
    @SerializedName("type") var tradeType: String = "",
    @SerializedName("amount") var amount: Double = -1.0,
    @SerializedName("rate") var rate: Double = -1.0,
    @SerializedName("original_amount") var originalAmount: Double = -1.0,
    @SerializedName("buy_amount") var buyAmount: Double = -1.0,
    @SerializedName("sell_amount") var sellAmount: Double = -1.0,
    @SerializedName("rates") var ratesMap: Map<Double, OrderAmount> = mapOf(),
    @SerializedName("timestamp") var timestamp: Long = 0L,
    @SerializedName("id") var id: Long = -1L,
    @SerializedName("irrelevant") var type: String = ""
) : Parcelable, Comparable<Order> {


    companion object {

        const val TRADE_TYPE_BUY = "buy"
        const val TRADE_TYPE_SELL = "sell"


        /**
         * Converts an active order to a cancelled one.
         *
         * @param order The active order to convert
         *
         * @return The cancelled order
         */
        fun convertFromActiveToCancelled(order: Order): Order {
            return order.copy(
                type = OrderTypes.CANCELLED.name,
                originalAmount = order.amount,
                amount = -1.0,
                ratesMap = mapOf(order.rate to OrderAmount()),
                rate = -1.0
            )
        }

    }




    /**
     * A field holding the base currency symbol of the market name.
     */
    @IgnoredOnParcel
    var baseCurrencySymbol: String = ""
        private set
        get() {
            if(field.isBlank()) {
                field = extractBaseCurrencySymbol(marketName)
            }

            return field
        }


    /**
     * A field holding the quote currency symbol of the market name.
     */
    @IgnoredOnParcel
    var quoteCurrencySymbol: String = ""
        private set
        get() {
            if(field.isBlank()) {
                field = extractQuoteCurrencySymbol(marketName)
            }

            return field
        }


    override fun compareTo(other: Order): Int {
        return when {
            (timestamp > other.timestamp) -> 1
            (timestamp < other.timestamp) -> -1
            else -> 0
        }
    }


    /**
     * Checks whether the trade type is of buy type.
     *
     * @return true if trade type is buy; false otherwise
     */
    fun isBuyTrade(): Boolean {
        return (tradeType.toLowerCase() == TRADE_TYPE_BUY)
    }


    /**
     * Checks whether the trade type is of sell type.
     *
     * @return true if trade type is sell; false otherwise
     */
    fun isSellTrade(): Boolean {
        return (tradeType.toLowerCase() == TRADE_TYPE_SELL)
    }


}
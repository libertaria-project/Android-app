package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing an order.
 */
@Parcelize
data class Order(
    @SerializedName("id") val id: Long,
    @SerializedName("currency_pair_id") val currencyPairId: Int,
    @SerializedName("price") val price: Double,
    @SerializedName("trigger_price") val triggerPrice: Double = -1.0,
    @SerializedName("initial_amount") val initialAmount: Double,
    @SerializedName("processed_amount") val processedAmount: Double,
    @SerializedName("type") val typeStr: String,
    @SerializedName("original_type") val originalTypeStr: String = "",
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("status") val statusStr: String,
    @SerializedName("trades") val trades: List<Trade> = listOf(),
    @SerializedName("fees") val fees: List<Fee> = listOf()
) : Parcelable {


    /**
     * A field that returns a boolean flag indicating
     * whether this order has a trigger price.
     */
    val hasTriggerPrice: Boolean
        get() = (triggerPrice > 0.0)


    /**
     * A field that returns a boolean flag indicating
     * whether this order has an original type.
     */
    val hasOriginalType: Boolean
        get() = originalTypeStr.isNotEmpty()


    /**
     * A field that returns a boolean flag indicating
     * whether this order has trades or not.
     */
    val hasTrades: Boolean
        get() = trades.isNotEmpty()


    /**
     * A field that returns a boolean flag indicating
     * whether this order has fees or not.
     */
    val hasFees: Boolean
        get() = fees.isNotEmpty()


    /**
     * A field that returns a type of the order.
     */
    val type: OrderTypes
        get() = when(typeStr) {
            OrderTypes.BUY.name -> OrderTypes.BUY
            OrderTypes.SELL.name -> OrderTypes.SELL

            else -> throw IllegalStateException("Cannot recognize order's type: $typeStr")
        }


    /**
     * A field that returns an original type of the order.
     */
    val originalType: OrderTypes
        get() = when(originalTypeStr) {
            OrderTypes.BUY.name -> OrderTypes.BUY
            OrderTypes.SELL.name -> OrderTypes.SELL

            else -> throw IllegalStateException("Cannot recognize order's original type: $originalTypeStr")
        }


    /**
     * A field that returns a status of the order.
     */
    val status: OrderStatuses
        get() = when(statusStr) {
            OrderStatuses.PENDING.name -> OrderStatuses.PENDING
            OrderStatuses.PROCESSING.name -> OrderStatuses.PROCESSING
            OrderStatuses.FINISHED.name -> OrderStatuses.FINISHED
            OrderStatuses.PARTIAL.name -> OrderStatuses.PARTIAL
            OrderStatuses.CANCELED.name -> OrderStatuses.CANCELED

            else -> OrderStatuses.UNKNOWN
        }


    /**
     * A model class representing a trade of the order.
     */
    @Parcelize
    data class Trade(
        @SerializedName("id") val id: Long,
        @SerializedName("buy_order_id") val buyOrderId: Long,
        @SerializedName("sell_order_id") val sellOrderId: Long,
        @SerializedName("price") val price: Double,
        @SerializedName("amount") val amount: Double,
        @SerializedName("tradeType") val tradeTypeStr: String,
        @SerializedName("timestamp") val timestamp: Long
    ) : Parcelable {

        /**
         * A field that returns a type of the trade.
         */
        val tradeType: TradeTypes
            get() = when(tradeTypeStr) {
                TradeTypes.BUY.name -> TradeTypes.BUY
                TradeTypes.SELL.name -> TradeTypes.SELL

                else -> throw IllegalStateException("Cannot recognize the trade type: $tradeTypeStr")
            }

    }


    /**
     * A model class representing a fee of the trade of the order.
     */
    @Parcelize
    data class Fee(
        @SerializedName("id") val id: Long,
        @SerializedName("currency_id") val currencyId: Int,
        @SerializedName("amount") val amount: Double,
        @SerializedName("timestamp") val timestamp: Long
    ) : Parcelable


}
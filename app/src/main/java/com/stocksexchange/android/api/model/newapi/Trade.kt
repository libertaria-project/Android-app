package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing a trade.
 */
@Parcelize
data class Trade(
    @SerializedName("id") val id: Long,
    @SerializedName("price") val price: Double,
    @SerializedName("amount") val amount: Double,
    @SerializedName("type") val typeStr: String,
    @SerializedName("timestamp") val timestamp: Long
) : Parcelable, Comparable<Trade> {


    companion object {

        private const val STUB_TRADE_ID = -1L


        /**
         * A stub buy trade.
         */
        val STUB_BUY_TRADE = Trade(
            id = STUB_TRADE_ID,
            timestamp = 0L,
            price = -1.0,
            amount = -1.0,
            typeStr = TradeTypes.BUY.name
        )

        /**
         * A stub sell trade.
         */
        val STUB_SELL_TRADE = Trade(
            id = STUB_TRADE_ID,
            timestamp = 0L,
            price = -1.0,
            amount = -1.0,
            typeStr = TradeTypes.SELL.name
        )

    }


    /**
     * A field that returns a boolean flag indicating
     * whether this trade is a stub or not.
     */
    val isStub: Boolean
        get() = (id == STUB_TRADE_ID)


    /**
     * A field that returns a type of the trade.
     */
    val type: TradeTypes
        get() = when(typeStr) {
            TradeTypes.BUY.name -> TradeTypes.BUY
            TradeTypes.SELL.name -> TradeTypes.SELL

            else -> throw IllegalStateException("Cannot recognize the trade type: $typeStr")
        }




    override fun compareTo(other: Trade): Int {
        return when {
            (timestamp > other.timestamp) -> 1
            (timestamp < other.timestamp) -> -1
            else -> 0
        }
    }


}
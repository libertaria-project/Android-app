package com.stocksexchange.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing a trade.
 */
@Parcelize
data class Trade(
    @SerializedName("id") val id: Long,
    @SerializedName("timestamp") val timestamp: Long, // in seconds
    @SerializedName("price") val price: Double,
    @SerializedName("quantity") val amount: Double,
    @SerializedName("type") val type: String
) : Parcelable, Comparable<Trade> {


    companion object {

        const val TYPE_BUY = "buy"
        const val TYPE_SELL = "sell"

        private const val STUB_TRADE_ID = -1L


        /**
         * A stub buy trade.
         */
        val STUB_BUY_TRADE = Trade(
            id = STUB_TRADE_ID,
            timestamp = 0L,
            price = -1.0,
            amount = -1.0,
            type = TYPE_BUY
        )

        /**
         * A stub sell trade.
         */
        val STUB_SELL_TRADE = Trade(
            id = STUB_TRADE_ID,
            timestamp = 0L,
            price = -1.0,
            amount = -1.0,
            type = TYPE_SELL
        )

    }


    /**
     * A field that returns the timestamp field in milliseconds.
     */
    val timestampInMillis: Long
        get() = (timestamp * 1000L)




    override fun compareTo(other: Trade): Int {
        return when {
            (timestamp > other.timestamp) -> 1
            (timestamp < other.timestamp) -> -1
            else -> 0
        }
    }


    /**
     * Checks whether this trade contains data or is a stub.
     *
     * @return true if stub; false otherwise
     */
    fun isStub(): Boolean {
        return (id == STUB_TRADE_ID)
    }


    /**
     * Checks whether the trade type is of buy type.
     *
     * @return true if trade type is buy; false otherwise
     */
    fun isBuyTrade(): Boolean {
        return (type.toLowerCase() == TYPE_BUY)
    }


    /**
     * Checks whether the trade type is of sell type.
     *
     * @return true if trade type is sell; false otherwise
     */
    fun isSellTrade(): Boolean {
        return (type.toLowerCase() == TYPE_SELL)
    }


}
package com.stocksexchange.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing the data of an orderbook order.
 */
@Parcelize
data class OrderbookOrder(
    @SerializedName("Quantity") val amount: Double,
    @SerializedName("Rate") val price: Double
) : Parcelable {


    companion object {

        private const val STUB_ORDERBOOK_ORDER_AMOUNT = -1.0
        private const val STUB_ORDERBOOK_ORDER_PRICE = -1.0


        /**
         * A stub orderbook order.
         */
        val STUB_ORDERBOOK_ORDER = OrderbookOrder(
            STUB_ORDERBOOK_ORDER_AMOUNT,
            STUB_ORDERBOOK_ORDER_PRICE
        )

    }


    /**
     * Checks whether this order contains data or is a stub.
     *
     * @return true if stub; false otherwise
     */
    fun isStub(): Boolean {
        return ((amount == STUB_ORDERBOOK_ORDER_AMOUNT) &&
                (price == STUB_ORDERBOOK_ORDER_PRICE))
    }


}
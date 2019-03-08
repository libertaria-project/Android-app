package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing the data of an orderbook order.
 */
@Parcelize
data class OrderbookOrder(
    @SerializedName("price") val price: Double,
    @SerializedName("amount") val baseCurrencyAmount: Double,
    @SerializedName("amount2") val quoteCurrencyAmount: Double
) : Parcelable {


    companion object {

        private const val STUB_ORDERBOOK_FIELD_VALUE = -1.0


        /**
         * A stub orderbook order.
         */
        val STUB_ORDERBOOK_ORDER = OrderbookOrder(
            price = STUB_ORDERBOOK_FIELD_VALUE,
            baseCurrencyAmount = STUB_ORDERBOOK_FIELD_VALUE,
            quoteCurrencyAmount = STUB_ORDERBOOK_FIELD_VALUE
        )

    }


    /**
     * A field that returns a boolean flag indicating
     * whether this order is stub or not.
     */
    val isStub: Boolean
        get() = (
            (price == STUB_ORDERBOOK_FIELD_VALUE) &&
            (baseCurrencyAmount == STUB_ORDERBOOK_FIELD_VALUE) &&
            (quoteCurrencyAmount == STUB_ORDERBOOK_FIELD_VALUE)
        )


}
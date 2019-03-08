package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing parameters for creating an order.
 */
@Parcelize
data class OrderCreationParameters(
    val currencyPairId: Int,
    val type: OrderTypes,
    val amount: String,
    val price: String
) : Parcelable
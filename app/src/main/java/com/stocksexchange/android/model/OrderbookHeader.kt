package com.stocksexchange.android.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A model class holding necessary header data of an orderbook.
 */
@Parcelize
data class OrderbookHeader(
    val title: String,
    val type: OrderbookOrderTypes
) : Parcelable
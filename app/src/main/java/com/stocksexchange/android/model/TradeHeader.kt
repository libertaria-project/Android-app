package com.stocksexchange.android.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A model class holding necessary header data of the trade history.
 */
@Parcelize
data class TradeHeader(
    val id: Long,
    val amountTitleText: String,
    val priceTitleText: String,
    val timeTitleText: String
) : Parcelable
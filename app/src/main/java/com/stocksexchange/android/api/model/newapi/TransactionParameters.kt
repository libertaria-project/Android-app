package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing parameters for loading transactions
 * (deposits and withdrawals).
 */
@Parcelize
data class TransactionParameters(
    val currencyCode: String,
    val sortOrder: SortOrders,
    val limit: Int,
    val offset: Int
) : Parcelable
package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing parameters for loading wallets.
 */
@Parcelize
data class WalletParameters(
    val sortOrder: SortOrders,
    val sortColumn: WalletBalanceTypes
) : Parcelable
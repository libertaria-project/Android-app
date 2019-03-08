package com.stocksexchange.android.model

import android.os.Parcelable
import com.stocksexchange.android.api.model.Currency
import kotlinx.android.parcel.Parcelize

/**
 * A model class holding wallet related data.
 */
@Parcelize
data class Wallet(
    val currency: Currency,
    val availableBalance: Double,
    val balanceInOrders: Double
): Parcelable
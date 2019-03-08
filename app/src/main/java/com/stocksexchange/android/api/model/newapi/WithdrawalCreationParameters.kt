package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing parameters for creating a withdrawal.
 */
@Parcelize
data class WithdrawalCreationParameters(
    val currencyId: Int,
    val amount: String,
    val address: String,
    val additionalAddress: String
) : Parcelable
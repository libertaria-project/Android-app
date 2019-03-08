package com.stocksexchange.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing order buy and sell amount.
 */
@Parcelize
data class OrderAmount(
    @SerializedName("buy_amount") var buyAmount: Double = -1.0,
    @SerializedName("sell_amount") var sellAmount: Double = -1.0
) : Parcelable
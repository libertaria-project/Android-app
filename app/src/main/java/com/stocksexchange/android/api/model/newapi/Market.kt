package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing a market of the exchange.
 */
@Parcelize
data class Market(
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String
) : Parcelable
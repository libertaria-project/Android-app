package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing a profile information of the user.
 */
@Parcelize
data class ProfileInfo(
    @SerializedName("email") val email: String,
    @SerializedName("username") val userName: String
) : Parcelable
package com.stocksexchange.android.api.model.newapi

import com.google.gson.annotations.SerializedName

/**
 * A wrapper model class used for encapsulating data
 * returned from the server when trying to sign up.
 */
data class SignUpResponse(
    @SerializedName("message") val data: String = "",
    @SerializedName("errors") val errorMessages: List<String> = listOf()
)
package com.stocksexchange.android.api.model

import com.google.gson.annotations.SerializedName

/**
 * A typical StocksExchange API response wrapper.
 */
data class ApiResponse<out Data>(
    @SerializedName("success") val success: Int = 0,
    @SerializedName("data", alternate = ["result"]) val data: Data? = null,
    @SerializedName("error") val error: String = ""
)
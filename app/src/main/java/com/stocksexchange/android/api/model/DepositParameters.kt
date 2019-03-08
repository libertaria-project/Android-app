package com.stocksexchange.android.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Parameters storing deposit related data.
 */
@Parcelize
data class DepositParameters(
    val currency: String
) : Parcelable {


    /**
     * Generates a cache key from unique and important fields.
     *
     * @return The cache key
     */
    fun getCacheKey(): String {
        return currency
    }


}
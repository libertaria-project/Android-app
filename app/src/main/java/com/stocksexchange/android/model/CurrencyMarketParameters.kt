package com.stocksexchange.android.model

import android.os.Parcelable
import com.stocksexchange.android.model.CurrencyMarketTypes
import kotlinx.android.parcel.Parcelize

/**
 * Parameters storing currency markets related data.
 */
@Parcelize
data class CurrencyMarketParameters(
    val searchQuery: String,
    val currencyMarketType: CurrencyMarketTypes
) : Parcelable {


    /**
     * A field that returns lowercase version of the search query.
     */
    val lowercasedSearchQuery: String
        get() = searchQuery.toLowerCase()


}
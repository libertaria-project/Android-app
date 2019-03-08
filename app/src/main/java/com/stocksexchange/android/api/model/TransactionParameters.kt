package com.stocksexchange.android.api.model

import android.os.Parcelable
import com.stocksexchange.android.model.SortTypes
import com.stocksexchange.android.model.TransactionModes
import com.stocksexchange.android.model.TransactionTypes
import kotlinx.android.parcel.Parcelize

/**
 * Parameters storing transaction related data.
 */
@Parcelize
data class TransactionParameters(
    val mode: TransactionModes,
    val type: TransactionTypes,
    val currency: String,
    val operation: TransactionOperations,
    val status: TransactionStatuses,
    val sortType: SortTypes,
    val count: Int,
    val searchQuery: String
) : Parcelable {


    companion object {


        private const val DEFAULT_CURRENCY = "ALL"
        private const val DEFAULT_COUNT = 50


        /**
         * Retrieves a default set of parameters.
         *
         * @return The default order parameters
         */
        fun getDefaultParameters(): TransactionParameters {
            return TransactionParameters(
                mode = TransactionModes.STANDARD,
                type = TransactionTypes.DEPOSITS,
                currency = DEFAULT_CURRENCY,
                operation = TransactionOperations.DEPOSIT,
                status = TransactionStatuses.ALL,
                sortType = SortTypes.DESC,
                count = DEFAULT_COUNT,
                searchQuery = ""
            )
        }


    }


    /**
     * A field that returns lowercase version of the search query.
     */
    val lowercasedSearchQuery: String
        get() = searchQuery.toLowerCase()


    /**
     * Generates a cache key from unique and important fields.
     *
     * @return The cache key
     */
    fun getCacheKey(): String {
        return "${type}_${currency}_${status}_${sortType}_$count"
    }


}
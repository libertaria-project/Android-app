package com.stocksexchange.android.api.model

import android.os.Parcelable
import com.stocksexchange.android.model.WalletModes
import kotlinx.android.parcel.Parcelize

/**
 * Parameters storing wallet related data.
 */
@Parcelize
data class WalletParameters(
    val shouldShowEmptyWallets: Boolean,
    val searchQuery: String,
    val walletMode: WalletModes
) : Parcelable {


    companion object {

        /**
         * Retrieves default wallet parameters.
         *
         * @return The default wallet parameters
         */
        fun getDefaultParameters(): WalletParameters {
            return WalletParameters(
                shouldShowEmptyWallets = true,
                searchQuery = "",
                walletMode = WalletModes.STANDARD
            )
        }

    }


    /**
     * A field that returns lowercase version of the search query.
     */
    val lowercasedSearchQuery: String
        get() = searchQuery.toLowerCase()


}
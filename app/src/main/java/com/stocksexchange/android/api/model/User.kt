package com.stocksexchange.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing a user.
 */
@Parcelize
data class User (
    @SerializedName("username") val userName: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("hash") val hash: String = "",
    @SerializedName("userSessions") val sessions: List<UserSession> = listOf(),
    @SerializedName("funds") val funds: Map<String, String> = mapOf(),
    @SerializedName("hold_funds") val holdFunds: Map<String, String> = mapOf(),
    @SerializedName("wallets_addresses") val walletAddresses: Map<String, String> = mapOf(),
    @SerializedName("publick_key") val walletPublicKeys: Map<String, String> = mapOf(),
    @SerializedName("Assets portfolio") val assetsPortfolio: AssetsPortfolio? = null,
    @SerializedName("open_orders") val openOrders: Int = 0
) : Parcelable {


    companion object {

        private const val STUB_USER_NAME = "none"
        private const val STUB_EMAIL = "email"

        /**
         * A stub user object.
         */
        val STUB_USER = User(userName = STUB_USER_NAME, email = STUB_EMAIL)

    }




    /**
     * Checks whether this user is a stub.
     *
     * @return true if stub; false otherwise
     */
    fun isStub(): Boolean {
        return ((userName == STUB_USER_NAME) && (email == STUB_EMAIL))
    }


}
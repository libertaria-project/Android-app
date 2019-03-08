package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.stocksexchange.android.api.utils.hashSha1
import kotlinx.android.parcel.Parcelize

/**
 * A model class holding login related parameters.
 */
@Parcelize
data class SignInParameters(
    val email: String,
    val password: String,
    val key: String,
    val code: String
) : Parcelable {


    companion object {

        const val KEY_STRING_LENGTH = 20


        /**
         * Retrieves a default set of parameters.
         *
         * @return The default sign in parameters
         */
        fun getDefaultParameters(): SignInParameters {
            return SignInParameters(
                email = "",
                password = "",
                key = "",
                code = ""
            )
        }


    }


    /**
     * A field that returns a SHA-1 hash of the key.
     */
    val hashedKey: String
        get() = hashSha1(key)


}
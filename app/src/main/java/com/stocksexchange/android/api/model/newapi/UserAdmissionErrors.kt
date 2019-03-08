package com.stocksexchange.android.api.model.newapi

import androidx.annotation.StringRes
import com.stocksexchange.android.R

/**
 * An enumeration of all possible errors that can be
 * returned in response to signing in or signing up.
 */
enum class UserAdmissionErrors(val message: String, @StringRes val stringId: Int) {


    EMAIL_TAKEN(
        message = "The email has already been taken.",
        stringId = R.string.error_email_taken
    ),
    PASSWORD_LENGTH(
        message = "The password must be between 6 and 32 characters.",
        stringId = R.string.error_password_length
    ),
    PASSWORD_COMMON(
        message = "This password is just too common. Please try another!",
        stringId = R.string.error_password_common
    ),

    UNKNOWN(
        message = "Unknown error.",
        stringId = -1
    );


    companion object {

        val LOGIN_ERRORS = arrayOf(PASSWORD_LENGTH)
        val REGISTRATION_ERRORS = arrayOf(EMAIL_TAKEN, PASSWORD_LENGTH, PASSWORD_COMMON)

    }


}
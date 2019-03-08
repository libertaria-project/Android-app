package com.stocksexchange.android.api.model.newapi

/**
 * An enumeration of all possible confirmation types
 * used for signing in.
 */
enum class SignInConfirmationTypes(val abbreviation: String) {

    EMAIL("email"),
    SMS("sms"),
    TWO_FACTOR_AUTHENTICATION("2fa"),

    UNKNOWN("unknown")

}
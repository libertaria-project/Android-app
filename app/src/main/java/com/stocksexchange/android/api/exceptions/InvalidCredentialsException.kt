package com.stocksexchange.android.api.exceptions

/**
 * Gets thrown whenever the user credentials are invalid.
 */
class InvalidCredentialsException(
    message: String = "",
    cause: Throwable? = null
) : IllegalStateException(message, cause)
package com.stocksexchange.android.api.exceptions

/**
 * Gets thrown whenever the user credentials are invalid.
 */
class MissingCredentialsException(
    message: String = "",
    cause: Throwable? = null
) : IllegalStateException(message, cause)
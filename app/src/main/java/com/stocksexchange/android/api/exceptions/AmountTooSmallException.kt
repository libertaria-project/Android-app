package com.stocksexchange.android.api.exceptions

/**
 * Gets thrown whenever the specified amount is too small.
 */
class AmountTooSmallException(
    message: String = "",
    throwable: Throwable? = null
) : ApiException(message, throwable)
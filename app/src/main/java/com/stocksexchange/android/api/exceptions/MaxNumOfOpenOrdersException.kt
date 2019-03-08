package com.stocksexchange.android.api.exceptions

/**
 * Gets thrown whenever a user tries to create more than
 * 10 open orders with the same price.
 */
class MaxNumOfOpenOrdersException(
    message: String = "",
    throwable: Throwable? = null
) : ApiException(message, throwable)
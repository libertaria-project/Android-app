package com.stocksexchange.android.database.tables.exceptions

/**
 * An exception to throw whenever something has not been found.
 */
open class NotFoundException(
    message: String = "",
    throwable: Throwable? = null
) : Exception(message, throwable)
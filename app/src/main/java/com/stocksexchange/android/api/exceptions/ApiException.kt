package com.stocksexchange.android.api.exceptions

/**
 * Gets thrown whenever an API error occurred.
 */
open class ApiException(
    message: String = "",
    throwable: Throwable? = null
) : Exception(message, throwable) {


    companion object {

        /**
         * Creates an instance with a message denoting that the returned
         * response is of invalid format.
         *
         * @param cause The optional cause exception
         *
         * @return The new instance
         */
        fun badResponse(cause: Throwable? = null): ApiException {
            return ApiException(
                message = "Bad Response.",
                throwable = cause
            )
        }

    }


}
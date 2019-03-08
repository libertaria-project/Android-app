package com.stocksexchange.android.api.exceptions

/**
 * An API exception to use when the provided parameters
 * for a particular endpoint were classified as invalid
 * by the server.
 */
class InvalidParametersException(
    message: String = "",
    cause: Throwable? = null
) : ApiException(message, cause) {


    companion object {

        private const val ERROR_WRONG_PARAMETERS = "Wrong parameters"

    }


    /**
     * A field that returns a boolean value indicating
     * whether the error is of wrong parameters type.
     */
    val isWrongParamsError: Boolean
        get() = (message == ERROR_WRONG_PARAMETERS)


}
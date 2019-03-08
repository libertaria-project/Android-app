package com.stocksexchange.android.api.model

/**
 * An enumeration of all errors that API can return.
 */
enum class ApiErrors(val message: String) {

    NO_WALLET("No Wallet"),
    NO_WALLET_ADDRESS("No Wallet Address"),
    INVALID_CURRENCY_CODE("Invalid Currency Code"),
    CURRENCY_DISABLED("Selected Currency is disabled"),
    INVALID_CREDENTIALS("The credentials are invalid"),
    AMOUNT_TOO_SMALL("Entered Amount is Too Small"),
    MAX_NUM_OF_OPEN_ORDERS("The maximum amount of open orders with the same price can not exceed  10")

}
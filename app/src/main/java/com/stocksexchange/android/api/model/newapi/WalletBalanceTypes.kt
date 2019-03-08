package com.stocksexchange.android.api.model.newapi

/**
 * An enumeration of all possible types of wallet balances.
 */
enum class WalletBalanceTypes(val type: String) {

    CURRENT("BALANCE"),
    FROZEN("FROZEN"),
    BONUS("BONUS"),
    TOTAL("TOTAL")

}
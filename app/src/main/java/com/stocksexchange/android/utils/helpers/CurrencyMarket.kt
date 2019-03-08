package com.stocksexchange.android.utils.helpers

import com.stocksexchange.android.Constants

/**
 * Extracts the base currency symbol from the market name.
 *
 * @param name The market name to extract the base currency symbol from
 *
 * @return The base currency symbol
 */
fun extractBaseCurrencySymbol(name: String): String {
   return extractSymbol(name, NameSymbols.BASE_CURRENCY)
}


/**
 * Extracts the quote currency symbol from the market name.
 *
 * @param name The market name to extract the quote currency symbol from
 *
 * @return The quote currency symbol
 */
fun extractQuoteCurrencySymbol(name: String): String {
    return extractSymbol(name, NameSymbols.QUOTE_CURRENCY)
}


private fun extractSymbol(name: String, nameSymbol: NameSymbols): String {
    return if(name.isBlank()) {
        ""
    } else {
        name.split(Constants.CURRENCY_MARKET_SEPARATOR)[nameSymbol.index]
    }
}


private enum class NameSymbols(val index: Int) {

    BASE_CURRENCY(0),
    QUOTE_CURRENCY(1)

}
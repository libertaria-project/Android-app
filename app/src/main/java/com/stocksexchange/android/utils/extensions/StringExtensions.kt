package com.stocksexchange.android.utils.extensions

import com.stocksexchange.android.utils.formatters.DoubleFormatter
import java.util.*

/**
 * Converts a string to a double in a locale-independent way.
 *
 * @param locale The locale to use for parsing
 * @param default The default value to return if
 * the string cannot be converted to a double
 *
 * @return The double converted from the string or the
 * default value
 */
fun String?.convertToDouble(locale: Locale, default: Double = 0.0): Double {
    if(this == null || isBlank()) {
        return default
    }

    return DoubleFormatter.getInstance(locale).parse(this, default)
}


/**
 * Truncates the string to the specified limit with the option
 * to ellipsize the ending of the string.
 *
 * @param characterLimit The number of characters to truncate
 * @param ellipsize Whether to ellipsize the ending or not
 *
 * @return The truncated string
 */
fun String.truncate(characterLimit: Int, ellipsize: Boolean = true): String {
    if(isBlank() || (length <= characterLimit)) {
        return this
    }

    return (substring(0, characterLimit) + (if(ellipsize) "…" else ""))
}


/**
 * Returns the index of the last digit in the string.
 *
 * @return The last digit index or -1 if there are
 * no digits in the string
 */
fun String.lastDigitIndex(): Int {
    for(i in (length - 1) downTo 0) {
        if(this[i].isDigit()) {
            return i
        }
    }

    return -1
}
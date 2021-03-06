package com.stocksexchange.android.utils.extensions

/**
 * Checks whether the double contains a fractional part.
 *
 * @return true if contains; false otherwise
 */
fun Double.hasFractionalPart(): Boolean {
    return ((this - this.toInt()) != 0.0)
}


/**
 * Returns a fractional part of the double if it has one
 * or a default value if not.
 *
 * @param default The default value to return if the double
 * does not have a fractional part
 *
 * @return The fractional part of the double or the default value
 */
fun Double.getFractionalPart(default: Double = 0.0): Double {
    return if(hasFractionalPart()) {
        (this - this.toInt())
    } else {
        default
    }
}


/**
 * Retrieves a fractional part precision from the double.
 *
 * @param maxPrecision The maximum precision
 * @param defaultPrecision The default precision to use if the
 * double does not have a fractional part
 * @param trimTrailingZeros Whether to trim trailing zeros or not
 *
 * @return The [defaultPrecision] if the double does not have a fractional part
 * or if [maxPrecision] if less than or equal to zero, [maxPrecision] if [trimTrailingZeros] is true,
 * or returns a precision by trimming the trailing zeros
 */
fun Double.getFractionalPartPrecision(maxPrecision: Int, defaultPrecision: Int = 0,
                                      trimTrailingZeros: Boolean = true): Int {
    if(!hasFractionalPart() || (maxPrecision <= 0)) {
        return defaultPrecision
    }

    if(!trimTrailingZeros) {
        return maxPrecision
    }

    val adjustedMaxPrecision = (maxPrecision + 1)
    val fractionalPart = String.format("%.${adjustedMaxPrecision}f", this).takeLast(adjustedMaxPrecision)
    var precision = 0

    for(characterIndex in 0 until maxPrecision) {
        if(fractionalPart[characterIndex] != '0') {
            precision = (characterIndex + 1)
        }
    }

    return precision
}
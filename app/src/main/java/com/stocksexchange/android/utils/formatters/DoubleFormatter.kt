package com.stocksexchange.android.utils.formatters

import com.stocksexchange.android.utils.extensions.getFractionalPartPrecision
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.ParseException
import java.util.Locale

/**
 * A formatter used for formatting double values.
 */
class DoubleFormatter private constructor(locale: Locale) {


    companion object {

        private const val ONE_HUNDRED_MILLIONTH = 0.00000001
        private const val TEN = 10.0
        private const val ONE_HUNDRED = 100.0
        private const val ONE_THOUSAND = 1_000.0
        private const val TEN_THOUSAND = 10_000.0
        private const val ONE_HUNDRED_THOUSAND = 100_000.0
        private const val ONE_MILLION = 1_000_000.0
        private const val TEN_MILLION = 10_000_000.0
        private const val ONE_HUNDRED_MILLION = 100_000_000.0
        private const val ONE_BILLION = 1_000_000_000.0
        private const val TEN_BILLION = 10_000_000_000.0
        private const val ONE_HUNDRED_BILLION = 100_000_000_000.0
        private const val ONE_TRILLION = 1_000_000_000_000.0

        private const val DEFAULT_GROUPING_SIZE = 3

        private const val DEFAULT_FIXED_PRICE_MAX_LENGTH = 10
        private const val DEFAULT_VOLUME_MAX_LENGTH = 7
        private const val DEFAULT_DAILY_PRICE_CHANGE_MAX_LENGTH = 6

        private const val FORMATTER_GROUPING_SYMBOL = ','
        private const val FORMATTER_DECIMAL_SYMBOL = '.'
        private const val FORMATTER_DIGIT_SYMBOL = '0'


        @Volatile
        private var INSTANCE : DoubleFormatter? = null


        fun getInstance(locale: Locale): DoubleFormatter {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDoubleFormatter(locale).also { INSTANCE = it }
            }
        }


        private fun buildDoubleFormatter(locale: Locale): DoubleFormatter {
            return DoubleFormatter(locale)
        }

    }


    private val mFormatter: DecimalFormat = (NumberFormat.getInstance(locale) as DecimalFormat)

    private var mIsGroupingEnabled: Boolean = true




    init {
        // Disabling the grouping in the formatter
        // since we are doing it manually due to the fact
        // that this flag is useless when the pattern
        // contains grouping delimiters
        mFormatter.isGroupingUsed = false
        mFormatter.roundingMode = RoundingMode.FLOOR
    }


    private fun format(payload: Payload): String {
        mFormatter.applyPattern(payload.pattern)
        return mFormatter.format(payload.value)
    }


    /**
     * Formats the market's volume real number to a string representation.
     *
     * @param value The value to format
     *
     * @return The market's volume in a string representation
     */
    fun formatVolume(value: Double): String = format(getVolumePayload(value))


    /**
     * Formats the market's daily volume real number to a string representation.
     *
     * @param value The value to format
     *
     * @return The market's daily volume in a string representation
     */
    fun formatDailyVolume(value: Double): String = format(getDailyVolumePayload(value))


    /**
     * Formats the price real number to a string representation.
     *
     * @param value The value to format
     *
     * @return The price value in a string representation
     */
    fun formatFixedPrice(value: Double): String = format(getFixedPricePayload(value))


    /**
     * Formats the market's last price change real number to a string representation.
     *
     * @param value The value to format
     *
     * @return The market's last price change value in a string representation
     */
    fun formatLastPriceChange(value: Double): String = format(getLastPriceChangePayload(value))


    /**
     * Formats the market's daily price change real number to a string representation.
     *
     * @param value The value to format
     *
     * @return The market's daily price change in a string representation
     */
    fun formatDailyPriceChange(value: Double): String = format(getDailyPriceChangePayload(value))


    /**
     * Formats the price real number to a string representation.
     *
     * @param value The value to format
     *
     * @return The price in a string representation
     */
    fun formatPrice(value: Double): String = format(getPricePayload(value))


    /**
     * Formats the min order amount real number to a string representation.
     *
     * @param value The value to format
     *
     * @return The min order amount in a string representation
     */
    fun formatMinOrderAmount(value: Double): String = format(getFixedPricePayload(value))


    /**
     * Formats the fee percent real number to a string representation.
     *
     * @param value The value to format
     *
     * @return The fee percent in a string representation
     */
    fun formatFeePercent(value: Double): String = format(getPercentPayload(value))


    /**
     * Formats the transaction fee real number to a string representation.
     *
     * @param value The value to format
     *
     * @return The transaction fee in a string representation
     */
    fun formatTransactionFee(value: Double): String = formatAmount(value)


    /**
     * Formats the balance real number to a string representation.
     *
     * @param value The value to format
     *
     * @return The balance in a string representation
     */
    fun formatBalance(value: Double): String = format(getBalancePayload(value))


    /**
     * Formats the amount real number to a string representation.
     *
     * @param value The value to format
     *
     * @return The amount in a string representation
     */
    fun formatAmount(value: Double): String = format(getAmountPayload(value))


    /**
     * Formats the orderbook's price real number to a string representation.
     *
     * @param value The value to format
     * @param maxLength The maximum length of a string representation
     *
     * @return The price in a string representation
     */
    fun formatOrderbookPrice(value: Double, maxLength: Int) = format(getAdvancedFixedPricePayload(value, maxLength))


    /**
     * Formats the orderbook's amount real number to a string representation.
     *
     * @param value The value to format
     * @param maxLength The maximum length of a string representation
     *
     * @return The amount in a string representation
     */
    fun formatOrderbookAmount(value: Double, maxLength: Int) = format(getAdvancedAmountPayload(value, maxLength))


    /**
     * Formats the trade's price real number to a string representation.
     *
     * @param value The value to format
     * @param maxLength The maximum length of a string representation
     *
     * @return The price in a string representation
     */
    fun formatTradePrice(value: Double, maxLength: Int) = format(getAdvancedFixedPricePayload(value, maxLength))


    /**
     * Formats the trade's amount real number to a string representation.
     *
     * @param value The value to format
     * @param maxLength maximum length of a string representation
     *
     * @return The amount in a string representation
     */
    fun formatTradeAmount(value: Double, maxLength: Int) = format(getAdvancedAmountPayload(value, maxLength))


    /**
     * Formats the percent spread real number to a string representation
     *
     * @param value The value to format
     *
     * @return The percent spread in a string representation
     */
    fun formatPercentSpread(value: Double) = format(getPercentPayload(value))


    /**
     * Parses a string to a double.
     *
     * @param source The string to parse
     * @param default The default value to return if parsing could not be done
     *
     * @return The parsed double or the default value
     */
    fun parse(source: String, default: Double = 0.0): Double {
        return try {
            with(mFormatter) {
                applyPattern(getParsingPattern(source))
                parse(source)
            }.toDouble()
        } catch(exception: ParseException) {
            default
        } catch(exception: IllegalArgumentException) {
            default
        }
    }


    /**
     * Sets the formatter's decimal separator.
     *
     * @param separator The new decimal separator
     */
    fun setDecimalSeparator(separator: Char) {
        val symbols = getDecimalFormatSymbols()
        symbols.decimalSeparator = separator
        setDecimalFormatSymbols(symbols)
    }


    /**
     * Gets the formatter's decimal separator.
     *
     * @return The decimal separator
     */
    fun getDecimalSeparator(): Char {
        return getDecimalFormatSymbols().decimalSeparator
    }


    /**
     * Sets the formatter's grouping separator.
     *
     * @param separator The new grouping separator
     */
    fun setGroupingSeparator(separator: Char) {
        val symbols = mFormatter.decimalFormatSymbols
        symbols.groupingSeparator = separator
        setDecimalFormatSymbols(symbols)
    }


    /**
     * Get's the formatter's grouping separator.
     *
     * @return The grouping separator
     */
    fun getGroupingSeparator(): Char {
        return getDecimalFormatSymbols().groupingSeparator
    }


    /**
     * Sets whether the grouping is used when formatting.
     *
     * @param isGroupingEnabled Whether to use grouping or not
     */
    fun setGroupingEnabled(isGroupingEnabled: Boolean) {
        this.mIsGroupingEnabled = isGroupingEnabled
    }


    /**
     * Checks whether the grouping is used when formatting.
     *
     * @return true if used; false otherwise
     */
    fun isGroupingEnabled(): Boolean {
        return mIsGroupingEnabled
    }


    private fun setDecimalFormatSymbols(symbols: DecimalFormatSymbols) {
        mFormatter.decimalFormatSymbols = symbols
    }


    private fun getDecimalFormatSymbols(): DecimalFormatSymbols {
        return mFormatter.decimalFormatSymbols
    }


    private fun getVolumePayload(value: Double): Payload {
        return when {
            (value < TEN) -> getAdvancedVolumePayload(value, 1)
            (value < ONE_HUNDRED) -> getAdvancedVolumePayload(value, 2)
            (value < ONE_THOUSAND) -> getAdvancedVolumePayload(value, 3)
            (value < TEN_THOUSAND) -> getAdvancedVolumePayload(value, 4)
            (value < ONE_HUNDRED_THOUSAND) -> getAdvancedVolumePayload(value, 5)
            (value < ONE_MILLION) -> getAdvancedVolumePayload(value, 6)
            (value < TEN_MILLION) -> getAdvancedVolumePayload(value, 7)
            (value < ONE_HUNDRED_MILLION) -> getAdvancedVolumePayload(value, 8)
            (value < ONE_BILLION) -> getAdvancedVolumePayload(value, 9)

            else -> getAdvancedVolumePayload(value, 10)
        }
    }


    private fun getAdvancedVolumePayload(value: Double, numOfIntegerPartDigits: Int): Payload {
        return getAdvancedPayloadWithoutTrimming(
            value,
            DEFAULT_VOLUME_MAX_LENGTH,
            numOfIntegerPartDigits
        )
    }


    private fun getDailyVolumePayload(value: Double): Payload {
        return getAdvancedAmountPayload(value, DEFAULT_FIXED_PRICE_MAX_LENGTH)
    }


    private fun getFixedPricePayload(value: Double): Payload {
        return getAdvancedFixedPricePayload(value, DEFAULT_FIXED_PRICE_MAX_LENGTH)
    }


    private fun getLastPriceChangePayload(value: Double): Payload {
        val signChar: String
        val signNumber: Double

        when {
            (value > 0.0) -> {
                signChar = "+"
                signNumber = 1.0
            }

            (value == 0.0) -> {
                signChar = ""
                signNumber = 1.0
            }

            else -> {
                signChar = ""
                signNumber = -1.0
            }
        }

        val pricePayload = getFixedPricePayload(Math.abs(value))

        return Payload((signChar + pricePayload.pattern), (signNumber * pricePayload.value))
    }


    private fun getDailyPriceChangePayload(value: Double): Payload {
        val absValue = Math.abs(value)
        val payload = when {
            (absValue < TEN) -> getAdvancedDailyPriceChangePayload(value, 1)
            (absValue < ONE_HUNDRED) -> getAdvancedDailyPriceChangePayload(value, 2)
            (absValue < ONE_THOUSAND) -> getAdvancedDailyPriceChangePayload(value, 3)
            (absValue < TEN_THOUSAND) -> getAdvancedDailyPriceChangePayload(value, 4)
            (absValue < ONE_HUNDRED_THOUSAND) -> getAdvancedDailyPriceChangePayload(value, 5)
            (absValue < ONE_MILLION) -> getAdvancedDailyPriceChangePayload(value, 6)
            (absValue < TEN_MILLION) -> getAdvancedDailyPriceChangePayload(value, 7)
            (absValue < ONE_HUNDRED_MILLION) -> getAdvancedDailyPriceChangePayload(value, 8)
            (absValue < ONE_BILLION) -> getAdvancedDailyPriceChangePayload(value, 9)

            else -> getAdvancedDailyPriceChangePayload(value, 10)
        }
        val sign = when {
            value > 0.0 -> "+"
            else -> ""
        }

        return Payload("$sign${payload.pattern}'%'", payload.value)
    }


    private fun getAdvancedDailyPriceChangePayload(value: Double, numOfIntegerPartDigits: Int): Payload {
        return getAdvancedPayloadWithoutTrimming(
            value,
            DEFAULT_DAILY_PRICE_CHANGE_MAX_LENGTH,
            numOfIntegerPartDigits
        )
    }


    private fun getPricePayload(value: Double): Payload {
        return when {
            (value < TEN) -> Payload(getFormattingPatternWithTrimming(value, 1, 8), value)
            (value < ONE_HUNDRED) -> Payload(getFormattingPatternWithTrimming(value, 2, 7), value)
            (value < ONE_THOUSAND) -> Payload(getFormattingPatternWithTrimming(value, 3, 6), value)
            (value < TEN_THOUSAND) -> Payload(getFormattingPatternWithTrimming(value, 4, 5), value)
            (value < ONE_HUNDRED_THOUSAND) -> Payload(getFormattingPatternWithTrimming(value, 5, 4), value)
            (value < ONE_MILLION) -> Payload(getFormattingPatternWithTrimming(value, 6, 3), value)
            (value < TEN_MILLION) -> Payload(getFormattingPatternWithTrimming(value, 7, 2), value)

            else -> Payload(getFormattingPatternWithTrimming(value, 8, 1), value)
        }
    }


    private fun getPercentPayload(value: Double): Payload {
        return when {
            (value < TEN) -> Payload("0.00'%'", value)
            (value < ONE_HUNDRED) -> Payload("00.00'%'", value)
            (value < ONE_THOUSAND) -> Payload("000.0'%'", value)

            else -> Payload("0000'%'", value)
        }
    }


    private fun getBalancePayload(value: Double): Payload {
        return if(value <= 0.0) {
            Payload("0.00000000", value)
        } else {
            getAmountPayload(value)
        }
    }


    private fun getAmountPayload(value: Double): Payload {
        if(value <= 0.0) {
            return Payload(getFormattingPatternWithTrimming(value, 1, 8), value)
        }

        return when {
            (value < ONE_HUNDRED_MILLIONTH) -> Payload("0.000E00", value)
            (value < TEN) -> Payload(getFormattingPatternWithTrimming(value, 1, 8), value)
            (value < ONE_HUNDRED) -> Payload(getFormattingPatternWithTrimming(value, 2, 7), value)
            (value < ONE_THOUSAND) -> Payload(getFormattingPatternWithTrimming(value, 3, 6), value)
            (value < TEN_THOUSAND) -> Payload(getFormattingPatternWithTrimming(value, 4, 5), value)
            (value < ONE_HUNDRED_THOUSAND) -> Payload(getFormattingPatternWithTrimming(value, 5, 4), value)
            (value < ONE_MILLION) -> Payload(getFormattingPatternWithTrimming(value, 6, 3), value)
            (value < TEN_MILLION) -> Payload(getFormattingPatternWithTrimming(value, 7, 2), value)
            (value < ONE_HUNDRED_MILLION) -> Payload(getFormattingPatternWithTrimming(value, 8, 1), value)
            (value < ONE_BILLION) -> Payload(getFormattingPatternWithTrimming(value, 9, 0), value)
            (value < TEN_BILLION) -> Payload(getFormattingPatternWithTrimming(value, 10, 0), value)
            (value < ONE_HUNDRED_BILLION) -> Payload(getFormattingPatternWithTrimming(value, 11, 0), value)
            (value < ONE_TRILLION) -> Payload(getFormattingPatternWithTrimming(value, 12, 0), value)

            else -> Payload("0.000E+00", value)
        }
    }


    private fun getAdvancedFixedPricePayload(value: Double, maxLength: Int): Payload {
        return when {
            (value < TEN) -> getAdvancedPayloadWithoutTrimming(value, maxLength, 1)
            (value < ONE_HUNDRED) -> getAdvancedPayloadWithoutTrimming(value, maxLength, 2)
            (value < ONE_THOUSAND) -> getAdvancedPayloadWithoutTrimming(value, maxLength, 3)
            (value < TEN_THOUSAND) -> getAdvancedPayloadWithoutTrimming(value, maxLength, 4)
            (value < ONE_HUNDRED_THOUSAND) -> getAdvancedPayloadWithoutTrimming(value, maxLength, 5)
            (value < ONE_MILLION) -> getAdvancedPayloadWithoutTrimming(value, maxLength, 6)
            (value < TEN_MILLION) -> getAdvancedPayloadWithoutTrimming(value, maxLength, 7)
            (value < ONE_HUNDRED_MILLION) -> getAdvancedPayloadWithoutTrimming(value, maxLength, 8)
            (value < ONE_BILLION) -> getAdvancedPayloadWithoutTrimming(value, maxLength, 9)
            (value < TEN_BILLION) -> getAdvancedPayloadWithoutTrimming(value, maxLength, 10)
            (value < ONE_HUNDRED_BILLION) -> getAdvancedPayloadWithoutTrimming(value, maxLength, 11)
            (value < ONE_TRILLION) -> getAdvancedPayloadWithoutTrimming(value, maxLength, 12)

            else -> getAdvancedPayloadWithoutTrimming(value, maxLength, 13)
        }
    }


    private fun getAdvancedAmountPayload(value: Double, maxLength: Int): Payload {
        return when {
            (value < TEN) -> getAdvancedPayloadWithTrimming(value, maxLength, 1)
            (value < ONE_HUNDRED) -> getAdvancedPayloadWithTrimming(value, maxLength, 2)
            (value < ONE_THOUSAND) -> getAdvancedPayloadWithTrimming(value, maxLength, 3)
            (value < TEN_THOUSAND) -> getAdvancedPayloadWithTrimming(value, maxLength, 4)
            (value < ONE_HUNDRED_THOUSAND) -> getAdvancedPayloadWithTrimming(value, maxLength, 5)
            (value < ONE_MILLION) -> getAdvancedPayloadWithTrimming(value, maxLength, 6)
            (value < TEN_MILLION) -> getAdvancedPayloadWithTrimming(value, maxLength, 7)
            (value < ONE_HUNDRED_MILLION) -> getAdvancedPayloadWithTrimming(value, maxLength, 8)
            (value < ONE_BILLION) -> getAdvancedPayloadWithTrimming(value, maxLength, 9)
            (value < TEN_BILLION) -> getAdvancedPayloadWithTrimming(value, maxLength, 10)
            (value < ONE_HUNDRED_BILLION) -> getAdvancedPayloadWithTrimming(value, maxLength, 11)
            (value < ONE_TRILLION) -> getAdvancedPayloadWithTrimming(value, maxLength, 12)

            else -> getAdvancedPayloadWithTrimming(value, maxLength, 13)
        }
    }


    private fun getAdvancedPayloadWithTrimming(value: Double, maxLength: Int,
                                               numOfIntegerPartDigits: Int): Payload {
        return getAdvancedPayload(value, maxLength, numOfIntegerPartDigits, true)
    }


    private fun getAdvancedPayloadWithoutTrimming(value: Double, maxLength: Int,
                                                  numOfIntegerPartDigits: Int): Payload {
        return getAdvancedPayload(value, maxLength, numOfIntegerPartDigits, false)
    }


    private fun getAdvancedPayload(value: Double, maxLength: Int,
                                   numOfIntegerPartDigits: Int, trimTrailingZeros: Boolean,
                                   abbreviation: String = ""): Payload {
        val integerPart = getIntegerPart(numOfIntegerPartDigits)

        if(integerPart.length > maxLength) {
            return getAdvancedLargePayload(value, maxLength, trimTrailingZeros)
        }

        // Calculating the maximum number of fractional part digits by subtracting
        // from the maximum length the length of integer part, the length of prefix multiplied by
        // 2 (2 was added cause abbreviations tend to take width of up to 2 digits), and 1
        // (fractional part delimiter)
        val maxNumOfFractionalPartDigits = (maxLength - integerPart.length - (abbreviation.length * 2) - 1)
        val defaultPrecision = if(trimTrailingZeros) 0 else maxNumOfFractionalPartDigits
        val fractionalPart = getFractionalPart(
            value,
            maxNumOfFractionalPartDigits,
            defaultPrecision,
            trimTrailingZeros
        )

        return Payload((integerPart + fractionalPart + abbreviation), value)
    }


    private fun getAdvancedLargePayload(value: Double, maxLength: Int, trimTrailingZeros: Boolean): Payload {
        return when {
            (value < ONE_MILLION) -> getAdvancedPayload((value / ONE_THOUSAND), maxLength, 3, trimTrailingZeros, "K")
            (value < TEN_MILLION) -> getAdvancedPayload((value / ONE_MILLION), maxLength, 1, trimTrailingZeros, "M")
            (value < ONE_HUNDRED_MILLION) -> getAdvancedPayload((value / ONE_MILLION), maxLength, 2, trimTrailingZeros, "M")
            (value < ONE_BILLION) -> getAdvancedPayload((value / ONE_MILLION), maxLength, 3, trimTrailingZeros, "M")
            (value < TEN_BILLION) -> getAdvancedPayload((value / ONE_BILLION), maxLength, 1, trimTrailingZeros, "B")
            (value < ONE_HUNDRED_BILLION) -> getAdvancedPayload((value / ONE_BILLION), maxLength, 2, trimTrailingZeros, "B")
            (value < ONE_TRILLION) -> getAdvancedPayload((value / ONE_BILLION), maxLength, 3, trimTrailingZeros, "B")

            else -> Payload("0.000E+00", value)
        }
    }


    private fun getFormattingPatternWithTrimming(value: Double, numOfIntegerPartDigits: Int,
                                                 maxNumOfFractionalPartDigits: Int): String {
        return getFormattingPattern(
            value,
            numOfIntegerPartDigits,
            maxNumOfFractionalPartDigits,
            true
        )
    }


    private fun getFormattingPatternWithoutTrimming(value: Double, numOfIntegerPartDigits: Int,
                                                    maxNumOfFractionalPartDigits: Int): String {
        return getFormattingPattern(
            value,
            numOfIntegerPartDigits,
            maxNumOfFractionalPartDigits,
            false
        )
    }


    private fun getFormattingPattern(value: Double, numOfIntegerPartDigits: Int,
                                     maxNumOfFractionalPartDigits: Int, trimTrailingZeros: Boolean): String {
        val integerPart = getIntegerPart(numOfIntegerPartDigits)
        val fractionalPart = getFractionalPart(value, maxNumOfFractionalPartDigits, 0, trimTrailingZeros)

        return (integerPart + fractionalPart)
    }


    private fun getIntegerPart(numOfDigits: Int): String {
        val integerPart = StringBuilder()

        for(index in 1..numOfDigits) {
            if(mIsGroupingEnabled && (index > 1) && (((index - 1) % DEFAULT_GROUPING_SIZE) == 0)) {
                integerPart.append(",")
            }

            integerPart.append("0")
        }

        return integerPart.reverse().toString()
    }


    private fun getFractionalPart(value: Double, maxPrecision: Int,
                                  defaultPrecision: Int, trimTrailingZeros: Boolean): String {
        return value.getFractionalPartPrecision(maxPrecision, defaultPrecision, trimTrailingZeros)
            .takeIf { it > 0 }
            ?.let { ".${"0".repeat(it)}" }
            ?: ""
    }


    private fun getParsingPattern(source: String): String {
        val pattern = StringBuilder()
        val groupingSeparator = getGroupingSeparator()
        val decimalSeparator = getDecimalSeparator()

        source.forEachIndexed { _, char ->
            pattern.append(when(char) {
                groupingSeparator -> FORMATTER_GROUPING_SYMBOL
                decimalSeparator -> FORMATTER_DECIMAL_SYMBOL

                else -> FORMATTER_DIGIT_SYMBOL
            })
        }

        return pattern.toString().trim(FORMATTER_GROUPING_SYMBOL, FORMATTER_DECIMAL_SYMBOL)
    }


    /**
     * A helper class used for holding the payload data of the formatting.
     */
    data class Payload(
        val pattern: String,
        val value: Double
    )


}
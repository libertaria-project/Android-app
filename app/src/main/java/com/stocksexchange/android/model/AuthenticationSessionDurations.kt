package com.stocksexchange.android.model

/**
 * An enumeration of all possible authentication session durations.
 */
enum class AuthenticationSessionDurations(val timeInMillis: Long) {

    FIFTEEN_SECONDS(15L * 1000L),   // For debugging purposes only


    FIVE_MINUTES(5L * 60L * 1000L),
    TEN_MINUTES(10L * 60L * 1000L),
    FIFTEEN_MINUTES(15L * 60L * 1000L),
    THIRTY_MINUTES(30L * 60L * 1000L),
    FORTY_FIVE_MINUTES(45L * 60L * 1000L),
    ONE_HOUR(1L * 60L * 60L * 1000L),
    TWO_HOURS(2L * 60L * 60L * 1000L),
    THREE_HOURS(3L * 60L * 60L * 1000L),
    SIX_HOURS(6L * 60L * 60L * 1000L),
    NINE_HOURS(9L * 60L * 60L * 1000L),
    TWELVE_HOURS(12L * 60L * 60L * 1000L)

}
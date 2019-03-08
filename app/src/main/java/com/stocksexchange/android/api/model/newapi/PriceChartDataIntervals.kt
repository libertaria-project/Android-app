package com.stocksexchange.android.api.model.newapi

/**
 * An enumeration of all possible intervals of price chart data.
 */
enum class PriceChartDataIntervals(
    val interval: String,
    val milliseconds: Long
) {

    ONE_MINUTE("1", 1L * 60L * 1000L),
    FIVE_MINUTES("5", 5L * 60L * 1000L),
    THIRTY_MINUTES("30", 30L * 60L * 1000L),
    SIXTY_MINUTES("60", 60L * 60L * 1000L),
    FOUR_HOURS("240", 4L * 60L * 60L * 1000L),
    TWELVE_HOURS("720", 12L * 60L * 60L * 1000L),
    ONE_DAY("1D", 24L * 60L * 60L * 1000L)

}
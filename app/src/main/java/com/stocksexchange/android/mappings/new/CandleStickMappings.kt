package com.stocksexchange.android.mappings.new

import com.stocksexchange.android.api.model.newapi.CandleStick
import com.stocksexchange.android.api.model.newapi.PriceChartDataParameters
import com.stocksexchange.android.database.model.newd.DatabaseCandleStick

fun CandleStick.mapToDatabaseCandleStick(
    parameters: PriceChartDataParameters
): DatabaseCandleStick {
    return DatabaseCandleStick(
        currencyPairId = parameters.currencyPairId,
        interval = parameters.interval.interval,
        openPrice = openPrice,
        highPrice = highPrice,
        lowPrice = lowPrice,
        closePrice = closePrice,
        volume = volume,
        timestamp = timestamp
    )
}


fun List<CandleStick>.mapToDatabaseCandleStickList(
    parameters: PriceChartDataParameters
): List<DatabaseCandleStick> {
    return map { it.mapToDatabaseCandleStick(parameters) }
}


fun DatabaseCandleStick.mapToCandleStick(): CandleStick {
    return CandleStick(
        openPrice = openPrice,
        highPrice = highPrice,
        lowPrice = lowPrice,
        closePrice = closePrice,
        volume = volume,
        timestamp = timestamp
    )
}


fun List<DatabaseCandleStick>.mapToCandleStickList(): List<CandleStick> {
    return map { it.mapToCandleStick() }
}
package com.stocksexchange.android.mappings

import com.stocksexchange.android.api.model.PriceChartData
import com.stocksexchange.android.database.model.DatabasePriceChartData

fun PriceChartData.mapToDatabasePriceChartData(): DatabasePriceChartData {
    return DatabasePriceChartData(
        marketName = marketName,
        interval = interval,
        order = order,
        startDate = startDate,
        endDate = endDate,
        count = count,
        pagesCount = pagesCount,
        currentPage = currentPage,
        candleSticks = candleSticks
    )
}


fun DatabasePriceChartData.mapToPriceChartData(): PriceChartData {
    return PriceChartData(
        marketName = marketName,
        interval = interval,
        order = order,
        startDate = startDate,
        endDate = endDate,
        count = count,
        pagesCount = pagesCount,
        currentPage = currentPage,
        candleSticks = candleSticks
    )
}
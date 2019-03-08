package com.stocksexchange.android.mappings.new

import com.stocksexchange.android.api.model.newapi.TickerItem
import com.stocksexchange.android.database.model.newd.DatabaseTickerItem

fun TickerItem.mapToDatabaseTickerItem(): DatabaseTickerItem {
    return DatabaseTickerItem(
        id = id,
        baseCurrencyCode = baseCurrencyCode,
        baseCurrencyName = baseCurrencyName,
        quoteCurrencyCode = quoteCurrencyCode,
        quoteCurrencyName = quoteCurrencyName,
        symbol = symbol,
        askPrice = askPrice,
        bidPrice = bidPrice,
        lastPrice = lastPrice,
        openPrice = openPrice,
        lowPrice = lowPrice,
        highPrice = highPrice,
        volume = volume,
        volumeQuote = volumeQuote,
        timestamp = timestamp
    )
}


fun List<TickerItem>.mapToDatabaseTickerItemList(): List<DatabaseTickerItem> {
    return map { it.mapToDatabaseTickerItem() }
}


fun DatabaseTickerItem.mapToTickerItem(): TickerItem {
    return TickerItem(
        id = id,
        baseCurrencyCode = baseCurrencyCode,
        baseCurrencyName = baseCurrencyName,
        quoteCurrencyCode = quoteCurrencyCode,
        quoteCurrencyName = quoteCurrencyName,
        symbol = symbol,
        askPrice = askPrice,
        bidPrice = bidPrice,
        lastPrice = lastPrice,
        openPrice = openPrice,
        lowPrice = lowPrice,
        highPrice = highPrice,
        volume = volume,
        volumeQuote = volumeQuote,
        timestamp = timestamp
    )
}


fun List<DatabaseTickerItem>.mapToTickerItemList(): List<TickerItem> {
    return map { it.mapToTickerItem() }
}
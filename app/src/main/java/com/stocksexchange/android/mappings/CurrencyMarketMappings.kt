package com.stocksexchange.android.mappings

import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.database.model.DatabaseCurrencyMarket
import com.stocksexchange.android.ui.currencymarkets.fragment.CurrencyMarketItem

fun CurrencyMarket.mapToDatabaseCurrencyMarket(): DatabaseCurrencyMarket {
    return DatabaseCurrencyMarket(
        id = id,
        name = name,
        baseCurrencyName = baseCurrencyName,
        quoteCurrencyName = quoteCurrencyName,
        baseCurrencySymbol = baseCurrencySymbol,
        quoteCurrencySymbol = quoteCurrencySymbol,
        minOrderAmount = minOrderAmount,
        dailyMinPrice = dailyMinPrice,
        dailyMaxPrice = dailyMaxPrice,
        lastPrice = lastPrice,
        lastPriceDayAgo = lastPriceDayAgo,
        dailyPriceChange = dailyPriceChange,
        dailyVolume = dailyVolume,
        volume = volume,
        buyFeePercent = buyFeePercent,
        sellFeePercent = sellFeePercent,
        isActive = isActive
    )
}


fun CurrencyMarket.mapToCurrencyMarketItem(): CurrencyMarketItem {
    return CurrencyMarketItem(this)
}


fun List<CurrencyMarket>.mapToDatabaseCurrencyMarketList(): List<DatabaseCurrencyMarket> {
    return map { it.mapToDatabaseCurrencyMarket() }
}


fun List<CurrencyMarket>.mapToCurrencyMarketItemList(): List<CurrencyMarketItem> {
    return map { it.mapToCurrencyMarketItem() }
}


fun List<CurrencyMarket>.mapToNameMarketMap(): Map<String, CurrencyMarket> {
    val map: MutableMap<String, CurrencyMarket> = mutableMapOf()

    for(currencyMarket in this) {
        map[currencyMarket.name] = currencyMarket
    }

    return map
}


fun DatabaseCurrencyMarket.mapToCurrencyMarket(): CurrencyMarket {
    return CurrencyMarket(
        id = id,
        name = name,
        baseCurrencyName = baseCurrencyName,
        quoteCurrencyName = quoteCurrencyName,
        minOrderAmount = minOrderAmount,
        dailyMinPrice = dailyMinPrice,
        dailyMaxPrice = dailyMaxPrice,
        lastPrice = lastPrice,
        lastPriceDayAgo = lastPriceDayAgo,
        dailyPriceChange = dailyPriceChange,
        dailyVolume = dailyVolume,
        volume = volume,
        buyFeePercent = buyFeePercent,
        sellFeePercent = sellFeePercent,
        isActive = isActive
    )
}


fun List<DatabaseCurrencyMarket>.mapToCurrencyMarketList(): List<CurrencyMarket> {
    return map { it.mapToCurrencyMarket() }
}
package com.stocksexchange.android.mappings.new

import com.stocksexchange.android.api.model.newapi.CurrencyPair
import com.stocksexchange.android.database.model.newd.DatabaseCurrencyPair

fun CurrencyPair.mapToDatabaseCurrencyPair(): DatabaseCurrencyPair {
    return DatabaseCurrencyPair(
        id = id,
        baseCurrencyId = baseCurrencyId,
        baseCurrencyCode = baseCurrencyCode,
        baseCurrencyName = baseCurrencyName,
        quoteCurrencyId = quoteCurrencyId,
        quoteCurrencyCode = quoteCurrencyCode,
        quoteCurrencyName = quoteCurrencyName,
        symbol = symbol,
        groupName = groupName,
        groupId = groupId,
        minOrderAmount = minOrderAmount,
        minBuyPrice = minBuyPrice,
        minSellPrice = minSellPrice,
        buyFeeInPercentage = buyFeeInPercentage,
        sellFeeInPercentage = sellFeeInPercentage,
        isActive = isActive,
        isDelisted = isDelisted,
        message = message,
        baseCurrencyPrecision = baseCurrencyPrecision,
        quoteCurrencyPrecision = quoteCurrencyPrecision
    )
}


fun List<CurrencyPair>.mapToDatabaseCurrencyPairList(): List<DatabaseCurrencyPair> {
    return map { it.mapToDatabaseCurrencyPair() }
}


fun DatabaseCurrencyPair.mapToCurrencyPair(): CurrencyPair {
    return CurrencyPair(
        id = id,
        baseCurrencyId = baseCurrencyId,
        baseCurrencyCode = baseCurrencyCode,
        baseCurrencyName = baseCurrencyName,
        quoteCurrencyId = quoteCurrencyId,
        quoteCurrencyCode = quoteCurrencyCode,
        quoteCurrencyName = quoteCurrencyName,
        symbol = symbol,
        groupName = groupName,
        groupId = groupId,
        minOrderAmount = minOrderAmount,
        minBuyPrice = minBuyPrice,
        minSellPrice = minSellPrice,
        buyFeeInPercentage = buyFeeInPercentage,
        sellFeeInPercentage = sellFeeInPercentage,
        isActive = isActive,
        isDelisted = isDelisted,
        message = message,
        baseCurrencyPrecision = baseCurrencyPrecision,
        quoteCurrencyPrecision = quoteCurrencyPrecision
    )
}


fun List<DatabaseCurrencyPair>.mapToCurrencyPairList(): List<CurrencyPair> {
    return map { it.mapToCurrencyPair() }
}
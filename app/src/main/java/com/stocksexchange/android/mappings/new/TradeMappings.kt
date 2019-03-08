package com.stocksexchange.android.mappings.new

import com.stocksexchange.android.api.model.newapi.Trade
import com.stocksexchange.android.api.model.newapi.TradeParameters
import com.stocksexchange.android.database.model.newd.DatabaseTrade

fun Trade.mapToDatabaseTrade(
    parameters: TradeParameters
): DatabaseTrade {
    return DatabaseTrade(
        id = id,
        currencyPairId = parameters.currencyPairId,
        price = price,
        amount = amount,
        typeStr = typeStr,
        timestamp = timestamp
    )
}


fun List<Trade>.mapToDatabaseTradeList(
    parameters: TradeParameters
): List<DatabaseTrade> {
    return map { it.mapToDatabaseTrade(parameters) }
}


fun DatabaseTrade.mapToTrade(): Trade {
    return Trade(
        id = id,
        price = price,
        amount = amount,
        typeStr = typeStr,
        timestamp = timestamp
    )
}


fun List<DatabaseTrade>.mapToTradeList(): List<Trade> {
    return map { it.mapToTrade() }
}
package com.stocksexchange.android.mappings

import com.stocksexchange.android.api.model.Trade
import com.stocksexchange.android.database.model.DatabaseTrade
import com.stocksexchange.android.model.DataActionItem

fun Trade.mapToDatabaseTrade(marketName: String): DatabaseTrade {
    return DatabaseTrade(
        id = id,
        marketName = marketName,
        timestamp = timestamp,
        price = price,
        amount = amount,
        type = type
    )
}


fun List<Trade>.mapToDatabaseTradeList(marketName: String): List<DatabaseTrade> {
    return map { it.mapToDatabaseTrade(marketName) }
}


fun DatabaseTrade.mapToTrade(): Trade {
    return Trade(
        id = id,
        timestamp = timestamp,
        price = price,
        amount = amount,
        type = type
    )
}


fun List<DatabaseTrade>.mapToTradeList(): List<Trade> {
    return map { it.mapToTrade() }
}


fun List<DataActionItem<Trade>>.mapToIdTradeActionItemsMap(): Map<Long, DataActionItem<Trade>> {
    val map: MutableMap<Long, DataActionItem<Trade>> = mutableMapOf()

    for(item in this) {
        map[item.dataItem.id] = item
    }

    return map
}
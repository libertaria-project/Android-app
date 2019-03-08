package com.stocksexchange.android.mappings

import com.stocksexchange.android.api.model.OrderbookOrder
import com.stocksexchange.android.model.DataActionItem

fun List<DataActionItem<OrderbookOrder>>.mapToPriceOrderbookOrderActionItemsMap()
    : Map<Double, DataActionItem<OrderbookOrder>> {
    val map: MutableMap<Double, DataActionItem<OrderbookOrder>> = mutableMapOf()

    for(item in this) {
        map[item.dataItem.price] = item
    }

    return map
}
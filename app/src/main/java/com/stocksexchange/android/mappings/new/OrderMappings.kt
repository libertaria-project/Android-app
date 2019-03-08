package com.stocksexchange.android.mappings.new

import com.stocksexchange.android.api.model.newapi.Order
import com.stocksexchange.android.database.model.newd.DatabaseOrder

fun Order.mapToDatabaseOrder(): DatabaseOrder {
    return DatabaseOrder(
        id = id,
        currencyPairId = currencyPairId,
        price = price,
        triggerPrice = triggerPrice,
        initialAmount = initialAmount,
        processedAmount = processedAmount,
        typeStr = typeStr,
        originalTypeStr = originalTypeStr,
        timestamp = timestamp,
        statusStr = statusStr,
        trades = trades,
        fees = fees
    )
}


fun List<Order>.mapToDatabaseOrderList(): List<DatabaseOrder> {
    return map { it.mapToDatabaseOrder() }
}


fun DatabaseOrder.mapToOrder(): Order {
    return Order(
        id = id,
        currencyPairId = currencyPairId,
        price = price,
        triggerPrice = triggerPrice,
        initialAmount = initialAmount,
        processedAmount = processedAmount,
        typeStr = typeStr,
        originalTypeStr = originalTypeStr,
        timestamp = timestamp,
        statusStr = statusStr,
        trades = trades,
        fees = fees
    )
}


fun List<DatabaseOrder>.mapToOrderList(): List<Order> {
    return map { it.mapToOrder() }
}
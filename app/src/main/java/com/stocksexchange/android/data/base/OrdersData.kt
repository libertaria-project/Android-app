package com.stocksexchange.android.data.base

import com.stocksexchange.android.api.model.newapi.Order
import com.stocksexchange.android.api.model.newapi.OrderCreationParameters
import com.stocksexchange.android.api.model.newapi.OrderParameters

interface OrdersData<
    OrderCreationResult,
    OrderCancellationResult,
    OrdersFetchingResult
> {

    suspend fun save(order: Order)

    suspend fun save(orders: List<Order>)

    suspend fun create(params: OrderCreationParameters): OrderCreationResult

    suspend fun cancel(order: Order): OrderCancellationResult

    suspend fun delete(order: Order)

    suspend fun delete(status: String)

    suspend fun deleteAll()

    suspend fun getActiveOrders(params: OrderParameters): OrdersFetchingResult

    suspend fun getHistoryOrders(params: OrderParameters): OrdersFetchingResult

}
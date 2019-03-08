package com.stocksexchange.android.datastores.orders

import com.stocksexchange.android.api.model.Order
import com.stocksexchange.android.api.model.OrderCreationParameters
import com.stocksexchange.android.api.model.OrderParameters
import com.stocksexchange.android.api.model.OrderResponse
import com.stocksexchange.android.model.Result

interface OrdersDataStore {

    suspend fun save(order: Order)

    suspend fun save(orders: List<Order>)

    suspend fun delete(order: Order)

    suspend fun delete(type: String)

    suspend fun deleteAll()

    suspend fun create(params: OrderCreationParameters): Result<OrderResponse>

    suspend fun cancel(orderId: Long): Result<OrderResponse>

    suspend fun search(params: OrderParameters): Result<List<Order>>

    suspend fun getActiveOrders(params: OrderParameters): Result<List<Order>>

    suspend fun getCompletedOrders(params: OrderParameters): Result<List<Order>>

    suspend fun getCancelledOrders(params: OrderParameters): Result<List<Order>>

}
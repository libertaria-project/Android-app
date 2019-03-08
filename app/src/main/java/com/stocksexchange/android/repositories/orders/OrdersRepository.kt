package com.stocksexchange.android.repositories.orders

import com.stocksexchange.android.api.model.*
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.repositories.base.repository.Repository

interface OrdersRepository : Repository {

    fun refresh(params: OrderParameters)

    suspend fun save(params: OrderParameters, order: Order)

    suspend fun save(params: OrderParameters, orders: List<Order>)

    suspend fun delete(params: OrderParameters, order: Order)

    suspend fun delete(orderType: OrderTypes)

    suspend fun deleteAll()

    suspend fun create(params: OrderCreationParameters): RepositoryResult<OrderResponse>

    suspend fun cancel(order: Order, params: OrderParameters): RepositoryResult<OrderResponse>

    suspend fun search(params: OrderParameters): RepositoryResult<List<Order>>

    suspend fun getActiveOrders(params: OrderParameters): RepositoryResult<List<Order>>

    suspend fun getCompletedOrders(params: OrderParameters): RepositoryResult<List<Order>>

    suspend fun getCancelledOrders(params: OrderParameters): RepositoryResult<List<Order>>

}
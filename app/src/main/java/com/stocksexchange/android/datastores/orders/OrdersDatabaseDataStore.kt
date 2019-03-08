package com.stocksexchange.android.datastores.orders

import com.stocksexchange.android.api.model.Order
import com.stocksexchange.android.api.model.OrderCreationParameters
import com.stocksexchange.android.api.model.OrderParameters
import com.stocksexchange.android.api.model.OrderResponse
import com.stocksexchange.android.database.tables.OrdersTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation

class OrdersDatabaseDataStore(
    private val ordersTable: OrdersTable
) : OrdersDataStore {


    override suspend fun save(order: Order) {
        executeBackgroundOperation {
            ordersTable.save(order)
        }
    }


    override suspend fun save(orders: List<Order>) {
        executeBackgroundOperation {
            ordersTable.save(orders)
        }
    }


    override suspend fun delete(order: Order) {
        executeBackgroundOperation {
            ordersTable.delete(order)
        }
    }


    override suspend fun delete(type: String) {
        executeBackgroundOperation {
            ordersTable.delete(type)
        }
    }


    override suspend fun deleteAll() {
        executeBackgroundOperation {
            ordersTable.deleteAll()
        }
    }


    override suspend fun create(params: OrderCreationParameters): Result<OrderResponse> {
        throw UnsupportedOperationException()
    }


    override suspend fun cancel(orderId: Long): Result<OrderResponse> {
        throw UnsupportedOperationException()
    }


    override suspend fun search(params: OrderParameters): Result<List<Order>> {
        return performBackgroundOperation {
            ordersTable.search(params)
        }
    }


    override suspend fun getActiveOrders(params: OrderParameters): Result<List<Order>> {
        return getUserOrders(params)
    }


    override suspend fun getCompletedOrders(params: OrderParameters): Result<List<Order>> {
        return getUserOrders(params)
    }


    override suspend fun getCancelledOrders(params: OrderParameters): Result<List<Order>> {
        return getUserOrders(params)
    }


    private suspend fun getUserOrders(params: OrderParameters): Result<List<Order>> {
        return performBackgroundOperation {
            ordersTable.getUserOrders(params)
        }
    }


}
package com.stocksexchange.android.data.stores.orders

import com.stocksexchange.android.api.model.newapi.Order
import com.stocksexchange.android.api.model.newapi.OrderCreationParameters
import com.stocksexchange.android.api.model.newapi.OrderParameters
import com.stocksexchange.android.api.model.newapi.OrdersCancellationResponse
import com.stocksexchange.android.database.tables.new.OrdersTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

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


    override suspend fun create(params: OrderCreationParameters): Result<Order> {
        throw UnsupportedOperationException()
    }


    override suspend fun cancel(order: Order): Result<OrdersCancellationResponse> {
        throw UnsupportedOperationException()
    }


    override suspend fun delete(order: Order) {
        executeBackgroundOperation {
            ordersTable.delete(order)
        }
    }


    override suspend fun delete(status: String) {
        executeBackgroundOperation {
            ordersTable.delete(status)
        }
    }


    override suspend fun deleteAll() {
        executeBackgroundOperation {
            ordersTable.deleteAll()
        }
    }


    override suspend fun getActiveOrders(params: OrderParameters): Result<List<Order>> {
        return getOrders(params)
    }


    override suspend fun getHistoryOrders(params: OrderParameters): Result<List<Order>> {
        return getOrders(params)
    }


    private suspend fun getOrders(params: OrderParameters): Result<List<Order>> {
        return performBackgroundOperation {
            ordersTable.get(params)
        }
    }


}
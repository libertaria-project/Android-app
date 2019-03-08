package com.stocksexchange.android.datastores.orders

import com.stocksexchange.android.api.StocksExchangeApi
import com.stocksexchange.android.api.model.Order
import com.stocksexchange.android.api.model.OrderCreationParameters
import com.stocksexchange.android.api.model.OrderParameters
import com.stocksexchange.android.api.model.OrderResponse
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class OrdersServerDataStore(
    private val stocksExchangeApi: StocksExchangeApi
) : OrdersDataStore {


    override suspend fun save(order: Order) {
        throw UnsupportedOperationException()
    }


    override suspend fun save(orders: List<Order>) {
        throw UnsupportedOperationException()
    }


    override suspend fun delete(order: Order) {
        throw UnsupportedOperationException()
    }


    override suspend fun delete(type: String) {
        throw UnsupportedOperationException()
    }


    override suspend fun deleteAll() {
        throw UnsupportedOperationException()
    }


    override suspend fun create(params: OrderCreationParameters): Result<OrderResponse> {
        return performBackgroundOperation {
            stocksExchangeApi.createOrder(params)
        }
    }


    override suspend fun cancel(orderId: Long): Result<OrderResponse> {
        return performBackgroundOperation {
            stocksExchangeApi.cancelOrder(orderId)
        }
    }


    override suspend fun search(params: OrderParameters): Result<List<Order>> {
        throw UnsupportedOperationException()
    }


    override suspend fun getActiveOrders(params: OrderParameters): Result<List<Order>> {
        return performBackgroundOperation {
            stocksExchangeApi.getActiveOrders(params)
        }
    }


    override suspend fun getCompletedOrders(params: OrderParameters): Result<List<Order>> {
        return performBackgroundOperation {
            stocksExchangeApi.getHistoryOrders(params)
        }
    }


    override suspend fun getCancelledOrders(params: OrderParameters): Result<List<Order>> {
        return performBackgroundOperation {
            stocksExchangeApi.getHistoryOrders(params)
        }
    }


}
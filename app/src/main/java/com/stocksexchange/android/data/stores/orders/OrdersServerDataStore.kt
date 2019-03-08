package com.stocksexchange.android.data.stores.orders

import com.stocksexchange.android.api.StexApi
import com.stocksexchange.android.api.model.newapi.Order
import com.stocksexchange.android.api.model.newapi.OrderCreationParameters
import com.stocksexchange.android.api.model.newapi.OrderParameters
import com.stocksexchange.android.api.model.newapi.OrdersCancellationResponse
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class OrdersServerDataStore(
    private val stexApi: StexApi
) : OrdersDataStore {


    override suspend fun save(order: Order) {
        throw UnsupportedOperationException()
    }


    override suspend fun save(orders: List<Order>) {
        throw UnsupportedOperationException()
    }


    override suspend fun create(params: OrderCreationParameters): Result<Order> {
        return performBackgroundOperation {
            stexApi.createOrder(params)
        }
    }


    override suspend fun cancel(order: Order): Result<OrdersCancellationResponse> {
        return performBackgroundOperation {
            stexApi.cancelActiveOrder(order)
        }
    }


    override suspend fun delete(order: Order) {
        throw UnsupportedOperationException()
    }


    override suspend fun delete(status: String) {
        throw UnsupportedOperationException()
    }


    override suspend fun deleteAll() {
        throw UnsupportedOperationException()
    }


    override suspend fun getActiveOrders(params: OrderParameters): Result<List<Order>> {
        return performBackgroundOperation {
            stexApi.getAllActiveOrders()
        }
    }


    override suspend fun getHistoryOrders(params: OrderParameters): Result<List<Order>> {
        return performBackgroundOperation {
            stexApi.getHistoryOrders(params)
        }
    }


}
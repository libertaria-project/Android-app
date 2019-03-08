package com.stocksexchange.android.data.repositories.orders

import com.stocksexchange.android.api.model.newapi.*
import com.stocksexchange.android.data.stores.orders.OrdersDataStore
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.repositories.base.repository.BaseRepository
import com.stocksexchange.android.repositories.utils.freshdatahandlers.interfaces.FreshDataHandler
import com.stocksexchange.android.utils.exceptions.NoInternetException
import com.stocksexchange.android.utils.providers.ConnectionProvider

class OrdersRepositoryImpl(
    private val serverDataStore: OrdersDataStore,
    private val databaseDataStore: OrdersDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepository(), OrdersRepository {


    override val freshDataHandler: FreshDataHandler = OrdersFreshDataHandler()




    override fun refresh(params: OrderParameters) {
        freshDataHandler.refresh(params)
    }


    override suspend fun save(order: Order) {
        databaseDataStore.save(order)
    }


    override suspend fun save(orders: List<Order>) {
        databaseDataStore.save(orders)
    }


    override suspend fun create(params: OrderCreationParameters): RepositoryResult<Order> {
        return RepositoryResult(serverResult = if(connectionProvider.isNetworkAvailable()) {
            serverDataStore.create(params)
        } else {
            Result.Failure(NoInternetException())
        })
    }


    override suspend fun cancel(order: Order): RepositoryResult<OrdersCancellationResponse> {
        return RepositoryResult(serverResult = if(connectionProvider.isNetworkAvailable()) {
            val result = serverDataStore.cancel(order)

            if(result is Result.Success) {
                save(order.copy(statusStr = OrderStatuses.CANCELED.name))
            }

            result
        } else {
            Result.Failure(NoInternetException())
        })
    }


    override suspend fun delete(order: Order) {
        databaseDataStore.delete(order)
    }


    override suspend fun delete(status: String) {
        databaseDataStore.delete(status)
    }


    override suspend fun deleteAll() {
        databaseDataStore.deleteAll()
    }


    override suspend fun getActiveOrders(params: OrderParameters): RepositoryResult<List<Order>> {
        return getOrders(params) { getActiveOrders(params) }
    }


    override suspend fun getHistoryOrders(params: OrderParameters): RepositoryResult<List<Order>> {
        return getOrders(params) { getHistoryOrders(params) }
    }


    private suspend fun getOrders(
        params: OrderParameters,
        fetchOrders: suspend OrdersDataStore.(params: OrderParameters) -> Result<List<Order>>
    ): RepositoryResult<List<Order>> {
        val result = RepositoryResult<List<Order>>()

        if(freshDataHandler.shouldLoadFreshData(connectionProvider, params)) {
            result.serverResult = serverDataStore.fetchOrders(params)

            if(result.isServerResultSuccessful()) {
                save(result.getSuccessfulResultValue())
            }
        }

        if(result.isServerResultErroneous(true)) {
            result.databaseResult = databaseDataStore.fetchOrders(params)
        }

        return result
    }


}
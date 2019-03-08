package com.stocksexchange.android.repositories.orders

import com.stocksexchange.android.api.model.*
import com.stocksexchange.android.datastores.orders.OrdersDataStore
import com.stocksexchange.android.datastores.orders.OrdersDatabaseDataStore
import com.stocksexchange.android.utils.exceptions.NoInternetException
import com.stocksexchange.android.model.*
import com.stocksexchange.android.utils.providers.ConnectionProvider
import com.stocksexchange.android.repositories.base.repository.BaseRepositoryWithCache
import com.stocksexchange.android.utils.extensions.getIndexToInsertAt
import com.stocksexchange.android.utils.extensions.truncate
import com.stocksexchange.android.utils.helpers.addOrUpdateItemToCache
import com.stocksexchange.android.utils.helpers.handleRepositoryResult
import com.stocksexchange.android.utils.helpers.removeItemFromCache
import org.koin.standalone.inject

class OrdersRepositoryImpl(
    private val serverDataStore: OrdersDataStore,
    private val databaseDataStore: OrdersDatabaseDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepositoryWithCache<OrdersCache>(), OrdersRepository {


    override val cache: OrdersCache by inject()




    @Synchronized
    override fun refresh(params: OrderParameters) {
        when(params.type) {
            OrderTypes.ACTIVE -> cache.removeActiveOrders()
            OrderTypes.COMPLETED -> cache.removeCompletedOrders()
            OrderTypes.CANCELLED -> cache.removeCancelledOrders()
        }
    }


    @Synchronized
    override suspend fun save(params: OrderParameters, order: Order) {
        databaseDataStore.save(order)

        when(params.type) {
            OrderTypes.ACTIVE -> addOrUpdateActiveOrderToCache(params, order)
            OrderTypes.COMPLETED -> addOrUpdateCompletedOrderToCache(params, order)
            OrderTypes.CANCELLED -> addOrUpdateCancelledOrderToCache(params, order)
        }
    }


    @Synchronized
    override suspend fun save(params: OrderParameters, orders: List<Order>) {
        databaseDataStore.save(orders)

        when(params.type) {
            OrderTypes.ACTIVE -> saveActiveOrdersToCache(orders)
            OrderTypes.COMPLETED -> saveCompletedOrdersToCache(orders)
            OrderTypes.CANCELLED -> saveCancelledOrdersToCache(orders)
        }
    }


    @Synchronized
    override suspend fun delete(params: OrderParameters, order: Order) {
        databaseDataStore.delete(order)

        when(params.type) {
            OrderTypes.ACTIVE -> removeActiveOrderFromCache(order)
            OrderTypes.COMPLETED -> removeCompletedOrderFromCache(order)
            OrderTypes.CANCELLED -> removeCancelledOrderFromCache(order)
        }
    }


    @Synchronized
    override suspend fun delete(orderType: OrderTypes) {
        databaseDataStore.delete(orderType.name)

        when(orderType) {
            OrderTypes.ACTIVE -> removeActiveOrdersFromCache()
            OrderTypes.COMPLETED -> removeCompletedOrdersFromCache()
            OrderTypes.CANCELLED -> removeCancelledOrdersFromCache()
        }
    }


    @Synchronized
    override suspend fun deleteAll() {
        databaseDataStore.deleteAll()
        removeAllOrdersFromCache()
    }


    @Synchronized
    override suspend fun create(params: OrderCreationParameters): RepositoryResult<OrderResponse> {
        return if(connectionProvider.isNetworkAvailable()) {
            return RepositoryResult(serverResult = serverDataStore.create(params))
        } else {
            RepositoryResult(serverResult = Result.Failure(NoInternetException()))
        }
    }


    @Synchronized
    override suspend fun cancel(order: Order, params: OrderParameters): RepositoryResult<OrderResponse> {
        return if(connectionProvider.isNetworkAvailable()) {
            val result = RepositoryResult(serverResult = serverDataStore.cancel(order.id))

            if(result.isServerResultSuccessful()) {
                // Deleting the old entry and inserting the new one
                // into the database
                delete(params, order)
                save(
                    params.copy(type = OrderTypes.CANCELLED),
                    Order.convertFromActiveToCancelled(order)
                )

                // Deleting the entry from the cache of active orders
                removeActiveOrderFromCache(order)
            }

            result
        } else {
            RepositoryResult(serverResult = Result.Failure(NoInternetException()))
        }
    }


    @Synchronized
    override suspend fun search(params: OrderParameters): RepositoryResult<List<Order>> {
        val result = when(params.type) {
            OrderTypes.ACTIVE -> getActiveOrders(params)
            OrderTypes.COMPLETED -> getCompletedOrders(params)
            OrderTypes.CANCELLED -> getCancelledOrders(params)
        }

        // Making sure that the data is present since the search is performed
        // solely on database records
        return if(result.isSuccessful()) {
            RepositoryResult(databaseResult = databaseDataStore.search(params))
        } else {
            result
        }
    }


    @Synchronized
    override suspend fun getActiveOrders(params: OrderParameters): RepositoryResult<List<Order>> {
        val result = RepositoryResult<List<Order>>()

        if(!cache.hasActiveOrders()) {
            var onSuccess: suspend ((Result.Success<List<Order>>) -> Unit) = {}

            if(connectionProvider.isNetworkAvailable()) {
                result.serverResult = serverDataStore.getActiveOrders(params)
                onSuccess = {
                    // Getting rid of old ones since most of them are already closed
                    // and it would be a mistake to show them to the user in the
                    // future database loads (e.g., when the network is not
                    // available)
                    delete(params.type)
                    save(params, it.value)
                }
            }

            if(result.isServerResultErroneous(true)) {
                result.databaseResult = databaseDataStore.getActiveOrders(params)
                onSuccess = { saveActiveOrdersToCache(it.value) }
            }

            handleRepositoryResult(result, onSuccess)
        } else {
            result.cacheResult = Result.Success(getActiveOrdersFromCache(params))
        }

        return result
    }


    @Synchronized
    override suspend fun getCompletedOrders(params: OrderParameters): RepositoryResult<List<Order>> {
        val result = RepositoryResult<List<Order>>()

        if(!cache.hasCompletedOrders()) {
            var onSuccess: suspend ((Result.Success<List<Order>>) -> Unit) = {}

            if(connectionProvider.isNetworkAvailable()) {
                result.serverResult = serverDataStore.getCompletedOrders(params)
                onSuccess = {
                    // Deleting the old completed orders to remove the redundant ones
                    // since the new data set may not already have them
                    delete(params.type)
                    save(params, it.value)
                }
            }

            if(result.isServerResultErroneous(true)) {
                result.databaseResult = databaseDataStore.getCompletedOrders(params)
                onSuccess = { saveCompletedOrdersToCache(it.value) }
            }

            handleRepositoryResult(result, onSuccess)
        } else {
            result.cacheResult = Result.Success(getCompletedOrdersFromCache(params))
        }

        return result
    }


    @Synchronized
    override suspend fun getCancelledOrders(params: OrderParameters): RepositoryResult<List<Order>> {
        val result = RepositoryResult<List<Order>>()

        if(!cache.hasCancelledOrders()) {
            var onSuccess: suspend ((Result.Success<List<Order>>) -> Unit) = {}

            if(connectionProvider.isNetworkAvailable()) {
                result.serverResult = serverDataStore.getCancelledOrders(params)
                onSuccess = {
                    // Deleting the old cancelled orders to remove the redundant ones
                    // since the new data set may not already have them
                    delete(params.type)
                    save(params, it.value)
                }
            }

            if(result.isServerResultErroneous(true)) {
                result.databaseResult = databaseDataStore.getCancelledOrders(params)
                onSuccess = { saveCancelledOrdersToCache(it.value) }
            }

            handleRepositoryResult(result, onSuccess)
        } else {
            result.cacheResult = Result.Success(getCancelledOrdersFromCache(params))
        }

        return result
    }


    private fun addOrUpdateActiveOrderToCache(params: OrderParameters, order: Order) {
        addOrUpdateOrderToCache(
            order = order,
            params = params,
            areItemsPresent = { hasActiveOrders() },
            fetchItems = { getActiveOrders() },
            saveCache = { saveActiveOrders(it) }
        )
    }


    private fun addOrUpdateCompletedOrderToCache(params: OrderParameters, order: Order) {
        addOrUpdateOrderToCache(
            order = order,
            params = params,
            areItemsPresent = { hasCompletedOrders() },
            fetchItems = { getCompletedOrders() },
            saveCache = { saveCompletedOrders(it) }
        )
    }


    private fun addOrUpdateCancelledOrderToCache(params: OrderParameters, order: Order) {
        addOrUpdateOrderToCache(
            order = order,
            params = params,
            areItemsPresent = { hasCancelledOrders() },
            fetchItems = { getCancelledOrders() },
            saveCache = { saveCancelledOrders(it) }
        )
    }


    private fun addOrUpdateOrderToCache(order: Order,
                                        params: OrderParameters,
                                        areItemsPresent: (OrdersCache.() -> Boolean),
                                        fetchItems: (OrdersCache.() -> List<Order>),
                                        saveCache: (OrdersCache.(List<Order>) -> Unit)) {
        addOrUpdateItemToCache(
            cache = cache,
            item = order,
            areItemsPresent = areItemsPresent,
            fetchItems = fetchItems,
            areEqual = { cacheItem, newItem ->
                cacheItem.id == newItem.id
            },
            getIndexToInsertAt = { cacheItems, newItem ->
                cacheItems.getIndexToInsertAt(newItem, params.sortType)
            },
            saveCache = saveCache
        )
    }


    private fun saveActiveOrdersToCache(orders: List<Order>) {
        cache.saveActiveOrders(orders)
    }


    private fun saveCompletedOrdersToCache(orders: List<Order>) {
        cache.saveCompletedOrders(orders)
    }


    private fun saveCancelledOrdersToCache(orders: List<Order>) {
        cache.saveCancelledOrders(orders)
    }


    private fun removeActiveOrderFromCache(order: Order) {
        removeOrderFromCache(
            order = order,
            areItemsPresent = { hasActiveOrders() },
            fetchItems = { getActiveOrders() },
            saveCache = { saveActiveOrders(it) }
        )
    }


    private fun removeCompletedOrderFromCache(order: Order) {
        removeOrderFromCache(
            order = order,
            areItemsPresent = { hasCompletedOrders() },
            fetchItems = { getCompletedOrders() },
            saveCache = { saveCompletedOrders(it) }
        )
    }


    private fun removeCancelledOrderFromCache(order: Order) {
        removeOrderFromCache(
            order = order,
            areItemsPresent = { hasCancelledOrders() },
            fetchItems = { getCancelledOrders() },
            saveCache = { saveCancelledOrders(it) }
        )
    }


    private fun removeOrderFromCache(order: Order,
                                     areItemsPresent: OrdersCache.() -> Boolean,
                                     fetchItems: OrdersCache.() -> List<Order>,
                                     saveCache: OrdersCache.(List<Order>) -> Unit) {
        removeItemFromCache(
            cache = cache,
            item = order,
            areItemsPresent = areItemsPresent,
            fetchItems = fetchItems,
            areEqual = { cacheItem, newItem ->
                cacheItem.id == newItem.id
            },
            saveCache = saveCache
        )
    }


    private fun removeActiveOrdersFromCache() {
        if(!cache.hasActiveOrders()) {
            return
        }

        cache.removeActiveOrders()
    }


    private fun removeCompletedOrdersFromCache() {
        if(!cache.hasCompletedOrders()) {
            return
        }

        cache.removeCompletedOrders()
    }


    private fun removeCancelledOrdersFromCache() {
        if(!cache.hasCancelledOrders()) {
            return
        }

        cache.removeCancelledOrders()
    }


    private fun removeAllOrdersFromCache() {
        if(cache.isEmpty()) {
            return
        }

        cache.clear()
    }


    private fun getActiveOrdersFromCache(params: OrderParameters): List<Order> {
        return cache.getActiveOrders().truncate(params.count)
    }


    private fun getCompletedOrdersFromCache(params: OrderParameters): List<Order> {
        return cache.getCompletedOrders().truncate(params.count)
    }


    private fun getCancelledOrdersFromCache(params: OrderParameters): List<Order> {
        return cache.getCancelledOrders().truncate(params.count)
    }


}
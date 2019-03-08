package com.stocksexchange.android.repositories.orderbook

import com.stocksexchange.android.api.model.Orderbook
import com.stocksexchange.android.api.model.OrderbookParameters
import com.stocksexchange.android.datastores.orderbook.OrderbookDataStore
import com.stocksexchange.android.datastores.orderbook.OrderbookDatabaseDataStore
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.repositories.base.repository.BaseRepositoryWithCache
import com.stocksexchange.android.utils.helpers.handleRepositoryResult
import com.stocksexchange.android.utils.providers.ConnectionProvider
import org.koin.standalone.inject

class OrderbookRepositoryImpl(
    private val serverDataStore: OrderbookDataStore,
    private val databaseDataStore: OrderbookDatabaseDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepositoryWithCache<OrderbookCache>(), OrderbookRepository {


    override val cache: OrderbookCache by inject()




    @Synchronized
    override fun refresh(params: OrderbookParameters) {
        cache.removeOrderbook(params.getCacheKey())
    }


    @Synchronized
    override suspend fun save(params: OrderbookParameters, orderbook: Orderbook) {
        databaseDataStore.save(params, orderbook)
        saveOrderbookToCache(params, orderbook)
    }


    @Synchronized
    override suspend fun get(params: OrderbookParameters): RepositoryResult<Orderbook> {
        val result = RepositoryResult<Orderbook>()

        if(!cache.hasOrderbook(params.getCacheKey())) {
            var onSuccess: suspend ((Result.Success<Orderbook>) -> Unit) = {}

            if(connectionProvider.isNetworkAvailable()) {
                result.serverResult = serverDataStore.get(params)
                onSuccess = { save(params, it.value) }
            }

            if(result.isServerResultErroneous(true)) {
                result.databaseResult = databaseDataStore.get(params)
                onSuccess = { saveOrderbookToCache(params, it.value) }
            }

            handleRepositoryResult(result, onSuccess)
        } else {
            result.cacheResult = Result.Success(getOrderbookFromCache(params))
        }

        return result
    }


    private fun saveOrderbookToCache(params: OrderbookParameters, orderbook: Orderbook) {
        cache.saveOrderbook(params.marketName, orderbook)
    }


    private fun getOrderbookFromCache(params: OrderbookParameters): Orderbook {
        return cache.getOrderbook(params.getCacheKey())
    }


}
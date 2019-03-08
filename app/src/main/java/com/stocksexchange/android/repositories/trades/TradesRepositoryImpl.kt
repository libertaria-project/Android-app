package com.stocksexchange.android.repositories.trades

import com.stocksexchange.android.api.model.Trade
import com.stocksexchange.android.api.model.TradeParameters
import com.stocksexchange.android.datastores.trades.TradesDataStore
import com.stocksexchange.android.datastores.trades.TradesDatabaseDataStore
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.repositories.base.repository.BaseRepositoryWithCache
import com.stocksexchange.android.utils.extensions.getIndexToInsertAt
import com.stocksexchange.android.utils.extensions.truncate
import com.stocksexchange.android.utils.helpers.addOrUpdateItemToCache
import com.stocksexchange.android.utils.helpers.handleRepositoryResult
import com.stocksexchange.android.utils.providers.ConnectionProvider
import org.koin.standalone.inject

class TradesRepositoryImpl(
    private val serverDataStore: TradesDataStore,
    private val databaseDataStore: TradesDatabaseDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepositoryWithCache<TradesCache>(), TradesRepository {


    override val cache: TradesCache by inject()




    @Synchronized
    override fun refresh(params: TradeParameters) {
        cache.removeTrades(params.getCacheKey())
    }


    @Synchronized
    override suspend fun save(params: TradeParameters, trade: Trade) {
        databaseDataStore.save(params, trade)
        saveTradeToCache(params, trade)
    }


    @Synchronized
    override suspend fun save(params: TradeParameters, trades: List<Trade>) {
        databaseDataStore.save(params, trades)
        saveTradesToCache(params, trades)
    }


    @Synchronized
    override suspend fun delete(params: TradeParameters, marketName: String) {
        databaseDataStore.delete(marketName)
        removeTradesFromCache(params)
    }


    @Synchronized
    override suspend fun get(params: TradeParameters): RepositoryResult<List<Trade>> {
        val result = RepositoryResult<List<Trade>>()

        if(!cache.hasTrades(params.getCacheKey())) {
            var onSuccess: suspend ((Result.Success<List<Trade>>) -> Unit) = {}

            if(connectionProvider.isNetworkAvailable()) {
                result.serverResult = serverDataStore.get(params)
                onSuccess = {
                    // Getting rid of old ones since they aren't much needed
                    // and it would be a mistake to show them to the user in the
                    // future database loads (e.g., when the network is not
                    // available)
                    delete(params, params.marketName)
                    save(params, it.value)
                }
            }

            if(result.isServerResultErroneous(true)) {
                result.databaseResult = databaseDataStore.get(params)
                onSuccess = { saveTradesToCache(params, it.value) }
            }

            handleRepositoryResult(result, onSuccess)
        } else {
            result.cacheResult = Result.Success(getTradesFromCache(params))
        }

        return result
    }


    private fun saveTradeToCache(params: TradeParameters, trade: Trade) {
        addOrUpdateItemToCache(
            cache = cache,
            item = trade,
            areItemsPresent = { hasTrades(params.getCacheKey()) },
            fetchItems = { getTrades(params.getCacheKey()) },
            areEqual = { cacheItem, newItem ->
                cacheItem.id == newItem.id
            },
            getIndexToInsertAt = { cacheItems, newItem ->
                cacheItems.getIndexToInsertAt(newItem, params.sortType)
            },
            saveCache = { saveTrades(params.getCacheKey(), it) }
        )
    }


    private fun saveTradesToCache(params: TradeParameters, trades: List<Trade>) {
        cache.saveTrades(params.getCacheKey(), trades)
    }


    private fun removeTradesFromCache(params: TradeParameters) {
        if(!cache.hasTrades(params.getCacheKey())) {
            return
        }

        cache.removeTrades(params.getCacheKey())
    }


    private fun getTradesFromCache(params: TradeParameters): List<Trade> {
        return cache.getTrades(params.getCacheKey()).truncate(params.count)
    }


}
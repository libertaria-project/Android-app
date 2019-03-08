package com.stocksexchange.android.repositories.currencymarkets

import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.datastores.currencymarkets.CurrencyMarketsDataStore
import com.stocksexchange.android.datastores.currencymarkets.CurrencyMarketsDatabaseDataStore
import com.stocksexchange.android.datastores.favoritecurrencymarkets.FavoriteCurrencyMarketsDataStore
import com.stocksexchange.android.model.CurrencyMarketTypes
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.providers.ConnectionProvider
import com.stocksexchange.android.repositories.base.repository.BaseRepositoryWithCache
import com.stocksexchange.android.utils.helpers.addOrUpdateItemToCache
import com.stocksexchange.android.utils.helpers.handleRepositoryResult
import com.stocksexchange.android.utils.helpers.removeItemFromCache
import org.koin.standalone.inject

class CurrencyMarketsRepositoryImpl(
    private val serverDataStore: CurrencyMarketsDataStore,
    private val databaseDataStore: CurrencyMarketsDatabaseDataStore,
    private val favoritesDatabaseDataStore: FavoriteCurrencyMarketsDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepositoryWithCache<CurrencyMarketsCache>(), CurrencyMarketsRepository {


    override val cache: CurrencyMarketsCache by inject()




    @Synchronized
    override fun refresh() {
        cache.removeCurrencyMarkets()
    }


    @Synchronized
    override suspend fun save(currencyMarket: CurrencyMarket) {
        databaseDataStore.save(currencyMarket)
        saveMarketToCache(currencyMarket)
    }


    @Synchronized
    override suspend fun save(currencyMarkets: List<CurrencyMarket>) {
        databaseDataStore.save(currencyMarkets)
        saveMarketsToCache(currencyMarkets)
    }


    @Synchronized
    override suspend fun favorite(currencyMarket: CurrencyMarket) {
        favoritesDatabaseDataStore.favorite(currencyMarket)
        saveFavoriteMarketIdToCache(currencyMarket.id)
    }


    @Synchronized
    override suspend fun unfavorite(currencyMarket: CurrencyMarket) {
        favoritesDatabaseDataStore.unfavorite(currencyMarket)
        removeFavoriteIdFromCache(currencyMarket.id)
    }


    @Synchronized
    override suspend fun deleteAll() {
        databaseDataStore.deleteAll()
        removeMarketsFromCache()
    }


    @Synchronized
    override suspend fun search(query: String): RepositoryResult<List<CurrencyMarket>> {
        val result = getAll()

        // Making sure that the data is present since the search is performed
        // solely on database records
        return if(result.isSuccessful()) {
            RepositoryResult(databaseResult = databaseDataStore.search(query))
        } else {
            result
        }
    }


    @Synchronized
    override suspend fun isCurrencyMarketFavorite(currencyMarket: CurrencyMarket): Boolean {
        val favoriteCurrencyMarketIdsResult = getFavoriteCurrencyMarketIds()
        val favoriteCurrencyMarketIds = when(favoriteCurrencyMarketIdsResult) {
            is Result.Success -> favoriteCurrencyMarketIdsResult.value
            is Result.Failure -> return false
        }

        for(favoriteCurrencyMarketId in favoriteCurrencyMarketIds) {
            if(favoriteCurrencyMarketId == currencyMarket.id) {
                return true
            }
        }

        return false
    }


    private suspend fun getFavoriteCurrencyMarketIds(): Result<List<Long>> {
        if(cache.hasFavoriteCurrencyMarketIds()) {
            return Result.Success(cache.getFavoriteCurrencyMarketIds())
        }

        val favoriteCurrencyMarketIdsResult = favoritesDatabaseDataStore.getAll()

        return when(favoriteCurrencyMarketIdsResult) {
            is Result.Success -> favoriteCurrencyMarketIdsResult.also {
                saveFavoriteMarketIdsToCache(it.value)
            }

            is Result.Failure -> favoriteCurrencyMarketIdsResult
        }
    }


    @Synchronized
    override suspend fun getCurrencyMarket(id: Long): RepositoryResult<CurrencyMarket> {
        return getCurrencyMarketResult {
            (it.id == id)
        }
    }


    @Synchronized
    override suspend fun getCurrencyMarket(baseCurrencySymbol: String,
                                           quoteCurrencySymbol: String): RepositoryResult<CurrencyMarket> {
        return getCurrencyMarketResult {
            ((it.baseCurrencySymbol == baseCurrencySymbol) && (it.quoteCurrencySymbol == quoteCurrencySymbol))
        }
    }


    private suspend fun getCurrencyMarketResult(condition: (CurrencyMarket) -> Boolean): RepositoryResult<CurrencyMarket> {
        val result = getAll()

        return if(result.isSuccessful()) {
            for(currencyMarket in result.getSuccessfulResultValue()) {
                if(condition(currencyMarket)) {
                    val currencyMarketResult = Result.Success(currencyMarket)

                    return when {
                        result.isCacheResultSuccessful() -> RepositoryResult(cacheResult = currencyMarketResult)
                        result.isServerResultSuccessful() -> RepositoryResult(serverResult = currencyMarketResult)

                        else -> RepositoryResult(databaseResult = currencyMarketResult)
                    }
                }
            }

            RepositoryResult(cacheResult = Result.Failure(NotFoundException()))
        } else {
            RepositoryResult(cacheResult = Result.Failure(NotFoundException()))
        }
    }


    @Synchronized
    override suspend fun getBitcoinMarkets(): RepositoryResult<List<CurrencyMarket>> {
        return getCurrencyMarkets(CurrencyMarketTypes.BTC.name)
    }


    @Synchronized
    override suspend fun getTetherMarkets(): RepositoryResult<List<CurrencyMarket>> {
        return getCurrencyMarkets(CurrencyMarketTypes.USDT.name)
    }


    @Synchronized
    override suspend fun getNxtMarkets(): RepositoryResult<List<CurrencyMarket>> {
        return getCurrencyMarkets(CurrencyMarketTypes.NXT.name)
    }


    @Synchronized
    override suspend fun getLitecoinMarkets(): RepositoryResult<List<CurrencyMarket>> {
        return getCurrencyMarkets(CurrencyMarketTypes.LTC.name)
    }


    @Synchronized
    override suspend fun getEthereumMarkets(): RepositoryResult<List<CurrencyMarket>> {
        return getCurrencyMarkets(CurrencyMarketTypes.ETH.name)
    }


    @Synchronized
    override suspend fun getUsdMarkets(): RepositoryResult<List<CurrencyMarket>> {
        return getCurrencyMarkets(CurrencyMarketTypes.USD.name)
    }


    @Synchronized
    override suspend fun getEurMarkets(): RepositoryResult<List<CurrencyMarket>> {
        return getCurrencyMarkets(CurrencyMarketTypes.EUR.name)
    }


    @Synchronized
    override suspend fun getJpyMarkets(): RepositoryResult<List<CurrencyMarket>> {
        return getCurrencyMarkets(CurrencyMarketTypes.JPY.name)
    }


    @Synchronized
    override suspend fun getRubMarkets(): RepositoryResult<List<CurrencyMarket>> {
        return getCurrencyMarkets(CurrencyMarketTypes.RUB.name)
    }


    @Synchronized
    override suspend fun getCurrencyMarkets(quoteCurrencySymbol: String): RepositoryResult<List<CurrencyMarket>> {
        val result = getAll()

        return if(result.isSuccessful()) {
            val marketsResult = Result.Success(result.getSuccessfulResultValue().filter {
                it.quoteCurrencySymbol == quoteCurrencySymbol
            })

            when {
                result.isCacheResultSuccessful() -> result.cacheResult = marketsResult
                result.isServerResultSuccessful() -> result.serverResult = marketsResult
                else -> result.databaseResult = marketsResult
            }

            result
        } else {
            result
        }
    }


    @Synchronized
    override suspend fun getFavoriteMarkets(): RepositoryResult<List<CurrencyMarket>> {
        val currencyMarketsResult = getAll()
        val currencyMarkets = if(currencyMarketsResult.isSuccessful()) {
            currencyMarketsResult.getSuccessfulResultValue()
        } else {
            return currencyMarketsResult
        }
        val favoriteCurrencyMarketIdsResult = getFavoriteCurrencyMarketIds()
        val favoriteCurrencyMarketIds = when(favoriteCurrencyMarketIdsResult) {
            is Result.Success -> favoriteCurrencyMarketIdsResult.value
            is Result.Failure -> return RepositoryResult(cacheResult = favoriteCurrencyMarketIdsResult)
        }
        val favoriteCurrencyMarkets = mutableListOf<CurrencyMarket>()

        for(currencyMarket in currencyMarkets) {
            for(favoriteCurrencyMarketId in favoriteCurrencyMarketIds) {
                if(favoriteCurrencyMarketId == currencyMarket.id) {
                    favoriteCurrencyMarkets.add(currencyMarket)
                }
            }
        }

        return RepositoryResult(cacheResult = Result.Success(favoriteCurrencyMarkets))
    }


    @Synchronized
    override suspend fun getAll(): RepositoryResult<List<CurrencyMarket>> {
        val result = RepositoryResult<List<CurrencyMarket>>()

        if(!cache.hasCurrencyMarkets()) {
            var onSuccess: suspend ((Result.Success<List<CurrencyMarket>>) -> Unit) = {}

            if(connectionProvider.isNetworkAvailable()) {
                result.serverResult = serverDataStore.getAll()
                onSuccess = {
                    // Getting rid of old ones since quite a few could be no
                    // longer active and it would be a mistake to show them
                    // to the user in the future database loads (e.g., when
                    // the network is not available)
                    deleteAll()
                    save(it.value)
                }
            }

            if(result.isServerResultErroneous(true)) {
                result.databaseResult = databaseDataStore.getAll()
                onSuccess = { saveMarketsToCache(it.value) }
            }

            handleRepositoryResult(result, onSuccess)
        } else {
            result.cacheResult = Result.Success(getCurrencyMarketsFromCache())
        }

        return result
    }


    private fun saveMarketToCache(currencyMarket: CurrencyMarket) {
        addOrUpdateItemToCache(
            cache = cache,
            item = currencyMarket,
            areItemsPresent = { hasCurrencyMarkets() },
            fetchItems = { getCurrencyMarkets() },
            areEqual = { cacheItem, newItem ->
                cacheItem.id == newItem.id
            },
            getIndexToInsertAt = { cacheItems, _ ->
                cacheItems.size
            },
            saveCache = { saveCurrencyMarkets(it) }
        )
    }


    private fun saveFavoriteMarketIdToCache(id: Long) {
        addOrUpdateItemToCache(
            cache = cache,
            item = id,
            areItemsPresent = { hasFavoriteCurrencyMarketIds() },
            fetchItems = { getFavoriteCurrencyMarketIds() },
            areEqual = { cacheItem, newItem ->
                cacheItem == newItem
            },
            getIndexToInsertAt = { cacheItems, _ ->
                cacheItems.size
            },
            saveCache = { saveFavoriteCurrencyMarketIds(it) }
        )
    }


    private fun saveMarketsToCache(currencyMarkets: List<CurrencyMarket>) {
        cache.saveCurrencyMarkets(currencyMarkets)
    }


    private fun saveFavoriteMarketIdsToCache(ids: List<Long>) {
        cache.saveFavoriteCurrencyMarketIds(ids)
    }


    private fun removeFavoriteIdFromCache(id: Long) {
        removeItemFromCache(
            cache = cache,
            item = id,
            areItemsPresent = { hasFavoriteCurrencyMarketIds() },
            fetchItems = { getFavoriteCurrencyMarketIds() },
            areEqual = { cacheItem, newItem ->
                cacheItem == newItem
            },
            saveCache = { saveFavoriteCurrencyMarketIds(it) }
        )
    }


    private fun removeMarketsFromCache() {
        if(cache.hasCurrencyMarkets()) {
            return
        }

        cache.removeCurrencyMarkets()
    }


    private fun getCurrencyMarketsFromCache(): List<CurrencyMarket> {
        return cache.getCurrencyMarkets()
    }


}
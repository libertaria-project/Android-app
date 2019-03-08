package com.stocksexchange.android.repositories.currencies

import com.stocksexchange.android.api.model.Currency
import com.stocksexchange.android.datastores.currencies.CurrenciesDataStore
import com.stocksexchange.android.datastores.currencies.CurrenciesDatabaseDataStore
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.providers.ConnectionProvider
import com.stocksexchange.android.repositories.base.repository.BaseRepositoryWithCache
import com.stocksexchange.android.utils.helpers.handleRepositoryResult
import org.koin.standalone.inject

class CurrenciesRepositoryImpl(
    private val serverDataStore: CurrenciesDataStore,
    private val databaseDataStore: CurrenciesDatabaseDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepositoryWithCache<CurrenciesCache>(), CurrenciesRepository {


    override val cache: CurrenciesCache by inject()




    @Synchronized
    override fun refresh() {
        cache.clear()
    }


    @Synchronized
    override suspend fun save(currencies: List<Currency>) {
        databaseDataStore.save(currencies)
        saveCurrenciesToCache(currencies)
    }


    @Synchronized
    override suspend fun deleteAll() {
        databaseDataStore.deleteAll()
        removeCurrenciesFromCache()
    }


    @Synchronized
    override suspend fun search(query: String): RepositoryResult<List<Currency>> {
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
    override suspend fun getAll(): RepositoryResult<List<Currency>> {
        val result = RepositoryResult<List<Currency>>()

        if(!cache.hasCurrencies()) {
            var onSuccess: suspend ((Result.Success<List<Currency>>) -> Unit) = {}

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
                onSuccess = { saveCurrenciesToCache(it.value) }
            }

            handleRepositoryResult(result, onSuccess)
        } else {
            result.cacheResult = Result.Success(getCurrenciesFromCache())
        }

        return result
    }


    private fun saveCurrenciesToCache(currencies: List<Currency>) {
        cache.saveCurrencies(currencies)
    }


    private fun removeCurrenciesFromCache() {
        if(cache.isEmpty()) {
            return
        }

        cache.clear()
    }


    private fun getCurrenciesFromCache(): List<Currency> {
        return cache.getCurrencies()
    }


}
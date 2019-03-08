package com.stocksexchange.android.repositories.pricechartdata

import com.stocksexchange.android.api.model.PriceChartData
import com.stocksexchange.android.datastores.pricechartdata.PriceChartDataDataStore
import com.stocksexchange.android.datastores.pricechartdata.PriceChartDataDatabaseDataStore
import com.stocksexchange.android.api.model.PriceChartParameters
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.providers.ConnectionProvider
import com.stocksexchange.android.repositories.base.repository.BaseRepositoryWithCache
import com.stocksexchange.android.utils.helpers.handleRepositoryResult
import org.koin.standalone.inject

class PriceChartDataRepositoryImpl(
    private val serverDataStore: PriceChartDataDataStore,
    private val databaseDataStore: PriceChartDataDatabaseDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepositoryWithCache<PriceChartDataCache>(), PriceChartDataRepository {


    override val cache: PriceChartDataCache by inject()




    @Synchronized
    override fun refresh(params: PriceChartParameters) {
        cache.removeAllPriceChartDataThatStartsWithKey(params.getCacheKey().marketName)
    }


    @Synchronized
    override suspend fun save(params: PriceChartParameters, priceChartData: PriceChartData) {
        databaseDataStore.save(priceChartData)
        savePriceChartDataToCache(params, priceChartData)
    }


    @Synchronized
    override suspend fun get(params: PriceChartParameters): RepositoryResult<PriceChartData> {
        val result = RepositoryResult<PriceChartData>()

        if(!cache.hasPriceChartData(params.getCacheKeyString())) {
            var onSuccess: suspend ((Result.Success<PriceChartData>) -> Unit) = {}

            if(connectionProvider.isNetworkAvailable()) {
                result.serverResult = serverDataStore.get(params)
                onSuccess = { save(params, it.value) }
            }

            if(result.isServerResultErroneous(true)) {
                result.databaseResult = databaseDataStore.get(params)
                onSuccess = { savePriceChartDataToCache(params, it.value) }
            }

            handleRepositoryResult(result, onSuccess)
        } else {
            result.cacheResult = Result.Success(getPriceChartDataFromCache(params))
        }

        return result
    }


    private fun savePriceChartDataToCache(params: PriceChartParameters, data: PriceChartData) {
        cache.savePriceChartData(params.getCacheKeyString(), data)
    }


    private fun getPriceChartDataFromCache(params: PriceChartParameters): PriceChartData {
        return cache.getPriceChartData(params.getCacheKeyString())
    }


}
package com.stocksexchange.android.repositories.pricechartdata

import com.stocksexchange.android.api.model.PriceChartData
import com.stocksexchange.android.repositories.base.cache.BaseRepositoryCache

class PriceChartDataCache : BaseRepositoryCache() {


    fun savePriceChartData(key: String, priceChartData: PriceChartData) {
        cache.put(key, priceChartData)
    }


    fun removeAllPriceChartDataThatStartsWithKey(key: String) {
        val cacheKeys = cache.getKeys()

        for(cacheKey in cacheKeys) {
            if(cacheKey.startsWith(key)) {
                cache.remove(cacheKey)
            }
        }
    }


    fun getPriceChartData(key: String): PriceChartData {
        return (cache.get(key) as PriceChartData)
    }


    fun hasPriceChartData(key: String): Boolean {
        return cache.contains(key)
    }


}
package com.stocksexchange.android.repositories.trades

import com.stocksexchange.android.api.model.Trade
import com.stocksexchange.android.repositories.base.cache.BaseRepositoryCache

class TradesCache : BaseRepositoryCache() {


    fun saveTrades(key: String, trades: List<Trade>) {
        cache.put(key, trades)
    }


    fun removeTrades(key: String) {
        cache.remove(key)
    }


    @Suppress("UNCHECKED_CAST")
    fun getTrades(key: String): List<Trade> {
        return (cache.get(key) as List<Trade>)
    }


    fun hasTrades(key: String): Boolean {
        return cache.contains(key)
    }


}
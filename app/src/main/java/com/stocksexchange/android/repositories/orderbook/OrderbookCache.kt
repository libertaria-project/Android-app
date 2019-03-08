package com.stocksexchange.android.repositories.orderbook

import com.stocksexchange.android.api.model.Orderbook
import com.stocksexchange.android.repositories.base.cache.BaseRepositoryCache

class OrderbookCache : BaseRepositoryCache() {


    fun saveOrderbook(key: String, orderbook: Orderbook) {
        cache.put(key, orderbook)
    }


    fun removeOrderbook(key: String) {
        cache.remove(key)
    }


    fun getOrderbook(key: String): Orderbook {
        return (cache.get(key) as Orderbook)
    }


    fun hasOrderbook(key: String): Boolean {
        return cache.contains(key)
    }


}
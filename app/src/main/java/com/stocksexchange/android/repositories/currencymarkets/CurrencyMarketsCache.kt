package com.stocksexchange.android.repositories.currencymarkets

import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.repositories.base.cache.BaseRepositoryCache

class CurrencyMarketsCache : BaseRepositoryCache() {


    companion object {

        private const val KEY_CURRENCY_MARKETS = "currency_markets"
        private const val KEY_FAVORITE_CURRENCY_MARKET_IDS = "favorite_currency_market_ds"

    }




    fun saveCurrencyMarkets(currencyMarkets: List<CurrencyMarket>) {
        cache.put(KEY_CURRENCY_MARKETS, currencyMarkets)
    }


    fun removeCurrencyMarkets() {
        cache.remove(KEY_CURRENCY_MARKETS)
    }


    @Suppress("UNCHECKED_CAST")
    fun getCurrencyMarkets(): List<CurrencyMarket> {
        return (cache.get(KEY_CURRENCY_MARKETS) as List<CurrencyMarket>)
    }


    fun hasCurrencyMarkets(): Boolean {
        return cache.contains(KEY_CURRENCY_MARKETS)
    }


    fun saveFavoriteCurrencyMarketIds(ids: List<Long>) {
        cache.put(KEY_FAVORITE_CURRENCY_MARKET_IDS, ids)
    }


    @Suppress("UNCHECKED_CAST")
    fun getFavoriteCurrencyMarketIds(): List<Long> {
        return (cache.get(KEY_FAVORITE_CURRENCY_MARKET_IDS) as List<Long>)
    }


    fun hasFavoriteCurrencyMarketIds(): Boolean {
        return cache.contains(KEY_FAVORITE_CURRENCY_MARKET_IDS)
    }


}
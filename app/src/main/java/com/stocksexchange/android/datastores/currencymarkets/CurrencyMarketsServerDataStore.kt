package com.stocksexchange.android.datastores.currencymarkets

import com.stocksexchange.android.api.StocksExchangeApi
import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class CurrencyMarketsServerDataStore(
    private val stocksExchangeApi: StocksExchangeApi
) : CurrencyMarketsDataStore {


    override suspend fun save(currencyMarket: CurrencyMarket) {
        throw UnsupportedOperationException()
    }


    override suspend fun save(currencyMarkets: List<CurrencyMarket>) {
        throw UnsupportedOperationException()
    }


    override suspend fun deleteAll() {
        throw UnsupportedOperationException()
    }


    override suspend fun search(query: String): Result<List<CurrencyMarket>> {
        throw UnsupportedOperationException()
    }


    override suspend fun getAll(): Result<List<CurrencyMarket>> {
        return performBackgroundOperation {
            stocksExchangeApi.getCurrencyMarkets()
        }
    }


}
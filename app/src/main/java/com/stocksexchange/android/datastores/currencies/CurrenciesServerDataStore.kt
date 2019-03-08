package com.stocksexchange.android.datastores.currencies

import com.stocksexchange.android.api.StocksExchangeApi
import com.stocksexchange.android.api.model.Currency
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class CurrenciesServerDataStore(
    private val stocksExchangeApi: StocksExchangeApi
) : CurrenciesDataStore {


    override suspend fun save(currencies: List<Currency>) {
        throw UnsupportedOperationException()
    }


    override suspend fun deleteAll() {
        throw UnsupportedOperationException()
    }


    override suspend fun search(query: String): Result<List<Currency>> {
        throw UnsupportedOperationException()
    }


    override suspend fun getAll(): Result<List<Currency>> {
        return performBackgroundOperation {
            stocksExchangeApi.getCurrencies()
        }
    }


}
package com.stocksexchange.android.data.stores.currencypairs

import com.stocksexchange.android.api.StexApi
import com.stocksexchange.android.api.model.newapi.CurrencyPair
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class CurrencyPairsServerDataStore(
    private val stexApi: StexApi
) : CurrencyPairsDataStore {


    override suspend fun save(currencyPairs: List<CurrencyPair>) {
        throw UnsupportedOperationException()
    }


    override suspend fun deleteAll() {
        throw UnsupportedOperationException()
    }


    override suspend fun getAll(): Result<List<CurrencyPair>> {
        return performBackgroundOperation {
            stexApi.getAllCurrencyPairs()
        }
    }


}
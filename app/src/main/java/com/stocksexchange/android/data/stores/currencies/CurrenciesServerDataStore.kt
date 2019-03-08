package com.stocksexchange.android.data.stores.currencies

import com.stocksexchange.android.api.StexApi
import com.stocksexchange.android.api.model.newapi.Currency
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class CurrenciesServerDataStore(
    private val stexApi: StexApi
) : CurrenciesDataStore {


    override suspend fun save(currencies: List<Currency>) {
        throw UnsupportedOperationException()
    }


    override suspend fun deleteAll() {
        throw UnsupportedOperationException()
    }


    override suspend fun getAll(): Result<List<Currency>> {
        return performBackgroundOperation {
            stexApi.getCurrencies()
        }
    }


}
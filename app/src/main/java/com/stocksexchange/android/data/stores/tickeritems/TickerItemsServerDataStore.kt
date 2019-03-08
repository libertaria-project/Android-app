package com.stocksexchange.android.data.stores.tickeritems

import com.stocksexchange.android.api.StexApi
import com.stocksexchange.android.api.model.newapi.TickerItem
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class TickerItemsServerDataStore(
    private val stexApi: StexApi
) : TickerItemsDataStore {


    override suspend fun save(tickerItems: List<TickerItem>) {
        throw UnsupportedOperationException()
    }


    override suspend fun deleteAll() {
        throw UnsupportedOperationException()
    }


    override suspend fun getAll(): Result<List<TickerItem>> {
        return performBackgroundOperation {
            stexApi.getTickerItems()
        }
    }


}
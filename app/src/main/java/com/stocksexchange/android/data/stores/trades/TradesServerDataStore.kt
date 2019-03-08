package com.stocksexchange.android.data.stores.trades

import com.stocksexchange.android.api.StexApi
import com.stocksexchange.android.api.model.newapi.Trade
import com.stocksexchange.android.api.model.newapi.TradeParameters
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class TradesServerDataStore(
    private val stexApi: StexApi
) : TradesDataStore {


    override suspend fun save(params: TradeParameters, trade: Trade) {
        throw UnsupportedOperationException()
    }


    override suspend fun save(params: TradeParameters, trades: List<Trade>) {
        throw UnsupportedOperationException()
    }


    override suspend fun delete(currencyPairId: Int) {
        throw UnsupportedOperationException()
    }


    override suspend fun get(params: TradeParameters): Result<List<Trade>> {
        return performBackgroundOperation {
            stexApi.getTrades(params)
        }
    }


}
package com.stocksexchange.android.datastores.trades

import com.stocksexchange.android.api.StocksExchangeApi
import com.stocksexchange.android.api.model.Trade
import com.stocksexchange.android.api.model.TradeParameters
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation
import java.lang.UnsupportedOperationException

class TradesServerDataStore(
    private val stocksExchangeApi: StocksExchangeApi
) : TradesDataStore {


    override suspend fun save(params: TradeParameters, trade: Trade) {
        throw UnsupportedOperationException()
    }


    override suspend fun save(params: TradeParameters, trades: List<Trade>) {
        throw UnsupportedOperationException()
    }


    override suspend fun delete(marketName: String) {
        throw UnsupportedOperationException()
    }


    override suspend fun get(params: TradeParameters): Result<List<Trade>> {
        return performBackgroundOperation {
            stocksExchangeApi.getTrades(params)
        }
    }


}
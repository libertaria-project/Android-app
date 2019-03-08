package com.stocksexchange.android.datastores.trades

import com.stocksexchange.android.api.model.Trade
import com.stocksexchange.android.api.model.TradeParameters
import com.stocksexchange.android.model.Result

interface TradesDataStore {

    suspend fun save(params: TradeParameters, trade: Trade)

    suspend fun save(params: TradeParameters, trades: List<Trade>)

    suspend fun delete(marketName: String)

    suspend fun get(params: TradeParameters): Result<List<Trade>>

}
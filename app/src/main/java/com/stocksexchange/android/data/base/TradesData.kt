package com.stocksexchange.android.data.base

import com.stocksexchange.android.api.model.newapi.Trade
import com.stocksexchange.android.api.model.newapi.TradeParameters

interface TradesData<TradesFetchingResult> {

    suspend fun save(params: TradeParameters, trade: Trade)

    suspend fun save(params: TradeParameters, trades: List<Trade>)

    suspend fun delete(currencyPairId: Int)

    suspend fun get(params: TradeParameters): TradesFetchingResult

}
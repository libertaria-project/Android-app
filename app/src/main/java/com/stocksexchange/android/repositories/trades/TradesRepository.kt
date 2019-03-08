package com.stocksexchange.android.repositories.trades

import com.stocksexchange.android.api.model.Trade
import com.stocksexchange.android.api.model.TradeParameters
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.repositories.base.repository.Repository

interface TradesRepository : Repository {

    fun refresh(params: TradeParameters)

    suspend fun save(params: TradeParameters, trade: Trade)

    suspend fun save(params: TradeParameters, trades: List<Trade>)

    suspend fun delete(params: TradeParameters, marketName: String)

    suspend fun get(params: TradeParameters): RepositoryResult<List<Trade>>

}
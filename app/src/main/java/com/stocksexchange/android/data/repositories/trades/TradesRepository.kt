package com.stocksexchange.android.data.repositories.trades

import com.stocksexchange.android.api.model.newapi.Trade
import com.stocksexchange.android.api.model.newapi.TradeParameters
import com.stocksexchange.android.data.base.TradesData
import com.stocksexchange.android.model.RepositoryResult

interface TradesRepository : TradesData<RepositoryResult<List<Trade>>> {

    fun refresh(params: TradeParameters)

}
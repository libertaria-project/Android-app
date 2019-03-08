package com.stocksexchange.android.data.repositories.candlesticks

import com.stocksexchange.android.api.model.newapi.CandleStick
import com.stocksexchange.android.api.model.newapi.PriceChartDataParameters
import com.stocksexchange.android.data.base.CandleSticksData
import com.stocksexchange.android.model.RepositoryResult

interface CandleSticksRepository : CandleSticksData<RepositoryResult<List<CandleStick>>> {

    fun refresh(params: PriceChartDataParameters)

}
package com.stocksexchange.android.data.base

import com.stocksexchange.android.api.model.newapi.CandleStick
import com.stocksexchange.android.api.model.newapi.PriceChartDataParameters

interface CandleSticksData<CandleSticksFetchingResult> {

    suspend fun save(params: PriceChartDataParameters, candleSticks: List<CandleStick>)

    suspend fun delete(params: PriceChartDataParameters)

    suspend fun get(params: PriceChartDataParameters): CandleSticksFetchingResult

}
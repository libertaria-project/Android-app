package com.stocksexchange.android.datastores.pricechartdata

import com.stocksexchange.android.api.model.PriceChartData
import com.stocksexchange.android.api.model.PriceChartParameters
import com.stocksexchange.android.model.Result

interface PriceChartDataDataStore {

    suspend fun save(priceChartData: PriceChartData)

    suspend fun get(params: PriceChartParameters): Result<PriceChartData>

}
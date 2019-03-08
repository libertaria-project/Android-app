package com.stocksexchange.android.datastores.pricechartdata

import com.stocksexchange.android.api.StocksExchangeApi
import com.stocksexchange.android.api.model.PriceChartData
import com.stocksexchange.android.api.model.PriceChartParameters
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class PriceChartDataServerDataStore(
    private val stocksExchangeApi: StocksExchangeApi
) : PriceChartDataDataStore {


    override suspend fun save(priceChartData: PriceChartData) {
        throw UnsupportedOperationException()
    }


    override suspend fun get(params: PriceChartParameters): Result<PriceChartData> {
        return performBackgroundOperation {
            stocksExchangeApi.getPriceChartData(params)
        }
    }


}
package com.stocksexchange.android.data.stores.candlesticks

import com.stocksexchange.android.api.StexApi
import com.stocksexchange.android.api.model.newapi.CandleStick
import com.stocksexchange.android.api.model.newapi.PriceChartDataParameters
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation
import java.lang.UnsupportedOperationException

class CandleSticksServerDataStore(
    private val stexApi: StexApi
) : CandleSticksDataStore {


    override suspend fun save(params: PriceChartDataParameters, candleSticks: List<CandleStick>) {
        throw UnsupportedOperationException()
    }


    override suspend fun delete(params: PriceChartDataParameters) {
        throw UnsupportedOperationException()
    }


    override suspend fun get(params: PriceChartDataParameters): Result<List<CandleStick>> {
        return performBackgroundOperation {
            stexApi.getCandleSticks(params)
        }
    }


}
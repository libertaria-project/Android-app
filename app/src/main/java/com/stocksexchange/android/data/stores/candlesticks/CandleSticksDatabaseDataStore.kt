package com.stocksexchange.android.data.stores.candlesticks

import com.stocksexchange.android.api.model.newapi.CandleStick
import com.stocksexchange.android.api.model.newapi.PriceChartDataParameters
import com.stocksexchange.android.database.tables.new.CandleSticksTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class CandleSticksDatabaseDataStore(
    private val candleSticksTable: CandleSticksTable
) : CandleSticksDataStore {


    override suspend fun save(params: PriceChartDataParameters, candleSticks: List<CandleStick>) {
        executeBackgroundOperation {
            candleSticksTable.save(params, candleSticks)
        }
    }


    override suspend fun delete(params: PriceChartDataParameters) {
        executeBackgroundOperation {
            candleSticksTable.delete(params)
        }
    }


    override suspend fun get(params: PriceChartDataParameters): Result<List<CandleStick>> {
        return performBackgroundOperation {
            candleSticksTable.get(params)
        }
    }


}
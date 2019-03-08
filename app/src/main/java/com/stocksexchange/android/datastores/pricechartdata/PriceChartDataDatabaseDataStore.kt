package com.stocksexchange.android.datastores.pricechartdata

import com.stocksexchange.android.api.model.PriceChartData
import com.stocksexchange.android.api.model.PriceChartParameters
import com.stocksexchange.android.database.tables.PriceChartDataTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation

class PriceChartDataDatabaseDataStore(
    private val priceChartDataTable: PriceChartDataTable
) : PriceChartDataDataStore {


    override suspend fun save(priceChartData: PriceChartData) {
        executeBackgroundOperation {
            priceChartDataTable.save(priceChartData)
        }
    }


    override suspend fun get(params: PriceChartParameters): Result<PriceChartData> {
        return performBackgroundOperation {
            priceChartDataTable.get(params)
        }
    }


}
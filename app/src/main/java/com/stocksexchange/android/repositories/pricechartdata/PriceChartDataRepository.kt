package com.stocksexchange.android.repositories.pricechartdata

import com.stocksexchange.android.api.model.PriceChartData
import com.stocksexchange.android.api.model.PriceChartParameters
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.repositories.base.repository.Repository

interface PriceChartDataRepository : Repository {

    fun refresh(params: PriceChartParameters)

    suspend fun save(params: PriceChartParameters, priceChartData: PriceChartData)

    suspend fun get(params: PriceChartParameters): RepositoryResult<PriceChartData>

}
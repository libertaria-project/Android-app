package com.stocksexchange.android.data.repositories.candlesticks

import com.stocksexchange.android.api.model.newapi.CandleStick
import com.stocksexchange.android.api.model.newapi.PriceChartDataParameters
import com.stocksexchange.android.data.stores.candlesticks.CandleSticksDataStore
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.repositories.base.repository.BaseRepository
import com.stocksexchange.android.repositories.utils.freshdatahandlers.interfaces.FreshDataHandler
import com.stocksexchange.android.utils.providers.ConnectionProvider

class CandleSticksRepositoryImpl(
    private val serverDataStore: CandleSticksDataStore,
    private val databaseDataStore: CandleSticksDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepository(), CandleSticksRepository {


    override val freshDataHandler: FreshDataHandler = CandleSticksFreshDataHandler()




    @Synchronized
    override fun refresh(params: PriceChartDataParameters) {
        freshDataHandler.refresh(params)
    }


    @Synchronized
    override suspend fun save(params: PriceChartDataParameters, candleSticks: List<CandleStick>) {
        databaseDataStore.save(params, candleSticks)
    }


    @Synchronized
    override suspend fun delete(params: PriceChartDataParameters) {
        databaseDataStore.delete(params)
    }


    @Synchronized
    override suspend fun get(params: PriceChartDataParameters): RepositoryResult<List<CandleStick>> {
        val result = RepositoryResult<List<CandleStick>>()

        if(freshDataHandler.shouldLoadFreshData(connectionProvider, params)) {
            result.serverResult = serverDataStore.get(params)

            if(result.isServerResultSuccessful()) {
                delete(params)
                save(params, result.getSuccessfulResultValue())
            }
        }

        if(result.isServerResultErroneous(true)) {
            result.databaseResult = databaseDataStore.get(params)
        }

        return result
    }


}
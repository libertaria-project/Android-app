package com.stocksexchange.android.data.repositories.trades

import com.stocksexchange.android.api.model.newapi.Trade
import com.stocksexchange.android.api.model.newapi.TradeParameters
import com.stocksexchange.android.data.stores.trades.TradesDataStore
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.repositories.base.repository.BaseRepository
import com.stocksexchange.android.repositories.utils.freshdatahandlers.interfaces.FreshDataHandler
import com.stocksexchange.android.utils.providers.ConnectionProvider

class TradesRepositoryImpl(
    private val serverDataStore: TradesDataStore,
    private val databaseDataStore: TradesDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepository(), TradesRepository {


    override val freshDataHandler: FreshDataHandler = TradesFreshDataHandler()




    override fun refresh(params: TradeParameters) {
        freshDataHandler.refresh(params)
    }


    override suspend fun save(params: TradeParameters, trade: Trade) {
        databaseDataStore.save(params, trade)
    }


    override suspend fun save(params: TradeParameters, trades: List<Trade>) {
        databaseDataStore.save(params, trades)
    }


    override suspend fun delete(currencyPairId: Int) {
        databaseDataStore.delete(currencyPairId)
    }


    override suspend fun get(params: TradeParameters): RepositoryResult<List<Trade>> {
        val result = RepositoryResult<List<Trade>>()

        if(freshDataHandler.shouldLoadFreshData(connectionProvider, params)) {
            result.serverResult = serverDataStore.get(params)

            if(result.isServerResultSuccessful()) {
                save(params, result.getSuccessfulResultValue())
            }
        }

        if(result.isServerResultErroneous(true)) {
            result.databaseResult = databaseDataStore.get(params)
        }

        return result
    }


}
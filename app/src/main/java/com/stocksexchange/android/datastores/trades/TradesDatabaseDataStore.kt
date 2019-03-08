package com.stocksexchange.android.datastores.trades

import com.stocksexchange.android.api.model.Trade
import com.stocksexchange.android.api.model.TradeParameters
import com.stocksexchange.android.database.tables.TradesTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class TradesDatabaseDataStore(
    private val tradesTable: TradesTable
) : TradesDataStore {


    override suspend fun save(params: TradeParameters, trade: Trade) {
        executeBackgroundOperation {
            tradesTable.save(params, trade)
        }
    }


    override suspend fun save(params: TradeParameters, trades: List<Trade>) {
        executeBackgroundOperation {
            tradesTable.save(params, trades)
        }
    }


    override suspend fun delete(marketName: String) {
        executeBackgroundOperation {
            tradesTable.delete(marketName)
        }
    }


    override suspend fun get(params: TradeParameters): Result<List<Trade>> {
        return performBackgroundOperation {
            tradesTable.get(params)
        }
    }


}
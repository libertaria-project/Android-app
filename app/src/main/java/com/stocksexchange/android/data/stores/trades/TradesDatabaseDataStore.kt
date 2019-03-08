package com.stocksexchange.android.data.stores.trades

import com.stocksexchange.android.api.model.newapi.Trade
import com.stocksexchange.android.api.model.newapi.TradeParameters
import com.stocksexchange.android.database.tables.new.TradesTable
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


    override suspend fun delete(currencyPairId: Int) {
        executeBackgroundOperation {
            tradesTable.delete(currencyPairId)
        }
    }


    override suspend fun get(params: TradeParameters): Result<List<Trade>> {
        return performBackgroundOperation {
            tradesTable.get(params)
        }
    }


}
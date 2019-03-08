package com.stocksexchange.android.datastores.orderbook

import com.stocksexchange.android.api.model.Orderbook
import com.stocksexchange.android.api.model.OrderbookParameters
import com.stocksexchange.android.database.tables.OrderbookTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class OrderbookDatabaseDataStore(
    private val orderbookTable: OrderbookTable
) : OrderbookDataStore {


    override suspend fun save(params: OrderbookParameters, orderbook: Orderbook) {
        executeBackgroundOperation {
            orderbookTable.save(params, orderbook)
        }
    }


    override suspend fun get(params: OrderbookParameters): Result<Orderbook> {
        return performBackgroundOperation {
            orderbookTable.get(params)
        }
    }


}
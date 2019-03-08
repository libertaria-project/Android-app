package com.stocksexchange.android.data.stores.orderbooks

import com.stocksexchange.android.api.model.newapi.Orderbook
import com.stocksexchange.android.api.model.newapi.OrderbookParameters
import com.stocksexchange.android.database.tables.new.OrderbooksTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class OrderbooksDatabaseDataStore(
    private val orderbooksTable: OrderbooksTable
) : OrderbooksDataStore {


    override suspend fun save(params: OrderbookParameters, orderbook: Orderbook) {
        executeBackgroundOperation {
            orderbooksTable.save(params, orderbook)
        }
    }


    override suspend fun get(params: OrderbookParameters): Result<Orderbook> {
        return performBackgroundOperation {
            orderbooksTable.get(params)
        }
    }


}
package com.stocksexchange.android.data.stores.tickeritems

import com.stocksexchange.android.api.model.newapi.TickerItem
import com.stocksexchange.android.database.tables.new.TickerItemsTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class TickerItemsDatabaseDataStore(
    private val tickerItemsTable: TickerItemsTable
) : TickerItemsDataStore {


    override suspend fun save(tickerItems: List<TickerItem>) {
        executeBackgroundOperation {
            tickerItemsTable.save(tickerItems)
        }
    }


    override suspend fun deleteAll() {
        executeBackgroundOperation {
            tickerItemsTable.deleteAll()
        }
    }


    override suspend fun getAll(): Result<List<TickerItem>> {
        return performBackgroundOperation {
            tickerItemsTable.getAll()
        }
    }


}
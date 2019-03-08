package com.stocksexchange.android.datastores.currencymarkets

import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.database.tables.CurrencyMarketsTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation

class CurrencyMarketsDatabaseDataStore(
    private val currencyMarketsTable: CurrencyMarketsTable
) : CurrencyMarketsDataStore {


    override suspend fun save(currencyMarket: CurrencyMarket) {
        executeBackgroundOperation {
            currencyMarketsTable.save(currencyMarket)
        }
    }


    override suspend fun save(currencyMarkets: List<CurrencyMarket>) {
        executeBackgroundOperation {
            currencyMarketsTable.save(currencyMarkets)
        }
    }


    override suspend fun deleteAll() {
        executeBackgroundOperation {
            currencyMarketsTable.deleteAll()
        }
    }


    override suspend fun search(query: String): Result<List<CurrencyMarket>> {
        return performBackgroundOperation{
            currencyMarketsTable.search(query)
        }
    }


    override suspend fun getAll(): Result<List<CurrencyMarket>> {
        return performBackgroundOperation {
            currencyMarketsTable.getAll()
        }
    }


}
package com.stocksexchange.android.data.stores.currencypairs

import com.stocksexchange.android.api.model.newapi.CurrencyPair
import com.stocksexchange.android.database.tables.new.CurrencyPairsTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class CurrencyPairsDatabaseDataStore(
    private val currencyPairsTable: CurrencyPairsTable
) : CurrencyPairsDataStore {


    override suspend fun save(currencyPairs: List<CurrencyPair>) {
        executeBackgroundOperation {
            currencyPairsTable.save(currencyPairs)
        }
    }


    override suspend fun deleteAll() {
        executeBackgroundOperation {
            currencyPairsTable.deleteAll()
        }
    }


    override suspend fun getAll(): Result<List<CurrencyPair>> {
        return performBackgroundOperation {
            currencyPairsTable.getAll()
        }
    }


}
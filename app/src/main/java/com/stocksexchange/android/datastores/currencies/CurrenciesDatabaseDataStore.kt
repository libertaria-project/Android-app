package com.stocksexchange.android.datastores.currencies

import com.stocksexchange.android.api.model.Currency
import com.stocksexchange.android.database.tables.CurrenciesTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation

class CurrenciesDatabaseDataStore(
    private val currenciesTable: CurrenciesTable
) : CurrenciesDataStore {


    override suspend fun save(currencies: List<Currency>) {
        executeBackgroundOperation {
            currenciesTable.save(currencies)
        }
    }


    override suspend fun deleteAll() {
        executeBackgroundOperation {
            currenciesTable.deleteAll()
        }
    }


    override suspend fun search(query: String): Result<List<Currency>> {
        return performBackgroundOperation {
            currenciesTable.search(query)
        }
    }


    override suspend fun getAll(): Result<List<Currency>> {
        return performBackgroundOperation {
            currenciesTable.getAll()
        }
    }


}
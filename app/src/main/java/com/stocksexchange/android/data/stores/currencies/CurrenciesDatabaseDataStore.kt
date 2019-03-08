package com.stocksexchange.android.data.stores.currencies

import com.stocksexchange.android.api.model.newapi.Currency
import com.stocksexchange.android.database.tables.new.CurrenciesTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

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


    override suspend fun getAll(): Result<List<Currency>> {
        return performBackgroundOperation {
            currenciesTable.getAll()
        }
    }


}
package com.stocksexchange.android.datastores.favoritecurrencymarkets

import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.database.tables.FavoriteCurrencyMarketsTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation

class FavoriteCurrencyMarketsDatabaseDataStore(
    private val favoriteCurrencyMarketsTable: FavoriteCurrencyMarketsTable
) : FavoriteCurrencyMarketsDataStore {


    override suspend fun favorite(currencyMarket: CurrencyMarket) {
        executeBackgroundOperation {
            favoriteCurrencyMarketsTable.favorite(currencyMarket)
        }
    }


    override suspend fun unfavorite(currencyMarket: CurrencyMarket) {
        executeBackgroundOperation {
            favoriteCurrencyMarketsTable.unfavorite(currencyMarket)
        }
    }


    override suspend fun getAll(): Result<List<Long>> {
        return performBackgroundOperation {
            favoriteCurrencyMarketsTable.getAll()
        }
    }


}
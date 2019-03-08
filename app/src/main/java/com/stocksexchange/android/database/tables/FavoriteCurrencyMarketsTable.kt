package com.stocksexchange.android.database.tables

import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.database.daos.FavoriteCurrencyMarketDao
import com.stocksexchange.android.mappings.mapToDatabaseFavoriteCurrencyMarket
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.model.Result
import org.koin.standalone.inject

/**
 * A database table holding favorite currency markets
 * related functionality.
 */
object FavoriteCurrencyMarketsTable : BaseTable() {


    private val mFavoriteCurrencyMarketDao: FavoriteCurrencyMarketDao by inject()




    /**
     * Saves a currency market as favorite within the database.
     *
     * @param currencyMarket The currency market to save
     */
    fun favorite(currencyMarket: CurrencyMarket) {
        mFavoriteCurrencyMarketDao.insert(currencyMarket.mapToDatabaseFavoriteCurrencyMarket())
    }


    /**
     * Deletes a currency market from favorites within the database.
     *
     * @param currencyMarket The currency market to delete
     */
    fun unfavorite(currencyMarket: CurrencyMarket) {
        mFavoriteCurrencyMarketDao.delete(currencyMarket.id)
    }


    /**
     * Retrieves all favorite currency markets' IDs from
     * the database.
     *
     * @return The currency markets' IDs or [NotFoundException]
     * error packaged inside an instance of [Result] class
     */
    fun getAll(): Result<List<Long>> {
        return mFavoriteCurrencyMarketDao.getAll()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}
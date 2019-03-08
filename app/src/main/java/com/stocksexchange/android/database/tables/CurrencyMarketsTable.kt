package com.stocksexchange.android.database.tables

import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.database.daos.CurrencyMarketDao
import com.stocksexchange.android.mappings.mapToCurrencyMarketList
import com.stocksexchange.android.mappings.mapToDatabaseCurrencyMarket
import com.stocksexchange.android.mappings.mapToDatabaseCurrencyMarketList
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.model.Result
import org.koin.standalone.inject

/**
 * A database table holding currency markets related functionality.
 */
object CurrencyMarketsTable : BaseTable() {


    private val mCurrencyMarketDao: CurrencyMarketDao by inject()




    /**
     * Saves a currency market within the database.
     *
     * @param currencyMarket The currency market to save
     */
    fun save(currencyMarket: CurrencyMarket) {
        mCurrencyMarketDao.insert(currencyMarket.mapToDatabaseCurrencyMarket())
    }


    /**
     * Saves currency markets within the database.
     *
     * @param currencyMarkets The currency markets to save
     */
    fun save(currencyMarkets: List<CurrencyMarket>) {
        mCurrencyMarketDao.insert(currencyMarkets.mapToDatabaseCurrencyMarketList())
    }


    /**
     * Deletes all currency markets within the database.
     */
    fun deleteAll() {
        mCurrencyMarketDao.deleteAll()
    }


    /**
     * Searches currency markets within the database based on the
     * passed query.
     *
     * @param query The query to perform a search on
     *
     * @return The currency markets or [NotFoundException]
     * error packaged inside an instance of [Result] class
     */
    fun search(query: String): Result<List<CurrencyMarket>> {
        return mCurrencyMarketDao.search(query)
            .mapToCurrencyMarketList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


    /**
     * Retrieves all currency markets from the database.
     *
     * @return The currency markets or [NotFoundException]
     * error packaged inside an instance of [Result] class
     */
    fun getAll(): Result<List<CurrencyMarket>> {
        return mCurrencyMarketDao.getAll().
            mapToCurrencyMarketList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}
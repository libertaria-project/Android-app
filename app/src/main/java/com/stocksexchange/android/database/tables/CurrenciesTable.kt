package com.stocksexchange.android.database.tables

import com.stocksexchange.android.api.model.Currency
import com.stocksexchange.android.database.daos.CurrencyDao
import com.stocksexchange.android.mappings.mapToCurrencyList
import com.stocksexchange.android.mappings.mapToDatabaseCurrencyList
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.model.Result
import org.koin.standalone.inject

/**
 * A database table holding currencies related functionality.
 */
object CurrenciesTable : BaseTable() {


    private val mCurrencyDao: CurrencyDao by inject()




    /**
     * Saves currencies within the database.
     *
     * @param currencies The currencies to save
     */
    fun save(currencies: List<Currency>) {
        mCurrencyDao.insert(currencies.mapToDatabaseCurrencyList())
    }


    /**
     * Deletes all coins within the database.
     */
    fun deleteAll() {
        mCurrencyDao.deleteAll()
    }


    /**
     * Searches currencies within the database based on the
     * passed query.
     *
     * @param query The query to perform a search on
     *
     * @return The currencies or [NotFoundException] error
     * packaged inside an instance of [Result] class
     */
    fun search(query: String): Result<List<Currency>> {
        return mCurrencyDao.search(query)
            .mapToCurrencyList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


    /**
     * Retrieves all currencies from the database.
     *
     * @return The currencies or [NotFoundException] error
     * packaged inside an instance of [Result] class
     */
    fun getAll(): Result<List<Currency>> {
        return mCurrencyDao.getAll()
            .mapToCurrencyList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}
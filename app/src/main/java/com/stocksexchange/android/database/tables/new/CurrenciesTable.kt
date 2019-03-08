package com.stocksexchange.android.database.tables.new

import com.stocksexchange.android.api.model.newapi.Currency
import com.stocksexchange.android.database.daos.newd.CurrencyDao
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.mappings.new.mapToCurrencyList
import com.stocksexchange.android.mappings.new.mapToDatabaseCurrencyList
import com.stocksexchange.android.model.Result
import org.koin.standalone.inject

/**
 * A database table holding currencies related functionality.
 */
object CurrenciesTable : BaseTable() {


    private val mCurrencyDao: CurrencyDao by inject()




    /**
     * Saves currencies inside the database.
     *
     * @param currencies The currencies to save
     */
    fun save(currencies: List<Currency>) {
        mCurrencyDao.insert(currencies.mapToDatabaseCurrencyList())
    }


    /**
     * Deletes all currencies from the database.
     */
    fun deleteAll() {
        mCurrencyDao.deleteAll()
    }


    /**
     * Retrieves all currencies from the database.
     *
     * @return The currencies or [NotFoundException] error packaged
     * inside an instance of [Result] class
     */
    fun getAll(): Result<List<Currency>> {
        return mCurrencyDao.getAll()
            .mapToCurrencyList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}
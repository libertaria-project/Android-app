package com.stocksexchange.android.database.tables.new

import com.stocksexchange.android.api.model.newapi.CurrencyPair
import com.stocksexchange.android.database.daos.newd.CurrencyPairDao
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.mappings.new.mapToCurrencyPairList
import com.stocksexchange.android.mappings.new.mapToDatabaseCurrencyPairList
import com.stocksexchange.android.model.Result
import org.koin.standalone.inject

/**
 * A database table holding currency pairs related functionality.
 */
object CurrencyPairsTable : BaseTable() {


    private val mCurrencyPairsDao: CurrencyPairDao by inject()




    /**
     * Saves currency pairs inside the database.
     *
     * @param currencyPairs The currency pairs to save
     */
    fun save(currencyPairs: List<CurrencyPair>) {
        mCurrencyPairsDao.insert(currencyPairs.mapToDatabaseCurrencyPairList())
    }


    /**
     * Deletes all currency pairs from the database.
     */
    fun deleteAll() {
        mCurrencyPairsDao.deleteAll()
    }


    /**
     * Retrieves all currency pairs from the database.
     *
     * @return The currency pairs or [NotFoundException] error packaged
     * inside an instance of [Result] class
     */
    fun getAll(): Result<List<CurrencyPair>> {
        return mCurrencyPairsDao.getAll()
            .mapToCurrencyPairList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}
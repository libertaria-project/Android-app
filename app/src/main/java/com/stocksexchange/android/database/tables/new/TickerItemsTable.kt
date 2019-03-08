package com.stocksexchange.android.database.tables.new

import com.stocksexchange.android.api.model.newapi.TickerItem
import com.stocksexchange.android.database.daos.newd.TickerItemDao
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.mappings.new.mapToDatabaseTickerItemList
import com.stocksexchange.android.mappings.new.mapToTickerItemList
import com.stocksexchange.android.model.Result
import org.koin.standalone.inject

/**
 * A database table holding ticker items related functionality.
 */
object TickerItemsTable : BaseTable() {


    private val mTickerItemDao: TickerItemDao by inject()




    /**
     * Saves ticker items inside the database.
     *
     * @param tickerItems The ticker items to save
     */
    fun save(tickerItems: List<TickerItem>) {
        mTickerItemDao.insert(tickerItems.mapToDatabaseTickerItemList())
    }


    /**
     * Deletes all ticker items from the database.
     */
    fun deleteAll() {
        mTickerItemDao.deleteAll()
    }


    /**
     * Retrieves all ticker items from the database.
     *
     * @return The ticker items or [NotFoundException] error packaged
     * inside an instance of [Result] class
     */
    fun getAll(): Result<List<TickerItem>> {
        return mTickerItemDao.getAll()
            .mapToTickerItemList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}
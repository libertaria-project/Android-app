package com.stocksexchange.android.database.daos.newd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stocksexchange.android.database.model.newd.DatabaseOrderbook

/**
 * A Room data access object used for interacting
 * with a [DatabaseOrderbook] model class.
 */
@Dao
interface OrderbookDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(orderbook: DatabaseOrderbook)


    @Query(
        "SELECT * FROM ${DatabaseOrderbook.TABLE_NAME} " +
        "WHERE ${DatabaseOrderbook.CURRENCY_PAIR_ID} = :currencyPairId"
    )
    fun get(currencyPairId: Int): DatabaseOrderbook?


}
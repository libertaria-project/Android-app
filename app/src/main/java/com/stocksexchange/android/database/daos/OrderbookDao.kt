package com.stocksexchange.android.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stocksexchange.android.database.model.DatabaseOrderbook

/**
 * A Room data across object used for interacting
 * with [DatabaseOrderbook] model class.
 */
@Dao
interface OrderbookDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(orderbook: DatabaseOrderbook)


    @Query(
        "SELECT * FROM ${DatabaseOrderbook.TABLE_NAME} WHERE " +
        "${DatabaseOrderbook.MARKET_NAME} = :marketName"
    )
    fun get(marketName: String): DatabaseOrderbook?


}
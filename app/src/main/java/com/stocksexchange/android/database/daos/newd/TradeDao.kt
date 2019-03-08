package com.stocksexchange.android.database.daos.newd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stocksexchange.android.database.model.newd.DatabaseTrade

/**
 * A Room data access object used for interacting
 * with a [DatabaseTrade] model class.
 */
@Dao
interface TradeDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trade: DatabaseTrade)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trades: List<DatabaseTrade>)


    @Query(
        "DELETE FROM ${DatabaseTrade.TABLE_NAME} " +
        "WHERE ${DatabaseTrade.CURRENCY_PAIR_ID} = :currencyPairId"
    )
    fun delete(currencyPairId: Int)


    @Query(
        "SELECT * FROM ${DatabaseTrade.TABLE_NAME} " +
        "WHERE ${DatabaseTrade.CURRENCY_PAIR_ID} = :currencyPairId " +
        "ORDER BY ${DatabaseTrade.TIMESTAMP} ASC " +
        "LIMIT :count"
    )
    fun getAsc(currencyPairId: Int, count: Int): List<DatabaseTrade>


    @Query(
        "SELECT * FROM ${DatabaseTrade.TABLE_NAME} " +
        "WHERE ${DatabaseTrade.CURRENCY_PAIR_ID} = :currencyPairId " +
        "ORDER BY ${DatabaseTrade.TIMESTAMP} DESC " +
        "LIMIT :count"
    )
    fun getDesc(currencyPairId: Int, count: Int): List<DatabaseTrade>


}
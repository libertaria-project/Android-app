package com.stocksexchange.android.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stocksexchange.android.database.model.DatabaseTrade

/**
 * A Room data access object used for interacting
 * with [DatabaseTrade] model class.
 */
@Dao
interface TradeDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(order: DatabaseTrade)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(orders: List<DatabaseTrade>)


    @Query(
        "DELETE FROM ${DatabaseTrade.TABLE_NAME} " +
        "WHERE ${DatabaseTrade.MARKET_NAME} = :marketName"
    )
    fun delete(marketName: String)


    @Query(
        "SELECT * FROM ${DatabaseTrade.TABLE_NAME} " +
        "WHERE ${DatabaseTrade.MARKET_NAME} = :marketName " +
        "ORDER BY ${DatabaseTrade.TIMESTAMP} ASC " +
        "LIMIT :count"
    )
    fun getAsc(marketName: String, count: Int): List<DatabaseTrade>


    @Query(
        "SELECT * FROM ${DatabaseTrade.TABLE_NAME} " +
        "WHERE ${DatabaseTrade.MARKET_NAME} = :marketName " +
        "ORDER BY ${DatabaseTrade.TIMESTAMP} DESC " +
        "LIMIT :count"
    )
    fun getDesc(marketName: String, count: Int): List<DatabaseTrade>


}
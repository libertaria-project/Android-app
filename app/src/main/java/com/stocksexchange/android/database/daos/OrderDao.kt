package com.stocksexchange.android.database.daos

import androidx.room.*
import com.stocksexchange.android.Constants
import com.stocksexchange.android.database.model.DatabaseOrder

/**
 * A Room data access object used for interacting
 * with [DatabaseOrder] model class.
 */
@Dao
interface OrderDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(order: DatabaseOrder)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(orders: List<DatabaseOrder>)


    @Delete
    fun delete(order: DatabaseOrder)


    @Query(
        "DELETE FROM ${DatabaseOrder.TABLE_NAME} " +
        "WHERE ${DatabaseOrder.TYPE} = :type"
    )
    fun delete(type: String)


    @Query("DELETE FROM ${DatabaseOrder.TABLE_NAME}")
    fun deleteAll()


    @Query(
        "SELECT * FROM ${DatabaseOrder.TABLE_NAME} " +
        "WHERE ${DatabaseOrder.TYPE} = :type AND (" +
        "(LOWER(${DatabaseOrder.BASE_CURRENCY_SYMBOL}) = :query) OR (LOWER(${DatabaseOrder.QUOTE_CURRENCY_SYMBOL}) = :query) OR (" +
        "REPLACE(LOWER(${DatabaseOrder.MARKET_NAME}), '${Constants.CURRENCY_MARKET_SEPARATOR}', ' / ') LIKE (:query || '%'))) " +
        "ORDER BY ${DatabaseOrder.TIMESTAMP} ASC " +
        "LIMIT :count"
    )
    fun searchAsc(query: String, type: String, count: Int): List<DatabaseOrder>


    @Query(
        "SELECT * FROM ${DatabaseOrder.TABLE_NAME} " +
        "WHERE ${DatabaseOrder.TYPE} = :type AND (" +
        "(LOWER(${DatabaseOrder.BASE_CURRENCY_SYMBOL}) = :query) OR (LOWER(${DatabaseOrder.QUOTE_CURRENCY_SYMBOL}) = :query) OR (" +
        "REPLACE(LOWER(${DatabaseOrder.MARKET_NAME}), '${Constants.CURRENCY_MARKET_SEPARATOR}', ' / ') LIKE (:query || '%'))) " +
        "ORDER BY ${DatabaseOrder.TIMESTAMP} DESC " +
        "LIMIT :count"
    )
    fun searchDesc(query: String, type: String, count: Int): List<DatabaseOrder>


    @Query(
        "SELECT * FROM ${DatabaseOrder.TABLE_NAME} " +
        "WHERE ${DatabaseOrder.TYPE} = :type " +
        "ORDER BY ${DatabaseOrder.TIMESTAMP} ASC " +
        "LIMIT :count"
    )
    fun getAsc(type: String, count: Int): List<DatabaseOrder>


    @Query(
        "SELECT * FROM ${DatabaseOrder.TABLE_NAME} " +
        "WHERE ${DatabaseOrder.TYPE} = :type " +
        "ORDER BY ${DatabaseOrder.TIMESTAMP} DESC " +
        "LIMIT :count"
    )
    fun getDesc(type: String, count: Int): List<DatabaseOrder>


    @Query(
        "SELECT * FROM ${DatabaseOrder.TABLE_NAME} " +
        "WHERE ${DatabaseOrder.MARKET_NAME} = :marketName AND ${DatabaseOrder.TYPE} = :type " +
        "ORDER BY ${DatabaseOrder.TIMESTAMP} ASC " +
        "LIMIT :count"
    )
    fun getAsc(marketName: String, type: String, count: Int): List<DatabaseOrder>


    @Query(
        "SELECT * FROM ${DatabaseOrder.TABLE_NAME} " +
        "WHERE ${DatabaseOrder.MARKET_NAME} = :marketName AND ${DatabaseOrder.TYPE} = :type " +
        "ORDER BY ${DatabaseOrder.TIMESTAMP} DESC " +
        "LIMIT :count"
    )
    fun getDesc(marketName: String, type: String, count: Int): List<DatabaseOrder>


}
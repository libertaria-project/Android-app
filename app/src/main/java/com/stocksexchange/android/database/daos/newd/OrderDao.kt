package com.stocksexchange.android.database.daos.newd

import androidx.room.*
import com.stocksexchange.android.database.model.newd.DatabaseOrder

/**
 * A Room data access object used for interacting
 * with a [DatabaseOrder] model class.
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
        "WHERE ${DatabaseOrder.STATUS_STR} = :status"
    )
    fun delete(status: String)


    @Query("DELETE FROM ${DatabaseOrder.TABLE_NAME}")
    fun deleteAll()


    @Query(
        "SELECT * FROM ${DatabaseOrder.TABLE_NAME} " +
        "WHERE ${DatabaseOrder.STATUS_STR} = :status " +
        "ORDER BY ${DatabaseOrder.TIMESTAMP} ASC " +
        "LIMIT :offset, :limit"
    )
    fun getAsc(status: String, limit: Int, offset: Int): List<DatabaseOrder>


    @Query(
        "SELECT * FROM ${DatabaseOrder.TABLE_NAME} " +
        "WHERE ${DatabaseOrder.STATUS_STR} = :status " +
        "ORDER BY ${DatabaseOrder.TIMESTAMP} DESC " +
        "LIMIT :offset, :limit"
    )
    fun getDesc(status: String, limit: Int, offset: Int): List<DatabaseOrder>



}
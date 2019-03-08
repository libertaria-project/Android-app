package com.stocksexchange.android.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stocksexchange.android.database.model.DatabaseTransaction

/**
 * A Room data access object used for interacting
 * with [DatabaseTransaction] model class.
 */
@Dao
interface TransactionDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transactions: List<DatabaseTransaction>)


    @Query(
        "DELETE FROM ${DatabaseTransaction.TABLE_NAME} " +
        "WHERE ${DatabaseTransaction.TYPE} = :type"
    )
    fun delete(type: String)


    @Query("DELETE FROM ${DatabaseTransaction.TABLE_NAME}")
    fun deleteAll()


    @Query(
        "SELECT * FROM ${DatabaseTransaction.TABLE_NAME} " +
        "WHERE ${DatabaseTransaction.TYPE} = :type AND " +
        "LOWER(${DatabaseTransaction.CURRENCY}) LIKE (:currency || '%') " +
        "ORDER BY ${DatabaseTransaction.TIMESTAMP} ASC " +
        "LIMIT :count"
    )
    fun searchAsc(type: String, currency: String, count: Int): List<DatabaseTransaction>


    @Query(
        "SELECT * FROM ${DatabaseTransaction.TABLE_NAME} " +
        "WHERE ${DatabaseTransaction.TYPE} = :type AND " +
        "LOWER(${DatabaseTransaction.CURRENCY}) LIKE (:currency || '%') " +
        "ORDER BY ${DatabaseTransaction.TIMESTAMP} DESC " +
        "LIMIT :count"
    )
    fun searchDesc(type: String, currency: String, count: Int): List<DatabaseTransaction>


    @Query(
        "SELECT * FROM ${DatabaseTransaction.TABLE_NAME} " +
        "WHERE ${DatabaseTransaction.TYPE} = :type " +
        "ORDER BY ${DatabaseTransaction.TIMESTAMP} ASC " +
        "LIMIT :count"
    )
    fun getAsc(type: String, count: Int): List<DatabaseTransaction>


    @Query(
        "SELECT * FROM ${DatabaseTransaction.TABLE_NAME} " +
        "WHERE ${DatabaseTransaction.TYPE} = :type " +
        "ORDER BY ${DatabaseTransaction.TIMESTAMP} DESC " +
        "LIMIT :count"
    )
    fun getDesc(type: String, count: Int): List<DatabaseTransaction>


}
package com.stocksexchange.android.database.daos.newd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stocksexchange.android.database.model.newd.DatabaseWithdrawal

/**
 * A Room data access object used for interacting
 * with a [DatabaseWithdrawal] model class.
 */
@Dao
interface WithdrawalDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(withdrawals: List<DatabaseWithdrawal>)


    @Query("DELETE FROM ${DatabaseWithdrawal.TABLE_NAME}")
    fun deleteAll()


    @Query(
        "SELECT * FROM ${DatabaseWithdrawal.TABLE_NAME} " +
        "WHERE ${DatabaseWithdrawal.CURRENCY_CODE} LIKE (:currencyCode || '%') " +
        "ORDER BY ${DatabaseWithdrawal.CREATION_TIMESTAMP} ASC " +
        "LIMIT :offset, :limit"
    )
    fun searchAsc(currencyCode: String, limit: Int, offset: Int): List<DatabaseWithdrawal>


    @Query(
        "SELECT * FROM ${DatabaseWithdrawal.TABLE_NAME} " +
        "WHERE ${DatabaseWithdrawal.CURRENCY_CODE} LIKE (:currencyCode || '%') " +
        "ORDER BY ${DatabaseWithdrawal.CREATION_TIMESTAMP} DESC " +
        "LIMIT :offset, :limit"
    )
    fun searchDesc(currencyCode: String, limit: Int, offset: Int): List<DatabaseWithdrawal>


    @Query(
        "SELECT * FROM ${DatabaseWithdrawal.TABLE_NAME} " +
        "ORDER BY ${DatabaseWithdrawal.CREATION_TIMESTAMP} ASC " +
        "LIMIT :offset, :limit"
    )
    fun getAsc(limit: Int, offset: Int): List<DatabaseWithdrawal>


    @Query(
        "SELECT * FROM ${DatabaseWithdrawal.TABLE_NAME} " +
        "ORDER BY ${DatabaseWithdrawal.CREATION_TIMESTAMP} DESC " +
        "LIMIT :offset, :limit"
    )
    fun getDesc(limit: Int, offset: Int): List<DatabaseWithdrawal>


}
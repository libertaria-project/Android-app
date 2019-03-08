package com.stocksexchange.android.database.daos.newd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stocksexchange.android.database.model.newd.DatabaseDeposit

/**
 * A Room data access object used for interacting
 * with a [DatabaseDeposit] model class.
 */
@Dao
interface DepositDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(deposits: List<DatabaseDeposit>)


    @Query("DELETE FROM ${DatabaseDeposit.TABLE_NAME}")
    fun deleteAll()


    @Query(
        "SELECT * FROM ${DatabaseDeposit.TABLE_NAME} " +
        "WHERE ${DatabaseDeposit.CURRENCY_CODE} LIKE (:currencyCode || '%') " +
        "ORDER BY ${DatabaseDeposit.TIMESTAMP} ASC " +
        "LIMIT :offset, :limit"
    )
    fun searchAsc(currencyCode: String, limit: Int, offset: Int): List<DatabaseDeposit>


    @Query(
        "SELECT * FROM ${DatabaseDeposit.TABLE_NAME} " +
        "WHERE ${DatabaseDeposit.CURRENCY_CODE} LIKE (:currencyCode || '%') " +
        "ORDER BY ${DatabaseDeposit.TIMESTAMP} DESC " +
        "LIMIT :offset, :limit"
    )
    fun searchDesc(currencyCode: String, limit: Int, offset: Int): List<DatabaseDeposit>


    @Query(
        "SELECT * FROM ${DatabaseDeposit.TABLE_NAME} " +
        "ORDER BY ${DatabaseDeposit.TIMESTAMP} ASC " +
        "LIMIT :offset, :limit"
    )
    fun getAsc(limit: Int, offset: Int): List<DatabaseDeposit>


    @Query(
        "SELECT * FROM ${DatabaseDeposit.TABLE_NAME} " +
        "ORDER BY ${DatabaseDeposit.TIMESTAMP} DESC " +
        "LIMIT :offset, :limit"
    )
    fun getDesc(limit: Int, offset: Int): List<DatabaseDeposit>


}
package com.stocksexchange.android.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stocksexchange.android.database.model.DatabaseDeposit

/**
 * A Room data access object used for interacting
 * with [DatabaseDeposit] model class.
 */
@Dao
interface DepositDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(deposit: DatabaseDeposit)


    @Query("DELETE FROM ${DatabaseDeposit.TABLE_NAME}")
    fun deleteAll()


    @Query(
        "SELECT * FROM ${DatabaseDeposit.TABLE_NAME} " +
        "WHERE ${DatabaseDeposit.CURRENCY} = :currency"
    )
    fun get(currency: String): DatabaseDeposit?


}
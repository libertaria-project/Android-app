package com.stocksexchange.android.database.daos.newd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stocksexchange.android.database.model.newd.DatabaseCurrencyPair

/**
 * A Room data access object used for interacting
 * with a [DatabaseCurrencyPair] model class.
 */
@Dao
interface CurrencyPairDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencyPairs: List<DatabaseCurrencyPair>)


    @Query("DELETE FROM ${DatabaseCurrencyPair.TABLE_NAME}")
    fun deleteAll()


    @Query("SELECT * FROM ${DatabaseCurrencyPair.TABLE_NAME}")
    fun getAll(): List<DatabaseCurrencyPair>



}
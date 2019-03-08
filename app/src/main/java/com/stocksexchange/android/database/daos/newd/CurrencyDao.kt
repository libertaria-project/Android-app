package com.stocksexchange.android.database.daos.newd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stocksexchange.android.database.model.newd.DatabaseCurrency

/**
 * A Room data access object used for interacting
 * with a [DatabaseCurrency] model class.
 */
@Dao
interface CurrencyDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencies: List<DatabaseCurrency>)


    @Query("DELETE FROM ${DatabaseCurrency.TABLE_NAME}")
    fun deleteAll()


    @Query("SELECT * FROM ${DatabaseCurrency.TABLE_NAME}")
    fun getAll(): List<DatabaseCurrency>


}
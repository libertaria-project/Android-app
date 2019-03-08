package com.stocksexchange.android.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stocksexchange.android.database.model.DatabaseFavoriteCurrencyMarket

/**
 * A Room data access object used for interacting
 * with [DatabaseFavoriteCurrencyMarket] model class.
 */
@Dao
interface FavoriteCurrencyMarketDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencyMarket: DatabaseFavoriteCurrencyMarket)


    @Query(
        "DELETE FROM ${DatabaseFavoriteCurrencyMarket.TABLE_NAME} " +
        "WHERE ${DatabaseFavoriteCurrencyMarket.ID} = :id"
    )
    fun delete(id: Long)


    @Query(
        "SELECT ${DatabaseFavoriteCurrencyMarket.ID} " +
        "FROM ${DatabaseFavoriteCurrencyMarket.TABLE_NAME}"
    )
    fun getAll(): List<Long>


}
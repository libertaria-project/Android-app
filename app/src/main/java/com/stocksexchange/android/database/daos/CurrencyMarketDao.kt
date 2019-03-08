package com.stocksexchange.android.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stocksexchange.android.Constants
import com.stocksexchange.android.database.model.DatabaseCurrencyMarket

/**
 * A Room data access object used for interacting
 * with [DatabaseCurrencyMarket] model class.
 */
@Dao
interface CurrencyMarketDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencyMarket: DatabaseCurrencyMarket)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencyMarkets: List<DatabaseCurrencyMarket>)


    @Query("DELETE FROM ${DatabaseCurrencyMarket.TABLE_NAME}")
    fun deleteAll()


    @Query(
        "SELECT * FROM ${DatabaseCurrencyMarket.TABLE_NAME} " +
        "WHERE LOWER(${DatabaseCurrencyMarket.BASE_CURRENCY_SYMBOL}) = :query OR " +
        "LOWER(${DatabaseCurrencyMarket.QUOTE_CURRENCY_SYMBOL}) = :query OR (" +
        "REPLACE(LOWER(${DatabaseCurrencyMarket.NAME}), '${Constants.CURRENCY_MARKET_SEPARATOR}', ' / ') LIKE (:query || '%')) OR " +
        "LOWER(${DatabaseCurrencyMarket.BASE_CURRENCY_NAME}) LIKE (:query || '%') OR " +
        "LOWER(${DatabaseCurrencyMarket.QUOTE_CURRENCY_NAME}) LIKE (:query || '%')"
    )
    fun search(query: String): List<DatabaseCurrencyMarket>


    @Query("SELECT * FROM ${DatabaseCurrencyMarket.TABLE_NAME}")
    fun getAll(): List<DatabaseCurrencyMarket>


}
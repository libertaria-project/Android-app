package com.stocksexchange.android.database.daos.newd

import androidx.room.*
import com.stocksexchange.android.database.model.newd.DatabaseCandleStick

/**
 * A Room data access object used for interacting
 * with a [DatabaseCandleStick] model class.
 */
@Dao
interface CandleStickDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(candleSticks: List<DatabaseCandleStick>)


    @Query(
        "DELETE FROM ${DatabaseCandleStick.TABLE_NAME} " +
        "WHERE ${DatabaseCandleStick.CURRENCY_PAIR_ID} = :currencyPairId AND " +
        "${DatabaseCandleStick.INTERVAL} = :interval"
    )
    fun delete(currencyPairId: Int, interval: String)


    @Query(
        "SELECT * FROM ${DatabaseCandleStick.TABLE_NAME} " +
        "WHERE ${DatabaseCandleStick.CURRENCY_PAIR_ID} = :currencyPairId AND " +
        "${DatabaseCandleStick.INTERVAL} = :interval " +
        "LIMIT :count"
    )
    fun get(currencyPairId: Int, interval: String, count: Int): List<DatabaseCandleStick>


}
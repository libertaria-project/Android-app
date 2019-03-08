package com.stocksexchange.android.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stocksexchange.android.database.model.DatabasePriceChartData

/**
 * A Room data access object used for interacting
 * with [DatabasePriceChartData] model class.
 */
@Dao
interface PriceChartDataDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(priceChartData: DatabasePriceChartData)


    @Query(
        "SELECT * FROM ${DatabasePriceChartData.TABLE_NAME} WHERE " +
        "${DatabasePriceChartData.MARKET_NAME} = :marketName AND " +
        "${DatabasePriceChartData.INTERVAL} = :interval AND " +
        "`${DatabasePriceChartData.ORDER}` = :order AND " +
        "${DatabasePriceChartData.COUNT} = :count AND " +
        "${DatabasePriceChartData.CURRENT_PAGE} = :currentPage"
    )
    fun get(
        marketName: String,
        interval: String,
        order: String,
        count: Int,
        currentPage: Int
    ): DatabasePriceChartData?


}
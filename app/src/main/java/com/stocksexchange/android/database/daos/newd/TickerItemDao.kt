package com.stocksexchange.android.database.daos.newd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stocksexchange.android.database.model.newd.DatabaseTickerItem

/**
 * A Room data access object used for interacting
 * with a [DatabaseTickerItem] model class.
 */
@Dao
interface TickerItemDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tickerItems: List<DatabaseTickerItem>)


    @Query("DELETE FROM ${DatabaseTickerItem.TABLE_NAME}")
    fun deleteAll()


    @Query("SELECT * FROM ${DatabaseTickerItem.TABLE_NAME}")
    fun getAll(): List<DatabaseTickerItem>


}
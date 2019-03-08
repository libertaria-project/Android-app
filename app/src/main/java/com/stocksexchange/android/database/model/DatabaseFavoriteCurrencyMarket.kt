package com.stocksexchange.android.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stocksexchange.android.database.model.DatabaseFavoriteCurrencyMarket.Companion.TABLE_NAME

/**
 * A Room database model class representing
 * user's favorite currency markets.
 */
@Entity(tableName = TABLE_NAME)
data class DatabaseFavoriteCurrencyMarket(
    @PrimaryKey @ColumnInfo(name = ID) var id: Long
) {

    companion object {

        const val TABLE_NAME = "favorite_currency_markets"

        const val ID = "id"

    }


    constructor(): this(-1L)

}
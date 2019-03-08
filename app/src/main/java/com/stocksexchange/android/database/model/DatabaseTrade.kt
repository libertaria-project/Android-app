package com.stocksexchange.android.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stocksexchange.android.api.model.Trade
import com.stocksexchange.android.database.model.DatabaseTrade.Companion.TABLE_NAME

/**
 * A Room database model class of [Trade] class.
 */
@Entity(tableName = TABLE_NAME)
data class DatabaseTrade(
    @PrimaryKey @ColumnInfo(name = ID) val id: Long,
    @ColumnInfo(name = MARKET_NAME) var marketName: String,
    @ColumnInfo(name = TYPE) val type: String,
    @ColumnInfo(name = TIMESTAMP) val timestamp: Long,
    @ColumnInfo(name = PRICE) val price: Double,
    @ColumnInfo(name = AMOUNT) val amount: Double
) {

    companion object {

        const val TABLE_NAME = "trades"

        const val ID = "id"
        const val MARKET_NAME = "market_name"
        const val TYPE = "type"
        const val TIMESTAMP = "timestamp"
        const val PRICE = "price"
        const val AMOUNT = "amount"

    }


    constructor(): this(-1L, "", "", 0L, -1.0, -1.0)

}
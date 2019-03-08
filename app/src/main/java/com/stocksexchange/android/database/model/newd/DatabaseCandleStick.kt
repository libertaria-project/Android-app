package com.stocksexchange.android.database.model.newd

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.stocksexchange.android.api.model.newapi.CandleStick
import com.stocksexchange.android.database.model.newd.DatabaseCandleStick.Companion.CURRENCY_PAIR_ID
import com.stocksexchange.android.database.model.newd.DatabaseCandleStick.Companion.INTERVAL
import com.stocksexchange.android.database.model.newd.DatabaseCandleStick.Companion.TABLE_NAME
import com.stocksexchange.android.database.model.newd.DatabaseCandleStick.Companion.TIMESTAMP

/**
 * A Room database model class for [CandleStick] class.
 */
@Entity(tableName = TABLE_NAME, primaryKeys = [CURRENCY_PAIR_ID, INTERVAL, TIMESTAMP])
data class DatabaseCandleStick(
    @ColumnInfo(name = CURRENCY_PAIR_ID) var currencyPairId: Int,
    @ColumnInfo(name = INTERVAL) var interval: String,
    @ColumnInfo(name = OPEN_PRICE) var openPrice: Double,
    @ColumnInfo(name = HIGH_PRICE) var highPrice: Double,
    @ColumnInfo(name = LOW_PRICE) var lowPrice: Double,
    @ColumnInfo(name = CLOSE_PRICE) var closePrice: Double,
    @ColumnInfo(name = VOLUME) var volume: Double,
    @ColumnInfo(name = TIMESTAMP) var timestamp: Long
) {

    companion object {

        const val TABLE_NAME = "candle_sticks"

        const val CURRENCY_PAIR_ID = "currency_pair_id"
        const val INTERVAL = "interval"
        const val OPEN_PRICE = "open_price"
        const val HIGH_PRICE = "high_price"
        const val LOW_PRICE = "low_price"
        const val CLOSE_PRICE = "close_price"
        const val VOLUME = "volume"
        const val TIMESTAMP = "timestamp"

    }


    constructor(): this(
        currencyPairId = -1,
        interval = "",
        openPrice = -1.0,
        highPrice = -1.0,
        lowPrice = -1.0,
        closePrice = -1.0,
        volume = -1.0,
        timestamp = -1L
    )

}
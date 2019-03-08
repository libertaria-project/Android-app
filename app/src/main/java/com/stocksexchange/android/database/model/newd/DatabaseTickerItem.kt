package com.stocksexchange.android.database.model.newd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stocksexchange.android.api.model.newapi.TickerItem
import com.stocksexchange.android.database.model.newd.DatabaseTickerItem.Companion.TABLE_NAME

/**
 * A Room database model for [TickerItem] class.
 */
@Entity(tableName = TABLE_NAME)
data class DatabaseTickerItem(
    @PrimaryKey @ColumnInfo(name = ID) var id: Int,
    @ColumnInfo(name = BASE_CURRENCY_CODE) var baseCurrencyCode: String,
    @ColumnInfo(name = BASE_CURRENCY_NAME) var baseCurrencyName: String,
    @ColumnInfo(name = QUOTE_CURRENCY_CODE) var quoteCurrencyCode: String,
    @ColumnInfo(name = QUOTE_CURRENCY_NAME) var quoteCurrencyName: String,
    @ColumnInfo(name = SYMBOL) var symbol: String,
    @ColumnInfo(name = ASK_PRICE) var askPrice: Double,
    @ColumnInfo(name = BID_PRICE) var bidPrice: Double,
    @ColumnInfo(name = LAST_PRICE) var lastPrice: Double?,
    @ColumnInfo(name = OPEN_PRICE) var openPrice: Double?,
    @ColumnInfo(name = LOW_PRICE) var lowPrice: Double?,
    @ColumnInfo(name = HIGH_PRICE) var highPrice: Double?,
    @ColumnInfo(name = VOLUME) var volume: Double?,
    @ColumnInfo(name = VOLUME_QUOTE) var volumeQuote: Double?,
    @ColumnInfo(name = TIMESTAMP) var timestamp: Long
) {


    companion object {

        const val TABLE_NAME = "ticker_items"

        const val ID = "id"
        const val BASE_CURRENCY_CODE = "base_currency_code"
        const val BASE_CURRENCY_NAME = "base_currency_name"
        const val QUOTE_CURRENCY_CODE = "quote_currency_code"
        const val QUOTE_CURRENCY_NAME = "quote_currency_name"
        const val SYMBOL = "symbol"
        const val ASK_PRICE = "ask_price"
        const val BID_PRICE = "bid_price"
        const val LAST_PRICE = "last_price"
        const val OPEN_PRICE = "open_price"
        const val LOW_PRICE = "low_price"
        const val HIGH_PRICE = "high_price"
        const val VOLUME = "volume"
        const val VOLUME_QUOTE = "volume_quote"
        const val TIMESTAMP = "timestamp"

    }


    constructor(): this(
        id = -1,
        baseCurrencyCode = "",
        baseCurrencyName = "",
        quoteCurrencyCode = "",
        quoteCurrencyName = "",
        symbol = "",
        askPrice = -1.0,
        bidPrice = -1.0,
        lastPrice = null,
        openPrice = null,
        lowPrice = null,
        highPrice = null,
        volume = null,
        volumeQuote = null,
        timestamp = -1L
    )

}
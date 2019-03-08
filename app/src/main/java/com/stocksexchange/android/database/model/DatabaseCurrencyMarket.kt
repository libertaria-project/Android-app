package com.stocksexchange.android.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.database.model.DatabaseCurrencyMarket.Companion.TABLE_NAME

/**
 * A Room database model class of [CurrencyMarket] class.
 */
@Entity(tableName = TABLE_NAME)
data class DatabaseCurrencyMarket(
    @PrimaryKey @ColumnInfo(name = ID) var id: Long,
    @ColumnInfo(name = NAME) var name: String,
    @ColumnInfo(name = BASE_CURRENCY_NAME) var baseCurrencyName: String,
    @ColumnInfo(name = QUOTE_CURRENCY_NAME) var quoteCurrencyName: String,
    @ColumnInfo(name = BASE_CURRENCY_SYMBOL) var baseCurrencySymbol: String,
    @ColumnInfo(name = QUOTE_CURRENCY_SYMBOL) var quoteCurrencySymbol: String,
    @ColumnInfo(name = MIN_ORDER_AMOUNT) var minOrderAmount: Double,
    @ColumnInfo(name = DAILY_MIN_PRICE) var dailyMinPrice: Double,
    @ColumnInfo(name = DAILY_MAX_PRICE) var dailyMaxPrice: Double,
    @ColumnInfo(name = LAST_PRICE) var lastPrice: Double,
    @ColumnInfo(name = LAST_PRICE_DAY_AGO) var lastPriceDayAgo: Double,
    @ColumnInfo(name = DAILY_PRICE_CHANGE) var dailyPriceChange: Double,
    @ColumnInfo(name = DAILY_VOLUME) var dailyVolume: Double,
    @ColumnInfo(name = VOLUME) var volume: Double,
    @ColumnInfo(name = BUY_FEE_PERCENT) var buyFeePercent: Double,
    @ColumnInfo(name = SELL_FEE_PERCENT) var sellFeePercent: Double,
    @ColumnInfo(name = IS_ACTIVE) var isActive: Boolean
) {

    companion object {

        const val TABLE_NAME = "currency_markets"

        const val ID = "id"
        const val NAME = "name"
        const val BASE_CURRENCY_NAME = "base_currency_name"
        const val QUOTE_CURRENCY_NAME = "quote_currency_name"
        const val BASE_CURRENCY_SYMBOL = "base_currency_symbol"
        const val QUOTE_CURRENCY_SYMBOL = "quote_currency_symbol"
        const val MIN_ORDER_AMOUNT = "min_order_amount"
        const val DAILY_MIN_PRICE = "daily_min_price"
        const val DAILY_MAX_PRICE = "daily_max_price"
        const val LAST_PRICE = "last_price"
        const val LAST_PRICE_DAY_AGO = "last_price_day_ago"
        const val DAILY_PRICE_CHANGE = "daily_price_change"
        const val DAILY_VOLUME = "daily_volume"
        const val VOLUME = "volume"
        const val BUY_FEE_PERCENT = "buy_fee_percent"
        const val SELL_FEE_PERCENT = "sell_fee_percent"
        const val IS_ACTIVE = "is_active"

    }


    constructor(): this(-1L, "", "", "", "", "", -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, true)

}
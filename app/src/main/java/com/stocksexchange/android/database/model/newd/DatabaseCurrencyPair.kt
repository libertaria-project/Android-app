package com.stocksexchange.android.database.model.newd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stocksexchange.android.api.model.newapi.CurrencyPair
import com.stocksexchange.android.database.model.newd.DatabaseCurrencyPair.Companion.TABLE_NAME

/**
 * A Room database model for [CurrencyPair] class.
 */
@Entity(tableName = TABLE_NAME)
data class DatabaseCurrencyPair(
    @PrimaryKey @ColumnInfo(name = ID) var id: Int,
    @ColumnInfo(name = BASE_CURRENCY_ID) var baseCurrencyId: Int,
    @ColumnInfo(name = BASE_CURRENCY_CODE) var baseCurrencyCode: String,
    @ColumnInfo(name = BASE_CURRENCY_NAME) var baseCurrencyName: String,
    @ColumnInfo(name = QUOTE_CURRENCY_ID) var quoteCurrencyId: Int,
    @ColumnInfo(name = QUOTE_CURRENCY_CODE) var quoteCurrencyCode: String,
    @ColumnInfo(name = QUOTE_CURRENCY_NAME) var quoteCurrencyName: String,
    @ColumnInfo(name = SYMBOL) var symbol: String,
    @ColumnInfo(name = GROUP_NAME) var groupName: String,
    @ColumnInfo(name = GROUP_ID) var groupId: Int,
    @ColumnInfo(name = MIN_ORDER_AMOUNT) var minOrderAmount: Double,
    @ColumnInfo(name = MIN_BUY_PRICE) var minBuyPrice: Double,
    @ColumnInfo(name = MIN_SELL_RRICE) var minSellPrice: Double,
    @ColumnInfo(name = BUY_FEE_IN_PERCENTAGE) var buyFeeInPercentage: Double,
    @ColumnInfo(name = SELL_FEE_IN_PERCENTAGE) var sellFeeInPercentage: Double,
    @ColumnInfo(name = IS_ACTIVE) var isActive: Boolean,
    @ColumnInfo(name = IS_DELISTED) var isDelisted: Boolean,
    @ColumnInfo(name = MESSAGE) var message: String?,
    @ColumnInfo(name = BASE_CURRENCY_PRECISION) var baseCurrencyPrecision: Int,
    @ColumnInfo(name = QUOTE_CURRENCY_PRECISION) var quoteCurrencyPrecision: Int
) {

    companion object {

        const val TABLE_NAME = "currency_pairs"

        const val ID = "id"
        const val BASE_CURRENCY_ID = "base_currency_id"
        const val BASE_CURRENCY_CODE = "base_currency_code"
        const val BASE_CURRENCY_NAME = "base_currency_name"
        const val QUOTE_CURRENCY_ID = "quote_currency_id"
        const val QUOTE_CURRENCY_CODE = "quote_currency_code"
        const val QUOTE_CURRENCY_NAME = "quote_currency_name"
        const val SYMBOL = "symbol"
        const val GROUP_NAME = "group_name"
        const val GROUP_ID = "group_id"
        const val MIN_ORDER_AMOUNT = "min_order_amount"
        const val MIN_BUY_PRICE = "min_buy_price"
        const val MIN_SELL_RRICE = "min_sell_price"
        const val BUY_FEE_IN_PERCENTAGE = "buy_fee_in_percentage"
        const val SELL_FEE_IN_PERCENTAGE = "sell_fee_in_percentage"
        const val IS_ACTIVE = "is_active"
        const val IS_DELISTED = "is_delisted"
        const val MESSAGE = "message"
        const val BASE_CURRENCY_PRECISION = "base_currency_precision"
        const val QUOTE_CURRENCY_PRECISION = "quote_currency_precision"

    }


    constructor(): this(
        id = -1,
        baseCurrencyId = -1,
        baseCurrencyCode = "",
        baseCurrencyName = "",
        quoteCurrencyId = -1,
        quoteCurrencyCode = "",
        quoteCurrencyName = "",
        symbol = "",
        groupName = "",
        groupId = -1,
        minOrderAmount = -1.0,
        minBuyPrice = -1.0,
        minSellPrice = -1.0,
        buyFeeInPercentage = -1.0,
        sellFeeInPercentage = -1.0,
        isActive = true,
        isDelisted = false,
        message = null,
        baseCurrencyPrecision = 0,
        quoteCurrencyPrecision = 0
    )

}
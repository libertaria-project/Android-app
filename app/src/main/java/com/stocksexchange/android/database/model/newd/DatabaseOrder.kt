package com.stocksexchange.android.database.model.newd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stocksexchange.android.api.model.newapi.Order
import com.stocksexchange.android.database.model.newd.DatabaseOrder.Companion.TABLE_NAME

/**
 * A Room database model for [Order] class.
 */
@Entity(tableName = TABLE_NAME)
data class DatabaseOrder(
    @PrimaryKey @ColumnInfo(name = ID) var id: Long,
    @ColumnInfo(name = CURRENCY_PAIR_ID) var currencyPairId: Int,
    @ColumnInfo(name = PRICE) var price: Double,
    @ColumnInfo(name = TRIGGER_PRICE) var triggerPrice: Double,
    @ColumnInfo(name = INITIAL_AMOUNT) var initialAmount: Double,
    @ColumnInfo(name = PROCESSED_AMOUNT) var processedAmount: Double,
    @ColumnInfo(name = TYPE_STR) var typeStr: String,
    @ColumnInfo(name = ORIGINAL_TYPE_STR) var originalTypeStr: String,
    @ColumnInfo(name = TIMESTAMP) var timestamp: Long,
    @ColumnInfo(name = STATUS_STR) var statusStr: String,
    @ColumnInfo(name = TRADES) var trades: List<Order.Trade>,
    @ColumnInfo(name = FEES) var fees: List<Order.Fee>
) {

    companion object {

        const val TABLE_NAME = "new_orders"

        const val ID = "id"
        const val CURRENCY_PAIR_ID = "currency_pair_id"
        const val PRICE = "price"
        const val TRIGGER_PRICE = "trigger_price"
        const val INITIAL_AMOUNT = "initial_amount"
        const val PROCESSED_AMOUNT = "processed_amount"
        const val TYPE_STR = "type_str"
        const val ORIGINAL_TYPE_STR = "original_type_str"
        const val TIMESTAMP = "timestamp"
        const val STATUS_STR = "status_str"
        const val TRADES = "trades"
        const val FEES = "fees"

    }


    constructor(): this(
        id = -1,
        currencyPairId = -1,
        price = -1.0,
        triggerPrice = -1.0,
        initialAmount = -1.0,
        processedAmount = -1.0,
        typeStr = "",
        originalTypeStr = "",
        timestamp = -1L,
        statusStr = "",
        trades = listOf(),
        fees = listOf()
    )

}
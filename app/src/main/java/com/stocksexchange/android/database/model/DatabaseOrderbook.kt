package com.stocksexchange.android.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stocksexchange.android.api.model.Orderbook
import com.stocksexchange.android.api.model.OrderbookOrder
import com.stocksexchange.android.database.model.DatabaseOrderbook.Companion.TABLE_NAME

/**
 * A Room database model class of [Orderbook] class.
 */
@Entity(tableName = TABLE_NAME)
data class DatabaseOrderbook(
    @PrimaryKey @ColumnInfo(name = MARKET_NAME) var marketName: String,
    @ColumnInfo(name = BUY_ORDERS) var buyOrders: List<OrderbookOrder>,
    @ColumnInfo(name = SELL_ORDERS) var sellOrders: List<OrderbookOrder>
) {

    companion object {

        const val TABLE_NAME = "orderbook"

        const val MARKET_NAME = "market_name"
        const val BUY_ORDERS = "buy_orders"
        const val SELL_ORDERS = "sell_orders"

    }


    constructor(): this("", listOf(), listOf())

}
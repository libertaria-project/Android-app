package com.stocksexchange.android.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.stocksexchange.android.api.model.CandleStick
import com.stocksexchange.android.api.model.PriceChartData
import com.stocksexchange.android.database.model.DatabasePriceChartData.Companion.INTERVAL
import com.stocksexchange.android.database.model.DatabasePriceChartData.Companion.MARKET_NAME
import com.stocksexchange.android.database.model.DatabasePriceChartData.Companion.TABLE_NAME

/**
 * A Room database model class of [PriceChartData] class.
 */
@Entity(tableName = TABLE_NAME, primaryKeys = [MARKET_NAME, INTERVAL])
data class DatabasePriceChartData(
    @ColumnInfo(name = MARKET_NAME) var marketName: String,
    @ColumnInfo(name = INTERVAL) var interval: String,
    @ColumnInfo(name = ORDER) var order: String,
    @ColumnInfo(name = START_DATE) var startDate: String,
    @ColumnInfo(name = END_DATE) var endDate: String,
    @ColumnInfo(name = COUNT) var count: Int,
    @ColumnInfo(name = PAGES_COUNT) var pagesCount: Int,
    @ColumnInfo(name = CURRENT_PAGE) var currentPage: Int,
    @ColumnInfo(name = CANDLE_STICKS) var candleSticks: List<CandleStick>
) {

    companion object {

        const val TABLE_NAME = "price_chart_data"

        const val MARKET_NAME = "market_name"
        const val INTERVAL = "interval"
        const val ORDER = "order"
        const val START_DATE = "start_date"
        const val END_DATE = "end_date"
        const val COUNT = "count"
        const val PAGES_COUNT = "pages_count"
        const val CURRENT_PAGE = "current_page"
        const val CANDLE_STICKS = "candle_sticks"

    }


    constructor(): this("", "", "", "", "", -1, -1, -1, listOf())

}
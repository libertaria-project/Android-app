package com.stocksexchange.android.ui.views.marketpreview.trades

import android.content.Context
import com.arthurivanets.adapster.markers.ItemResources
import com.stocksexchange.android.ui.utils.extensions.getLocale
import com.stocksexchange.android.utils.formatters.DoubleFormatter
import com.stocksexchange.android.utils.formatters.TimeFormatter

class TradeResources(
    val priceMaxCharsLength: Int,
    val amountMaxCharsLength: Int,
    val stubText: String,
    val doubleFormatter: DoubleFormatter,
    val timeFormatter: TimeFormatter,
    val colors: List<Int>
) : ItemResources {


    companion object {

        const val COLOR_HEADER_TITLE_TEXT = 0
        const val COLOR_HEADER_SEPARATOR = 1
        const val COLOR_BUY_TRADE_BACKGROUND_HIGHLIGHT = 2
        const val COLOR_SELL_TRADE_BACKGROUND_HIGHLIGHT = 3
        const val COLOR_BUY_TRADE_PRICE_HIGHLIGHT = 4
        const val COLOR_SELL_TRADE_PRICE_HIGHLIGHT = 5
        const val COLOR_BUY_TRADE_PRICE = 6
        const val COLOR_SELL_TRADE_PRICE = 7
        const val COLOR_TRADE_AMOUNT = 8
        const val COLOR_TRADE_TIME = 9


        fun newInstance(context: Context, priceMaxCharsLength: Int,
                        amountMaxCharsLength: Int, stubText: String,
                        colors: List<Int>): TradeResources {
            val doubleFormatter = DoubleFormatter.getInstance(context.getLocale())
            val timeFormatter = TimeFormatter.getInstance(context)

            return TradeResources(
                priceMaxCharsLength,
                amountMaxCharsLength,
                stubText,
                doubleFormatter,
                timeFormatter,
                colors
            )
        }


        fun getDefaultResources(context: Context): TradeResources {
            return TradeResources(
                priceMaxCharsLength = -1,
                amountMaxCharsLength = -1,
                stubText = "",
                doubleFormatter = DoubleFormatter.getInstance(context.getLocale()),
                timeFormatter = TimeFormatter.getInstance(context),
                colors = emptyList()
            )
        }

    }


}
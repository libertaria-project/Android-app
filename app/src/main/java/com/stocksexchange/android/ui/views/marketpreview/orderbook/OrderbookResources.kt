package com.stocksexchange.android.ui.views.marketpreview.orderbook

import android.content.Context
import com.arthurivanets.adapster.markers.ItemResources
import com.stocksexchange.android.ui.utils.extensions.getLocale
import com.stocksexchange.android.utils.formatters.DoubleFormatter

class OrderbookResources(
    val shouldHideHeaderMoreButton: Boolean,
    val priceMaxCharsLength: Int,
    val amountMaxCharsLength: Int,
    val stubText: String,
    val doubleFormatter: DoubleFormatter,
    val colors: List<Int>
) : ItemResources {


    companion object {

        const val COLOR_HEADER_TITLE_TEXT = 0
        const val COLOR_HEADER_MORE_BUTTON = 1
        const val COLOR_HEADER_SEPARATOR = 2
        const val COLOR_BUY_ORDER_BACKGROUND_HIGHLIGHT = 3
        const val COLOR_SELL_ORDER_BACKGROUND_HIGHLIGHT = 4
        const val COLOR_BUY_ORDER_PRICE_HIGHLIGHT = 5
        const val COLOR_SELL_ORDER_PRICE_HIGHLIGHT = 6
        const val COLOR_BUY_ORDER_PRICE = 7
        const val COLOR_SELL_ORDER_PRICE = 8
        const val COLOR_ORDER_AMOUNT = 9
        const val COLOR_BUY_ORDER_BACKGROUND = 10
        const val COLOR_SELL_ORDER_BACKGROUND = 11


        fun newInstance(context: Context, shouldHideHeaderMoreButton: Boolean,
                        priceMaxCharsLength: Int, amountMaxCharsLength: Int,
                        stubText: String, colors: List<Int>): OrderbookResources {
            val doubleFormatter = DoubleFormatter.getInstance(context.getLocale())

            return OrderbookResources(
                shouldHideHeaderMoreButton,
                priceMaxCharsLength,
                amountMaxCharsLength,
                stubText,
                doubleFormatter,
                colors
            )
        }


        fun getDefaultResources(context: Context): OrderbookResources {
            return OrderbookResources(
                shouldHideHeaderMoreButton = false,
                priceMaxCharsLength = -1,
                amountMaxCharsLength = -1,
                stubText = "",
                doubleFormatter = DoubleFormatter.getInstance(context.getLocale()),
                colors = emptyList()
            )
        }

    }


}
package com.stocksexchange.android.theming.model

import com.stocksexchange.android.ui.views.marketpreview.orderbook.OrderbookView
import java.io.Serializable

/**
 * A model class containing [OrderbookView] colors.
 */
data class OrderbookViewTheme(
    val backgroundColor: Int,
    val headerTitleTextColor: Int,
    val headerMoreButtonColor: Int,
    val headerSeparatorColor: Int,
    val buyPriceColor: Int,
    val buyPriceHighlightColor: Int,
    val buyOrderBackgroundColor: Int,
    val buyOrderHighlightBackgroundColor: Int,
    val sellPriceColor: Int,
    val sellPriceHighlightColor: Int,
    val sellOrderBackgroundColor: Int,
    val sellOrderHighlightBackgroundColor: Int,
    val amountColor: Int,
    val progressBarColor: Int,
    val infoViewColor: Int
) : Serializable
package com.stocksexchange.android.theming.model

import com.stocksexchange.android.ui.views.marketpreview.trades.TradesView
import java.io.Serializable

/**
 * A model class containing [TradesView] colors.
 */
data class TradesViewTheme(
    val backgroundColor: Int,
    val headerTitleTextColor: Int,
    val headerSeparatorColor: Int,
    val buyHighlightBackgroundColor: Int,
    val sellHighlightBackgroundColor: Int,
    val buyPriceHighlightColor: Int,
    val sellPriceHighlightColor: Int,
    val buyPriceColor: Int,
    val sellPriceColor: Int,
    val amountColor: Int,
    val tradeTimeColor: Int,
    val progressBarColor: Int,
    val infoViewColor: Int
) : Serializable
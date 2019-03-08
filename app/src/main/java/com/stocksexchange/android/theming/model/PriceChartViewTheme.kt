package com.stocksexchange.android.theming.model

import com.stocksexchange.android.ui.views.marketpreview.PriceChartView
import java.io.Serializable

/**
 * A model class containing [PriceChartView] related colors.
 */
data class PriceChartViewTheme(
    val backgroundColor: Int,
    val progressBarColor: Int,
    val infoViewColor: Int,
    val infoFieldsDefaultTextColor: Int,
    val axisGridColor: Int,
    val highlighterColor: Int,
    val positiveColor: Int,
    val negativeColor: Int,
    val neutralColor: Int,
    val volumeBarsColor: Int,
    val candleStickShadowColor: Int,
    val tabTextColor: Int,
    val tabIndicatorColor: Int
) : Serializable
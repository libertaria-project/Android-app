package com.stocksexchange.android.theming.model

import com.stocksexchange.android.ui.views.marketpreview.DepthChartView
import java.io.Serializable

/**
 * A model class containing [DepthChartView] related colors.
 */
data class DepthChartViewTheme(
    val backgroundColor: Int,
    val progressBarColor: Int,
    val infoViewColor: Int,
    val infoFieldsDefaultTextColor: Int,
    val axisGridColor: Int,
    val highlighterColor: Int,
    val positiveColor: Int,
    val negativeColor: Int,
    val neutralColor: Int,
    val tabTextColor: Int,
    val tabIndicatorColor: Int
) : Serializable
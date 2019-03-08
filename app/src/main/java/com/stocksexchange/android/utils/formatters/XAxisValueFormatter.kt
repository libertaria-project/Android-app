package com.stocksexchange.android.utils.formatters

import android.content.Context
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.stocksexchange.android.api.model.PriceChartData

/**
 * Formatter used for formatting numeric X axis values
 * to the date representations.
 */
class XAxisValueFormatter(
    private val context: Context,
    private val priceChartData: PriceChartData,
    private val timeFormatter: TimeFormatter
) : IAxisValueFormatter {


    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        if((value.toInt() < 0) || (value.toInt() >= priceChartData.candleSticks.size)) {
            return ""
        }

        return timeFormatter.formatCandleStickDate(priceChartData.candleSticks[value.toInt()].date)
    }


}
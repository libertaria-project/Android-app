package com.stocksexchange.android.model

import android.os.Bundle
import android.os.Parcelable
import com.github.mikephil.charting.highlight.Highlight
import kotlinx.android.parcel.Parcelize

/**
 * A model class holding information about a highlight
 * of the chart.
 */
@Parcelize
data class ChartHighlight(
    val x: Float,
    val dataSetIndex: Int,
    var data: ChartHighlightData? = null
) : Parcelable {


    /**
     * A data class for chart highlights.
     */
    @Parcelize
    data class ChartHighlightData(
        val key: String,
        val bundle: Bundle? = null
    ) : Parcelable


    /**
     * Converts this model class to the library's highlight model class.
     *
     * @return The library's highlight model class
     */
    fun toHighlight(): Highlight {
        return Highlight(x, dataSetIndex, -1)
    }


}
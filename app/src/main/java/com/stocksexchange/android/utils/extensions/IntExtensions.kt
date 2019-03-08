package com.stocksexchange.android.utils.extensions

import android.graphics.Color
import androidx.annotation.FloatRange

/**
 * Adjusts alpha of the color.
 *
 * @param alpha The alpha to set. Accepts values in a range from 0.0 (min)
 * to 1.0 (max).
 */
fun Int.adjustAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float): Int {
    val alphaChannel = (255 * alpha).toInt()
    val redChannel = Color.red(this)
    val greenChannel = Color.green(this)
    val blueChannel = Color.blue(this)

    return Color.argb(alphaChannel, redChannel, greenChannel, blueChannel)
}
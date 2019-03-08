package com.stocksexchange.android.ui.utils.extensions

import android.widget.ProgressBar
import androidx.annotation.ColorInt

/**
 * Sets the progress bar color by mutating its drawable.
 *
 * @param color The color tos set
 */
fun ProgressBar.setColor(@ColorInt color: Int) {
    indeterminateDrawable.setColor(color)
}
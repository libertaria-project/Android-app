package com.stocksexchange.android.ui.utils.extensions

import android.widget.ImageView
import androidx.annotation.ColorInt

/**
 * Sets the color of the drawable.
 *
 * @param color The color to set
 */
fun ImageView.setColor(@ColorInt color: Int) {
    if(drawable == null) {
        return
    }

    drawable.setColor(color)
}
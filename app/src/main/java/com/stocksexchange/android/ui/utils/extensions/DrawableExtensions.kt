package com.stocksexchange.android.ui.utils.extensions

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import androidx.annotation.ColorInt
import com.stocksexchange.android.R

/**
 * Sets the color for the drawable to the specified color.
 *
 * @param color The color to set
 */
fun Drawable.setColor(@ColorInt color: Int) {
    mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
}


/**
 * Sets colors of the gradient drawable.
 *
 * @param startColor The start color of the gradient
 * @param endColor The end color of the gradient
 */
fun Drawable.setGradientColors(@ColorInt startColor: Int, @ColorInt endColor: Int) {
    if(this !is GradientDrawable) {
        return
    }

    mutate()
    colors = intArrayOf(startColor, endColor)
}


/**
 * Sets a color of the drawable stroke.
 *
 * @param strokeWidth The width of the stroke
 * @param borderColor The color for the border
 */
fun Drawable.setStrokeColor(strokeWidth: Int, @ColorInt borderColor: Int) {
    if(this !is LayerDrawable) {
        return
    }

    // Background
    val backgroundDrawable = (findDrawableByLayerId(R.id.background) as GradientDrawable)
    backgroundDrawable.mutate()
    backgroundDrawable.setStroke(strokeWidth, borderColor)
}
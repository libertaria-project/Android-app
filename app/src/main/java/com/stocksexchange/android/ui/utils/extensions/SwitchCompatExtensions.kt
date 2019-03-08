package com.stocksexchange.android.ui.utils.extensions

import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import androidx.appcompat.widget.SwitchCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.stocksexchange.android.utils.extensions.adjustAlpha

/**
 * Sets a color scheme of the switch to a single color.
 *
 * @param color The color of the switch color scheme
 */
fun SwitchCompat.setColor(@ColorInt color: Int) {
    setColors(color, color, color, color)
}

/**
 * Sets colors of the switch.
 *
 * @param pointerDeactivatedColor The color of the deactivated pointer
 * @param pointerActivatedColor The color of the activated pointer
 * @param backgroundDeactivatedColor The color of the deactivated pointer
 * @param backgroundActivatedColor The color of the activated color
 */
fun SwitchCompat.setColors(
    @ColorInt pointerDeactivatedColor: Int,
    @ColorInt pointerActivatedColor: Int,
    @ColorInt backgroundDeactivatedColor: Int,
    @ColorInt backgroundActivatedColor: Int
) {
    val switchStates: Array<IntArray> = arrayOf(
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_checked)
    )

    val switchThumbDrawableColors = intArrayOf(
        pointerDeactivatedColor,
        pointerActivatedColor
    )

    val switchTrackDrawableColors = intArrayOf(
        backgroundDeactivatedColor.adjustAlpha(0.5f),
        backgroundActivatedColor.adjustAlpha(0.5f)
    )

    DrawableCompat.setTintList(
        DrawableCompat.wrap(thumbDrawable),
        ColorStateList(switchStates, switchThumbDrawableColors)
    )

    DrawableCompat.setTintList(
        DrawableCompat.wrap(trackDrawable),
        ColorStateList(switchStates, switchTrackDrawableColors)
    )
}
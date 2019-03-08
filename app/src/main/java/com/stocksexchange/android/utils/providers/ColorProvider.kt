package com.stocksexchange.android.utils.providers

import android.content.Context
import androidx.annotation.ColorRes
import com.stocksexchange.android.ui.utils.extensions.getCompatColor

/**
 * A helper class used for providing color related functionality.
 */
class ColorProvider(context: Context) {


    private val context: Context = context.applicationContext




    /**
     * Gets a color specified by the resource id.
     *
     * @param colorResId The id to get the color for
     *
     * @return The resolved color
     */
    fun getColor(@ColorRes colorResId: Int): Int {
        return context.getCompatColor(colorResId)
    }


}
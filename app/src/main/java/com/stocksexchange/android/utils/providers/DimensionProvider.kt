package com.stocksexchange.android.utils.providers

import android.content.Context
import androidx.annotation.DimenRes
import com.stocksexchange.android.ui.utils.extensions.getDimension

/**
 * A helper class used for providing dimension related functionality.
 */
class DimensionProvider(context: Context) {


    private val context: Context = context.applicationContext




    /**
     * Gets a dimension specified by the resource id.
     *
     * @param id The id to get the dimension for
     *
     * @return The resolved dimension
     */
    fun getDimension(@DimenRes id: Int): Float {
        return context.getDimension(id)
    }


}
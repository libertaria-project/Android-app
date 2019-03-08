package com.stocksexchange.android.model

import com.stocksexchange.android.theming.model.Theme
import java.io.Serializable

/**
 * A model class holding theme related data.
 */
data class ThemeData(
    val theme: Theme,
    var isSelected: Boolean = false
) : Serializable
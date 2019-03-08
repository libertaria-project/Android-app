package com.stocksexchange.android.theming.model

import com.stocksexchange.android.ui.views.DottedMapView
import java.io.Serializable

/**
 * A model class containing [DottedMapView] related colors.
 */
data class DottedMapViewTheme(
    val titleColor: Int,
    val textColor: Int
) : Serializable
package com.stocksexchange.android.theming.model

import com.stocksexchange.android.ui.views.SwitchOptionsView
import java.io.Serializable

/**
 * A model class containing [SwitchOptionsView] colors.
 */
data class SwitchOptionsViewTheme(
    val titleTextColor: Int,
    val switchColor: Int
) : Serializable
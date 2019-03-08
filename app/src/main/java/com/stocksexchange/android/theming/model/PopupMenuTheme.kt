package com.stocksexchange.android.theming.model

import java.io.Serializable

/**
 * A model class containing [PopupMenuTheme] related colors.
 */
data class PopupMenuTheme(
    val backgroundColor: Int,
    val titleColor: Int
) : Serializable
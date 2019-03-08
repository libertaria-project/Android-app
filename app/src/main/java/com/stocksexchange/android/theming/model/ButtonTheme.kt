package com.stocksexchange.android.theming.model

import java.io.Serializable

/**
 * A model class containing button related colors.
 */
data class ButtonTheme(
    val contentColor: Int,
    val releasedStateBackgroundColor: Int,
    val pressedStateBackgroundColor: Int
) : Serializable
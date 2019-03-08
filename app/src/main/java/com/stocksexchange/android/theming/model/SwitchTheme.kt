package com.stocksexchange.android.theming.model

import java.io.Serializable

/**
 * A model class containing switch related colors.
 */
data class SwitchTheme(
    val pointerActivatedColor: Int,
    val pointerDeactivatedColor: Int,
    val backgroundActivatedColor: Int,
    val backgroundDeactivatedColor: Int
) : Serializable
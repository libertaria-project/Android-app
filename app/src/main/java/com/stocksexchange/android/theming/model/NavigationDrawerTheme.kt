package com.stocksexchange.android.theming.model

import com.stocksexchange.android.ui.drawer.NavigationDrawer
import java.io.Serializable

/**
 * A model class containing [NavigationDrawer] related colors.
 */
data class NavigationDrawerTheme(
    val backgroundColor: Int,
    val itemColor: Int,
    val signInButtonContentColor: Int,
    val signInButtonReleasedStateBackgroundColor: Int,
    val signInButtonPressedStateBackgroundColor: Int
) : Serializable
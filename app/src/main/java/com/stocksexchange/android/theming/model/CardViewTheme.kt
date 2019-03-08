package com.stocksexchange.android.theming.model

import androidx.cardview.widget.CardView
import java.io.Serializable

/**
 * A model class containing [CardView] related colors.
 */
data class CardViewTheme(
    val backgroundColor: Int,
    val primaryTextColor: Int,
    val primaryDarkTextColor: Int
) : Serializable
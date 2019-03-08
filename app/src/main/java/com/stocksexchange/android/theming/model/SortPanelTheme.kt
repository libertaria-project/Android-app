package com.stocksexchange.android.theming.model

import com.stocksexchange.android.ui.views.CurrencyMarketsSortPanel
import java.io.Serializable

/**
 * A model class containing [CurrencyMarketsSortPanel] related colors.
 */
data class SortPanelTheme(
    val backgroundColor: Int,
    val selectedTitleColor: Int,
    val unselectedTitleColor: Int
) : Serializable
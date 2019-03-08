package com.stocksexchange.android.theming.model

import com.stocksexchange.android.ui.views.detailsviews.MarketDetailsView
import java.io.Serializable

/**
 * A model class containing [MarketDetailsView] colors.
 */
data class MarketDetailsViewTheme(
    val backgroundColor: Int,
    val itemTitleColor: Int,
    val itemValueColor: Int,
    val itemSeparatorColor: Int,
    val positiveStatusColor: Int,
    val negativeStatusColor: Int
) : Serializable
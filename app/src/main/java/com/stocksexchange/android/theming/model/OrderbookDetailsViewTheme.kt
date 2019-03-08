package com.stocksexchange.android.theming.model

import com.stocksexchange.android.ui.views.detailsviews.OrderbookDetailsView
import java.io.Serializable

/**
 * A model class containing [OrderbookDetailsView] colors.
 */
data class OrderbookDetailsViewTheme(
    val backgroundColor: Int,
    val itemTitleColor: Int,
    val itemValueColor: Int,
    val itemSeparatorColor: Int,
    val highestBidValueColor: Int,
    val lowestAskValueColor: Int,
    val progressBarColor: Int,
    val infoViewCaptionColor: Int
) : Serializable
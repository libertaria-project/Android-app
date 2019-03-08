package com.stocksexchange.android.model

import com.stocksexchange.android.api.model.Trade
import com.stocksexchange.android.ui.views.marketpreview.base.interfaces.BaseMarketPreviewListData

/**
 * A model class acting as a wrapper around an [Trade] with
 * additional data.
 */
data class TradeData(
    val trade: Trade,
    override val highlightEndTimestamp: Long
) : BaseMarketPreviewListData
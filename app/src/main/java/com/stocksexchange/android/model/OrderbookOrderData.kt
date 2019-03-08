package com.stocksexchange.android.model

import com.stocksexchange.android.api.model.OrderbookOrder
import com.stocksexchange.android.ui.views.marketpreview.base.interfaces.BaseMarketPreviewListData

/**
 * A model class acting as a wrapper around an [OrderbookOrder] with
 * additional data.
 */
data class OrderbookOrderData(
    val type: OrderbookOrderTypes,
    val order: OrderbookOrder,
    val volumeLevel: Int,
    override val highlightEndTimestamp: Long
) : BaseMarketPreviewListData {


    companion object {

        const val VOLUME_LEVEL_MAX_VALUE = 10000

    }


}
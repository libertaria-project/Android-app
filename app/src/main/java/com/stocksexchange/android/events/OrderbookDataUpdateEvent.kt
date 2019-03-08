package com.stocksexchange.android.events

import com.stocksexchange.android.api.model.Orderbook
import com.stocksexchange.android.api.model.OrderbookOrder
import com.stocksexchange.android.model.DataActionItem
import com.stocksexchange.android.utils.helpers.tag

/**
 * An event to send to notify subscribers about
 * orderbook data updates.
 */
class OrderbookDataUpdateEvent private constructor(
    attachment: Orderbook,
    sourceTag: String,
    onConsumeListener: OnConsumeListener?,
    val dataActionItems: List<DataActionItem<OrderbookOrder>>
) : BaseEvent<Orderbook>(
    type = TYPE_MULTIPLE_ITEMS,
    attachment = attachment,
    sourceTag = sourceTag,
    onConsumeListener = onConsumeListener
) {


    companion object {


        fun newInstance(newData: Orderbook, dataActionItems: List<DataActionItem<OrderbookOrder>>,
                        source: Any, onConsumeListener: OnConsumeListener?): OrderbookDataUpdateEvent {
            return newInstance(newData, dataActionItems, tag(source), onConsumeListener)
        }


        fun newInstance(newData: Orderbook, dataActionItems: List<DataActionItem<OrderbookOrder>>,
                        sourceTag: String, onConsumeListener: OnConsumeListener?): OrderbookDataUpdateEvent {
            return OrderbookDataUpdateEvent(newData, sourceTag, onConsumeListener, dataActionItems)
        }


    }


}
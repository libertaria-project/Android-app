package com.stocksexchange.android.events

import com.stocksexchange.android.api.model.Trade
import com.stocksexchange.android.model.DataActionItem
import com.stocksexchange.android.utils.helpers.tag

/**
 * An event to send to notify subscribers about
 * trades data updates.
 */
class TradesDataUpdateEvent private constructor(
    attachment: List<Trade>,
    sourceTag: String,
    onConsumeListener: OnConsumeListener?,
    val dataActionItems: List<DataActionItem<Trade>>
) : BaseEvent<List<Trade>>(
    type = TYPE_MULTIPLE_ITEMS,
    attachment = attachment,
    sourceTag = sourceTag,
    onConsumeListener = onConsumeListener
) {


    companion object {


        fun newInstance(newData: List<Trade>, dataActionItems: List<DataActionItem<Trade>>,
                        source: Any, onConsumeListener: OnConsumeListener?): TradesDataUpdateEvent {
            return newInstance(newData, dataActionItems, tag(source), onConsumeListener)
        }


        fun newInstance(newData: List<Trade>, dataActionItems: List<DataActionItem<Trade>>,
                        sourceTag: String, onConsumeListener: OnConsumeListener?): TradesDataUpdateEvent {
            return TradesDataUpdateEvent(newData, sourceTag, onConsumeListener, dataActionItems)
        }


    }


}
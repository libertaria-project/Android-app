package com.stocksexchange.android.events

import com.stocksexchange.android.utils.helpers.tag

/**
 * An event to send to notify that an orderbook
 * data should be reloaded.
 */
class OrderbookDataReloadEvent private constructor(
    sourceTag: String
) : BaseEvent<Void?>(TYPE_INVALID, null, sourceTag) {


    companion object {

        fun newInstance(source: Any): OrderbookDataReloadEvent {
            return newInstance(tag(source))
        }


        fun newInstance(sourceTag: String): OrderbookDataReloadEvent {
            return OrderbookDataReloadEvent(sourceTag)
        }

    }


}
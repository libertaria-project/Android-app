package com.stocksexchange.android.events

import com.stocksexchange.android.utils.helpers.tag

/**
 * An event to send to notify subscribers that currency markets
 * data needs to be updated.
 */
class CurrencyMarketsUpdateEvent private constructor(
    sourceTag: String,
    consumerCount: Int
) : BaseEvent<Void?>(TYPE_INVALID, null, sourceTag, consumerCount) {


    companion object {

        fun newInstance(source: Any, consumerCount: Int): CurrencyMarketsUpdateEvent {
            return newInstance(tag(source), consumerCount)
        }


        fun newInstance(sourceTag: String, consumerCount: Int): CurrencyMarketsUpdateEvent {
            return CurrencyMarketsUpdateEvent(sourceTag, consumerCount)
        }

    }


}
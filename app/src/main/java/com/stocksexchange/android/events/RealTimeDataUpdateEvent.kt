package com.stocksexchange.android.events

import com.stocksexchange.android.utils.helpers.tag

/**
 * An event to send to notify subscribers that real time
 * data needs to be updated.
 */
open class RealTimeDataUpdateEvent private constructor(
    sourceTag: String
) : BaseEvent<Void?>(TYPE_INVALID, null, sourceTag) {


    companion object {

        fun newInstance(source: Any): RealTimeDataUpdateEvent {
            return newInstance(tag(source))
        }


        fun newInstance(sourceTag: String): RealTimeDataUpdateEvent {
            return RealTimeDataUpdateEvent(sourceTag)
        }

    }


}
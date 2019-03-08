package com.stocksexchange.android.events

import com.stocksexchange.android.model.PerformedCurrencyMarketActions
import com.stocksexchange.android.utils.helpers.tag

/**
 * An event to send to notify subscribers about
 * [PerformedCurrencyMarketActions] model class.
 */
class PerformedCurrencyMarketsActionsEvent private constructor(
    type: Int,
    attachment: PerformedCurrencyMarketActions,
    sourceTag: String,
    onConsumeListener: OnConsumeListener?
) : BaseEvent<PerformedCurrencyMarketActions>(
    type = type,
    attachment = attachment,
    sourceTag = sourceTag,
    onConsumeListener = onConsumeListener
) {


    companion object {


        fun init(performedActions: PerformedCurrencyMarketActions, source: Any,
                 onConsumeListener: OnConsumeListener? = null): PerformedCurrencyMarketsActionsEvent {
            return init(performedActions, tag(source), onConsumeListener)
        }


        fun init(performedActions: PerformedCurrencyMarketActions, sourceTag: String,
                 onConsumeListener: OnConsumeListener? = null): PerformedCurrencyMarketsActionsEvent {
            return PerformedCurrencyMarketsActionsEvent(TYPE_MULTIPLE_ITEMS, performedActions, sourceTag, onConsumeListener)
        }


    }


}
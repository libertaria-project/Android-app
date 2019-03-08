package com.stocksexchange.android.ui.orders.fragment

import android.content.Context
import com.arthurivanets.adapster.markers.ItemResources
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.api.model.OrderTypes
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.ui.utils.extensions.getLocale
import com.stocksexchange.android.utils.formatters.DoubleFormatter
import com.stocksexchange.android.utils.formatters.TimeFormatter

class OrderResources(
    val strings: List<String>,
    val timeFormatter: TimeFormatter,
    val doubleFormatter: DoubleFormatter,
    val settings: Settings
) : ItemResources {


    companion object {

        const val STRING_TYPE_BUY = 0
        const val STRING_TYPE_SELL = 1
        const val STRING_RESULT_SPENT = 2
        const val STRING_RESULT_RECEIVED = 3


        fun newInstance(context: Context, settings: Settings): OrderResources {
            val strings = listOf(
                context.getString(R.string.trade_type_buy),
                context.getString(R.string.trade_type_sell),
                context.getString(R.string.action_spent),
                context.getString(R.string.action_received)
            )

            val timeFormatter = TimeFormatter.getInstance(context)
            val doubleFormatter = DoubleFormatter.getInstance(context.getLocale())

            return OrderResources(
                strings,
                timeFormatter,
                doubleFormatter,
                settings
            )
        }

    }


    var orderType: OrderTypes = OrderTypes.ACTIVE

    var currencyMarkets: Map<String, CurrencyMarket> = mapOf()


}
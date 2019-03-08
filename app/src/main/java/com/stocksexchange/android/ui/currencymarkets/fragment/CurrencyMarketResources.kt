package com.stocksexchange.android.ui.currencymarkets.fragment

import android.content.Context
import com.arthurivanets.adapster.markers.ItemResources
import com.stocksexchange.android.R
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.ui.utils.extensions.getLocale
import com.stocksexchange.android.utils.formatters.DoubleFormatter

data class CurrencyMarketResources(
    val lastPriceTvStyleResId: Int,
    val volumeTemplate: String,
    val formatter: DoubleFormatter,
    val settings: Settings
) : ItemResources {


    companion object {

        fun newInstance(context: Context, settings: Settings): CurrencyMarketResources {
            val lastPriceTvStyleResId = R.style.CurrencyMarketLastPrice
            val volumeTemplate = context.getString(R.string.currency_market_item_volume_template)
            val locale = context.getLocale()
            val formatter = DoubleFormatter.getInstance(locale)

            return CurrencyMarketResources(
                lastPriceTvStyleResId,
                volumeTemplate,
                formatter,
                settings
            )
        }

    }


    var baseCurrencySymbolCharacterLimit: Int = 0


}
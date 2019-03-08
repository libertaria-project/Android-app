package com.stocksexchange.android.ui.wallets.fragment

import android.content.Context
import com.arthurivanets.adapster.markers.ItemResources
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.ui.utils.extensions.getLocale
import com.stocksexchange.android.utils.formatters.DoubleFormatter

class WalletsResources(
    val formatter: DoubleFormatter,
    val settings: Settings
) : ItemResources {


    companion object {

        fun newInstance(context: Context, settings: Settings): WalletsResources {
            val formatter = DoubleFormatter.getInstance(context.getLocale())

            return WalletsResources(formatter, settings)
        }

    }


}
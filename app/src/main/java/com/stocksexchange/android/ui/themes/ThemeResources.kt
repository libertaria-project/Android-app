package com.stocksexchange.android.ui.themes

import android.content.Context
import com.arthurivanets.adapster.markers.ItemResources
import com.stocksexchange.android.R
import com.stocksexchange.android.model.Settings

class ThemeResources(
    val strings: List<String>,
    val settings: Settings
) : ItemResources {


    companion object {

        const val STRING_FIRST_COLUMN = 0


        fun newInstance(context: Context, settings: Settings): ThemeResources {
            val stringBuilder = StringBuilder(context.getString(R.string.currency_markets_sort_panel_name_title))
                .append("/")
                .append(context.getString(R.string.currency_markets_sort_panel_volume_title))

            val strings = listOf(stringBuilder.toString())

            return ThemeResources(strings, settings)
        }

    }


}
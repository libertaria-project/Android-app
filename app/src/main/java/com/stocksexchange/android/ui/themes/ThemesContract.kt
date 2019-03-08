package com.stocksexchange.android.ui.themes

import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.model.ThemeData

interface ThemesContract {


    interface View {

        fun updateThemeData(themeData: ThemeData)

        fun setItems(items: MutableList<ThemeData>)

        fun isDataSetEmpty(): Boolean

        fun getDataSetSize(): Int

        fun getThemeDataAt(position: Int): ThemeData?

        fun getAppSettings(): Settings

    }


    interface ActionListener {

        fun onThemeClicked(themeData: ThemeData)

    }


}
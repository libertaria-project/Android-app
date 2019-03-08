package com.stocksexchange.android.mappings

import com.stocksexchange.android.model.ThemeData
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.ui.themes.ThemeItem

fun ThemeData.mapToThemeItem(): ThemeItem {
    return ThemeItem(this)
}


fun List<ThemeData>.mapToThemeItems(): List<ThemeItem> {
    return map { it.mapToThemeItem() }
}
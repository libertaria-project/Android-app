package com.stocksexchange.android.mappings

import com.arthurivanets.adapster.model.BaseItem
import com.stocksexchange.android.model.Setting
import com.stocksexchange.android.model.SettingSection
import com.stocksexchange.android.ui.settings.items.SettingItem
import com.stocksexchange.android.ui.settings.items.SettingSectionItem

fun Setting.mapToSettingItem(): SettingItem {
    return SettingItem(this)
}


fun SettingSection.mapToSettingSectionItem(): SettingSectionItem {
    return SettingSectionItem(this)
}


fun List<Any>.mapToSettingItemList(): List<BaseItem<*, *, *>> {
    return map {
        when (it) {
            is SettingSection -> it.mapToSettingSectionItem()
            is Setting -> it.mapToSettingItem()

            else -> throw IllegalStateException(
                "Please provide a mapping function for ${it.javaClass.simpleName} class."
            )
        }
    }
}
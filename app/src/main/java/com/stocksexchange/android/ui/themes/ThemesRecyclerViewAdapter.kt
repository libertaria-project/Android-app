package com.stocksexchange.android.ui.themes

import android.content.Context
import android.view.View
import com.arthurivanets.adapster.recyclerview.TrackableRecyclerViewAdapter
import com.stocksexchange.android.model.Settings

class ThemesRecyclerViewAdapter(
    context: Context,
    settings: Settings,
    items: MutableList<ThemeItem>
) : TrackableRecyclerViewAdapter<Int, ThemeItem, ThemeItem.ViewHolder>(context, items) {


    private val mResources: ThemeResources = ThemeResources.newInstance(context, settings)

    /**
     * A listener used for notifying about whenever a setting item is clicked.
     */
    var onThemeItemClickListener: ((View, ThemeItem, Int) -> Unit)? = null




    override fun assignListeners(holder: ThemeItem.ViewHolder, position: Int, item: ThemeItem) {
        super.assignListeners(holder, position, item)

        item.setOnItemClickListener(holder, position, onThemeItemClickListener)
    }


    override fun getResources(): ThemeResources? {
        return mResources
    }


}
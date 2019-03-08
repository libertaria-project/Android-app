package com.stocksexchange.android.ui.drawer

import android.content.Context
import android.view.View
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.recyclerview.TrackableRecyclerViewAdapter
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.ui.drawer.items.NavigationDrawerHeaderItem
import com.stocksexchange.android.ui.drawer.items.NavigationDrawerItem

class NavigationDrawerRecyclerViewAdapter(
    context: Context,
    items: MutableList<BaseItem<*, *, *>>
) : TrackableRecyclerViewAdapter<Int, BaseItem<*, *, *>, BaseItem.ViewHolder<*>>(context, items) {


    private val mResources: NavigationDrawerResources = NavigationDrawerResources.newInstance(context)

    /**
     * A listener used for notifying whenever a sign in button is clicked.
     */
    var onSignInButtonClickListener: ((View, NavigationDrawerHeaderItem, Int) -> Unit)? = null

    /**
     * A listener used for notifying whenever an item is clicked.
     */
    var onItemClickListener: ((View, NavigationDrawerItem, Int) -> Unit)? = null




    override fun assignListeners(holder: BaseItem.ViewHolder<*>, position: Int, item: BaseItem<*, *, *>) {
        super.assignListeners(holder, position, item)

        when(item.getLayout()) {

            NavigationDrawerHeaderItem.MAIN_LAYOUT_ID -> {
                (item as NavigationDrawerHeaderItem).setOnSignInButtonClickListener(
                    (holder as NavigationDrawerHeaderItem.ViewHolder),
                    position,
                    onSignInButtonClickListener
                )
            }

            NavigationDrawerItem.MAIN_LAYOUT_ID -> {
                (item as NavigationDrawerItem).setOnItemClickListener(
                    (holder as NavigationDrawerItem.ViewHolder),
                    position,
                    onItemClickListener
                )
            }

        }
    }


    fun setSettings(settings: Settings) {
        mResources.settings = settings
    }


    override fun getResources(): NavigationDrawerResources? {
        return mResources
    }


}
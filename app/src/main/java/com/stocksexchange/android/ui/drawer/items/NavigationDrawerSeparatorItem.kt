package com.stocksexchange.android.ui.drawer.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.Adapter
import com.arthurivanets.adapster.markers.ItemResources
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.model.Item
import com.arthurivanets.adapster.model.markers.Trackable
import com.stocksexchange.android.R
import com.stocksexchange.android.model.Separator
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.ui.base.adapters.recyclerview.BaseViewHolder
import com.stocksexchange.android.ui.drawer.NavigationDrawerResources
import com.stocksexchange.android.theming.ThemingUtil

class NavigationDrawerSeparatorItem(itemModel: Separator) : BaseItem<
    Separator,
    NavigationDrawerSeparatorItem.ViewHolder,
    NavigationDrawerResources
>(itemModel), Trackable<Int> {


    companion object {

        const val MAIN_LAYOUT_ID = R.layout.navigation_drawer_separator_item_layout

    }




    override fun init(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?, parent: ViewGroup,
                      inflater: LayoutInflater, resources: NavigationDrawerResources?): ViewHolder {
        return ViewHolder(inflater.inflate(layout, parent, false)).apply {
            applyTheme(resources!!.settings.theme)
        }
    }


    override fun getLayout(): Int = MAIN_LAYOUT_ID


    override fun getTrackKey(): Int = itemModel.id


    class ViewHolder(itemView: View) : BaseViewHolder<Separator>(itemView) {

        val mSeparatorIv: ImageView = itemView.findViewById(R.id.separatorIv)


        override fun applyTheme(theme: Theme) {
            ThemingUtil.Drawer.Item.separator(mSeparatorIv, theme)
        }

    }


}
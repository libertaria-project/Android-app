package com.stocksexchange.android.ui.drawer.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.Adapter
import com.arthurivanets.adapster.markers.ItemResources
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.model.Item
import com.arthurivanets.adapster.model.markers.Trackable
import com.stocksexchange.android.R
import com.stocksexchange.android.model.Option
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.ui.base.adapters.recyclerview.BaseViewHolder
import com.stocksexchange.android.ui.drawer.NavigationDrawerResources
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.extensions.getCompatDrawable

class NavigationDrawerItem(itemModel: Option) : BaseItem<
    Option,
    NavigationDrawerItem.ViewHolder,
    NavigationDrawerResources
>(itemModel), Trackable<Int> {


    companion object {

        const val MAIN_LAYOUT_ID = R.layout.navigation_drawer_item_layout

    }




    override fun init(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
                      parent: ViewGroup, inflater: LayoutInflater, resources: NavigationDrawerResources?): ViewHolder {
        return ViewHolder(inflater.inflate(layout, parent, false)).apply {
            applyTheme(resources!!.settings.theme)
        }
    }


    override fun bind(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?, viewHolder: ViewHolder,
                      resources: NavigationDrawerResources?) {
        super.bind(adapter, viewHolder, resources)

        val theme = resources!!.settings.theme

        with(viewHolder) {
            mIconIv.setImageDrawable(itemView.context.getCompatDrawable(itemModel.iconDrawableId))
            ThemingUtil.Drawer.Item.icon(mIconIv, theme)

            mTitleTv.text = itemModel.title
        }
    }


    fun setOnItemClickListener(viewHolder: ViewHolder, position: Int,
                               listener: ((View, NavigationDrawerItem, Int) -> Unit)?) {
        viewHolder.itemView.setOnClickListener {
            listener?.invoke(it, this@NavigationDrawerItem, position)
        }
    }


    override fun getLayout(): Int = MAIN_LAYOUT_ID


    override fun getTrackKey(): Int = itemModel.id


    class ViewHolder(itemView: View) : BaseViewHolder<Option>(itemView) {

        val mIconIv: ImageView = itemView.findViewById(R.id.iconIv)

        val mTitleTv: TextView = itemView.findViewById(R.id.titleTv)


        override fun applyTheme(theme: Theme) {
            ThemingUtil.Drawer.Item.text(mTitleTv, theme)
        }

    }


}
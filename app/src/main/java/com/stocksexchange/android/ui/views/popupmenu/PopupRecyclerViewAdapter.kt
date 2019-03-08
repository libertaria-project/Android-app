package com.stocksexchange.android.ui.views.popupmenu

import android.content.Context
import android.view.View
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.recyclerview.TrackableRecyclerViewAdapter

class PopupRecyclerViewAdapter(
    context: Context,
    items: MutableList<BaseItem<*, *, *>>
) : TrackableRecyclerViewAdapter<Int, BaseItem<*, *, *>, BaseItem.ViewHolder<*>>(context, items) {


    private lateinit var mResources: PopupResources

    /**
     * A listener used for notifying whenever the item is clicked.
     */
    var onItemClickListener: ((View, PopupMenuItem, Int) -> Unit)? = null




    override fun assignListeners(holder: BaseItem.ViewHolder<*>, position: Int, item: BaseItem<*, *, *>) {
        super.assignListeners(holder, position, item)

        when(item.getLayout()) {

            PopupMenuItem.MAIN_LAYOUT_ID -> {
                (item as PopupMenuItem).setOnItemClickListener(
                    (holder as PopupMenuItem.ViewHolder),
                    position,
                    onItemClickListener
                )
            }

        }
    }


    /**
     * Sets resources of the adapter.
     *
     * @param resources The resources to set
     */
    fun setResources(resources: PopupResources) {
        mResources = resources
        notifyDataSetChanged()
    }


    override fun getResources(): PopupResources? {
        return mResources
    }


}
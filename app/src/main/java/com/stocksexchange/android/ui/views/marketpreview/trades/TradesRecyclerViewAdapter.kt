package com.stocksexchange.android.ui.views.marketpreview.trades

import android.content.Context
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.recyclerview.TrackableRecyclerViewAdapter

class TradesRecyclerViewAdapter(
    context: Context,
    items: MutableList<BaseItem<*, *, *>>
) : TrackableRecyclerViewAdapter<Long, BaseItem<*, *, *>, BaseItem.ViewHolder<*>>(context, items) {


    private lateinit var mResources: TradeResources




    /**
     * Sets the resources.
     *
     * @param resources The resources to set
     */
    fun setResources(resources: TradeResources) {
        mResources = resources
    }


    override fun getResources(): TradeResources? {
        return mResources
    }


}
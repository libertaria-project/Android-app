package com.stocksexchange.android.ui.views.marketpreview.orderbook

import android.content.Context
import android.view.View
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.recyclerview.TrackableRecyclerViewAdapter
import com.stocksexchange.android.ui.views.marketpreview.orderbook.items.OrderbookHeaderItem

class OrderbookRecyclerViewAdapter(
    context: Context,
    items: MutableList<BaseItem<*, *, *>>
) : TrackableRecyclerViewAdapter<String, BaseItem<*, *, *>, BaseItem.ViewHolder<*>>(context, items) {


    private lateinit var mResources: OrderbookResources


    /**
     * A listener used for notifying whenever a more button of the header is clicked.
     */
    var onHeaderMoreButtonClickListener: ((View, OrderbookHeaderItem, Int) -> Unit)? = null




    override fun assignListeners(holder: BaseItem.ViewHolder<*>, position: Int, item: BaseItem<*, *, *>) {
        super.assignListeners(holder, position, item)

        when(item.getLayout()) {

            OrderbookHeaderItem.BID_ITEM_LAYOUT_ID,
            OrderbookHeaderItem.ASK_ITEM_LAYOUT_ID -> {
                (item as OrderbookHeaderItem).setOnMoreButtonClickListener(
                    (holder as OrderbookHeaderItem.ViewHolder),
                    position,
                    onHeaderMoreButtonClickListener
                )
            }

        }
    }


    /**
     * Sets the resources.
     *
     * @param resources The resources to set
     */
    fun setResources(resources: OrderbookResources) {
        mResources = resources
    }


    override fun getResources(): OrderbookResources? {
        return mResources
    }


}
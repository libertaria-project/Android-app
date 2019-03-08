package com.stocksexchange.android.ui.orders.fragment

import android.content.Context
import android.view.View
import com.arthurivanets.adapster.recyclerview.TrackableRecyclerViewAdapter
import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.api.model.Order
import com.stocksexchange.android.api.model.OrderTypes
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.model.SortTypes

class OrdersRecyclerViewAdapter(
    context: Context,
    settings: Settings,
    items: MutableList<OrderItem>
) : TrackableRecyclerViewAdapter<Long, OrderItem, OrderItem.ViewHolder>(context, items) {


    private val mResources: OrderResources = OrderResources.newInstance(context, settings)

    /**
     * A listener used for notifying whenever a market name is clicked.
     */
    var onMarketNameClickListener: ((View, OrderItem, CurrencyMarket?, Int) -> Unit)? = null

    /**
     * A listener used for notifying whenever a cancel button is clicked.
     */
    var onCancelBtnClickListener: ((View, OrderItem, CurrencyMarket?, Int) -> Unit)? = null




    override fun assignListeners(holder: OrderItem.ViewHolder, position: Int, item: OrderItem) {
        super.assignListeners(holder, position, item)

        item.setOnMarketNameClickListener(holder, resources, position, onMarketNameClickListener)
        item.setOnCancelBtnClickListener(holder, resources, position, onCancelBtnClickListener)
    }


    fun setOrderType(orderType: OrderTypes) {
        mResources.orderType = orderType
    }


    fun setCurrencyMarkets(currencyMarkets: Map<String, CurrencyMarket>) {
        mResources.currencyMarkets = currencyMarkets
    }


    fun getChronologicalPositionForOrder(order: Order, sortTypes: SortTypes): Int {
        return items.indices.firstOrNull {
            items[it].itemModel.compareTo(order) == (if(sortTypes == SortTypes.ASC) 1 else -1)
        } ?: itemCount
    }


    override fun getResources(): OrderResources? {
        return mResources
    }


}
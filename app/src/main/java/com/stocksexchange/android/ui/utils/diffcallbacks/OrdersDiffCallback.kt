package com.stocksexchange.android.ui.utils.diffcallbacks

import com.stocksexchange.android.api.model.Order
import com.stocksexchange.android.ui.orders.fragment.OrderItem

/**
 * A diff utility callback for [Order] model class.
 */
class OrdersDiffCallback(
    oldList: List<OrderItem>,
    newList: List<OrderItem>
) : BaseDiffCallback<Order, OrderItem>(oldList, newList) {


    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return (oldItem.id == newItem.id)
    }


}
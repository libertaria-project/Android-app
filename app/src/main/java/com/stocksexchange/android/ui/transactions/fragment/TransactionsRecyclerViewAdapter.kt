package com.stocksexchange.android.ui.transactions.fragment

import android.content.Context
import android.view.View
import com.arthurivanets.adapster.recyclerview.TrackableRecyclerViewAdapter
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.model.TransactionTypes

class TransactionsRecyclerViewAdapter(
    context: Context,
    settings: Settings,
    items: MutableList<TransactionItem>
) : TrackableRecyclerViewAdapter<Long, TransactionItem, TransactionItem.ViewHolder>(context, items) {


    private val mResources: TransactionResources = TransactionResources.newInstance(context, settings)

    /**
     * A listener used for notifying whenever a transaction address has been clicked.
     */
    var onTransactionAddressClickListener: ((View, TransactionItem, Int) -> Unit)? = null

    /**
     * A listener used for notifying whenever a transaction ID has been clicked.
     */
    var onTransactionIdClickListener: ((View, TransactionItem, Int) -> Unit)? = null




    override fun assignListeners(holder: TransactionItem.ViewHolder, position: Int, item: TransactionItem) {
        super.assignListeners(holder, position, item)

        with(item) {
            setOnTransactionAddressClickListener(holder, mResources, position, onTransactionAddressClickListener)
            setOnTransactionIdClickListener(holder, mResources, position, onTransactionIdClickListener)
        }
    }


    fun setTransactionType(type: TransactionTypes) {
        mResources.transactionType = type
    }


    override fun getResources(): TransactionResources? {
        return mResources
    }


}
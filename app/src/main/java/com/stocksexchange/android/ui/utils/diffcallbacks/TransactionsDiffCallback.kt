package com.stocksexchange.android.ui.utils.diffcallbacks

import com.stocksexchange.android.api.model.Transaction
import com.stocksexchange.android.ui.transactions.fragment.TransactionItem

/**
 * A diff utility callback for [Transaction] model class.
 */
class TransactionsDiffCallback(
    oldList: List<TransactionItem>,
    newList: List<TransactionItem>
) : BaseDiffCallback<Transaction, TransactionItem>(oldList, newList) {


    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return (oldItem.id == newItem.id)
    }


}
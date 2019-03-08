package com.stocksexchange.android.ui.utils.diffcallbacks

import androidx.recyclerview.widget.DiffUtil
import com.arthurivanets.adapster.model.BaseItem

/**
 * A base diff utility callback to extend from.
 */
abstract class BaseDiffCallback<M : Any, out IT : BaseItem<M, *, *>>(
    protected val oldList: List<IT>,
    protected val newList: List<IT>
) : DiffUtil.Callback() {


    override fun getOldListSize(): Int = oldList.size


    override fun getNewListSize(): Int = newList.size


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition].getItemModel() == newList[newItemPosition].getItemModel())
    }


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSame(oldList[oldItemPosition].getItemModel(), newList[newItemPosition].getItemModel())
    }


    protected abstract fun areItemsTheSame(oldItem: M, newItem: M): Boolean


}
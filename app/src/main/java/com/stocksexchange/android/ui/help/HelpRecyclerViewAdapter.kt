package com.stocksexchange.android.ui.help

import android.content.Context
import com.arthurivanets.adapster.recyclerview.TrackableRecyclerViewAdapter
import com.stocksexchange.android.model.Settings

class HelpRecyclerViewAdapter(
    context: Context,
    items: MutableList<HelpItem>
): TrackableRecyclerViewAdapter<Int, HelpItem, HelpItem.ViewHolder>(context, items) {


    private lateinit var mResources: HelpItemResources

    /**
     * A listener used for notifying whenever a particular item is clicked.
     */
    var onItemClickListener: ((HelpItem.ViewHolder, HelpItem, Int) -> Unit)? = null

    /**
     * A listener used for notifying whenever an action button is clicked.
     */
    var onActionButtonClickListener: ((HelpItem.ViewHolder, HelpItem, Int) -> Unit)? = null




    init {
        setHasStableIds(true)
    }


    override fun assignListeners(holder: HelpItem.ViewHolder, position: Int, item: HelpItem) {
        super.assignListeners(holder, position, item)

        with(item) {
            setOnItemClickListener(holder, position, onItemClickListener)
            setOnActionButtonClickListener(holder, position, onActionButtonClickListener)
        }
    }


    /**
     * Sets resources for the adapter.
     *
     * @param resources The resources to set
     */
    fun setResources(resources: HelpItemResources) {
        mResources = resources
    }


    override fun getResources(): HelpItemResources? {
        return mResources
    }


}
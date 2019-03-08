package com.stocksexchange.android.ui.utils.listeners.adapters

import androidx.recyclerview.widget.RecyclerView
import com.stocksexchange.android.ui.utils.listeners.RecyclerViewScrollListener

/**
 * An adapter for [RecyclerViewScrollListener.StateListener]. Primarily used for extending.
 */
interface RecyclerViewStateListenerAdapter : RecyclerViewScrollListener.StateListener {


    override fun onScrolledUpwards(recyclerView: RecyclerView, deltaY: Int) {
        // Stub
    }


    override fun onScrolledDownwards(recyclerView: RecyclerView, deltaY: Int) {
        // Stub
    }


    override fun onTopReached(recyclerView: RecyclerView, reachedCompletely: Boolean) {
        // Stub
    }


    override fun onMiddleReached(recyclerView: RecyclerView, direction: RecyclerViewScrollListener.Directions) {
        // Stub
    }


    override fun onBottomReached(recyclerView: RecyclerView, reachedCompletely: Boolean) {
        // Stub
    }


}
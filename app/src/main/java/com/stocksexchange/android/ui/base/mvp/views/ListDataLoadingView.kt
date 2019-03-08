package com.stocksexchange.android.ui.base.mvp.views

import com.stocksexchange.android.ui.utils.interfaces.CanObserveNetworkStateChanges
import com.stocksexchange.android.ui.utils.interfaces.Scrollable

/**
 * A base list data loading view to build views on.
 */
interface ListDataLoadingView<Data> : DataLoadingView<Data>, Scrollable,
    CanObserveNetworkStateChanges {


    /**
     * Sets a search query.
     *
     * @param query The search query
     */
    fun setSearchQuery(query: String)


    /**
     * Returns the size of the data set of the main view.
     *
     * @return The size of the data set
     */
    fun getDataSetSize(): Int


    /**
     * Retrieves a current search query.
     *
     * @return The search query
     */
    fun getSearchQuery(): String


}
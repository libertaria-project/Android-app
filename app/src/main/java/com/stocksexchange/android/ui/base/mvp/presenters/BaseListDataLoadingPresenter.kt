package com.stocksexchange.android.ui.base.mvp.presenters

import com.stocksexchange.android.model.DataLoadingSources
import com.stocksexchange.android.ui.base.mvp.model.BaseDataLoadingModel
import com.stocksexchange.android.ui.base.mvp.views.ListDataLoadingView

/**
 * A base list data loading presenter to build presenters on.
 */
abstract class BaseListDataLoadingPresenter<out Model, out View, Data, Parameters>(
    model: Model,
    view: View
) : BaseDataLoadingPresenter<Model, View, Data, Parameters>(model, view) where
        Model : BaseDataLoadingModel<Data, Parameters, *>,
        View : ListDataLoadingView<Data>,
        Data : Any,
        Parameters : Any {


    /**
     * A helper function that is responsible for showing
     * and hiding the proper views based on the adapter's
     * data set size.
     */
    open fun onDataSetSizeChanged(size: Int) {
        if(size > 0) {
            mView.hideInfoView()
            mView.hideProgressBar()
        } else {
            mView.showEmptyView()
        }
    }


    /**
     * Gets called whenever a search query has been changed.
     *
     * @param query The new query
     */
    open fun onSearchQueryChanged(query: String) {
        cancelDataLoading()
        reloadData(DataLoadingSources.SEARCH_QUERY_CHANGE)
    }


    /**
     * Gets called whenever a user has reached the bottom
     * of the data set.
     *
     * @param reachedCompletely Whether a user has reached the
     * bottom completely
     */
    open fun onBottomReached(reachedCompletely: Boolean) {
        // Stub
    }


    /**
     * Gets called whenever a user explicitly wants
     * to cancel the data loading.
     */
    open fun cancelDataLoading() {
        mModel.cancelDataLoading()
    }


}
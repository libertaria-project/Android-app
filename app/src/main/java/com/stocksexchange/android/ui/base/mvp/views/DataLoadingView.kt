package com.stocksexchange.android.ui.base.mvp.views

/**
 * A base data loading view to build views on.
 */
interface DataLoadingView<in Data> {


    /**
     * Shows the main view holding the loaded data.
     */
    fun showMainView()


    /**
     * Hides the main view holding the loaded data.
     */
    fun hideMainView()


    /**
     * Shows the empty view in case there is no data available.
     */
    fun showEmptyView()


    /**
     * Shows the error view in case an error occurred while loading data.
     */
    fun showErrorView()


    /**
     * Hides the info view (i.e., empty and error views).
     */
    fun hideInfoView()


    /**
     * Shows the progress bar to notify about the data loading start.
     */
    fun showProgressBar()


    /**
     * Hides the progress bar to notify about the data loading end.
     */
    fun hideProgressBar()


    /**
     * Shows the refresh progress bar to notify about the data loading start.
     */
    fun showRefreshProgressBar()


    /**
     * Hides the refresh progress bar to notify about the data loading start.
     */
    fun hideRefreshProgressBar()


    /**
     * Enables the refresh progress bar.
     */
    fun enableRefreshProgressBar()


    /**
     * Disables the refresh progress bar.
     */
    fun disableRefreshProgressBar()


    /**
     * Adds the data to the main view.
     *
     * @param data The data to supply to the main view
     */
    fun addData(data: Data)


    /**
     * Checks whether the data source is empty (i.e., whether the data
     * has already been downloaded or not).
     *
     * @return true if empty; false otherwise
     */
    fun isDataSourceEmpty(): Boolean


    /**
     * Checks whether the view is currently selected or not.
     *
     * @return true if selected; false otherwise
     */
    fun isViewSelected(): Boolean


}
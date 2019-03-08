package com.stocksexchange.android.ui.utils.interfaces

import com.stocksexchange.android.model.DataLoadingStates

/**
 * An interface to implement to mark a class to be capable
 * of loading the data.
 */
interface DataLoadable<in Data> {


    /**
     * This method is called to notify you that the data loading
     * has been started.
     */
    fun onDataLoadingStarted()


    /**
     * This method is called to notify you that the data loading
     * has been ended.
     */
    fun onDataLoadingEnded()


    /**
     * This method is called to notify you that the data loading
     * state has been changed.
     *
     * @param dataLoadingState One of data loading states. See [DataLoadingStates].
     */
    fun onDataLoadingStateChanged(dataLoadingState: DataLoadingStates)


    /**
     * This method is called to notify you that the data loading
     * has succeeded.
     *
     * @param data The actual data
     */
    fun onDataLoadingSucceeded(data: Data)


    /**
     * This method is called to notify you that the data loading
     * has failed.
     *
     * @param error The cause of the fail
     */
    fun onDataLoadingFailed(error: Throwable)


}
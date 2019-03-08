package com.stocksexchange.android.ui.utils.interfaces

import com.stocksexchange.android.model.DataLoadingStates

/**
 * An interface to implement to mark a class to be capable
 * of loading multiple types of data.
 */
interface MultipleDataLoadable {


    /**
     * This method is called to notify you that the data loading
     * of the given type has been started.
     *
     * @param dataType The type of data
     */
    fun onDataLoadingStarted(dataType: Int)


    /**
     * This method is called to notify you that the data loading
     * of the given type has been ended.
     *
     * @param dataType The type of data
     */
    fun onDataLoadingEnded(dataType: Int)


    /**
     * This method is called to notify you that the data loading
     * state of the given type has been changed.
     *
     * @param dataType The type of data
     * @param dataLoadingState One of data loading states. See [DataLoadingStates].
     */
    fun onDataLoadingStateChanged(dataType: Int, dataLoadingState: DataLoadingStates)


    /**
     * This method is called to notify you that the data loading
     * of the given type has succeeded.
     *
     * @param dataType The type of data
     * @param data The actual data
     */
    fun onDataLoadingSucceeded(dataType: Int, data: Any)


    /**
     * This method is called to notify you that the data loading
     * of the given type has failed.
     *
     * @param dataType The type of data
     * @param error The cause of the fail
     */
    fun onDataLoadingFailed(dataType: Int, error: Throwable)


}
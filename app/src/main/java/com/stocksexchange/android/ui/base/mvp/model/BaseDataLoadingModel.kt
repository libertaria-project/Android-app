package com.stocksexchange.android.ui.base.mvp.model

import com.stocksexchange.android.Constants
import com.stocksexchange.android.model.DataLoadingSources
import com.stocksexchange.android.model.DataLoadingStates
import com.stocksexchange.android.model.DataTypes
import com.stocksexchange.android.ui.base.mvp.model.BaseDataLoadingModel.BaseDataLoadingActionListener
import com.stocksexchange.android.ui.utils.interfaces.DataLoadable
import com.stocksexchange.android.utils.SavedState
import com.stocksexchange.android.utils.helpers.tag
import kotlinx.coroutines.Job

/**
 * A base data loading model to build model classes on.
 */
abstract class BaseDataLoadingModel<
    Data : Any,
    in Parameters : Any,
    ActionListener: BaseDataLoadingActionListener<Data>
> : BaseModel() {


    companion object {

        private val CLASS = BaseDataLoadingModel::class.java

        private val SAVED_STATE_WAS_LAST_DATA_FETCHING_SUCCESSFUL = tag(CLASS, "was_last_data_fetching_successful")
        private val SAVED_STATE_WAS_LAST_DATA_FETCHING_INITIATED_BY_USER = tag(CLASS, "was_last_data_fetching_initiated_by_user")
        private val SAVED_STATE_LAST_DATA_FETCHING_TIME = tag(CLASS, "last_data_fetching_time")

    }


    /**
     * A flag indicating whether the last data loading has been
     * successfully performed or not.
     */
    var wasLastDataFetchingSuccessful: Boolean = false
        private set

    /**
     * A flag indicating whether the last data loading has been
     * initiated by the user or not. Helpful in determining what
     * progress bar to hide (standard or refresh one), etc.
     */
    var wasLastDataFetchingInitiatedByTheUser: Boolean = false
        private set

    /**
     * A flag indicating whether the data is currently being
     * loaded or not.
     */
    var isDataLoading: Boolean = false
        private set

    /**
     * A flag indicating whether the data loading has been
     * cancelled or not.
     */
    var isDataLoadingCancelled: Boolean = false
        private set

    /**
     * A timestamp holding the time in milliseconds of the last
     * loading of new data.
     */
    protected var mLastDataFetchingTime: Long = 0L

    /**
     * A job responsible for loading the data. Needed in case
     * the data loading is cancelled.
     */
    protected var mDataLoadingJob: Job? = null

    /**
     * A listener to report events to a presenter.
     */
    protected var mActionListener: ActionListener? = null




    override fun stop() {
        cancelDataLoading()

        super.stop()
    }


    /**
     * Cancels the data loading (if one is being performed).
     */
    open fun cancelDataLoading() {
        if(mDataLoadingJob?.isActive == true) {
            isDataLoadingCancelled = true

            mDataLoadingJob?.cancel()
            onDataLoadingEnded()
        }
    }


    /**
     * Updates the right timestamp based on the last data type.
     */
    protected open fun updateDataFetchingTimestamp() {
        mLastDataFetchingTime = System.currentTimeMillis()
    }


    /**
     * Resets necessary parameters for data reloading.
     */
    open fun resetParameters() {
        wasLastDataFetchingSuccessful = false
        wasLastDataFetchingInitiatedByTheUser = false
        mLastDataFetchingTime = 0L
    }


    /**
     * Gets called in case the data loading has been successful.
     */
    open fun handleSuccessfulResponse(data: Data) {
        if(isDataLoadingCancelled) {
            return
        }

        wasLastDataFetchingSuccessful = true
        updateDataFetchingTimestamp()

        mActionListener?.onDataLoadingSucceeded(data)
        onDataLoadingEnded()
    }


    /**
     * Gets called in case the data loading has been unsuccessful.
     */
    open fun handleUnsuccessfulResponse(error: Throwable) {
        if(isDataLoadingCancelled) {
            return
        }

        updateDataFetchingTimestamp()

        onDataLoadingEnded()
        mActionListener?.onDataLoadingFailed(error)
    }


    /**
     * Gets called whenever the data loading has been started.
     */
    protected open fun onDataLoadingStarted() {
        isDataLoading = true

        mActionListener?.onDataLoadingStarted()
        mActionListener?.onDataLoadingStateChanged(DataLoadingStates.ACTIVE)
    }


    /**
     * Gets called whenever the data loading has been ended.
     */
    protected open fun onDataLoadingEnded() {
        isDataLoading = false

        mActionListener?.onDataLoadingEnded()
        mActionListener?.onDataLoadingStateChanged(DataLoadingStates.IDLE)
    }


    /**
     * Sets an action listener to speak with a presenter.
     *
     * @param actionListener The listener to set
     */
    open fun setActionListener(actionListener: ActionListener) {
        mActionListener = actionListener
    }


    /**
     * Checks whether the data loading interval is applied for
     * the last data type.
     */
    fun isDataLoadingIntervalApplied(): Boolean {
        return ((System.currentTimeMillis() - (mLastDataFetchingTime)) > Constants.MIN_DATA_REFRESHING_INTERVAL)
    }


    /**
     * Gets the data by changing the particular flags and returns
     * immediately if the data loading cannot be performed for some
     * reason (specifically, if [canLoadData] returns false).
     */
    fun getData(params: Parameters, dataType: DataTypes,
                dataLoadingSource: DataLoadingSources, wasInitiatedByTheUser: Boolean) {
        // Altering the states
        wasLastDataFetchingInitiatedByTheUser = wasInitiatedByTheUser
        isDataLoadingCancelled = false

        if(!canLoadData(params, dataType, dataLoadingSource)) {
            onDataLoadingEnded()
            return
        }

        if(dataType == DataTypes.NEW_DATA) {
            refreshData(params)
        }

        mDataLoadingJob = getDataAsync(params)
        onDataLoadingStarted()
    }


    /**
     * Override this method to check for specific conditions
     * under which the data loading cannot be performed.
     */
    open fun canLoadData(params: Parameters, dataType: DataTypes,
                         dataLoadingSource: DataLoadingSources): Boolean {
        val isNewDataWithIntervalNotApplied = ((dataType == DataTypes.NEW_DATA) && !isDataLoadingIntervalApplied())
        val isNetworkConnectivitySource = (dataLoadingSource == DataLoadingSources.NETWORK_CONNECTIVITY)

        return (!isNewDataWithIntervalNotApplied || isNetworkConnectivitySource)
    }


    /**
     * Gets called whenever data needs to be refreshed.
     */
    protected abstract fun refreshData(params: Parameters)


    /**
     * Should return a coroutine job where the data loading
     * itself is performed.
     */
    private fun getDataAsync(params: Parameters): Job {
        return createBgLaunchCoroutine {
            performDataLoading(params)
        }
    }


    /**
     * Should load data from a specific source based on the passed parameters.
     */
    protected abstract suspend fun performDataLoading(params: Parameters)


    override fun onRestoreState(savedState: SavedState) {
        with(savedState) {
            wasLastDataFetchingSuccessful = get(SAVED_STATE_WAS_LAST_DATA_FETCHING_SUCCESSFUL, false)
            wasLastDataFetchingInitiatedByTheUser = get(SAVED_STATE_WAS_LAST_DATA_FETCHING_INITIATED_BY_USER, false)
            mLastDataFetchingTime = get(SAVED_STATE_LAST_DATA_FETCHING_TIME, 0L)
        }
    }


    override fun onSaveState(savedState: SavedState) {
        with(savedState) {
            save(SAVED_STATE_WAS_LAST_DATA_FETCHING_SUCCESSFUL, wasLastDataFetchingSuccessful)
            save(SAVED_STATE_WAS_LAST_DATA_FETCHING_INITIATED_BY_USER, wasLastDataFetchingInitiatedByTheUser)
            save(SAVED_STATE_LAST_DATA_FETCHING_TIME, mLastDataFetchingTime)
        }
    }


    /**
     * A base listener to speak with presenter for data loading
     * related stuff.
     */
    interface BaseDataLoadingActionListener<in Data> : DataLoadable<Data>


}
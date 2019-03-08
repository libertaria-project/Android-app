package com.stocksexchange.android.ui.base.mvp.presenters

import androidx.annotation.CallSuper
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.model.DataLoadingSources
import com.stocksexchange.android.model.DataLoadingStates
import com.stocksexchange.android.model.DataTypes
import com.stocksexchange.android.model.HttpCodes
import com.stocksexchange.android.ui.base.mvp.model.BaseDataLoadingModel
import com.stocksexchange.android.ui.base.mvp.views.DataLoadingView
import com.stocksexchange.android.ui.utils.interfaces.CanObserveNetworkStateChanges
import com.stocksexchange.android.ui.utils.interfaces.DataLoadable
import com.stocksexchange.android.utils.SavedState
import com.stocksexchange.android.utils.helpers.tag
import retrofit2.HttpException

/**
 * A base data loading presenter to build presenters on.
 */
abstract class BaseDataLoadingPresenter<out Model, out View, Data, Parameters>(
    model: Model,
    view: View
) : BasePresenter<Model, View>(model, view),
    DataLoadable<Data>,
    CanObserveNetworkStateChanges where
        Model : BaseDataLoadingModel<Data, Parameters, *>,
        View : DataLoadingView<Data>,
        Data : Any,
        Parameters : Any {


    companion object {

        private val CLASS = BaseDataLoadingPresenter::class.java

        private val SAVED_STATE_IS_PREVIOUS_DATA_SET_EMPTY = tag(CLASS, "is_previous_data_set_empty")

    }


    /**
     * A flag indicating whether the previous data set is empty or not. Helpful in
     * determining whether to show the main view or not.
     */
    private var mIsPreviousDataSetEmpty: Boolean = true




    override fun start() {
        super.start()

        if(mView.isDataSourceEmpty()) {
            mView.hideMainView()
            mView.showEmptyView()
        }

        if(isDataLoadingAllowed()) {
            reloadData(DataLoadingSources.START)
        }
    }


    /**
     * Checks whether to allow the data loading to be performed or not.
     */
    protected fun isDataLoadingAllowed(): Boolean {
        return ((mView.isDataSourceEmpty() || !mModel.wasLastDataFetchingSuccessful)
                && !mModel.isDataLoading
                && mView.isViewSelected()
                && mModel.isDataLoadingIntervalApplied())
    }


    /**
     * Reloads the data by resetting the flags and changing
     * the view state.
     */
    protected open fun reloadData(dataLoadingSource: DataLoadingSources) {
        if(mModel.isDataLoading) {
            return
        }

        resetParameters()

        mView.hideMainView()
        mView.hideInfoView()

        loadData(
            if(dataLoadingSource == DataLoadingSources.START) {
                if(shouldLoadNewDataOnStart()) DataTypes.NEW_DATA else DataTypes.OLD_DATA
            } else {
                DataTypes.OLD_DATA
            },
            dataLoadingSource,
            false
        )
    }


    /**
     * Resets data loading parameters.
     */
    @CallSuper
    protected open fun resetParameters() {
        mModel.resetParameters()
    }


    /**
     * Should return a flag specifying whether to load new (server) data or not.
     */
    protected open fun shouldLoadNewData(): Boolean {
        return false
    }


    /**
     * Should return a flag specifying whether to load new (server) data on start or not.
     */
    protected open fun shouldLoadNewDataOnStart(): Boolean {
        return shouldLoadNewData()
    }


    /**
     * Loads the data by delegating the work to the model.
     */
    protected open fun loadData(dataType: DataTypes, dataLoadingSource: DataLoadingSources, wasInitiatedByTheUser: Boolean) {
        mIsPreviousDataSetEmpty = mView.isDataSourceEmpty()

        mModel.getData(getDataLoadingParams(), dataType, dataLoadingSource, wasInitiatedByTheUser)
    }


    /**
     * Should return parameters holding data necessary
     * for data loading.
     */
    protected abstract fun getDataLoadingParams(): Parameters


    override fun onDataLoadingStarted() {
        mView.hideInfoView()

        // Showing the proper progress bar
        if(mView.isDataSourceEmpty() && !mModel.wasLastDataFetchingInitiatedByTheUser) {
            mView.hideRefreshProgressBar()
            mView.disableRefreshProgressBar()
            mView.showProgressBar()
        } else {
            mView.showRefreshProgressBar()
        }
    }


    override fun onDataLoadingEnded() {
        // Hiding the proper progress bar
        if(mIsPreviousDataSetEmpty && !mModel.wasLastDataFetchingInitiatedByTheUser) {
            mView.hideProgressBar()
            mView.enableRefreshProgressBar()
        } else {
            mView.hideRefreshProgressBar()
        }

        // Just to make sure InfoView showing is handled
        if(mView.isDataSourceEmpty()) {
            mView.showEmptyView()
        } else {
            mView.hideInfoView()
            mView.hideProgressBar()
        }
    }


    override fun onDataLoadingSucceeded(data: Data) {
        mView.addData(data)
    }


    override fun onDataLoadingFailed(error: Throwable) {
        mView.hideInfoView()

        if(mView.isDataSourceEmpty()) {
            if(((error is HttpException) && (error.code() == HttpCodes.TOO_MANY_REQUESTS.code)) || (error is NotFoundException)) {
                mView.showEmptyView()
            } else {
                mView.showErrorView()
            }
        }
    }


    override fun onDataLoadingStateChanged(dataLoadingState: DataLoadingStates) {
        if(dataLoadingState == DataLoadingStates.IDLE) {
            if(mIsPreviousDataSetEmpty && !mView.isDataSourceEmpty() && !mModel.isDataLoadingCancelled) {
                mView.showMainView()
            }
        }
    }


    /**
     * Gets called whenever a view becomes visible to the user on the screen.
     */
    open fun onViewSelected() {
        if(isDataLoadingAllowed()) {
            loadData(
                if(shouldLoadNewDataOnViewSelection()) {
                    DataTypes.NEW_DATA
                } else {
                    DataTypes.OLD_DATA
                },
                DataLoadingSources.VIEW_SELECTION,
                false
            )
        }
    }


    /**
     * Should return a flag specifying whether to load new (server) data on the view
     * selection or not.
     */
    protected open fun shouldLoadNewDataOnViewSelection(): Boolean {
        return shouldLoadNewData()
    }


    /**
     * Gets called in case user explicitly specified (e.g., pulled a swipe to refresh
     * progress bar) that he/she wants to refresh data.
     */
    fun onRefreshData() {
        loadData(DataTypes.NEW_DATA, DataLoadingSources.REFRESHMENT, true)
    }


    override fun onNetworkConnected() {
        if(shouldLoadDataOnNetworkConnectivity()) {
            loadData(DataTypes.NEW_DATA, DataLoadingSources.NETWORK_CONNECTIVITY, false)
        }
    }


    /**
     * Checks whether to allow the data loading to be performed or not when
     * the network has become available.
     */
    protected fun shouldLoadDataOnNetworkConnectivity(): Boolean {
        return ((mView.isDataSourceEmpty() || !mModel.wasLastDataFetchingSuccessful)
                && mView.isViewSelected()
                && !mModel.isDataLoading)
    }


    override fun onNetworkDisconnected() {
        // Stub
    }


    override fun onRestoreState(savedState: SavedState) {
        super.onRestoreState(savedState)

        with(savedState) {
            mIsPreviousDataSetEmpty = get(SAVED_STATE_IS_PREVIOUS_DATA_SET_EMPTY, true)
        }
    }


    override fun onSaveState(savedState: SavedState) {
        super.onSaveState(savedState)

        with(savedState) {
            save(SAVED_STATE_IS_PREVIOUS_DATA_SET_EMPTY, mIsPreviousDataSetEmpty)
        }
    }


}
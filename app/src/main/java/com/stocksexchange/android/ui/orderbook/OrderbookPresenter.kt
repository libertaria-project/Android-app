package com.stocksexchange.android.ui.orderbook

import com.stocksexchange.android.api.model.Orderbook
import com.stocksexchange.android.api.model.OrderbookOrder
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.events.OrderbookDataReloadEvent
import com.stocksexchange.android.events.OrderbookDataUpdateEvent
import com.stocksexchange.android.events.RealTimeDataUpdateEvent
import com.stocksexchange.android.model.*
import com.stocksexchange.android.ui.base.mvp.presenters.BasePresenter
import com.stocksexchange.android.utils.SavedState
import com.stocksexchange.android.utils.helpers.tag
import com.stocksexchange.android.utils.providers.ConnectionProvider
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.standalone.inject

class OrderbookPresenter(
    model: OrderbookModel,
    view: OrderbookContract.View
) : BasePresenter<OrderbookModel, OrderbookContract.View>(model, view), OrderbookContract.ActionListener,
    OrderbookModel.ActionListener {


    companion object {

        private val CLASS = OrderbookPresenter::class.java


        private val SAVED_STATE_IS_REAL_TIME_DATA_UPDATE_EVENT_RECEIVED = tag(CLASS, "is_real_time_data_update_event_received")
        private val SAVED_STATE_IS_REAL_TIME_DATA_LOSS_UPDATE_PENDING = tag(CLASS, "is_real_time_data_loss_update_pending")
        private val SAVED_STATE_IS_DATA_LOADING_PERFORMED = tag(CLASS, "is_data_loading_performed")

    }


    private var mIsRealTimeDataUpdateEventReceived = false
    private var mIsRealTimeDataLossUpdatePending = false
    private var mIsDataLoadingPerformed = false

    private val mConnectionProvider: ConnectionProvider by inject()




    init {
        model.setActionListener(this)
    }


    constructor(view: OrderbookContract.View): this(OrderbookModel(), view)


    override fun start() {
        super.start()

        if(mView.isOrderbookEmpty()) {
            loadData(DataTypes.OLD_DATA)
        }
    }


    private fun loadData(dataType: DataTypes) {
        if(mModel.isDataLoading) {
            return
        }

        mView.hideMainView()
        mView.hideInfoView()

        mModel.getData(
            params = mView.getOrderbookParameters(),
            dataType = dataType,
            dataLoadingSource = DataLoadingSources.OTHER,
            wasInitiatedByTheUser = false
        )
    }


    override fun onDataLoadingStarted() {
        disableAppBarScrolling()
        mView.showProgressBar()
    }


    override fun onDataLoadingEnded() {
        updateAppBarScrollingState()
        mView.hideProgressBar()

        if(mView.isOrderbookEmpty()) {
            mView.showEmptyView()
        } else {
            mView.hideInfoView()
        }
    }


    override fun onDataLoadingStateChanged(dataLoadingState: DataLoadingStates) {
        if(dataLoadingState == DataLoadingStates.IDLE) {
            if(!mView.isOrderbookEmpty() && !mModel.isDataLoadingCancelled) {
                mView.bindData()
                mView.showMainView()
            }
        }
    }


    override fun onDataLoadingSucceeded(data: Orderbook) {
        mIsDataLoadingPerformed = true

        mView.setData(
            info = OrderbookInfo.newInstance(data),
            orderbook = data,
            shouldBindData = false
        )
    }


    override fun onDataLoadingFailed(error: Throwable) {
        mIsDataLoadingPerformed = true

        if(error is NotFoundException) {
            mView.showEmptyView()
        } else {
            mView.showErrorView()
        }
    }


    override fun onLeftButtonClicked() {
        mView.onBackPressed()
    }


    private fun updateAppBarScrollingState() {
        if(mView.isOrderbookEmpty()) {
            disableAppBarScrolling()
        } else {
            enableAppBarScrolling()
        }
    }


    private fun enableAppBarScrolling() {
        if(!mView.isAppBarScrollingEnabled()) {
            mView.enableAppBarScrolling()
        }
    }


    private fun disableAppBarScrolling() {
        if(mView.isAppBarScrollingEnabled()) {
            mView.disableAppBarScrolling()
        }
    }


    override fun onNetworkConnected() {
        if(mIsRealTimeDataLossUpdatePending) {
            performRealTimeDataLossUpdate()
        }
    }


    override fun onBackButtonPressed() {
        EventBus.getDefault().postSticky(if(mIsRealTimeDataUpdateEventReceived) {
            RealTimeDataUpdateEvent.newInstance(this)
        } else {
            OrderbookDataReloadEvent.newInstance(this)
        })
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: RealTimeDataUpdateEvent) {
        if(event.isOriginatedFrom(this) || event.isConsumed) {
            return
        }

        if(mConnectionProvider.isNetworkAvailable()) {
            performRealTimeDataLossUpdate()
        } else {
            mIsRealTimeDataLossUpdatePending = true
        }

        mIsRealTimeDataUpdateEventReceived = true

        event.consume()
    }


    private fun performRealTimeDataLossUpdate() {
        mIsRealTimeDataLossUpdatePending = false
        mIsDataLoadingPerformed = false

        mView.showAppBar(false)
        mView.scrollOrderbookViewToTop()

        loadData(DataTypes.NEW_DATA)
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: OrderbookDataUpdateEvent) {
        if(event.isOriginatedFrom(this) || event.isConsumed) {
            return
        }

        updateData(event.attachment, event.dataActionItems)

        event.consume()
    }


    private fun updateData(orderbook: Orderbook,
                           dataActionItems: List<DataActionItem<OrderbookOrder>>) {
        if(!mIsDataLoadingPerformed) {
            return
        }

        val wasOrderbookEmpty = mView.isOrderbookEmpty()

        mView.updateData(
            info = OrderbookInfo.newInstance(orderbook),
            orderbook = orderbook,
            dataActionItems = dataActionItems
        )

        val isOrderbookEmpty = mView.isOrderbookEmpty()

        if(wasOrderbookEmpty && !isOrderbookEmpty) {
            mView.hideInfoView()
            mView.showMainView()
        } else if(isOrderbookEmpty) {
            mView.hideMainView()
            mView.showEmptyView()
        }

        updateAppBarScrollingState()
    }


    override fun canReceiveEvents(): Boolean {
        return true
    }


    override fun onRestoreState(savedState: SavedState) {
        super.onRestoreState(savedState)

        with(savedState) {
            mIsRealTimeDataUpdateEventReceived = get(SAVED_STATE_IS_REAL_TIME_DATA_UPDATE_EVENT_RECEIVED, false)
            mIsRealTimeDataLossUpdatePending = get(SAVED_STATE_IS_REAL_TIME_DATA_LOSS_UPDATE_PENDING, false)
            mIsDataLoadingPerformed = get(SAVED_STATE_IS_DATA_LOADING_PERFORMED, false)
        }
    }


    override fun onSaveState(savedState: SavedState) {
        super.onSaveState(savedState)

        with(savedState) {
            save(SAVED_STATE_IS_REAL_TIME_DATA_UPDATE_EVENT_RECEIVED, mIsRealTimeDataUpdateEventReceived)
            save(SAVED_STATE_IS_REAL_TIME_DATA_LOSS_UPDATE_PENDING, mIsRealTimeDataLossUpdatePending)
            save(SAVED_STATE_IS_DATA_LOADING_PERFORMED, mIsDataLoadingPerformed)
        }
    }


    override fun toString(): String {
        return "${super.toString()}_${mView.getCurrencyMarket().name}"
    }


}
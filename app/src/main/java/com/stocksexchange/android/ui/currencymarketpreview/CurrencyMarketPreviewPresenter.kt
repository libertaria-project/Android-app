package com.stocksexchange.android.ui.currencymarketpreview

import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.*
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.events.*
import com.stocksexchange.android.events.utils.handleCurrencyMarketEvent
import com.stocksexchange.android.model.*
import com.stocksexchange.android.model.CurrencyMarketPreviewDataSources.*
import com.stocksexchange.android.ui.base.mvp.presenters.BasePresenter
import com.stocksexchange.android.utils.SavedState
import com.stocksexchange.android.utils.helpers.tag
import com.stocksexchange.android.utils.providers.ConnectionProvider
import com.stocksexchange.android.utils.providers.DimensionProvider
import com.stocksexchange.android.utils.providers.StringProvider
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.standalone.inject

class CurrencyMarketPreviewPresenter(
    model: CurrencyMarketPreviewModel,
    view: CurrencyMarketPreviewContract.View
) : BasePresenter<CurrencyMarketPreviewModel, CurrencyMarketPreviewContract.View>(model, view),
    CurrencyMarketPreviewContract.ActionListener, CurrencyMarketPreviewModel.ActionListener {


    companion object {

        private val CLASS = CurrencyMarketPreviewPresenter::class.java

        private val SAVED_STATE_IS_REAL_TIME_DATA_UPDATE_EVENT_RECEIVED = tag(CLASS, "is_real_time_data_update_event_received")
        private val SAVED_STATE_IS_REAL_TIME_DATA_LOSS_UPDATE_PENDING = tag(CLASS, "is_real_time_data_loss_update_pending")
        private val SAVED_STATE_IS_DATA_LOADING_PERFORMED_MAP = tag(CLASS, "is_data_loading_performed_map")
        private val SAVED_STATE_PERFORMED_ACTIONS = tag(CLASS, "performed_actions")

    }


    private var mIsRealTimeDataUpdateEventReceived = false
    private var mIsRealTimeDataLossUpdatePending = false

    private var mIsDataLoadingPerformedMap: MutableMap<CurrencyMarketPreviewDataSources, Boolean> = mutableMapOf()
    private var mMainViewsToShowMap: MutableMap<CurrencyMarketPreviewDataSources, Boolean> = mutableMapOf()
    private var mMainViewsShowingMap: MutableMap<CurrencyMarketPreviewDataSources, Boolean> = mutableMapOf()

    private var mPerformedActions: PerformedCurrencyMarketActions = PerformedCurrencyMarketActions()

    private val mStringProvider: StringProvider by inject()
    private val mDimensionProvider: DimensionProvider by inject()
    private val mConnectionProvider: ConnectionProvider by inject()




    init {
        model.setActionListener(this)
    }


    constructor(view: CurrencyMarketPreviewContract.View): this(CurrencyMarketPreviewModel(), view)


    override fun start() {
        super.start()

        for(dataSource in CurrencyMarketPreviewDataSources.values()) {
            if(mView.isViewSelected(dataSource) && mView.isDataEmpty(dataSource)) {
                loadData(dataSource)
            }
        }
    }


    private fun loadData(dataSource: CurrencyMarketPreviewDataSources) {
        if(mModel.isDataLoadingMap[dataSource] == true) {
            return
        }

        mView.hideMainView(dataSource)
        mView.hideInfoView(dataSource)

        mModel.loadData(
            getTypeForLoadingData(dataSource),
            mView.getDataParameters(dataSource),
            dataSource
        )
    }


    private fun getTypeForLoadingData(dataSource: CurrencyMarketPreviewDataSources): DataTypes {
        return if(isDataLoadingPerformed(dataSource)) DataTypes.OLD_DATA else DataTypes.NEW_DATA
    }


    private fun setDataLoadingPerformed(isDataLoadingPerformed: Boolean,
                                        dataSource: CurrencyMarketPreviewDataSources) {
        mIsDataLoadingPerformedMap[dataSource] = isDataLoadingPerformed

        if(dataSource == ORDERBOOK) {
            mIsDataLoadingPerformedMap[DEPTH_CHART] = isDataLoadingPerformed
        } else if(dataSource == DEPTH_CHART) {
            mIsDataLoadingPerformedMap[ORDERBOOK] = isDataLoadingPerformed
        }
    }


    private fun isDataLoadingPerformed(dataSource: CurrencyMarketPreviewDataSources): Boolean {
        if((dataSource == DEPTH_CHART) || (dataSource == ORDERBOOK)) {
            return (mIsDataLoadingPerformedMap[DEPTH_CHART] == true)
                || (mIsDataLoadingPerformedMap[ORDERBOOK] == true)
        }

        return (mIsDataLoadingPerformedMap[dataSource] == true)
    }


    private fun resetDataLoadingPerformedFlags() {
        for(dataSource in CurrencyMarketPreviewDataSources.values()) {
            mIsDataLoadingPerformedMap[dataSource] = false
        }
    }


    override fun onDataLoadingStarted(dataSource: CurrencyMarketPreviewDataSources) {
        mView.showProgressBar(dataSource)
    }


    override fun onDataLoadingEnded(dataSource: CurrencyMarketPreviewDataSources) {
        if(!isMainViewCurrentlyShowing() || mView.isDataEmpty(dataSource)) {
            mView.hideProgressBar(dataSource)
        }

        if(mView.isDataEmpty(dataSource)) {
            mView.showEmptyView(dataSource)
        } else {
            mView.hideInfoView(dataSource)
        }
    }


    override fun onDataLoadingStateChanged(state: DataLoadingStates, dataSource: CurrencyMarketPreviewDataSources) {
        if(state == DataLoadingStates.IDLE) {
            if(!mView.isDataEmpty(dataSource) && (mModel.isDataLoadingCancelledMap[dataSource] == false)) {
                handleMainViewShowing(dataSource)
            }
        }
    }


    override fun onDataLoadingSucceeded(data: Any, dataSource: CurrencyMarketPreviewDataSources) {
        setDataLoadingPerformed(true, dataSource)

        mView.setData(data, false, dataSource)
    }


    override fun onDataLoadingFailed(error: Throwable, dataSource: CurrencyMarketPreviewDataSources) {
        setDataLoadingPerformed(true, dataSource)

        if(error is NotFoundException) {
            mView.showEmptyView(dataSource)
        } else {
            mView.showErrorView(dataSource)
        }
    }


    private fun handleMainViewShowing(dataSource: CurrencyMarketPreviewDataSources) {
        if(isMainViewCurrentlyShowing()) {
            mMainViewsToShowMap[dataSource] = true
        } else {
            mView.bindData(dataSource)
            mView.hideProgressBar(dataSource)
            mView.showMainView(dataSource)
        }
    }


    private fun showNextMainViewIfNecessary() {
        for(entry in mMainViewsToShowMap) {
            if(entry.value) {
                mMainViewsToShowMap.remove(entry.key)
                handleMainViewShowing(entry.key)

                return
            }
        }
    }


    override fun onMainViewShowingStarted(dataSource: CurrencyMarketPreviewDataSources) {
        mMainViewsShowingMap[dataSource] = true
    }


    override fun onMainViewShowingEnded(dataSource: CurrencyMarketPreviewDataSources) {
        mMainViewsShowingMap[dataSource] = false

        showNextMainViewIfNecessary()
    }


    private fun isMainViewCurrentlyShowing(): Boolean {
        for(entry in mMainViewsShowingMap.entries) {
            if(entry.value) {
                return true
            }
        }

        return false
    }


    override fun onBackButtonClicked() {
        if(!mPerformedActions.isEmpty()) {
            EventBus.getDefault().postSticky(PerformedCurrencyMarketsActionsEvent.init(
                mPerformedActions, this
            ))
        }

        if(mView.isAuthenticationPreviousActivity()) {
            EventBus.getDefault().postSticky(UserEvent.signIn(this))
        }

        if(mIsRealTimeDataUpdateEventReceived) {
            EventBus.getDefault().postSticky(RealTimeDataUpdateEvent.newInstance(this))
        }
    }


    override fun onTradeButtonClicked(orderTradeType: OrderTradeTypes) {
        val currencyMarket = mView.getCurrencyMarket()

        if(!currencyMarket.isActive) {
            mView.showToast(mStringProvider.getString(R.string.error_exchange_disabled))
            return
        }

        val user = mView.getUser()

        if(user == null) {
            mView.launchLoginActivity()
        } else {
            when(orderTradeType) {
                OrderTradeTypes.BUY -> mView.launchBuyActivity(currencyMarket, user)
                OrderTradeTypes.SELL -> mView.launchSellActivity(currencyMarket, user)
            }
        }
    }


    override fun onNetworkConnected() {
        if(mIsRealTimeDataLossUpdatePending) {
            performRealTimeDataLossUpdate()
        }
    }


    override fun onLeftButtonClicked() {
        mView.onBackPressed()
    }


    override fun onRightButtonClicked() {
        mModel.handleFavoriteAction(mView.getCurrencyMarket())
    }


    override fun onScrollViewTopReached() {
        mView.setToolbarElevation(0f)
    }


    override fun onScrollViewScrolledDownward() {
        mView.setToolbarElevation(mDimensionProvider.getDimension(R.dimen.toolbar_elevation))
    }


    override fun onPriceChartSelected() {
        onViewSelected(DEPTH_CHART, PRICE_CHART)
    }


    override fun onDepthChartSelected() {
        onViewSelected(PRICE_CHART, DEPTH_CHART)
    }


    private fun onViewSelected(oldDataSource: CurrencyMarketPreviewDataSources,
                               newDataSource: CurrencyMarketPreviewDataSources) {
        mView.hideView(oldDataSource)

        mView.hideMainView(newDataSource)
        mView.showProgressBar(newDataSource)
        mView.showView(newDataSource)

        mModel.cancelDataLoadingAndWait(oldDataSource) {
            if(mView.isDataEmpty(newDataSource) || !isDataLoadingPerformed(newDataSource)) {
                loadData(newDataSource)
            } else {
                mView.hideProgressBar(newDataSource)
                mView.showMainView(newDataSource)
            }
        }
    }


    override fun onChartPressed() {
        mView.disableScrollViewScrolling()
    }


    override fun onChartReleased() {
        mView.enableScrollViewScrolling()
    }


    override fun onPriceChartDataIntervalChanged(dataInterval: PriceChartDataIntervals) {
        mMainViewsToShowMap[PRICE_CHART] = false

        mView.clearData(PRICE_CHART)
        mModel.cancelDataLoadingAndWait(PRICE_CHART) {
            loadData(PRICE_CHART)
        }

        mView.updatePriceChartWebSocketData(dataInterval)
    }


    override fun onDepthChartDepthLevelChanged(newDepthLevel: Int) {
        mMainViewsToShowMap[DEPTH_CHART] = false

        mView.clearData(DEPTH_CHART)
        mModel.cancelDataLoadingAndWait(DEPTH_CHART) {
            loadData(DEPTH_CHART)
        }
    }


    override fun onOrderbookSelected() {
        onViewSelected(TRADES, ORDERBOOK)
    }


    override fun onTradesSelected() {
        onViewSelected(ORDERBOOK, TRADES)
    }


    override fun onOrderbookHeaderMoreButtonClicked() {
        mView.launchOrderbookActivity()
    }


    override fun onCurrencyMarketFavorited(currencyMarket: CurrencyMarket) {
        mView.updateFavoriteButtonState(true)

        handleCurrencyMarketEvent(CurrencyMarketEvent.favorite(currencyMarket, this), mPerformedActions)
    }


    override fun onCurrencyMarketUnfavorited(currencyMarket: CurrencyMarket) {
        mView.updateFavoriteButtonState(false)

        handleCurrencyMarketEvent(CurrencyMarketEvent.unfavorite(currencyMarket, this), mPerformedActions)
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
        resetDataLoadingPerformedFlags()

        for(dataSource in CurrencyMarketPreviewDataSources.values()) {
            if(mView.isViewSelected(dataSource)) {
                loadData(dataSource)
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: OrderbookDataReloadEvent) {
        if(event.isOriginatedFrom(this) || event.isConsumed) {
            return
        }

        for(dataSource in listOf(DEPTH_CHART, ORDERBOOK)) {
            if(mView.isViewSelected(dataSource)) {
                loadData(dataSource)
            }
        }

        event.consume()
    }


    @Suppress("NON_EXHAUSTIVE_WHEN")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: CurrencyMarketEvent) {
        if(event.isOriginatedFrom(this)) {
            return
        }

        when(event.action) {
            CurrencyMarketEvent.Actions.UPDATED -> {
                val attachment = event.attachment

                if(attachment.id == mView.getCurrencyMarket().id) {
                    mView.updateCurrencyMarket(attachment)
                }

                mPerformedActions.addUpdatedCurrencyMarket(attachment)
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: OrderbookDataUpdateEvent) {
        if(event.isOriginatedFrom(this) || event.isConsumed) {
            return
        }

        updateData(event.attachment, event.dataActionItems, DEPTH_CHART)
        updateData(event.attachment, event.dataActionItems, ORDERBOOK)

        event.consume()
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: TradesDataUpdateEvent) {
        if(event.isOriginatedFrom(this) || event.isConsumed) {
            return
        }

        updateData(event.attachment, event.dataActionItems, TRADES)

        event.consume()
    }


    private fun updateData(newData: Any, dataActionItems: List<Any>,
                           dataSource: CurrencyMarketPreviewDataSources) {
        if(!isDataLoadingPerformed(dataSource)) {
            return
        }

        val wasDataEmpty = mView.isDataEmpty(dataSource)

        mView.updateData(newData, dataActionItems, dataSource)

        val isDataEmpty = mView.isDataEmpty(dataSource)

        if(wasDataEmpty && !isDataEmpty) {
            mView.hideInfoView(dataSource)
            mView.showMainView(dataSource)
        } else if(isDataEmpty) {
            mView.hideMainView(dataSource)
            mView.showEmptyView(dataSource)
        }
    }


    override fun canReceiveEvents(): Boolean {
        return true
    }


    @Suppress("UNCHECKED_CAST")
    override fun onRestoreState(savedState: SavedState) {
        super.onRestoreState(savedState)

        with(savedState) {
            mIsRealTimeDataUpdateEventReceived = get(SAVED_STATE_IS_REAL_TIME_DATA_UPDATE_EVENT_RECEIVED, false)
            mIsRealTimeDataLossUpdatePending = get(SAVED_STATE_IS_REAL_TIME_DATA_LOSS_UPDATE_PENDING, false)
            mIsDataLoadingPerformedMap = get(SAVED_STATE_IS_DATA_LOADING_PERFORMED_MAP, mutableMapOf())
            mPerformedActions = get(SAVED_STATE_PERFORMED_ACTIONS, PerformedCurrencyMarketActions())
        }
    }


    override fun onSaveState(savedState: SavedState) {
        super.onSaveState(savedState)

        with(savedState) {
            save(SAVED_STATE_IS_REAL_TIME_DATA_UPDATE_EVENT_RECEIVED, mIsRealTimeDataUpdateEventReceived)
            save(SAVED_STATE_IS_REAL_TIME_DATA_LOSS_UPDATE_PENDING, mIsRealTimeDataLossUpdatePending)
            save(SAVED_STATE_IS_DATA_LOADING_PERFORMED_MAP, mIsDataLoadingPerformedMap)
            save(SAVED_STATE_PERFORMED_ACTIONS, mPerformedActions)
        }
    }


    override fun toString(): String {
        return "${super.toString()}_${mView.getCurrencyMarket().name}"
    }


}
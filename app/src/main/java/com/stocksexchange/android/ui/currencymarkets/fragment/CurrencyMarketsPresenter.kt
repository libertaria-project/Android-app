package com.stocksexchange.android.ui.currencymarkets.fragment

import com.stocksexchange.android.Constants
import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.events.CurrencyMarketEvent
import com.stocksexchange.android.events.CurrencyMarketsUpdateEvent
import com.stocksexchange.android.events.PerformedCurrencyMarketsActionsEvent
import com.stocksexchange.android.events.RealTimeDataUpdateEvent
import com.stocksexchange.android.events.utils.handlePerformedCurrencyMarketsActionsEvent
import com.stocksexchange.android.model.*
import com.stocksexchange.android.ui.base.mvp.presenters.BaseListDataLoadingPresenter
import com.stocksexchange.android.utils.SavedState
import com.stocksexchange.android.utils.helpers.tag
import com.stocksexchange.android.utils.providers.ConnectionProvider
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.standalone.inject

class CurrencyMarketsPresenter(
    model: CurrencyMarketsModel,
    view: CurrencyMarketsContract.View
) : BaseListDataLoadingPresenter<
    CurrencyMarketsModel,
    CurrencyMarketsContract.View,
    List<CurrencyMarket>,
    CurrencyMarketParameters
>(model, view), CurrencyMarketsContract.ActionListener, CurrencyMarketsModel.ActionListener {


    companion object {

        private val CLASS = CurrencyMarketsPresenter::class.java

        private val SAVED_STATE_IS_REAL_TIME_DATA_UPDATE_EVENT_RECEIVED = tag(CLASS, "is_real_time_data_update_event_received")
        private val SAVED_STATE_IS_REAL_TIME_DATA_LOSS_UPDATE_PENDING = tag(CLASS, "is_real_time_data_loss_update_pending")
        private val SAVED_STATE_PERFORMED_ACTIONS = tag(CLASS, "performed_actions")

    }


    private var mIsRealTimeDataUpdateEventReceived = false
    private var mIsRealTimeDataLossUpdatePending = false

    private var mPerformedActions = PerformedCurrencyMarketActions()

    private val mConnectionProvider: ConnectionProvider by inject()




    init {
        model.setActionListener(this)
    }


    constructor(view: CurrencyMarketsContract.View): this(CurrencyMarketsModel(), view)


    private fun performRealTimeDataLossUpdate(dataType: DataTypes) {
        mIsRealTimeDataLossUpdatePending = false

        loadData(dataType, DataLoadingSources.REAL_TIME_DATA_LOSS, false)
    }


    private fun performRealTimeDataLossUpdateIfNecessary(dataType: DataTypes) {
        if(mView.isDataSourceEmpty()) {
            return
        }

        if(mConnectionProvider.isNetworkAvailable()) {
            performRealTimeDataLossUpdate(dataType)
        } else {
            mIsRealTimeDataLossUpdatePending = true
        }
    }


    override fun getDataLoadingParams(): CurrencyMarketParameters = mView.getCurrencyMarketsParameters()


    override fun onViewSelected() {
        if(isDataLoadingAllowed()) {
            super.onViewSelected()
            return
        }

        mView.sortDataSetIfNecessary()
    }


    override fun onNetworkConnected() {
        if(mIsRealTimeDataLossUpdatePending) {
            performRealTimeDataLossUpdate(DataTypes.NEW_DATA)
        }
    }


    override fun onCurrencyMarketItemClicked(currencyMarket: CurrencyMarket) {
        mView.launchCurrencyMarketPreviewActivity(currencyMarket)
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onEvent(event: PerformedCurrencyMarketsActionsEvent) {
        if(event.isOriginatedFrom(this) || event.isConsumed) {
            return
        }

        val currencyMarketType = mView.getCurrencyMarketsParameters().currencyMarketType

        handlePerformedCurrencyMarketsActionsEvent(event, event)

        // Merging with this fragment's actions in order to pass it
        // backwards in the stack
        if((currencyMarketType == CurrencyMarketTypes.FAVORITES) ||
            (currencyMarketType == CurrencyMarketTypes.SEARCH)) {
            mPerformedActions.merge(event.attachment)
        }

        event.consume()
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: CurrencyMarketEvent) {
        if(event.isOriginatedFrom(this)) {
            return
        }

        val currencyMarketType = mView.getCurrencyMarketsParameters().currencyMarketType
        val currencyMarket = event.attachment

        when(event.action) {

            CurrencyMarketEvent.Actions.UPDATED -> {
                mView.getMarketIndexForMarketId(currencyMarket.id)?.apply {
                    mView.updateItemWith(currencyMarket, this)
                }

                if((currencyMarketType == CurrencyMarketTypes.FAVORITES) ||
                    (currencyMarketType == CurrencyMarketTypes.SEARCH)) {
                    mPerformedActions.addUpdatedCurrencyMarket(currencyMarket)
                }
            }

            CurrencyMarketEvent.Actions.FAVORITED -> {
                if(currencyMarketType == CurrencyMarketTypes.FAVORITES) {
                    if(!mView.containsCurrencyMarket(currencyMarket)) {
                        mView.addCurrencyMarketChronologically(currencyMarket)
                    }
                }
            }

            CurrencyMarketEvent.Actions.UNFAVORITED -> {
                if(currencyMarketType == CurrencyMarketTypes.FAVORITES) {
                    if(mView.containsCurrencyMarket(currencyMarket)) {
                        mView.deleteCurrencyMarket(currencyMarket)
                    }
                }
            }

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: RealTimeDataUpdateEvent) {
        if(event.isOriginatedFrom(this) || event.isConsumed) {
            return
        }

        val type = getDataLoadingParams().currencyMarketType

        if((type == CurrencyMarketTypes.FAVORITES) || (type == CurrencyMarketTypes.SEARCH)) {
            mIsRealTimeDataUpdateEventReceived = true
        } else {
            EventBus.getDefault().postSticky(CurrencyMarketsUpdateEvent.newInstance(
                event.sourceTag,
                Constants.DASHBOARD_ACTIVITY_TAB_COUNT
            ))
        }

        event.consume()
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: CurrencyMarketsUpdateEvent) {
        if(event.isOriginatedFrom(this) || event.isConsumed(this) || event.isExhausted()) {
            return
        }

        performRealTimeDataLossUpdateIfNecessary(if(event.getCurrentConsumerCount() == 0) {
            DataTypes.NEW_DATA
        } else {
            DataTypes.OLD_DATA
        })

        event.consume(this)
    }


    override fun canReceiveEvents(): Boolean {
        return true
    }


    override fun onBackPressed() {
        if(!mPerformedActions.isEmpty()) {
            EventBus.getDefault().postSticky(PerformedCurrencyMarketsActionsEvent.init(
                mPerformedActions, this
            ))
        }

        if(mIsRealTimeDataUpdateEventReceived) {
            EventBus.getDefault().postSticky(RealTimeDataUpdateEvent.newInstance(this))
        }
    }


    @Suppress("UNCHECKED_CAST")
    override fun onRestoreState(savedState: SavedState) {
        super.onRestoreState(savedState)

        with(savedState) {
            mIsRealTimeDataUpdateEventReceived = get(SAVED_STATE_IS_REAL_TIME_DATA_UPDATE_EVENT_RECEIVED, false)
            mIsRealTimeDataLossUpdatePending = get(SAVED_STATE_IS_REAL_TIME_DATA_LOSS_UPDATE_PENDING, false)
            mPerformedActions = get(SAVED_STATE_PERFORMED_ACTIONS, PerformedCurrencyMarketActions())
        }
    }


    override fun onSaveState(savedState: SavedState) {
        super.onSaveState(savedState)

        with(savedState) {
            save(SAVED_STATE_IS_REAL_TIME_DATA_UPDATE_EVENT_RECEIVED, mIsRealTimeDataUpdateEventReceived)
            save(SAVED_STATE_IS_REAL_TIME_DATA_LOSS_UPDATE_PENDING, mIsRealTimeDataLossUpdatePending)
            save(SAVED_STATE_PERFORMED_ACTIONS, mPerformedActions)
        }
    }


    override fun toString(): String {
        return "${super.toString()}_${mView.getCurrencyMarketsParameters().currencyMarketType}"
    }


}
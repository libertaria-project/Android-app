package com.stocksexchange.android.ui.orders.fragment

import com.stocksexchange.android.Constants
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.*
import com.stocksexchange.android.events.OrderEvent
import com.stocksexchange.android.events.PerformedOrderActionsEvent
import com.stocksexchange.android.model.HttpCodes.*
import com.stocksexchange.android.model.OrderModes
import com.stocksexchange.android.model.PerformedOrderActions
import com.stocksexchange.android.ui.base.mvp.presenters.BaseListDataLoadingPresenter
import com.stocksexchange.android.utils.SavedState
import com.stocksexchange.android.utils.exceptions.NoInternetException
import com.stocksexchange.android.utils.helpers.tag
import com.stocksexchange.android.utils.providers.StringProvider
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.standalone.inject
import retrofit2.HttpException

class OrdersPresenter(
    model: OrdersModel,
    view: OrdersContract.View
) : BaseListDataLoadingPresenter<OrdersModel, OrdersContract.View, List<Order>, OrderParameters>(model, view),
    OrdersContract.ActionListener, OrdersModel.ActionListener {


    companion object {

        private val CLASS = OrdersPresenter::class.java

        private val SAVED_STATE_PERFORMED_ACTIONS = tag(CLASS, "performed_actions")

    }


    private val mStringProvider: StringProvider by inject()

    private var mPerformedActions = PerformedOrderActions()




    init {
        model.setActionListener(this)
    }


    constructor(view: OrdersContract.View): this(OrdersModel(), view)


    override fun stop() {
        super.stop()

        mView.hideMaterialDialog()
    }


    override fun shouldLoadNewData(): Boolean {
        return true
    }


    override fun getDataLoadingParams(): OrderParameters {
        return mView.getOrderParameters()
    }


    override fun onMarketNameClicked(currencyMarket: CurrencyMarket?) {
        if(currencyMarket == null) {
            mView.showToast(mStringProvider.getString(R.string.market_not_found))
        } else {
            mView.launchCurrencyMarketPreviewActivity(currencyMarket)
        }
    }


    override fun onCancelButtonClicked(order: Order, currencyMarket: CurrencyMarket?) {
        mView.showOrderCancellationConfirmationDialog(order)
    }


    override fun onOrderCancellationConfirmed(order: Order) {
        if(mModel.isOrderCancellationOperationStarted) {
            return
        }

        mModel.performOrderCancellationOperation(order, mView.getOrderParameters())
    }


    override fun onOrderCancellationOperationStarted() {
        mView.showSecondaryProgressBar()
    }


    override fun onOrderCancellationOperationEnded() {
        mView.hideSecondaryProgressBar()
    }


    override fun onOrderCancellationOperationServerPartSucceeded(orderResponse: OrderResponse, order: Order) {
        val user = mView.getUser()!!.copy(funds = orderResponse.funds)

        mView.updateUser(user)
        mModel.performOrderCancellationOperationDatabasePart(user, orderResponse, order)
    }


    override fun onOrderCancellationOperationSucceeded(orderResponse: OrderResponse, order: Order) {
        mView.deleteOrder(order)
        mView.showToast(mStringProvider.getString(R.string.order_cancelled))

        EventBus.getDefault().post(OrderEvent.cancel(
            Order.convertFromActiveToCancelled(order), this
        ))
    }


    override fun onOrderCancellationOperationFailed(error: Throwable) {
        mView.showToast(when(error) {
            is NoInternetException -> mStringProvider.getNetworkCheckMessage()
            is HttpException -> {
                when(error.code()) {
                    TOO_MANY_REQUESTS.code -> mStringProvider.getTooManyRequestsMessage()
                    in INTERNAL_SERVER_ERROR.code..NETWORK_CONNECT_TIMEOUT.code -> mStringProvider.getServerUnresponsiveMessage()

                    else -> mStringProvider.getSomethingWentWrongMessage()
                }
            }

            else -> mStringProvider.getSomethingWentWrongMessage()
        })
    }


    override fun onBackPressed() {
        if(!mPerformedActions.isEmpty()) {
            EventBus.getDefault().postSticky(PerformedOrderActionsEvent.init(
                mPerformedActions, this, Constants.ORDERS_ACTIVITY_TAB_COUNT
            ))
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: PerformedOrderActionsEvent) {
        if(event.isOriginatedFrom(this) || event.isConsumed(this) || event.isExhausted()) {
            return
        }

        val attachment = event.attachment

        if(attachment.hasCancelledOrders()) {
            for(order in attachment.cancelledOrdersMap.values) {
                onEvent(OrderEvent.cancel(order, event.sourceTag))
            }
        }

        event.consume(this)
    }


    @Suppress("NON_EXHAUSTIVE_WHEN")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: OrderEvent) {
        if(event.isOriginatedFrom(this)) {
            return
        }

        val orderMode = mView.getOrderParameters().mode
        val ordersType = mView.getOrderParameters().type
        val order = event.attachment

        when(orderMode) {

            OrderModes.STANDARD -> {
                when(event.action) {

                    OrderEvent.Actions.CANCELLED -> {
                        if((ordersType == OrderTypes.ACTIVE) && !mView.isDataSourceEmpty()) {
                            if(mView.containsOrder(order)) {
                                mView.deleteOrder(order)
                            }
                        }

                        if((ordersType == OrderTypes.CANCELLED) && !mView.isDataSourceEmpty()) {
                            if(!mView.containsOrder(order)) {
                                mView.addOrderChronologically(order)
                            }
                        }
                    }

                }
            }

            OrderModes.SEARCH -> {
                mPerformedActions.addCancelledOrder(order)
            }

        }
    }


    override fun canReceiveEvents(): Boolean {
        return true
    }


    override fun onRestoreState(savedState: SavedState) {
        super.onRestoreState(savedState)

        with(savedState) {
            mPerformedActions = get(SAVED_STATE_PERFORMED_ACTIONS, PerformedOrderActions())
        }
    }


    override fun onSaveState(savedState: SavedState) {
        super.onSaveState(savedState)

        with(savedState) {
            save(SAVED_STATE_PERFORMED_ACTIONS, mPerformedActions)
        }
    }


    override fun toString(): String {
        val params = mView.getOrderParameters()
        val mode = params.mode.name
        val type = params.type.name

        return "${super.toString()}_${mode}_$type"
    }


}
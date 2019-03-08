package com.stocksexchange.android.ui.orders.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.afollestad.materialdialogs.MaterialDialog
import com.stocksexchange.android.AppController
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.*
import com.stocksexchange.android.cache.ObjectCache
import com.stocksexchange.android.mappings.mapToNameMarketMap
import com.stocksexchange.android.mappings.mapToOrderItem
import com.stocksexchange.android.mappings.mapToOrderItemList
import com.stocksexchange.android.model.OrderModes
import com.stocksexchange.android.model.utils.onSuccess
import com.stocksexchange.android.repositories.currencymarkets.CurrencyMarketsRepository
import com.stocksexchange.android.ui.base.fragments.BaseListDataLoadingFragment
import com.stocksexchange.android.ui.currencymarketpreview.CurrencyMarketPreviewActivity
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.diffcallbacks.OrdersDiffCallback
import com.stocksexchange.android.ui.utils.extensions.ctx
import com.stocksexchange.android.ui.utils.extensions.dimenInPx
import com.stocksexchange.android.ui.views.InfoView
import com.stocksexchange.android.utils.handlers.BrowserHandler
import com.stocksexchange.android.utils.providers.InfoViewProvider
import kotlinx.android.synthetic.main.orders_fragment_layout.view.*
import org.koin.android.ext.android.get

class OrdersFragment : BaseListDataLoadingFragment<
    OrdersPresenter,
    List<Order>,
    OrderItem,
    OrdersRecyclerViewAdapter
>(), OrdersContract.View {


    companion object {

        const val TAG = "OrdersFragment"

        private const val SAVED_STATE_ORDER_PARAMETERS = "order_parameters"
        private const val SAVED_STATE_CURRENCY_MARKETS_MAP = "currency_markets_map"


        fun newActiveInstance(): OrdersFragment {
            return newInstance(OrderParameters.getActiveOrdersParameters())
        }

        fun newCompletedInstance(): OrdersFragment {
            return newInstance(OrderParameters.getCompletedOrdersParameters())
        }

        fun newCancelledInstance(): OrdersFragment {
            return newInstance(OrderParameters.getCancelledOrdersParameters())
        }

        fun newSearchInstance(type: OrderTypes): OrdersFragment {
            return newInstance(OrderParameters.getSearchOrdersParameters(type))
        }

        /**
         * Creates a new fragment with particular parameters and returns it.
         *
         * @param parameters The parameters
         *
         * @return The new instance of this fragment
         */
        fun newInstance(parameters: OrderParameters): OrdersFragment {
            return OrdersFragment().apply {
                mOrderParameters = parameters
            }
        }

    }


    interface ProgressBarListener {

        fun showProgressBar()

        fun hideProgressBar()

    }


    /**
     * A listener used for interacting with a progress bar.
     */
    var progressBarListener: ProgressBarListener? = null

    private var mOrderParameters: OrderParameters = OrderParameters.getDefaultParameters()

    private var mCurrencyMarketsMap: Map<String, CurrencyMarket> = mapOf()

    private var mMaterialDialog: MaterialDialog? = null




    override fun initPresenter(): OrdersPresenter = OrdersPresenter(this)


    override fun init() {
        super.init()

        initContentContainer()
    }


    override fun initAdapter(): OrdersRecyclerViewAdapter {
        return OrdersRecyclerViewAdapter(ctx, getSettings(), mItems).apply {
            setOrderType(mOrderParameters.type)

            onMarketNameClickListener = { _, _, currencyMarket, _ ->
                mPresenter?.onMarketNameClicked(currencyMarket)
            }
            onCancelBtnClickListener = { _, orderItem, currencyMarket, _ ->
                mPresenter?.onCancelButtonClicked(orderItem.itemModel, currencyMarket)
            }

            if(mCurrencyMarketsMap.isEmpty()) {
                mCoroutineHandler.createUiLaunchCoroutine {
                    get<CurrencyMarketsRepository>().getAll().onSuccess {
                        mCurrencyMarketsMap = it.mapToNameMarketMap()
                        setCurrencyMarkets(mCurrencyMarketsMap)
                    }
                }
            } else {
                setCurrencyMarkets(mCurrencyMarketsMap)
            }
        }
    }


    private fun initContentContainer() {
        ThemingUtil.Main.contentContainer(mRootView.contentContainerFl, getAppTheme())
    }


    override fun adjustView(view: View) {
        val canCenterView = (mOrderParameters.mode == OrderModes.SEARCH)
        val layoutParams: FrameLayout.LayoutParams

        when(view.id) {

            R.id.progressBar -> {
                layoutParams = (view.layoutParams as FrameLayout.LayoutParams)

                if(canCenterView) {
                    layoutParams.gravity = Gravity.CENTER
                    layoutParams.setMargins(0, 0, 0, 0)
                } else {
                    layoutParams.setMargins(0, dimenInPx(R.dimen.fragment_progress_bar_margin_top), 0, 0)
                }

                view.layoutParams = layoutParams
            }

            R.id.infoView -> {
                layoutParams = (view.layoutParams as FrameLayout.LayoutParams)

                if(canCenterView) {
                    layoutParams.gravity = Gravity.CENTER
                }

                view.layoutParams = layoutParams
            }

        }
    }


    override fun showOrderCancellationConfirmationDialog(order: Order) {
        val onPositiveButtonCallback = MaterialDialog.SingleButtonCallback { _, _ ->
            mPresenter?.onOrderCancellationConfirmed(order)
        }

        val dialog = MaterialDialog.Builder(ctx)
            .title(R.string.orders_fragment_order_cancellation_dialog_title)
            .content(R.string.orders_fragment_order_cancellation_dialog_content)
            .positiveText(R.string.yes)
            .onPositive(onPositiveButtonCallback)
            .negativeText(R.string.no)
            .apply { ThemingUtil.Orders.dialogBuilder(this, getAppTheme()) }
            .build()

        mMaterialDialog = dialog
        mMaterialDialog?.show()
    }


    override fun hideMaterialDialog() {
        mMaterialDialog?.dismiss()
    }


    override fun showSecondaryProgressBar() {
        progressBarListener?.showProgressBar()
    }


    override fun hideSecondaryProgressBar() {
        progressBarListener?.hideProgressBar()
    }


    override fun addData(data: List<Order>) {
        val orderItemList = data.mapToOrderItemList().toMutableList()

        if(isDataSourceEmpty()) {
            mAdapter.items = orderItemList
        } else {
            mAdapter.setItems(orderItemList, OrdersDiffCallback(mItems, orderItemList))
        }

        mItems = orderItemList
    }


    override fun addOrderChronologically(order: Order) {
        mAdapter.addItem(
            mAdapter.getChronologicalPositionForOrder(order, mOrderParameters.sortType),
            order.mapToOrderItem()
        )
    }


    override fun setSearchQuery(query: String) {
        mOrderParameters = mOrderParameters.copy(searchQuery = query)
    }


    override fun containsOrder(order: Order): Boolean {
        return mAdapter.contains(order.mapToOrderItem())
    }


    override fun deleteOrder(order: Order) {
        mAdapter.deleteItem(order.mapToOrderItem())
    }


    override fun launchBrowser(url: String) {
        get<BrowserHandler>().launchBrowser(ctx, url, getAppTheme())
    }


    override fun launchCurrencyMarketPreviewActivity(currencyMarket: CurrencyMarket) {
        startActivity(CurrencyMarketPreviewActivity.newInstance(ctx, TAG, currencyMarket))
    }


    override fun shouldDisableRVAnimations(): Boolean {
        return false
    }


    override fun updateUser(user: User) {
        AppController.INSTANCE.setUser(user)
    }


    override fun getUser(): User? {
        return AppController.INSTANCE.getUser()
    }


    override fun getOrderParameters(): OrderParameters {
        return mOrderParameters
    }


    override fun getInfoViewIcon(infoViewProvider: InfoViewProvider): Drawable? {
        return infoViewProvider.getOrdersIcon()
    }


    override fun getEmptyViewCaption(infoViewProvider: InfoViewProvider): String {
        return infoViewProvider.getOrdersEmptyCaption(mOrderParameters)
    }


    private fun getCurrencyMarketsMapCacheKey(): String {
        return "${mPresenter!!}_$SAVED_STATE_CURRENCY_MARKETS_MAP"
    }


    override fun getSearchQuery(): String = mOrderParameters.searchQuery


    override fun getMainView(): View = mRootView.recyclerView


    override fun getInfoView(): InfoView = mRootView.infoView


    override fun getProgressBar(): ProgressBar = mRootView.progressBar


    override fun getRefreshProgressBar(): SwipeRefreshLayout = mRootView.swipeRefreshLayout


    override fun getContentLayoutResourceId(): Int = R.layout.orders_fragment_layout


    override fun onStart() {
        super.onStart()

        // Clearing the cache of orders related data since sometimes onSaveState
        // is called when its counterpart onRestoreState is not
        if(ObjectCache.contains(getCurrencyMarketsMapCacheKey())) {
            ObjectCache.remove(getCurrencyMarketsMapCacheKey())
        }
    }


    override fun onBackPressed() {
        mPresenter?.onBackPressed()
    }


    @Suppress("UNCHECKED_CAST")
    override fun onRestoreState(savedState: Bundle?) {
        savedState?.apply {
            mOrderParameters = getParcelable(SAVED_STATE_ORDER_PARAMETERS)
        }

        mCurrencyMarketsMap = (ObjectCache.remove(
            getCurrencyMarketsMapCacheKey(),
            mapOf<String, CurrencyMarket>()
        ) as Map<String, CurrencyMarket>)

        super.onRestoreState(savedState)
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        savedState.putParcelable(SAVED_STATE_ORDER_PARAMETERS, mOrderParameters)

        ObjectCache.put(getCurrencyMarketsMapCacheKey(), mCurrencyMarketsMap)
    }


}
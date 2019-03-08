package com.stocksexchange.android.ui.orderbook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.api.model.Orderbook
import com.stocksexchange.android.api.model.OrderbookOrder
import com.stocksexchange.android.api.model.OrderbookParameters
import com.stocksexchange.android.model.*
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.base.activities.BaseActivity
import com.stocksexchange.android.ui.utils.extensions.getStatusBarHeight
import com.stocksexchange.android.ui.utils.extensions.setTopMargin
import com.stocksexchange.android.utils.providers.InfoViewProvider
import kotlinx.android.synthetic.main.orderbook_activity_layout.*
import org.jetbrains.anko.intentFor
import org.koin.android.ext.android.get

class OrderbookActivity : BaseActivity<OrderbookPresenter>(), OrderbookContract.View {


    companion object {

        private const val EXTRA_CURRENCY_MARKET = "currency_market"

        private const val SAVED_STATE_IS_APP_BAR_SCROLLING_ENABLED = "is_app_bar_scrolling_enabled"
        private const val SAVED_STATE_CURRENCY_MARKET = "currency_market"


        fun newInstance(context: Context, currencyMarket: CurrencyMarket): Intent {
            return context.intentFor<OrderbookActivity>(
                EXTRA_CURRENCY_MARKET to currencyMarket
            )
        }

    }


    private var mIsAppBarScrollingEnabled: Boolean = false

    private lateinit var mCurrencyMarket: CurrencyMarket




    override fun initPresenter(): OrderbookPresenter = OrderbookPresenter(this)


    override fun init() {
        super.init()

        initCoordinatorLayout()
        initAppBarLayout()
        initOrderbookView()
    }


    private fun initCoordinatorLayout() {
        ThemingUtil.Orderbook.contentContainer(contentContainerCl, getAppTheme())
    }


    private fun initAppBarLayout() {
        initToolbar()
        initOrderbookDetailsView()
        initOrderbookViewTitle()

        if(mIsAppBarScrollingEnabled) {
            enableAppBarScrolling()
        } else {
            disableAppBarScrolling()
        }

        ThemingUtil.Orderbook.appBarLayout(appBarLayout, getAppTheme())
    }


    private fun initToolbar() {
        toolbar.setTopMargin(getStatusBarHeight())
        toolbar.setTitleText(getString(
            R.string.orderbook_activity_toolbar_title_template,
            String.format(
                "%s / %s",
                mCurrencyMarket.baseCurrencySymbol,
                mCurrencyMarket.quoteCurrencySymbol
            )
        ))

        toolbar.setOnLeftButtonClickListener {
            mPresenter?.onLeftButtonClicked()
        }

        ThemingUtil.Orderbook.toolbar(toolbar, getAppTheme())
    }


    private fun initOrderbookDetailsView() {
        with(orderbookDetailsView) {
            val infoViewProvider: InfoViewProvider = get()

            setBaseCurrencySymbol(mCurrencyMarket.baseCurrencySymbol)
            setInfoViewErrorCaption(infoViewProvider.getDefaultErrorCaption())
            setInfoViewEmptyCaption(infoViewProvider.getOrderbookDetailsViewEmptyCaption())

            ThemingUtil.Orderbook.orderbookDetailsView(this, getAppTheme())
        }
    }


    private fun initOrderbookViewTitle() {
        ThemingUtil.Orderbook.orderbookViewTitle(orderbookViewTitleTv, getAppTheme())
    }


    private fun initOrderbookView() {
        with(orderbookView) {
            val infoViewProvider: InfoViewProvider = get()

            setHighlightingEnabled(getSettings().isOrderbookHighlightingEnabled())
            setMarketName(mCurrencyMarket.name)
            setInfoViewErrorCaption(infoViewProvider.getDefaultErrorCaption())
            setInfoViewEmptyCaption(infoViewProvider.getOrderbookViewEmptyCaption())
            setInfoViewIcon(infoViewProvider.getOrderbookViewIcon())

            ThemingUtil.Orderbook.orderbookView(this, getAppTheme())
        }
    }


    override fun showAppBar(animate: Boolean) {
        appBarLayout.setExpanded(true, animate)
    }


    override fun enableAppBarScrolling() {
        mIsAppBarScrollingEnabled = true

        val layoutParams = (toolbarContainerRl.layoutParams as AppBarLayout.LayoutParams)
        layoutParams.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
        toolbarContainerRl.layoutParams = layoutParams
    }


    override fun disableAppBarScrolling() {
        mIsAppBarScrollingEnabled = false

        val layoutParams = (toolbarContainerRl.layoutParams as AppBarLayout.LayoutParams)
        layoutParams.scrollFlags = 0
        toolbarContainerRl.layoutParams = layoutParams
    }


    override fun showProgressBar() {
        orderbookDetailsView.showProgressBar()
        orderbookView.showProgressBar()
    }


    override fun hideProgressBar() {
        orderbookDetailsView.hideProgressBar()
        orderbookView.hideProgressBar()
    }


    override fun showMainView() {
        orderbookDetailsView.showMainView()
        orderbookView.showMainView()
    }


    override fun hideMainView() {
        orderbookDetailsView.hideMainView()
        orderbookView.hideMainView()
    }


    override fun showEmptyView() {
        orderbookDetailsView.showEmptyView()
        orderbookView.showEmptyView()
    }


    override fun showErrorView() {
        orderbookDetailsView.showErrorView()
        orderbookView.showErrorView()
    }


    override fun hideInfoView() {
        orderbookDetailsView.hideInfoView()
        orderbookView.hideInfoView()
    }


    override fun setData(info: OrderbookInfo, orderbook: Orderbook, shouldBindData: Boolean) {
        orderbookDetailsView.setData(info, shouldBindData)
        orderbookView.setData(orderbook, shouldBindData)
    }


    override fun updateData(info: OrderbookInfo, orderbook: Orderbook,
                            dataActionItems: List<DataActionItem<OrderbookOrder>>) {
        orderbookDetailsView.updateData(info)
        orderbookView.updateData(orderbook, dataActionItems)
    }


    override fun bindData() {
        orderbookDetailsView.bindData()
        orderbookView.bindData()
    }


    override fun scrollOrderbookViewToTop() {
        orderbookView.scrollToTop()
    }


    override fun isAppBarScrollingEnabled(): Boolean {
        return mIsAppBarScrollingEnabled
    }


    override fun isOrderbookEmpty(): Boolean {
        return orderbookView.isDataEmpty()
    }


    override fun getOrderbookParameters(): OrderbookParameters {
        return orderbookView.getDataParameters()
    }


    override fun getCurrencyMarket(): CurrencyMarket {
        return mCurrencyMarket
    }


    override fun getContentLayoutResourceId(): Int = R.layout.orderbook_activity_layout


    override fun getTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.HORIZONTAL_SLIDING_ANIMATIONS
    }


    override fun onConnected() {
        if(isInitialized()) {
            mPresenter?.onNetworkConnected()
        }
    }


    override fun onBackPressed() {
        mPresenter?.onBackButtonPressed()

        super.onBackPressed()
    }


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            with(savedState) {
                mIsAppBarScrollingEnabled = getBoolean(SAVED_STATE_IS_APP_BAR_SCROLLING_ENABLED, false)
                mCurrencyMarket = getParcelable(SAVED_STATE_CURRENCY_MARKET)!!
            }
        } else {
            with(intent) {
                mCurrencyMarket = getParcelableExtra(EXTRA_CURRENCY_MARKET)
            }
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        with(savedState) {
            putBoolean(SAVED_STATE_IS_APP_BAR_SCROLLING_ENABLED, mIsAppBarScrollingEnabled)
            putParcelable(SAVED_STATE_CURRENCY_MARKET, mCurrencyMarket)
        }
    }


}
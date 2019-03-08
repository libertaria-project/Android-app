package com.stocksexchange.android.ui.currencymarketpreview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.stocksexchange.android.AppController
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.*
import com.stocksexchange.android.model.*
import com.stocksexchange.android.repositories.currencymarkets.CurrencyMarketsRepository
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.auth.AuthenticationActivity
import com.stocksexchange.android.ui.base.activities.BaseActivity
import com.stocksexchange.android.ui.login.LoginActivity
import com.stocksexchange.android.ui.orderbook.OrderbookActivity
import com.stocksexchange.android.ui.trade.buy.BuyActivity
import com.stocksexchange.android.ui.trade.sell.SellActivity
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.views.LockableScrollView
import com.stocksexchange.android.ui.views.SwitchOptionsView
import com.stocksexchange.android.ui.views.marketpreview.base.BaseMarketPreviewChartView
import com.stocksexchange.android.ui.views.marketpreview.base.BaseMarketPreviewView
import com.stocksexchange.android.utils.formatters.DoubleFormatter
import com.stocksexchange.android.utils.providers.InfoViewProvider
import kotlinx.android.synthetic.main.currency_market_preview_activity_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.portrait
import org.koin.android.ext.android.get

class CurrencyMarketPreviewActivity : BaseActivity<CurrencyMarketPreviewPresenter>(), CurrencyMarketPreviewContract.View {


    companion object {

        const val TAG = "CurrencyMarketPreviewActivity"

        private const val EXTRA_PREVIOUS_ACTIVITY_TAG = "previous_activity_tag"
        private const val EXTRA_CURRENCY_MARKET = "currency_market"

        private const val SAVED_STATE_IS_PRICE_CHART_SELECTED = "is_price_chart_selected"
        private const val SAVED_STATE_IS_ORDERBOOK_SELECTED = "is_orderbook_selected"
        private const val SAVED_STATE_PREVIOUS_ACTIVITY_TAG = "previous_activity_tag"
        private const val SAVED_STATE_CURRENCY_MARKET = "currency_market"


        fun newInstance(context: Context, previousActivityTag: String,
                        currencyMarket: CurrencyMarket): Intent {
            return context.intentFor<CurrencyMarketPreviewActivity>(
                EXTRA_PREVIOUS_ACTIVITY_TAG to previousActivityTag,
                EXTRA_CURRENCY_MARKET to currencyMarket
            ).clearTop()
        }

    }


    private var mIsPriceChartSelected: Boolean = true
    private var mIsOrderbookSelected: Boolean = true

    private lateinit var mPreviousActivityTag: String

    private lateinit var mCurrencyMarket: CurrencyMarket

    private lateinit var mMarketPreviewViewsMap: Map<CurrencyMarketPreviewDataSources, BaseMarketPreviewView<*, *, *>>




    override fun preInit() {
        super.preInit()

        mMarketPreviewViewsMap = mapOf(
            CurrencyMarketPreviewDataSources.PRICE_CHART to priceChartView,
            CurrencyMarketPreviewDataSources.DEPTH_CHART to depthChartView,
            CurrencyMarketPreviewDataSources.ORDERBOOK to orderbookView,
            CurrencyMarketPreviewDataSources.TRADES to tradesView
        )
    }


    override fun initPresenter(): CurrencyMarketPreviewPresenter = CurrencyMarketPreviewPresenter(this)


    override fun init() {
        super.init()

        initToolbar()
        initScrollView()
        initBottomBar()
    }


    private fun initToolbar() {
        toolbar.setTitleText(String.format(
            "%s / %s",
            mCurrencyMarket.baseCurrencySymbol,
            mCurrencyMarket.quoteCurrencySymbol
        ))

        toolbar.setOnLeftButtonClickListener {
            mPresenter?.onLeftButtonClicked()
        }
        toolbar.setOnRightButtonClickListener {
            mPresenter?.onRightButtonClicked()
        }

        mCoroutineHandler.createBgLaunchCoroutine {
            val isFavorite = get<CurrencyMarketsRepository>().isCurrencyMarketFavorite(mCurrencyMarket)

            withContext(Dispatchers.Main) {
                updateFavoriteButtonState(isFavorite)
            }
        }

        ThemingUtil.CurrencyMarketPreview.toolbar(toolbar, getAppTheme())
    }


    private fun initScrollView() {
        scrollView.listener = object : LockableScrollView.Listener {

            override fun onTopReached() {
                mPresenter?.onScrollViewTopReached()
            }

            override fun onScrolledDownward() {
                mPresenter?.onScrollViewScrolledDownward()
            }

        }

        initTopPanel()
        initMarketDetails()
        initTradeInfo()

        ThemingUtil.CurrencyMarketPreview.scrollView(scrollView, getAppTheme())
    }


    private fun initTopPanel() {
        initPrices()
        initChartTypes()
        initChartViews()

        ThemingUtil.CurrencyMarketPreview.topPanel(topPanelRl, getAppTheme())
    }


    private fun initPrices() {
        val formatter = DoubleFormatter.getInstance(getLocale())
        val lastPriceDailyChange = (mCurrencyMarket.lastPrice - mCurrencyMarket.lastPriceDayAgo)

        lastPriceTv.text = formatter.formatFixedPrice(mCurrencyMarket.lastPrice)
        priceChangeTv.text = getString(
            R.string.currency_market_preview_price_change_template,
            formatter.formatLastPriceChange(lastPriceDailyChange),
            formatter.formatDailyPriceChange(mCurrencyMarket.dailyPriceChange)
        )

        updatePriceInfoTextColor(lastPriceDailyChange, lastPriceTv)
        updatePriceInfoTextColor(lastPriceDailyChange, priceChangeTv)
    }


    private fun initChartTypes() {
        with(chartTypesSov) {
            onOptionChangeListener = object : SwitchOptionsView.OnOptionSelectionListener {

                override fun onLeftOptionSelected() {
                    mPresenter?.onPriceChartSelected()
                }

                override fun onRightOptionSelected() {
                    mPresenter?.onDepthChartSelected()
                }

            }

            if(mIsPriceChartSelected) {
                setLeftOptionSelected(false)
            } else {
                setRightOptionSelected(false)
            }

            // Disabling the state saving since we do that by ourselves
            // in the activity and avoiding corrupt state due to same IDs
            getSwitchView().isSaveEnabled = false
        }

        ThemingUtil.CurrencyMarketPreview.chartTypes(chartTypesSov, getAppTheme())
    }


    private fun initChartViews() {
        initPriceChartView()
        initDepthChartView()

        if(chartTypesSov.isLeftOptionSelected()) {
            showView(CurrencyMarketPreviewDataSources.PRICE_CHART)
            hideView(CurrencyMarketPreviewDataSources.DEPTH_CHART)
        } else {
            showView(CurrencyMarketPreviewDataSources.DEPTH_CHART)
            hideView(CurrencyMarketPreviewDataSources.PRICE_CHART)
        }
    }


    private fun initPriceChartView() {
        with(priceChartView) {
            val settings = getSettings()
            val infoViewProvider: InfoViewProvider = get()

            setChartAnimationEnabled(settings.shouldAnimateCharts)
            setChartZoomInEnabled(settings.isPriceChartZoomInEnabled)
            setMarketName(mCurrencyMarket.name)
            setBullishCandleStickStyle(settings.bullishCandleStickStyle)
            setBearishCandleStickStyle(settings.bearishCandleStickStyle)
            setInfoViewErrorCaption(infoViewProvider.getDefaultErrorCaption())
            setInfoViewEmptyCaption(infoViewProvider.getPriceChartViewEmptyCaption())
            setInfoViewIcon(infoViewProvider.getPriceChartViewIcon())
            onMainViewShowingListener = OnMainViewShowingListener(CurrencyMarketPreviewDataSources.PRICE_CHART)
            onChartTouchListener = mOnChartTouchListener
            onChartIntervalChangeListener = {
                mPresenter?.onPriceChartDataIntervalChanged(it)
            }

            ThemingUtil.CurrencyMarketPreview.priceChartView(this, getAppTheme())
        }
    }


    private fun initDepthChartView() {
        with(depthChartView) {
            val settings = getSettings()
            val infoViewProvider: InfoViewProvider = get()

            setChartAnimationEnabled(settings.shouldAnimateCharts)
            setMarketName(mCurrencyMarket.name)
            setLineStyle(settings.depthChartLineStyle)
            setInfoViewErrorCaption(infoViewProvider.getDefaultErrorCaption())
            setInfoViewEmptyCaption(infoViewProvider.getDepthChartViewEmptyCaption())
            setInfoViewIcon(infoViewProvider.getDepthChartViewIcon())
            onMainViewShowingListener = OnMainViewShowingListener(CurrencyMarketPreviewDataSources.DEPTH_CHART)
            onChartTouchListener = mOnChartTouchListener
            onDepthLevelChangeListener = {
                mPresenter?.onDepthChartDepthLevelChanged(it)
            }

            ThemingUtil.CurrencyMarketPreview.depthChartView(this, getAppTheme())
        }
    }


    private fun initMarketDetails() {
        initMarketInfoTitle()
        initMarketDetailsView()
    }


    private fun initMarketInfoTitle() {
        ThemingUtil.CurrencyMarketPreview.marketInfoTitle(marketInfoTv, getAppTheme())
    }


    private fun initMarketDetailsView() {
        with(marketDetailsView) {
            setCurrencyNameCharacterLimit(getCurrencyNameCharacterLimit())
            setData(mCurrencyMarket, true)
            showMainView()

            ThemingUtil.CurrencyMarketPreview.marketDetailsView(this, getAppTheme())
        }
    }


    private fun initTradeInfo() {
        initTradeInfoSov()
        initOrderbookView()
        initTradesView()

        if(tradeInfoSov.isLeftOptionSelected()) {
            showView(CurrencyMarketPreviewDataSources.ORDERBOOK)
            hideView(CurrencyMarketPreviewDataSources.TRADES)
        } else {
            showView(CurrencyMarketPreviewDataSources.TRADES)
            hideView(CurrencyMarketPreviewDataSources.ORDERBOOK)
        }
    }


    private fun initTradeInfoSov() {
        with(tradeInfoSov) {
            onOptionChangeListener = object : SwitchOptionsView.OnOptionSelectionListener {

                override fun onLeftOptionSelected() {
                    mPresenter?.onOrderbookSelected()
                }

                override fun onRightOptionSelected() {
                    mPresenter?.onTradesSelected()
                }

            }

            if(mIsOrderbookSelected) {
                setLeftOptionSelected(false)
            } else {
                setRightOptionSelected(false)
            }

            // Disabling the state saving since we do that by ourselves
            // in the activity and avoiding corrupt state due to same IDs
            getSwitchView().isSaveEnabled = false
        }

        ThemingUtil.CurrencyMarketPreview.tradeInfo(tradeInfoSov, getAppTheme())
    }


    private fun initOrderbookView() {
        with(orderbookView) {
            val infoViewProvider: InfoViewProvider = get()

            setHighlightingEnabled(getSettings().isOrderbookHighlightingEnabled())
            setMarketName(mCurrencyMarket.name)
            setInfoViewErrorCaption(infoViewProvider.getDefaultErrorCaption())
            setInfoViewEmptyCaption(infoViewProvider.getOrderbookViewEmptyCaption())
            setInfoViewIcon(infoViewProvider.getOrderbookViewIcon())
            setOnHeaderMoreButtonClickListener { _, _, _ ->
                mPresenter?.onOrderbookHeaderMoreButtonClicked()
            }
            onMainViewShowingListener = OnMainViewShowingListener(CurrencyMarketPreviewDataSources.ORDERBOOK)

            ThemingUtil.CurrencyMarketPreview.orderbookView(this, getAppTheme())
        }
    }


    private fun initTradesView() {
        with(tradesView) {
            val infoViewProvider: InfoViewProvider = get()

            setHighlightingEnabled(getSettings().isNewTradesHighlightingEnabled())
            setMarketName(mCurrencyMarket.name)
            setInfoViewErrorCaption(infoViewProvider.getDefaultErrorCaption())
            setInfoViewEmptyCaption(infoViewProvider.getTradesViewEmptyCaption())
            setInfoViewIcon(infoViewProvider.getTradesViewIcon())
            onMainViewShowingListener = OnMainViewShowingListener(CurrencyMarketPreviewDataSources.TRADES)

            ThemingUtil.CurrencyMarketPreview.tradesView(this, getAppTheme())
        }
    }


    private fun initBottomBar() {
        buyBtn.setOnClickListener {
            mPresenter?.onTradeButtonClicked(OrderTradeTypes.BUY)
        }
        sellBtn.setOnClickListener {
            mPresenter?.onTradeButtonClicked(OrderTradeTypes.SELL)
        }

        with(ThemingUtil.CurrencyMarketPreview) {
            val theme = getAppTheme()

            bottomBar(bottomBarLl, theme)
            buyButton(buyBtn, theme)
            sellButton(sellBtn, theme)
        }
    }


    override fun postInit() {
        super.postInit()

        AppController.INSTANCE.getSocketConnection()?.startListeningToMarketPreviewUpdates(
            priceChartView.getDataParameters().interval.intervalName,
            mCurrencyMarket.id.toString()
        )
    }


    override fun showView(dataSource: CurrencyMarketPreviewDataSources) {
        mMarketPreviewViewsMap[dataSource]
            ?.takeIf { it.isGone() }
            ?.apply { makeVisible() }
    }


    override fun hideView(dataSource: CurrencyMarketPreviewDataSources) {
        mMarketPreviewViewsMap[dataSource]
            ?.takeIf { it.isVisible() }
            ?.apply { makeGone() }
    }


    override fun showMainView(dataSource: CurrencyMarketPreviewDataSources) {
        mMarketPreviewViewsMap[dataSource]?.showMainView()
    }


    override fun hideMainView(dataSource: CurrencyMarketPreviewDataSources) {
        mMarketPreviewViewsMap[dataSource]?.hideMainView()
    }


    override fun showProgressBar(dataSource: CurrencyMarketPreviewDataSources) {
        mMarketPreviewViewsMap[dataSource]?.showProgressBar()
    }


    override fun hideProgressBar(dataSource: CurrencyMarketPreviewDataSources) {
        mMarketPreviewViewsMap[dataSource]?.hideProgressBar()
    }


    override fun showEmptyView(dataSource: CurrencyMarketPreviewDataSources) {
        mMarketPreviewViewsMap[dataSource]?.showEmptyView()
    }


    override fun showErrorView(dataSource: CurrencyMarketPreviewDataSources) {
        mMarketPreviewViewsMap[dataSource]?.showErrorView()
    }


    override fun hideInfoView(dataSource: CurrencyMarketPreviewDataSources) {
        mMarketPreviewViewsMap[dataSource]?.hideInfoView()
    }


    @Suppress("UNCHECKED_CAST")
    override fun setData(data: Any, shouldBindData: Boolean, dataSource: CurrencyMarketPreviewDataSources) {
        when(dataSource) {

            CurrencyMarketPreviewDataSources.PRICE_CHART -> {
                priceChartView.setData((data as PriceChartData), shouldBindData)
            }

            CurrencyMarketPreviewDataSources.DEPTH_CHART -> {
                depthChartView.setData((data as Orderbook), shouldBindData)
            }

            CurrencyMarketPreviewDataSources.ORDERBOOK -> {
                orderbookView.setData((data as Orderbook), shouldBindData)
            }

            CurrencyMarketPreviewDataSources.TRADES -> {
                tradesView.setData((data as List<Trade>), shouldBindData)
            }

        }
    }


    @Suppress("NON_EXHAUSTIVE_WHEN", "UNCHECKED_CAST")
    override fun updateData(data: Any, dataActionItems: List<Any>, dataSource: CurrencyMarketPreviewDataSources) {
        when(dataSource) {

            CurrencyMarketPreviewDataSources.PRICE_CHART -> {
                priceChartView.updateData((data as PriceChartData), (dataActionItems as List<DataActionItem<CandleStick>>))
            }

            CurrencyMarketPreviewDataSources.DEPTH_CHART -> {
                depthChartView.updateData((data as Orderbook), (dataActionItems as List<DataActionItem<OrderbookOrder>>))
            }

            CurrencyMarketPreviewDataSources.ORDERBOOK -> {
                orderbookView.updateData((data as Orderbook), (dataActionItems as List<DataActionItem<OrderbookOrder>>))
            }

            CurrencyMarketPreviewDataSources.TRADES -> {
                tradesView.updateData((data as List<Trade>), (dataActionItems as List<DataActionItem<Trade>>))
            }

        }
    }


    override fun bindData(dataSource: CurrencyMarketPreviewDataSources) {
        mMarketPreviewViewsMap[dataSource]?.bindData()
    }


    override fun clearData(dataSource: CurrencyMarketPreviewDataSources) {
        mMarketPreviewViewsMap[dataSource]?.clearData()
    }


    override fun enableScrollViewScrolling() {
        scrollView.isScrollable = true
    }


    override fun disableScrollViewScrolling() {
        scrollView.isScrollable = false
    }


    override fun updateCurrencyMarket(currencyMarket: CurrencyMarket) {
        marketDetailsView.updateData(currencyMarket)

        if(currencyMarket.lastPrice == mCurrencyMarket.lastPrice) {
            return
        }

        val formatter = DoubleFormatter.getInstance(getLocale())

        val lastPriceDailyChange = (currencyMarket.lastPrice - currencyMarket.lastPriceDayAgo)
        val lastPriceString = formatter.formatFixedPrice(currencyMarket.lastPrice)
        val priceChangeString = getString(
            R.string.currency_market_preview_price_change_template,
            formatter.formatLastPriceChange(lastPriceDailyChange),
            formatter.formatDailyPriceChange(currencyMarket.dailyPriceChange)
        )

        mCurrencyMarket = currencyMarket

        priceInfoContainerLl.crossFade {
            updatePriceInfoTextColor(lastPriceDailyChange, lastPriceTv)
            updatePriceInfoTextColor(lastPriceDailyChange, priceChangeTv)

            lastPriceTv.text = lastPriceString
            priceChangeTv.text = priceChangeString
        }
    }


    private fun updatePriceInfoTextColor(lastPriceDailyChange: Double, textView: TextView) {
        with(ThemingUtil.CurrencyMarketPreview) {
            val theme = getAppTheme()

            when {
                (lastPriceDailyChange > 0.0) -> positivePriceField(textView, theme)
                (lastPriceDailyChange < 0.0) -> negativePriceField(textView, theme)
                else -> neutralPriceField(textView, theme)
            }
        }
    }


    override fun updateFavoriteButtonState(isFavorite: Boolean) {
        with(ThemingUtil.CurrencyMarketPreview) {
            val theme = getAppTheme()
            val rightButtonIv: ImageView = toolbar.getRightButtonIv()

            if(isFavorite) {
                favoriteButton(rightButtonIv, theme)
            } else {
                unfavoriteButton(rightButtonIv, theme)
            }
        }
    }


    override fun updatePriceChartWebSocketData(priceChartDataInterval: PriceChartDataIntervals) {
        AppController.INSTANCE.getSocketConnection()?.startListeningToPriceChartDataUpdates(
            priceChartDataInterval.intervalName,
            mCurrencyMarket.id.toString()
        )
    }


    override fun launchOrderbookActivity() {
        startActivity(OrderbookActivity.newInstance(this, mCurrencyMarket))
    }


    override fun launchBuyActivity(currencyMarket: CurrencyMarket, user: User) {
        startActivity(BuyActivity.newInstance(this, currencyMarket, user))
    }


    override fun launchSellActivity(currencyMarket: CurrencyMarket, user: User) {
        startActivity(SellActivity.newInstance(this, currencyMarket, user))
    }


    override fun launchLoginActivity() {
        startActivity(LoginActivity.newInstance(
            this,
            TransitionAnimations.FADING_ANIMATIONS,
            newInstance(this, AuthenticationActivity.TAG, mCurrencyMarket)
        ))
    }


    override fun setToolbarElevation(elevation: Float) {
        toolbar.elevation = elevation
    }


    override fun isAuthenticationPreviousActivity(): Boolean {
        return (mPreviousActivityTag == AuthenticationActivity.TAG)
    }


    override fun isViewSelected(dataSource: CurrencyMarketPreviewDataSources): Boolean {
        return when(dataSource) {
            CurrencyMarketPreviewDataSources.PRICE_CHART -> chartTypesSov.isLeftOptionSelected()
            CurrencyMarketPreviewDataSources.DEPTH_CHART -> chartTypesSov.isRightOptionSelected()
            CurrencyMarketPreviewDataSources.ORDERBOOK -> tradeInfoSov.isLeftOptionSelected()
            CurrencyMarketPreviewDataSources.TRADES -> tradeInfoSov.isRightOptionSelected()
        }
    }


    override fun isDataEmpty(dataSource: CurrencyMarketPreviewDataSources): Boolean {
        return mMarketPreviewViewsMap[dataSource]?.isDataEmpty() ?: true
    }


    private fun getCurrencyNameCharacterLimit(): Int {
        return if(resources.configuration.portrait) {
            val screenWidthInDp = getSmallestScreenWidthInDp()

            when{
                (screenWidthInDp < 400) -> 17
                (screenWidthInDp < 500) -> 20
                (screenWidthInDp < 600) -> 23

                else -> -1
            }
        } else {
            -1
        }
    }


    override fun getDataParameters(dataSource: CurrencyMarketPreviewDataSources): Any {
        return mMarketPreviewViewsMap[dataSource]?.getDataParameters() ?: Any()
    }


    override fun getContentLayoutResourceId(): Int = R.layout.currency_market_preview_activity_layout


    override fun getUser(): User? {
        return AppController.INSTANCE.getUser()
    }


    override fun getCurrencyMarket(): CurrencyMarket = mCurrencyMarket


    override fun getTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.OVERSHOOT_SCALING_ANIMATIONS
    }


    override fun onConnected() {
        if(isInitialized()) {
            mPresenter?.onNetworkConnected()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()

        mPresenter?.onBackButtonClicked()
    }


    private val mOnChartTouchListener = object : BaseMarketPreviewChartView.OnChartTouchListener {

        override fun onChartPressed() {
            mPresenter?.onChartPressed()
        }

        override fun onChartReleased() {
            mPresenter?.onChartReleased()
        }

    }


    private inner class OnMainViewShowingListener(val dataSource: CurrencyMarketPreviewDataSources) :
        BaseMarketPreviewView.OnMainViewShowListener {

        override fun onMainViewShowingStarted() {
            mPresenter?.onMainViewShowingStarted(dataSource)
        }


        override fun onMainViewShowingEnded() {
            mPresenter?.onMainViewShowingEnded(dataSource)
        }

    }


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            with(savedState) {
                mIsPriceChartSelected = getBoolean(SAVED_STATE_IS_PRICE_CHART_SELECTED, true)
                mIsOrderbookSelected = getBoolean(SAVED_STATE_IS_ORDERBOOK_SELECTED, true)
                mPreviousActivityTag = (getString(SAVED_STATE_PREVIOUS_ACTIVITY_TAG, ""))
                mCurrencyMarket = getParcelable(SAVED_STATE_CURRENCY_MARKET)!!
            }
        } else {
            if(intent != null) {
                mPreviousActivityTag = (intent?.getStringExtra(EXTRA_PREVIOUS_ACTIVITY_TAG) ?: "")
                mCurrencyMarket = intent?.getParcelableExtra(EXTRA_CURRENCY_MARKET)!!
            }
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        with(savedState) {
            putBoolean(SAVED_STATE_IS_PRICE_CHART_SELECTED, chartTypesSov.isLeftOptionSelected())
            putBoolean(SAVED_STATE_IS_ORDERBOOK_SELECTED, tradeInfoSov.isLeftOptionSelected())
            putString(SAVED_STATE_PREVIOUS_ACTIVITY_TAG, mPreviousActivityTag)
            putParcelable(SAVED_STATE_CURRENCY_MARKET, mCurrencyMarket)
        }
    }


    override fun onRecycle(isChangingConfigurations: Boolean) {
        super.onRecycle(isChangingConfigurations)

        if(!isChangingConfigurations) {
            AppController.INSTANCE.getSocketConnection()?.stopListeningToMarketPreviewUpdates(
                mCurrencyMarket.id.toString()
            )
        }
    }


}
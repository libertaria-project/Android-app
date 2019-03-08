package com.stocksexchange.android.ui.currencymarketpreview

import com.stocksexchange.android.api.model.*
import com.stocksexchange.android.model.CurrencyMarketPreviewDataSources
import com.stocksexchange.android.model.OrderbookHeader

interface CurrencyMarketPreviewContract {


    interface View {

        fun showToast(message: String)

        fun showView(dataSource: CurrencyMarketPreviewDataSources)

        fun hideView(dataSource: CurrencyMarketPreviewDataSources)

        fun showMainView(dataSource: CurrencyMarketPreviewDataSources)

        fun hideMainView(dataSource: CurrencyMarketPreviewDataSources)

        fun showProgressBar(dataSource: CurrencyMarketPreviewDataSources)

        fun hideProgressBar(dataSource: CurrencyMarketPreviewDataSources)

        fun showEmptyView(dataSource: CurrencyMarketPreviewDataSources)

        fun showErrorView(dataSource: CurrencyMarketPreviewDataSources)

        fun hideInfoView(dataSource: CurrencyMarketPreviewDataSources)

        fun setData(data: Any, shouldBindData: Boolean, dataSource: CurrencyMarketPreviewDataSources)

        fun updateData(data: Any, dataActionItems: List<Any>, dataSource: CurrencyMarketPreviewDataSources)

        fun bindData(dataSource: CurrencyMarketPreviewDataSources)

        fun clearData(dataSource: CurrencyMarketPreviewDataSources)

        fun enableScrollViewScrolling()

        fun disableScrollViewScrolling()

        fun updateCurrencyMarket(currencyMarket: CurrencyMarket)

        fun updateFavoriteButtonState(isFavorite: Boolean)

        fun updatePriceChartWebSocketData(priceChartDataInterval: PriceChartDataIntervals)

        fun launchOrderbookActivity()

        fun launchBuyActivity(currencyMarket: CurrencyMarket, user: User)

        fun launchSellActivity(currencyMarket: CurrencyMarket, user: User)

        fun launchLoginActivity()

        fun setToolbarElevation(elevation: Float)

        fun isAuthenticationPreviousActivity(): Boolean

        fun isViewSelected(dataSource: CurrencyMarketPreviewDataSources): Boolean

        fun isDataEmpty(dataSource: CurrencyMarketPreviewDataSources): Boolean

        fun getDataParameters(dataSource: CurrencyMarketPreviewDataSources): Any

        fun getUser(): User?

        fun getCurrencyMarket(): CurrencyMarket

        fun onBackPressed()

    }


    interface ActionListener {

        fun onLeftButtonClicked()

        fun onRightButtonClicked()

        fun onScrollViewTopReached()

        fun onScrollViewScrolledDownward()

        fun onPriceChartSelected()

        fun onDepthChartSelected()

        fun onMainViewShowingStarted(dataSource: CurrencyMarketPreviewDataSources)

        fun onMainViewShowingEnded(dataSource: CurrencyMarketPreviewDataSources)

        fun onChartPressed()

        fun onChartReleased()

        fun onPriceChartDataIntervalChanged(dataInterval: PriceChartDataIntervals)

        fun onDepthChartDepthLevelChanged(newDepthLevel: Int)

        fun onOrderbookSelected()

        fun onTradesSelected()

        fun onOrderbookHeaderMoreButtonClicked()

        fun onTradeButtonClicked(orderTradeType: OrderTradeTypes)

        fun onNetworkConnected()

        fun onBackButtonClicked()

    }


}
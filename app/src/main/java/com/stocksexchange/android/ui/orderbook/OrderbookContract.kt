package com.stocksexchange.android.ui.orderbook

import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.api.model.Orderbook
import com.stocksexchange.android.api.model.OrderbookOrder
import com.stocksexchange.android.api.model.OrderbookParameters
import com.stocksexchange.android.model.DataActionItem
import com.stocksexchange.android.model.OrderbookInfo

interface OrderbookContract {


    interface View {

        fun showAppBar(animate: Boolean)

        fun enableAppBarScrolling()

        fun disableAppBarScrolling()

        fun showProgressBar()

        fun hideProgressBar()

        fun showMainView()

        fun hideMainView()

        fun showEmptyView()

        fun showErrorView()

        fun hideInfoView()

        fun setData(info: OrderbookInfo, orderbook: Orderbook, shouldBindData: Boolean)

        fun updateData(info: OrderbookInfo, orderbook: Orderbook,
                       dataActionItems: List<DataActionItem<OrderbookOrder>>)

        fun bindData()

        fun scrollOrderbookViewToTop()

        fun isAppBarScrollingEnabled(): Boolean

        fun isOrderbookEmpty(): Boolean

        fun getOrderbookParameters(): OrderbookParameters

        fun getCurrencyMarket(): CurrencyMarket

        fun onBackPressed()

    }


    interface ActionListener {

        fun onLeftButtonClicked()

        fun onNetworkConnected()

        fun onBackButtonPressed()

    }


}
package com.stocksexchange.android.ui.orders.fragment

import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.api.model.Order
import com.stocksexchange.android.api.model.OrderParameters
import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.ui.base.mvp.views.ListDataLoadingView

interface OrdersContract {


    interface View : ListDataLoadingView<List<Order>> {

        fun showToast(message: String)

        fun showOrderCancellationConfirmationDialog(order: Order)

        fun hideMaterialDialog()

        fun showSecondaryProgressBar()

        fun hideSecondaryProgressBar()

        fun addOrderChronologically(order: Order)

        fun containsOrder(order: Order): Boolean

        fun deleteOrder(order: Order)

        fun launchBrowser(url: String)

        fun launchCurrencyMarketPreviewActivity(currencyMarket: CurrencyMarket)

        fun updateUser(user: User)

        fun getUser(): User?

        fun getOrderParameters(): OrderParameters

    }


    interface ActionListener {

        fun onMarketNameClicked(currencyMarket: CurrencyMarket?)

        fun onCancelButtonClicked(order: Order, currencyMarket: CurrencyMarket?)

        fun onOrderCancellationConfirmed(order: Order)

        fun onBackPressed()

    }


}
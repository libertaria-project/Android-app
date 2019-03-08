package com.stocksexchange.android.ui.trade.buy

import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.ui.base.mvp.presenters.BaseTradePresenter

class BuyPresenter(
    model: BuyModel,
    view: BuyContract.View
) : BaseTradePresenter<BuyModel, BuyContract.View>(model, view), BuyContract.ActionListener,
    BuyModel.ActionListener {


    init {
        model.setActionListener(this)
    }


    constructor(view: BuyContract.View): this(BuyModel(), view)


    override fun getAppropriateUserBalance(): Double {
        return mView.getQuoteCurrencyUserBalance()
    }


    override fun getBalanceTooSmallStringArg(currencyMarket: CurrencyMarket): String {
        return currencyMarket.quoteCurrencySymbol
    }


}
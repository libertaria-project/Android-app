package com.stocksexchange.android.ui.trade.sell

import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.ui.base.mvp.presenters.BaseTradePresenter

class SellPresenter(
    model: SellModel,
    view: SellContract.View
) : BaseTradePresenter<SellModel, SellContract.View>(model, view), SellContract.ActionListener,
    SellModel.ActionListener {


    init {
        model.setActionListener(this)
    }


    constructor(view: SellContract.View): this(SellModel(), view)


    override fun getAppropriateUserBalance(): Double {
        return mView.getBaseCurrencyUserBalance()
    }


    override fun getBalanceTooSmallStringArg(currencyMarket: CurrencyMarket): String {
        return currencyMarket.baseCurrencySymbol
    }


}
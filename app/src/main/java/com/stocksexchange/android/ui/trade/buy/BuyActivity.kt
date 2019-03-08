package com.stocksexchange.android.ui.trade.buy

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.api.model.OrderTradeTypes
import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.ui.base.activities.BaseTradeActivity
import com.stocksexchange.android.ui.utils.extensions.getCompatColor
import com.stocksexchange.android.ui.utils.extensions.getPrimaryButtonBackground
import org.jetbrains.anko.intentFor

class BuyActivity : BaseTradeActivity<BuyPresenter>(), BuyContract.View {


    companion object {

        fun newInstance(context: Context, currencyMarket: CurrencyMarket,
                        user: User): Intent {
            return context.intentFor<BuyActivity>(
                EXTRA_ORDER_TRADE_TYPE to OrderTradeTypes.BUY,
                EXTRA_CURRENCY_MARKET to currencyMarket,
                EXTRA_USER to user
            )
        }

    }




    override fun initPresenter(): BuyPresenter = BuyPresenter(this)


    override fun getAtPrice(): Double = mCurrencyMarket.dailyMaxPrice


    override fun getAtPriceInputHintStringRes(): Int = R.string.action_ask


    override fun getTradeButtonText(): String {
        return getString(
            R.string.trade_activity_buy_button_template,
            mCurrencyMarket.baseCurrencySymbol,
            mCurrencyMarket.quoteCurrencySymbol
        )
    }


    override fun getTradeButtonBackground(): Drawable? {
        return getPrimaryButtonBackground(
            getCompatColor(R.color.colorGreenAccentDark),
            getCompatColor(R.color.colorGreenAccent)
        )
    }


    override fun calculateAmount(): Double {
        return (getAmountInput() * getAtPriceInput())
    }


    override fun calculateTransactionFee(amount: Double, price: Double): Double {
        return (amount * getFeePercent() / 100.0)
    }


    override fun getTransactionFeeCoinName(): String {
        return mCurrencyMarket.baseCurrencySymbol
    }


    override fun getFeePercent(): Double {
        return mCurrencyMarket.buyFeePercent
    }


    override fun calculateUserDeduction(amount: Double, price: Double): Double {
        return (amount * price)
    }


    override fun getUserDeductionCoinName(): String {
        return mCurrencyMarket.quoteCurrencySymbol
    }


    override fun calculateUserAddition(amount: Double, price: Double): Double {
        return (amount - calculateTransactionFee(amount, price))
    }


    override fun getUserAdditionCoinName(): String {
        return mCurrencyMarket.baseCurrencySymbol
    }


}
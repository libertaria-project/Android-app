package com.stocksexchange.android.ui.trade.sell

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

class SellActivity : BaseTradeActivity<SellPresenter>(), SellContract.View {


    companion object {

        fun newInstance(context: Context, currencyMarket: CurrencyMarket,
                        user: User): Intent {
            return context.intentFor<SellActivity>(
                EXTRA_ORDER_TRADE_TYPE to OrderTradeTypes.SELL,
                EXTRA_CURRENCY_MARKET to currencyMarket,
                EXTRA_USER to user
            )
        }

    }




    override fun initPresenter(): SellPresenter = SellPresenter(this)


    override fun getAtPrice(): Double = mCurrencyMarket.dailyMinPrice


    override fun getAtPriceInputHintStringRes(): Int = R.string.action_bid


    override fun getTradeButtonText(): String {
        return getString(
            R.string.trade_activity_sell_button_template,
            mCurrencyMarket.baseCurrencySymbol,
            mCurrencyMarket.quoteCurrencySymbol
        )
    }


    override fun getTradeButtonBackground(): Drawable? {
        return getPrimaryButtonBackground(
            getCompatColor(R.color.colorRedAccentDark),
            getCompatColor(R.color.colorRedAccent)
        )
    }


    override fun calculateAmount(): Double {
        return getAmountInput()
    }


    override fun calculateTransactionFee(amount: Double, price: Double): Double {
        return (amount * price * getFeePercent() / 100.0)
    }


    override fun getTransactionFeeCoinName(): String {
        return mCurrencyMarket.quoteCurrencySymbol
    }


    override fun getFeePercent(): Double {
        return mCurrencyMarket.sellFeePercent
    }


    override fun calculateUserDeduction(amount: Double, price: Double): Double {
        return amount
    }


    override fun getUserDeductionCoinName(): String {
        return mCurrencyMarket.baseCurrencySymbol
    }


    override fun calculateUserAddition(amount: Double, price: Double): Double {
        return ((amount * price) - calculateTransactionFee(amount, price))
    }


    override fun getUserAdditionCoinName(): String {
        return mCurrencyMarket.quoteCurrencySymbol
    }


}
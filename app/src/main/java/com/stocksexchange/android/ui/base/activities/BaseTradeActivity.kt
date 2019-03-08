package com.stocksexchange.android.ui.base.activities

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.text.method.KeyListener
import com.stocksexchange.android.AppController
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.api.model.OrderTradeTypes
import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.ui.base.mvp.presenters.BaseTradePresenter
import com.stocksexchange.android.ui.base.mvp.views.TradeView
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.utils.listeners.adapters.TextWatcherAdapter
import com.stocksexchange.android.utils.extensions.convertToDouble
import com.stocksexchange.android.utils.extensions.getWithDefault
import com.stocksexchange.android.utils.formatters.DoubleFormatter
import kotlinx.android.synthetic.main.trade_activity_layout.*
import java.lang.StringBuilder
import java.util.*

/**
 * A base trading activity to extend from.
 */
abstract class BaseTradeActivity<P : BaseTradePresenter<*, *>> : BaseActivity<P>(),
    TradeView {


    companion object {

        internal const val EXTRA_ORDER_TRADE_TYPE = "order_trade_type"
        internal const val EXTRA_CURRENCY_MARKET = "currency_market"
        internal const val EXTRA_USER = "user"

        internal const val SAVED_STATE_AMOUNT_INPUT_TEXT_STRING = "amount_input_text_string"
        internal const val SAVED_STATE_PRICE_INPUT_TEXT_STRING = "price_input_text_string"
        internal const val SAVED_STATE_ORDER_TRADE_TYPE = "order_trade_type"
        internal const val SAVED_STATE_CURRENCY_MARKET = "currency_market"
        internal const val SAVED_STATE_USER = "user"

        private const val INPUT_MAX_LENGTH = 20

    }


    protected var mAmountInputTextString: String = ""
    protected var mPriceInputTextString: String = ""

    /**
     * An order trade type used for determining whether the user
     * is trying to buy or sell.
     */
    protected lateinit var mOrderTradeType: OrderTradeTypes

    /**
     * A currency market the user wishes to exchange.
     */
    protected lateinit var mCurrencyMarket: CurrencyMarket

    /**
     * A signed-in user.
     */
    protected lateinit var mUser: User




    override fun init() {
        super.init()

        initContentContainer()
        initToolbar()
        initUserBalanceTitle()
        initOptionButtons()
        initAmountInput()
        initAtPriceInput()
        initDetailsContainer()
        initTradeButton()
    }


    private fun initContentContainer() {
        ThemingUtil.Trade.contentContainer(contentContainerRl, getAppTheme())
    }


    private fun initToolbar() {
        toolbar.setOnLeftButtonClickListener {
            onBackPressed()
        }

        ThemingUtil.Trade.toolbar(toolbar, getAppTheme())
    }


    private fun initUserBalanceTitle() {
        ThemingUtil.Trade.userBalance(userBalanceTitleTv, getAppTheme())
    }


    private fun initOptionButtons() {
        baseCurrencyUserBalanceBmv.setTitleText(mCurrencyMarket.baseCurrencySymbol)
        quoteCurrencyUserBalanceBmv.setTitleText(mCurrencyMarket.quoteCurrencySymbol)

        with(ThemingUtil.Trade) {
            val theme = getAppTheme()

            borderedMap(baseCurrencyUserBalanceBmv, theme)
            borderedMap(quoteCurrencyUserBalanceBmv, theme)
        }

        updateBalance()
    }


    private fun initAmountInput() {
        val keyListener = getKeyListener()

        with(amountLet) {
            setInputType(getInputType())
            setInputFilters(arrayOf(InputFilter.LengthFilter(INPUT_MAX_LENGTH)))
            setInputHint(getString(R.string.trade_activity_amount_edit_text_hint, getMinOrderAmount()))
            setLabelText(mCurrencyMarket.baseCurrencySymbol)

            if(keyListener != null) {
                setKeyListener(keyListener)
            }

            if(mAmountInputTextString.isNotEmpty()) {
                setInputText(mAmountInputTextString)
            }

            addTextChangedListener(object : TextWatcherAdapter {

                override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                    mPresenter?.onAmountInputChanged()
                }

            })

            // Disabling the state saving since we do that by ourselves
            // in the activity and avoiding corrupt state due to same IDs
            getInputEt().isSaveEnabled = false

            ThemingUtil.Trade.labeledEditText(this, getAppTheme())
        }
    }


    private fun initAtPriceInput() {
        val atPrice = getAtPrice()
        val atPriceString = DoubleFormatter.getInstance(getLocale()).formatFixedPrice(
            if(atPrice == 0.0) mCurrencyMarket.lastPrice else atPrice
        )
        val keyListener = getKeyListener()

        with(atPriceLet) {
            setInputType(getInputType())
            setInputFilters(arrayOf(InputFilter.LengthFilter(INPUT_MAX_LENGTH)))

            if(atPrice != 0.0) {
                setInputHint(String.format(
                    "%s: %s",
                    getString(getAtPriceInputHintStringRes()),
                    atPriceString
                ))
            } else {
                setInputHint(String.format(
                    "%s: %s",
                    getString(R.string.last_price),
                    atPriceString
                ))
            }
            setInputText(atPriceString)
            setLabelText(mCurrencyMarket.quoteCurrencySymbol)

            if(keyListener != null) {
                setKeyListener(keyListener)
            }

            if(mPriceInputTextString.isNotEmpty()) {
                setInputText(mPriceInputTextString)
            }

            addTextChangedListener(object : TextWatcherAdapter {

                override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                    mPresenter?.onAtPriceInputChanged()
                }

            })

            // Disabling the state saving since we do that by ourselves
            // in the activity and avoiding corrupt state due to same IDs
            getInputEt().isSaveEnabled = false

            ThemingUtil.Trade.labeledEditText(this, getAppTheme())
        }
    }


    private fun getInputType(): Int {
        return if(isGoogleKeyboard()) {
            (InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
        } else {
            (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)
        }
    }


    private fun getKeyListener(): KeyListener? {
        return if(isGoogleKeyboard()) {
            val keys = StringBuilder("1234567890").apply {
                append(getSettings().decimalSeparator.separator)

                if(getSettings().isGroupingEnabled) {
                    append(getSettings().groupingSeparator.separator)
                }
            }.toString()

            DigitsKeyListener.getInstance(keys)
        } else {
            null
        }
    }


    /**
     * Should return at price value.
     */
    protected abstract fun getAtPrice(): Double


    /**
     * Should return at price EditText hint string resource.
     */
    protected abstract fun getAtPriceInputHintStringRes(): Int


    private fun initDetailsContainer() {
        updateTradeDetails(getAmountInput(), getAtPriceInput())
    }


    private fun initTradeButton() {
        tradeBtn.text = getTradeButtonText()
        tradeBtn.background = getTradeButtonBackground()
        tradeBtn.setOnClickListener {
            mPresenter?.onTradeButtonClicked()
        }

        ThemingUtil.Trade.button(tradeBtn, getAppTheme())

        disableTradeButton()
    }


    /**
     * Should return text for the trade button.
     */
    protected abstract fun getTradeButtonText(): String


    /**
     * Should return background for the trade button.
     */
    protected abstract fun getTradeButtonBackground(): Drawable?


    override fun showProgressBar() {
        toolbar.showProgressBar()
    }


    override fun hideProgressBar() {
        toolbar.hideProgressBar()
    }


    override fun enableTradeButton() {
        tradeBtn.enable(true)
    }


    override fun disableTradeButton() {
        tradeBtn.disable(true)
    }


    override fun setAmountInputError(error: String) {
        amountLet.setErrorText(error)
    }


    override fun setAtPriceInputError(error: String) {
        atPriceLet.setErrorText(error)

    }


    override fun updateBalance() {
        val formatter = DoubleFormatter.getInstance(getLocale())

        baseCurrencyUserBalanceBmv.setSubtitleText(formatter.formatBalance(getBaseCurrencyUserBalance()))
        quoteCurrencyUserBalanceBmv.setSubtitleText(formatter.formatBalance(getQuoteCurrencyUserBalance()))
    }


    override fun updateTradeDetails(amount: Double, price: Double) {
        updateTransactionFee(amount, price)
        updateUserDeduction(amount, price)
        updateUserAddition(amount, price)
    }


    override fun updateUser(user: User) {
        mUser = user

        AppController.INSTANCE.setUser(user)
    }


    private fun updateTransactionFee(amount: Double, price: Double) {
        val transactionFee = calculateTransactionFee(amount, price)
        val doubleFormatter = DoubleFormatter.getInstance(getLocale())

        feeSmv.setValueText(String.format(
            "%s %s (%s)",
            doubleFormatter.formatTransactionFee(transactionFee),
            getTransactionFeeCoinName(),
            doubleFormatter.formatFeePercent(getFeePercent())
        ))
    }


    /**
     * Should calculate the transaction fee.
     *
     * @param amount The amount to calculate fee on
     * @param price The price at which the amount is going to be bought
     * or sold
     */
    protected abstract fun calculateTransactionFee(amount: Double, price: Double): Double


    /**
     * Should return a name of the coin for the transaction fee.
     */
    protected abstract fun getTransactionFeeCoinName(): String


    /**
     * Should return a percent fee for the transaction.
     */
    protected abstract fun getFeePercent(): Double


    private fun updateUserDeduction(amount: Double, price: Double) {
        val userDeduction = calculateUserDeduction(amount, price)
        val doubleFormatter = DoubleFormatter.getInstance(getLocale())

        userDeductionSmv.setValueText(String.format(
            "%s %s",
            doubleFormatter.formatAmount(userDeduction),
            getUserDeductionCoinName()
        ))
    }


    /**
     * Should calculate the user deduction.
     *
     * @param amount The amount to calculate user deduction on
     * @param price The price at which the amount is going to be bought
     * or sold
     */
    protected abstract fun calculateUserDeduction(amount: Double, price: Double): Double


    /**
     * Should return a name of the coin for the user deduction.
     */
    protected abstract fun getUserDeductionCoinName(): String


    private fun updateUserAddition(amount: Double, price: Double) {
        val userAddition = calculateUserAddition(amount, price)
        val doubleFormatter = DoubleFormatter.getInstance(getLocale())

        userAdditionSmv.setValueText(String.format(
            "%s %s",
            doubleFormatter.formatAmount(userAddition),
            getUserAdditionCoinName()
        ))
    }


    /**
     * Should calculate the user addition.
     *
     * @param amount The amount to calculate user addition on
     * @param price The price at which the amount is going to be bought
     * or sold
     */
    protected abstract fun calculateUserAddition(amount: Double, price: Double): Double


    /**
     * Should return a name of the coin for the user addition.
     */
    protected abstract fun getUserAdditionCoinName(): String


    override fun getMinOrderAmount(): String {
        return DoubleFormatter.getInstance(getLocale()).formatMinOrderAmount(mCurrencyMarket.minOrderAmount)
    }


    override fun getAmountInput(): Double {
        return amountLet.getInputText().convertToDouble(getLocale())
    }


    override fun getAtPriceInput(): Double {
        return atPriceLet.getInputText().convertToDouble(getLocale())
    }


    override fun getBaseCurrencyUserBalance(): Double {
        return mUser.funds.getWithDefault(mCurrencyMarket.baseCurrencySymbol, "0").toDouble()
    }


    override fun getQuoteCurrencyUserBalance(): Double {
        return mUser.funds.getWithDefault(mCurrencyMarket.quoteCurrencySymbol, "0").toDouble()
    }


    override fun getOrderTradeType(): OrderTradeTypes {
        return mOrderTradeType
    }


    override fun getUser(): User {
        return mUser
    }


    override fun getCurrencyMarket(): CurrencyMarket {
        return mCurrencyMarket
    }


    override fun getLocale(): Locale {
        return ctx.getLocale()
    }


    override fun getContentLayoutResourceId(): Int = R.layout.trade_activity_layout


    override fun getTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.HORIZONTAL_SLIDING_ANIMATIONS
    }


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            with(savedState) {
                mAmountInputTextString = getString(SAVED_STATE_AMOUNT_INPUT_TEXT_STRING, "")
                mPriceInputTextString = getString(SAVED_STATE_PRICE_INPUT_TEXT_STRING, "")
                mOrderTradeType = (getSerializable(SAVED_STATE_ORDER_TRADE_TYPE) as OrderTradeTypes)
                mCurrencyMarket = getParcelable(SAVED_STATE_CURRENCY_MARKET)!!
                mUser = getParcelable(SAVED_STATE_USER)!!
            }
        } else {
            with(intent) {
                mOrderTradeType = (getSerializableExtra(EXTRA_ORDER_TRADE_TYPE) as OrderTradeTypes)
                mCurrencyMarket = getParcelableExtra(EXTRA_CURRENCY_MARKET)
                mUser = getParcelableExtra(EXTRA_USER)
            }
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        with(savedState) {
            putString(SAVED_STATE_AMOUNT_INPUT_TEXT_STRING, amountLet.getInputText())
            putString(SAVED_STATE_PRICE_INPUT_TEXT_STRING, atPriceLet.getInputText())
            putSerializable(SAVED_STATE_ORDER_TRADE_TYPE, mOrderTradeType)
            putParcelable(SAVED_STATE_CURRENCY_MARKET, mCurrencyMarket)
            putParcelable(SAVED_STATE_USER, mUser)
        }
    }


}
package com.stocksexchange.android.ui.base.mvp.views

import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.api.model.OrderTradeTypes
import com.stocksexchange.android.api.model.User
import java.util.Locale

/**
 * A base trading view to build views on.
 */
interface TradeView {


    /**
     * Shows a toast.
     *
     * @param message The toast message
     */
    fun showToast(message: String)


    /**
     * Shows the progress bar.
     */
    fun showProgressBar()


    /**
     * Hides the progress bar.
     */
    fun hideProgressBar()


    /**
     * Enables the trade button.
     */
    fun enableTradeButton()


    /**
     * Disables the trade button.
     */
    fun disableTradeButton()


    /**
     * Sets an amount input error.
     *
     * @param error The error to set
     */
    fun setAmountInputError(error: String)


    /**
     * Sets an at price input error.
     */
    fun setAtPriceInputError(error: String)


    /**
     * Updates the user balance after the trade request has been sent.
     */
    fun updateBalance()


    /**
     * Updates the trade details (fee, user deduction, user addition)
     * with the passed information.
     *
     * @param amount The amount to trade
     * @param price The price at which to trade
     */
    fun updateTradeDetails(amount: Double, price: Double)


    /**
     * Updates the user.
     */
    fun updateUser(user: User)


    /**
     * Calculates the trade amount.
     *
     * @return The amount to trade
     */
    fun calculateAmount(): Double


    /**
     * Returns a raw amount from the input widget.
     *
     * @return The raw amount
     */
    fun getAmountInput(): Double


    /**
     * Returns a raw price from the input widget.
     *
     * @return The raw price
     */
    fun getAtPriceInput(): Double


    /**
     * Returns a user's base currency balance.
     *
     * @return The user's base currency balance
     */
    fun getBaseCurrencyUserBalance(): Double


    /**
     * Returns a user's quote currency balance.
     *
     * @return The user's quote currency balance
     */
    fun getQuoteCurrencyUserBalance(): Double


    /**
     * Returns a minimum order amount necessary for a trade.
     *
     * @return The minimum order amount
     */
    fun getMinOrderAmount(): String


    /**
     * Returns an order trade type (either buy or sell).
     *
     * @return The order trade type
     */
    fun getOrderTradeType(): OrderTradeTypes


    /**
     * Returns a signed in user.
     *
     * @return The signed in user
     */
    fun getUser(): User


    /**
     * Returns a currency market a trade is about to be performed on.
     *
     * @return The currency market
     */
    fun getCurrencyMarket(): CurrencyMarket


    /**
     * Returns a locale of the device
     *
     * @return The device's locale
     */
    fun getLocale(): Locale


}
package com.stocksexchange.android.ui.base.mvp.presenters

import com.stocksexchange.android.R
import com.stocksexchange.android.api.exceptions.AmountTooSmallException
import com.stocksexchange.android.api.exceptions.MaxNumOfOpenOrdersException
import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.api.model.OrderCreationParameters
import com.stocksexchange.android.api.model.OrderResponse
import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.model.DecimalSeparators
import com.stocksexchange.android.model.HttpCodes.*
import com.stocksexchange.android.ui.base.mvp.model.BaseTradeModel
import com.stocksexchange.android.ui.base.mvp.views.TradeView
import com.stocksexchange.android.utils.exceptions.NoInternetException
import com.stocksexchange.android.utils.formatters.DoubleFormatter
import com.stocksexchange.android.utils.providers.StringProvider
import org.koin.standalone.inject
import retrofit2.HttpException

/**
 * A base trading presenter to build presenters on.
 */
abstract class BaseTradePresenter<out M, out V>(
    model: M,
    view: V
) : BasePresenter<M, V>(model, view),
    BaseTradeModel.BaseTradeActionListener where
        M : BaseTradeModel<*>,
        V : TradeView {


    /**
     * A string provider used for fetching string related data.
     */
    protected val mStringProvider: StringProvider by inject()




    open fun onAmountInputChanged() {
        updateTradeDetails()
    }


    open fun onAtPriceInputChanged() {
        updateTradeDetails()
    }


    private fun updateTradeDetails() {
        mView.updateTradeDetails(mView.getAmountInput(), mView.getAtPriceInput())

        val isAmountValidated = validateAmountInput()
        val isAtPriceValidated = validateAtPriceInput()

        if(isAmountValidated && isAtPriceValidated) {
            mView.enableTradeButton()
        } else {
            mView.disableTradeButton()
        }
    }


    private fun validateAmountInput(): Boolean {
        val calculatedAmount: Double = mView.calculateAmount()

        if(mView.getCurrencyMarket().minOrderAmount > calculatedAmount) {
            mView.setAmountInputError(mStringProvider.getString(
                R.string.error_min_order_amount,
                mView.getMinOrderAmount()
            ))

            return false
        }

        if(calculatedAmount >= getAppropriateUserBalance()) {
            mView.setAmountInputError(mStringProvider.getString(
                R.string.error_balance_too_small,
                getBalanceTooSmallStringArg(mView.getCurrencyMarket())
            ))

            return false
        }

        mView.setAmountInputError("")
        return true
    }


    protected abstract fun getAppropriateUserBalance(): Double


    protected abstract fun getBalanceTooSmallStringArg(currencyMarket: CurrencyMarket): String


    private fun validateAtPriceInput(): Boolean {
        return if(mView.getAtPriceInput() <= 0.0) {
            mView.setAtPriceInputError(mStringProvider.getString(R.string.error_price_is_not_positive))
            false
        } else {
            mView.setAtPriceInputError("")
            true
        }
    }


    open fun onTradeButtonClicked() {
        if(mModel.isTradeOperationStarted) {
            return
        }

        sendTradeRequest()
    }


    private fun sendTradeRequest() {
        val amount = mView.getAmountInput()
        val price = mView.getAtPriceInput()

        val formatter = DoubleFormatter.getInstance(mView.getLocale())
        val decimalSeparator = formatter.getDecimalSeparator()
        val isGroupingEnabled = formatter.isGroupingEnabled()

        formatter.setGroupingEnabled(false)
        formatter.setDecimalSeparator(DecimalSeparators.PERIOD.separator)

        mModel.performTradeOperation(OrderCreationParameters(
            mView.getCurrencyMarket().name,
            mView.getOrderTradeType().name,
            formatter.formatAmount(amount),
            formatter.formatPrice(price)
        ))

        formatter.setDecimalSeparator(decimalSeparator)
        formatter.setGroupingEnabled(isGroupingEnabled)
    }


    override fun onTradeOperationStarted() {
        mView.showProgressBar()
    }


    override fun onTradeOperationEnded() {
        mView.hideProgressBar()
    }


    override fun onTradeOperationServerPartSucceeded(orderResponse: OrderResponse) {
        val user = mView.getUser().copy(funds = orderResponse.funds)

        mView.updateUser(user)
        mModel.performTradeOperationDatabasePart(user, orderResponse)
    }


    override fun onTradeOperationSucceeded(orderResponse: OrderResponse) {
        mView.updateBalance()
        mView.showToast(mStringProvider.getString(R.string.order_created))
    }


    override fun onTradeOperationFailed(error: Throwable) {
        mView.showToast(when(error) {
            is NoInternetException -> mStringProvider.getNetworkCheckMessage()
            is HttpException -> {
                when(error.code()) {
                    TOO_MANY_REQUESTS.code -> mStringProvider.getTooManyRequestsMessage()
                    in INTERNAL_SERVER_ERROR.code..NETWORK_CONNECT_TIMEOUT.code -> mStringProvider.getServerUnresponsiveMessage()

                    else -> mStringProvider.getSomethingWentWrongMessage()
                }
            }
            is AmountTooSmallException -> mStringProvider.getString(R.string.error_amount_too_small)
            is MaxNumOfOpenOrdersException -> mStringProvider.getString(R.string.error_max_num_of_open_orders)

            else -> mStringProvider.getSomethingWentWrongMessage()
        })
    }


    override fun toString(): String {
        return "${super.toString()}_${mView.getOrderTradeType().name}"
    }


}
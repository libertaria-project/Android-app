package com.stocksexchange.android.ui.base.mvp.model

import com.stocksexchange.android.api.model.OrderCreationParameters
import com.stocksexchange.android.api.model.OrderResponse
import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.model.utils.log
import com.stocksexchange.android.model.utils.onFailure
import com.stocksexchange.android.model.utils.onSuccess
import com.stocksexchange.android.repositories.orders.OrdersRepository
import com.stocksexchange.android.repositories.users.UsersRepository
import com.stocksexchange.android.ui.base.mvp.model.BaseTradeModel.BaseTradeActionListener
import org.koin.standalone.inject

/**
 * A base trading model to build model classes on.
 */
abstract class BaseTradeModel<
    in ActionListener: BaseTradeActionListener
> : BaseModel() {


    /**
     * A boolean flag signifying whether the trade operation has been
     * started or not.
     */
    var isTradeOperationStarted: Boolean = false
        private set


    private val mOrdersRepository: OrdersRepository by inject()
    private val mUsersRepository: UsersRepository by inject()

    private var mActionListener: ActionListener? = null




    fun performTradeOperation(params: OrderCreationParameters) {
        performTradeOperationServerPart(params)
        onTradeOperationStarted()
    }


    private fun performTradeOperationServerPart(params: OrderCreationParameters) {
        createUiLaunchCoroutine {
            mOrdersRepository.create(params)
                .log("ordersRepository.create(params: $params)")
                .onSuccess { mActionListener?.onTradeOperationServerPartSucceeded(it) }
                .onFailure { handleUnsuccessfulTradeOperationResponse(it) }
        }
    }


    fun performTradeOperationDatabasePart(user: User, orderResponse: OrderResponse) {
        createUiLaunchCoroutine {
            mUsersRepository.saveSignedInUser(user)

            handleSuccessfulTradeOperationResponse(orderResponse)
        }
    }


    private fun handleSuccessfulTradeOperationResponse(orderResponse: OrderResponse) {
        onTradeOperationEnded()

        mActionListener?.onTradeOperationSucceeded(orderResponse)
    }


    private fun handleUnsuccessfulTradeOperationResponse(error: Throwable) {
        onTradeOperationEnded()

        mActionListener?.onTradeOperationFailed(error)
    }


    private fun onTradeOperationStarted() {
        isTradeOperationStarted = true

        mActionListener?.onTradeOperationStarted()
    }


    private fun onTradeOperationEnded() {
        isTradeOperationStarted = false

        mActionListener?.onTradeOperationEnded()
    }


    fun setActionListener(listener: ActionListener) {
        mActionListener = listener
    }


    interface BaseTradeActionListener {

        fun onTradeOperationStarted()

        fun onTradeOperationEnded()

        fun onTradeOperationServerPartSucceeded(orderResponse: OrderResponse)

        fun onTradeOperationSucceeded(orderResponse: OrderResponse)

        fun onTradeOperationFailed(error: Throwable)

    }


}
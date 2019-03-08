package com.stocksexchange.android.ui.orders.fragment

import com.stocksexchange.android.api.model.*
import com.stocksexchange.android.model.DataLoadingSources
import com.stocksexchange.android.model.DataTypes
import com.stocksexchange.android.model.OrderModes
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.model.utils.log
import com.stocksexchange.android.model.utils.onFailure
import com.stocksexchange.android.model.utils.onSuccess
import com.stocksexchange.android.repositories.orders.OrdersRepository
import com.stocksexchange.android.repositories.users.UsersRepository
import com.stocksexchange.android.ui.base.mvp.model.BaseDataLoadingSimpleModel
import com.stocksexchange.android.ui.orders.fragment.OrdersModel.ActionListener
import org.koin.standalone.inject

class OrdersModel : BaseDataLoadingSimpleModel<
    List<Order>,
    OrderParameters,
    ActionListener
>() {


    /**
     * A boolean flag signifying whether the order cancellation operation
     * has been started or not.
     */
    var isOrderCancellationOperationStarted: Boolean = false
        private set


    private val mOrdersRepository: OrdersRepository by inject()
    private val mUsersRepository: UsersRepository by inject()




    override fun canLoadData(params: OrderParameters, dataType: DataTypes,
                             dataLoadingSource: DataLoadingSources): Boolean {
        val orderMode = params.mode
        val searchQuery = params.searchQuery

        val isOrderSearch = (orderMode == OrderModes.SEARCH)
        val isNewData = (dataType == DataTypes.NEW_DATA)

        val isOrderSearchWithNotQuery = (isOrderSearch && searchQuery.isBlank())
        val isOrderSearchNewData = (isOrderSearch && isNewData)
        val isNewDataWithIntervalNotApplied = (isNewData && !isDataLoadingIntervalApplied())
        val isNetworkConnectivitySource = (dataLoadingSource == DataLoadingSources.NETWORK_CONNECTIVITY)

        return (!isOrderSearchWithNotQuery
                && !isOrderSearchNewData
                && (!isNewDataWithIntervalNotApplied || isNetworkConnectivitySource))
    }


    override fun refreshData(params: OrderParameters) {
        mOrdersRepository.refresh(params)
    }


    override suspend fun getRepositoryResult(params: OrderParameters): RepositoryResult<List<Order>> {
        return when(params.mode) {
            OrderModes.STANDARD -> {
                when(params.type) {
                    OrderTypes.ACTIVE -> mOrdersRepository.getActiveOrders(params)
                    OrderTypes.COMPLETED -> mOrdersRepository.getCompletedOrders(params)
                    OrderTypes.CANCELLED -> mOrdersRepository.getCancelledOrders(params)
                }
            }

            OrderModes.SEARCH -> mOrdersRepository.search(params)
        }.log("ordersRepository.getOrders(params: $params)")
    }


    fun performOrderCancellationOperation(order: Order, orderParameters: OrderParameters) {
        performOrderCancellationOperationServerPart(order, orderParameters)
        onOrderCancellationOperationStarted()
    }


    private fun performOrderCancellationOperationServerPart(order: Order, orderParameters: OrderParameters) {
        createUiLaunchCoroutine {
            mOrdersRepository.cancel(order, orderParameters)
                .log("ordersRepository.cancel(order: $order, params: $orderParameters)")
                .onSuccess { mActionListener?.onOrderCancellationOperationServerPartSucceeded(it, order) }
                .onFailure { handleUnsuccessfulOrderCancellationOperation(it) }
        }
    }


    fun performOrderCancellationOperationDatabasePart(user: User, orderResponse: OrderResponse, order: Order) {
        createUiLaunchCoroutine {
            mUsersRepository.saveSignedInUser(user)

            handleSuccessfulOrderCancellationOperation(orderResponse, order)
        }
    }


    private fun handleSuccessfulOrderCancellationOperation(orderResponse: OrderResponse, order: Order) {
        onOrderCancellationOperationEnded()

        mActionListener?.onOrderCancellationOperationSucceeded(orderResponse, order)
    }


    private fun handleUnsuccessfulOrderCancellationOperation(error: Throwable) {
        onOrderCancellationOperationEnded()

        mActionListener?.onOrderCancellationOperationFailed(error)
    }


    private fun onOrderCancellationOperationStarted() {
        isOrderCancellationOperationStarted = true

        mActionListener?.onOrderCancellationOperationStarted()
    }


    private fun onOrderCancellationOperationEnded() {
        isOrderCancellationOperationStarted = false

        mActionListener?.onOrderCancellationOperationEnded()
    }


    interface ActionListener : BaseDataLoadingActionListener<List<Order>> {

        fun onOrderCancellationOperationStarted()

        fun onOrderCancellationOperationEnded()

        fun onOrderCancellationOperationServerPartSucceeded(orderResponse: OrderResponse, order: Order)

        fun onOrderCancellationOperationSucceeded(orderResponse: OrderResponse, order: Order)

        fun onOrderCancellationOperationFailed(error: Throwable)

    }


}
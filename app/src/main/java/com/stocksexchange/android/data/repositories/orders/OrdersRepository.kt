package com.stocksexchange.android.data.repositories.orders

import com.stocksexchange.android.api.model.newapi.Order
import com.stocksexchange.android.api.model.newapi.OrderParameters
import com.stocksexchange.android.api.model.newapi.OrdersCancellationResponse
import com.stocksexchange.android.data.base.OrdersData
import com.stocksexchange.android.model.RepositoryResult

interface OrdersRepository : OrdersData<
    RepositoryResult<Order>,
    RepositoryResult<OrdersCancellationResponse>,
    RepositoryResult<List<Order>>
>{

    fun refresh(params: OrderParameters)

}
package com.stocksexchange.android.data.stores.orders

import com.stocksexchange.android.api.model.newapi.OrdersCancellationResponse
import com.stocksexchange.android.api.model.newapi.Order
import com.stocksexchange.android.data.base.OrdersData
import com.stocksexchange.android.model.Result

interface OrdersDataStore : OrdersData<
    Result<Order>,
    Result<OrdersCancellationResponse>,
    Result<List<Order>>
>
package com.stocksexchange.android.data.repositories.orderbooks

import com.stocksexchange.android.api.model.newapi.Orderbook
import com.stocksexchange.android.api.model.newapi.OrderbookParameters
import com.stocksexchange.android.data.base.OrderbooksData
import com.stocksexchange.android.model.RepositoryResult

interface OrderbooksRepository : OrderbooksData<RepositoryResult<Orderbook>> {

    fun refresh(params: OrderbookParameters)

}
package com.stocksexchange.android.repositories.orderbook

import com.stocksexchange.android.api.model.Orderbook
import com.stocksexchange.android.api.model.OrderbookParameters
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.repositories.base.repository.Repository

interface OrderbookRepository : Repository {

    fun refresh(params: OrderbookParameters)

    suspend fun save(params: OrderbookParameters, orderbook: Orderbook)

    suspend fun get(params: OrderbookParameters): RepositoryResult<Orderbook>

}
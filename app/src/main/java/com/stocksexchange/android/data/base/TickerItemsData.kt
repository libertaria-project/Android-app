package com.stocksexchange.android.data.base

import com.stocksexchange.android.api.model.newapi.TickerItem

interface TickerItemsData<TickerItemsFetchingResult> {

    suspend fun save(tickerItems: List<TickerItem>)

    suspend fun deleteAll()

    suspend fun getAll(): TickerItemsFetchingResult

}
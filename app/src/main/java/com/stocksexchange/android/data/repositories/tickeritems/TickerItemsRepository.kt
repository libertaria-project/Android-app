package com.stocksexchange.android.data.repositories.tickeritems

import com.stocksexchange.android.api.model.newapi.TickerItem
import com.stocksexchange.android.data.base.TickerItemsData
import com.stocksexchange.android.model.RepositoryResult

interface TickerItemsRepository : TickerItemsData<RepositoryResult<List<TickerItem>>> {

    fun refresh()

}
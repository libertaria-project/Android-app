package com.stocksexchange.android.data.stores.tickeritems

import com.stocksexchange.android.api.model.newapi.TickerItem
import com.stocksexchange.android.data.base.TickerItemsData
import com.stocksexchange.android.model.Result

interface TickerItemsDataStore : TickerItemsData<Result<List<TickerItem>>>
package com.stocksexchange.android.data.stores.orderbooks

import com.stocksexchange.android.api.model.newapi.Orderbook
import com.stocksexchange.android.data.base.OrderbooksData
import com.stocksexchange.android.model.Result

interface OrderbooksDataStore : OrderbooksData<Result<Orderbook>>
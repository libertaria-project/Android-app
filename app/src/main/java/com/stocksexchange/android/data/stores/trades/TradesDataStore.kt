package com.stocksexchange.android.data.stores.trades

import com.stocksexchange.android.api.model.newapi.Trade
import com.stocksexchange.android.data.base.TradesData
import com.stocksexchange.android.model.Result

interface TradesDataStore : TradesData<Result<List<Trade>>>
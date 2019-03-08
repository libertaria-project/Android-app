package com.stocksexchange.android.data.stores.candlesticks

import com.stocksexchange.android.api.model.newapi.CandleStick
import com.stocksexchange.android.data.base.CandleSticksData
import com.stocksexchange.android.model.Result

interface CandleSticksDataStore : CandleSticksData<Result<List<CandleStick>>>
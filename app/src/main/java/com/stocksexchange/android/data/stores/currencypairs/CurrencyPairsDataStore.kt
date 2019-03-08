package com.stocksexchange.android.data.stores.currencypairs

import com.stocksexchange.android.api.model.newapi.CurrencyPair
import com.stocksexchange.android.data.base.CurrencyPairsData
import com.stocksexchange.android.model.Result

interface CurrencyPairsDataStore : CurrencyPairsData<Result<List<CurrencyPair>>>
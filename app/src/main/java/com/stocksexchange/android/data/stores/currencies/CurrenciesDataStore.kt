package com.stocksexchange.android.data.stores.currencies

import com.stocksexchange.android.api.model.newapi.Currency
import com.stocksexchange.android.data.base.CurrenciesData
import com.stocksexchange.android.model.Result

interface CurrenciesDataStore : CurrenciesData<Result<List<Currency>>>
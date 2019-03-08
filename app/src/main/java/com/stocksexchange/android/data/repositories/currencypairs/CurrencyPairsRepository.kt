package com.stocksexchange.android.data.repositories.currencypairs

import com.stocksexchange.android.api.model.newapi.CurrencyPair
import com.stocksexchange.android.data.base.CurrencyPairsData
import com.stocksexchange.android.model.RepositoryResult

interface CurrencyPairsRepository : CurrencyPairsData<RepositoryResult<List<CurrencyPair>>> {

    fun refresh()

}
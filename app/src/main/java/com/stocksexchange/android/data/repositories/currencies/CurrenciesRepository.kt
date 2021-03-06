package com.stocksexchange.android.data.repositories.currencies

import com.stocksexchange.android.api.model.newapi.Currency
import com.stocksexchange.android.data.base.CurrenciesData
import com.stocksexchange.android.model.RepositoryResult

interface CurrenciesRepository : CurrenciesData<RepositoryResult<List<Currency>>> {

    fun refresh()

}
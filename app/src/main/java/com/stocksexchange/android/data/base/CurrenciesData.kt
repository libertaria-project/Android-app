package com.stocksexchange.android.data.base

import com.stocksexchange.android.api.model.newapi.Currency

interface CurrenciesData<CurrenciesFetchingResult> {

    suspend fun save(currencies: List<Currency>)

    suspend fun deleteAll()

    suspend fun getAll(): CurrenciesFetchingResult

}
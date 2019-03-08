package com.stocksexchange.android.data.base

import com.stocksexchange.android.api.model.newapi.CurrencyPair

interface CurrencyPairsData<CurrencyPairsFetchingResult> {

    suspend fun save(currencyPairs: List<CurrencyPair>)

    suspend fun deleteAll()

    suspend fun getAll(): CurrencyPairsFetchingResult

}
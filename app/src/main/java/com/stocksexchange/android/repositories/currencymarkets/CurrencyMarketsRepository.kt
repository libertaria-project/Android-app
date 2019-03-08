package com.stocksexchange.android.repositories.currencymarkets

import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.repositories.base.repository.Repository

interface CurrencyMarketsRepository : Repository {

    fun refresh()

    suspend fun save(currencyMarket: CurrencyMarket)

    suspend fun save(currencyMarkets: List<CurrencyMarket>)

    suspend fun favorite(currencyMarket: CurrencyMarket)

    suspend fun unfavorite(currencyMarket: CurrencyMarket)

    suspend fun deleteAll()

    suspend fun search(query: String): RepositoryResult<List<CurrencyMarket>>

    suspend fun isCurrencyMarketFavorite(currencyMarket: CurrencyMarket): Boolean

    suspend fun getCurrencyMarket(id: Long): RepositoryResult<CurrencyMarket>

    suspend fun getCurrencyMarket(baseCurrencySymbol: String, quoteCurrencySymbol: String): RepositoryResult<CurrencyMarket>

    suspend fun getBitcoinMarkets(): RepositoryResult<List<CurrencyMarket>>

    suspend fun getTetherMarkets(): RepositoryResult<List<CurrencyMarket>>

    suspend fun getNxtMarkets(): RepositoryResult<List<CurrencyMarket>>

    suspend fun getLitecoinMarkets(): RepositoryResult<List<CurrencyMarket>>

    suspend fun getEthereumMarkets(): RepositoryResult<List<CurrencyMarket>>

    suspend fun getUsdMarkets(): RepositoryResult<List<CurrencyMarket>>

    suspend fun getEurMarkets(): RepositoryResult<List<CurrencyMarket>>

    suspend fun getJpyMarkets(): RepositoryResult<List<CurrencyMarket>>

    suspend fun getRubMarkets(): RepositoryResult<List<CurrencyMarket>>

    suspend fun getCurrencyMarkets(quoteCurrencySymbol: String): RepositoryResult<List<CurrencyMarket>>

    suspend fun getFavoriteMarkets(): RepositoryResult<List<CurrencyMarket>>

    suspend fun getAll(): RepositoryResult<List<CurrencyMarket>>

}
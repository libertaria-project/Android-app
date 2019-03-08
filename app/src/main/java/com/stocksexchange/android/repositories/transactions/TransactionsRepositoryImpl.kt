package com.stocksexchange.android.repositories.transactions

import com.stocksexchange.android.api.model.Transaction
import com.stocksexchange.android.datastores.transactions.TransactionsDataStore
import com.stocksexchange.android.datastores.transactions.TransactionsDatabaseDataStore
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.api.model.TransactionParameters
import com.stocksexchange.android.model.TransactionTypes
import com.stocksexchange.android.utils.providers.ConnectionProvider
import com.stocksexchange.android.repositories.base.repository.BaseRepositoryWithCache
import com.stocksexchange.android.utils.extensions.truncate
import com.stocksexchange.android.utils.helpers.handleRepositoryResult
import org.koin.standalone.inject

class TransactionsRepositoryImpl(
    private val serverDataStore: TransactionsDataStore,
    private val databaseDataStore: TransactionsDatabaseDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepositoryWithCache<TransactionsCache>(), TransactionsRepository {


    override val cache: TransactionsCache by inject()




    @Synchronized
    override fun refresh(params: TransactionParameters) {
        cache.removeTransactions(params.getCacheKey())
    }


    @Synchronized
    override suspend fun save(params: TransactionParameters, transactions: List<Transaction>) {
        databaseDataStore.save(transactions)
        saveTransactionsToCache(params, transactions)
    }


    @Synchronized
    override suspend fun delete(params: TransactionParameters, type: TransactionTypes) {
        databaseDataStore.delete(type.name)
        removeTransactions(params)
    }


    @Synchronized
    override suspend fun deleteAll() {
        databaseDataStore.deleteAll()
        removeAllTransactions()
    }


    @Synchronized
    override suspend fun search(params: TransactionParameters): RepositoryResult<List<Transaction>> {
        val result = get(params)

        // Making sure that the data is present since the search is performed
        // solely on database records
        return if(result.isSuccessful()) {
            RepositoryResult(databaseResult = databaseDataStore.search(params))
        } else {
            result
        }
    }


    @Synchronized
    override suspend fun get(params: TransactionParameters): RepositoryResult<List<Transaction>> {
        val result = RepositoryResult<List<Transaction>>()

        if(!cache.hasTransactions(params.getCacheKey())) {
            var onSuccess: suspend ((Result.Success<List<Transaction>>) -> Unit) = {}

            if(connectionProvider.isNetworkAvailable()) {
                result.serverResult = serverDataStore.get(params)
                onSuccess = {
                    // Deleting the old transactions to remove the redundant ones
                    // since the new data set may not already have them
                    delete(params, params.type)
                    save(params, it.value)
                }
            }

            if(result.isServerResultErroneous(true)) {
                result.databaseResult = databaseDataStore.get(params)
                onSuccess = { saveTransactionsToCache(params, it.value) }
            }

            handleRepositoryResult(result, onSuccess)
        } else {
            result.cacheResult = Result.Success(getTransactionsFromCache(params))
        }

        return result
    }


    private fun saveTransactionsToCache(params: TransactionParameters, transactions: List<Transaction>) {
        cache.saveTransactions(params.getCacheKey(), transactions)
    }


    private fun removeTransactions(params: TransactionParameters) {
        if(!cache.hasTransactions(params.getCacheKey())) {
            return
        }

        cache.removeTransactions(params.getCacheKey())
    }


    private fun removeAllTransactions() {
        if(cache.isEmpty()) {
            return
        }

        cache.clear()
    }


    private fun getTransactionsFromCache(params: TransactionParameters): List<Transaction> {
        return cache.getTransactions(params.getCacheKey()).truncate(params.count)
    }


}
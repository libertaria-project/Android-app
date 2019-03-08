package com.stocksexchange.android.datastores.transactions

import com.stocksexchange.android.api.StocksExchangeApi
import com.stocksexchange.android.api.model.Transaction
import com.stocksexchange.android.api.model.TransactionParameters
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class TransactionsServerDataStore(
    private val stocksExchangeApi: StocksExchangeApi
) : TransactionsDataStore {


    override suspend fun save(transactions: List<Transaction>) {
        throw UnsupportedOperationException()
    }


    override suspend fun delete(type: String) {
        throw UnsupportedOperationException()
    }


    override suspend fun deleteAll() {
        throw UnsupportedOperationException()
    }


    override suspend fun search(params: TransactionParameters): Result<List<Transaction>> {
        throw UnsupportedOperationException()
    }


    override suspend fun get(params: TransactionParameters): Result<List<Transaction>> {
        return performBackgroundOperation {
            stocksExchangeApi.getTransactions(params)
        }
    }


}
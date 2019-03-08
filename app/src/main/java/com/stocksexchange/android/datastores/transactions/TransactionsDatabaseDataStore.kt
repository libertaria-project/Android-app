package com.stocksexchange.android.datastores.transactions

import com.stocksexchange.android.api.model.Transaction
import com.stocksexchange.android.api.model.TransactionParameters
import com.stocksexchange.android.database.tables.TransactionsTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation

class TransactionsDatabaseDataStore(
    private val transactionsTable: TransactionsTable
) : TransactionsDataStore {


    override suspend fun save(transactions: List<Transaction>) {
        executeBackgroundOperation {
            transactionsTable.save(transactions)
        }
    }


    override suspend fun delete(type: String) {
        executeBackgroundOperation {
            transactionsTable.delete(type)
        }
    }


    override suspend fun deleteAll() {
        executeBackgroundOperation {
            transactionsTable.deleteAll()
        }
    }


    override suspend fun search(params: TransactionParameters): Result<List<Transaction>> {
        return performBackgroundOperation {
            transactionsTable.search(params)
        }
    }


    override suspend fun get(params: TransactionParameters): Result<List<Transaction>> {
        return performBackgroundOperation {
            transactionsTable.get(params)
        }
    }


}
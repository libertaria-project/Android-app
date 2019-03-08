package com.stocksexchange.android.repositories.transactions

import com.stocksexchange.android.api.model.Transaction
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.api.model.TransactionParameters
import com.stocksexchange.android.model.TransactionTypes
import com.stocksexchange.android.repositories.base.repository.Repository

interface TransactionsRepository : Repository {

    fun refresh(params: TransactionParameters)

    suspend fun save(params: TransactionParameters, transactions: List<Transaction>)

    suspend fun delete(params: TransactionParameters, type: TransactionTypes)

    suspend fun deleteAll()

    suspend fun search(params: TransactionParameters): RepositoryResult<List<Transaction>>

    suspend fun get(params: TransactionParameters): RepositoryResult<List<Transaction>>

}
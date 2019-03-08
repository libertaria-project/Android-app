package com.stocksexchange.android.database.tables

import com.stocksexchange.android.api.model.Transaction
import com.stocksexchange.android.api.model.TransactionParameters
import com.stocksexchange.android.database.daos.TransactionDao
import com.stocksexchange.android.mappings.mapToDatabaseTransactionList
import com.stocksexchange.android.mappings.mapToTransactionList
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.model.SortTypes
import org.koin.standalone.inject

/**
 * A database table holding transactions related functionality.
 */
object TransactionsTable : BaseTable() {


    private val mTransactionDao: TransactionDao by inject()




    /**
     * Saves transactions within the database.
     *
     * @param transactions The transactions to save
     */
    fun save(transactions: List<Transaction>) {
        mTransactionDao.insert(transactions.mapToDatabaseTransactionList())
    }


    /**
     * Deletes all transactions with a specific type.
     *
     * @param type The type of transactions to delete
     */
    fun delete(type: String) {
        mTransactionDao.delete(type)
    }


    /**
     * Deletes all transactions within the database.
     */
    fun deleteAll() {
        mTransactionDao.deleteAll()
    }


    /**
     * Searches transactions within the database based on the
     * passed parameters.
     *
     * @param params The transaction parameters for data loading
     *
     * @return The searched transactions or [NotFoundException] error
     * packaged inside an instance of [Result] class
     */
    fun search(params: TransactionParameters): Result<List<Transaction>> {
        val type = params.type.name
        val searchQuery = params.lowercasedSearchQuery
        val count = params.count

        return when(params.sortType) {
            SortTypes.ASC -> mTransactionDao.searchAsc(type, searchQuery, count)
            SortTypes.DESC -> mTransactionDao.searchDesc(type, searchQuery, count)
        }.mapToTransactionList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


    /**
     * Retrieves transactions based on the passed parameters.
     *
     * @param params The transaction parameters for data loading
     *
     * @return The transactions or [NotFoundException] error
     * packaged inside an instance of [Result] class
     */
    fun get(params: TransactionParameters): Result<List<Transaction>> {
        val type = params.type.name
        val count = params.count

        return when(params.sortType) {
            SortTypes.ASC -> mTransactionDao.getAsc(type, count)
            SortTypes.DESC -> mTransactionDao.getDesc(type, count)
        }.mapToTransactionList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}
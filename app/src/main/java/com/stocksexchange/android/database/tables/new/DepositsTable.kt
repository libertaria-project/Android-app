package com.stocksexchange.android.database.tables.new

import com.stocksexchange.android.api.model.newapi.Deposit
import com.stocksexchange.android.api.model.newapi.SortOrders
import com.stocksexchange.android.api.model.newapi.TransactionParameters
import com.stocksexchange.android.database.daos.newd.DepositDao
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.mappings.new.mapToDatabaseDepositList
import com.stocksexchange.android.mappings.new.mapToDepositList
import com.stocksexchange.android.model.Result
import org.koin.standalone.inject

/**
 * A database table holding deposits related functionality.
 */
object DepositsTable : BaseTable() {


    private val mDepositDao: DepositDao by inject()




    /**
     * Saves deposits inside the database.
     *
     * @param deposits The deposits to save
     */
    fun save(deposits: List<Deposit>) {
        mDepositDao.insert(deposits.mapToDatabaseDepositList())
    }


    /**
     * Deletes all deposits from the database.
     */
    fun deleteAll() {
        mDepositDao.deleteAll()
    }


    /**
     * Searches deposits inside the database based on the parameters.
     *
     * @param params The parameters for searching deposits
     *
     * @return The deposits or [NotFoundException] error packaged
     * inside an instance of [Result] class
     */
    fun search(params: TransactionParameters): Result<List<Deposit>> {
        val currencyCode = params.currencyCode
        val limit = params.limit
        val offset = params.offset

        return when(params.sortOrder) {
            SortOrders.ASC -> mDepositDao.searchAsc(currencyCode, limit, offset)
            SortOrders.DESC -> mDepositDao.searchDesc(currencyCode, limit, offset)
        }.mapToDepositList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


    /**
     * Retrieves deposits from the database.
     *
     * @param params The parameters for fetching deposits
     *
     * @return The deposits or [NotFoundException] error packaged
     * inside an instance of [Result] class
     */
    fun get(params: TransactionParameters): Result<List<Deposit>> {
        val limit = params.limit
        val offset = params.offset

        return when(params.sortOrder) {
            SortOrders.ASC -> mDepositDao.getAsc(limit, offset)
            SortOrders.DESC -> mDepositDao.getDesc(limit, offset)
        }.mapToDepositList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}
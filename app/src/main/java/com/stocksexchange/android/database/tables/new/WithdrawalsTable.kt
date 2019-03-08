package com.stocksexchange.android.database.tables.new

import com.stocksexchange.android.api.model.newapi.SortOrders
import com.stocksexchange.android.api.model.newapi.TransactionParameters
import com.stocksexchange.android.api.model.newapi.Withdrawal
import com.stocksexchange.android.database.daos.newd.WithdrawalDao
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.mappings.new.mapToDatabaseWithdrawalList
import com.stocksexchange.android.mappings.new.mapToWithdrawalList
import com.stocksexchange.android.model.Result
import org.koin.standalone.inject

/**
 * A database table holding withdrawals related functionality.
 */
object WithdrawalsTable : BaseTable() {


    private val mWithdrawalDao: WithdrawalDao by inject()




    /**
     * Saves withdrawals inside the database.
     *
     * @param withdrawals The withdrawals to save
     */
    fun save(withdrawals: List<Withdrawal>) {
        mWithdrawalDao.insert(withdrawals.mapToDatabaseWithdrawalList())
    }


    /**
     * Deletes all withdrawals from the database.
     */
    fun deleteAll() {
        mWithdrawalDao.deleteAll()
    }


    /**
     * Searches withdrawals inside the database based on the parameters.
     *
     * @param params The parameters for searching withdrawals
     *
     * @return The withdrawals or [NotFoundException] error packaged
     * inside an instance of [Result] class
     */
    fun search(params: TransactionParameters): Result<List<Withdrawal>> {
        val currencyCode = params.currencyCode
        val limit = params.limit
        val offset = params.offset

        return when(params.sortOrder) {
            SortOrders.ASC -> mWithdrawalDao.searchAsc(currencyCode, limit, offset)
            SortOrders.DESC -> mWithdrawalDao.searchDesc(currencyCode, limit, offset)
        }.mapToWithdrawalList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


    /**
     * Retrieves withdrawals from the database.
     *
     * @param params The parameters for loading withdrawals
     *
     * @return The withdrawals or [NotFoundException] error packaged
     * inside an instance of [Result] class
     */
    fun get(params: TransactionParameters): Result<List<Withdrawal>> {
        val limit = params.limit
        val offset = params.offset

        return when(params.sortOrder) {
            SortOrders.ASC -> mWithdrawalDao.getAsc(limit, offset)
            SortOrders.DESC -> mWithdrawalDao.getDesc(limit, offset)
        }.mapToWithdrawalList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}
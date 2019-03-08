package com.stocksexchange.android.database.tables

import com.stocksexchange.android.api.model.Deposit
import com.stocksexchange.android.api.model.DepositParameters
import com.stocksexchange.android.database.daos.DepositDao
import com.stocksexchange.android.mappings.mapToDatabaseDeposit
import com.stocksexchange.android.mappings.mapToDeposit
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.model.Result
import org.koin.standalone.inject

/**
 * A database table holding deposits related functionality.
 */
object DepositsTable : BaseTable() {


    private val mDepositDao: DepositDao by inject()




    /**
     * Saves a deposit within the database.
     *
     * @param deposit The deposit to save
     */
    fun save(deposit: Deposit) {
        mDepositDao.insert(deposit.mapToDatabaseDeposit())
    }


    /**
     * Deletes all deposits within the database.
     */
    fun deleteAll() {
        mDepositDao.deleteAll()
    }


    /**
     * Retrieves deposits based on the passed parameters.
     *
     * @param params The deposit parameters for data loading
     *
     * @return The deposits or [NotFoundException] error
     * packaged inside an instance of [Result] class
     */
    fun get(params: DepositParameters): Result<Deposit> {
        return mDepositDao.get(params.currency)
            ?.mapToDeposit()
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}
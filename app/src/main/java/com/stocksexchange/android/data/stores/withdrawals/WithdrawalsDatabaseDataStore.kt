package com.stocksexchange.android.data.stores.withdrawals

import com.stocksexchange.android.api.model.newapi.TransactionParameters
import com.stocksexchange.android.api.model.newapi.Withdrawal
import com.stocksexchange.android.database.tables.new.WithdrawalsTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class WithdrawalsDatabaseDataStore(
    private val withdrawalsTable: WithdrawalsTable
) : WithdrawalsDataStore {


    override suspend fun save(withdrawals: List<Withdrawal>) {
        executeBackgroundOperation {
            withdrawalsTable.save(withdrawals)
        }
    }


    override suspend fun deleteAll() {
        executeBackgroundOperation {
            withdrawalsTable.deleteAll()
        }
    }


    override suspend fun search(params: TransactionParameters): Result<List<Withdrawal>> {
        return performBackgroundOperation {
            withdrawalsTable.search(params)
        }
    }


    override suspend fun get(params: TransactionParameters): Result<List<Withdrawal>> {
        return performBackgroundOperation {
            withdrawalsTable.get(params)
        }
    }


}
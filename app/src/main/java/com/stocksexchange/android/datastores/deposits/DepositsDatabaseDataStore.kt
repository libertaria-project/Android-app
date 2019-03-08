package com.stocksexchange.android.datastores.deposits

import com.stocksexchange.android.api.model.Deposit
import com.stocksexchange.android.api.model.DepositParameters
import com.stocksexchange.android.database.tables.DepositsTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation

class DepositsDatabaseDataStore(
    private val depositsTable: DepositsTable
) : DepositsDataStore {


    override suspend fun save(deposit: Deposit) {
        executeBackgroundOperation {
            depositsTable.save(deposit)
        }
    }


    override suspend fun deleteAll() {
        executeBackgroundOperation {
            depositsTable.deleteAll()
        }
    }


    override suspend fun generateWalletAddress(params: DepositParameters): Result<Deposit> {
        throw UnsupportedOperationException()
    }


    override suspend fun get(params: DepositParameters): Result<Deposit> {
        return performBackgroundOperation {
            depositsTable.get(params)
        }
    }


}
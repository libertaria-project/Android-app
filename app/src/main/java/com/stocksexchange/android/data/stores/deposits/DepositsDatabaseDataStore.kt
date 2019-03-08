package com.stocksexchange.android.data.stores.deposits

import com.stocksexchange.android.api.model.newapi.Deposit
import com.stocksexchange.android.api.model.newapi.TransactionParameters
import com.stocksexchange.android.database.tables.new.DepositsTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class DepositsDatabaseDataStore(
    private val depositsTable: DepositsTable
) : DepositsDataStore {


    override suspend fun save(deposits: List<Deposit>) {
        executeBackgroundOperation {
            depositsTable.save(deposits)
        }
    }


    override suspend fun deleteAll() {
        executeBackgroundOperation {
            depositsTable.deleteAll()
        }
    }


    override suspend fun search(params: TransactionParameters): Result<List<Deposit>> {
        return performBackgroundOperation {
            depositsTable.search(params)
        }
    }


    override suspend fun get(params: TransactionParameters): Result<List<Deposit>> {
        return performBackgroundOperation {
            depositsTable.get(params)
        }
    }


}
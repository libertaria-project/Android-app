package com.stocksexchange.android.data.stores.deposits

import com.stocksexchange.android.api.StexApi
import com.stocksexchange.android.api.model.newapi.Deposit
import com.stocksexchange.android.api.model.newapi.TransactionParameters
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class DepositsServerDataStore(
    private val stexApi: StexApi
) : DepositsDataStore {


    override suspend fun save(deposits: List<Deposit>) {
        throw UnsupportedOperationException()
    }


    override suspend fun deleteAll() {
        throw UnsupportedOperationException()
    }


    override suspend fun search(params: TransactionParameters): Result<List<Deposit>> {
        throw UnsupportedOperationException()
    }


    override suspend fun get(params: TransactionParameters): Result<List<Deposit>> {
        return performBackgroundOperation {
            stexApi.getDeposits(params)
        }
    }


}
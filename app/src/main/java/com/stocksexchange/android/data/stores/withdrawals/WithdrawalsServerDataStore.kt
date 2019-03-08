package com.stocksexchange.android.data.stores.withdrawals

import com.stocksexchange.android.api.StexApi
import com.stocksexchange.android.api.model.newapi.TransactionParameters
import com.stocksexchange.android.api.model.newapi.Withdrawal
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class WithdrawalsServerDataStore(
    private val stexApi: StexApi
) : WithdrawalsDataStore {


    override suspend fun save(withdrawals: List<Withdrawal>) {
        throw UnsupportedOperationException()
    }


    override suspend fun deleteAll() {
        throw UnsupportedOperationException()
    }


    override suspend fun search(params: TransactionParameters): Result<List<Withdrawal>> {
        throw UnsupportedOperationException()
    }


    override suspend fun get(params: TransactionParameters): Result<List<Withdrawal>> {
        return performBackgroundOperation {
            stexApi.getWithdrawals(params)
        }
    }


}
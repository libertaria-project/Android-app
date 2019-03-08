package com.stocksexchange.android.datastores.deposits

import com.stocksexchange.android.api.StocksExchangeApi
import com.stocksexchange.android.api.model.Deposit
import com.stocksexchange.android.api.model.DepositParameters
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class DepositsServerDataStore(
    private val stocksExchangeApi: StocksExchangeApi
) : DepositsDataStore {


    override suspend fun save(deposit: Deposit) {
        throw UnsupportedOperationException()
    }


    override suspend fun deleteAll() {
        throw UnsupportedOperationException()
    }


    override suspend fun generateWalletAddress(params: DepositParameters): Result<Deposit> {
        return performBackgroundOperation {
            stocksExchangeApi.generateWalletAddress(params)
        }
    }


    override suspend fun get(params: DepositParameters): Result<Deposit> {
        return performBackgroundOperation {
            stocksExchangeApi.getDeposit(params)
        }
    }


}
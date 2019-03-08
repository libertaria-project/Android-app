package com.stocksexchange.android.datastores.deposits

import com.stocksexchange.android.api.model.Deposit
import com.stocksexchange.android.api.model.DepositParameters
import com.stocksexchange.android.model.Result

interface DepositsDataStore {

    suspend fun save(deposit: Deposit)

    suspend fun deleteAll()

    suspend fun generateWalletAddress(params: DepositParameters): Result<Deposit>

    suspend fun get(params: DepositParameters): Result<Deposit>

}
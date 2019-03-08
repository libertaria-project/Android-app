package com.stocksexchange.android.data.base

import com.stocksexchange.android.api.model.newapi.Deposit
import com.stocksexchange.android.api.model.newapi.TransactionParameters

interface DepositsData<DepositsFetchingResult> {

    suspend fun save(deposits: List<Deposit>)

    suspend fun deleteAll()

    suspend fun search(params: TransactionParameters): DepositsFetchingResult

    suspend fun get(params: TransactionParameters): DepositsFetchingResult

}
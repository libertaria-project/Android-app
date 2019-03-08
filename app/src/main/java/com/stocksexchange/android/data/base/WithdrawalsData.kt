package com.stocksexchange.android.data.base

import com.stocksexchange.android.api.model.newapi.TransactionParameters
import com.stocksexchange.android.api.model.newapi.Withdrawal

interface WithdrawalsData<WithdrawalsFetchingResult> {

    suspend fun save(withdrawals: List<Withdrawal>)

    suspend fun deleteAll()

    suspend fun search(params: TransactionParameters): WithdrawalsFetchingResult

    suspend fun get(params: TransactionParameters): WithdrawalsFetchingResult

}
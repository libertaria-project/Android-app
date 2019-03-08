package com.stocksexchange.android.repositories.deposits

import com.stocksexchange.android.api.model.Deposit
import com.stocksexchange.android.api.model.DepositParameters
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.repositories.base.repository.Repository

interface DepositsRepository : Repository {

    fun refresh(params: DepositParameters)

    suspend fun save(params: DepositParameters, deposit: Deposit)

    suspend fun deleteAll()

    suspend fun generateWalletAddress(params: DepositParameters): RepositoryResult<Deposit>

    suspend fun get(params: DepositParameters): RepositoryResult<Deposit>

}
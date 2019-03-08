package com.stocksexchange.android.data.repositories.withdrawals

import com.stocksexchange.android.api.model.newapi.Withdrawal
import com.stocksexchange.android.data.base.WithdrawalsData
import com.stocksexchange.android.model.RepositoryResult

interface WithdrawalsRepository : WithdrawalsData<RepositoryResult<List<Withdrawal>>> {

    fun refresh()

}
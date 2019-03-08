package com.stocksexchange.android.data.repositories.deposits

import com.stocksexchange.android.api.model.newapi.Deposit
import com.stocksexchange.android.data.base.DepositsData
import com.stocksexchange.android.model.RepositoryResult

interface DepositsRepository : DepositsData<RepositoryResult<List<Deposit>>> {

    fun refresh()

}
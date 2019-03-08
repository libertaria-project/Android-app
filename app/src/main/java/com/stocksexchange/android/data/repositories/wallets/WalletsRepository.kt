package com.stocksexchange.android.data.repositories.wallets

import com.stocksexchange.android.api.model.newapi.Wallet
import com.stocksexchange.android.data.base.WalletsData
import com.stocksexchange.android.model.RepositoryResult

interface WalletsRepository : WalletsData<RepositoryResult<List<Wallet>>> {

    fun refresh()

}
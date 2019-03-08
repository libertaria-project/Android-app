package com.stocksexchange.android.data.base

import com.stocksexchange.android.api.model.newapi.Wallet
import com.stocksexchange.android.api.model.newapi.WalletParameters

interface WalletsData<WalletsFetchingResult> {

    suspend fun save(wallets: List<Wallet>)

    suspend fun deleteAll()

    suspend fun getAll(params: WalletParameters): WalletsFetchingResult

}
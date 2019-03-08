package com.stocksexchange.android.data.stores.wallets

import com.stocksexchange.android.api.StexApi
import com.stocksexchange.android.api.model.newapi.Wallet
import com.stocksexchange.android.api.model.newapi.WalletParameters
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class WalletsServerDataStore(
    private val stexApi: StexApi
) : WalletsDataStore {


    override suspend fun save(wallets: List<Wallet>) {
        throw UnsupportedOperationException()
    }


    override suspend fun deleteAll() {
        throw UnsupportedOperationException()
    }


    override suspend fun getAll(params: WalletParameters): Result<List<Wallet>> {
        return performBackgroundOperation {
            stexApi.getWallets(params)
        }
    }


}
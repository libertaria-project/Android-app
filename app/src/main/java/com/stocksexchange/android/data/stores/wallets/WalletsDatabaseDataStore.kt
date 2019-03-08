package com.stocksexchange.android.data.stores.wallets

import com.stocksexchange.android.api.model.newapi.Wallet
import com.stocksexchange.android.api.model.newapi.WalletParameters
import com.stocksexchange.android.database.tables.new.WalletsTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class WalletsDatabaseDataStore(
    private val walletsTable: WalletsTable
) : WalletsDataStore {


    override suspend fun save(wallets: List<Wallet>) {
        executeBackgroundOperation {
            walletsTable.save(wallets)
        }
    }


    override suspend fun deleteAll() {
        executeBackgroundOperation {
            walletsTable.deleteAll()
        }
    }


    override suspend fun getAll(params: WalletParameters): Result<List<Wallet>> {
        return performBackgroundOperation {
            walletsTable.getAll(params)
        }
    }


}
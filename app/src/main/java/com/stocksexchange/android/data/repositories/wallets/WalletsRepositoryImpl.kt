package com.stocksexchange.android.data.repositories.wallets

import com.stocksexchange.android.api.model.newapi.Wallet
import com.stocksexchange.android.api.model.newapi.WalletParameters
import com.stocksexchange.android.data.stores.wallets.WalletsDataStore
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.repositories.base.repository.BaseRepository
import com.stocksexchange.android.repositories.utils.freshdatahandlers.concrete.SimpleFreshDataHandlerImpl
import com.stocksexchange.android.repositories.utils.freshdatahandlers.interfaces.FreshDataHandler
import com.stocksexchange.android.utils.providers.ConnectionProvider

class WalletsRepositoryImpl(
    private val serverDataStore: WalletsDataStore,
    private val databaseDataStore: WalletsDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepository(), WalletsRepository {


    override val freshDataHandler: FreshDataHandler = SimpleFreshDataHandlerImpl()




    override fun refresh() {
        freshDataHandler.refresh()
    }


    override suspend fun save(wallets: List<Wallet>) {
        databaseDataStore.save(wallets)
    }


    override suspend fun deleteAll() {
        databaseDataStore.deleteAll()
    }


    override suspend fun getAll(params: WalletParameters): RepositoryResult<List<Wallet>> {
        val result = RepositoryResult<List<Wallet>>()

        if(freshDataHandler.shouldLoadFreshData(connectionProvider)) {
            result.serverResult = serverDataStore.getAll(params)

            if(result.isServerResultSuccessful()) {
                deleteAll()
                save(result.getSuccessfulResultValue())
            }
        }

        if(result.isServerResultErroneous(true)) {
            result.databaseResult = databaseDataStore.getAll(params)
        }

        return result
    }


}
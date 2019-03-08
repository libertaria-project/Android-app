package com.stocksexchange.android.repositories.deposits

import com.stocksexchange.android.api.model.Deposit
import com.stocksexchange.android.api.model.DepositParameters
import com.stocksexchange.android.datastores.deposits.DepositsDataStore
import com.stocksexchange.android.datastores.deposits.DepositsDatabaseDataStore
import com.stocksexchange.android.utils.exceptions.NoInternetException
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.providers.ConnectionProvider
import com.stocksexchange.android.repositories.base.repository.BaseRepositoryWithCache
import com.stocksexchange.android.utils.helpers.handleRepositoryResult
import org.koin.standalone.inject

class DepositsRepositoryImpl(
    private val serverDataStore: DepositsDataStore,
    private val databaseDataStore: DepositsDatabaseDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepositoryWithCache<DepositsCache>(), DepositsRepository {


    override val cache: DepositsCache by inject()




    @Synchronized
    override fun refresh(params: DepositParameters) {
        cache.removeDeposit(params.getCacheKey())
    }


    @Synchronized
    override suspend fun save(params: DepositParameters, deposit: Deposit) {
        databaseDataStore.save(deposit)
        saveDepositToCache(params, deposit)
    }


    @Synchronized
    override suspend fun deleteAll() {
        databaseDataStore.deleteAll()
        removeAllDepositsFromCache()
    }


    @Synchronized
    override suspend fun generateWalletAddress(params: DepositParameters): RepositoryResult<Deposit> {
        return if(connectionProvider.isNetworkAvailable()) {
            RepositoryResult(serverResult = serverDataStore.generateWalletAddress(params))
        } else {
            RepositoryResult(serverResult = Result.Failure(NoInternetException()))
        }
    }


    @Synchronized
    override suspend fun get(params: DepositParameters): RepositoryResult<Deposit> {
        val result = RepositoryResult<Deposit>()

        if(!cache.hasDeposit(params.getCacheKey())) {
            var onSuccess: suspend ((Result.Success<Deposit>) -> Unit) = {}

            if(connectionProvider.isNetworkAvailable()) {
                result.serverResult = serverDataStore.get(params)
                onSuccess = { save(params, it.value) }
            }

            if(result.isServerResultErroneous(true)) {
                result.databaseResult = databaseDataStore.get(params)
                onSuccess = { saveDepositToCache(params, it.value) }
            }

            handleRepositoryResult(result, onSuccess)
        } else {
            result.cacheResult = Result.Success(getDepositFromCache(params))
        }

        return result
    }


    private fun saveDepositToCache(params: DepositParameters, deposit: Deposit) {
        cache.saveDeposit(params.getCacheKey(), deposit)
    }


    private fun removeAllDepositsFromCache() {
        if(cache.isEmpty()) {
            return
        }

        cache.clear()
    }


    private fun getDepositFromCache(params: DepositParameters): Deposit {
        return cache.getDeposit(params.getCacheKey())
    }


}
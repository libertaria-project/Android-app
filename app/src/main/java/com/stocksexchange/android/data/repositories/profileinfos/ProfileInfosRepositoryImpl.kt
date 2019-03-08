package com.stocksexchange.android.data.repositories.profileinfos

import com.stocksexchange.android.api.model.newapi.ProfileInfo
import com.stocksexchange.android.data.stores.profileinfos.ProfileInfosDataStore
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.repositories.base.repository.BaseRepository
import com.stocksexchange.android.repositories.utils.freshdatahandlers.concrete.SimpleFreshDataHandlerImpl
import com.stocksexchange.android.repositories.utils.freshdatahandlers.interfaces.FreshDataHandler
import com.stocksexchange.android.utils.providers.ConnectionProvider

class ProfileInfosRepositoryImpl(
    private val serverDataStore: ProfileInfosDataStore,
    private val databaseDataStore: ProfileInfosDataStore,
    private val connectionProvider: ConnectionProvider
) : BaseRepository(), ProfileInfosRepository {


    override val freshDataHandler: FreshDataHandler = SimpleFreshDataHandlerImpl()




    override fun refresh() {
        freshDataHandler.refresh()
    }


    override suspend fun save(profileInfo: ProfileInfo) {
        databaseDataStore.save(profileInfo)
    }


    override suspend fun deleteAll() {
        databaseDataStore.deleteAll()
    }


    override suspend fun get(email: String): RepositoryResult<ProfileInfo> {
        val result = RepositoryResult<ProfileInfo>()

        if(freshDataHandler.shouldLoadFreshData(connectionProvider)) {
            result.serverResult = serverDataStore.get(email)

            if(result.isServerResultSuccessful()) {
                save(result.getSuccessfulResultValue())
            }
        }

        if(result.isServerResultErroneous(true)) {
            result.databaseResult = databaseDataStore.get(email)
        }

        return result
    }


}
package com.stocksexchange.android.repositories.settings

import com.stocksexchange.android.datastores.settings.SettingsDataStore
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.repositories.base.repository.BaseRepositoryWithCache
import com.stocksexchange.android.utils.helpers.handleRepositoryResult
import org.koin.standalone.inject

class SettingsRepositoryImpl(
    private val databaseDataStore: SettingsDataStore
) : BaseRepositoryWithCache<SettingsCache>(), SettingsRepository {


    override val cache: SettingsCache by inject()




    @Synchronized
    override fun refresh() {
        cache.clear()
    }


    @Synchronized
    override suspend fun save(settings: Settings) {
        databaseDataStore.save(settings)
        saveToCache(settings)
    }


    @Synchronized
    override suspend fun get(): RepositoryResult<Settings> {
        val result = RepositoryResult<Settings>()

        if(!cache.hasSettings()) {
            result.databaseResult = databaseDataStore.get()

            handleRepositoryResult(result, { saveToCache(it.value) })
        } else {
            result.cacheResult = Result.Success(cache.getSettings())
        }

        return result
    }


    private fun saveToCache(settings: Settings) {
        cache.saveSettings(settings)
    }


}
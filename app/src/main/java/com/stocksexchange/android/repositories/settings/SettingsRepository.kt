package com.stocksexchange.android.repositories.settings

import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.repositories.base.repository.Repository

interface SettingsRepository : Repository {

    fun refresh()

    suspend fun save(settings: Settings)

    suspend fun get(): RepositoryResult<Settings>

}
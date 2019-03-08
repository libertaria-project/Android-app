package com.stocksexchange.android.database.tables

import com.stocksexchange.android.database.daos.SettingsDao
import com.stocksexchange.android.mappings.mapToDatabaseSettings
import com.stocksexchange.android.mappings.mapToSettings
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.model.Settings
import org.koin.standalone.inject

/**
 * A database table holding settings related functionality.
 */
object SettingsTable : BaseTable() {


    private val mSettingsDao: SettingsDao by inject()




    /**
     * Saves settings within the database.
     *
     * @param settings The settings to save
     */
    fun save(settings: Settings) {
        mSettingsDao.insert(settings.mapToDatabaseSettings())
    }


    /**
     * Retrieves settings from the database.
     *
     * @return The settings or [NotFoundException] error
     * packaged inside an instance of [Result] class
     */
    fun get(): Result<Settings> {
        return mSettingsDao.get(Settings.SETTINGS_ID)
            ?.mapToSettings()
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}
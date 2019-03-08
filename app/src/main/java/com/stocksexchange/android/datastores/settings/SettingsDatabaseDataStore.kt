package com.stocksexchange.android.datastores.settings

import com.stocksexchange.android.database.tables.SettingsTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.utils.helpers.performBackgroundOperation
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation

class SettingsDatabaseDataStore(
    private val settingsTable: SettingsTable
) : SettingsDataStore {


    override suspend fun save(settings: Settings) {
        executeBackgroundOperation {
            settingsTable.save(settings)
        }
    }


    override suspend fun get(): Result<Settings> {
        return performBackgroundOperation {
            settingsTable.get()
        }
    }


}
package com.stocksexchange.android.data.stores.profileinfos

import com.stocksexchange.android.api.model.newapi.ProfileInfo
import com.stocksexchange.android.database.tables.new.ProfileInfosTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class ProfileInfosDatabaseDataStore(
    private val profileInfosTable: ProfileInfosTable
) : ProfileInfosDataStore {


    override suspend fun save(profileInfo: ProfileInfo) {
        executeBackgroundOperation {
            profileInfosTable.save(profileInfo)
        }
    }


    override suspend fun deleteAll() {
        executeBackgroundOperation {
            profileInfosTable.deleteAll()
        }
    }


    override suspend fun get(email: String): Result<ProfileInfo> {
        return performBackgroundOperation {
            profileInfosTable.get(email)
        }
    }


}
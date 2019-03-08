package com.stocksexchange.android.data.stores.profileinfos

import com.stocksexchange.android.api.StexApi
import com.stocksexchange.android.api.model.newapi.ProfileInfo
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class ProfileInfosServerDataStore(
    private val stexApi: StexApi
) : ProfileInfosDataStore {


    override suspend fun save(profileInfo: ProfileInfo) {
        throw UnsupportedOperationException()
    }


    override suspend fun deleteAll() {
        throw UnsupportedOperationException()
    }


    override suspend fun get(email: String): Result<ProfileInfo> {
        return performBackgroundOperation {
            stexApi.getProfileInfo()
        }
    }


}
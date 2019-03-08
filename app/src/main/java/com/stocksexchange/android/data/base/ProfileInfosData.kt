package com.stocksexchange.android.data.base

import com.stocksexchange.android.api.model.newapi.ProfileInfo

interface ProfileInfosData<ProfileInfoFetchingResult> {

    suspend fun save(profileInfo: ProfileInfo)

    suspend fun deleteAll()

    suspend fun get(email: String): ProfileInfoFetchingResult

}
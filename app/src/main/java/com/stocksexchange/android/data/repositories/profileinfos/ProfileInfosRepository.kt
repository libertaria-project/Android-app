package com.stocksexchange.android.data.repositories.profileinfos

import com.stocksexchange.android.api.model.newapi.ProfileInfo
import com.stocksexchange.android.data.base.ProfileInfosData
import com.stocksexchange.android.model.RepositoryResult

interface ProfileInfosRepository : ProfileInfosData<RepositoryResult<ProfileInfo>> {

    fun refresh()

}
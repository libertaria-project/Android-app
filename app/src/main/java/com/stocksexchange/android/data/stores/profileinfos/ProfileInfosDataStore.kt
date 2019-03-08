package com.stocksexchange.android.data.stores.profileinfos

import com.stocksexchange.android.api.model.newapi.ProfileInfo
import com.stocksexchange.android.data.base.ProfileInfosData
import com.stocksexchange.android.model.Result

interface ProfileInfosDataStore : ProfileInfosData<Result<ProfileInfo>>
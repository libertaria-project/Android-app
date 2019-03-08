package com.stocksexchange.android.mappings.new

import com.stocksexchange.android.api.model.newapi.ProfileInfo
import com.stocksexchange.android.database.model.newd.DatabaseProfileInfo

fun ProfileInfo.mapToDatabaseProfileInfo(): DatabaseProfileInfo {
    return DatabaseProfileInfo(
        email = email,
        userName = userName
    )
}


fun DatabaseProfileInfo.mapToProfileInfo(): ProfileInfo {
    return ProfileInfo(
        email = email,
        userName = userName
    )
}
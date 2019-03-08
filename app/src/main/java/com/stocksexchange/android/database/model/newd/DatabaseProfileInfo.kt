package com.stocksexchange.android.database.model.newd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stocksexchange.android.api.model.newapi.ProfileInfo
import com.stocksexchange.android.database.model.newd.DatabaseProfileInfo.Companion.TABLE_NAME

/**
 * A Room database model for [ProfileInfo] class.
 */
@Entity(tableName = TABLE_NAME)
data class DatabaseProfileInfo(
    @PrimaryKey @ColumnInfo(name = EMAIL) var email: String,
    @ColumnInfo(name = USER_NAME) var userName: String
) {

    companion object {

        const val TABLE_NAME = "profile_infos"

        const val EMAIL = "email"
        const val USER_NAME = "user_name"

    }


    constructor(): this(
        email = "",
        userName = ""
    )

}
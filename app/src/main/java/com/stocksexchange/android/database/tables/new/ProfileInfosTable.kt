package com.stocksexchange.android.database.tables.new

import com.stocksexchange.android.api.model.newapi.ProfileInfo
import com.stocksexchange.android.database.daos.newd.ProfileInfoDao
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.mappings.new.mapToDatabaseProfileInfo
import com.stocksexchange.android.mappings.new.mapToProfileInfo
import com.stocksexchange.android.model.Result
import org.koin.standalone.inject

/**
 * A database table holding profile info related functionality.
 */
object ProfileInfosTable : BaseTable() {


    private val mProfileInfoDao: ProfileInfoDao by inject()




    /**
     * Saves profile information inside the database.
     *
     * @param profileInfo The profile information to save
     */
    fun save(profileInfo: ProfileInfo) {
        mProfileInfoDao.insert(profileInfo.mapToDatabaseProfileInfo())
    }


    /**
     * Deletes all profile information from the database.
     */
    fun deleteAll() {
        mProfileInfoDao.deleteAll()
    }


    /**
     * Retrieves profile information from the database.
     *
     * @param email The email of profile information to fetch
     *
     * @return The profile information or [NotFoundException] error
     * packaged inside an instance of [Result] class
     */
    fun get(email: String): Result<ProfileInfo> {
        return mProfileInfoDao.get(email)
            ?.mapToProfileInfo()
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}
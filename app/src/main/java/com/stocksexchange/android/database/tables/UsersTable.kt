package com.stocksexchange.android.database.tables

import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.database.daos.UserDao
import com.stocksexchange.android.mappings.mapToDatabaseUser
import com.stocksexchange.android.mappings.mapToUser
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.database.tables.exceptions.InvalidUserNameException
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.handlers.PreferenceHandler
import org.koin.standalone.inject

/**
 * A database table holding users related functionality.
 */
object UsersTable : BaseTable() {

    private val preferenceHandler: PreferenceHandler by inject()

    private val mUserDao: UserDao by inject()




    /**
     * Saves a signed in user within the database.
     *
     * @param user The user to save
     */
    fun saveSignedInUser(user: User) {
        mUserDao.insert(user.mapToDatabaseUser())
    }


    /**
     * Deletes a signed in user within the database.
     */
    fun deleteSignedInUser() {
        val userName = preferenceHandler.getUserName()

        if(userName.isBlank()) {
            return
        }

        mUserDao.deleteUser(userName)
    }


    /**
     * Retrieves a signed in user from the database.
     *
     * @return The user or a specific exception packaged inside
     * an instance of [Result] class
     */
    fun getSignedInUser(): Result<User> {
        val userName = preferenceHandler.getUserName()

        if(userName.isBlank()) {
            return Result.Failure(InvalidUserNameException(userName))
        }

        return mUserDao.get(userName)
            ?.mapToUser()
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}
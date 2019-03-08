package com.stocksexchange.android.repositories.users

import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.datastores.users.UsersDataStore
import com.stocksexchange.android.datastores.users.UsersDatabaseDataStore
import com.stocksexchange.android.utils.exceptions.NoInternetException
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.providers.ConnectionProvider
import com.stocksexchange.android.utils.handlers.PreferenceHandler
import com.stocksexchange.android.repositories.base.repository.BaseRepositoryWithCache
import com.stocksexchange.android.utils.helpers.handleRepositoryResult
import org.koin.standalone.inject

class UsersRepositoryImpl(
    private val serverDataStore: UsersDataStore,
    private val databaseDataStore: UsersDatabaseDataStore,
    private val preferenceHandler: PreferenceHandler,
    private val connectionProvider: ConnectionProvider
) : BaseRepositoryWithCache<UsersCache>(), UsersRepository {


    override val cache: UsersCache by inject()




    @Synchronized
    override fun refresh() {
        cache.clear()
    }


    @Synchronized
    override suspend fun saveSignedInUser(signedInUser: User) {
        databaseDataStore.saveSignedInUser(signedInUser)
        saveSignedInUserToCache(signedInUser)
    }


    @Synchronized
    override suspend fun deleteSignedInUser() {
        databaseDataStore.deleteSignedInUser()
        preferenceHandler.removeUserName()
        removeSignedInUserFromCache()
    }


    @Synchronized
    override suspend fun getSignedInUser(): RepositoryResult<User> {
        val result = RepositoryResult<User>()

        if(!cache.hasSignedInUser()) {
            val onSuccess: suspend ((Result.Success<User>) -> Unit)

            if(connectionProvider.isNetworkAvailable()) {
                result.serverResult = serverDataStore.getSignedInUser()
                onSuccess = {
                    saveSignedInUser(it.value)
                    saveSignedInUserToPreferences(it.value)
                }
            } else {
                result.serverResult = Result.Failure(NoInternetException())

                result.databaseResult = databaseDataStore.getSignedInUser()
                onSuccess = { saveSignedInUserToCache(it.value) }
            }

            handleRepositoryResult(result, onSuccess)
        } else {
            result.cacheResult = Result.Success(cache.getSignedInUser())
        }

        return result
    }


    @Synchronized
    override suspend fun hasSignedInUser(): Boolean {
        return (databaseDataStore.getSignedInUser() is Result.Success)
    }


    private fun saveSignedInUserToCache(user: User) {
        cache.saveSignedInUser(user)
    }


    private fun removeSignedInUserFromCache() {
        if(cache.hasSignedInUser()) {
            cache.removeSignedInUser()
        }
    }


    private fun saveSignedInUserToPreferences(user: User) {
        preferenceHandler.saveUserName(user.userName)
    }


}
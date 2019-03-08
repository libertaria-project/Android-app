package com.stocksexchange.android.repositories.users

import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.repositories.base.repository.Repository

interface UsersRepository : Repository {

    fun refresh()

    suspend fun saveSignedInUser(signedInUser: User)

    suspend fun deleteSignedInUser()

    suspend fun getSignedInUser(): RepositoryResult<User>

    suspend fun hasSignedInUser(): Boolean

}
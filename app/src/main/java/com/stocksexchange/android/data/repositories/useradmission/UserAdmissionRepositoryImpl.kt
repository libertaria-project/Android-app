package com.stocksexchange.android.data.repositories.useradmission

import com.stocksexchange.android.api.model.newapi.*
import com.stocksexchange.android.data.stores.useradmission.UserAdmissionDataStore
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.exceptions.NoInternetException
import com.stocksexchange.android.utils.providers.ConnectionProvider

class UserAdmissionRepositoryImpl(
    private val serverDataStore: UserAdmissionDataStore,
    private val connectionProvider: ConnectionProvider
) : UserAdmissionRepository {


    override suspend fun signIn(params: SignInParameters): RepositoryResult<SignInConfirmation> {
        if(!hasInternetConnection()) {
            return getNoInternetConnectionRepositoryResult()
        }

        return RepositoryResult(serverResult = serverDataStore.signIn(params))
    }


    override suspend fun confirmSignIn(params: SignInParameters): RepositoryResult<SignInConfirmationResponse> {
        if(!hasInternetConnection()) {
            return getNoInternetConnectionRepositoryResult()
        }

        return RepositoryResult(serverResult = serverDataStore.confirmSignIn(params))
    }


    override suspend fun signUp(params: SignUpParameters): RepositoryResult<SignUpResponse> {
        if(!hasInternetConnection()) {
            return getNoInternetConnectionRepositoryResult()
        }

        return RepositoryResult(serverResult = serverDataStore.signUp(params))
    }


    private fun hasInternetConnection(): Boolean = connectionProvider.isNetworkAvailable()


    private fun <T : Any> getNoInternetConnectionRepositoryResult(): RepositoryResult<T> {
        return RepositoryResult(serverResult = Result.Failure(NoInternetException()))
    }


}
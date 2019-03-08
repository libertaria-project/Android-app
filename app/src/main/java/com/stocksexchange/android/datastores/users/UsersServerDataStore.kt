package com.stocksexchange.android.datastores.users

import com.stocksexchange.android.api.StocksExchangeApi
import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class UsersServerDataStore(
    private val stocksExchangeApi: StocksExchangeApi
) : UsersDataStore {


    override suspend fun saveSignedInUser(signedInUser: User) {
        throw UnsupportedOperationException()
    }


    override suspend fun deleteSignedInUser() {
        throw UnsupportedOperationException()
    }


    override suspend fun getSignedInUser(): Result<User> {
        return performBackgroundOperation {
            stocksExchangeApi.getUser()
        }
    }


}
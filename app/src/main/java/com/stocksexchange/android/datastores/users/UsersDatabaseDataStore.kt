package com.stocksexchange.android.datastores.users

import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.database.tables.UsersTable
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation
import com.stocksexchange.android.utils.helpers.executeBackgroundOperation

class UsersDatabaseDataStore(
    private val usersTable: UsersTable
) : UsersDataStore {


    override suspend fun saveSignedInUser(signedInUser: User) {
        executeBackgroundOperation {
            usersTable.saveSignedInUser(signedInUser)
        }
    }


    override suspend fun deleteSignedInUser() {
        executeBackgroundOperation {
            usersTable.deleteSignedInUser()
        }
    }


    override suspend fun getSignedInUser(): Result<User> {
        return performBackgroundOperation {
            usersTable.getSignedInUser()
        }
    }


}
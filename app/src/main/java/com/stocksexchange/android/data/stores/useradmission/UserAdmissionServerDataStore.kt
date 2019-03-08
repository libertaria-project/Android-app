package com.stocksexchange.android.data.stores.useradmission

import com.stocksexchange.android.api.StexApi
import com.stocksexchange.android.api.model.newapi.*
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.helpers.performBackgroundOperation

class UserAdmissionServerDataStore(
    private val stexApi: StexApi
) : UserAdmissionDataStore {


    override suspend fun signIn(params: SignInParameters): Result<SignInConfirmation> {
        return performBackgroundOperation {
            stexApi.signIn(params)
        }
    }


    override suspend fun confirmSignIn(params: SignInParameters): Result<SignInConfirmationResponse> {
        return performBackgroundOperation {
            stexApi.confirmSignIn(params)
        }
    }


    override suspend fun signUp(params: SignUpParameters): Result<SignUpResponse> {
        return performBackgroundOperation {
            stexApi.signUp(params)
        }
    }


}
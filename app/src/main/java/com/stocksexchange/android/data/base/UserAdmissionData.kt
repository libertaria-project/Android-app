package com.stocksexchange.android.data.base

import com.stocksexchange.android.api.model.newapi.SignInParameters
import com.stocksexchange.android.api.model.newapi.SignUpParameters

interface UserAdmissionData<
    SignInResponseResult,
    SignInConfirmationResponseResult,
    SignUpResponseResult
> {

    suspend fun signIn(params: SignInParameters): SignInResponseResult

    suspend fun confirmSignIn(params: SignInParameters): SignInConfirmationResponseResult

    suspend fun signUp(params: SignUpParameters): SignUpResponseResult

}
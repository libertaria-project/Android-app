package com.stocksexchange.android.data.stores.useradmission

import com.stocksexchange.android.api.model.newapi.SignInConfirmation
import com.stocksexchange.android.api.model.newapi.SignInConfirmationResponse
import com.stocksexchange.android.api.model.newapi.SignUpResponse
import com.stocksexchange.android.data.base.UserAdmissionData
import com.stocksexchange.android.model.Result

interface UserAdmissionDataStore : UserAdmissionData<
    Result<SignInConfirmation>,
    Result<SignInConfirmationResponse>,
    Result<SignUpResponse>
>
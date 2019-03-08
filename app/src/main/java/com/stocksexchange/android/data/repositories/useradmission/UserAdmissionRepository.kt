package com.stocksexchange.android.data.repositories.useradmission

import com.stocksexchange.android.api.model.newapi.SignInConfirmation
import com.stocksexchange.android.api.model.newapi.SignInConfirmationResponse
import com.stocksexchange.android.api.model.newapi.SignUpResponse
import com.stocksexchange.android.data.base.UserAdmissionData
import com.stocksexchange.android.model.RepositoryResult

interface UserAdmissionRepository : UserAdmissionData<
    RepositoryResult<SignInConfirmation>,
    RepositoryResult<SignInConfirmationResponse>,
    RepositoryResult<SignUpResponse>
>
package com.stocksexchange.android.ui.login.new

import com.stocksexchange.android.api.model.newapi.SignInConfirmation
import com.stocksexchange.android.api.model.newapi.SignInConfirmationResponse
import com.stocksexchange.android.api.model.newapi.SignInParameters
import com.stocksexchange.android.data.repositories.useradmission.UserAdmissionRepository
import com.stocksexchange.android.model.utils.log
import com.stocksexchange.android.model.utils.onFailure
import com.stocksexchange.android.model.utils.onSuccess
import com.stocksexchange.android.ui.base.mvp.model.BaseModel
import org.koin.standalone.inject

class LoginModel : BaseModel() {


    /**
     * A boolean flag denoting whether any type of request
     * has been sent or not.
     */
    var isRequestFired: Boolean = false
        private set

    private var mActionListener: ActionListener? = null

    private val mUserAdmissionRepository: UserAdmissionRepository by inject()




    fun performSignInRequest(params: SignInParameters) {
        createUiLaunchCoroutine {
            mUserAdmissionRepository.signIn(params)
                .log("mUserAdmissionRepository.signIn(params: $params)")
                .onSuccess { handleSuccessfulSignInResponse(it) }
                .onFailure { handleErroneousSignInResponse(it) }
        }

        onRequestSent()
    }


    private fun handleSuccessfulSignInResponse(response: SignInConfirmation) {
        onResponseReceived()

        mActionListener?.onSignInRequestSucceeded(response)
    }


    private fun handleErroneousSignInResponse(error: Throwable) {
        onResponseReceived()

        mActionListener?.onSignInRequestFailed(error)
    }


    fun performSignInConfirmationRequest(params: SignInParameters) {
        createUiLaunchCoroutine {
            mUserAdmissionRepository.confirmSignIn(params)
                .log("mUserAdmissionRepository.confirmSignIn(params: $params)")
                .onSuccess { handleSuccessfulSignInConfirmationResponse(it) }
                .onFailure { handleErroneousSignInConfirmationResponse(it) }
        }

        onRequestSent()
    }


    private fun handleSuccessfulSignInConfirmationResponse(response: SignInConfirmationResponse) {
        onResponseReceived()

        mActionListener?.onSignInConfirmationRequestSucceeded(response)
    }


    private fun handleErroneousSignInConfirmationResponse(error: Throwable) {
        onResponseReceived()

        mActionListener?.onSignInConfirmationRequestFailed(error)
    }


    private fun onRequestSent() {
        isRequestFired = true

        mActionListener?.onRequestSent()
    }


    private fun onResponseReceived() {
        isRequestFired = false

        mActionListener?.onResponseReceived()
    }


    fun setActionListener(listener: ActionListener) {
        mActionListener = listener
    }


    interface ActionListener {

        fun onRequestSent()

        fun onResponseReceived()

        fun onSignInRequestSucceeded(response: SignInConfirmation)

        fun onSignInRequestFailed(error: Throwable)

        fun onSignInConfirmationRequestSucceeded(response: SignInConfirmationResponse)

        fun onSignInConfirmationRequestFailed(error: Throwable)

    }


}
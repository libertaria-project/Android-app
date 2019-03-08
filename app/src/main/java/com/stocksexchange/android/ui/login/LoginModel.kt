package com.stocksexchange.android.ui.login

import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.model.utils.log
import com.stocksexchange.android.model.utils.onFailure
import com.stocksexchange.android.model.utils.onSuccess
import com.stocksexchange.android.repositories.users.UsersRepository
import com.stocksexchange.android.ui.base.mvp.model.BaseModel
import org.koin.standalone.inject

class LoginModel : BaseModel() {


    /**
     * A boolean flag signifying whether the server request has been
     * sent or not.
     */
    var isRequestFired: Boolean = false
        private set

    private var mActionListener: ActionListener? = null

    private val mUsersRepository: UsersRepository by inject()




    fun performSignInRequest() {
        performSignInRequestAsync()
        onSignInRequestSent()
    }


    private fun performSignInRequestAsync() {
        createUiLaunchCoroutine {
            mUsersRepository.getSignedInUser()
                .log("usersRepository.getSignedInUser()")
                .onSuccess { handleSuccessfulSignInResponse(it) }
                .onFailure { handleUnsuccessfulSignInResponse(it) }
        }
    }

    private fun handleSuccessfulSignInResponse(user: User) {
        onSignInResponseReceived()

        mActionListener?.onSignInRequestSucceeded(user)
    }


    private fun handleUnsuccessfulSignInResponse(error: Throwable) {
        onSignInResponseReceived()

        mActionListener?.onSignInRequestFailed(error)
    }


    private fun onSignInRequestSent() {
        isRequestFired = true

        mActionListener?.onSignInRequestSent()
    }


    private fun onSignInResponseReceived() {
        isRequestFired = false

        mActionListener?.onSignInResponseReceived()
    }


    fun setActionListener(listener: ActionListener) {
        mActionListener = listener
    }


    interface ActionListener {

        fun onSignInRequestSent()

        fun onSignInResponseReceived()

        fun onSignInRequestSucceeded(user: User)

        fun onSignInRequestFailed(error: Throwable)

    }


}
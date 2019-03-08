package com.stocksexchange.android.ui.login.new

import com.stocksexchange.android.R
import com.stocksexchange.android.api.exceptions.InvalidParametersException
import com.stocksexchange.android.api.exceptions.UserAdmissionException
import com.stocksexchange.android.api.model.newapi.*
import com.stocksexchange.android.model.LoginProcessPhases
import com.stocksexchange.android.ui.base.mvp.presenters.BasePresenter
import com.stocksexchange.android.utils.SavedState
import com.stocksexchange.android.utils.exceptions.NoInternetException
import com.stocksexchange.android.utils.handlers.CredentialsHandler
import com.stocksexchange.android.utils.helpers.generateRandomString
import com.stocksexchange.android.utils.helpers.isEmailInvalid
import com.stocksexchange.android.utils.helpers.tag
import com.stocksexchange.android.utils.providers.StringProvider
import org.koin.standalone.inject

class LoginPresenter(
    model: LoginModel,
    view: LoginContract.View
) : BasePresenter<LoginModel, LoginContract.View>(model, view), LoginContract.ActionListener,
    LoginModel.ActionListener {


    companion object {

        private val CLASS = LoginPresenter::class.java

        private val SAVED_STATE_SIGN_IN_PARAMETERS = tag(CLASS, "sign_in_parameters")

    }


    private var mSignInParameters = SignInParameters.getDefaultParameters()

    private val mStringProvider: StringProvider by inject()

    private val mCredentialsHandler: CredentialsHandler by inject()




    init {
        model.setActionListener(this)
    }


    constructor(view: LoginContract.View): this(LoginModel(), view)


    override fun stop() {
        super.stop()

        mView.hideMaterialDialog()
    }


    private fun onConfirmationReceived(confirmation: SignInConfirmation) {
        val newProcessPhase = LoginProcessPhases.AWAITING_CONFIRMATION

        mView.setProcessPhase(newProcessPhase)
        mView.setConfirmationType(confirmation.confirmationType)
        mView.setMainButtonText(mStringProvider.getMainButtonTextForLoginProcessPhase(newProcessPhase))
        mView.updateMainView()
    }


    private fun onOauthCredentialsReceived(oauthCredentials: OAuthCredentials) {
        mCredentialsHandler.saveCredentials(oauthCredentials)

        mView.launchDestinationActivity()
        mView.finishActivity()
    }


    private fun onErrorReceived(exception: Throwable) {
        when(exception) {
            is NoInternetException -> mView.showToast(mStringProvider.getNetworkCheckMessage())
            is UserAdmissionException -> {
                when(exception.error) {
                    UserAdmissionErrors.UNKNOWN -> mView.showToast(mStringProvider.getSomethingWentWrongMessage())

                    else -> showInvalidCredentialsDialog(exception.loginErrors)
                }
            }
            is InvalidParametersException -> {
                if(exception.isWrongParamsError) {
                    showInvalidParametersDialog()
                } else {
                    mView.showToast(mStringProvider.getSomethingWentWrongMessage())
                }
            }

            else -> mView.showToast(mStringProvider.getSomethingWentWrongMessage())
        }
    }


    private fun onSecurityCodeReceived(code: String) {
        mSignInParameters = mSignInParameters.copy(code = code)

        sendSignInConfirmationRequest()
    }


    private fun showInvalidCredentialsDialog(errorsList: List<UserAdmissionErrors>) {
        if(errorsList.isEmpty()) {
            mView.showToast(mStringProvider.getSomethingWentWrongMessage())
            return
        }

        val stringBuilder = StringBuilder()
        var index = 1

        for(error in errorsList) {
            if(index > 1) {
                stringBuilder.append("\n")
            }

            stringBuilder
                .append(index)
                .append(". ")
                .append(mStringProvider.getString(error.stringId))

            index++
        }

        stringBuilder
            .append("\n\n")
            .append(mStringProvider.getString(R.string.login_invalid_credentials_dialog_footer_text))

        val title = mStringProvider.getString(R.string.login_invalid_credentials_dialog_title)
        val message = stringBuilder.toString()

        mView.showErrorDialog(title, message)
    }


    private fun showInvalidParametersDialog() {
        val title: String = mStringProvider.getString(R.string.login_invalid_parameters_dialog_title)
        val message: String = mStringProvider.getString(when(mView.getProcessPhase()) {
            LoginProcessPhases.AWAITING_CREDENTIALS -> R.string.error_invalid_credentials
            LoginProcessPhases.AWAITING_CONFIRMATION -> R.string.error_invalid_code
        })

        mView.showErrorDialog(title, message)
    }


    override fun onCredentialsViewHelpButtonClicked() {
        mView.showToast("To be implemented...")
        //todo
    }


    override fun onCredentialsViewRegisterButtonClicked() {
        mView.showToast("To be implemented...")
        //todo
    }


    override fun onConfirmationViewHelpButtonClicked(type: SignInConfirmationTypes) {
        if(type == SignInConfirmationTypes.TWO_FACTOR_AUTHENTICATION) {
            mView.showTwoFactorAuthHelpDialog()
        }
    }


    override fun onContactSupportButtonClicked() {
        mView.launchSupportActivity()
    }


    override fun onSecurityCodeReceived(type: SignInConfirmationTypes, code: String) {
        if(type != mView.getConfirmationType()) {
            mView.showToast(mStringProvider.getSomethingWentWrongMessage())

            return
        }

        onSecurityCodeReceived(code)
    }


    override fun onMainButtonClicked() {
        when(mView.getProcessPhase()) {
            LoginProcessPhases.AWAITING_CREDENTIALS -> handleCredentials()
            LoginProcessPhases.AWAITING_CONFIRMATION -> handleSecurityCode()
        }
    }


    private fun handleCredentials() {
        val email = mView.getEmail()

        // Checking if blank
        if(email.isBlank()) {
            mView.showToast(mStringProvider.getString(
                R.string.error_empty_email_address
            ))

            return
        }

        // Checking if invalid
        if(isEmailInvalid(email)) {
            mView.showToast(mStringProvider.getString(
                R.string.error_invald_email_address
            ))

            return
        }

        val password = mView.getPassword()

        // Checking if blank
        if(password.isBlank()) {
            mView.showToast(mStringProvider.getString(
                R.string.error_empty_password
            ))

            return
        }

        mSignInParameters = mSignInParameters.copy(
            email = email,
            password = password,
            key = generateRandomString(SignInParameters.KEY_STRING_LENGTH)
        )

        sendSignInRequest()
    }


    private fun sendSignInRequest() {
        if(mModel.isRequestFired) {
            return
        }

        mModel.performSignInRequest(mSignInParameters)
    }


    override fun onSignInRequestSucceeded(response: SignInConfirmation) {
        onConfirmationReceived(response)
    }


    override fun onSignInRequestFailed(error: Throwable) {
        onErrorReceived(error)
    }


    private fun handleSecurityCode() {
        val code = mView.getCode()

        // Checking if blank
        if(code.isBlank()) {
            mView.showToast(mStringProvider.getString(
                R.string.error_empty_code
            ))

            return
        }

        onSecurityCodeReceived(code)
    }


    private fun sendSignInConfirmationRequest() {
        if(mModel.isRequestFired) {
            return
        }

        mModel.performSignInConfirmationRequest(mSignInParameters)
    }


    override fun onSignInConfirmationRequestSucceeded(response: SignInConfirmationResponse) {
        if(!response.hasData) {
            return
        }

        if(response.hasOauthCredentials) {
            onOauthCredentialsReceived(response.oauthCredentials!!)
        } else {
            onConfirmationReceived(response.confirmation!!)
        }
    }


    override fun onSignInConfirmationRequestFailed(error: Throwable) {
        onErrorReceived(error)
    }


    override fun onRequestSent() {
        mView.showProgressBar()
    }


    override fun onResponseReceived() {
        mView.hideProgressBar()
    }


    override fun onRestoreState(savedState: SavedState) {
        super.onRestoreState(savedState)

        with(savedState) {
            mSignInParameters = get(
                SAVED_STATE_SIGN_IN_PARAMETERS,
                SignInParameters.getDefaultParameters()
            )
        }
    }


    override fun onSaveState(savedState: SavedState) {
        super.onSaveState(savedState)

        with(savedState) {
            save(SAVED_STATE_SIGN_IN_PARAMETERS, mSignInParameters)
        }
    }


}
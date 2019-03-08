package com.stocksexchange.android.ui.login

import com.crashlytics.android.Crashlytics
import com.stocksexchange.android.R
import com.stocksexchange.android.api.exceptions.InvalidCredentialsException
import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.model.Credentials
import com.stocksexchange.android.model.HttpCodes.*
import com.stocksexchange.android.ui.base.mvp.presenters.BasePresenter
import com.stocksexchange.android.utils.SavedState
import com.stocksexchange.android.utils.exceptions.NoInternetException
import com.stocksexchange.android.utils.handlers.CredentialsHandler
import com.stocksexchange.android.utils.handlers.QrCodeHandler
import com.stocksexchange.android.utils.helpers.tag
import com.stocksexchange.android.utils.providers.StringProvider
import org.koin.standalone.inject
import retrofit2.HttpException

class LoginPresenter(
    model: LoginModel,
    view: LoginContract.View
) : BasePresenter<LoginModel, LoginContract.View>(model, view), LoginContract.ActionListener, LoginModel.ActionListener {


    companion object {

        private val CLASS = LoginPresenter::class.java

        private val SAVED_STATE_IS_INVALID_CREDENTIALS_DIALOG_SHOWN = tag(CLASS, "is_invalid_credentials_dialog_shown")

    }


    private var mIsQrCodeScanned: Boolean = false
    private var mAreCredentialsManuallyEntered: Boolean = false
    private var mIsInvalidCredentialsDialogShown: Boolean = false

    private val mStringProvider: StringProvider by inject()
    private val mCredentialsHandler: CredentialsHandler by inject()
    private val mQrCodeHandler: QrCodeHandler by inject()




    init {
        model.setActionListener(this)
    }


    constructor(view: LoginContract.View): this(LoginModel(), view)


    override fun stop() {
        super.stop()

        mView.hideInvalidCredentialsDialog()
    }


    override fun onQrCodeScanned(qrCode: String) {
        mIsQrCodeScanned = true

        if(mQrCodeHandler.isQrCodeValid(qrCode)) {
            onCredentialsRetrieved(mQrCodeHandler.parseQrCode(qrCode)!!)
        } else {
            mView.showToast(mStringProvider.getString(R.string.error_malformed_qr_code))
        }
    }


    private fun onCredentialsRetrieved(credentials: Credentials) {
        if(mModel.isRequestFired) {
            return
        }

        mCredentialsHandler.saveKeys(credentials)
        mModel.performSignInRequest()
    }


    override fun onSignInProblemsButtonClicked() {
        mView.showCredentialsInputDialog()
    }


    override fun onCredentialsManuallyEntered(publicKey: String, secretKey: String) {
        mAreCredentialsManuallyEntered = true

        onCredentialsRetrieved(Credentials(publicKey, secretKey))
    }


    override fun onSignInRequestSent() {
        mView.showProgress()
        mView.disableQrCodeScanner()
        mView.disableSignInProblemsButton()
    }


    override fun onSignInResponseReceived() {
        mView.hideProgress()
        mView.enableQrCodeScanner()
        mView.enableSignInProblemsButton()
    }


    override fun onSignInRequestSucceeded(user: User) {
        mView.updateUser(user)
        mView.launchDestinationActivity()
        mView.finishActivity()
    }


    override fun onSignInRequestFailed(error: Throwable) {
        if(error is InvalidCredentialsException) {
            if(!mIsInvalidCredentialsDialogShown) {
                mView.showInvalidCredentialsDialog(mStringProvider.getString(when {
                    mIsQrCodeScanned -> R.string.login_invalid_credentials_dialog_qr_code_message
                    mAreCredentialsManuallyEntered -> R.string.login_invalid_credentials_dialog_keys_message

                    else -> throw IllegalStateException("Please provide a string message for the invalid credentials dialog.")
                }))

                mIsInvalidCredentialsDialogShown = true
            } else {
                mView.showToast(mStringProvider.getString(when {
                    mIsQrCodeScanned -> R.string.error_invalid_qr_code
                    mAreCredentialsManuallyEntered -> R.string.error_invalid_keys

                    else -> throw IllegalStateException("Please provide a string message for the invalid credentials toast.")
                }))
            }

            mCredentialsHandler.clearKeys()

            mIsQrCodeScanned = false
            mAreCredentialsManuallyEntered = false

            return
        }

        mView.showToast(when(error) {
            is NoInternetException -> mStringProvider.getNetworkCheckMessage()
            is HttpException -> {
                when(error.code()) {
                    NOT_FOUND.code -> mStringProvider.getUserNotFoundMessage()
                    TOO_MANY_REQUESTS.code -> mStringProvider.getTooManyRequestsMessage()
                    in INTERNAL_SERVER_ERROR.code..NETWORK_CONNECT_TIMEOUT.code -> mStringProvider.getServerUnresponsiveMessage()

                    else -> {
                        Crashlytics.logException(error)
                        mStringProvider.getSomethingWentWrongMessage()
                    }
                }
            }

            else -> {
                //todo to be removed
                Crashlytics.logException(error)
                mStringProvider.getSomethingWentWrongMessage()
            }
        })
    }


    override fun onRestoreState(savedState: SavedState) {
        super.onRestoreState(savedState)

        with(savedState) {
            mIsInvalidCredentialsDialogShown = get(SAVED_STATE_IS_INVALID_CREDENTIALS_DIALOG_SHOWN, false)
        }
    }


    override fun onSaveState(savedState: SavedState) {
        super.onSaveState(savedState)

        with(savedState) {
            save(SAVED_STATE_IS_INVALID_CREDENTIALS_DIALOG_SHOWN, mIsInvalidCredentialsDialogShown)
        }
    }


}

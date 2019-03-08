package com.stocksexchange.android.ui.auth

import com.stocksexchange.android.R
import com.stocksexchange.android.model.PinCode
import com.stocksexchange.android.model.PinCodeModes
import com.stocksexchange.android.ui.base.mvp.presenters.BasePresenter
import com.stocksexchange.android.utils.SavedState
import com.stocksexchange.android.utils.helpers.tag
import com.stocksexchange.android.utils.providers.StringProvider
import org.koin.standalone.inject

class AuthenticationPresenter(
    model: AuthenticationModel,
    view: AuthenticationContract.View
) : BasePresenter<AuthenticationModel, AuthenticationContract.View>(model, view), AuthenticationContract.ActionListener {


    companion object {

        private const val PIN_CODE_LENGTH = 4

        private val COMMON_PIN_CODES = arrayOf("1234", "1111", "0000", "1212", "7777")

        private val CLASS = AuthenticationPresenter::class.java

        private val SAVED_STATE_ARE_AUTH_VARIABLES_INITIALIZED = tag(CLASS, "are_auth_variables_initialized")
        private val SAVED_STATE_IS_TIMER_CANCELLED = tag(CLASS, "is_timer_cancelled")

    }


    private var mAreAuthVariablesInitialized: Boolean = false
    private var mIsTimerRunning: Boolean = false
    private var mIsTimerCanceled: Boolean = false

    private val mStringProvider: StringProvider by inject()




    constructor(view: AuthenticationContract.View): this(AuthenticationModel(), view)


    override fun start() {
        super.start()

        if(!mAreAuthVariablesInitialized) {
            mModel.initAuthVariables {
                restartTimerIfNecessary()
                runStartingChecks()
            }

            mAreAuthVariablesInitialized = true
        } else {
            runStartingChecks()
        }
    }


    private fun runStartingChecks() {
        updateFingerprintScannerButtonState()

        if(mIsTimerCanceled) {
            if(!restartTimerIfNecessary()) {
                mView.setMessageText(getHintMessage(), false)
                enableAuthentication()
            }

            mIsTimerCanceled = false
        }

        if(isFingerprintEnabled() &&
            !isFingerprintScannerExhausted() &&
            !isInProtectionMode()) {
            mView.showFingerprintDialog()
        }
    }


    override fun stop() {
        super.stop()

        mView.hideInvalidPinCodeAttemptsDialog()
        mView.hideFingerprintDialog()

        if(isInProtectionMode()) {
            mView.cancelTimer()
        }
    }


    private fun updateFingerprintScannerButtonState() {
        if(!isFingerprintEnabled()) {
            mView.hideKeypadFingerprintScannerButton()
            return
        }

        if(isFingerprintScannerExhausted()) {
            mView.disableKeypadFingerprintScannerButton()

            if(isInProtectionMode()) {
                mView.hideKeypadFingerprintOverlayView()
            } else {
                mView.showKeypadFingerprintOverlayView()
            }

            return
        }

        if(!isInProtectionMode()) {
            mView.showKeypadFingerprintScannerButton()
            mView.enableKeypadFingerprintScannerButton()
            mView.hideKeypadFingerprintOverlayView()
        }
    }


    private fun isFingerprintEnabled(): Boolean {
        return ((mView.getPinCodeMode() == PinCodeModes.ENTER) &&
                mView.getAppSettings().isFingerprintUnlockEnabled)
    }


    private fun isFingerprintScannerExhausted(): Boolean {
        return mModel.areFingerprintAttemptsUsedUp()
    }


    private fun isInProtectionMode(): Boolean {
        return mIsTimerRunning
    }


    override fun onFingerprintDialogButtonClicked() {
        mView.hideFingerprintDialog()
    }


    override fun onFingerprintRecognized() {
        mModel.resetAuthData()

        onUserAuthenticated()
    }


    override fun onFingerprintAttemptsWasted() {
        mModel.setFingerprintAttemptsUsedUp(true)

        mView.disableKeypadFingerprintScannerButton()
        mView.showKeypadFingerprintOverlayView()
    }


    override fun onUserAuthenticated() {
        val lastAuthTimestamp = System.currentTimeMillis()

        mModel.saveLastAuthTimestamp(lastAuthTimestamp) {
            mView.updateLastAuthenticationTimestamp(lastAuthTimestamp)
            mView.launchDestinationActivityIfNecessary()
            mView.finishActivityWithResult(true)
        }
    }


    override fun onKeypadDigitButtonClicked(digit: String) {
        val pinCode = mView.getPinCode()
        val confirmedPinCode = mView.getConfirmedPinCode()
        val pinCodeMode = mView.getPinCodeMode()
        val newPinCode: String

        if(pinCodeMode != PinCodeModes.CONFIRMATION) {
            newPinCode = (pinCode + digit)
            mView.updatePinCode(newPinCode)
        } else {
            newPinCode = (confirmedPinCode + digit)
            mView.updateConfirmedPinCode(newPinCode)
        }

        mView.updatePinBoxContainer(newPinCode)

        if(newPinCode.length == PIN_CODE_LENGTH) {
            mView.startIntentionalDelay(pinCodeMode, newPinCode)
        }
    }


    override fun onKeypadFingerprintButtonClicked() {
        mView.showFingerprintDialog()
    }


    override fun onKeypadFingerprintOverlayClicked() {
        mView.showToast(mStringProvider.getString(
            R.string.authentication_activity_fingerprint_exhausted_message
        ))
    }


    override fun onIntentionalDelayFinished(pinCodeMode: PinCodeModes, pinCode: String) {
        when(pinCodeMode) {
            PinCodeModes.CREATION -> onPinCodeEnteredOnCreation(pinCode)
            PinCodeModes.CONFIRMATION -> onPinCodeEnteredOnConfirmation(pinCode)
            PinCodeModes.ENTER -> onPinCodeEnteredOnEnter(pinCode)
            PinCodeModes.CHANGE -> onPinCodeEnteredOnChange(pinCode)
        }
    }


    private fun onPinCodeEnteredOnCreation(pinCode: String) {
        if(mView.getAppSettings().pinCode.code == pinCode) {
            mView.showToast(mStringProvider.getString(R.string.error_invalid_new_pin))
            mView.clearPinCode()
            mView.clearPinBoxContainer()

            return
        }

        if(COMMON_PIN_CODES.contains(pinCode)) {
            mView.showCommonPinCodesDialog()
            return
        }

        proceedToConfirmationStep()
    }


    private fun onPinCodeEnteredOnConfirmation(pinCode: String) {
        if(mView.getPinCode() != pinCode) {
            mView.showToast(mStringProvider.getString(R.string.error_pins_mismatch))

            mView.updatePinCodeMode(PinCodeModes.CREATION)
            mView.setMessageText(getHintMessage(), true)

            mView.clearPinCode()
            mView.clearConfirmedPinCode()
            mView.clearPinBoxContainer()

            return
        }

        val newSettings = mView.getAppSettings().copy(pinCode = PinCode(pinCode))

        mModel.updateSettings(newSettings) {
            mView.updateSettings(newSettings)

            onUserAuthenticated()
        }
    }


    private fun onPinCodeEnteredOnEnter(pinCode: String) {
        if(mView.getAppSettings().pinCode.code != pinCode) {
            mView.clearPinCode()
            mView.clearPinBoxContainer()

            applyProtectionMeasureIfNecessary()

            return
        }

        mModel.resetAuthData()

        onUserAuthenticated()
    }


    private fun onPinCodeEnteredOnChange(pinCode: String) {
        if(mView.getAppSettings().pinCode.code != pinCode) {
            mView.showToast(mStringProvider.getString(R.string.error_invalid_pin))
            mView.clearPinCode()
            mView.clearPinBoxContainer()

            applyProtectionMeasureIfNecessary()

            return
        }

        mModel.resetAuthData()

        mView.updatePinCodeMode(PinCodeModes.CREATION)
        mView.setMessageText(getHintMessage(), true)

        mView.clearPinCode()
        mView.clearPinBoxContainer()
    }


    private fun proceedToConfirmationStep() {
        mView.updatePinCodeMode(PinCodeModes.CONFIRMATION)

        mView.setMessageText(getHintMessage(), true)
        mView.clearPinBoxContainer()
    }


    private fun enableAuthentication() {
        mView.enableKeypadDigitButtons()
        mView.enableKeypadDeleteButton()

        if(isFingerprintEnabled()) {
            if(isFingerprintScannerExhausted()) {
                mView.showKeypadFingerprintOverlayView()
            } else {
                mView.enableKeypadFingerprintScannerButton()
            }
        }
    }


    private fun disableAuthentication() {
        mView.disableKeypadDigitButtons()
        mView.disableKeypadDeleteButton()

        if(isFingerprintEnabled()) {
            if(isFingerprintScannerExhausted()) {
                mView.hideKeypadFingerprintOverlayView()
            } else {
                mView.disableKeypadFingerprintScannerButton()
            }
        }
    }


    private fun startTimer(millisInFuture: Long, animateMessage: Boolean) {
        mView.setMessageText(getTimerMessage(millisInFuture), animateMessage) {
            mView.startTimer(
                millisInFuture,
                AuthenticationModel.TIMER_INTERVAL,
                AuthenticationModel.TIMER_MIN_FINISH_TIME
            )
        }

        mIsTimerRunning = true
    }


    /**
     * Restarts the timer if the timestamp of when to allow authentication
     * has not been reached yet.
     *
     * @return true if timer has been restarted; false otherwise
     */
    private fun restartTimerIfNecessary(): Boolean {
        val timeLeftToWait = (mModel.getAllowAuthTimestamp() - System.currentTimeMillis())

        return if(timeLeftToWait > 0L) {
            disableAuthentication()
            startTimer(timeLeftToWait, false)

            true
        } else {
            false
        }
    }


    private fun applyProtectionMeasureIfNecessary() {
        mModel.increaseInvalidPinCodeAttemptsNumber()

        if(mModel.getInvalidPinCodeAttemptsNumber() >= AuthenticationModel.MAX_INVALID_PIN_CODE_ATTEMPTS_NUMBER) {
            mModel.updateAllowAuthTimestamp()

            disableAuthentication()
            startTimer(AuthenticationModel.TIMER_DURATION, true)

            mView.showInvalidPinCodeAttemptsDialog(mModel.getInvalidPinCodeAttemptsNumber())
        } else {
            mView.showToast(mStringProvider.getString(R.string.error_invalid_pin))
        }
    }


    override fun onCommonPinCodesDialogPositiveButtonClicked() {
        proceedToConfirmationStep()
    }


    override fun onCommonPinCodesDialogNegativeButtonClicked() {
        mView.clearPinCode()
        mView.clearPinBoxContainer()
    }


    override fun onKeypadDeleteButtonClicked() {
        val pinCodeMode = mView.getPinCodeMode()
        val pinCode = if(pinCodeMode == PinCodeModes.CONFIRMATION) {
            mView.getConfirmedPinCode()
        } else {
            mView.getPinCode()
        }

        pinCode.takeIf { it.isNotEmpty() }?.apply {
            val newPinCode = dropLast(1)

            if(pinCodeMode == PinCodeModes.CONFIRMATION) {
                mView.updateConfirmedPinCode(newPinCode)
            } else {
                mView.updatePinCode(newPinCode)
            }

            mView.updatePinBoxContainer(newPinCode)
        }
    }


    override fun onTimerTick(millisecondsTillFinished: Long) {
        updateTimerMessage(millisecondsTillFinished)
    }


    override fun onTimerCancelled() {
        mIsTimerRunning = false
        mIsTimerCanceled = true
    }


    override fun onTimerFinished() {
        mIsTimerRunning = false

        mView.setMessageText(getHintMessage(), true) {
            mModel.deleteAllowAuthTimestamp()

            enableAuthentication()
        }
    }


    private fun updateTimerMessage(millisecondsTillFinished: Long) {
        mView.setMessageText(getTimerMessage(millisecondsTillFinished))
    }


    private fun getTimerMessage(millisecondsTillFinished: Long): String {
        val secondsLeft = (millisecondsTillFinished / AuthenticationModel.TIMER_INTERVAL).toInt()

        return mStringProvider.getQuantityString(
            R.plurals.authentication_activity_count_down_timer_template,
            secondsLeft,
            secondsLeft
        )
    }


    private fun getHintMessage(): String {
        return mStringProvider.getMessageForPinCodeMode(mView.getPinCodeMode())
    }


    override fun onBackPressed() {
        if(mView.getPinCodeMode() == PinCodeModes.CHANGE) {
            mView.finishActivityWithResult(false)
        }
    }


    override fun onRestoreState(savedState: SavedState) {
        super.onRestoreState(savedState)

        with(savedState) {
            mAreAuthVariablesInitialized = get(SAVED_STATE_ARE_AUTH_VARIABLES_INITIALIZED, false)
            mIsTimerCanceled = get(SAVED_STATE_IS_TIMER_CANCELLED, false)
        }
    }


    override fun onSaveState(savedState: SavedState) {
        super.onSaveState(savedState)

        with(savedState) {
            save(SAVED_STATE_ARE_AUTH_VARIABLES_INITIALIZED, mAreAuthVariablesInitialized)
            save(SAVED_STATE_IS_TIMER_CANCELLED, mIsTimerCanceled)
        }
    }


}
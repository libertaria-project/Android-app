package com.stocksexchange.android.ui.auth

import com.stocksexchange.android.model.PinCodeModes
import com.stocksexchange.android.model.Settings

interface AuthenticationContract {


    interface View {

        fun showToast(message: String)

        fun showKeypadFingerprintScannerButton()

        fun hideKeypadFingerprintScannerButton()

        fun showKeypadFingerprintOverlayView()

        fun hideKeypadFingerprintOverlayView()

        fun enableKeypadDigitButtons()

        fun disableKeypadDigitButtons()

        fun enableKeypadFingerprintScannerButton()

        fun disableKeypadFingerprintScannerButton()

        fun enableKeypadDeleteButton()

        fun disableKeypadDeleteButton()

        fun showInvalidPinCodeAttemptsDialog(invalidPinCodeAttemptsNumber: Int)

        fun hideInvalidPinCodeAttemptsDialog()

        fun showCommonPinCodesDialog()

        fun showFingerprintDialog()

        fun hideFingerprintDialog()

        fun startTimer(millisInFuture: Long, tickInterval: Long, minFinishTime: Long)

        fun cancelTimer()

        fun updatePinBoxContainer(pinCode: String)

        fun clearPinBoxContainer()

        fun updateLastAuthenticationTimestamp(lastAuthenticationTimestamp: Long)

        fun updatePinCode(pinCode: String)

        fun updateConfirmedPinCode(confirmedPinCode: String)

        fun updatePinCodeMode(pinCodeMode: PinCodeModes)

        fun updateSettings(settings: Settings)

        fun clearPinCode()

        fun clearConfirmedPinCode()

        fun launchDestinationActivityIfNecessary()

        fun finishActivityWithResult(result: Boolean)

        fun startIntentionalDelay(pinCodeMode: PinCodeModes, pinCode: String)

        fun setMessageText(message: String, animate: Boolean = false,
                           onFinish: (() -> Unit)? = null)

        fun getPinCode(): String

        fun getConfirmedPinCode(): String

        fun getPinCodeMode(): PinCodeModes

        fun getAppSettings(): Settings

    }


    interface ActionListener {

        fun onFingerprintDialogButtonClicked()

        fun onFingerprintRecognized()

        fun onFingerprintAttemptsWasted()

        fun onUserAuthenticated()

        fun onKeypadDigitButtonClicked(digit: String)

        fun onKeypadFingerprintButtonClicked()

        fun onKeypadFingerprintOverlayClicked()

        fun onKeypadDeleteButtonClicked()

        fun onTimerTick(millisecondsTillFinished: Long)

        fun onTimerCancelled()

        fun onTimerFinished()

        fun onCommonPinCodesDialogPositiveButtonClicked()

        fun onCommonPinCodesDialogNegativeButtonClicked()

        fun onIntentionalDelayFinished(pinCodeMode: PinCodeModes, pinCode: String)

        fun onBackPressed()

    }


}
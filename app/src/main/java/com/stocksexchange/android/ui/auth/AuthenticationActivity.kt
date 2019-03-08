package com.stocksexchange.android.ui.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.afollestad.materialdialogs.MaterialDialog
import com.stocksexchange.android.AppController
import com.stocksexchange.android.R
import com.stocksexchange.android.model.PinCodeModes
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.ui.base.activities.BaseActivity
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.views.PinEntryKeypad
import com.stocksexchange.android.ui.views.dialogs.FingerprintDialog
import com.stocksexchange.android.utils.Timer
import com.stocksexchange.android.utils.providers.StringProvider
import kotlinx.android.synthetic.main.authentication_activity_layout.*
import org.jetbrains.anko.intentFor
import org.koin.android.ext.android.get

class AuthenticationActivity : BaseActivity<AuthenticationPresenter>(), AuthenticationContract.View {


    companion object {

        const val TAG = "AuthenticationActivity"

        private const val EXTRA_PIN_CODE_MODE = "pin_code_mode"
        private const val EXTRA_TRANSITION_ANIMATIONS = "transition_animations"
        private const val EXTRA_THEME = "theme"
        private const val EXTRA_DESTINATION_INTENT = "destination_intent"

        private const val SAVED_STATE_PIN_CODE = "pin_code"
        private const val SAVED_STATE_CONFIRMED_PIN_CODE = "confirmed_pin_code"
        private const val SAVED_STATE_PIN_CODE_MODE = "pin_code_mode"
        private const val SAVED_STATE_TRANSITION_ANIMATIONS = "transition_animations"
        private const val SAVED_STATE_THEME = "theme"
        private const val SAVED_STATE_DESTINATION_INTENT = "destination_intent"

        private const val INTENTIONAL_DELAY_DURATION = 300L


        fun newInstance(context: Context, pinCodeMode: PinCodeModes,
                        transitionAnimations: TransitionAnimations, theme: Theme,
                        destinationIntent: Intent? = null): Intent {
            return context.intentFor<AuthenticationActivity>(
                EXTRA_PIN_CODE_MODE to pinCodeMode,
                EXTRA_TRANSITION_ANIMATIONS to transitionAnimations,
                EXTRA_THEME to theme,
                EXTRA_DESTINATION_INTENT to destinationIntent
            )
        }

    }


    private var mPinCode: String = ""
    private var mConfirmedPinCode: String = ""

    private var mPinCodeMode: PinCodeModes = PinCodeModes.ENTER

    private lateinit var mTransitionAnimations: TransitionAnimations
    private lateinit var mTheme: Theme

    private var mTimer: Timer? = null

    private lateinit var mHandler: Handler

    private var mDestinationIntent: Intent? = null

    private var mMaterialDialog: MaterialDialog? = null

    private var mFingerprintDialog: FingerprintDialog? = null




    override fun initPresenter(): AuthenticationPresenter = AuthenticationPresenter(this)


    override fun init() {
        super.init()

        mHandler = Handler(Looper.getMainLooper())

        initContentContainer()
        initMessage()
        initPinBoxContainer()
        initPinEntryKeypad()
    }


    private fun initContentContainer() {
        ThemingUtil.Authentication.contentContainer(contentContainerRl, mTheme)
    }


    private fun initMessage() {
        setMessageText(get<StringProvider>().getMessageForPinCodeMode(mPinCodeMode))

        ThemingUtil.Authentication.message(messageTv, mTheme)
    }


    private fun initPinBoxContainer() {
        updatePinBoxContainer(if(mPinCodeMode != PinCodeModes.CONFIRMATION) mPinCode else mConfirmedPinCode)
    }


    private fun initPinEntryKeypad() {
        pinEntryKeypad.onButtonClickListener = mOnButtonClickListener

        ThemingUtil.Authentication.pinEntryKeypad(pinEntryKeypad, mTheme)
    }


    override fun postInit() {
        super.postInit()

        overrideEnterTransition(mTransitionAnimations)
    }


    override fun showKeypadFingerprintScannerButton() {
        pinEntryKeypad.showFingerprintButton()
    }


    override fun hideKeypadFingerprintScannerButton() {
        pinEntryKeypad.hideFingerprintButton()
    }


    override fun showKeypadFingerprintOverlayView() {
        pinEntryKeypad.showFingerprintOverlayView()
    }


    override fun hideKeypadFingerprintOverlayView() {
        pinEntryKeypad.hideFingerprintOverlayView()
    }


    override fun enableKeypadDigitButtons() {
        pinEntryKeypad.enableDigitButtons()
    }


    override fun disableKeypadDigitButtons() {
        pinEntryKeypad.disableDigitButtons()
    }


    override fun enableKeypadFingerprintScannerButton() {
        pinEntryKeypad.enableFingerprintButton()
    }


    override fun disableKeypadFingerprintScannerButton() {
        pinEntryKeypad.disableFingerprintButton()
    }


    override fun enableKeypadDeleteButton() {
        pinEntryKeypad.enableDeleteButton()
    }


    override fun disableKeypadDeleteButton() {
        pinEntryKeypad.disableDeleteButton()
    }


    override fun showInvalidPinCodeAttemptsDialog(invalidPinCodeAttemptsNumber: Int) {
        val dialog = MaterialDialog.Builder(this)
            .content(getString(R.string.error_pin_code_too_many_attempts, invalidPinCodeAttemptsNumber))
            .positiveText(R.string.ok)
            .apply { ThemingUtil.Authentication.dialogBuilder(this, mTheme) }
            .build()

        mMaterialDialog = dialog
        mMaterialDialog?.show()
    }


    override fun hideInvalidPinCodeAttemptsDialog() {
        mMaterialDialog?.dismiss()
        mMaterialDialog = null
    }


    override fun showCommonPinCodesDialog() {
        val dialog = MaterialDialog.Builder(this)
            .title(R.string.authentication_activity_common_pin_codes_dialog_title)
            .content(R.string.authentication_activity_common_pin_codes_dialog_message)
            .positiveText(R.string.authentication_activity_common_pin_codes_dialog_positive_button_text)
            .negativeText(R.string.authentication_activity_common_pin_codes_dialog_negative_button_text)
            .onPositive(mCommonPinCodesDialogPositiveButtonListener)
            .onNegative(mCommonPinCodesDialogNegativeButtonListener)
            .cancelable(false)
            .apply { ThemingUtil.Authentication.dialogBuilder(this, mTheme) }
            .build()

        mMaterialDialog = dialog
        mMaterialDialog?.show()
    }


    override fun showFingerprintDialog() {
        val dialog = FingerprintDialog(
            this,
            FingerprintDialog.Modes.SCAN,
            mTheme,
            mFingerprintDialogListener
        )
        dialog.setButtonText(getString(R.string.action_use_pin))
        dialog.setButtonListener {
            mPresenter?.onFingerprintDialogButtonClicked()
        }
        dialog.startAuthentication()

        mFingerprintDialog = dialog
        mFingerprintDialog?.show()
    }


    override fun hideFingerprintDialog() {
        mFingerprintDialog?.dismiss()
        mFingerprintDialog = null
    }


    override fun startTimer(millisInFuture: Long, tickInterval: Long,
                            minFinishTime: Long) {
        mTimer = object : Timer(millisInFuture, tickInterval) {

            override fun onTick(millisUntilFinished: Long) {
                mPresenter?.onTimerTick(millisUntilFinished)
            }

            override fun onCancelled() {
                mPresenter?.onTimerCancelled()
            }

            override fun onFinished() {
                mPresenter?.onTimerFinished()
            }

        }
        mTimer?.setMinimumFinishTime(minFinishTime)
        mTimer?.start()
    }


    override fun cancelTimer() {
        mTimer?.cancel()
    }


    override fun updatePinBoxContainer(pinCode: String) {
        val pinCodeLength = pinCode.length

        updatePinBox(firstPinBox, pinCodeLength, 0)
        updatePinBox(secondPinBox, pinCodeLength, 1)
        updatePinBox(thirdPinBox, pinCodeLength, 2)
        updatePinBox(fourthPinBox, pinCodeLength, 3)
    }


    private fun updatePinBox(pinBoxIv: ImageView, pinCodeLength: Int, threshold: Int) {
        if(pinCodeLength > threshold) {
            ThemingUtil.Authentication.filledPinBox(pinBoxIv, mTheme)
        } else {
            ThemingUtil.Authentication.emptyPinBox(pinBoxIv, mTheme)
        }
    }


    override fun clearPinBoxContainer() {
        updatePinBoxContainer("")
    }


    override fun updateLastAuthenticationTimestamp(lastAuthenticationTimestamp: Long) {
        AppController.INSTANCE.getAppLockManager()
            ?.updateLastAuthenticationTimestamp(lastAuthenticationTimestamp)
    }


    override fun updatePinCode(pinCode: String) {
        mPinCode = pinCode
    }


    override fun updateConfirmedPinCode(confirmedPinCode: String) {
        mConfirmedPinCode = confirmedPinCode
    }


    override fun updatePinCodeMode(pinCodeMode: PinCodeModes) {
        mPinCodeMode = pinCodeMode
    }


    override fun updateSettings(settings: Settings) {
        AppController.INSTANCE.setSettings(settings)
    }


    override fun clearPinCode() {
        updatePinCode("")
    }


    override fun clearConfirmedPinCode() {
        updateConfirmedPinCode("")
    }


    override fun launchDestinationActivityIfNecessary() {
        if(mDestinationIntent != null) {
            startActivity(mDestinationIntent)
        }
    }


    override fun finishActivityWithResult(result: Boolean) {
        setResult(
            if(result) Activity.RESULT_OK else Activity.RESULT_CANCELED,
            Intent()
        )

        finish()
        overrideExitTransition(getExitTransitionAnimations())
    }


    override fun startIntentionalDelay(pinCodeMode: PinCodeModes, pinCode: String) {
        val runnable = Runnable {
            mPresenter?.onIntentionalDelayFinished(pinCodeMode, pinCode)
        }

        mHandler.postDelayed(runnable, INTENTIONAL_DELAY_DURATION)
    }


    override fun setMessageText(message: String, animate: Boolean,
                                onFinish: (() -> Unit)?) {
        if(animate) {
            messageTv.crossFadeText(text = message, onFinish = onFinish)
        } else {
            messageTv.text = message
            onFinish?.invoke()
        }
    }


    override fun getPinCode(): String = mPinCode


    override fun getConfirmedPinCode(): String = mConfirmedPinCode


    override fun getPinCodeMode(): PinCodeModes = mPinCodeMode


    override fun getAppSettings(): Settings {
        return getSettings()
    }


    override fun getContentLayoutResourceId(): Int = R.layout.authentication_activity_layout


    override fun getStatusBarColor(): Int {
        return mTheme.generalTheme.primaryDarkColor
    }


    override fun getRecentAppsToolbarColor(): Int {
        return mTheme.generalTheme.primaryColor
    }


    override fun getEnterTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.DEFAULT_ANIMATIONS
    }


    override fun getExitTransitionAnimations(): TransitionAnimations {
        return mTransitionAnimations
    }


    override fun onBackPressed() {
        mPresenter?.onBackPressed()
    }


    private val mOnButtonClickListener: PinEntryKeypad.OnButtonClickListener =
        object : PinEntryKeypad.OnButtonClickListener {

        override fun onDigitButtonClicked(digit: String) {
            mPresenter?.onKeypadDigitButtonClicked(digit)
        }

        override fun onFingerprintButtonClicked() {
            mPresenter?.onKeypadFingerprintButtonClicked()
        }

        override fun onFingerprintOverlayClicked() {
            mPresenter?.onKeypadFingerprintOverlayClicked()
        }

        override fun onDeleteButtonClicked() {
            mPresenter?.onKeypadDeleteButtonClicked()
        }

    }


    private val mCommonPinCodesDialogPositiveButtonListener = MaterialDialog.SingleButtonCallback { _, _ ->
        mPresenter?.onCommonPinCodesDialogPositiveButtonClicked()
    }


    private val mCommonPinCodesDialogNegativeButtonListener = MaterialDialog.SingleButtonCallback { _, _ ->
        mPresenter?.onCommonPinCodesDialogNegativeButtonClicked()
    }


    private val mFingerprintDialogListener: FingerprintDialog.Listener = object : FingerprintDialog.Listener {

        override fun onSuccess() {
            mPresenter?.onFingerprintRecognized()
        }


        override fun onError(error: FingerprintDialog.Errors) {
            when(error) {
                FingerprintDialog.Errors.TOO_MANY_ATTEMPTS -> {
                    mPresenter?.onFingerprintAttemptsWasted()
                }
            }
        }

    }


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            with(savedState) {
                mPinCode = (getString(SAVED_STATE_PIN_CODE, ""))
                mConfirmedPinCode = (getString(SAVED_STATE_CONFIRMED_PIN_CODE, ""))
                mPinCodeMode = (getSerializable(SAVED_STATE_PIN_CODE_MODE) as PinCodeModes)
                mTransitionAnimations = (getSerializable(SAVED_STATE_TRANSITION_ANIMATIONS) as TransitionAnimations)
                mTheme = (getSerializable(SAVED_STATE_THEME) as Theme)
                mDestinationIntent = getParcelable(SAVED_STATE_DESTINATION_INTENT)
            }
        } else {
            if(intent != null) {
                with(intent) {
                    mPinCodeMode = (getSerializableExtra(EXTRA_PIN_CODE_MODE) as PinCodeModes)
                    mTransitionAnimations = (getSerializableExtra(EXTRA_TRANSITION_ANIMATIONS) as TransitionAnimations)
                    mTheme = (getSerializableExtra(EXTRA_THEME) as Theme)
                    mDestinationIntent = getParcelableExtra(EXTRA_DESTINATION_INTENT)
                }
            }
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        with(savedState) {
            putString(SAVED_STATE_PIN_CODE, mPinCode)
            putString(SAVED_STATE_CONFIRMED_PIN_CODE, mConfirmedPinCode)
            putSerializable(SAVED_STATE_PIN_CODE_MODE, mPinCodeMode)
            putSerializable(SAVED_STATE_TRANSITION_ANIMATIONS, mTransitionAnimations)
            putSerializable(SAVED_STATE_THEME, mTheme)
            putParcelable(SAVED_STATE_DESTINATION_INTENT, mDestinationIntent)
        }
    }


}
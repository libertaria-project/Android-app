package com.stocksexchange.android.ui.login

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.stocksexchange.android.AppController
import com.stocksexchange.android.Constants
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.model.PinCodeModes
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.ui.base.activities.BaseActivity
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.auth.AuthenticationActivity
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.views.dialogs.CredentialsInputDialog
import com.stocksexchange.android.utils.helpers.isPermissionSetGranted
import kotlinx.android.synthetic.main.login_activity_layout.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop

class LoginActivity : BaseActivity<LoginPresenter>(), LoginContract.View {


    companion object {

        const val TAG = "LoginActivity"

        private const val EXTRA_TRANSITION_ANIMATIONS = "transition_animations"
        private const val EXTRA_DESTINATION_INTENT = "destination_intent"

        private const val SAVED_STATE_TRANSITION_ANIMATIONS = "transition_animations"
        private const val SAVED_STATE_DESTINATION_INTENT = "destination_intent"


        fun newInstance(context: Context, transitionAnimations: TransitionAnimations,
                        destinationIntent: Intent): Intent {
            return context.intentFor<LoginActivity>(
                EXTRA_TRANSITION_ANIMATIONS to transitionAnimations,
                EXTRA_DESTINATION_INTENT to destinationIntent
            ).clearTop().singleTop()
        }

    }


    private lateinit var mTransitionAnimations: TransitionAnimations

    private lateinit var mDestinationIntent: Intent

    private var mInvalidCredentialsDialog: MaterialDialog? = null

    private var mCredentialsInputDialog: CredentialsInputDialog? = null




    override fun initPresenter(): LoginPresenter = LoginPresenter(this)


    override fun init() {
        super.init()

        initContentContainer()
        initTitle()
        initMotto()
        initQrCodeScanner()
        initQrCodeScannerBorders()
        initQrCodeScannerTint()
        initProgressBar()
        initHint()
        initSignInProblemsButton()

        if(checkPermissions(Constants.REQUEST_CODE_CAMERA_PERMISSION, arrayOf(Manifest.permission.CAMERA))) {
            qrCodeScanner.onCameraPermissionGranted()
        }
    }


    private fun initContentContainer() {
        ThemingUtil.Login.contentContainer(contentContainerCl, getAppTheme())
    }


    private fun initTitle() {
        ThemingUtil.Login.title(titleTv, getAppTheme())
    }


    private fun initMotto() {
        ThemingUtil.Login.motto(mottoTv, getAppTheme())
    }


    private fun initQrCodeScanner() {
        qrCodeScanner.onBarcodeDetected {
            mPresenter?.onQrCodeScanned(it.rawValue)
        }
    }


    private fun initQrCodeScannerBorders() {
        with(ThemingUtil.Login) {
            val theme = getAppTheme()

            qrScannerBorder(topLeftLeftLine, theme)
            qrScannerBorder(topLeftTopLine, theme)
            qrScannerBorder(topRightRightLine, theme)
            qrScannerBorder(topRightTopLine, theme)
            qrScannerBorder(bottomLeftLeftLine, theme)
            qrScannerBorder(bottomLeftBottomLine, theme)
            qrScannerBorder(bottomRightRightLine, theme)
            qrScannerBorder(bottomRightBottomLine, theme)
        }
    }


    private fun initQrCodeScannerTint() {
        ThemingUtil.Login.qrScannerTint(qrCodeScannerTint, getAppTheme())
    }


    private fun initProgressBar() {
        ThemingUtil.apply(progressBar, getAppTheme())
    }


    private fun initHint() {
        ThemingUtil.Login.hint(hintTv, getAppTheme())
    }


    private fun initSignInProblemsButton() {
        signInProblemsBtnTv.setOnClickListener {
            mPresenter?.onSignInProblemsButtonClicked()
        }

        ThemingUtil.Login.signInProblems(signInProblemsBtnTv, getAppTheme())
    }


    override fun postInit() {
        super.postInit()

        overrideEnterTransition(mTransitionAnimations)
    }


    override fun showInvalidCredentialsDialog(content: String) {
        val dialog = MaterialDialog.Builder(this)
            .title(R.string.login_invalid_credentials_dialog_title)
            .content(content)
            .positiveText(R.string.ok)
            .apply { ThemingUtil.Login.dialogBuilder(this, getAppTheme()) }
            .build()

        mInvalidCredentialsDialog = dialog
        mInvalidCredentialsDialog?.show()
    }


    override fun hideInvalidCredentialsDialog() {
        mInvalidCredentialsDialog?.dismiss()
        mInvalidCredentialsDialog = null
    }


    override fun showCredentialsInputDialog() {
        val dialog = CredentialsInputDialog(this, mCredentialsInputDialogListener)
        ThemingUtil.Login.credentialsInputDialog(dialog, getAppTheme())

        mCredentialsInputDialog = dialog
        mCredentialsInputDialog?.show()
    }


    override fun showProgress() {
        if(!progressGrp.isVisible()) {
            progressGrp.makeVisible()
        }
    }


    override fun hideProgress() {
        if(progressGrp.isVisible()) {
            progressGrp.makeGone()
        }
    }


    override fun enableQrCodeScanner() {
        if(!qrCodeScanner.isEnabled) {
            qrCodeScanner.enable()
        }
    }


    override fun disableQrCodeScanner() {
        if(qrCodeScanner.isEnabled) {
            qrCodeScanner.disable()
        }
    }


    override fun enableSignInProblemsButton() {
        if(!signInProblemsBtnTv.isEnabled) {
            signInProblemsBtnTv.enable()
        }
    }


    override fun disableSignInProblemsButton() {
        if(signInProblemsBtnTv.isEnabled) {
            signInProblemsBtnTv.disable()
        }
    }


    override fun updateUser(user: User) {
        AppController.INSTANCE.setUser(user)
    }


    override fun launchDestinationActivity() {
        startActivity(AuthenticationActivity.newInstance(
            this,
            PinCodeModes.CREATION,
            TransitionAnimations.OVERSHOOT_SCALING_ANIMATIONS,
            getSettings().theme,
            mDestinationIntent
        ))
    }


    override fun finishActivity() {
        finish()
    }


    override fun getContentLayoutResourceId(): Int = R.layout.login_activity_layout


    override fun getEnterTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.DEFAULT_ANIMATIONS
    }


    override fun getExitTransitionAnimations(): TransitionAnimations {
        return mTransitionAnimations
    }


    override fun shouldSetStatusBarColor(): Boolean = false


    override fun onResume() {
        super.onResume()

        qrCodeScanner.start()
    }


    override fun onPause() {
        super.onPause()

        qrCodeScanner.stop()
    }


    private val mCredentialsInputDialogListener: CredentialsInputDialog.Listener = object : CredentialsInputDialog.Listener {

        override fun onCredentialsEntered(publicKey: String, secretKey: String) {
            mPresenter?.onCredentialsManuallyEntered(publicKey, secretKey)
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(isPermissionSetGranted(grantResults)) {
            if(requestCode == Constants.REQUEST_CODE_CAMERA_PERMISSION) {
                qrCodeScanner.onCameraPermissionGranted()
            }
        } else {
            showToast(getString(R.string.error_permissions_not_granted))
        }
    }


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            with(savedState) {
                mTransitionAnimations = (getSerializable(SAVED_STATE_TRANSITION_ANIMATIONS) as TransitionAnimations)
                mDestinationIntent = getParcelable(SAVED_STATE_DESTINATION_INTENT)!!
            }
        } else {
            with(intent) {
                mTransitionAnimations = (getSerializableExtra(EXTRA_TRANSITION_ANIMATIONS) as TransitionAnimations)
                mDestinationIntent = (getParcelableExtra(EXTRA_DESTINATION_INTENT))

            }
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        with(savedState) {
            putSerializable(SAVED_STATE_TRANSITION_ANIMATIONS, mTransitionAnimations)
            putParcelable(SAVED_STATE_DESTINATION_INTENT, mDestinationIntent)
        }
    }


    override fun onRecycle(isChangingConfigurations: Boolean) {
        super.onRecycle(isChangingConfigurations)

        qrCodeScanner.release()

        mCredentialsInputDialog?.dismiss()
        mCredentialsInputDialog = null
    }


}
package com.stocksexchange.android.ui.login.new

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.EditText
import com.afollestad.materialdialogs.MaterialDialog
import com.stocksexchange.android.BuildConfig
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.newapi.SignInConfirmationTypes
import com.stocksexchange.android.model.LoginProcessPhases
import com.stocksexchange.android.model.PinCodeModes
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.auth.AuthenticationActivity
import com.stocksexchange.android.ui.base.activities.BaseActivity
import com.stocksexchange.android.ui.feedback.FeedbackActivity
import com.stocksexchange.android.ui.utils.extensions.makeGone
import com.stocksexchange.android.ui.utils.extensions.makeInvisible
import com.stocksexchange.android.ui.utils.extensions.makeVisible
import com.stocksexchange.android.ui.utils.extensions.overrideEnterTransition
import com.stocksexchange.android.ui.views.login.confirmationviews.base.BaseLoginConfirmationView
import com.stocksexchange.android.utils.managers.KeyboardManager
import com.stocksexchange.android.utils.providers.StringProvider
import kotlinx.android.synthetic.main.new_login_activity_layout.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import org.koin.android.ext.android.get

class LoginActivity : BaseActivity<LoginPresenter>(), LoginContract.View {


    companion object {

        const val TAG = "LoginActivity"

        private const val EXTRA_TRANSITION_ANIMATIONS = "transition_animations"
        private const val EXTRA_DESTINATION_INTENT = "destination_intent"

        private const val SAVED_STATE_PROCESS_PHASE = "process_phase"
        private const val SAVED_STATE_CONFIRMATION_TYPE = "confirmation_type"
        private const val SAVED_STATE_TRANSITION_ANIMATIONS = "transition_animations"
        private const val SAVED_STATE_DESTINATION_INTENT = "destination_intent"

        private const val KEYBOARD_HIDING_DELAY = 150L

        private const val CROSS_FADING_ANIMATION_DURATION = 150L
        private val CROSS_FADING_ANIMATION_INTERPOLATOR = LinearInterpolator()


        fun newInstance(context: Context, transitionAnimations: TransitionAnimations,
                        destinationIntent: Intent): Intent {
            return context.intentFor<LoginActivity>(
                EXTRA_TRANSITION_ANIMATIONS to transitionAnimations,
                EXTRA_DESTINATION_INTENT to destinationIntent
            ).clearTop().singleTop()
        }

    }


    private var mProcessPhase = LoginProcessPhases.AWAITING_CREDENTIALS

    private var mConfirmationType = SignInConfirmationTypes.UNKNOWN

    private lateinit var mKeyboardManager: KeyboardManager

    private lateinit var mTransitionAnimations: TransitionAnimations

    private lateinit var mDestinationIntent: Intent

    private val mHandler = Handler()

    private var mMainInputView: View? = null

    private var mMaterialDialog: MaterialDialog? = null

    private lateinit var mInputViews: Array<View>




    override fun preInit() {
        super.preInit()

        mKeyboardManager = KeyboardManager.newInstance(this)
    }


    override fun initPresenter(): LoginPresenter = LoginPresenter(this)


    override fun init() {
        super.init()

        initContentContainer()
    }


    private fun initContentContainer() {
        initTitle()
        initMotto()
        initMainInputView()
        initInputViews()
        initProgressBar()
        initMainButton()

        ThemingUtil.Login.contentContainer(contentContainerCl, getAppTheme())
    }


    @SuppressLint("SetTextI18n")
    private fun initTitle() {
        if(BuildConfig.DEBUG) {
            titleTv.text = "STEX"
        }

        ThemingUtil.Login.title(titleTv, getAppTheme())
    }


    private fun initMotto() {
        ThemingUtil.Login.motto(mottoTv, getAppTheme())
    }


    private fun initInputViews() {
        initCredentialsView()
        initConfirmationViews()

        mInputViews = arrayOf(
            credentialsView,
            emailConfirmationView,
            smsConfirmationView,
            twoFactorAuthConfirmationView
        )

        mInputViews.forEach {
            if(it.id != mMainInputView?.id) {
                it.makeGone()
            }
        }
    }


    private fun initCredentialsView() {
        with(credentialsView) {
            setOnHelpButtonClickListener {
                mPresenter?.onCredentialsViewHelpButtonClicked()
            }

            setOnRegisterButtonClickListener {
                mPresenter?.onCredentialsViewRegisterButtonClicked()
            }

            ThemingUtil.Login.credentialsView(this, getAppTheme())
        }
    }


    private fun initConfirmationViews() {
        val onCodeEnterListenerImpl = object : BaseLoginConfirmationView.OnCodeEnterListener {

            override fun onCodeEntered(editText: EditText, type: SignInConfirmationTypes, code: String) {
                mKeyboardManager.hideKeyboard(editText)

                mHandler.postDelayed(
                    { mPresenter?.onSecurityCodeReceived(type, code) },
                    KEYBOARD_HIDING_DELAY
                )
            }

        }

        emailConfirmationView.onCodeEnterListener = onCodeEnterListenerImpl
        smsConfirmationView.onCodeEnterListener = onCodeEnterListenerImpl

        with(twoFactorAuthConfirmationView) {
            onCodeEnterListener = onCodeEnterListenerImpl

            setOnHelpButtonClickListener { type, _ ->
                mPresenter?.onConfirmationViewHelpButtonClicked(type)
            }
        }

        with(ThemingUtil.Login) {
            val theme = getAppTheme()

            confirmationView(emailConfirmationView, theme)
            confirmationView(smsConfirmationView, theme)
            confirmationView(twoFactorAuthConfirmationView, theme)
        }
    }


    private fun initMainInputView() {
        mMainInputView = when(mProcessPhase) {
            LoginProcessPhases.AWAITING_CREDENTIALS -> credentialsView
            LoginProcessPhases.AWAITING_CONFIRMATION -> when(mConfirmationType) {
                SignInConfirmationTypes.EMAIL -> emailConfirmationView
                SignInConfirmationTypes.SMS -> smsConfirmationView
                SignInConfirmationTypes.TWO_FACTOR_AUTHENTICATION -> twoFactorAuthConfirmationView

                else -> null
            }
        }

        mMainInputView?.makeVisible()
    }


    private fun initProgressBar() {
        ThemingUtil.apply(progressBar, getAppTheme())
    }


    private fun initMainButton() {
        with(mainBtn) {
            setMainButtonText(get<StringProvider>().getMainButtonTextForLoginProcessPhase(
                mProcessPhase
            ))

            setOnClickListener {
                mPresenter?.onMainButtonClicked()
            }

            ThemingUtil.Login.button(this, getAppTheme())
        }
    }


    override fun postInit() {
        super.postInit()

        overrideEnterTransition(mTransitionAnimations)
    }


    override fun showErrorDialog(title: String, message: String) {
        val dialog = MaterialDialog.Builder(this)
            .title(title)
            .content(message)
            .positiveText(R.string.ok)
            .apply { ThemingUtil.Login.dialogBuilder(this, getAppTheme()) }
            .build()

        mMaterialDialog = dialog
        mMaterialDialog?.show()
    }


    override fun showTwoFactorAuthHelpDialog() {
        val dialog = MaterialDialog.Builder(this)
            .title(R.string.login_2fa_help_dialog_title)
            .content(R.string.login_2fa_help_dialog_message)
            .positiveText(R.string.login_2fa_help_dialog_positive_button_text)
            .negativeText(R.string.action_cancel)
            .onPositive { _, _ -> mPresenter?.onContactSupportButtonClicked() }
            .apply { ThemingUtil.Login.dialogBuilder(this, getAppTheme()) }
            .build()

        mMaterialDialog = dialog
        mMaterialDialog?.show()
    }


    override fun hideMaterialDialog() {
        mMaterialDialog?.dismiss()
        mMaterialDialog = null
    }


    override fun showProgressBar() {
        progressBar.makeVisible()
    }


    override fun hideProgressBar() {
        progressBar.makeInvisible()
    }


    override fun updateMainView() {
        if((mMainInputView == null) || (mConfirmationType == SignInConfirmationTypes.UNKNOWN)) {
            return
        }

        val viewToHide = mMainInputView!!
        val viewToShow = when(mConfirmationType) {
            SignInConfirmationTypes.EMAIL -> emailConfirmationView
            SignInConfirmationTypes.SMS -> smsConfirmationView
            SignInConfirmationTypes.TWO_FACTOR_AUTHENTICATION -> twoFactorAuthConfirmationView

            else -> mMainInputView
        }

        if((viewToShow == null) || (viewToHide.id == viewToShow.id)) {
            return
        }

        mMainInputView = viewToShow

        // Cross-fading the views
        viewToHide.animate()
            .alpha(0f)
            .setDuration(CROSS_FADING_ANIMATION_DURATION)
            .setListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator?) {
                    viewToHide.makeGone()
                    viewToHide.alpha = 1f

                    viewToShow.alpha = 0f
                    viewToShow.makeVisible()

                    viewToShow.animate()
                        .alpha(1f)
                        .setDuration(CROSS_FADING_ANIMATION_DURATION)
                        .setInterpolator(CROSS_FADING_ANIMATION_INTERPOLATOR)
                        .start()
                }

            })
            .setInterpolator(CROSS_FADING_ANIMATION_INTERPOLATOR)
            .start()
    }


    override fun launchSupportActivity() {
        startActivity(FeedbackActivity.newSupportInstance(this))
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


    override fun setProcessPhase(phase: LoginProcessPhases) {
        mProcessPhase = phase
    }


    override fun setConfirmationType(confirmationType: SignInConfirmationTypes) {
        mConfirmationType = confirmationType
    }


    override fun setMainButtonText(text: String) {
        mainBtn.text = text
    }


    override fun getEmail(): String = credentialsView.getEmail()


    override fun getPassword(): String = credentialsView.getPassword()


    override fun getCode(): String = when(mConfirmationType) {
        SignInConfirmationTypes.EMAIL -> emailConfirmationView.getCode()
        SignInConfirmationTypes.SMS -> smsConfirmationView.getCode()
        SignInConfirmationTypes.TWO_FACTOR_AUTHENTICATION -> twoFactorAuthConfirmationView.getCode()
        SignInConfirmationTypes.UNKNOWN -> ""
    }


    override fun getProcessPhase(): LoginProcessPhases = mProcessPhase


    override fun getConfirmationType(): SignInConfirmationTypes = mConfirmationType


    override fun getContentLayoutResourceId(): Int = R.layout.new_login_activity_layout


    override fun getEnterTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.DEFAULT_ANIMATIONS
    }


    override fun getExitTransitionAnimations(): TransitionAnimations {
        return mTransitionAnimations
    }


    override fun shouldSetStatusBarColor(): Boolean = false


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            with(savedState) {
                mProcessPhase = (getSerializable(SAVED_STATE_PROCESS_PHASE) as LoginProcessPhases)
                mConfirmationType = (getSerializable(SAVED_STATE_CONFIRMATION_TYPE) as SignInConfirmationTypes)
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
            putSerializable(SAVED_STATE_PROCESS_PHASE, mProcessPhase)
            putSerializable(SAVED_STATE_CONFIRMATION_TYPE, mConfirmationType)
            putSerializable(SAVED_STATE_TRANSITION_ANIMATIONS, mTransitionAnimations)
            putParcelable(SAVED_STATE_DESTINATION_INTENT, mDestinationIntent)
        }
    }


    override fun onRecycle(isChangingConfigurations: Boolean) {
        super.onRecycle(isChangingConfigurations)

        mKeyboardManager.recycle()
    }


}
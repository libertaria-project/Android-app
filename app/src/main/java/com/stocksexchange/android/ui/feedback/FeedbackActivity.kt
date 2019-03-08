package com.stocksexchange.android.ui.feedback

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.stocksexchange.android.Constants
import com.stocksexchange.android.R
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.ui.base.activities.BaseActivity
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.utils.listeners.QueryListener
import com.stocksexchange.android.utils.handlers.EmailHandler
import com.stocksexchange.android.utils.managers.KeyboardManager
import kotlinx.android.synthetic.main.feedback_activity_footer_layout.*
import kotlinx.android.synthetic.main.feedback_activity_layout.*
import org.jetbrains.anko.intentFor
import org.koin.android.ext.android.get

class FeedbackActivity : BaseActivity<FeedbackPresenter>(), FeedbackContract.View,
    QueryListener.Callback{


    companion object {

        private const val EXTRA_RECIPIENT_EMAIL_ADDRESS = "recipient_email_address"

        private const val SAVED_STATE_RECIPIENT_EMAIL_ADDRESS = "recipient_email_address"


        fun newFeedbackInstance(context: Context): Intent = newInstance(
            context,
            Constants.FEEDBACK_EMAIL_ADDRESS
        )


        fun newSupportInstance(context: Context): Intent = newInstance(
            context,
            Constants.SUPPORT_EMAIL_ADDRESS
        )


        fun newInstance(context: Context, recipientEmailAddress: String): Intent {
            return context.intentFor<FeedbackActivity>(
                EXTRA_RECIPIENT_EMAIL_ADDRESS to recipientEmailAddress
            )
        }

    }


    private var mRecipientEmailAddress: String = Constants.FEEDBACK_EMAIL_ADDRESS

    private lateinit var mKeyboardManager: KeyboardManager




    override fun preInit() {
        super.preInit()

        mKeyboardManager = KeyboardManager.newInstance(this)
    }


    override fun initPresenter(): FeedbackPresenter = FeedbackPresenter(this)


    override fun init() {
        super.init()

        initContentContainer()
        initToolbar()
        initHeader()
        initCardView()
    }


    private fun initContentContainer() {
        ThemingUtil.Feedback.contentContainer(contentContainerRl, getAppTheme())
    }


    private fun initToolbar() {
        toolbar.setOnLeftButtonClickListener {
            onBackPressed()
        }

        ThemingUtil.Feedback.toolbar(toolbar, getAppTheme())
    }


    private fun initHeader() {
        with(ThemingUtil.Feedback) {
            val theme = getAppTheme()

            greeting(greetingTv, theme)
            detailedMessage(detailedMessageTv, theme)
        }
    }


    private fun initCardView() {
        initTopBar()
        initSeparator()
        initMainScrollView()
        initInputEditText()

        ThemingUtil.Feedback.cardView(cardView, getAppTheme())
    }


    private fun initTopBar() {
        sendBtnTv.setOnClickListener {
            mPresenter?.onSendButtonClicked()
        }
        disableSendButton()

        with(getAppTheme()) {
            ThemingUtil.Feedback.feedbackTitle(newFeedbackTitleTv, this)
            ThemingUtil.Feedback.button(sendBtnTv, this)
        }
    }


    private fun initSeparator() {
        ThemingUtil.Feedback.dottedSeparator(separatorIv, getAppTheme())
    }


    private fun initMainScrollView() {
        scrollView.interceptableClickListener = View.OnClickListener {
            mPresenter?.onContentContainerClicked()
        }
    }


    private fun initInputEditText() {
        feedbackTextEt.addTextChangedListener(QueryListener(this))

        ThemingUtil.Feedback.editText(feedbackTextEt, getAppTheme())
    }


    override fun showKeyboard() {
        feedbackTextEt.requestFocus()
        mKeyboardManager.showKeyboard(feedbackTextEt)
    }


    override fun enableSendButton() {
        sendBtnTv.enable(true)
    }


    override fun disableSendButton() {
        sendBtnTv.disable(true)
    }


    override fun sendEmail(emailAddress: String, subject: String, text: String) {
        get<EmailHandler>().sendEmail(this, emailAddress, subject, text, Constants.REQUEST_CODE_NEW_EMAIL)
    }


    override fun getFeedbackText(): String {
        return feedbackTextEt.getContent()
    }


    override fun getRecipientEmailAddress(): String = mRecipientEmailAddress


    override fun getContentLayoutResourceId() = R.layout.feedback_activity_layout


    override fun getTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.HORIZONTAL_SLIDING_ANIMATIONS
    }


    override fun onQueryEntered(query: String) {
        mPresenter?.onQueryEntered(query)
    }


    override fun onQueryRemoved() {
        mPresenter?.onQueryRemoved()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if((requestCode == Constants.REQUEST_CODE_NEW_EMAIL) && (resultCode == Activity.RESULT_OK)) {
            finish()
        }
    }


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            mRecipientEmailAddress = savedState.getString(
                SAVED_STATE_RECIPIENT_EMAIL_ADDRESS,
                Constants.FEEDBACK_EMAIL_ADDRESS
            )
        } else {
            mRecipientEmailAddress = intent.getStringExtra(EXTRA_RECIPIENT_EMAIL_ADDRESS)
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        with(savedState) {
            putString(SAVED_STATE_RECIPIENT_EMAIL_ADDRESS, mRecipientEmailAddress)
        }
    }


    override fun onRecycle(isChangingConfigurations: Boolean) {
        super.onRecycle(isChangingConfigurations)

        mKeyboardManager.recycle()
    }


}
package com.stocksexchange.android.ui.views.login

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.stocksexchange.android.R
import com.stocksexchange.android.ui.utils.extensions.getContent
import com.stocksexchange.android.ui.views.login.base.BaseLoginInputView
import kotlinx.android.synthetic.main.login_credentials_view.view.*

/**
 * A login input view that enables the user to enter its
 * credentials (user and password).
 */
class LoginCredentialsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseLoginInputView(context, attrs, defStyleAttr) {


    override fun saveAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.LoginCredentialsView, defStyleAttr, 0).run {
            try {
                with(mAttributes) {
                    save(ATTRIBUTE_KEY_INPUT_VIEW_TEXT_COLOR, getColor(R.styleable.LoginCredentialsView_inputViewTextColor, DEFAULT_INPUT_VIEW_TEXT_COLOR))
                    save(ATTRIBUTE_KEY_INPUT_VIEW_HINT_TEXT_COLOR, getColor(R.styleable.LoginCredentialsView_inputViewHintTextColor, DEFAULT_INPUT_VIEW_HINT_TEXT_COLOR))
                    save(ATTRIBUTE_KEY_INPUT_VIEW_CURSOR_COLOR, getColor(R.styleable.LoginCredentialsView_inputViewCursorColor, DEFAULT_INPUT_VIEW_CURSOR_COLOR))
                    save(ATTRIBUTE_KEY_HELP_BUTTON_TEXT_COLOR, getColor(R.styleable.LoginCredentialsView_helpButtonTextColor, DEFAULT_HELP_BUTTON_TEXT_COLOR))
                }
            } finally {
                recycle()
            }
        }
    }


    /**
     * Sets a click listener of the help button.
     *
     * @param listener The listener to set
     */
    fun setOnHelpButtonClickListener(listener: ((View) -> Unit)) {
        helpBtnTv.setOnClickListener(listener)
    }


    /**
     * Sets a click listener of the register button.
     *
     * @param listener The listener to set
     */
    fun setOnRegisterButtonClickListener(listener: ((View) -> Unit)) {
        registerBtnTv.setOnClickListener(listener)
    }


    override fun hasHint(): Boolean = false


    override fun hasHelpButtons(): Boolean = true


    override fun getLayoutResourceId(): Int = R.layout.login_credentials_view


    /**
     * Retrieves an email from the input view.
     *
     * @return The email string
     */
    fun getEmail(): String = emailEt.getContent()


    /**
     * Retrieves a password from the input view.
     *
     * @return The password string
     */
    fun getPassword(): String = passwordEt.getContent()


    override fun getHelpButtonsArray(): Array<TextView> = arrayOf(helpBtnTv, registerBtnTv)


    override fun getInputViewsArray(): Array<EditText> = arrayOf(emailEt, passwordEt)


}
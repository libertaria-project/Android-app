package com.stocksexchange.android.ui.views.login.confirmationviews.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.newapi.SignInConfirmationTypes
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.utils.listeners.QueryListener
import com.stocksexchange.android.ui.views.login.base.BaseLoginInputView
import kotlinx.android.synthetic.main.login_confirmation_view.view.*

/**
 * A base login input view for confirmation views.
 */
abstract class BaseLoginConfirmationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseLoginInputView(context, attrs, defStyleAttr) {


    /**
     * A listener to invoke when the code has been provided into
     * the input view.
     */
    var onCodeEnterListener: OnCodeEnterListener? = null




    override fun saveAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.LoginConfirmationView, defStyleAttr, 0).run {
            try {
                with(mAttributes) {
                    save(ATTRIBUTE_KEY_HINT_TEXT_COLOR, getColor(R.styleable.LoginConfirmationView_hintTextColor, DEFAULT_HINT_TEXT_COLOR))
                    save(ATTRIBUTE_KEY_HELP_BUTTON_TEXT_COLOR, getColor(R.styleable.LoginConfirmationView_helpButtonTextColor, DEFAULT_HELP_BUTTON_TEXT_COLOR))
                    save(ATTRIBUTE_KEY_INPUT_VIEW_TEXT_COLOR, getColor(R.styleable.LoginConfirmationView_inputViewTextColor, DEFAULT_INPUT_VIEW_TEXT_COLOR))
                    save(ATTRIBUTE_KEY_INPUT_VIEW_HINT_TEXT_COLOR, getColor(R.styleable.LoginConfirmationView_inputViewHintTextColor, DEFAULT_INPUT_VIEW_HINT_TEXT_COLOR))
                    save(ATTRIBUTE_KEY_INPUT_VIEW_CURSOR_COLOR, getColor(R.styleable.LoginConfirmationView_inputViewCursorColor, DEFAULT_INPUT_VIEW_CURSOR_COLOR))
                }
            } finally {
                recycle()
            }
        }
    }


    override fun init() {
        super.init()

        codeEt.addTextChangedListener(QueryListener(object : QueryListener.Callback {

            override fun onQueryEntered(query: String) {
                if(query.isBlank() || (query.length != getCodeLength())) {
                    return
                }

                onCodeEnterListener?.onCodeEntered(codeEt, getConfirmationType(), query)
            }

        }))

        setHintText(getHintText())
        setInputViewType(getInputViewType())

        if(hasHelpButtons()) {
            helpBtnTv.makeVisible()

            setHelpButtonText(getHelpButtonText())
        } else {
            helpBtnTv.makeGone()
        }
    }


    /**
     * Sets an input type of the input view.
     *
     * @param type The type to set
     */
    fun setInputViewType(type: Int) {
        codeEt.inputType = type
    }


    /**
     * Sets a text of the hint.
     *
     * @param text The text to set
     */
    fun setHintText(text: String) {
        hintTv.text = text
    }


    /**
     * Sets a text of the help button.
     *
     * @param text The text to set
     */
    fun setHelpButtonText(text: String) {
        helpBtnTv.text = text
    }


    /**
     * Sets a click listener of the help button.
     *
     * @param listener The listener to set
     */
    fun setOnHelpButtonClickListener(listener: ((SignInConfirmationTypes, View) -> Unit)) {
        helpBtnTv.setOnClickListener {
            listener(getConfirmationType(), it)
        }
    }


    override fun hasHint(): Boolean = true


    override fun getLayoutResourceId(): Int = R.layout.login_confirmation_view


    /**
     * Should return a length of the code that is expected by
     * the input view.
     */
    protected abstract fun getCodeLength(): Int


    /**
     * Should return an input type for the input view.
     */
    protected abstract fun getInputViewType(): Int


    /**
     * Should return a type of the confirmation.
     */
    protected abstract fun getConfirmationType(): SignInConfirmationTypes


    /**
     * Should return a text of the hint.
     */
    protected abstract fun getHintText(): String


    /**
     * Should return a text of the help button if the confirmation
     * view has one.
     */
    protected open fun getHelpButtonText(): String {
        return ""
    }


    /**
     * Retrieves a code from the input view.
     *
     * @return The code string
     */
    fun getCode(): String = codeEt.getContent()


    override fun getHintTv(): TextView? = hintTv


    override fun getHelpButtonsArray(): Array<TextView> = arrayOf(helpBtnTv)


    override fun getInputViewsArray(): Array<EditText> = arrayOf(codeEt)


    interface OnCodeEnterListener {

        fun onCodeEntered(editText: EditText, type: SignInConfirmationTypes, code: String)

    }


}
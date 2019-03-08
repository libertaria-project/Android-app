package com.stocksexchange.android.ui.views.login.confirmationviews

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.newapi.SignInConfirmationTypes
import com.stocksexchange.android.ui.views.login.confirmationviews.base.BaseLoginConfirmationView

/**
 * A login confirmation view that works with a two-factor
 * authentication code.
 */
class Login2FaConfirmationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseLoginConfirmationView(context, attrs, defStyleAttr) {


    override fun hasHelpButtons(): Boolean = true


    override fun getCodeLength(): Int = 6


    override fun getInputViewType(): Int {
        return (InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL)
    }


    override fun getHintText(): String {
        return context.getString(R.string.login_confirmation_2fa_hint)
    }


    override fun getHelpButtonText(): String {
        return context.getString(R.string.login_confirmation_2fa_help_button_text)
    }


    override fun getConfirmationType(): SignInConfirmationTypes {
        return SignInConfirmationTypes.TWO_FACTOR_AUTHENTICATION
    }


}
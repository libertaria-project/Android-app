package com.stocksexchange.android.ui.views.login.confirmationviews

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.newapi.SignInConfirmationTypes
import com.stocksexchange.android.ui.views.login.confirmationviews.base.BaseLoginConfirmationView

/**
 * A login confirmation view that works with an SMS code.
 */
class LoginSmsConfirmationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseLoginConfirmationView(context, attrs, defStyleAttr) {


    override fun hasHelpButtons(): Boolean = false


    override fun getCodeLength(): Int = 4


    override fun getInputViewType(): Int {
        return (InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL)
    }


    override fun getHintText(): String {
        return context.getString(R.string.login_confirmation_sms_hint)
    }


    override fun getConfirmationType(): SignInConfirmationTypes {
        return SignInConfirmationTypes.SMS
    }


}
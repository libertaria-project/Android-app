package com.stocksexchange.android.ui.login.new

import com.stocksexchange.android.api.model.newapi.SignInConfirmationTypes
import com.stocksexchange.android.model.LoginProcessPhases

interface LoginContract {


    interface View {

        fun showToast(message: String)

        fun showErrorDialog(title: String, message: String)

        fun showTwoFactorAuthHelpDialog()

        fun hideMaterialDialog()

        fun showProgressBar()

        fun hideProgressBar()

        fun updateMainView()

        fun launchSupportActivity()

        fun launchDestinationActivity()

        fun finishActivity()

        fun setProcessPhase(phase: LoginProcessPhases)

        fun setConfirmationType(confirmationType: SignInConfirmationTypes)

        fun setMainButtonText(text: String)

        fun getEmail(): String

        fun getPassword(): String

        fun getCode(): String

        fun getProcessPhase(): LoginProcessPhases

        fun getConfirmationType(): SignInConfirmationTypes

    }


    interface ActionListener {

        fun onCredentialsViewHelpButtonClicked()

        fun onCredentialsViewRegisterButtonClicked()

        fun onConfirmationViewHelpButtonClicked(type: SignInConfirmationTypes)

        fun onContactSupportButtonClicked()

        fun onSecurityCodeReceived(type: SignInConfirmationTypes, code: String)

        fun onMainButtonClicked()

    }


}
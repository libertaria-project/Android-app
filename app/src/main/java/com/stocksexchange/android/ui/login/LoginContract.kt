package com.stocksexchange.android.ui.login

import com.stocksexchange.android.api.model.User

interface LoginContract {


    interface View {

        fun showToast(message: String)

        fun showInvalidCredentialsDialog(content: String)

        fun hideInvalidCredentialsDialog()

        fun showCredentialsInputDialog()

        fun showProgress()

        fun hideProgress()

        fun enableQrCodeScanner()

        fun disableQrCodeScanner()

        fun enableSignInProblemsButton()

        fun disableSignInProblemsButton()

        fun updateUser(user: User)

        fun launchDestinationActivity()

        fun finishActivity()

    }


    interface ActionListener {

        fun onQrCodeScanned(qrCode: String)

        fun onSignInProblemsButtonClicked()

        fun onCredentialsManuallyEntered(publicKey: String, secretKey: String)

    }


}

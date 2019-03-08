package com.stocksexchange.android.ui.splash

import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.model.PinCodeModes
import com.stocksexchange.android.model.Settings
import java.util.Locale

interface SplashContract {


    interface View {

        fun installSecurityProvider()

        fun updateUser(user: User)

        fun updateSettings(settings: Settings)

        fun launchAuthenticationActivity(pinCodeMode: PinCodeModes)

        fun launchDestinationIntent()

        fun finishActivity()

        fun shouldAuthenticate(): Boolean

        fun getLocale(): Locale

    }


    interface ActionListener {

        fun onSecurityProviderInstalled()

        fun onUserAuthenticated()

    }


}
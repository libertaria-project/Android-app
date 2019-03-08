package com.stocksexchange.android.ui.splash

import com.stocksexchange.android.model.PinCodeModes
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.ui.base.mvp.presenters.BasePresenter
import com.stocksexchange.android.utils.SavedState
import com.stocksexchange.android.utils.formatters.DoubleFormatter
import com.stocksexchange.android.utils.handlers.ShortcutsHandler
import com.stocksexchange.android.utils.helpers.tag
import org.koin.standalone.inject
import java.util.Locale

class SplashPresenter(
    model: SplashModel,
    view: SplashContract.View
) : BasePresenter<SplashModel, SplashContract.View>(model, view), SplashContract.ActionListener {


    companion object {

        private val CLASS = SplashPresenter::class.java

        private val SAVED_STATE_IS_SECURITY_PROVIDER_INSTALLED = tag(CLASS, "is_security_provider_installed")

    }


    private var mIsSecurityProviderInstalled: Boolean = false
    private var mIsSecurityProviderInstalling: Boolean = false

    private val mShortcutsHandler: ShortcutsHandler by inject()




    constructor(view: SplashContract.View): this(SplashModel(), view)


    override fun start() {
        super.start()

        if(!mIsSecurityProviderInstalled && !mIsSecurityProviderInstalling) {
            mIsSecurityProviderInstalling = true

            mView.installSecurityProvider()
        }
    }


    override fun onSecurityProviderInstalled() {
        mIsSecurityProviderInstalled = true
        mIsSecurityProviderInstalling = false

        val locale = mView.getLocale()

        mModel.initSettings { settings ->
            mView.updateSettings(settings)
            mShortcutsHandler.setupShortcuts()
            initDoubleFormatter(settings, locale)

            if(mView.shouldAuthenticate()) {
                mView.launchAuthenticationActivity(if(settings.hasPinCode()) {
                    PinCodeModes.ENTER
                } else {
                    PinCodeModes.CREATION
                })
            } else {
                onUserAuthenticated()
            }
        }
    }


    private fun initDoubleFormatter(settings: Settings, locale: Locale) {
        with(DoubleFormatter.getInstance(locale)) {
            setDecimalSeparator(settings.decimalSeparator.separator)
            setGroupingSeparator(settings.groupingSeparator.separator)
            setGroupingEnabled(settings.isGroupingEnabled)
        }
    }


    override fun onUserAuthenticated() {
        mModel.updateUserIfNecessary {
            if(it != null) {
                mView.updateUser(it)
            }

            mView.launchDestinationIntent()
            mView.finishActivity()
        }
    }


    override fun onRestoreState(savedState: SavedState) {
        super.onRestoreState(savedState)

        with(savedState) {
            mIsSecurityProviderInstalled = get(SAVED_STATE_IS_SECURITY_PROVIDER_INSTALLED, false)
        }
    }


    override fun onSaveState(savedState: SavedState) {
        super.onSaveState(savedState)

        with(savedState) {
            save(SAVED_STATE_IS_SECURITY_PROVIDER_INSTALLED, mIsSecurityProviderInstalled)
        }
    }


}
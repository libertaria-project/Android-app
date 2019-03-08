package com.stocksexchange.android.ui.splash

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.security.ProviderInstaller
import com.google.android.gms.security.ProviderInstaller.ProviderInstallListener
import com.stocksexchange.android.AppController
import com.stocksexchange.android.Constants
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.model.PinCodeModes
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.theming.factories.ThemeFactory
import com.stocksexchange.android.ui.auth.AuthenticationActivity
import com.stocksexchange.android.ui.base.activities.BaseActivity
import com.stocksexchange.android.ui.currencymarkets.search.CurrencyMarketsSearchActivity
import com.stocksexchange.android.ui.dashboard.DashboardActivity
import com.stocksexchange.android.ui.favoritecurrencymarkets.FavoriteCurrencyMarketsActivity
import com.stocksexchange.android.ui.orders.OrdersActivity
import com.stocksexchange.android.ui.utils.extensions.ctx
import com.stocksexchange.android.ui.utils.extensions.getLocale
import com.stocksexchange.android.ui.wallets.WalletsActivity
import org.jetbrains.anko.intentFor
import org.koin.android.ext.android.get
import java.util.*

class SplashActivity : BaseActivity<SplashPresenter>(), SplashContract.View {


    companion object {

        private const val EXTRA_DESTINATION_NAME = "destination_name"
        private const val EXTRA_DESTINATION_INTENT = "destination_intent"

        private const val SAVED_STATE_DESTINATION_INTENT = "destination_intent"

        private const val DESTINATION_WALLETS = "wallets"
        private const val DESTINATION_ORDERS = "orders"
        private const val DESTINATION_FAVORITE_MARKETS = "favorite_markets"
        private const val DESTINATION_MARKETS_SEARCH = "markets_search"


        fun newInstanceWithWalletsDestination(context: Context): Intent {
            return newInstance(context, DESTINATION_WALLETS)
        }


        fun newInstanceWithOrdersDestination(context: Context): Intent {
            return newInstance(context, DESTINATION_ORDERS)
        }


        fun newInstanceWithFavoriteMarketsDestination(context: Context): Intent {
            return newInstance(context, DESTINATION_FAVORITE_MARKETS)
        }


        fun newInstanceWithMarketsSearchDestination(context: Context): Intent {
            return newInstance(context, DESTINATION_MARKETS_SEARCH)
        }


        fun newInstance(context: Context, destinationName: String): Intent {
            return context.intentFor<SplashActivity>(
                EXTRA_DESTINATION_NAME to destinationName
            )
        }


        fun newInstance(context: Context, destinationIntent: Intent): Intent {
            return context.intentFor<SplashActivity>(
                EXTRA_DESTINATION_INTENT to destinationIntent
            )
        }

    }


    private var mDestinationIntent: Intent? = null




    override fun initPresenter(): SplashPresenter = SplashPresenter(this)


    override fun getContentLayoutResourceId(): Int = R.layout.splash_activity_layout


    override fun shouldSetStatusBarColor(): Boolean = false


    override fun shouldSetRecentAppsToolbarColor(): Boolean = false


    override fun installSecurityProvider() {
        ProviderInstaller.installIfNeededAsync(this, providerInstallListener)
    }


    override fun updateUser(user: User) {
        AppController.INSTANCE.setUser(user)
    }


    override fun updateSettings(settings: Settings) {
        AppController.INSTANCE.setSettings(settings)
    }


    override fun launchDestinationIntent() {
        startActivity(mDestinationIntent)
    }


    override fun launchAuthenticationActivity(pinCodeMode: PinCodeModes) {
        startActivityForResult(AuthenticationActivity.newInstance(
            this,
            pinCodeMode,
            TransitionAnimations.OVERSHOOT_SCALING_ANIMATIONS,
            get<ThemeFactory>().getDefaultTheme()
        ), Constants.REQUEST_CODE_AUTHENTICATION_ACTIVITY)
    }


    override fun finishActivity() {
        finish()
    }


    override fun shouldAuthenticate(): Boolean {
        val appLockManager = AppController.INSTANCE.getAppLockManager()

        return ((appLockManager?.shouldAuthenticate() ?: true)
                || ((appLockManager?.isUserPresent() ?: false) && getSettings().isForceAuthenticationOnAppStartupEnabled))
    }


    override fun shouldUpdateLastInteractionTime(): Boolean = false


    override fun getLocale(): Locale {
        return ctx.getLocale()
    }


    private fun onSecurityProviderInstallerNotAvailable() {
        showLongToast(getString(R.string.error_security_provider))
        finish()
    }


    private val providerInstallListener: ProviderInstallListener = object : ProviderInstallListener {

        /**
         * This method is only called if the provider is successfully updated
         * (or is already up-to-date).
         */
        override fun onProviderInstalled() {
            mPresenter?.onSecurityProviderInstalled()
        }

        /**
         * This method is called if updating fails; the error code indicates
         * whether the error is recoverable.
         */
        override fun onProviderInstallFailed(errorCode: Int, recoveryIntent: Intent?) {
            if(GooglePlayServicesUtil.isUserRecoverableError(errorCode)) {
                // Recoverable error. Show a dialog prompting the user to
                // install/update/enable Google Play services
                GooglePlayServicesUtil.showErrorDialogFragment(
                    errorCode,
                    this@SplashActivity,
                    Constants.REQUEST_CODE_SECURITY_PROVIDER,
                    { onSecurityProviderInstallerNotAvailable() }
                )
            } else {
                // Google Play services is not available
                onSecurityProviderInstallerNotAvailable()
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != Activity.RESULT_OK) {
            return
        }

        when {
            (requestCode == Constants.REQUEST_CODE_AUTHENTICATION_ACTIVITY) -> {
                mPresenter?.onUserAuthenticated()
            }
        }
    }


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            mDestinationIntent = savedState.getParcelable(SAVED_STATE_DESTINATION_INTENT)
        } else {
            mDestinationIntent = if(intent.hasExtra(EXTRA_DESTINATION_NAME)) {
                when(intent.getStringExtra(EXTRA_DESTINATION_NAME)) {
                    DESTINATION_WALLETS -> WalletsActivity.newInstance(this, TransitionAnimations.KITKAT_SCALING_ANIMATIONS)
                    DESTINATION_ORDERS -> OrdersActivity.newInstance(this, TransitionAnimations.KITKAT_SCALING_ANIMATIONS)
                    DESTINATION_FAVORITE_MARKETS -> FavoriteCurrencyMarketsActivity.newInstance(this)
                    DESTINATION_MARKETS_SEARCH -> CurrencyMarketsSearchActivity.newInstance(this)

                    else -> throw IllegalStateException()
                }
            } else {
                if(intent.hasExtra(EXTRA_DESTINATION_INTENT)) {
                    intent.getParcelableExtra(EXTRA_DESTINATION_INTENT)
                } else {
                    DashboardActivity.newInstance(this)
                }
            }
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        savedState.putParcelable(SAVED_STATE_DESTINATION_INTENT, mDestinationIntent)
    }


}
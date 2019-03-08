package com.stocksexchange.android.ui.deeplinkhandler

import android.os.Bundle
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.ui.base.activities.BaseActivity
import com.stocksexchange.android.ui.currencymarketpreview.CurrencyMarketPreviewActivity
import com.stocksexchange.android.ui.splash.SplashActivity
import com.stocksexchange.android.ui.utils.extensions.isNetworkAvailable

class DeepLinkHandlerActivity : BaseActivity<DeepLinkHandlerPresenter>(), DeepLinkHandlerContract.View {


    companion object {

        const val TAG = "DeepLinkHandlerActivity"

        private const val EXTRA_IS_APP_INITIALIZED = "is_app_initialized"

    }




    override fun preViewInflation(): Boolean {
        return if(!isNetworkAvailable()) {
            showToast(getString(R.string.error_check_network_connection))
            finishActivity()

            true
        } else if(!intent.getBooleanExtra(EXTRA_IS_APP_INITIALIZED, false)) {
            intent.putExtra(EXTRA_IS_APP_INITIALIZED, true)

            startActivity(SplashActivity.newInstance(this, intent))
            finishActivity()

            true
        } else {
            false
        }
    }


    override fun initPresenter(): DeepLinkHandlerPresenter = DeepLinkHandlerPresenter(this)


    override fun shouldUpdateLastInteractionTime(): Boolean = false


    override fun shouldSetStatusBarColor(): Boolean = false


    override fun shouldSetRecentAppsToolbarColor(): Boolean = false


    override fun canObserveNetworkStateChanges(): Boolean = false


    override fun getContentLayoutResourceId(): Int = R.layout.deep_link_handler_activity_layout


    override fun launchCurrencyMarketPreviewActivity(currencyMarket: CurrencyMarket) {
        startActivity(CurrencyMarketPreviewActivity.newInstance(this, TAG, currencyMarket))
    }


    override fun finishActivity() {
        finish()
    }


    override fun finishActivityWithError(error: String) {
        showToast(error)
        finishActivity()
    }


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState == null) {
            if((intent != null) && (intent.data != null)) {
                mPresenter?.onUriRetrieved(intent.data.toString())
            } else {
                finishActivityWithError(getString(R.string.error_invalid_deep_link))
            }
        }
    }


}
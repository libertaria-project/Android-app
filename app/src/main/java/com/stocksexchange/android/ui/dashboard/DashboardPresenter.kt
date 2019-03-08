package com.stocksexchange.android.ui.dashboard

import com.stocksexchange.android.Constants
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.events.SettingsEvent
import com.stocksexchange.android.events.UserEvent
import com.stocksexchange.android.ui.base.mvp.model.StubModel
import com.stocksexchange.android.ui.base.mvp.presenters.BaseViewPagerPresenter
import com.stocksexchange.android.utils.providers.BooleanProvider
import com.stocksexchange.android.utils.providers.StringProvider
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.standalone.inject

class DashboardPresenter(
    model: StubModel,
    view: DashboardContract.View
) : BaseViewPagerPresenter<StubModel, DashboardContract.View>(model, view), DashboardContract.ActionListener {


    private val mBooleanProvider: BooleanProvider by inject()
    private val mStringProvider: StringProvider by inject()




    constructor(view: DashboardContract.View): this(StubModel(), view)


    override fun onFavoritesButtonClicked() {
        mView.launchFavoriteCurrencyMarketsActivity()
    }


    override fun onSearchButtonClicked() {
        mView.launchSearchActivity()
    }


    override fun onSignInButtonClicked() {
        mView.launchSignInActivity()
    }


    override fun onDrawerHeaderClicked(user: User?) {
        if(user == null) {
            return
        }

        mView.launchSettingsActivity()
    }


    override fun onWalletsClicked() {
        mView.launchWalletsActivity()
    }


    override fun onOrdersClicked() {
        mView.launchOrdersActivity()
    }


    override fun onDepositsClicked() {
        mView.launchDepositsActivity()
    }


    override fun onWithdrawalsClicked() {
        mView.launchWithdrawalsActivity()
    }


    override fun onSettingsClicked() {
        mView.launchSettingsActivity()
    }


    override fun onAboutClicked() {
        mView.launchAboutActivity()
    }


    override fun onFeedbackClicked() {
        mView.launchFeedbackActivity()
    }


    override fun onHelpClicked() {
        mView.launchHelpActivity()
    }


    override fun onShareTheAppClicked() {
        mView.shareText(
            mStringProvider.getString(
                R.string.app_sharing_template,
                Constants.PLAY_STORE_APP_REFERENCE
            ),
            mStringProvider.getString(R.string.action_share_via)
        )
    }


    override fun onTabReselected(position: Int) {
        super.onTabReselected(position)

        mView.showAppBar(true)
    }


    override fun onBackPressed(): Boolean {
        return if(shouldCloseDrawer()) {
            mView.closeDrawer()
            true
        } else {
            false
        }
    }


    private fun shouldCloseDrawer(): Boolean {
        return ((!(mBooleanProvider.getBoolean(R.bool.isTablet) && mView.isLandscape())) && mView.isDrawerOpen())
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onEvent(event: SettingsEvent) {
        if(event.isOriginatedFrom(this) || event.isConsumed) {
            return
        }

        when(event.action) {

            SettingsEvent.Actions.THEME_CHANGED,
            SettingsEvent.Actions.DEFAULTS_RESTORED,
            SettingsEvent.Actions.GROUPING_STATE_CHANGED,
            SettingsEvent.Actions.GROUPING_SEPARATOR_CHANGED,
            SettingsEvent.Actions.DECIMAL_SEPARATOR_CHANGED -> {
                mView.restartActivity()
            }

        }

        event.consume()
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onEvent(event: UserEvent) {
        if(event.isOriginatedFrom(this) || event.isConsumed) {
            return
        }

        when(event.action) {

            UserEvent.Actions.SIGNED_IN, UserEvent.Actions.SIGNED_OUT -> {
                mView.restartActivity()
            }

        }

        event.consume()
    }


    override fun canReceiveEvents(): Boolean {
        return true
    }


}
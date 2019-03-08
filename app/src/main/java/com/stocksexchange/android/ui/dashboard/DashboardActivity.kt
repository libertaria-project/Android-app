package com.stocksexchange.android.ui.dashboard

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.stocksexchange.android.AppController
import com.stocksexchange.android.Constants
import com.stocksexchange.android.R
import com.stocksexchange.android.model.DashboardTabs
import com.stocksexchange.android.model.Option
import com.stocksexchange.android.model.Separator
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator.Companion.VOLUME_DESCENDING_COMPARATOR
import com.stocksexchange.android.ui.about.AboutActivity
import com.stocksexchange.android.ui.base.activities.BaseViewPagerActivity
import com.stocksexchange.android.ui.currencymarkets.fragment.CurrencyMarketsFragment
import com.stocksexchange.android.ui.currencymarkets.search.CurrencyMarketsSearchActivity
import com.stocksexchange.android.ui.drawer.items.NavigationDrawerHeaderItem
import com.stocksexchange.android.ui.drawer.items.NavigationDrawerItem
import com.stocksexchange.android.ui.drawer.items.NavigationDrawerSeparatorItem
import com.stocksexchange.android.ui.favoritecurrencymarkets.FavoriteCurrencyMarketsActivity
import com.stocksexchange.android.ui.feedback.FeedbackActivity
import com.stocksexchange.android.ui.help.HelpActivity
import com.stocksexchange.android.ui.login.new.LoginActivity
import com.stocksexchange.android.ui.orders.OrdersActivity
import com.stocksexchange.android.ui.settings.SettingsActivity
import com.stocksexchange.android.ui.transactions.TransactionsActivity
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.views.Toolbar
import com.stocksexchange.android.ui.wallets.WalletsActivity
import com.stocksexchange.android.utils.handlers.SharingHandler
import kotlinx.android.synthetic.main.dashboard_activity_content_layout.*
import kotlinx.android.synthetic.main.dashboard_activity_layout.*
import org.jetbrains.anko.configuration
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.landscape
import org.koin.android.ext.android.get

class DashboardActivity : BaseViewPagerActivity<DashboardViewPagerAdapter, DashboardPresenter>(),
    DashboardContract.View {


    companion object {

        const val TAG = "DashboardActivity"

        private const val SAVED_STATE_COMPARATOR = "comparator"

        private const val NAVIGATION_DRAWER_ITEM_ID_WALLETS = 0
        private const val NAVIGATION_DRAWER_ITEM_ID_ORDERS = 1
        private const val NAVIGATION_DRAWER_ITEM_ID_DEPOSITS = 2
        private const val NAVIGATION_DRAWER_ITEM_ID_WITHDRAWALS = 3
        private const val NAVIGATION_DRAWER_SEPARATOR_ID_TOP = 4
        private const val NAVIGATION_DRAWER_ITEM_ID_SETTINGS = 5
        private const val NAVIGATION_DRAWER_ITEM_ID_ABOUT = 6
        private const val NAVIGATION_DRAWER_ITEM_ID_FEEDBACK = 7
        private const val NAVIGATION_DRAWER_ITEM_ID_HELP = 8
        private const val NAVIGATION_DRAWER_SEPARATOR_ID_BOTTOM = 9
        private const val NAVIGATION_DRAWER_ITEM_SHARE_THE_APP = 10


        fun newInstance(context: Context): Intent {
            return context.intentFor<DashboardActivity>().clearTop()
        }

    }


    private var mIsRestarting: Boolean = false

    // todo to be extracted to the settings in the future
    private var mComparator: CurrencyMarketComparator = VOLUME_DESCENDING_COMPARATOR




    override fun initPresenter(): DashboardPresenter = DashboardPresenter(this)


    override fun init() {
        super.init()

        initAppBarLayout()
        initCurrencyMarketsSortPanel()
        initDrawerLayout()
        initNavigationDrawer()
    }


    override fun initToolbar() {
        setSupportActionBar(toolbar)

        favoritesBtnIv.setOnClickListener {
            mPresenter?.onFavoritesButtonClicked()
        }
        searchBtnIv.setOnClickListener {
            mPresenter?.onSearchButtonClicked()
        }

        with(ThemingUtil.Dashboard) {
            val theme = getAppTheme()

            toolbarBackground(toolbar, theme)
            toolbarTitle(titleTv, theme)
            toolbarIcon(favoritesBtnIv, theme)
            toolbarIcon(searchBtnIv, theme)
        }
    }


    override fun populateAdapter() {
        with(mAdapter) {
            addFragment(getFragment(DashboardTabs.BTC.ordinal) ?: CurrencyMarketsFragment.newBtcInstance())
            addFragment(getFragment(DashboardTabs.USDT.ordinal) ?: CurrencyMarketsFragment.newUsdtInstance())
            addFragment(getFragment(DashboardTabs.NXT.ordinal) ?: CurrencyMarketsFragment.newNxtInstance())
            addFragment(getFragment(DashboardTabs.LTC.ordinal) ?: CurrencyMarketsFragment.newLtcInstance())
            addFragment(getFragment(DashboardTabs.ETH.ordinal) ?: CurrencyMarketsFragment.newEthInstance())
            addFragment(getFragment(DashboardTabs.USD.ordinal) ?: CurrencyMarketsFragment.newUsdInstance())
            addFragment(getFragment(DashboardTabs.EUR.ordinal) ?: CurrencyMarketsFragment.newEurInstance())
            addFragment(getFragment(DashboardTabs.JPY.ordinal) ?: CurrencyMarketsFragment.newJpyInstance())
            addFragment(getFragment(DashboardTabs.RUB.ordinal) ?: CurrencyMarketsFragment.newRubInstance())
        }
    }


    override fun initTabLayoutTabs() {
        with(mTabAnimator) {
            getTabAt(DashboardTabs.BTC.ordinal)?.setTitleText(DashboardTabs.BTC.name)
            getTabAt(DashboardTabs.USDT.ordinal)?.setTitleText(DashboardTabs.USDT.name)
            getTabAt(DashboardTabs.NXT.ordinal)?.setTitleText(DashboardTabs.NXT.name)
            getTabAt(DashboardTabs.LTC.ordinal)?.setTitleText(DashboardTabs.LTC.name)
            getTabAt(DashboardTabs.ETH.ordinal)?.setTitleText(DashboardTabs.ETH.name)
            getTabAt(DashboardTabs.USD.ordinal)?.setTitleText(DashboardTabs.USD.name)
            getTabAt(DashboardTabs.EUR.ordinal)?.setTitleText(DashboardTabs.EUR.name)
            getTabAt(DashboardTabs.JPY.ordinal)?.setTitleText(DashboardTabs.JPY.name)
            getTabAt(DashboardTabs.RUB.ordinal)?.setTitleText(DashboardTabs.RUB.name)

            applyTheme(getAppTheme())
        }
    }


    private fun initAppBarLayout() {
        ThemingUtil.Dashboard.appBarLayout(appBarLayout, getAppTheme())
    }


    private fun initCurrencyMarketsSortPanel() {
        with(currencyMarketsSortPanel) {
            mOnSortPanelTitleClickListener = {
                mAdapter.sort(it.comparator)
            }
            applyTheme(getAppTheme())
            setComparator(mComparator)

            ThemingUtil.Dashboard.sortPanel(this, getAppTheme())
        }
    }


    private fun initDrawerLayout() {
        val drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.dashboard_drawer_open,
            R.string.dashboard_drawer_close
        )

        if(resources.getBoolean(R.bool.isTablet) && isLandscape()) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN)
            drawerLayout.setScrimColor(Color.TRANSPARENT)
            drawerLayout.drawerElevation = 0f
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            drawerLayout.addDrawerListener(drawerToggle)
            drawerToggle.syncState()

            if(!Constants.AT_LEAST_LOLLIPOP) {
                drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START)
            }
        }

        with(ThemingUtil.Dashboard) {
            val theme = getAppTheme()

            drawerLayout(drawerLayout, theme)
            drawerToggle(drawerToggle, theme)
        }
    }


    private fun initNavigationDrawer() {
        with(navigationDrawer) {
            mIsAutocloseable = false
            setDrawerLayout(drawerLayout)
            setSettings(getSettings())

            onHeaderClickListener = { _, drawerHeaderItem, _ ->
                mPresenter?.onDrawerHeaderClicked((drawerHeaderItem as NavigationDrawerHeaderItem).itemModel)
            }
            onSignInButtonClickListener = { _, _, _ ->
                mPresenter?.onSignInButtonClicked()
            }
            onItemClickListener = { _, item, _ ->
                when(item.itemModel.id) {
                    NAVIGATION_DRAWER_ITEM_ID_WALLETS -> mPresenter?.onWalletsClicked()
                    NAVIGATION_DRAWER_ITEM_ID_ORDERS -> mPresenter?.onOrdersClicked()
                    NAVIGATION_DRAWER_ITEM_ID_DEPOSITS -> mPresenter?.onDepositsClicked()
                    NAVIGATION_DRAWER_ITEM_ID_WITHDRAWALS -> mPresenter?.onWithdrawalsClicked()
                    NAVIGATION_DRAWER_ITEM_ID_SETTINGS -> mPresenter?.onSettingsClicked()
                    NAVIGATION_DRAWER_ITEM_ID_ABOUT -> mPresenter?.onAboutClicked()
                    NAVIGATION_DRAWER_ITEM_ID_FEEDBACK -> mPresenter?.onFeedbackClicked()
                    NAVIGATION_DRAWER_ITEM_ID_HELP -> mPresenter?.onHelpClicked()
                    NAVIGATION_DRAWER_ITEM_SHARE_THE_APP -> mPresenter?.onShareTheAppClicked()
                }
            }

            populateNavigationDrawer()
            ThemingUtil.Dashboard.navigationDrawer(this, getAppTheme())
        }
    }


    private fun populateNavigationDrawer() {
        val user = AppController.INSTANCE.getUser()

        with(navigationDrawer) {
            addItem(NavigationDrawerHeaderItem(user))

            if(user != null) {
                addItem(NavigationDrawerItem(Option(
                    id = NAVIGATION_DRAWER_ITEM_ID_WALLETS,
                    iconDrawableId = R.drawable.ic_wallet,
                    title = getString(R.string.wallets)
                )))
                addItem(NavigationDrawerItem(Option(
                    id = NAVIGATION_DRAWER_ITEM_ID_ORDERS,
                    iconDrawableId = R.drawable.ic_swap_vertical,
                    title = getString(R.string.orders)
                )))
                addItem(NavigationDrawerItem(Option(
                    id = NAVIGATION_DRAWER_ITEM_ID_DEPOSITS,
                    iconDrawableId = R.drawable.ic_deposit,
                    title = getString(R.string.deposits)
                )))
                addItem(NavigationDrawerItem(Option(
                    id = NAVIGATION_DRAWER_ITEM_ID_WITHDRAWALS,
                    iconDrawableId = R.drawable.ic_withdrawal,
                    title = getString(R.string.withdrawals)
                )))

                addItem(NavigationDrawerSeparatorItem(Separator(
                    id = NAVIGATION_DRAWER_SEPARATOR_ID_TOP
                )))
            }

            addItem(NavigationDrawerItem(Option(
                id = NAVIGATION_DRAWER_ITEM_ID_SETTINGS,
                iconDrawableId = R.drawable.ic_settings,
                title = getString(R.string.settings)
            )))
            addItem(NavigationDrawerItem(Option(
                id = NAVIGATION_DRAWER_ITEM_ID_ABOUT,
                iconDrawableId = R.drawable.ic_information,
                title = getString(R.string.about)
            )))
            addItem(NavigationDrawerItem(Option(
                id = NAVIGATION_DRAWER_ITEM_ID_FEEDBACK,
                iconDrawableId = R.drawable.ic_announcement,
                title = getString(R.string.feedback)
            )))
            addItem(NavigationDrawerItem(Option(
                id = NAVIGATION_DRAWER_ITEM_ID_HELP,
                iconDrawableId = R.drawable.ic_help,
                title = getString(R.string.help)
            )))

            addItem(NavigationDrawerSeparatorItem(Separator(
                NAVIGATION_DRAWER_SEPARATOR_ID_BOTTOM
            )))

            addItem(NavigationDrawerItem(Option(
                id = NAVIGATION_DRAWER_ITEM_SHARE_THE_APP,
                iconDrawableId = R.drawable.ic_share,
                title = getString(R.string.share_the_app)
            )))
        }
    }


    override fun postInit() {
        super.postInit()

        AppController.INSTANCE.getSocketConnection()?.startListeningToCurrencyMarketsUpdates()
    }


    override fun showAppBar(animate: Boolean) {
        appBarLayout.setExpanded(true, animate)
    }


    override fun closeDrawer() {
        navigationDrawer.close()
    }


    override fun shareText(text: String, chooserTitle: String) {
        get<SharingHandler>().shareText(this, text, chooserTitle)
    }


    override fun restartActivity() {
        mIsRestarting = true

        finish()
        startActivity(DashboardActivity.newInstance(this))
    }


    override fun launchFavoriteCurrencyMarketsActivity() {
        startActivity(FavoriteCurrencyMarketsActivity.newInstance(this))
    }


    override fun launchSearchActivity() {
        startActivity(CurrencyMarketsSearchActivity.newInstance(this))
    }


    override fun launchSignInActivity() {
        startActivity(LoginActivity.newInstance(
            this,
            TransitionAnimations.FADING_ANIMATIONS,
            DashboardActivity.newInstance(this)
        ))
    }


    override fun launchWalletsActivity() {
        startActivity(WalletsActivity.newInstance(this, TransitionAnimations.HORIZONTAL_SLIDING_ANIMATIONS))
    }


    override fun launchOrdersActivity() {
        startActivity(OrdersActivity.newInstance(this, TransitionAnimations.HORIZONTAL_SLIDING_ANIMATIONS))
    }


    override fun launchDepositsActivity() {
        startActivity(TransactionsActivity.newDepositsInstance(this))
    }


    override fun launchWithdrawalsActivity() {
        startActivity(TransactionsActivity.newWithdrawalsInstance(this))
    }


    override fun launchSettingsActivity() {
        startActivity(SettingsActivity.newInstance(this))
    }


    override fun launchAboutActivity() {
        startActivity(AboutActivity.newInstance(this))
    }


    override fun launchHelpActivity() {
        startActivity(HelpActivity.newInstance(this))
    }


    override fun launchFeedbackActivity() {
        startActivity(FeedbackActivity.newFeedbackInstance(this))
    }


    override fun isDrawerOpen(): Boolean {
        return navigationDrawer.isOpen()
    }


    override fun isLandscape(): Boolean = configuration.landscape


    override fun getToolbarTitle(): String = getString(R.string.dashboard_toolbar_title)


    override fun getToolbar(): Toolbar? = null


    override fun getViewPager(): ViewPager = viewPager


    override fun getViewPagerAdapter(): DashboardViewPagerAdapter {
        return DashboardViewPagerAdapter(supportFragmentManager)
    }


    override fun getTabLayout(): TabLayout = tabLayout


    override fun getContentLayoutResourceId(): Int = R.layout.dashboard_activity_layout


    override fun shouldSetStatusBarColor(): Boolean = false


    override fun getEnterTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.FADING_ANIMATIONS
    }


    override fun getExitTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.DEFAULT_ANIMATIONS
    }


    override fun onBackPressed() {
        if(mPresenter?.onBackPressed() == true) {
            return
        }

        super.onBackPressed()
    }


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        savedState?.apply {
            mComparator = getParcelable(SAVED_STATE_COMPARATOR)
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        savedState.putParcelable(SAVED_STATE_COMPARATOR, currencyMarketsSortPanel.getComparator())
    }


    override fun onRecycle(isChangingConfigurations: Boolean) {
        super.onRecycle(isChangingConfigurations)

        if(!isChangingConfigurations && !mIsRestarting) {
            AppController.INSTANCE.getSocketConnection()?.stopListeningToCurrencyMarketsUpdates()
        }
    }


}
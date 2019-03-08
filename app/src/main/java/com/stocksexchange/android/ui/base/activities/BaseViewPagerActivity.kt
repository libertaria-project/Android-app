package com.stocksexchange.android.ui.base.activities

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.stocksexchange.android.ui.base.adapters.viewpager.BaseViewPagerAdapter
import com.stocksexchange.android.ui.base.mvp.presenters.BaseViewPagerPresenter
import com.stocksexchange.android.ui.base.mvp.views.ViewPagerView
import com.stocksexchange.android.ui.utils.CustomTabAnimator
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.interfaces.Scrollable
import com.stocksexchange.android.ui.utils.listeners.adapters.OnPageChangeListenerAdapter
import com.stocksexchange.android.ui.utils.listeners.adapters.OnTabSelectedListenerAdapter
import com.stocksexchange.android.ui.views.Toolbar

/**
 * A base activity to extend from if the activity hosts the
 * [ViewPager].
 */
abstract class BaseViewPagerActivity<VPA : BaseViewPagerAdapter, P : BaseViewPagerPresenter<*, *>> : BaseActivity<P>(),
    ViewPagerView {


    companion object {

        private const val SAVED_STATE_SELECTED_TAB = "selected_tab"

    }


    /**
     * An integer representing the currently selected tab position.
     */
    protected open var mSelectedTabPosition: Int = 0

    /**
     * An adapter to be used with a view pager.
     */
    protected lateinit var mAdapter: VPA

    /**
     * A custom wrapper around [TabLayout] used for animating tabs.
     */
    protected lateinit var mTabAnimator: CustomTabAnimator




    override fun init() {
        super.init()

        initToolbar()
        initViewPager()
        initTabLayout()
    }


    /**
     * Performs toolbar initialization.
     */
    protected open fun initToolbar() {
        val toolbar = getToolbar()

        if(toolbar != null) {
            toolbar.setOnLeftButtonClickListener {
                onBackPressed()
            }
            toolbar.setTitleText(getToolbarTitle())

            ThemingUtil.Main.toolbar(toolbar, getAppTheme())
        }
    }


    /**
     * Performs view pager initialization.
     */
    protected open fun initViewPager() {
        val viewPager = getViewPager()

        mAdapter = getViewPagerAdapter()
        mAdapter.viewPagerId = viewPager.id

        populateAdapter()

        viewPager.adapter = mAdapter
        viewPager.offscreenPageLimit = mAdapter.count
        viewPager.addOnPageChangeListener(object : OnPageChangeListenerAdapter {

            override fun onPageSelected(position: Int) {
                mPresenter?.onPageSelected(position)
            }

        })
    }


    /**
     * Should perform adapter population.
     */
    protected abstract fun populateAdapter()


    /**
     * Performs tab layout initialization.
     */
    protected open fun initTabLayout() {
        val tabLayout = getTabLayout()

        tabLayout.setupWithViewPager(getViewPager())
        tabLayout.getTabAt(getInitialTabPosition())?.select()
        tabLayout.addOnTabSelectedListener(mOnTabSelectedListener)

        mTabAnimator = CustomTabAnimator.newInstance(tabLayout)
        initTabLayoutTabs()

        ThemingUtil.TabBar.tabLayout(tabLayout, getAppTheme())
    }


    /**
     * Should perform [TabLayout] tabs initialization here.
     */
    protected abstract fun initTabLayoutTabs()


    override fun selectFragmentByTabClick(position: Int) {
        val fragment = mAdapter.getFragment(position) ?: return

        if(!fragment.isSelectedBySwipe() && !fragment.isSelected()) {
            fragment.setSelectedByTabClick(true)
        }
    }


    override fun selectFragmentBySwipe(position: Int) {
        val fragment = mAdapter.getFragment(position) ?: return

        if(!fragment.isSelectedByTabClick() && !fragment.isSelected()) {
            fragment.setSelectedBySwipe(true)
        }
    }


    override fun scrollFragmentToTop(position: Int) {
        (mAdapter.getFragment(position) as? Scrollable)?.scrollToTop()
    }


    override fun canObserveNetworkStateChanges(): Boolean {
        return true
    }


    /**
     * Returns a position of the initial selected tab.
     *
     * @return The tab position
     */
    protected open fun getInitialTabPosition(): Int {
        return mSelectedTabPosition
    }


    /**
     * Should return the title for the toolbar.
     */
    protected abstract fun getToolbarTitle(): String


    /**
     * Should return a reference to the Toolbar.
     */
    protected abstract fun getToolbar(): Toolbar?


    /**
     * Should return a reference to the ViewPager.
     */
    protected abstract fun getViewPager(): ViewPager


    /**
     * Should return an instance of [VPA] adapter.
     */
    protected abstract fun getViewPagerAdapter(): VPA


    /**
     * Should return a reference to the TabLayout.
     */
    protected abstract fun getTabLayout(): TabLayout


    override fun onConnected() {
        if(isInitialized()) {
            mAdapter.onNetworkConnected()
        }
    }


    override fun onDisconnected() {
        if(isInitialized()) {
            mAdapter.onNetworkDisconnected()
        }
    }


    /**
     * Gets called whenever a tab is selected.
     *
     * @param tab The tab selected
     */
    @CallSuper
    protected open fun onTabSelected(tab: TabLayout.Tab) {
        mPresenter?.onTabSelected(tab.position)
    }


    /**
     * Gets called whenever a tab is unselected.
     *
     * @param tab The tab unselected
     */
    @CallSuper
    protected open fun onTabUnselected(tab: TabLayout.Tab) {
        mPresenter?.onTabUnselected(tab.position)
    }


    /**
     * Gets called whenever a tab is reselected.
     *
     * @param tab The tab reselected
     */
    @CallSuper
    protected open fun onTabReselected(tab: TabLayout.Tab) {
        mPresenter?.onTabReselected(tab.position)
    }


    override fun onBackPressed() {
        super.onBackPressed()

        if(isInitialized()) {
            mAdapter.onBackPressed()
        }
    }


    private val mOnTabSelectedListener: OnTabSelectedListenerAdapter = object : OnTabSelectedListenerAdapter {

        override fun onTabSelected(tab: TabLayout.Tab) {
            this@BaseViewPagerActivity.onTabSelected(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            this@BaseViewPagerActivity.onTabUnselected(tab)
        }

        override fun onTabReselected(tab: TabLayout.Tab) {
            this@BaseViewPagerActivity.onTabReselected(tab)
        }

    }


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        savedState?.apply {
            mSelectedTabPosition = getInt(SAVED_STATE_SELECTED_TAB)
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        savedState.putInt(SAVED_STATE_SELECTED_TAB, getTabLayout().selectedTabPosition)
    }


}
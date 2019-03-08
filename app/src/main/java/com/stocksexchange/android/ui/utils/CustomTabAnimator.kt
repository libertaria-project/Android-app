package com.stocksexchange.android.ui.utils

import com.google.android.material.tabs.TabLayout
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.ui.utils.interfaces.Themable
import com.stocksexchange.android.ui.utils.listeners.adapters.OnTabSelectedListenerAdapter
import com.stocksexchange.android.ui.views.CustomTab

/**
 * A class used for animating tabs of [TabLayout].
 *
 * @see TabLayout
 */
class CustomTabAnimator private constructor (
    private val tabLayout: TabLayout
) : Themable<Theme> {


    companion object {

        /**
         * Returns an instance of the CustomTabAnimator.
         *
         * @param tabLayout The tab layout for the animator
         *
         * @return The new instance of the CustomTabAnimator
         */
        fun newInstance(tabLayout: TabLayout) = CustomTabAnimator(tabLayout)

    }




    init {
        initTabs()
        selectTab(tabLayout.selectedTabPosition, false)
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListenerAdapter {

            override fun onTabSelected(tab: TabLayout.Tab) {
                selectTab(tab.position, true)
            }

        })
    }


    private fun initTabs() {
        var customTab: CustomTab

        for (i in 0 until getTabCount()) {
            customTab = CustomTab(tabLayout.context)
            customTab.minimize(false)

            tabLayout.getTabAt(i)?.customView = customTab
        }
    }



    /**
     * Selects a tab at a specific symbolPosition through animation.
     *
     * The new selected tab will be maximized while the old selected tab
     * will be minimized.
     *
     * @param tabPosition The symbolPosition of the tab
     * @param animate Whether to animate the tab
     */
    fun selectTab(tabPosition: Int, animate: Boolean) {
        if(tabPosition !in 0..(tabLayout.tabCount - 1)) {
            return
        }

        var tab: CustomTab

        for (i in 0 until tabLayout.tabCount) {
            tab = (tabLayout.getTabAt(i)?.customView as CustomTab)

            if (i == tabPosition) {
                if (!tab.isMaximized()) {
                    tab.maximize(animate)
                }
            } else {
                if (!tab.isMinimized()) {
                    tab.minimize(animate)
                }
            }
        }
    }


    override fun applyTheme(theme: Theme) {
        for(i in 0 until getTabCount()) {
            val tab: CustomTab? = getTabAt(i)

            if(tab != null) {
                ThemingUtil.TabBar.tab(tab, theme)
            }
        }
    }


    /**
     * Returns the tab count;
     *
     * @return The tab count
     */
    fun getTabCount(): Int {
        return tabLayout.tabCount
    }


    /**
     * Gets a tab at a specified symbolPosition.
     *
     * @param tabPosition The symbolPosition of the tab
     *
     * @return The tab at the specified symbolPosition or null if there is not tab
     * at the specified symbolPosition
     *
     * @see CustomTab
     */
    fun getTabAt(tabPosition: Int): CustomTab? {
        return if (tabPosition >= 0 && tabPosition < tabLayout.tabCount) {
            tabLayout.getTabAt(tabPosition)?.customView as CustomTab
        } else {
            null
        }
    }


}

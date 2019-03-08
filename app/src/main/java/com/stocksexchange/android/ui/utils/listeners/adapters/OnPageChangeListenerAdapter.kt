package com.stocksexchange.android.ui.utils.listeners.adapters

import androidx.viewpager.widget.ViewPager

/**
 * An adapter for [ViewPager.OnPageChangeListener]. Primarily used for extending.
 */
interface OnPageChangeListenerAdapter : ViewPager.OnPageChangeListener {


    override fun onPageSelected(position: Int) {
        // Stub
    }


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        // Stub
    }


    override fun onPageScrollStateChanged(state: Int) {
        // Stub
    }


}
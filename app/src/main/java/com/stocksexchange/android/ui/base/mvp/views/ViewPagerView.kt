package com.stocksexchange.android.ui.base.mvp.views

/**
 * A base view pager view to build views on.
 */
interface ViewPagerView {


    /**
     * Selects fragment at the specified position by tab clicking.
     *
     * @param position The position of the fragment to select
     */
    fun selectFragmentByTabClick(position: Int)


    /**
     * Selects a fragment at the specified position by swiping.
     *
     * @param position The position of the fragment to select
     */
    fun selectFragmentBySwipe(position: Int)


    /**
     * Scrolls the fragment to the top position.
     *
     * @param position The position of the fragment to scroll to top
     */
    fun scrollFragmentToTop(position: Int)


}
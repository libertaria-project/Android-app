package com.stocksexchange.android.ui.base.mvp.presenters

import androidx.annotation.CallSuper
import com.stocksexchange.android.ui.base.mvp.model.Model
import com.stocksexchange.android.ui.base.mvp.views.ViewPagerView

/**
 * A base view pager presenter to build presenters on.
 */
abstract class BaseViewPagerPresenter<out M, out V>(
    model: M,
    view: V
) : BasePresenter<M, V>(model, view) where
        M : Model,
        V : ViewPagerView {


    /**
     * Gets called whenever a tab has been selected.
     *
     * @param position The position of the selected tab
     */
    @CallSuper
    open fun onTabSelected(position: Int) {
        mView.selectFragmentByTabClick(position)
    }


    /**
     * Gets called whenever a tab has been unselected.
     *
     * @param position The position of the unselected tab
     */
    open fun onTabUnselected(position: Int) {
        // Left for subclass implementations
    }


    /**
     * Gets called whenever a tab has been reselected.
     *
     * @param position The position of the reselected tab
     */
    @CallSuper
    open fun onTabReselected(position: Int) {
        mView.scrollFragmentToTop(position)
    }


    /**
     * Gets called whenever a page of the view pager has been selected.
     *
     * @param position The position of the selected tab
     */
    @CallSuper
    open fun onPageSelected(position: Int) {
        mView.selectFragmentBySwipe(position)
    }


}
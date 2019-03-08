package com.stocksexchange.android.ui.base.fragments

import android.graphics.drawable.Drawable
import androidx.annotation.CallSuper
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import com.stocksexchange.android.Constants
import com.stocksexchange.android.cache.ObjectCache
import com.stocksexchange.android.ui.base.mvp.presenters.BaseDataLoadingPresenter
import com.stocksexchange.android.ui.base.mvp.views.DataLoadingView
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.utils.interfaces.CanObserveNetworkStateChanges
import com.stocksexchange.android.ui.views.InfoView
import com.stocksexchange.android.utils.providers.InfoViewProvider
import org.koin.android.ext.android.get

/**
 * A base fragment class holding the common methods
 * for data loading.
 */
abstract class BaseDataLoadingFragment<P : BaseDataLoadingPresenter<*, *, Data, *>, Data : Any> : BaseFragment<P>(),
    DataLoadingView<Data>, CanObserveNetworkStateChanges {


    @CallSuper
    override fun init() {
        initProgressBar()
        initInfoView()
        initSwipeRefreshProgressBar()
    }


    /**
     * Performs progress bar initialization.
     */
    protected open fun initProgressBar() {
        val progressBar = getProgressBar()
        ThemingUtil.apply(progressBar, getAppTheme())

        adjustView(progressBar)
        hideProgressBar()
    }


    /**
     * Performs info view initialization.
     */
    protected open fun initInfoView() {
        val infoView = getInfoView()
        infoView.setIcon(getInfoViewIcon(get()))

        adjustView(infoView)
        hideInfoView()

        if(shouldColorInfoViewIcon()) {
            ThemingUtil.InfoView.icon(infoView.getIconIv(), getAppTheme())
        }

        ThemingUtil.InfoView.caption(infoView.getCaptionTv(), getAppTheme())
    }


    /**
     * Gets called with views that need adjustments.
     *
     * @param view The view to adjust
     */
    protected open fun adjustView(view: android.view.View) {
        // Override if needed
    }


    /**
     * Performs swipe refresh progress bar initialization.
     */
    protected open fun initSwipeRefreshProgressBar() {
        val swipeRefreshLayout = getRefreshProgressBar()

        swipeRefreshLayout.setOnRefreshListener {
            onRefreshData()
        }

        if(!isRefreshProgressBarUtilized()) {
            swipeRefreshLayout.disable()
        }

        ThemingUtil.apply(swipeRefreshLayout, getAppTheme())
    }


    override fun showMainView() {
        val mainView = getMainView()

        mainView.alpha = 0f
        mainView
            .animate()
            .alpha(1f)
            .setInterpolator(LinearInterpolator())
            .setDuration(Constants.MAIN_VIEW_ANIMATION_DURATION)
            .start()
    }


    override fun hideMainView() {
        val mainView = getMainView()

        if(mainView.alpha != 0f) {
            mainView.alpha = 0f
        }
    }


    override fun showEmptyView() {
        val infoView = getInfoView()
        infoView.setCaption(getEmptyViewCaption(get()))

        if(!infoView.isVisible()) {
            infoView.makeVisible()
        }
    }


    override fun showErrorView() {
        val infoView = getInfoView()
        infoView.setCaption(getErrorViewCaption(get()))

        if(!infoView.isVisible()) {
            infoView.makeVisible()
        }
    }


    override fun hideInfoView() {
        val infoView = getInfoView()

        if(!infoView.isGone()) {
            infoView.makeGone()
        }
    }


    override fun showProgressBar() {
        val progressBar = getProgressBar()

        if(!progressBar.isVisible()) {
            progressBar.makeVisible()
        }
    }


    override fun hideProgressBar() {
        val progressBar = getProgressBar()

        if(!progressBar.isGone()) {
            progressBar.makeGone()
        }
    }


    override fun showRefreshProgressBar() {
        if(!isRefreshProgressBarUtilized()) {
            return
        }

        val swipeRefreshLayout = getRefreshProgressBar()

        if(!swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = true
        }
    }


    override fun hideRefreshProgressBar() {
        if(!isRefreshProgressBarUtilized()) {
            return
        }

        val swipeRefreshLayout = getRefreshProgressBar()

        if(swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
    }


    override fun enableRefreshProgressBar() {
        if(!isRefreshProgressBarUtilized()) {
            return
        }

        getRefreshProgressBar().enable()
    }


    override fun disableRefreshProgressBar() {
        if(!isRefreshProgressBarUtilized()) {
            return
        }

        getRefreshProgressBar().disable()
    }


    override fun isViewSelected(): Boolean {
        return isSelected()
    }


    /**
     * Should return a flag indicating whether the data is able to be cached or not.
     */
    protected open fun isDataCacheable(): Boolean = true


    /**
     * Should return a flag indicating whether the refresh progress bar is utilized
     * in this particular fragment or not.
     */
    protected open fun isRefreshProgressBarUtilized(): Boolean = true


    /**
     * Should return a flag indicating whether the info view's icon should be
     * colored or not.
     */
    protected open fun shouldColorInfoViewIcon(): Boolean = true


    override fun shouldDelayUserVisibilityHint(isVisibleToUser: Boolean): Boolean {
        return (super.shouldDelayUserVisibilityHint(isVisibleToUser) && isDataSourceEmpty())
    }


    /**
     * Should return a cache key of the data.
     */
    protected open fun getDataCacheKey(): String = ""


    /**
     * Should return the drawable for the info view.
     */
    protected abstract fun getInfoViewIcon(infoViewProvider: InfoViewProvider): Drawable?


    /**
     * Should return the caption for the info view.
     */
    protected abstract fun getEmptyViewCaption(infoViewProvider: InfoViewProvider): String


    /**
     * Override this method to return a more specific error message.
     */
    open fun getErrorViewCaption(infoViewProvider: InfoViewProvider): String {
        return infoViewProvider.getDefaultErrorCaption()
    }


    /**
     * Should return a reference to the main view.
     */
    protected abstract fun getMainView(): android.view.View


    /**
     * Should return a reference to the info view.
     */
    protected abstract fun getInfoView(): InfoView


    /**
     * Should return a reference to the progress bar.
     */
    protected abstract fun getProgressBar(): ProgressBar


    /**
     * Should return a reference to the refresh progress bar.
     */
    protected abstract fun getRefreshProgressBar(): SwipeRefreshLayout


    override fun onStart() {
        super.onStart()

        // Clearing the cache of data since sometimes onSaveState
        // is called when its counterpart onRestoreState is not
        if(isDataCacheable() && ObjectCache.contains(getDataCacheKey())) {
            ObjectCache.remove(getDataCacheKey())
        }
    }


    override fun onNetworkConnected() {
        if(isInitialized()) {
            mPresenter?.onNetworkConnected()
        }
    }


    override fun onNetworkDisconnected() {
        if(isInitialized()) {
            mPresenter?.onNetworkDisconnected()
        }
    }


    override fun onSelected() {
        super.onSelected()

        if(isInitialized()) {
            mPresenter?.onViewSelected()
        }
    }


    /**
     * Gets called whenever a user pulled the refresh progress
     * bar to update the data.
     */
    open fun onRefreshData() {
        mPresenter?.onRefreshData()
    }


}
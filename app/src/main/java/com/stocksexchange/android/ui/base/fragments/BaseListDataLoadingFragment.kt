package com.stocksexchange.android.ui.base.fragments

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthurivanets.adapster.listeners.OnDatasetChangeListener
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.recyclerview.BaseRecyclerViewAdapter
import com.stocksexchange.android.Constants
import com.stocksexchange.android.R
import com.stocksexchange.android.cache.ObjectCache
import com.stocksexchange.android.ui.base.mvp.presenters.BaseListDataLoadingPresenter
import com.stocksexchange.android.ui.base.mvp.views.ListDataLoadingView
import com.stocksexchange.android.ui.utils.decorators.VerticalSpacingItemDecorator
import com.stocksexchange.android.ui.utils.extensions.ctx
import com.stocksexchange.android.ui.utils.extensions.dimenInPx
import com.stocksexchange.android.ui.utils.extensions.disableAnimations
import com.stocksexchange.android.ui.utils.interfaces.Searchable
import com.stocksexchange.android.ui.utils.listeners.RecyclerViewScrollListener
import com.stocksexchange.android.ui.utils.listeners.adapters.OnDatasetChangeListenerAdapter
import com.stocksexchange.android.ui.utils.listeners.adapters.RecyclerViewStateListenerAdapter

/**
 * A base fragment class holding the common methods
 * to load list data.
 */
abstract class BaseListDataLoadingFragment<PR, DA, IM, AD> : BaseDataLoadingFragment<PR, DA>(),
    ListDataLoadingView<DA>,
    Searchable where
        PR : BaseListDataLoadingPresenter<*, *, DA, *>,
        DA : Any,
        IM : BaseItem<*, *, *>,
        AD : BaseRecyclerViewAdapter<IM, *> {


    companion object {

        private const val SAVED_STATE_ITEMS = "items"

    }


    /**
     * Items that this fragment's adapter contains.
     */
    protected var mItems: MutableList<IM> = mutableListOf()

    /**
     * An adapter used by the recycler view.
     */
    protected lateinit var mAdapter: AD




    override fun init() {
        super.init()

        initRecyclerView()
    }


    /**
     * Initializes the recycler view returned by the [getMainView] method.
     */
    open fun initRecyclerView() {
        val recyclerView = (getMainView() as RecyclerView)

        if(shouldDisableRVAnimations()) {
            recyclerView.disableAnimations()
        }

        recyclerView.addOnScrollListener(RecyclerViewScrollListener(mOnRecyclerViewScrollListener))
        recyclerView.layoutManager = LinearLayoutManager(ctx)
        recyclerView.addItemDecoration(getRecyclerViewItemDecoration())

        mAdapter = initAdapter()
        mAdapter.addOnDatasetChangeListener(mOnDataSetChangeListener)

        recyclerView.adapter = mAdapter

        adjustView(recyclerView)
    }


    /**
     * Should initialize an adapter for the RecyclerView.
     */
    protected abstract fun initAdapter(): AD


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
        mAdapter.clear()
    }


    override fun scrollToTop() {
        (getMainView() as RecyclerView).scrollToPosition(0)
    }


    override fun setSearchQuery(query: String) {
        // Allow children to override this method
    }


    override fun isDataSourceEmpty(): Boolean {
        return (getDataSetSize() == 0)
    }


    /**
     * Override this method if you want to enable animations
     * for the RecyclerView. By default, they are disabled.
     */
    open fun shouldDisableRVAnimations(): Boolean {
        return true
    }


    override fun getDataSetSize(): Int {
        return if(::mAdapter.isInitialized) mAdapter.itemCount else 0
    }


    override fun getDataCacheKey(): String {
        return "${mPresenter!!}_$SAVED_STATE_ITEMS"
    }


    override fun getSearchQuery(): String {
        // Allow children to override this method
        return ""
    }


    /**
     * Should return an instance of [RecyclerView.ItemDecoration] to
     * set to the recycler view.
     */
    protected open fun getRecyclerViewItemDecoration(): RecyclerView.ItemDecoration {
        return VerticalSpacingItemDecorator(
            dimenInPx(R.dimen.recycler_view_divider_size),
            dimenInPx(R.dimen.card_view_elevation)
        )
    }


    override fun onPerformSearch(query: String) {
        if(query.isBlank() || (query == getSearchQuery())) {
            return
        }

        query.also { setSearchQuery(it) }
            .takeIf { isInitialized() }
            ?.let { mPresenter?.onSearchQueryChanged(it) }
            ?: return
    }


    override fun onCancelSearch() {
        mPresenter?.cancelDataLoading()
    }


    private val mOnRecyclerViewScrollListener: RecyclerViewScrollListener.StateListener =
        object : RecyclerViewStateListenerAdapter {

            override fun onBottomReached(recyclerView: RecyclerView, reachedCompletely: Boolean) {
                mPresenter?.onBottomReached(reachedCompletely)
            }

        }


    private val mOnDataSetChangeListener: OnDatasetChangeListener<MutableList<IM>, IM> =
        object : OnDatasetChangeListenerAdapter<MutableList<IM>, IM> {

            override fun onDatasetSizeChanged(oldSize: Int, newSize: Int) {
                mPresenter?.onDataSetSizeChanged(newSize)
            }

        }


    @Suppress("UNCHECKED_CAST")
    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        mItems = (ObjectCache.remove(getDataCacheKey(), mutableListOf<IM>()) as MutableList<IM>)
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        ObjectCache.put(getDataCacheKey(), mItems)
    }


}
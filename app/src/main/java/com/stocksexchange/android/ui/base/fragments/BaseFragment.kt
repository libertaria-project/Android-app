package com.stocksexchange.android.ui.base.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.stocksexchange.android.AppController
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.ui.base.mvp.presenters.Presenter
import com.stocksexchange.android.ui.utils.extensions.ctx
import com.stocksexchange.android.ui.utils.interfaces.Selectable
import com.stocksexchange.android.ui.utils.listeners.OnBackPressListener
import com.stocksexchange.android.utils.handlers.CoroutineHandler
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

/**
 * A fragment wrapper class used for holding common
 * functionality among all fragments.
 */
abstract class BaseFragment<P : Presenter> : Fragment(), Selectable, OnBackPressListener {


    companion object {

        private const val SAVED_STATE_STATE_BUNDLE = "saved_state"
        private const val SAVED_STATE_PRESENTER = "presenter"
        private const val SAVED_STATE_IS_SELECTED = "is_selected"

        private const val VISIBILITY_HINT_DELAY_FOR_TAB_CLICK = 300L
        private const val VISIBILITY_HINT_DELAY_FOR_SWIPE = 50L

    }


    private var mIsInitialized: Boolean = false

    private var mIsSelected: Boolean = false
    private var mIsSelectedByTabClick: Boolean = false
    private var mIsSelectedBySwipe: Boolean = false

    protected val mCoroutineHandler: CoroutineHandler by inject()

    private var mHandler: Handler = Handler(Looper.getMainLooper())

    /**
     * A presenter associated with this activity.
     */
    protected var mPresenter: P? = null

    /**
     * A view representing the fragment's UI.
     */
    protected lateinit var mRootView: View




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(getContentLayoutResourceId(), container, false)

        preInit()
        onRestoreState(savedInstanceState?.getBundle(SAVED_STATE_STATE_BUNDLE))
        init()
        postInit()

        mIsInitialized = true

        return mRootView
    }


    /**
     * Called right after [LayoutInflater.inflate] method is called.
     * Can be useful for performing some tasks before views
     * initialization.
     */
    @CallSuper
    protected open fun preInit() {
        mPresenter = initPresenter()
    }


    /**
     * An abstract method used for initialization of the presenter.
     * Gets invoked from within [preInit].
     */
    protected abstract fun initPresenter(): P


    /**
     * Called right after [onRestoreState] method is called. Typically,
     * all views initialization should go here.
     */
    protected open fun init() {
        // Stub
    }


    /**
     * Called right after [init] method is called. Can be useful
     * for performing some tasks after view initialization.
     */
    @CallSuper
    protected open fun postInit() {
        // Stub
    }


    /**
     * Shows a toast with a length of [Toast.LENGTH_SHORT] to the user.
     *
     * @param message The message for the toast
     */
    fun showToast(message: String) {
        ctx.toast(message)
    }


    /**
     * Shows a toast with a length of [Toast.LENGTH_LONG] to the user.
     *
     * @param message The message for the toast
     */
    fun showLongToast(message: String) {
        ctx.longToast(message)
    }


    /**
     * Sets a flag whether this fragment has been selected by tab click.
     *
     * @param isSelectedByTabClick Whether this fragment has been selected by tab
     * click
     */
    fun setSelectedByTabClick(isSelectedByTabClick: Boolean) {
        mIsSelectedByTabClick = isSelectedByTabClick
    }


    /**
     * Sets a flag whether this fragment has been selected by swipe.
     *
     * @param isSelectedBySwipe Whether this fragment has been selected by swipe
     */
    fun setSelectedBySwipe(isSelectedBySwipe: Boolean) {
        mIsSelectedBySwipe = isSelectedBySwipe
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        val runnable = Runnable {
            mIsSelectedByTabClick = false
            mIsSelectedBySwipe = false

            if(isVisibleToUser) {
                onSelected()
            } else {
                onUnselected()
            }
        }

        if(shouldDelayUserVisibilityHint(isVisibleToUser)) {
            mHandler.postDelayed(
                runnable,
                when {
                    mIsSelectedByTabClick -> VISIBILITY_HINT_DELAY_FOR_TAB_CLICK
                    mIsSelectedBySwipe -> VISIBILITY_HINT_DELAY_FOR_SWIPE

                    else -> 0L
                }
            )
        } else {
            runnable.run()
        }
    }


    /**
     * Checks whether this fragment has been initialized,
     * i.e. whether [onCreateView] has finished running.
     *
     * @return true if initialization is done; false otherwise
     */
    protected fun isInitialized(): Boolean {
        return mIsInitialized
    }


    /**
     * Checks whether this fragment is currently selected
     * (visible to the user) or not.
     *
     * @return true if visible; false otherwise
     */
    fun isSelected(): Boolean = mIsSelected


    /**
     * Checks whether this fragment has been selected by tab click or not.
     *
     * @return true if selected by tab click; false otherwise
     */
    fun isSelectedByTabClick(): Boolean = mIsSelectedByTabClick


    /**
     * Checks whether this fragment has been selected by swipe or not.
     *
     * @return true if selected by swipe; false otherwise
     */
    fun isSelectedBySwipe(): Boolean = mIsSelectedBySwipe


    /**
     * Should return a boolean indicating whether to delay
     * user visibility hint or not.
     *
     * @param isVisibleToUser Whether the fragment has become visible or invisible
     *
     * @return true if should be delayed; false otherwise
     */
    protected open fun shouldDelayUserVisibilityHint(isVisibleToUser: Boolean): Boolean {
        return (isInitialized() && isVisibleToUser)
    }


    /**
     * Returns an ID of a layout that this fragment is associated with.
     *
     * @return The ID of the layout
     */
    @LayoutRes
    protected abstract fun getContentLayoutResourceId(): Int


    /**
     * Returns application's settings.
     */
    protected fun getSettings(): Settings {
        return AppController.INSTANCE.getSettings()
    }


    /**
     * Returns application's currently selected theme.
     */
    protected fun getAppTheme(): Theme {
        return getSettings().theme
    }


    override fun onResume() {
        super.onResume()

        mPresenter?.start()
    }


    override fun onPause() {
        super.onPause()

        mPresenter?.stop()

        mCoroutineHandler.cancelChildren()
    }


    @CallSuper
    override fun onSelected() {
        mIsSelected = true
    }


    @CallSuper
    override fun onUnselected() {
        mIsSelected = false
    }


    override fun onBackPressed() {
        // Stub
    }


    /**
     * Override this method if you need to restore state from fragment.
     */
    @CallSuper
    @Suppress("UNCHECKED_CAST")
    protected open fun onRestoreState(savedState: Bundle?) {
        if(savedState != null) {
            mPresenter?.onRestoreState(savedState.getParcelable(SAVED_STATE_PRESENTER)!!)

            mIsSelected = savedState.getBoolean(SAVED_STATE_IS_SELECTED, false)
        }
    }


    /**
     * Override this method if you need to save state in fragment.
     */
    @CallSuper
    protected open fun onSaveState(savedState: Bundle) {
        val presenterState = com.stocksexchange.android.utils.SavedState().also {
            mPresenter?.onSaveState(it)
        }

        with(savedState) {
            putParcelable(SAVED_STATE_PRESENTER, presenterState)
            putBoolean(SAVED_STATE_IS_SELECTED, mIsSelected)
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        val savedState = Bundle()

        onSaveState(savedState)

        outState.putBundle(SAVED_STATE_STATE_BUNDLE, savedState)

        super.onSaveInstanceState(outState)
    }


    @CallSuper
    override fun onDestroy() {
        super.onDestroy()

        onRecycle()
    }


    /**
     * Override this method if you need to recycle stuff.
     */
    @CallSuper
    protected open fun onRecycle() {
        // Stub
    }


}
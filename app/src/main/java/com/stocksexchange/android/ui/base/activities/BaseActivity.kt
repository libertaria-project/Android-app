package com.stocksexchange.android.ui.base.activities

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.stocksexchange.android.AppController
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.receivers.AppLockReceiver
import com.stocksexchange.android.receivers.NetworkStateReceiver
import com.stocksexchange.android.ui.base.mvp.presenters.Presenter
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.utils.SavedState
import com.stocksexchange.android.utils.handlers.CoroutineHandler
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

/**
 * An activity wrapper class used for holding common
 * functionality among all activities.
 */
abstract class BaseActivity<P : Presenter> : AppCompatActivity(), NetworkStateReceiver.Listener,
    AppLockReceiver.Listener {


    companion object {

        private const val SAVED_STATE_STATE_BUNDLE = "saved_state"
        private const val SAVED_STATE_PRESENTER = "presenter"

    }




    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }


    private var mIsNetworkStateReceiverRegistered: Boolean = false
    private var mIsAppLockReceiverRegistered: Boolean = false
    private var mIsInitialized: Boolean = false

    private var mNetworkStateReceiver: NetworkStateReceiver? = null

    private var mAppLockReceiver: AppLockReceiver? = null

    protected val mCoroutineHandler: CoroutineHandler by inject()

    /**
     * A presenter associated with this activity.
     */
    protected var mPresenter: P? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(preViewInflation()) {
            return
        }

        setContentView(getContentLayoutResourceId())
        preInit()
        onRestoreState(savedInstanceState?.getBundle(SAVED_STATE_STATE_BUNDLE))
        init()
        postInit()

        mIsInitialized = true
    }


    /**
     * Called before [setContentView] is called. Can be useful for
     * determining whether the activity should be opened or closed
     * according to specific conditions.
     *
     * @return true if activity should be stopped; false otherwise
     */
    protected open fun preViewInflation(): Boolean = false


    /**
     * Called right after [setContentView] method is called.
     * Can be useful for performing some tasks before views
     * initialization.
     */
    @CallSuper
    protected open fun preInit() {
        mPresenter = initPresenter()

        overrideEnterTransition(getEnterTransitionAnimations())
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
    @CallSuper
    protected open fun init() {
        if(shouldSetStatusBarColor()) {
            setStatusBarColor(getStatusBarColor())
        }

        if(shouldSetRecentAppsToolbarColor()) {
            setRecentAppsToolbarColor(getRecentAppsToolbarColor())
        }
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
        toast(message)
    }


    /**
     * Shows a toast with a length of [Toast.LENGTH_LONG] to the user.
     *
     * @param message The message for the toast
     */
    fun showLongToast(message: String) {
        longToast(message)
    }


    private fun registerNetworkStateReceiver() {
        if(!canObserveNetworkStateChanges() && ((mNetworkStateReceiver != null) && mIsNetworkStateReceiverRegistered)) {
            return
        }

        mNetworkStateReceiver = NetworkStateReceiver(this, this)
        registerReceiver(mNetworkStateReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        mIsNetworkStateReceiverRegistered = true
    }


    private fun unregisterNetworkStateReceiver() {
        if (!mIsNetworkStateReceiverRegistered || (mNetworkStateReceiver == null)) {
            return
        }

        unregisterReceiver(mNetworkStateReceiver)

        mIsNetworkStateReceiverRegistered = false
    }


    private fun registerAppLockReceiver() {
        if((mAppLockReceiver != null) && mIsAppLockReceiverRegistered) {
            return
        }

        mAppLockReceiver = AppLockReceiver(this)
        registerReceiver(mAppLockReceiver, IntentFilter(AppLockReceiver.ACTION_APP_LOCK))

        mIsAppLockReceiverRegistered = true
    }


    private fun unregisterAppLockReceiver() {
        if(!mIsAppLockReceiverRegistered || (mAppLockReceiver == null)) {
            return
        }

        unregisterReceiver(mAppLockReceiver)

        mIsAppLockReceiverRegistered = false
    }


    /**
     * Override this method if your want to get notified about
     * network state changes.
     */
    protected open fun canObserveNetworkStateChanges(): Boolean {
        return false
    }


    /**
     * Returns an ID of a layout that this activity is associated with.
     *
     * @return The ID of the layout
     */
    @LayoutRes
    protected abstract fun getContentLayoutResourceId(): Int


    /**
     * Returns a color for the status bar.
     */
    protected open fun getStatusBarColor(): Int {
        return getAppTheme().generalTheme.primaryDarkColor
    }


    /**
     * Returns a color for the recent applications menu.
     */
    protected open fun getRecentAppsToolbarColor(): Int {
        return getAppTheme().generalTheme.primaryColor
    }


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


    /**
     * Returns transition animations for the entering window.
     */
    protected open fun getEnterTransitionAnimations(): TransitionAnimations {
        return getTransitionAnimations()
    }


    /**
     * Returns transition animations for the exiting window.
     */
    protected open fun getExitTransitionAnimations(): TransitionAnimations {
        return getTransitionAnimations()
    }


    /**
     * Returns transition animations both for the entering and exiting windows.
     */
    protected open fun getTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.DEFAULT_ANIMATIONS
    }


    /**
     * Returns a boolean specifying whether to set the status bar's color or not.
     */
    protected open fun shouldSetStatusBarColor(): Boolean {
        return true
    }


    /**
     * Returns a boolean specifying whether to set recent apps toolbar's color or not.
     */
    protected open fun shouldSetRecentAppsToolbarColor(): Boolean {
        return true
    }


    /**
     * Returns a boolean specifying whether to update the last interaction time for auth purposes.
     */
    protected open fun shouldUpdateLastInteractionTime(): Boolean {
        return true
    }


    /**
     * Checks whether this activity has been initialized
     * (i.e. whether [onCreate] method has finished running).
     */
    protected fun isInitialized(): Boolean {
        return mIsInitialized
    }


    override fun onResume() {
        super.onResume()

        mPresenter?.start()

        registerNetworkStateReceiver()
        registerAppLockReceiver()
    }


    override fun onPause() {
        super.onPause()

        mPresenter?.stop()

        mCoroutineHandler.cancelChildren()

        unregisterNetworkStateReceiver()
        unregisterAppLockReceiver()
    }


    override fun onUserInteraction() {
        super.onUserInteraction()

        if(shouldUpdateLastInteractionTime()) {
            AppController.INSTANCE.getAppLockManager()
                ?.updateLastInteractionTimestamp(this, System.currentTimeMillis())
        }
    }


    override fun onConnected() {
        // Stub
    }


    override fun onDisconnected() {
        // Stub
    }


    /**
     * Override this method if you want to get notified
     * that the device is soon to be locked.
     */
    @CallSuper
    override fun onAppLockRequested() {
        AppController.INSTANCE.getAppLockManager()?.authenticate(this)
    }


    override fun onBackPressed() {
        super.onBackPressed()

        overrideExitTransition(getExitTransitionAnimations())
    }


    /**
     * Override this method if you need to restore state from activity.
     */
    @CallSuper
    @Suppress("UNCHECKED_CAST")
    protected open fun onRestoreState(savedState: Bundle?) {
        if(savedState != null) {
            mPresenter?.onRestoreState(savedState.getParcelable(SAVED_STATE_PRESENTER)!!)
        }
    }


    /**
     * Override this method if you need to save state in activity.
     */
    @CallSuper
    protected open fun onSaveState(savedState: Bundle) {
        val presenterState = SavedState().also {
            mPresenter?.onSaveState(it)
        }

        savedState.putParcelable(SAVED_STATE_PRESENTER, presenterState)
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

        onRecycle(isChangingConfigurations)
    }


    /**
     * Override this method if you need to recycle stuff.
     *
     * @param isChangingConfigurations Whether the activity
     * is destroyed only to be recreated thereafter because
     * of a configuration change or if it destroyed without
     * the intention to be recreated immediately thereafter
     */
    @CallSuper
    protected open fun onRecycle(isChangingConfigurations: Boolean) {
        // Stub
    }


}
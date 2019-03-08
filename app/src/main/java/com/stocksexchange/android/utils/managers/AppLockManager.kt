package com.stocksexchange.android.utils.managers

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import com.stocksexchange.android.AppController
import com.stocksexchange.android.Constants
import com.stocksexchange.android.model.PinCodeModes
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.receivers.AppLockReceiver
import com.stocksexchange.android.ui.auth.AuthenticationActivity
import com.stocksexchange.android.ui.login.LoginActivity
import com.stocksexchange.android.ui.splash.SplashActivity
import com.stocksexchange.android.ui.utils.extensions.getAlarmManager
import com.stocksexchange.android.ui.utils.extensions.setAlarm
import com.stocksexchange.android.ui.utils.listeners.adapters.ActivityLifecycleCallbacksAdapter
import com.stocksexchange.android.utils.EncryptionUtil
import com.stocksexchange.android.utils.handlers.PreferenceHandler

/**
 * A manager class responsible for locking the application (by launching the authentication activity)
 * if the authentication session expires or if the user has not interacted with the app for 5 minutes.
 */
class AppLockManager private constructor(
    private val appController: AppController,
    private val preferenceHandler: PreferenceHandler
) : ActivityLifecycleCallbacksAdapter {


    companion object {

        @Volatile
        private var INSTANCE : AppLockManager? = null


        fun getInstance(appController: AppController,
                        preferenceHandler: PreferenceHandler): AppLockManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildAppLockManager(appController, preferenceHandler).also { INSTANCE = it }
            }
        }


        private fun buildAppLockManager(appController: AppController,
                                        preferenceHandler: PreferenceHandler): AppLockManager {
            return AppLockManager(appController, preferenceHandler)
        }

    }


    private var mLastAuthenticationTimestamp: Long
    private var mLastInteractionTimestamp: Long

    private var mAlarmManager: AlarmManager




    init {
        appController.registerActivityLifecycleCallbacks(this)

        mLastAuthenticationTimestamp = EncryptionUtil.getInstance().decrypt(preferenceHandler.getLastAuthTimestamp())
            .takeIf { it.isNotBlank() }
            ?.toLong()
            ?: 0L
        mLastInteractionTimestamp = 0L

        mAlarmManager = appController.getAlarmManager()
    }


    override fun onActivityResumed(activity: Activity) {
        if(!isActivityAuthenticable(activity)) {
            return
        }

        if(isUserPresent()) {
            registerAppLockReceiver()
        }

        if(shouldAuthenticate()) {
            authenticate(activity)
        }
    }


    override fun onActivityPaused(activity: Activity) {
        if(isUserPresent()) {
            unregisterAppLockReceiver()
        }
    }


    private fun registerAppLockReceiver() {
        mAlarmManager.setAlarm(
            (System.currentTimeMillis() + appController.getSettings().authenticationSessionDuration.timeInMillis),
            createPendingIntent()
        )
    }


    private fun unregisterAppLockReceiver() {
        mAlarmManager.cancel(createPendingIntent())
    }


    private fun createPendingIntent(): PendingIntent {
        return PendingIntent.getBroadcast(
            appController,
            Constants.REQUEST_CODE_APP_LOCK_RECEIVER,
            Intent(AppLockReceiver.ACTION_APP_LOCK),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }


    /**
     * Responsible for determining whether the user should authenticate or not.
     *
     * @return true if should; false otherwise
     */
    fun shouldAuthenticate(): Boolean {
        if(!isUserPresent()) {
            return false
        }

        if(!hasLastAuthenticationTimestamp()) {
            return true
        }

        val currentTime = System.currentTimeMillis()
        val authenticationSessionDuration = appController.getSettings().authenticationSessionDuration.timeInMillis
        val isAuthenticationSessionExpired = ((currentTime - mLastAuthenticationTimestamp) > authenticationSessionDuration)
        val isLastInteractionSessionExpired = ((currentTime - mLastInteractionTimestamp) > authenticationSessionDuration)

        return (isAuthenticationSessionExpired && isLastInteractionSessionExpired)
    }


    /**
     * Forces the user to authenticate to proceed using the app by
     * redirecting him to the authentication screen.
     *
     * @param activity The activity currently running
     */
    fun authenticate(activity: Activity) {
        activity.startActivity(AuthenticationActivity.newInstance(
            activity,
            PinCodeModes.ENTER,
            TransitionAnimations.OVERSHOOT_SCALING_ANIMATIONS,
            appController.getSettings().theme,
            activity.intent
        ))
        activity.finish()
    }


    /**
     * Checks if the user is present or not.
     *
     * @return true if present; false otherwise
     */
    fun isUserPresent(): Boolean {
        return preferenceHandler.hasUserName()
    }


    /**
     * Updates the last authentication timestamp.
     *
     * @param lastAuthenticationTimestamp The last authentication timestamp
     */
    fun updateLastAuthenticationTimestamp(lastAuthenticationTimestamp: Long) {
        mLastAuthenticationTimestamp = lastAuthenticationTimestamp
    }


    /**
     * Updates the last interaction timestamp.
     *
     * @param activity The activity which received the last interaction timestamp
     * @param lastInteractionTimestamp The last user interaction timestamp
     */
    fun updateLastInteractionTimestamp(activity: Activity, lastInteractionTimestamp: Long) {
        if(!isUserPresent()) {
            return
        }

        mLastInteractionTimestamp = lastInteractionTimestamp

        if(isActivityAuthenticable(activity)) {
            registerAppLockReceiver()
        }
    }


    /**
     * Resets the app lock manager.
     */
    fun reset() {
        mLastAuthenticationTimestamp = 0L
        mLastInteractionTimestamp = 0L

        unregisterAppLockReceiver()
    }


    private fun isActivityAuthenticable(activity: Activity): Boolean {
        if((activity is SplashActivity) || (activity is LoginActivity)) {
            return false
        }

        if((activity is AuthenticationActivity)
            && ((activity.getPinCodeMode() == PinCodeModes.ENTER) || (activity.getPinCodeMode() == PinCodeModes.CREATION))) {
            return false
        }

        return true
    }


    private fun hasLastAuthenticationTimestamp(): Boolean {
        return (mLastAuthenticationTimestamp != 0L)
    }


}
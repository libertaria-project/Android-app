package com.stocksexchange.android

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.google.firebase.analytics.FirebaseAnalytics
import com.newrelic.agent.android.NewRelic
import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.database.tables.SettingsTable
import com.stocksexchange.android.database.tables.UsersTable
import com.stocksexchange.android.di.applicationModules
import com.stocksexchange.android.factories.SettingsFactory
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.utils.CrashReportingTree
import com.stocksexchange.android.utils.SocketConnection
import com.stocksexchange.android.utils.managers.AppLockManager
import com.stocksexchange.android.utils.managers.RealTimeDataManager
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin
import org.koin.log.EmptyLogger
import org.koin.standalone.KoinComponent
import org.koin.standalone.get
import timber.log.Timber

/**
 * An application controller responsible for maintaining
 * global application state.
 */
class AppController : Application(), KoinComponent {


    companion object {

        lateinit var INSTANCE: AppController

    }


    private var mUser: User? = null

    private var mSettings: Settings? = null

    private var mSocketConnection: SocketConnection? = null

    private var mRealTimeDataManager: RealTimeDataManager? = null

    private var mAppLockManager: AppLockManager? = null




    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        initDi()
        initLogger()
        initSocketConnection()
        initRealTimeDataManager()
        initAppLockManager()
        initFirebaseAnalytics()
        initCrashlytics()
        initNewRelic()
    }


    private fun initDi() {
        startKoin(
            application = this,
            modules = applicationModules,
            logger = EmptyLogger()
        )
    }


    private fun initLogger() {
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }


    private fun initSocketConnection() {
        mSocketConnection = SocketConnection.getInstance(this, getSettings())
    }


    private fun initRealTimeDataManager() {
        mRealTimeDataManager = RealTimeDataManager.getInstance(this)
    }


    private fun initAppLockManager() {
        mAppLockManager = AppLockManager.getInstance(this, get())
    }


    private fun initFirebaseAnalytics() {
        if(!BuildConfig.DEBUG) {
            FirebaseAnalytics.getInstance(this)
        }
    }


    private fun initCrashlytics() {
        if(!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
        }
    }


    private fun initNewRelic() {
        if(!BuildConfig.DEBUG) {
            NewRelic.withApplicationToken(BuildConfig.NEW_RELIC_TOKEN).start(this)
        }
    }


    /**
     * Sets a user.
     *
     * @param user The user to set
     */
    fun setUser(user: User) {
        mUser = user
    }


    /**
     * Retrieves a user.
     *
     * @return The user
     */
    fun getUser(): User? {
        if(mUser == null) {
            mUser = get<UsersTable>().getSignedInUser()
                    .takeIf { it is Result.Success }
                    ?.let { (it as Result.Success).value }
                    ?: User.STUB_USER
        }

        return if(mUser?.isStub() != false) null else mUser
    }


    /**
     * Sets the settings.
     *
     * @param settings The settings to set
     */
    fun setSettings(settings: Settings) {
        mSettings = settings
    }


    /**
     * Retrieves application settings.
     *
     * @return The app's settings
     */
    fun getSettings(): Settings {
        if(mSettings == null) {
            mSettings = get<SettingsTable>().get()
                    .takeIf { it is Result.Success }
                    ?.let { (it as Result.Success).value }
                    ?: get<SettingsFactory>().getDefaultSettings()
        }

        return mSettings!!
    }


    /**
     * Retrieves the socket connection.
     *
     * @return The socket connection
     */
    fun getSocketConnection(): SocketConnection? {
        return mSocketConnection
    }


    /**
     * Retrieves the app lock manager.
     *
     * @return The app lock manager
     */
    fun getAppLockManager(): AppLockManager? {
        return mAppLockManager
    }


}
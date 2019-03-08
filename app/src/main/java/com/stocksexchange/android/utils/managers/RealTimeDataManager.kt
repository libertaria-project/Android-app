package com.stocksexchange.android.utils.managers

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import com.stocksexchange.android.events.RealTimeDataUpdateEvent
import com.stocksexchange.android.receivers.NetworkStateReceiver
import org.greenrobot.eventbus.EventBus

/**
 * A manager responsible for tracking and notifying system components
 * when real time data needs to be updated (e.g., when the network
 * has become available or the app has come to foreground from the
 * background state).
 */
class RealTimeDataManager private constructor(
    application: Application
) {


    companion object {

        @Volatile
        private var INSTANCE : RealTimeDataManager? = null


        fun getInstance(application: Application): RealTimeDataManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildRealTimeDataManager(application).also { INSTANCE = it }
            }
        }


        private fun buildRealTimeDataManager(application: Application): RealTimeDataManager {
            return RealTimeDataManager(application)
        }

    }




    init {
        initNetworkReceiver(application)
        initBackgroundManager(application)
    }


    private fun initNetworkReceiver(context: Context) {
        val networkListener = object : NetworkStateReceiver.Listener {

            override fun onConnected() {
                sendRealTimeDataUpdateEvent()
            }

        }

        context.registerReceiver(
            NetworkStateReceiver(context, networkListener),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }


    private fun initBackgroundManager(application: Application) {
        val backgroundManagerListener = object : BackgroundManager.Listener {

            override fun onBecameForeground() {
                sendRealTimeDataUpdateEvent()
            }

        }

        BackgroundManager.getInstance(application).registerListener(backgroundManagerListener)
    }


    private fun sendRealTimeDataUpdateEvent() {
        EventBus.getDefault().postSticky(RealTimeDataUpdateEvent.newInstance(this))
    }


}
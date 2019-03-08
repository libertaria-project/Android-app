package com.stocksexchange.android.receivers

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.stocksexchange.android.ui.utils.extensions.isNetworkAvailable

/**
 * A receiver to get notified whenever the network
 * state changes.
 */
class NetworkStateReceiver(
    context: Context,
    private val listener: Listener
) : BaseBroadcastReceiver() {


    private var isConnected: Boolean = context.isNetworkAvailable()




    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val isNetworkAvailable = context.isNetworkAvailable()

            if(isConnected == isNetworkAvailable) {
                return
            }

            if(isNetworkAvailable) {
                if(!isConnected) {
                    isConnected = true
                    listener.onConnected()
                }
            } else {
                if(isConnected) {
                    isConnected = false
                    listener.onDisconnected()
                }
            }
        }
    }


    interface Listener {


        /**
         * Allows you to get notified when the device has been
         * connected to the network.
         */
        fun onConnected() {
            // Stub
        }


        /**
         * Allows you to get notified when the device has been
         * disconnected from the network.
         */
        fun onDisconnected() {
            // Stub
        }


    }


}
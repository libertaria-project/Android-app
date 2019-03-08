package com.stocksexchange.android.receivers

import android.content.Context
import android.content.Intent

/**
 * A received user for handling app locking functionality.
 */
class AppLockReceiver(private val listener: Listener) : BaseBroadcastReceiver() {


    companion object {

        val ACTION_APP_LOCK = "${AppLockReceiver::class.java.`package`.name}.ACTION_APP_LOCK"

    }




    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == ACTION_APP_LOCK) {
            listener.onAppLockRequested()
        }
    }


    interface Listener {

        fun onAppLockRequested()

    }


}
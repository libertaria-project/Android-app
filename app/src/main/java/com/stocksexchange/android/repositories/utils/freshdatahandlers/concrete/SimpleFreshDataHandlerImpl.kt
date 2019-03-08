package com.stocksexchange.android.repositories.utils.freshdatahandlers.concrete

import com.stocksexchange.android.repositories.utils.freshdatahandlers.interfaces.SimpleFreshDataHandler
import com.stocksexchange.android.utils.providers.ConnectionProvider

/**
 * A simple fresh data handler. Simple means that it holds
 * just a single a boolean value that is actually used for
 * determining whether to load fresh data or not.
 */
class SimpleFreshDataHandlerImpl : SimpleFreshDataHandler {


    private var mShouldLoadFreshData: Boolean = true




    override fun refresh(extraData: Any?) {
        mShouldLoadFreshData = true
    }


    override fun shouldLoadFreshData(connectionProvider: ConnectionProvider,
                                     extraData: Any?): Boolean {
        val result = (connectionProvider.isNetworkAvailable() && mShouldLoadFreshData)

        if(result) {
            mShouldLoadFreshData = false
        }

        return result
    }


}
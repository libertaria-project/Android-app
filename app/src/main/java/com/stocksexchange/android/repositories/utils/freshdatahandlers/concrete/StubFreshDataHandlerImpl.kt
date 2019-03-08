package com.stocksexchange.android.repositories.utils.freshdatahandlers.concrete

import com.stocksexchange.android.repositories.utils.freshdatahandlers.interfaces.FreshDataHandler
import com.stocksexchange.android.utils.providers.ConnectionProvider

/**
 * A stub fresh data handler.
 */
class StubFreshDataHandlerImpl : FreshDataHandler {


    override fun refresh(extraData: Any?) {
        // Stub
    }


    override fun shouldLoadFreshData(connectionProvider: ConnectionProvider,
                                     extraData: Any?): Boolean {
        return false
    }


}
package com.stocksexchange.android.repositories.utils.freshdatahandlers.interfaces

import com.stocksexchange.android.utils.providers.ConnectionProvider

/**
 * An interface to implement all implementations of
 * fresh data handlers.
 */
interface FreshDataHandler {

    /**
     * Should request fresh data to be downloaded on subsequent
     * data loading requests.
     *
     * @param extraData The optional extra data to use
     */
    fun refresh(extraData: Any? = null)

    /**
     * Should return a boolean value indicating whether the
     * fresh data should be loaded or not.
     *
     * @param connectionProvider The connection provider to use
     * to check if internet connection is available
     * @param extraData The optional extra data to use
     *
     * @return true if fresh data should be loaded; false otherwise
     */
    fun shouldLoadFreshData(connectionProvider: ConnectionProvider, extraData: Any? = null): Boolean

}
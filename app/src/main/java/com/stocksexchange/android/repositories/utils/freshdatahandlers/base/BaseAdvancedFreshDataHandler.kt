package com.stocksexchange.android.repositories.utils.freshdatahandlers.base

import com.stocksexchange.android.api.model.newapi.utils.HasUniqueKey
import com.stocksexchange.android.repositories.utils.freshdatahandlers.interfaces.FreshDataHandler
import com.stocksexchange.android.utils.extensions.getWithDefault
import com.stocksexchange.android.utils.providers.ConnectionProvider

/**
 * A base class advanced fresh data handler class to extend from.
 */
abstract class BaseAdvancedFreshDataHandler<T : HasUniqueKey> : FreshDataHandler {


    /**
     * A map holding boolean values indicating whether to load
     * fresh data or not.
     */
    private val mShouldLoadFreshDataMap: MutableMap<String, Boolean> = mutableMapOf()




    override fun refresh(extraData: Any?) {
        if(extraData !is HasUniqueKey) {
            return
        }

        mShouldLoadFreshDataMap[extraData.uniqueKey] = true
    }


    override fun shouldLoadFreshData(connectionProvider: ConnectionProvider,
                                     extraData: Any?): Boolean {
        if(extraData !is HasUniqueKey) {
            return false
        }

        val result = (connectionProvider.isNetworkAvailable() &&
                      mShouldLoadFreshDataMap.getWithDefault(extraData.uniqueKey, true))

        if(result) {
            mShouldLoadFreshDataMap[extraData.uniqueKey] = false
        }

        return result
    }


}
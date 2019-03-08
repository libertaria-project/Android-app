package com.stocksexchange.android.repositories.base.repository

import com.stocksexchange.android.repositories.utils.freshdatahandlers.concrete.StubFreshDataHandlerImpl
import com.stocksexchange.android.repositories.utils.freshdatahandlers.interfaces.FreshDataHandler
import org.koin.standalone.KoinComponent

/**
 * A base repository to extend from.
 */
abstract class BaseRepository : Repository, KoinComponent {


    /**
     * A fresh data handler used for handling when to and when
     * not to load fresh data.
     */
    open val freshDataHandler: FreshDataHandler = StubFreshDataHandlerImpl()


}
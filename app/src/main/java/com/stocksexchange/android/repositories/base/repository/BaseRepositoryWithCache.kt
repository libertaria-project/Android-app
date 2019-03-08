package com.stocksexchange.android.repositories.base.repository

import com.stocksexchange.android.repositories.base.cache.BaseRepositoryCache

/**
 * A base repository with cache support to extend from.
 */
abstract class BaseRepositoryWithCache<out T : BaseRepositoryCache> : BaseRepository() {


    /**
     * A cache for storing repository related data.
     */
    abstract val cache: T


}
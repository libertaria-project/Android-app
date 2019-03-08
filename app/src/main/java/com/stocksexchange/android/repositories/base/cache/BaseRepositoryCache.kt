package com.stocksexchange.android.repositories.base.cache

import com.stocksexchange.android.cache.Cache
import com.stocksexchange.android.cache.CacheFactory

/**
 * A base cache used inside [BaseRepository] class.
 */
abstract class BaseRepositoryCache : RepositoryCache {


    /**
     * A cache for storing the user-related data.
     */
    protected val cache: Cache<String, Any> = CacheFactory.getCache(true)




    /**
     * Clears the cache.
     */
    fun clear() {
        cache.clear()
    }


    /**
     * Checks whether the cache is empty.
     *
     * @return true if empty; false otherwise
     */
    fun isEmpty(): Boolean {
        return cache.isEmpty()
    }


}
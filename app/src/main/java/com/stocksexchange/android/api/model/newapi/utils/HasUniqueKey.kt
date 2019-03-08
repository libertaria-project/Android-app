package com.stocksexchange.android.api.model.newapi.utils

/**
 * A helper interface used for extracting a unique
 * string key from an implementor in question.
 */
interface HasUniqueKey {

    /**
     * A field that returns a unique string key.
     */
    val uniqueKey: String

}
package com.stocksexchange.android.model

/**
 * A model class representing a data of a deep link.
 */
data class DeepLinkData(
    val deepLinkType: DeepLinkTypes,
    val data: Any? = null
)
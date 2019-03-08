package com.stocksexchange.android.model

/**
 * An enumeration of all possible data loading sources.
 */
enum class DataLoadingSources {

    START,
    VIEW_SELECTION,
    NETWORK_CONNECTIVITY,
    SEARCH_QUERY_CHANGE,
    REFRESHMENT,
    BOTTOM_REACH,
    REAL_TIME_DATA_LOSS,
    OTHER

}
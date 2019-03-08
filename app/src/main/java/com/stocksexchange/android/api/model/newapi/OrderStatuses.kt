package com.stocksexchange.android.api.model.newapi

/**
 * An enumeration of all possible statuses of an order.
 */
enum class OrderStatuses {

    ALL,

    PENDING,
    PROCESSING,
    FINISHED,
    PARTIAL,
    CANCELED,

    UNKNOWN

}
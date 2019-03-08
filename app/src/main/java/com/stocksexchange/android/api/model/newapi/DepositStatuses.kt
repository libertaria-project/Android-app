package com.stocksexchange.android.api.model.newapi

/**
 * An enumeration of all possible statuses of a deposit.
 */
enum class DepositStatuses(val status: String) {

    PROCESSING("PROCESSING"),
    AWAITING_APPROVAL("AWAITING APPROVAL"),
    FINISHED("FINISHED"),
    CANCELLED_BY_ADMIN("CANCELLED BY ADMIN"),
    DEPOSIT_ERROR("DEPOSIT ERROR"),
    HODL("HODL"),

    UNKNOWN("UNKNOWN")

}
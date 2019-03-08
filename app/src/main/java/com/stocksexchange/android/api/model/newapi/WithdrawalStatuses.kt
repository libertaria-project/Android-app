package com.stocksexchange.android.api.model.newapi

/**
 * An enumeration of all possible statuses of a withdrawal.
 */
enum class WithdrawalStatuses(val status: String) {

    PROCESSING("Processing"),
    AWAITING_APPROVAL("Awaiting Approval"),
    APPROVED("Approved"),
    AWAITING("Awaiting"),
    NOT_CONFIRMED("Not Confirmed"),
    FINISHED("Finished"),
    CANCELLED_BY_USER("Cancelled by User"),
    CANCELLED_BY_ADMIN("Cancelled by Admin"),
    ERROR("Withdrawal Error"),

    UNKNOWN("Unknown")

}
package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing a withdrawal.
 */
@Parcelize
data class Withdrawal(
    @SerializedName("id") val id: Long,
    @SerializedName("currency_id") val currencyId: Int,
    @SerializedName("currency_code") val currencyCode: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("fee") val fee: Double,
    @SerializedName("fee_currency_id") val feeCurrencyId: Int,
    @SerializedName("fee_currency_code") val feeCurrencyCode: String,
    @SerializedName("status") val statusStr: String,
    @SerializedName("created_ts") val creationTimestamp: Long,
    @SerializedName("updated_ts") val updateTimestamp: Long,
    @SerializedName("txid") val transactionId: String? = null,
    @SerializedName("withdrawal_address") val address: TransactionAddress?
) : Parcelable {


    /**
     * A field that returns a boolean flag indicating
     * whether this withdrawal has a transaction ID.
     */
    val hasTransactionId: Boolean
        get() = (transactionId != null)


    /**
     * A field that returns a status of the withdrawal.
     */
    val status: WithdrawalStatuses
        get() = when(statusStr) {
            WithdrawalStatuses.PROCESSING.status -> WithdrawalStatuses.PROCESSING
            WithdrawalStatuses.AWAITING_APPROVAL.status -> WithdrawalStatuses.AWAITING_APPROVAL
            WithdrawalStatuses.APPROVED.status -> WithdrawalStatuses.APPROVED
            WithdrawalStatuses.AWAITING.status -> WithdrawalStatuses.AWAITING
            WithdrawalStatuses.NOT_CONFIRMED.status -> WithdrawalStatuses.NOT_CONFIRMED
            WithdrawalStatuses.FINISHED.status -> WithdrawalStatuses.FINISHED
            WithdrawalStatuses.CANCELLED_BY_USER.status -> WithdrawalStatuses.CANCELLED_BY_USER
            WithdrawalStatuses.CANCELLED_BY_ADMIN.status -> WithdrawalStatuses.CANCELLED_BY_ADMIN
            WithdrawalStatuses.ERROR.status -> WithdrawalStatuses.ERROR

            else -> WithdrawalStatuses.ERROR
        }


}
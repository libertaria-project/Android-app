package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing a deposit.
 */
@Parcelize
data class Deposit(
    @SerializedName("id") val id: Long,
    @SerializedName("currency_id") val currencyId: Int,
    @SerializedName("currency_code") val currencyCode: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("fee") val fee: Double,
    @SerializedName("status") val statusStr: String,
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("txid") val transactionId: String? = null,
    @SerializedName("confirmations") val confirmations: String
) : Parcelable {


    /**
     * A field that returns a boolean flag indicating
     * whether this deposit has a transaction ID.
     */
    val hasTransactionId: Boolean
        get() = (transactionId != null)


    /**
     * A field that returns a status of the this deposit.
     */
    val status: DepositStatuses
        get() = when(statusStr) {
            DepositStatuses.PROCESSING.status -> DepositStatuses.PROCESSING
            DepositStatuses.AWAITING_APPROVAL.status -> DepositStatuses.AWAITING_APPROVAL
            DepositStatuses.FINISHED.status -> DepositStatuses.FINISHED
            DepositStatuses.CANCELLED_BY_ADMIN.status -> DepositStatuses.CANCELLED_BY_ADMIN
            DepositStatuses.DEPOSIT_ERROR.status -> DepositStatuses.DEPOSIT_ERROR
            DepositStatuses.HODL.status -> DepositStatuses.HODL

            else -> DepositStatuses.UNKNOWN
        }


}
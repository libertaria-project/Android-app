package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing a wallet.
 */
@Parcelize
data class Wallet(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("currency_id") val currencyId: Int,
    @SerializedName("delisted") val isDelisted: Boolean,
    @SerializedName("disabled") val isDisabled: Boolean,
    @SerializedName("disable_deposits") val isDepositingDisabled: Boolean,
    @SerializedName("currency_name") val currencyName: String,
    @SerializedName("currency_code") val currencyCode: String,
    @SerializedName("balance") val currentBalance: Double,
    @SerializedName("frozen_balance") val frozenBalance: Double,
    @SerializedName("bonus_balance") val bonusBalance: Double,
    @SerializedName("total_balance") val totalBalance: Double,
    @SerializedName("deposit_address") val depositAddress: TransactionAddress? = null
) : Parcelable {


    /**
     * A field that returns a boolean flag indicating
     * whether this wallet has an ID or not.
     */
    val hasId: Boolean
        get() = (id != null)


    /**
     * A field that returns a boolean flag indicating
     * whether this wallet has a deposit address or not.
     */
    val hasDepositAddress: Boolean
        get() = (depositAddress != null)


}
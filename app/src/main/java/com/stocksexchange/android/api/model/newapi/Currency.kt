package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing a crypto currency (e.g., BTC, ETH, etc.)
 */
@Parcelize
data class Currency(
    @SerializedName("id") val id: Int,
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("active") val isActive: Boolean,
    @SerializedName("delisted") val isDelisted: Boolean,
    @SerializedName("precision") val precision: Int,
    @SerializedName("minimum_withdrawal_amount") val minimumWithdrawalAmount: Double,
    @SerializedName("minimum_deposit_amount") val minimumDepositAmount: Double,
    @SerializedName("deposit_fee_currency_id") val depositFeeCurrencyId: Int,
    @SerializedName("deposit_fee_const") val depositFee: Double,
    @SerializedName("deposit_fee_percent") val depositFeeInPercentage: Double,
    @SerializedName("withdrawal_fee_currency_id") val withdrawalFeeCurrencyId: Int,
    @SerializedName("withdrawal_fee_const") val withdrawalFee: Double,
    @SerializedName("withdrawal_fee_percent") val withdrawalFeeInPercentage: Double,
    @SerializedName("block_explorer_url") val blockExplorerUrl: String
) : Parcelable
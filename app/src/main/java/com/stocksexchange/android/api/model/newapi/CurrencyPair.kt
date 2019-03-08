package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing a currency pair.
 */
@Parcelize
data class CurrencyPair(
    @SerializedName("id") val id: Int,
    @SerializedName("currency_id") val baseCurrencyId: Int,
    @SerializedName("currency_code") val baseCurrencyCode: String,
    @SerializedName("currency_name") val baseCurrencyName: String,
    @SerializedName("market_currency_id") val quoteCurrencyId: Int,
    @SerializedName("market_code") val quoteCurrencyCode: String,
    @SerializedName("market_name") val quoteCurrencyName: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("group_name") val groupName: String,
    @SerializedName("group_id") val groupId: Int,
    @SerializedName("min_order_amount") val minOrderAmount: Double,
    @SerializedName("min_buy_price") val minBuyPrice: Double,
    @SerializedName("min_sell_price") val minSellPrice: Double,
    @SerializedName("buy_fee_percent") val buyFeeInPercentage: Double,
    @SerializedName("sell_fee_percent") val sellFeeInPercentage: Double,
    @SerializedName("active") val isActive: Boolean,
    @SerializedName("delisted") val isDelisted: Boolean,
    @SerializedName("pair_message") val message: String? = null,
    @SerializedName("currency_precision") val baseCurrencyPrecision: Int,
    @SerializedName("market_precision") val quoteCurrencyPrecision: Int
) : Parcelable {


    companion object {

        const val CURRENCY_PAIR_ALL_CODE = "ALL"

    }


    /**
     * A field that returns a boolean flag indicating
     * whether this currency pair has a message or not.
     */
    val hasMessage: Boolean
        get() = (message != null)


}
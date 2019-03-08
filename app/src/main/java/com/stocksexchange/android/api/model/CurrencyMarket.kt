package com.stocksexchange.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.stocksexchange.android.utils.helpers.extractBaseCurrencySymbol
import com.stocksexchange.android.utils.helpers.extractQuoteCurrencySymbol
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * A class representing a currency market (e.g., ETH_BTC, NXT_ETH).
 */
@Parcelize
data class CurrencyMarket(
    @SerializedName("market_id") val id: Long = -1L,
    @SerializedName("market_name") val name: String = "",
    @SerializedName("currency1_name") val baseCurrencyName: String = "",
    @SerializedName("currency2_name") val quoteCurrencyName: String = "",
    @SerializedName("min_order_amount") val minOrderAmount: Double = -1.0,
    @SerializedName("bid") val dailyMinPrice: Double = -1.0,
    @SerializedName("ask") val dailyMaxPrice: Double = -1.0,
    @SerializedName("last") val lastPrice: Double = -1.0,
    @SerializedName("lastDayAgo") val lastPriceDayAgo: Double = -1.0,
    @SerializedName("spread") val dailyPriceChange: Double = -1.0,
    @SerializedName("vol") val dailyVolume: Double = -1.0,
    @SerializedName("vol_market") val volume: Double = -1.0,
    @SerializedName("buy_fee_percent") val buyFeePercent: Double = -1.0,
    @SerializedName("sell_fee_percent") val sellFeePercent: Double = -1.0,
    @SerializedName("active") val isActive: Boolean = true
) : Parcelable {


    /**
     * A field holding the base currency symbol of the market name.
     */
    @IgnoredOnParcel
    var baseCurrencySymbol: String = ""
        private set
        get() {
            if(field.isBlank()) {
                field = extractBaseCurrencySymbol(name)
            }

            return field
        }


    /**
     * A field holding the quote currency symbol of the market name.
     */
    @IgnoredOnParcel
    var quoteCurrencySymbol: String = ""
        private set
        get() {
            if(field.isBlank()) {
                field = extractQuoteCurrencySymbol(name)
            }

            return field
        }


}
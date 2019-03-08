package com.stocksexchange.android.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.stocksexchange.android.api.services.StocksExchangeService
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing [StocksExchangeService.getDeposit]
 * method response.
 */
@Parcelize
data class Deposit(
    @SerializedName("msg") var message: String = "",
    @SerializedName("currency", alternate = ["code"]) val currency: String = "",
    @SerializedName("address") val address: String = "",
    @SerializedName("publicKey") val publicKey: String = "",
    @SerializedName("payment_id") val paymentId: String = "",
    @SerializedName("destination_tag") val destinationTag: String = ""
) : Parcelable {


    /**
     * Checks whether the currency is not empty.
     *
     * @return true if not empty; false otherwise
     */
    fun hasCurrency(): Boolean {
        return currency.isNotBlank()
    }


    /**
     * Checks whether the address is not empty.
     *
     * @return true if not empty; false otherwise
     */
    fun hasAddress(): Boolean {
        return address.isNotBlank()
    }


    /**
     * Checks whether the public key is not empty.
     *
     * @return true if not empty; false otherwise
     */
    fun hasPublicKey(): Boolean {
        return publicKey.isNotBlank()
    }


    /**
     * Checks whether the payment id is not empty.
     *
     * @return true if not empty; false otherwise
     */
    fun hasPaymentId(): Boolean {
        return paymentId.isNotBlank()
    }


    /**
     * Checks whether the destination tag is not empty.
     *
     * @return true if not empty; false otherwise
     */
    fun hasDestinationTag(): Boolean {
        return destinationTag.isNotBlank()
    }


    /**
     * Checks whether this deposit contains at least three items.
     *
     * @return true if contains; false otherwise
     */
    fun hasAtLeastThreeItems(): Boolean {
        return (hasAddress() && hasPublicKey() && (hasPaymentId() || hasDestinationTag()))
    }


    /**
     * Checks whether this deposit contains at least two items.
     *
     * @return true if contains; false otherwise
     */
    fun hasAtLeastTwoItems(): Boolean {
        return ((hasAddress() && hasPublicKey()) || (hasAddress() && hasPaymentId()) || (hasAddress() && hasDestinationTag()))
    }


    /**
     * Checks whether this deposit contains at least one item.
     *
     * @return true if contains; false otherwise
     */
    fun hasAtLeastOneItem(): Boolean {
        return (hasAddress() || hasPublicKey() || hasPaymentId() || hasDestinationTag())
    }


}
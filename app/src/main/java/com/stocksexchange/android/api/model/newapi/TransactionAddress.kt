package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing an address of the transaction.
 */
@Parcelize
data class TransactionAddress(
    @SerializedName("address") val address: String,
    @SerializedName("address_name") val addressName: String,
    @SerializedName("additional_address_parameter_name") val additionalAddressParameterNameStr: String? = null,
    @SerializedName("additional_address_parameter") val additionalAddressParameter: String? = null,
    @SerializedName("notification") val notification: String = ""
) : Parcelable {


    /**
     * A field that returns a boolean flag indicating
     * whether this transaction address has an additional
     * address parameter.
     */
    val hasAdditionalAddressParameter: Boolean
        get() = ((additionalAddressParameterNameStr != null) && (additionalAddressParameter != null))


    /**
     * A field that returns a boolean flag indicating
     * whether this transaction has a notification message.
     */
    val hasNotification: Boolean
        get() = notification.isNotEmpty()


    /**
     * A field that returns a type of the additional address parameter name.
     */
    val additionalAddressParameterName: AdditionalAddressParameterNames
        get() = when(additionalAddressParameterNameStr) {
            AdditionalAddressParameterNames.PUBLIC_KEY.title -> AdditionalAddressParameterNames.PUBLIC_KEY
            AdditionalAddressParameterNames.DESTINATION_TAG.title -> AdditionalAddressParameterNames.DESTINATION_TAG

            else -> AdditionalAddressParameterNames.UNKNOWN
        }


    /**
     * An enumeration of all possible additional address parameter names.
     */
    enum class AdditionalAddressParameterNames(val title: String) {

        PUBLIC_KEY("Public Key"),
        DESTINATION_TAG("Destination Tag"),

        UNKNOWN("Unknown")

    }


}
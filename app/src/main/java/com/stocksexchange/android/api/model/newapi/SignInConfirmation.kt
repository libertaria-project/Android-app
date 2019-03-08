package com.stocksexchange.android.api.model.newapi

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

/**
 * A model class representing a confirmation for a login.
 */
data class SignInConfirmation(
    @SerializedName(JSON_FIELD_KEY_MESSAGE) val message: String,
    @SerializedName(JSON_FIELD_KEY_CONFIRM_TYPE) val confirmationTypeStr: String
) {


    companion object {

        private const val JSON_FIELD_KEY_MESSAGE = "message"
        private const val JSON_FIELD_KEY_CONFIRM_TYPE = "confirm_type"


        /**
         * Tries to create a new instance object by fetching all
         * necessary data fields from passed json object.
         *
         * @param jsonObject The JSON object holding our data fields
         *
         * @return The new instance or null if the JSON object does not
         * have the fields that are needed by the instance
         */
        fun newInstance(jsonObject: JsonObject): SignInConfirmation? {
            return if(jsonObject.has(JSON_FIELD_KEY_MESSAGE) && jsonObject.has(JSON_FIELD_KEY_CONFIRM_TYPE)) {
                SignInConfirmation(
                    message = jsonObject.get(JSON_FIELD_KEY_MESSAGE).asString,
                    confirmationTypeStr = jsonObject.get(JSON_FIELD_KEY_CONFIRM_TYPE).asString
                )
            } else {
                null
            }
        }

    }


    /**
     * A field that returns a type of the confirmation
     * required to finish the sign in process.
     */
    val confirmationType: SignInConfirmationTypes
        get() = when(confirmationTypeStr) {
            SignInConfirmationTypes.EMAIL.abbreviation -> SignInConfirmationTypes.EMAIL
            SignInConfirmationTypes.SMS.abbreviation -> SignInConfirmationTypes.SMS
            SignInConfirmationTypes.TWO_FACTOR_AUTHENTICATION.abbreviation -> SignInConfirmationTypes.TWO_FACTOR_AUTHENTICATION

            else -> SignInConfirmationTypes.UNKNOWN
        }


}
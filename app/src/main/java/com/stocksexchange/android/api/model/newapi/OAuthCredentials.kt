package com.stocksexchange.android.api.model.newapi

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

/**
 * A model class holding OAuth related authorization tokens.
 */
data class OAuthCredentials(
    @SerializedName(JSON_FIELD_KEY_TOKEN_TYPE) val tokenType: String,
    @SerializedName(JSON_FIELD_KEY_ACCESS_TOKEN_TTL) val accessTokenTtl: Long, // in seconds
    @SerializedName(JSON_FIELD_KEY_ACCESS_TOKEN) val accessToken: String,
    @SerializedName(JSON_FIELD_KEY_REFRESH_TOKEN) val refreshToken: String,
    val accessTokenExpirationTime: Long = (System.currentTimeMillis() + (accessTokenTtl * 1000L)) // in milliseconds
) {

    companion object {

        private const val JSON_FIELD_KEY_TOKEN_TYPE = "token_type"
        private const val JSON_FIELD_KEY_ACCESS_TOKEN_TTL = "expires_in"
        private const val JSON_FIELD_KEY_ACCESS_TOKEN = "access_token"
        private const val JSON_FIELD_KEY_REFRESH_TOKEN = "refresh_token"


        /**
         * Tries to create a new instance object by fetching all necessary
         * data fields from passed json object.
         *
         * @param jsonObject The JSON object holding our data fields
         *
         * @return The new instacne or null if the JSON object does not have
         * the fields that are needed by the instance
         */
        fun newInstance(jsonObject: JsonObject): OAuthCredentials? {
            val hasTokenType = jsonObject.has(JSON_FIELD_KEY_TOKEN_TYPE)
            val hasAccessTokenTtl = jsonObject.has(JSON_FIELD_KEY_ACCESS_TOKEN_TTL)
            val hasAccessToken = jsonObject.has(JSON_FIELD_KEY_ACCESS_TOKEN)
            val hasRefreshToken = jsonObject.has(JSON_FIELD_KEY_REFRESH_TOKEN)

            return if(hasTokenType && hasAccessTokenTtl && hasAccessToken && hasRefreshToken) {
                OAuthCredentials(
                    tokenType = jsonObject.get(JSON_FIELD_KEY_TOKEN_TYPE).asString,
                    accessTokenTtl = jsonObject.get(JSON_FIELD_KEY_ACCESS_TOKEN_TTL).asLong,
                    accessToken = jsonObject.get(JSON_FIELD_KEY_ACCESS_TOKEN).asString,
                    refreshToken = jsonObject.get(JSON_FIELD_KEY_REFRESH_TOKEN).asString
                )
            } else {
                null
            }
        }

    }


    /**
     * A field that returns a boolean flag indicating
     * whether the access token is expired or not.
     */
    val isAccessTokenExpired: Boolean
        get() = (System.currentTimeMillis() >= accessTokenExpirationTime)


    /**
     * A field that returns the authorization header string.
     */
    val authorizationHeader: String
        get() = "$tokenType $accessToken"


}
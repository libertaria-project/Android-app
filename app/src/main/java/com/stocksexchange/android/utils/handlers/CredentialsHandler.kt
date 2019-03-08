package com.stocksexchange.android.utils.handlers

import com.stocksexchange.android.api.model.newapi.OAuthCredentials
import com.stocksexchange.android.model.Credentials

/**
 * A handler used for dealing with user's credentials.
 */
class CredentialsHandler(private val preferences: PreferenceHandler) {


    /**
     * Saves a public key.
     *
     * @param key The key to save
     */
    fun savePublicKey(key: String) = preferences.savePublicAuthKey(key)


    /**
     * Gets a public key.
     *
     * @return The public key
     */
    fun getPublicKey(): String = preferences.getPublicAuthKey()


    /**
     * Removes a public key.
     */
    fun removePublicKey() = preferences.removePublicAuthKey()


    /**
     * Saves a secret key.
     *
     * @param key The key to save
     */
    fun saveSecretKey(key: String) = preferences.saveSecretAuthKey(key)


    /**
     * Gets a secret key.
     *
     * @return The secret key
     */
    fun getSecretKey(): String = preferences.getSecretAuthKey()


    /**
     * Removes a secret key.
     */
    fun removeSecretKey() = preferences.removeSecretAuthKey()


    /**
     * Save public and secret keys.
     *
     * @param credentials The credentials which we want to save
     */
    fun saveKeys(credentials: Credentials) {
        savePublicKey(credentials.publicKey)
        saveSecretKey(credentials.secretKey)
    }


    /**
     * Clears public and secret keys.
     */
    fun clearKeys() {
        removePublicKey()
        removeSecretKey()
    }


    /**
     * Save OAuth credentials.
     *
     * @param credentials The credentials to save
     */
    fun saveCredentials(credentials: OAuthCredentials) {
        with(preferences) {
            saveTokenType(credentials.tokenType)
            saveAccessTokenTtl(credentials.accessTokenTtl)
            saveAccessToken(credentials.accessToken)
            saveRefreshToken(credentials.refreshToken)
            saveAccessTokenExpirationTime(credentials.accessTokenExpirationTime)
        }
    }


    /**
     * Retrieves OAuth credentials.
     *
     * @return The OAuth credentials
     */
    fun getCredentials(): OAuthCredentials {
        return OAuthCredentials(
            tokenType = preferences.getTokenType(),
            accessTokenTtl = preferences.getAccessTokenTtl(),
            accessToken = preferences.getAccessToken(),
            refreshToken = preferences.getRefreshToken(),
            accessTokenExpirationTime = preferences.getAccessTokenExpirationTime()
        )
    }


    /**
     * Clears OAuth credentials.
     */
    fun clearCredentials() {
        with(preferences) {
            removeTokenType()
            removeAccessTokenTtl()
            removeAccessToken()
            removeRefreshToken()
            removeAccessTokenExpirationTime()
        }
    }


    /**
     * Checks whether there are user credentials.
     *
     * @return true if exists; false otherwise
     */
    fun hasCredentials() : Boolean {
        return (preferences.hasPublicAuthKey() && preferences.hasSecretAuthKey())
    }


    /**
     * Checks whether there are OAuth credentials.
     *
     * @return true if exists; false otherwise
     */
    fun hasOAuthCredentials(): Boolean {
        return (preferences.hasTokenType() &&
                preferences.hasAccessTokenTtl() &&
                preferences.hasAccessToken() &&
                preferences.hasRefreshToken() &&
                preferences.hasAccessTokenExpirationTime())
    }


}
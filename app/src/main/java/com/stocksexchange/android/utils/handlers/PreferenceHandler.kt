package com.stocksexchange.android.utils.handlers

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * A helper class used for providing preferences functionality.
 */
class PreferenceHandler(context: Context) {


    companion object {

        private const val KEY_PUBLIC_KEY = "public_key"
        private const val KEY_SECRET_KEY = "secret_key"
        private const val KEY_USER_NAME = "user_name"

        private const val KEY_TOKEN_TYPE = "token_type"
        private const val KEY_ACCESS_TOKEN_TTL = "access_token_ttl"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_ACCESS_TOKEN_EXPIRATION_TIME = "access_token_expiration_time"

        private const val KEY_ARE_FINGERPRINT_ATTEMPTS_USED_UP = "are_fingerprint_attempts_used_up"
        private const val KEY_LAST_AUTH_TIMESTAMP = "last_auth_timestamp"
        private const val KEY_INVALID_PIN_CODES_ATTEMPTS_NUMBER = "invalid_pin_codes_attempts_number"
        private const val KEY_ALLOW_AUTH_TIMESTAMP = "allow_auth_timestamp"

    }


    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)




    /**
     * Saves a public authentication key.
     *
     * @param key The key to save
     */
    fun savePublicAuthKey(key: String) {
        put(KEY_PUBLIC_KEY, key)
    }


    /**
     * Removes a public authentication key.
     */
    fun removePublicAuthKey() {
        remove(KEY_PUBLIC_KEY)
    }


    /**
     * Gets a public authentication key.
     *
     * @return The public authentication key
     */
    fun getPublicAuthKey(): String {
        return get(KEY_PUBLIC_KEY, "")
    }


    /**
     * Checks if the public authentication key exists.
     *
     * @return true if exists; false otherwise
     */
    fun hasPublicAuthKey(): Boolean {
        return getPublicAuthKey().isNotBlank()
    }


    /**
     * Saves a secret authentication key.
     *
     * @param key The key to save
     */
    fun saveSecretAuthKey(key: String) {
        put(KEY_SECRET_KEY, key)
    }


    /**
     * Removes a secret authentication key.
     */
    fun removeSecretAuthKey() {
        remove(KEY_SECRET_KEY)
    }


    /**
     * Gets a secret authentication key.
     *
     * @return The secret authentication key
     */
    fun getSecretAuthKey(): String {
        return get(KEY_SECRET_KEY, "")
    }


    /**
     * Checks if the secret authentication key exists.
     *
     * @return true if exists; false otherwise
     */
    fun hasSecretAuthKey(): Boolean {
        return getSecretAuthKey().isNotBlank()
    }


    /**
     * Saves a user name.
     *
     * @param userName The user name to save
     */
    fun saveUserName(userName: String) {
        put(KEY_USER_NAME, userName)
    }


    /**
     * Removes a user name.
     */
    fun removeUserName() {
        remove(KEY_USER_NAME)
    }


    /**
     * Gets a user name.
     *
     * @return The user name
     */
    fun getUserName(): String {
        return get(KEY_USER_NAME, "")
    }


    /**
     * Checks if the user name key exists.
     *
     * @return true if exists; false otherwise
     */
    fun hasUserName(): Boolean {
        return getUserName().isNotBlank()
    }


    /**
     * Saves a token type.
     *
     * @param tokenType The token type to save
     */
    fun saveTokenType(tokenType: String) {
        put(KEY_TOKEN_TYPE, tokenType)
    }


    /**
     * Removes a token type.
     */
    fun removeTokenType() {
        remove(KEY_TOKEN_TYPE)
    }


    /**
     * Gets a token type.
     */
    fun getTokenType(): String {
        return get(KEY_TOKEN_TYPE, "")
    }


    /**
     * Checks if the token type key exists.
     *
     * @return true if exists; false otherwise
     */
    fun hasTokenType(): Boolean {
        return getTokenType().isNotBlank()
    }


    /**
     * Saves an access token TTL (time-to-live).
     *
     * @param accessTokenTtl The access token TTL to save
     */
    fun saveAccessTokenTtl(accessTokenTtl: Long) {
        put(KEY_ACCESS_TOKEN_TTL, accessTokenTtl)
    }


    /**
     * Removes an access token TTL.
     */
    fun removeAccessTokenTtl() {
        remove(KEY_ACCESS_TOKEN_TTL)
    }


    /**
     * Gets an access token TTl.
     */
    fun getAccessTokenTtl(): Long {
        return get(KEY_ACCESS_TOKEN_TTL, 0L)
    }


    /**
     * Checks if access token TTL key exists.
     *
     * @return true if exists; false otherwise
     */
    fun hasAccessTokenTtl(): Boolean {
        return (getAccessTokenTtl() > 0L)
    }


    /**
     * Saves an access token.
     *
     * @param accessToken The access token to save
     */
    fun saveAccessToken(accessToken: String) {
        put(KEY_ACCESS_TOKEN, accessToken)
    }


    /**
     * Removes an access token.
     */
    fun removeAccessToken() {
        remove(KEY_ACCESS_TOKEN)
    }


    /**
     * Gets an access token.
     */
    fun getAccessToken(): String {
        return get(KEY_ACCESS_TOKEN, "")
    }


    /**
     * Check if access token key exists.
     *
     * @return true if exists; false otherwise
     */
    fun hasAccessToken(): Boolean {
        return getAccessToken().isNotBlank()
    }


    /**
     * Saves a refresh token.
     *
     * @param refreshToken The refresh token to save
     */
    fun saveRefreshToken(refreshToken: String) {
        put(KEY_REFRESH_TOKEN, refreshToken)
    }


    /**
     * Removes a refresh token.
     */
    fun removeRefreshToken() {
        remove(KEY_REFRESH_TOKEN)
    }


    /**
     * Gets a refresh token.
     */
    fun getRefreshToken(): String {
        return get(KEY_REFRESH_TOKEN, "")
    }


    /**
     * Check if refresh token exists.
     *
     * @return true if exists; false otherwise
     */
    fun hasRefreshToken(): Boolean {
        return getRefreshToken().isNotBlank()
    }


    /**
     * Saves an access token expiration time.
     *
     * @param accessTokenExpirationTime The access token expiration time to save
     */
    fun saveAccessTokenExpirationTime(accessTokenExpirationTime: Long) {
        put(KEY_ACCESS_TOKEN_EXPIRATION_TIME, accessTokenExpirationTime)
    }


    /**
     * Removes an access token expiration time.
     */
    fun removeAccessTokenExpirationTime() {
        remove(KEY_ACCESS_TOKEN_EXPIRATION_TIME)
    }


    /**
     * Gets an access token expiration time.
     */
    fun getAccessTokenExpirationTime(): Long {
        return get(KEY_ACCESS_TOKEN_EXPIRATION_TIME, 0L)
    }


    /**
     * Check if access token expiration time key exists.
     *
     * @return true if exists; false otherwise
     */
    fun hasAccessTokenExpirationTime(): Boolean {
        return (getAccessTokenExpirationTime() >= 0L)
    }


    /**
     * Saves a flag signifying whether fingerprint attempts are used up.
     *
     * @param areFingerprintAttemptsUsedUp Whether the fingerprint attempts are used up or not
     */
    fun saveFingerprintAttemptsUsedUp(areFingerprintAttemptsUsedUp: String) {
        put(KEY_ARE_FINGERPRINT_ATTEMPTS_USED_UP, areFingerprintAttemptsUsedUp)
    }


    /**
     * Removes a flag signifying whether fingerprint attempts are used up.
     */
    fun removeFingerprintAttemptsUsedUp() {
        remove(KEY_ARE_FINGERPRINT_ATTEMPTS_USED_UP)
    }


    /**
     * Gets a flag signifying whether fingerprint attempts are used up.
     *
     * @return true if used up; false otherwise
     */
    fun areFingerprintAttemptsUsedUp(): String {
        return get(KEY_ARE_FINGERPRINT_ATTEMPTS_USED_UP, "")
    }


    /**
     * Checks if the flag signifying whether fingerprint attempts are used up exists.
     *
     * @return true if exists; false otherwise
     */
    fun hasFingerprintAttemptsUsedUp(): Boolean {
        return areFingerprintAttemptsUsedUp().isNotBlank()
    }


    /**
     * Saves a last authentication timestamp.
     *
     * @param lastAuthTimestamp The last authentication timestamp to save
     */
    fun saveLastAuthTimestamp(lastAuthTimestamp: String) {
        put(KEY_LAST_AUTH_TIMESTAMP, lastAuthTimestamp)
    }


    /**
     * Removes a last authentication timestamp.
     */
    fun removeLastAuthTimestamp() {
        remove(KEY_LAST_AUTH_TIMESTAMP)
    }


    /**
     * Gets a last authentication timestamp.
     *
     * @return The last authentication timestamp
     */
    fun getLastAuthTimestamp(): String {
        return get(KEY_LAST_AUTH_TIMESTAMP, "")
    }


    /**
     * Checks if the last authentication timestamp key exists.
     *
     * @return true if exists; false otherwise
     */
    fun hasLastAuthTimestamp(): Boolean {
        return getLastAuthTimestamp().isNotBlank()
    }


    /**
     * Saves an invalid pin code attempts number.
     *
     * @param invalidPinCodeAttemptsNumber The invalid pin code attempts number to save
     */
    fun saveInvalidPinCodeAttemptsNumber(invalidPinCodeAttemptsNumber: String) {
        put(KEY_INVALID_PIN_CODES_ATTEMPTS_NUMBER, invalidPinCodeAttemptsNumber)
    }


    /**
     * Removes an invalid authentication attempts number.
     */
    fun removeInvalidPinCodeAttemptsNumber() {
        remove(KEY_INVALID_PIN_CODES_ATTEMPTS_NUMBER)
    }


    /**
     * Gets an invalid authentication attempts number.
     *
     * @return The invalid authentication attempts number
     */
    fun getInvalidPinCodeAttemptsNumber(): String {
        return get(KEY_INVALID_PIN_CODES_ATTEMPTS_NUMBER, "")
    }


    /**
     * Checks if the invalid authentication attempts number key exists.
     *
     * @return true if exists; false otherwise
     */
    fun hasInvalidPinCodeAttemptsNumber(): Boolean {
        return getInvalidPinCodeAttemptsNumber().isNotBlank()
    }


    /**
     * Saves an allow authentication timestamp.
     *
     * @param allowAuthTimestamp The timestamp of when to allow user to authenticate
     */
    fun saveAllowAuthTimestamp(allowAuthTimestamp: String) {
        put(KEY_ALLOW_AUTH_TIMESTAMP, allowAuthTimestamp)
    }


    /**
     * Removes a timestamp of when to allow user to authenticate
     */
    fun removeAllowAuthTimestamp() {
        remove(KEY_ALLOW_AUTH_TIMESTAMP)
    }


    /**
     * Gets a timestamp of when to allow user to authenticate.
     *
     * @return The timestamp of when to allow user to authenticate
     */
    fun getAllowAuthTimestamp(): String {
        return get(KEY_ALLOW_AUTH_TIMESTAMP, "")
    }


    /**
     * Checks if the timestamp of when to allow user to authenticate key exists.
     *
     * @return true if exists; false otherwise
     */
    fun hasAllowAuthTime(): Boolean {
        return getAllowAuthTimestamp().isNotBlank()
    }


    /**
     * Clears all user related keys.
     */
    fun clearUserKeys() {
        removePublicAuthKey()
        removeSecretAuthKey()
        removeUserName()
        removeTokenType()
        removeAccessTokenTtl()
        removeAccessToken()
        removeRefreshToken()
        removeAccessTokenExpirationTime()
        removeFingerprintAttemptsUsedUp()
        removeLastAuthTimestamp()
        removeInvalidPinCodeAttemptsNumber()
        removeAllowAuthTimestamp()
    }


    fun put(key: String, value: Any) {
        put(key to value)
    }


    /**
     * Puts pairs of data inside shared preferences.
     *
     * @param pairs The pairs of data to put
     */
    fun put(vararg pairs: Pair<String, *>) {
        sharedPreferences.edit {
            pairs.forEach { put(it) }
        }
    }


    private fun SharedPreferences.Editor.put(pair: Pair<String, *>) {
        val (key, data) = pair

        when (data) {
            is Boolean -> putBoolean(key, data)
            is Int -> putInt(key, data)
            is Long -> putLong(key, data)
            is Float -> putFloat(key, data)
            is String -> putString(key, data)

            else -> throw IllegalArgumentException("Class ${data?.let { it::class }} is not supported")
        }
    }


    /**
     * Removes data from the preferences specified by the passed in keys.
     *
     * @param keys The keys of data to remove
     */
    fun remove(vararg keys: String) {
        sharedPreferences.edit {
            keys.forEach { remove(it) }
        }
    }


    /**
     * Gets data from the shared preferences.
     *
     * @param key The key to get the data for
     * @param defaultValue The default value to return
     * if the key is absent in the preferences
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(key: String, defaultValue: T) : T {
        return with(sharedPreferences) {
            when(defaultValue) {
                is Int -> getInt(key, defaultValue) as T
                is Long -> getLong(key, defaultValue) as T
                is Float -> getFloat(key, defaultValue) as T
                is String -> getString(key, defaultValue) as T
                is Boolean -> getBoolean(key, defaultValue) as T

                else -> throw IllegalArgumentException("Class ${defaultValue.let { it::class }} is not supported")
            }
        }
    }


    private fun SharedPreferences.edit(editAction: SharedPreferences.Editor.() -> Unit) {
        val editor = edit()
        editor.editAction()
        editor.apply()
    }


}
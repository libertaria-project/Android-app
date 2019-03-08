package com.stocksexchange.android.api.model.newapi

/**
 * A wrapper model class used for encapsulating data
 * returned from the server when trying to confirm
 * a login.
 */
data class SignInConfirmationResponse(
    val oauthCredentials: OAuthCredentials? = null,
    val confirmation: SignInConfirmation? = null
) {


    /**
     * A field that returns a boolean value indicating
     * whether this instance has OAuth credentials or not.
     */
    val hasOauthCredentials: Boolean
        get() = (oauthCredentials != null)


    /**
     * A field that returns a boolean value indicating
     * whether this instance has confirmation or not.
     */
    val hasConfirmation: Boolean
        get() = (confirmation != null)


    /**
     * A field that returns a boolean value indicating
     * whether this instance has some kind of data or not.
     */
    val hasData: Boolean
        get() = (hasOauthCredentials || hasConfirmation)


}
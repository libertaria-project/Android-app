package com.stocksexchange.android.model

/**
 * A model class containing keys for authenticating requests.
 */
data class Credentials(
    val publicKey: String,
    val secretKey: String
)
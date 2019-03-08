package com.stocksexchange.android.api.utils

import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.security.MessageDigest
import java.util.Random
import java.util.Formatter
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


private const val DEFAULT_CHARSET = "UTF-8"
private const val DEFAULT_HASH_ALGORITHM = "HmacSHA512"


/**
 * Generates a signature for the authenticated request.
 *
 * @param secretKey The secret key to sign the signature with
 * @param params The params for the request
 *
 * @return The generated signature
 */
fun generateSignature(secretKey: String, params: Map<String, String?>): String {
    return hash(secretKey, convertParamsToString(params))
}


/**
 * Converts parameters to a string in a key-value form (e.g., key1=value1&key2=value2).
 *
 * @param params The params for the request
 *
 * @return The string in a key-value form
 */
private fun convertParamsToString(params: Map<String, String?>): String {
    val stringBuilder = StringBuilder()
    var shouldPrependAmpersand = false

    params.forEach { (key, value) ->
        if((value != null) && value.isNotBlank()) {
            if(shouldPrependAmpersand) {
                stringBuilder.append("&")
            }

            stringBuilder
                .append(encode(key.toLowerCase()))
                .append("=")
                .append(encode(value))

            shouldPrependAmpersand = true
        }
    }

    return stringBuilder.toString()
}


/**
 * Encodes data in a URL friendly way.
 *
 * @param data The data to encode
 *
 * @return The encoded data
 */
private fun encode(data: String): String {
    if(data.isBlank()) {
        return data
    }

    return try {
        URLEncoder.encode(data, DEFAULT_CHARSET)
    } catch(exception: UnsupportedEncodingException) {
        ""
    }
}


/**
 * Generates a new nonce (random string).
 *
 * @return The nonce used for request signing
 */
fun generateNonce(): String {
    val randomizer = Random()
    val min = 1
    val max = 9
    val limit = (max - min + 1)
    var nonce = ""

    for(i in min..max) {
        nonce = "$nonce${randomizer.nextInt(limit) + min}"
    }

    return nonce
}


/**
 * Hashes the request body with HMAC-SHA512 hash with a secret key.
 *
 * @param secretKey The secret key to hash with
 * @param requestBody The body to hash
 *
 * @return The HMAC-SHA512 hash of the request body
 */
private fun hash(secretKey: String, requestBody: String): String {
    return try {
        val secretKeySpec = SecretKeySpec(
            secretKey.toByteArray(charset(DEFAULT_CHARSET)),
            DEFAULT_HASH_ALGORITHM
        )

        val mac = Mac.getInstance(DEFAULT_HASH_ALGORITHM)
        mac.init(secretKeySpec)

        val digest = mac.doFinal(requestBody.toByteArray(charset(DEFAULT_CHARSET)))

        return toHexString(digest)

    } catch(exception: Exception) {
        ""
    }
}


/**
 * Hashes the text using a SHA-1 function.
 *
 * @param text The text to hash
 *
 * @return The SHA-1 hash of the text
 */
fun hashSha1(text: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-1")
    val resultBytes = messageDigest.digest(text.toByteArray())

    return toHexString(resultBytes)
}


/**
 * Converts bytes to a hexadecimal string representation.
 *
 * @param bytes The bytes to convert
 *
 * @return The hexadecimal string of the bytes
 */
private fun toHexString(bytes: ByteArray): String {
    val formatter = Formatter()

    for(b in bytes) {
        formatter.format("%02x", b)
    }

    return formatter.toString()
}
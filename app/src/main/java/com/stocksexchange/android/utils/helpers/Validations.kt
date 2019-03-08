package com.stocksexchange.android.utils.helpers

import android.util.Patterns

/**
 * Checks whether the email is valid or not.
 *
 * @param email The email to check
 *
 * @return true if valid; false otherwise
 */
fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}


/**
 * Checks whether the email is invalid or not.
 *
 * @param email The email to check
 *
 * @return true if invalid; false otherwise
 */
fun isEmailInvalid(email: String): Boolean {
    return !isEmailValid(email)
}
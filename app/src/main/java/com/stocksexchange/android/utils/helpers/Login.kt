package com.stocksexchange.android.utils.helpers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.ui.login.LoginActivity
import com.stocksexchange.android.utils.handlers.CredentialsHandler

/**
 * Checks whether the user should login. If the user
 * is not signed in, starts [LoginActivity] and returns true.
 * If the user is signed in, returns false.
 *
 * @return true if not signed in; false otherwise
 */
fun loginIfNecessary(activity: AppCompatActivity,
                     destinationIntent: Intent,
                     credentialsHandler: CredentialsHandler): Boolean {
    return if(!credentialsHandler.hasCredentials()) {
        activity.startActivity(LoginActivity.newInstance(
            activity,
            TransitionAnimations.KITKAT_SCALING_ANIMATIONS,
            destinationIntent
        ))
        activity.finish()

        true
    } else {
        false
    }
}
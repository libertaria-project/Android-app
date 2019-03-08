package com.stocksexchange.android.utils.handlers

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.stocksexchange.android.R
import com.stocksexchange.android.ui.utils.extensions.canIntentBeHandled
import org.jetbrains.anko.toast

/**
 * A handler used for providing email-related functionality.
 */
class EmailHandler {


    /**
     * Sends an email.
     *
     * @param activity The activity to start an email client of
     * @param emailAddress The target address of the email
     * @param subject The subject of the email
     * @param text The text of the email
     * @param requestCode The request code of the email sending intent
     */
    fun sendEmail(activity: AppCompatActivity, emailAddress: String,
                  subject: String, text: String, requestCode: Int) {
        val intent = Intent(
            Intent.ACTION_SENDTO,
            Uri.fromParts("mailto", emailAddress, null)
        )

        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text)

        if(activity.canIntentBeHandled(intent)) {
            activity.startActivityForResult(intent, requestCode)
        } else {
            activity.toast(activity.getString(
                R.string.error_no_email_client_template,
                activity.getString(R.string.error_unable_to_proceed)
            ))
        }
    }


}
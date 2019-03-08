package com.stocksexchange.android.utils.helpers

import android.app.Notification
import android.content.Context
import com.stocksexchange.android.ui.utils.extensions.getCompatNotificationManager


private val DEFAULT_VIBRATION_PATTERN = longArrayOf(0L, 300L, 100L, 300L)


/**
 * Shows a notification in the notifications panel.
 *
 * @param context The context
 * @param id The id of the notification
 * @param notification The notification itself
 */
@Synchronized
fun show(context: Context, id: Int, notification: Notification) {
    context.getCompatNotificationManager().notify(id, notification)
}


/**
 * Dismisses a notification specified by id.
 *
 * @param context The context
 * @param id The id of the notification
 */
@Synchronized
fun dismiss(context: Context, id: Int) {
    context.getCompatNotificationManager().cancel(id)
}
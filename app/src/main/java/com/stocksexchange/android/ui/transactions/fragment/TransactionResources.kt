package com.stocksexchange.android.ui.transactions.fragment

import android.content.Context
import com.arthurivanets.adapster.markers.ItemResources
import com.stocksexchange.android.R
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.model.TransactionTypes
import com.stocksexchange.android.ui.utils.extensions.getLocale
import com.stocksexchange.android.utils.formatters.DoubleFormatter
import com.stocksexchange.android.utils.formatters.TimeFormatter

class TransactionResources(
    val strings: List<String>,
    val timeFormatter: TimeFormatter,
    val doubleFormatter: DoubleFormatter,
    val settings: Settings
) : ItemResources {


    companion object {

        const val STRING_FINISHED = 0
        const val STRING_AWAITING_CONFIRMATIONS = 1
        const val STRING_EMAIL_SENT = 2
        const val STRING_CANCELLED_BY_USER = 3
        const val STRING_AWAITING_APPROVAL = 4
        const val STRING_APPROVED = 5
        const val STRING_NOT_CONFIRMED = 6
        const val STRING_PROCESSING = 7
        const val STRING_WITHDRAWAL_ERROR = 8
        const val STRING_CANCELLED_BY_ADMIN = 9


        fun newInstance(context: Context, settings: Settings): TransactionResources {
            val strings = listOf(
                context.getString(R.string.transaction_status_finished),
                context.getString(R.string.transaction_status_awaiting_confirmations),
                context.getString(R.string.transaction_status_email_sent),
                context.getString(R.string.transaction_status_cancelled_by_user),
                context.getString(R.string.transaction_status_awaiting_approval),
                context.getString(R.string.transaction_status_approved),
                context.getString(R.string.transaction_status_not_confirmed),
                context.getString(R.string.transaction_status_processing),
                context.getString(R.string.transaction_status_withdrawal_error),
                context.getString(R.string.transaction_status_cancelled_by_admin)
            )

            val timeFormatter = TimeFormatter.getInstance(context)
            val doubleFormatter = DoubleFormatter.getInstance(context.getLocale())

            return TransactionResources(
                strings,
                timeFormatter,
                doubleFormatter,
                settings
            )
        }

    }


    var transactionType: TransactionTypes = TransactionTypes.DEPOSITS


}
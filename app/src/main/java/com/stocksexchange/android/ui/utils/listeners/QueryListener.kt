package com.stocksexchange.android.ui.utils.listeners

import android.text.Editable
import android.text.TextWatcher

/**
 * A ready to use listener to supply to EditText to get notified
 * about major EditText's events.
 */
class QueryListener(private val callback: Callback) : TextWatcher {


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Stub
    }


    override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
        if(text.isNotEmpty()) {
            callback.onQueryEntered(text.toString())
        } else {
            callback.onQueryRemoved()
        }
    }


    override fun afterTextChanged(s: Editable?) {
        // Stub
    }


    /**
     * A helper interface to get notified whenever a query is entered
     * or is removed.
     */
    interface Callback {

        /**
         * Gets called whenever a character is entered.
         *
         * @param query The whole query entered
         */
        fun onQueryEntered(query: String) {
            // Stub
        }

        /**
         * Gets called whenever a query is removed.
         */
        fun onQueryRemoved() {
            // Stub
        }

    }


}
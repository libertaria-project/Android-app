package com.stocksexchange.android.ui.utils.extensions

import android.os.Parcelable
import android.view.View
import com.stocksexchange.android.Constants.AT_LEAST_MARSHMALLOW

/**
 * Fetches the state from the parcelable.
 *
 * @return The proper state of this parcelable
 */
fun Parcelable?.fetchProperState(): Parcelable? {
    if(this == null) {
        return null
    }

    return if(AT_LEAST_MARSHMALLOW) {
        this
    } else {
        View.BaseSavedState.EMPTY_STATE
    }
}
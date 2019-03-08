package com.stocksexchange.android.ui.utils.extensions

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

/**
 * Disables change animations for the [RecyclerView].
 */
fun RecyclerView.disableChangeAnimations() {
    if(itemAnimator is DefaultItemAnimator) {
        (itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
    }
}

/**
 * Disables all animations for the [RecyclerView].
 */
fun RecyclerView.disableAnimations() {
    if (itemAnimator != null) {
        itemAnimator = null
    }
}
package com.stocksexchange.android.ui.base.mvp.presenters

import com.stocksexchange.android.utils.SavedState

/**
 * A base interface for defining a presenter.
 */
interface Presenter {

    fun start()

    fun stop()

    fun onRestoreState(savedState: SavedState)

    fun onSaveState(savedState: SavedState)

}
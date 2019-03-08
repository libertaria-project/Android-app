package com.stocksexchange.android.ui.base.mvp.model

import com.stocksexchange.android.utils.SavedState
import org.koin.standalone.KoinComponent

/**
 * A base interface for defining a model to
 * associate with a presenter.
 */
interface Model : KoinComponent {

    fun start()

    fun stop()

    fun onRestoreState(savedState: SavedState)

    fun onSaveState(savedState: SavedState)

}
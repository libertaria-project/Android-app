package com.stocksexchange.android.ui.base.mvp.model

import com.stocksexchange.android.utils.SavedState

/**
 * A [Model] implementation with stub methods.
 * Primarily used as a default model class.
 */
open class StubModel : Model {


    override fun start() {
        // Stub
    }


    override fun stop() {
        // Stub
    }


    override fun onRestoreState(savedState: SavedState) {
        // Stub
    }


    override fun onSaveState(savedState: SavedState) {
        // Stub
    }


}
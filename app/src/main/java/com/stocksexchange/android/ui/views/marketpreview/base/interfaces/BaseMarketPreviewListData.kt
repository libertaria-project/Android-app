package com.stocksexchange.android.ui.views.marketpreview.base.interfaces

/**
 * An interface to implement for base market preview
 * list data classes that are highlightable.
 */
interface BaseMarketPreviewListData {


    companion object {

        const val NO_TIMESTAMP = -1L

    }


    /**
     * A timestamp holding a timestamp of when the highlighting
     * should be stopped.
     */
    val highlightEndTimestamp: Long





    /**
     * Checks whether this preview data item should be highlighted or not.
     *
     * @return true if should; false otherwise
     */
    fun shouldBeHighlighted(): Boolean {
        return ((highlightEndTimestamp != NO_TIMESTAMP) && (getHighlightDuration() > 0L))
    }


    /**
     * Calculates a highlight duration.
     *
     * @return The highlight duration
     */
    fun getHighlightDuration(): Long {
        return (highlightEndTimestamp - System.currentTimeMillis())
    }


}
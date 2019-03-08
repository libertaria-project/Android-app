package com.stocksexchange.android.ui.utils.interfaces

/**
 * An interface to implement to mark a class to be selectable.
 */
interface Selectable {


    /**
     * This method is called to notify you that the
     * object has been selected.
     */
    fun onSelected()


    /**
     * This method is called to notify you that the
     * object has been unselected.
     */
    fun onUnselected()


}
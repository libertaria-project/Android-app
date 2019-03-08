package com.stocksexchange.android.ui.utils.interfaces

/**
 * An interface to implement to mark a class to be themable.
 */
interface Themable<in T> {


    /**
     * This method is called whenever a theme has to be
     * applied to the object.
     *
     * @param theme The actual theme model object
     */
    fun applyTheme(theme: T)


}
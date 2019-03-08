package com.stocksexchange.android.model

/**
 * An enumeration of all possible states a help item can be in.
 */
enum class HelpItemModelState {


    COLLAPSED,
    EXPANDED;


    /**
     * Returns an opposite state.
     *
     * @return The opposite help item state
     */
    operator fun not(): HelpItemModelState {
        return when(this) {
            COLLAPSED -> EXPANDED
            EXPANDED -> COLLAPSED
        }
    }


}
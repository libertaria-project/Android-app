package com.stocksexchange.android.model

import com.stocksexchange.android.ui.help.HelpItem
import java.io.Serializable

/**
 * A model class holding data for [HelpItem] class.
 */
data class HelpItemModel(
    val id: Int,
    val questionText: String,
    val answerText: String,
    val buttonText: String? = null,
    var state: HelpItemModelState = HelpItemModelState.COLLAPSED
) : Serializable {


    /**
     * Checks whether the button text is present.
     *
     * @return true if present; false otherwise
     */
    fun hasButtonText(): Boolean {
        return (buttonText?.isNotEmpty() ?: false)
    }


    /**
     * Checks whether the item is in [HelpItemModelState.COLLAPSED] state.
     *
     * @return true if in collapsed state; false otherwise
     */
    fun isCollapsed() = (state == HelpItemModelState.COLLAPSED)


    /**
     * Checks whether the item is in [HelpItemModelState.EXPANDED] state.
     *
     * @return true if in expanded state; false otherwise
     */
    fun isExpanded() = (state == HelpItemModelState.EXPANDED)


    /**
     * Toggles the state of this help item.
     */
    fun toggleState() {
        state = !state
    }


}
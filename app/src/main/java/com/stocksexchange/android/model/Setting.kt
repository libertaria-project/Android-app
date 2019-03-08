package com.stocksexchange.android.model

import java.io.Serializable

/**
 * A model class holding data for a single setting.
 */
data class Setting(
    var id: Int = -1,
    var isEnabled: Boolean = true,
    var isCheckable: Boolean = false,
    var isChecked: Boolean = false,
    var title: String = "",
    var description: String = "",
    var tag: Any? = null
) : Serializable {


    /**
     * Checks whether this setting has a description.
     *
     * @return true if has; false otherwise
     */
    fun hasDescription(): Boolean {
        return description.isNotBlank()
    }


}
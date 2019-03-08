package com.stocksexchange.android.theming.model

import com.stocksexchange.android.ui.views.LabeledEditText
import java.io.Serializable

/**
 * A model class containing [LabeledEditText] related colors.
 */
data class LabeledEditTextTheme(
    val titleColor: Int,
    val containerColor: Int,
    val hintColor: Int,
    val textColor: Int,
    val cursorColor: Int,
    val labelColor: Int
) : Serializable
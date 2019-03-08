package com.stocksexchange.android.theming.model

import java.io.Serializable

/**
 * A model class containing dialog related colors.
 */
data class DialogTheme(
    val backgroundColor: Int,
    val titleColor: Int,
    val textColor: Int,
    val buttonColor: Int
) : Serializable
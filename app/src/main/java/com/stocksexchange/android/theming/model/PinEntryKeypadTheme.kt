package com.stocksexchange.android.theming.model

import com.stocksexchange.android.ui.views.PinEntryKeypad
import java.io.Serializable

/**
 * A model class containing [PinEntryKeypad] colors.
 */
data class PinEntryKeypadTheme(
    val digitButtonTextColor: Int,
    val enabledButtonBackgroundColor: Int,
    val disabledButtonBackgroundColor: Int,
    val enabledFingerprintButtonForegroundColor: Int,
    val enabledDeleteButtonForegroundColor: Int,
    val disabledActionButtonForegroundColor: Int
) : Serializable
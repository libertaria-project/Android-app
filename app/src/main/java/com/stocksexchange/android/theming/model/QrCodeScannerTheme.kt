package com.stocksexchange.android.theming.model

import com.stocksexchange.android.ui.views.qrcodescanner.QrCodeScanner
import java.io.Serializable

/**
 * A model class containing [QrCodeScanner] related colors.
 */
data class QrCodeScannerTheme(
    val borderColor: Int,
    val tintColor: Int
) : Serializable
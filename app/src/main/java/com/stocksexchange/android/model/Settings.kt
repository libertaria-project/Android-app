package com.stocksexchange.android.model

import com.stocksexchange.android.theming.model.Theme

/**
 * A model class holding app's settings.
 */
data class Settings(
    val id: Int,
    val isSoundEnabled: Boolean,
    val isVibrationEnabled: Boolean,
    val isPhoneLedEnabled: Boolean,
    val isGroupingEnabled: Boolean,
    val isFingerprintUnlockEnabled: Boolean,
    val isForceAuthenticationOnAppStartupEnabled: Boolean,
    val isRealTimeDataStreamingEnabled: Boolean,
    val isOrderbookRealTimeUpdatesHighlightingEnabled: Boolean,
    val isNewTradesRealTimeAdditionHighlightingEnabled: Boolean,
    val isPriceChartZoomInEnabled: Boolean,
    val shouldAnimateCharts: Boolean,
    val notificationRingtone: String,
    val pinCode: PinCode,
    val bullishCandleStickStyle: CandleStickStyles,
    val bearishCandleStickStyle: CandleStickStyles,
    val depthChartLineStyle: DepthChartLineStyles,
    val decimalSeparator: DecimalSeparators,
    val groupingSeparator: GroupingSeparators,
    val authenticationSessionDuration: AuthenticationSessionDurations,
    val theme: Theme
) {


    companion object {

        const val SETTINGS_ID = 1

    }




    /**
     * Checks whether an orderbook highlighting is enabled or not.
     *
     * @return true if enabled; false otherwise
     */
    fun isOrderbookHighlightingEnabled(): Boolean {
        return (isRealTimeDataStreamingEnabled && isOrderbookRealTimeUpdatesHighlightingEnabled)
    }


    /**
     * Checks whether new trades highlighting is enabled or not.
     *
     * @return true if enabled; false otherwise
     */
    fun isNewTradesHighlightingEnabled(): Boolean {
        return (isRealTimeDataStreamingEnabled && isNewTradesRealTimeAdditionHighlightingEnabled)
    }


    /**
     * Checks whether the pin code is present.
     *
     * @return true if present; false otherwise
     */
    fun hasPinCode(): Boolean {
        return pinCode.hasCode()
    }


}
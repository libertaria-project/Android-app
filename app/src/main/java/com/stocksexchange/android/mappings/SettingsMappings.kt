package com.stocksexchange.android.mappings

import com.stocksexchange.android.database.model.DatabaseSettings
import com.stocksexchange.android.model.Settings

fun Settings.mapToDatabaseSettings(): DatabaseSettings {
    return DatabaseSettings(
        id = id,
        isSoundEnabled = isSoundEnabled,
        isVibrationEnabled = isVibrationEnabled,
        isPhoneLedEnabled = isPhoneLedEnabled,
        isGroupingEnabled = isGroupingEnabled,
        isFingerprintUnlockEnabled = isFingerprintUnlockEnabled,
        isForceAuthenticationOnAppStartupEnabled = isForceAuthenticationOnAppStartupEnabled,
        isRealTimeDataStreamingEnabled = isRealTimeDataStreamingEnabled,
        isOrderbookRealTimeUpdatesHighlightingEnabled = isOrderbookRealTimeUpdatesHighlightingEnabled,
        isNewTradesRealTimeAdditionHighlightingEnabled = isNewTradesRealTimeAdditionHighlightingEnabled,
        isPriceChartZoomInEnabled = isPriceChartZoomInEnabled,
        shouldAnimateCharts = shouldAnimateCharts,
        notificationRingtone = notificationRingtone,
        pinCode = pinCode,
        bullishCandleStickStyle = bullishCandleStickStyle,
        bearishCandleStickStyle = bearishCandleStickStyle,
        depthChartLineStyle = depthChartLineStyle,
        decimalSeparator = decimalSeparator,
        groupingSeparator = groupingSeparator,
        authenticationSessionDuration = authenticationSessionDuration,
        theme = theme
    )
}


fun DatabaseSettings.mapToSettings(): Settings {
    return Settings(
        id = id,
        isSoundEnabled = isSoundEnabled,
        isVibrationEnabled = isVibrationEnabled,
        isPhoneLedEnabled = isPhoneLedEnabled,
        isGroupingEnabled = isGroupingEnabled,
        isFingerprintUnlockEnabled = isFingerprintUnlockEnabled,
        isForceAuthenticationOnAppStartupEnabled = isForceAuthenticationOnAppStartupEnabled,
        isRealTimeDataStreamingEnabled = isRealTimeDataStreamingEnabled,
        isOrderbookRealTimeUpdatesHighlightingEnabled = isOrderbookRealTimeUpdatesHighlightingEnabled,
        isNewTradesRealTimeAdditionHighlightingEnabled = isNewTradesRealTimeAdditionHighlightingEnabled,
        isPriceChartZoomInEnabled = isPriceChartZoomInEnabled,
        shouldAnimateCharts = shouldAnimateCharts,
        notificationRingtone = notificationRingtone,
        pinCode = pinCode,
        bullishCandleStickStyle = bullishCandleStickStyle,
        bearishCandleStickStyle = bearishCandleStickStyle,
        depthChartLineStyle = depthChartLineStyle,
        decimalSeparator = decimalSeparator,
        groupingSeparator = groupingSeparator,
        authenticationSessionDuration = authenticationSessionDuration,
        theme = theme
    )
}
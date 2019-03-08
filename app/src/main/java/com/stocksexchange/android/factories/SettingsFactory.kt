package com.stocksexchange.android.factories

import com.stocksexchange.android.factories.base.BaseFactory
import com.stocksexchange.android.model.*
import com.stocksexchange.android.model.Settings.Companion.SETTINGS_ID
import com.stocksexchange.android.theming.factories.ThemeFactory
import com.stocksexchange.android.utils.helpers.getDefaultNotificationRingtone

/**
 * A factory producing instances of [Settings] class.
 */
object SettingsFactory : BaseFactory() {


    /**
     * Returns default settings.
     *
     * @return The default settings
     */
    fun getDefaultSettings(): Settings {
        return Settings(
            id = SETTINGS_ID,
            isSoundEnabled = true,
            isVibrationEnabled = true,
            isPhoneLedEnabled = true,
            isGroupingEnabled = true,
            isFingerprintUnlockEnabled = false,
            isForceAuthenticationOnAppStartupEnabled = false,
            isRealTimeDataStreamingEnabled = true,
            isOrderbookRealTimeUpdatesHighlightingEnabled = true,
            isNewTradesRealTimeAdditionHighlightingEnabled = true,
            isPriceChartZoomInEnabled = true,
            shouldAnimateCharts = true,
            pinCode = PinCode.getEmptyPinCode(),
            notificationRingtone = getDefaultNotificationRingtone(),
            bullishCandleStickStyle = CandleStickStyles.SOLID,
            bearishCandleStickStyle = CandleStickStyles.SOLID,
            depthChartLineStyle = DepthChartLineStyles.LINEAR,
            decimalSeparator = DecimalSeparators.PERIOD,
            groupingSeparator = GroupingSeparators.COMMA,
            authenticationSessionDuration = AuthenticationSessionDurations.FIVE_MINUTES,
            theme = ThemeFactory.getDefaultTheme()
        )
    }


}
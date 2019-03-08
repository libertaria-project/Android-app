package com.stocksexchange.android.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stocksexchange.android.database.model.DatabaseSettings.Companion.TABLE_NAME
import com.stocksexchange.android.model.*
import com.stocksexchange.android.theming.factories.ThemeFactory
import com.stocksexchange.android.theming.model.Theme

/**
 * A Room database model class of [Settings] class.
 */
@Entity(tableName = TABLE_NAME)
data class DatabaseSettings(
    @PrimaryKey @ColumnInfo(name = ID) var id: Int,
    @ColumnInfo(name = IS_SOUND_ENABLED) var isSoundEnabled: Boolean,
    @ColumnInfo(name = IS_VIBRATION_ENABLED) var isVibrationEnabled: Boolean,
    @ColumnInfo(name = IS_PHONE_LED_ENABLED) var isPhoneLedEnabled: Boolean,
    @ColumnInfo(name = IS_GROUPING_ENABLED) var isGroupingEnabled: Boolean,
    @ColumnInfo(name = IS_FINGERPRINT_UNLOCK_ENABLED) var isFingerprintUnlockEnabled: Boolean,
    @ColumnInfo(name = IS_FORCE_AUTHENTICATION_ON_APP_STARTUP_IS_ENABLED) var isForceAuthenticationOnAppStartupEnabled: Boolean,
    @ColumnInfo(name = IS_REAL_TIME_DATA_STREAMING_ENABLED) var isRealTimeDataStreamingEnabled: Boolean,
    @ColumnInfo(name = IS_ORDERBOOK_REAL_TIME_UPDATES_HIGHLIGHTING_ENABLED) var isOrderbookRealTimeUpdatesHighlightingEnabled: Boolean,
    @ColumnInfo(name = IS_NEW_TRADES_REAL_TIME_ADDITION_HIGHLIGHTING_ENABLED) var isNewTradesRealTimeAdditionHighlightingEnabled: Boolean,
    @ColumnInfo(name = IS_PRICE_CHART_ZOOM_IN_ENABLED) var isPriceChartZoomInEnabled: Boolean,
    @ColumnInfo(name = SHOULD_ANIMATE_CHARTS) var shouldAnimateCharts: Boolean,
    @ColumnInfo(name = NOTIFICATION_RINGTONE) var notificationRingtone: String,
    @ColumnInfo(name = PIN_CODE) var pinCode: PinCode,
    @ColumnInfo(name = BULLISH_CANDLE_STICK_STYLE) var bullishCandleStickStyle: CandleStickStyles,
    @ColumnInfo(name = BEARISH_CANDLE_STICK_STYLE) var bearishCandleStickStyle: CandleStickStyles,
    @ColumnInfo(name = DEPTH_CHART_LINE_STYLE) var depthChartLineStyle: DepthChartLineStyles,
    @ColumnInfo(name = DECIMAL_SEPARATOR) var decimalSeparator: DecimalSeparators,
    @ColumnInfo(name = GROUPING_SEPARATOR) var groupingSeparator: GroupingSeparators,
    @ColumnInfo(name = AUTHENTICATION_SESSION_DURATION) var authenticationSessionDuration: AuthenticationSessionDurations,
    @ColumnInfo(name = THEME_ID) var theme: Theme
) {

    companion object {

        const val TABLE_NAME = "settings"

        const val ID = "id"
        const val IS_SOUND_ENABLED = "is_sound_enabled"
        const val IS_VIBRATION_ENABLED = "is_vibration_enabled"
        const val IS_PHONE_LED_ENABLED = "is_phone_led_enabled"
        const val IS_GROUPING_ENABLED = "is_grouping_enabled"
        const val IS_FINGERPRINT_UNLOCK_ENABLED = "is_fingerprint_unlock_enabled"
        const val IS_FORCE_AUTHENTICATION_ON_APP_STARTUP_IS_ENABLED = "is_force_authentication_on_app_startup_is_enabled"
        const val IS_REAL_TIME_DATA_STREAMING_ENABLED = "is_real_time_data_streaming_enabled"
        const val IS_ORDERBOOK_REAL_TIME_UPDATES_HIGHLIGHTING_ENABLED = "is_orderbook_real_time_updates_highlighting_enabled"
        const val IS_NEW_TRADES_REAL_TIME_ADDITION_HIGHLIGHTING_ENABLED = "is_new_trades_real_time_addition_highlighting_enabled"
        const val IS_PRICE_CHART_ZOOM_IN_ENABLED = "is_price_chart_zoom_in_enabled"
        const val SHOULD_ANIMATE_CHARTS = "should_animate_charts"
        const val NOTIFICATION_RINGTONE = "notification_ringtone"
        const val PIN_CODE = "pin_code"
        const val BULLISH_CANDLE_STICK_STYLE = "bullish_candle_stick_style"
        const val BEARISH_CANDLE_STICK_STYLE = "bearish_candle_stick_style"
        const val DEPTH_CHART_LINE_STYLE = "depth_chart_line_style"
        const val DECIMAL_SEPARATOR = "decimal_separator"
        const val GROUPING_SEPARATOR = "grouping_separator"
        const val AUTHENTICATION_SESSION_DURATION = "authentication_session_duration"
        const val THEME_ID = "theme_id"

    }


    constructor(): this(
        -1,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        "",
        PinCode.getEmptyPinCode(),
        CandleStickStyles.SOLID,
        CandleStickStyles.SOLID,
        DepthChartLineStyles.LINEAR,
        DecimalSeparators.PERIOD,
        GroupingSeparators.COMMA,
        AuthenticationSessionDurations.FIVE_MINUTES,
        ThemeFactory.getStubTheme()
    )

}
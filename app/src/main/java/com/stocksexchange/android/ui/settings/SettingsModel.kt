package com.stocksexchange.android.ui.settings

import com.stocksexchange.android.BuildConfig
import com.stocksexchange.android.Constants.AT_LEAST_OREO
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.model.*
import com.stocksexchange.android.repositories.settings.SettingsRepository
import com.stocksexchange.android.ui.base.mvp.model.BaseModel
import com.stocksexchange.android.utils.handlers.UserDataClearingHandler
import com.stocksexchange.android.utils.providers.FingerprintProvider
import com.stocksexchange.android.utils.providers.RingtoneProvider
import com.stocksexchange.android.utils.providers.StringProvider
import org.koin.standalone.inject
import java.lang.IllegalStateException

class SettingsModel : BaseModel() {


    companion object {

        const val SETTING_ID_CHANGE_PIN = 0
        const val SETTING_ID_AUTHENTICATION_SESSION_DURATION = 1
        const val SETTING_ID_FINGERPRINT_UNLOCK = 2
        const val SETTING_ID_FORCE_AUTHENTICATION_ON_APP_STARTUP = 3
        const val SETTING_ID_SIGN_OUT = 4
        const val SETTING_ID_RESTORE_DEFAULTS = 5
        const val SETTING_ID_STREAM_REAL_TIME_DATA = 6
        const val SETTING_ID_THEME = 7
        const val SETTING_ID_ANIMATE_CHARTS = 8
        const val SETTING_ID_BULLISH_CANDLE_STICK_STYLE = 9
        const val SETTING_ID_BEARISH_CANDLE_STICK_STYLE = 10
        const val SETTING_ID_ZOOM_IN_ON_PRICE_CHART = 11
        const val SETTING_ID_DEPTH_CHART_LINE_STYLE = 12
        const val SETTING_ID_HIGHLIGHT_ORDERBOOK_REAL_TIME_UPDATES = 13
        const val SETTING_ID_HIGHLIGHT_NEW_TRADES_REAL_TIME_ADDITION = 14
        const val SETTING_ID_IS_GROUPING_ENABLED = 15
        const val SETTING_ID_GROUPING_SEPARATOR = 16
        const val SETTING_ID_DECIMAL_SEPARATOR = 17
        const val SETTING_ID_IS_SOUND_ENABLED = 18
        const val SETTING_ID_IS_VIBRATION_ENABLED = 19
        const val SETTING_ID_IS_PHONE_LED_ENABLED = 20
        const val SETTING_ID_NOTIFICATION_RINGTONE = 21
        const val SETTING_ID_DEVICE_METRICS = 22

    }


    private val mSettingsRepository: SettingsRepository by inject()
    private val mStringProvider: StringProvider by inject()
    private val mRingtoneProvider: RingtoneProvider by inject()
    private val mFingerprintProvider: FingerprintProvider by inject()
    private val mUserDataClearingHandler: UserDataClearingHandler by inject()




    fun getSettingItems(settings: Settings, user: User?): MutableList<Any> {
        val items: MutableList<Any> = mutableListOf()

        with(items) {
            if(user != null) {
                // Account section
                add(SettingSection(mStringProvider.getString(R.string.account)))
                add(getItemForId(SETTING_ID_CHANGE_PIN, settings))
                add(getItemForId(SETTING_ID_AUTHENTICATION_SESSION_DURATION, settings))

                if(mFingerprintProvider.isHardwareAvailable()) {
                    add(getItemForId(SETTING_ID_FINGERPRINT_UNLOCK, settings))
                }

                add(getItemForId(SETTING_ID_FORCE_AUTHENTICATION_ON_APP_STARTUP, settings))
                add(getItemForId(SETTING_ID_SIGN_OUT, settings, user))
            }

            // General section
            add(SettingSection(mStringProvider.getString(R.string.general)))
            add(getItemForId(SETTING_ID_RESTORE_DEFAULTS, settings))

            // Data Streaming section
            add(SettingSection(mStringProvider.getString(R.string.data_streaming)))
            add(getItemForId(SETTING_ID_STREAM_REAL_TIME_DATA, settings))

            // Appearance
            add(SettingSection(mStringProvider.getString(R.string.appearance)))
            add(getItemForId(SETTING_ID_THEME, settings))
            add(getItemForId(SETTING_ID_ANIMATE_CHARTS, settings))
            add(getItemForId(SETTING_ID_BULLISH_CANDLE_STICK_STYLE, settings))
            add(getItemForId(SETTING_ID_BEARISH_CANDLE_STICK_STYLE, settings))
            add(getItemForId(SETTING_ID_ZOOM_IN_ON_PRICE_CHART, settings))
            add(getItemForId(SETTING_ID_DEPTH_CHART_LINE_STYLE, settings))
            add(getItemForId(SETTING_ID_HIGHLIGHT_ORDERBOOK_REAL_TIME_UPDATES, settings))
            add(getItemForId(SETTING_ID_HIGHLIGHT_NEW_TRADES_REAL_TIME_ADDITION, settings))

            // Formatting section
            add(SettingSection(mStringProvider.getString(R.string.formatting)))
            add(getItemForId(SETTING_ID_IS_GROUPING_ENABLED, settings))
            add(getItemForId(SETTING_ID_GROUPING_SEPARATOR, settings))
            add(getItemForId(SETTING_ID_DECIMAL_SEPARATOR, settings))

            // Notifications section
            if(!AT_LEAST_OREO) {
                add(SettingSection(mStringProvider.getString(R.string.notifications)))
                add(getItemForId(SETTING_ID_IS_SOUND_ENABLED, settings))
                add(getItemForId(SETTING_ID_IS_VIBRATION_ENABLED, settings))
                add(getItemForId(SETTING_ID_IS_PHONE_LED_ENABLED, settings))
                add(getItemForId(SETTING_ID_NOTIFICATION_RINGTONE, settings))
            }

            // Debug section
            if(BuildConfig.DEBUG) {
                add(SettingSection(mStringProvider.getString(R.string.debug)))
                add(getItemForId(SETTING_ID_DEVICE_METRICS, settings))
            }
        }

        return items
    }


    fun updateSettings(settings: Settings) {
        createUiLaunchCoroutine {
            mSettingsRepository.save(settings)
        }
    }


    fun clearUserData(onFinish: (() -> Unit)? = null) {
        createUiLaunchCoroutine {
            mUserDataClearingHandler.clearUserData(onFinish)
        }
    }


    fun getItemForId(id: Int, settings: Settings, user: User? = null): Setting {
        return when(id) {

            SETTING_ID_CHANGE_PIN -> {
                Setting(
                    id = SETTING_ID_CHANGE_PIN,
                    title = mStringProvider.getString(R.string.change_pin),
                    description = mStringProvider.getString(R.string.settings_activity_change_pin_description)
                )
            }

            SETTING_ID_AUTHENTICATION_SESSION_DURATION -> {
                Setting(
                    id = SETTING_ID_AUTHENTICATION_SESSION_DURATION,
                    title = mStringProvider.getString(R.string.authentication_session_duration),
                    description = getAuthenticationSessionDurationDescription(settings.authenticationSessionDuration)
                )
            }

            SETTING_ID_FINGERPRINT_UNLOCK -> {
                Setting(
                    id = SETTING_ID_FINGERPRINT_UNLOCK,
                    isCheckable = true,
                    isChecked = settings.isFingerprintUnlockEnabled,
                    title = mStringProvider.getString(R.string.fingerprint_unlock),
                    description = mStringProvider.getString(R.string.settings_activity_fingerprint_unlock_description)
                )
            }

            SETTING_ID_FORCE_AUTHENTICATION_ON_APP_STARTUP -> {
                Setting(
                    id = SETTING_ID_FORCE_AUTHENTICATION_ON_APP_STARTUP,
                    isCheckable = true,
                    isChecked = settings.isForceAuthenticationOnAppStartupEnabled,
                    title = mStringProvider.getString(R.string.force_authentication_on_app_startup),
                    description = mStringProvider.getString(R.string.settings_activity_force_authentication_description)
                )
            }

            SETTING_ID_SIGN_OUT -> {
                Setting(
                    id = SETTING_ID_SIGN_OUT,
                    title = mStringProvider.getString(R.string.action_sign_out),
                    description = mStringProvider.getString(R.string.sign_out_template, user!!.userName)
                )
            }

            SETTING_ID_RESTORE_DEFAULTS -> {
                Setting(
                    id = SETTING_ID_RESTORE_DEFAULTS,
                    title = mStringProvider.getString(R.string.restore_defaults),
                    description = mStringProvider.getString(R.string.settings_activity_restore_defaults_description)
                )
            }

            SETTING_ID_STREAM_REAL_TIME_DATA -> {
                Setting(
                    id = SETTING_ID_STREAM_REAL_TIME_DATA,
                    isCheckable = true,
                    isChecked = settings.isRealTimeDataStreamingEnabled,
                    title = mStringProvider.getString(R.string.stream_real_time_data),
                    description = mStringProvider.getString(R.string.settings_activity_stream_real_time_data_description)
                )
            }

            SETTING_ID_THEME -> {
                Setting(
                    id = SETTING_ID_THEME,
                    title = mStringProvider.getString(R.string.theme),
                    description = settings.theme.name
                )
            }

            SETTING_ID_ANIMATE_CHARTS -> {
                Setting(
                    id = SETTING_ID_ANIMATE_CHARTS,
                    isCheckable = true,
                    isChecked = settings.shouldAnimateCharts,
                    title = mStringProvider.getString(R.string.animate_charts),
                    description = mStringProvider.getString(R.string.settings_activity_animate_charts_description)
                )
            }

            SETTING_ID_BULLISH_CANDLE_STICK_STYLE -> {
                Setting(
                    id = SETTING_ID_BULLISH_CANDLE_STICK_STYLE,
                    title = mStringProvider.getString(R.string.bullish_candle_stick_style),
                    description = getCandleStickStyleDescription(settings.bullishCandleStickStyle),
                    tag = CandleStickTypes.BULLISH
                )
            }

            SETTING_ID_BEARISH_CANDLE_STICK_STYLE -> {
                Setting(
                    id = SETTING_ID_BEARISH_CANDLE_STICK_STYLE,
                    title = mStringProvider.getString(R.string.bearish_candle_stick_style),
                    description = getCandleStickStyleDescription(settings.bearishCandleStickStyle),
                    tag = CandleStickTypes.BEARISH
                )
            }

            SETTING_ID_ZOOM_IN_ON_PRICE_CHART -> {
                Setting(
                    id = SETTING_ID_ZOOM_IN_ON_PRICE_CHART,
                    isCheckable = true,
                    isChecked = settings.isPriceChartZoomInEnabled,
                    title = mStringProvider.getString(R.string.zoom_in_on_the_price_chart),
                    description = mStringProvider.getString(R.string.settings_activity_zoom_in_on_the_price_chart_description)
                )
            }

            SETTING_ID_DEPTH_CHART_LINE_STYLE -> {
                Setting(
                    id = SETTING_ID_DEPTH_CHART_LINE_STYLE,
                    title = mStringProvider.getString(R.string.depth_chart_line_style),
                    description = getDepthChartLineStyleDescription(settings.depthChartLineStyle)
                )
            }

            SETTING_ID_HIGHLIGHT_ORDERBOOK_REAL_TIME_UPDATES -> {
                Setting(
                    id = SETTING_ID_HIGHLIGHT_ORDERBOOK_REAL_TIME_UPDATES,
                    isEnabled = settings.isRealTimeDataStreamingEnabled,
                    isCheckable = true,
                    isChecked = settings.isOrderbookRealTimeUpdatesHighlightingEnabled,
                    title = mStringProvider.getString(R.string.highlight_orderbook_real_time_updates),
                    description = mStringProvider.getString(R.string.settings_activity_highlight_orderbook_real_time_updates_description)
                )
            }

            SETTING_ID_HIGHLIGHT_NEW_TRADES_REAL_TIME_ADDITION -> {
                Setting(
                    id = SETTING_ID_HIGHLIGHT_NEW_TRADES_REAL_TIME_ADDITION,
                    isEnabled = settings.isRealTimeDataStreamingEnabled,
                    isCheckable = true,
                    isChecked = settings.isNewTradesRealTimeAdditionHighlightingEnabled,
                    title = mStringProvider.getString(R.string.highlight_new_trades_real_time_addition),
                    description = mStringProvider.getString(R.string.settings_activity_highlight_new_trades_real_time_addition_description)
                )
            }

            SETTING_ID_IS_GROUPING_ENABLED -> {
                Setting(
                    id = SETTING_ID_IS_GROUPING_ENABLED,
                    isCheckable = true,
                    isChecked = settings.isGroupingEnabled,
                    title = mStringProvider.getString(R.string.grouping),
                    description = mStringProvider.getString(R.string.settings_activity_grouping_section_description)
                )
            }

            SETTING_ID_GROUPING_SEPARATOR -> {
                Setting(
                    id = SETTING_ID_GROUPING_SEPARATOR,
                    isEnabled = settings.isGroupingEnabled,
                    title = mStringProvider.getString(R.string.grouping_separator),
                    description = getGroupingSeparatorDescription(settings.groupingSeparator)
                )
            }

            SETTING_ID_DECIMAL_SEPARATOR -> {
                Setting(
                    id = SETTING_ID_DECIMAL_SEPARATOR,
                    title = mStringProvider.getString(R.string.decimal_separator),
                    description = getDecimalSeparatorDescription(settings.decimalSeparator)
                )
            }

            SETTING_ID_IS_SOUND_ENABLED -> {
                Setting(
                    id = SETTING_ID_IS_SOUND_ENABLED,
                    isCheckable = true,
                    isChecked = settings.isSoundEnabled,
                    title = mStringProvider.getString(R.string.sounds)
                )
            }

            SETTING_ID_IS_VIBRATION_ENABLED -> {
                Setting(
                    id = SETTING_ID_IS_VIBRATION_ENABLED,
                    isCheckable = true,
                    isChecked = settings.isVibrationEnabled,
                    title = mStringProvider.getString(R.string.vibration)
                )
            }

            SETTING_ID_IS_PHONE_LED_ENABLED -> {
                Setting(
                    id = SETTING_ID_IS_PHONE_LED_ENABLED,
                    isCheckable = true,
                    isChecked = settings.isPhoneLedEnabled,
                    title = mStringProvider.getString(R.string.phone_led)
                )
            }

            SETTING_ID_NOTIFICATION_RINGTONE -> {
                Setting(
                    id = SETTING_ID_NOTIFICATION_RINGTONE,
                    title = mStringProvider.getString(R.string.notification_ringtone),
                    description = mRingtoneProvider.getRingtoneName(settings.notificationRingtone)
                )
            }

            SETTING_ID_DEVICE_METRICS -> {
                Setting(
                    id = SETTING_ID_DEVICE_METRICS,
                    title = mStringProvider.getString(R.string.device_metrics),
                    description = mStringProvider.getString(R.string.device_metrics_description)
                )
            }

            else -> throw IllegalStateException("Please provide an implementation for id \"$id\".")
        }
    }


    private fun getAuthenticationSessionDurationDescription(authenticationSessionDuration: AuthenticationSessionDurations): String {
        return mStringProvider.getString(when(authenticationSessionDuration) {
            AuthenticationSessionDurations.FIFTEEN_SECONDS -> R.string.time_period_fifteen_seconds
            AuthenticationSessionDurations.FIVE_MINUTES -> R.string.time_period_five_minutes
            AuthenticationSessionDurations.TEN_MINUTES -> R.string.time_period_ten_minutes
            AuthenticationSessionDurations.FIFTEEN_MINUTES -> R.string.time_period_fifteen_minutes
            AuthenticationSessionDurations.THIRTY_MINUTES -> R.string.time_period_thirty_minutes
            AuthenticationSessionDurations.FORTY_FIVE_MINUTES -> R.string.time_period_forty_five_minutes
            AuthenticationSessionDurations.ONE_HOUR -> R.string.time_period_one_hour
            AuthenticationSessionDurations.TWO_HOURS -> R.string.time_period_two_hours
            AuthenticationSessionDurations.THREE_HOURS -> R.string.time_period_three_hours
            AuthenticationSessionDurations.SIX_HOURS -> R.string.time_period_six_hours
            AuthenticationSessionDurations.NINE_HOURS -> R.string.time_period_nine_hours
            AuthenticationSessionDurations.TWELVE_HOURS -> R.string.time_period_twelve_hours
        })
    }


    /**
     * Returns a description for a specific candle stick style.
     *
     * @param style The candle stick type to return the description for
     *
     * @return The description for the candle stick style
     */
    fun getCandleStickStyleDescription(style: CandleStickStyles): String {
        return mStringProvider.getString(when(style) {
            CandleStickStyles.SOLID -> R.string.candle_stick_style_solid
            CandleStickStyles.HOLLOW -> R.string.candle_stick_style_hollow
        })
    }


    /**
     * Returns a description for a specific depth chart line style.
     *
     * @param style The depth chart line style to return the description for
     *
     * @return The description for the depth chart line style
     */
    fun getDepthChartLineStyleDescription(style: DepthChartLineStyles): String {
        return mStringProvider.getString(when(style) {
            DepthChartLineStyles.LINEAR -> R.string.depth_chart_line_style_linear
            DepthChartLineStyles.STEPPED -> R.string.depth_chart_line_style_stepped
        })
    }


    /**
     * Returns a description for a specific grouping separator.
     *
     * @param groupingSeparator The grouping separator to return the description for
     *
     * @return The description for the grouping separator
     */
    fun getGroupingSeparatorDescription(groupingSeparator: GroupingSeparators): String {
        return mStringProvider.getString(when(groupingSeparator) {
            GroupingSeparators.PERIOD -> R.string.grouping_separator_period
            GroupingSeparators.COMMA -> R.string.grouping_separator_comma
            GroupingSeparators.SPACE -> R.string.grouping_separator_space
        })
    }


    /**
     * Returns a description for a specific decimal separator.
     *
     * @param decimalSeparator The decimal separator to return the description for
     *
     * @return The description for the decimal separator
     */
    fun getDecimalSeparatorDescription(decimalSeparator: DecimalSeparators): String {
        return mStringProvider.getString(when(decimalSeparator) {
            DecimalSeparators.PERIOD -> R.string.decimal_separator_period
            DecimalSeparators.COMMA -> R.string.decimal_separator_comma
        })
    }


    /**
     * Returns a string notifying about the automatic change of the decimal separator.
     *
     * @param decimalSeparator The new decimal separator
     * @param groupingSeparator The newly selected grouping separator
     *
     * @return The string
     */
    fun getDecimalSeparatorAutomaticChangeString(decimalSeparator: DecimalSeparators,
                                                 groupingSeparator: GroupingSeparators): String {
        return mStringProvider.getString(
            R.string.settings_activity_decimal_separator_automatic_change,
            getAutomaticChangeStringFirstArg(decimalSeparator.separator),
            getAutomaticChangeStringSecondArg(groupingSeparator.separator)
        )
    }


    /**
     * Returns a string notifying about the automatic change of the grouping separator.
     *
     * @param groupingSeparator The new grouping separator
     * @param decimalSeparator The newly selected decimal separator
     *
     * @return The string
     */
    fun getGroupingSeparatorAutomaticChangeString(groupingSeparator: GroupingSeparators,
                                                  decimalSeparator: DecimalSeparators): String {
        return mStringProvider.getString(
            R.string.settings_activity_grouping_separator_automatic_change,
            getAutomaticChangeStringFirstArg(groupingSeparator.separator),
            getAutomaticChangeStringSecondArg(decimalSeparator.separator)
        )
    }


    private fun getAutomaticChangeStringFirstArg(separator: Char): String {
        return mStringProvider.getString(when(separator) {
            DecimalSeparators.PERIOD.separator -> R.string.settings_activity_automatic_change_first_arg_period
            DecimalSeparators.COMMA.separator -> R.string.settings_activity_automatic_change_first_arg_comma

            else -> throw IllegalStateException("Please provide an implementation for $separator")
        })
    }


    private fun getAutomaticChangeStringSecondArg(separator: Char): String {
        return mStringProvider.getString(when(separator) {
            DecimalSeparators.PERIOD.separator -> R.string.settings_activity_automatic_change_second_arg_period
            DecimalSeparators.COMMA.separator -> R.string.settings_activity_automatic_change_second_arg_comma

            else -> throw IllegalStateException("Please provide an implementation for $separator")
        })
    }


    private fun getGroupingSeparatorAutomaticChangeSecondArg(groupingSeparator: GroupingSeparators): String {
        return mStringProvider.getString(when(groupingSeparator) {
            GroupingSeparators.PERIOD -> R.string.settings_activity_automatic_change_second_arg_period
            GroupingSeparators.COMMA -> R.string.settings_activity_automatic_change_second_arg_comma

            else -> throw IllegalStateException("Please provide an implementation for $groupingSeparator")
        })
    }


    /**
     * Gets an instance of [CandleStickStyles] enumeration for its title.
     *
     * @param title The title to get an instance for
     *
     * @return An instance of [CandleStickStyles] class
     */
    fun getCandleStickStyleForTitleString(title: String): CandleStickStyles {
        return when(title) {
            mStringProvider.getString(R.string.candle_stick_style_solid) -> CandleStickStyles.SOLID
            mStringProvider.getString(R.string.candle_stick_style_hollow) -> CandleStickStyles.HOLLOW

            else -> throw IllegalStateException("Please provide an implementation for \"$title\" string.")
        }
    }


    /**
     * Gets an instance of [DepthChartLineStyles] enumeration for its title.
     *
     * @param title The title to get an instance for
     *
     * @return An instance of [DepthChartLineStyles] class
     */
    fun getDepthChartLineStyleForTitleString(title: String): DepthChartLineStyles {
        return when(title) {
            mStringProvider.getString(R.string.depth_chart_line_style_linear) -> DepthChartLineStyles.LINEAR
            mStringProvider.getString(R.string.depth_chart_line_style_stepped) -> DepthChartLineStyles.STEPPED

            else -> throw IllegalStateException("Please provide an implementation for \"$title\" string.")
        }
    }


    /**
     * Gets an instance of [GroupingSeparators] enumeration for its title.
     *
     * @param title The title to get an instance for
     *
     * @return An instance of [GroupingSeparators] class
     */
    fun getGroupingSeparatorForTitleString(title: String): GroupingSeparators {
        return when(title) {
            mStringProvider.getString(R.string.grouping_separator_period) -> GroupingSeparators.PERIOD
            mStringProvider.getString(R.string.grouping_separator_comma) -> GroupingSeparators.COMMA
            mStringProvider.getString(R.string.grouping_separator_space) -> GroupingSeparators.SPACE

            else -> throw IllegalStateException("Please provide an implementation for \"$title\" string.")
        }
    }


    /**
     * Gets an instance of [DecimalSeparators] enumeration for its title.
     *
     * @param title The title to get an instance for
     *
     * @return An instance of [DecimalSeparators] class
     */
    fun getDecimalSeparatorForTitleString(title: String): DecimalSeparators {
        return when(title) {
            mStringProvider.getString(R.string.decimal_separator_period) -> DecimalSeparators.PERIOD
            mStringProvider.getString(R.string.decimal_separator_comma) -> DecimalSeparators.COMMA

            else -> throw IllegalStateException("Please provide an implementation for \"$title\" string.")
        }
    }


    /**
     * Gets an instance of [AuthenticationSessionDurations] enumeration for its title.
     *
     * @param authenticationSessionDuration The timeout to get an instance for
     *
     * @return An instance of [AuthenticationSessionDurations] class
     */
    fun getAuthenticationSessionDurationTitleString(authenticationSessionDuration: String): AuthenticationSessionDurations {
        return when(authenticationSessionDuration) {
            mStringProvider.getString(R.string.time_period_fifteen_seconds) -> AuthenticationSessionDurations.FIFTEEN_SECONDS
            mStringProvider.getString(R.string.time_period_five_minutes) -> AuthenticationSessionDurations.FIVE_MINUTES
            mStringProvider.getString(R.string.time_period_ten_minutes) -> AuthenticationSessionDurations.TEN_MINUTES
            mStringProvider.getString(R.string.time_period_fifteen_minutes) -> AuthenticationSessionDurations.FIFTEEN_MINUTES
            mStringProvider.getString(R.string.time_period_thirty_minutes) -> AuthenticationSessionDurations.THIRTY_MINUTES
            mStringProvider.getString(R.string.time_period_forty_five_minutes) -> AuthenticationSessionDurations.FORTY_FIVE_MINUTES
            mStringProvider.getString(R.string.time_period_one_hour) -> AuthenticationSessionDurations.ONE_HOUR
            mStringProvider.getString(R.string.time_period_two_hours) -> AuthenticationSessionDurations.TWO_HOURS
            mStringProvider.getString(R.string.time_period_three_hours) -> AuthenticationSessionDurations.THREE_HOURS
            mStringProvider.getString(R.string.time_period_six_hours) -> AuthenticationSessionDurations.SIX_HOURS
            mStringProvider.getString(R.string.time_period_nine_hours) -> AuthenticationSessionDurations.NINE_HOURS
            mStringProvider.getString(R.string.time_period_twelve_hours) -> AuthenticationSessionDurations.TWELVE_HOURS

            else -> throw IllegalStateException("Please provide an implementation for \"$authenticationSessionDuration\" string.")
        }
    }


}
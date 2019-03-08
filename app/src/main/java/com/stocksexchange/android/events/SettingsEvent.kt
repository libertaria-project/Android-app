package com.stocksexchange.android.events

import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.utils.helpers.tag

/**
 * An event to send to notify subscribers about
 * [Settings] model class updates.
 */
class SettingsEvent private constructor(
    attachment: Settings,
    sourceTag: String,
    val action: Actions
) : BaseEvent<Settings>(TYPE_INVALID, attachment, sourceTag) {


    companion object {


        fun restoreDefaults(attachment: Settings, source: Any): SettingsEvent {
            return restoreDefaults(attachment, tag(source))
        }


        fun restoreDefaults(attachment: Settings, sourceTag: String): SettingsEvent {
            return SettingsEvent(attachment, sourceTag, Actions.DEFAULTS_RESTORED)
        }


        fun changeTheme(attachment: Settings, source: Any): SettingsEvent {
            return changeTheme(attachment, tag(source))
        }


        fun changeTheme(attachment: Settings, sourceTag: String): SettingsEvent {
            return SettingsEvent(attachment, sourceTag, Actions.THEME_CHANGED)
        }


        fun changeRealTimeDataStreamingState(attachment: Settings, source: Any): SettingsEvent {
            return changeRealTimeDataStreamingState(attachment, tag(source))
        }


        fun changeRealTimeDataStreamingState(attachment: Settings, sourceTag: String): SettingsEvent {
            return SettingsEvent(attachment, sourceTag, Actions.REAL_TIME_DATA_STREAMING_STATE_CHANGED)
        }


        fun changeGroupingState(attachment: Settings, source: Any): SettingsEvent {
            return changeGroupingState(attachment, tag(source))
        }


        fun changeGroupingState(attachment: Settings, sourceTag: String): SettingsEvent {
            return SettingsEvent(attachment, sourceTag, Actions.GROUPING_STATE_CHANGED)
        }


        fun changeGroupingSeparator(attachment: Settings, source: Any): SettingsEvent {
            return changeGroupingSeparator(attachment, tag(source))
        }


        fun changeGroupingSeparator(attachment: Settings, sourceTag: String): SettingsEvent {
            return SettingsEvent(attachment, sourceTag, Actions.GROUPING_SEPARATOR_CHANGED)
        }


        fun changeDecimalSeparator(attachment: Settings, source: Any): SettingsEvent {
            return changeDecimalSeparator(attachment, tag(source))
        }


        fun changeDecimalSeparator(attachment: Settings, sourceTag: String): SettingsEvent {
            return SettingsEvent(attachment, sourceTag, Actions.DECIMAL_SEPARATOR_CHANGED)
        }


    }


    enum class Actions {

        THEME_CHANGED,
        DEFAULTS_RESTORED,
        REAL_TIME_DATA_STREAMING_STATE_CHANGED,
        GROUPING_STATE_CHANGED,
        GROUPING_SEPARATOR_CHANGED,
        DECIMAL_SEPARATOR_CHANGED

    }


}
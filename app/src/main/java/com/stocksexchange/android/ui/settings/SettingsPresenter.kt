package com.stocksexchange.android.ui.settings

import com.stocksexchange.android.BuildConfig
import com.stocksexchange.android.R
import com.stocksexchange.android.events.SettingsEvent
import com.stocksexchange.android.events.UserEvent
import com.stocksexchange.android.factories.SettingsFactory
import com.stocksexchange.android.model.*
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.ui.base.mvp.presenters.BasePresenter
import com.stocksexchange.android.utils.formatters.DoubleFormatter
import com.stocksexchange.android.utils.providers.FingerprintProvider
import com.stocksexchange.android.utils.providers.StringProvider
import org.greenrobot.eventbus.EventBus
import org.koin.standalone.inject

class SettingsPresenter(
    model: SettingsModel,
    view: SettingsContract.View
) : BasePresenter<SettingsModel, SettingsContract.View>(model, view), SettingsContract.ActionListener {


    private val mStringProvider: StringProvider by inject()
    private val mFingerprintProvider: FingerprintProvider by inject()

    private val mSettingsFactory: SettingsFactory by inject()




    constructor(view: SettingsContract.View): this(SettingsModel(), view)


    override fun start() {
        super.start()

        if(mView.isDataSetEmpty()) {
            loadSettings()
        }
    }


    private fun loadSettings() {
        mView.setItems(mModel.getSettingItems(
            mView.getAppSettings(),
            mView.getUser()
        ))
    }


    private fun updateSettings(onFinish: ((Settings, Settings) -> Unit)? = null,
                               getNewSettings: (Settings) -> Settings) {
        val oldSettings = mView.getAppSettings()
        val newSettings = getNewSettings(oldSettings)

        mModel.updateSettings(newSettings)
        mView.updateSettings(newSettings)

        onFinish?.invoke(newSettings, oldSettings)
    }


    override fun stop() {
        super.stop()

        mView.hideMaterialDialog()
        mView.hideDeviceMetricsDialog()
        mView.hideFingerprintDialog()
    }


    override fun onSettingSwitchClicked(setting: Setting, isChecked: Boolean) {
        setting.isChecked = isChecked
        onSettingItemClicked(setting)
    }


    override fun onSettingItemClicked(setting: Setting) {
        when(setting.id) {

            SettingsModel.SETTING_ID_CHANGE_PIN -> onChangePinItemClicked()
            SettingsModel.SETTING_ID_FINGERPRINT_UNLOCK -> onFingerprintUnlockItemClicked(setting)
            SettingsModel.SETTING_ID_FORCE_AUTHENTICATION_ON_APP_STARTUP -> onForceAuthenticationOnAppStartItemClicked(setting)
            SettingsModel.SETTING_ID_AUTHENTICATION_SESSION_DURATION -> onAuthenticationSessionDurationItemClicked()
            SettingsModel.SETTING_ID_SIGN_OUT -> onSignOutItemClicked()
            SettingsModel.SETTING_ID_RESTORE_DEFAULTS -> onRestoreDefaultsItemClicked()
            SettingsModel.SETTING_ID_STREAM_REAL_TIME_DATA -> onStreamRealTimeDataItemClicked(setting)
            SettingsModel.SETTING_ID_THEME -> onThemeItemClicked()
            SettingsModel.SETTING_ID_ANIMATE_CHARTS -> onAnimateChartsItemClicked(setting)
            SettingsModel.SETTING_ID_BULLISH_CANDLE_STICK_STYLE,
            SettingsModel.SETTING_ID_BEARISH_CANDLE_STICK_STYLE -> onCandleStickStyleItemClicked(setting)
            SettingsModel.SETTING_ID_ZOOM_IN_ON_PRICE_CHART -> onZoomInOnPriceChartItemClicked(setting)
            SettingsModel.SETTING_ID_DEPTH_CHART_LINE_STYLE -> onDepthChartLineStyleItemClicked()
            SettingsModel.SETTING_ID_HIGHLIGHT_ORDERBOOK_REAL_TIME_UPDATES -> onHighlightOrderbookRealTimeUpdatesItemClicked(setting)
            SettingsModel.SETTING_ID_HIGHLIGHT_NEW_TRADES_REAL_TIME_ADDITION -> onHighlightNewTradesRealTimeAdditionItemClicked(setting)
            SettingsModel.SETTING_ID_IS_GROUPING_ENABLED -> onGroupingItemClicked(setting)
            SettingsModel.SETTING_ID_GROUPING_SEPARATOR -> onGroupingSeparatorItemClicked()
            SettingsModel.SETTING_ID_DECIMAL_SEPARATOR -> onDecimalSeparatorItemClicked()
            SettingsModel.SETTING_ID_IS_SOUND_ENABLED -> onSoundsItemClicked(setting)
            SettingsModel.SETTING_ID_IS_VIBRATION_ENABLED -> onVibrationItemClicked(setting)
            SettingsModel.SETTING_ID_IS_PHONE_LED_ENABLED -> onPhoneLedItemClicked(setting)
            SettingsModel.SETTING_ID_NOTIFICATION_RINGTONE -> onNotificationRingtoneItemClicked()
            SettingsModel.SETTING_ID_DEVICE_METRICS -> onDeviceMetricsClicked()

        }
    }


    override fun onChangePinItemClicked() {
        mView.launchPinCodeChangeActivity()
    }


    override fun onPinCodeChanged() {
        mView.showToast(mStringProvider.getString(R.string.pin_code_changed))
    }


    override fun onFingerprintUnlockItemClicked(setting: Setting) {
        if(setting.isChecked) {
            mView.showDisableFingerprintUnlockDialog()
        } else {
            if(!mFingerprintProvider.hasEnrolledFingerprints()) {
                mView.showNoFingerprintsAvailableDialog()
                return
            }

            mView.showFingerprintDialog()
        }
    }


    override fun onFingerprintDialogButtonClicked() {
        mView.hideFingerprintDialog()
    }


    override fun onFingerprintUnlockConfirmed() {
        updateFingerprintUnlockSetting(true)
    }


    override fun onRegisterFingerprintConfirmed() {
        mView.launchSecuritySettings()
    }


    override fun onDisableFingerprintUnlockConfirmed() {
        updateFingerprintUnlockSetting(false)
    }


    private fun updateFingerprintUnlockSetting(isFingerprintUnlockEnabled: Boolean) {
        val onFinish: ((Settings, Settings) -> Unit) = { newSettings, _ ->
            mView.updateSettingWith(mModel.getItemForId(
                SettingsModel.SETTING_ID_FINGERPRINT_UNLOCK,
                newSettings
            ))
        }

        updateSettings(onFinish) {
            it.copy(isFingerprintUnlockEnabled = isFingerprintUnlockEnabled)
        }
    }


    override fun onForceAuthenticationOnAppStartItemClicked(setting: Setting) {
        updateSettings {
            it.copy(isForceAuthenticationOnAppStartupEnabled = setting.isChecked)
        }
    }


    override fun onAuthenticationSessionDurationItemClicked() {
        var itemList = mStringProvider.getStringArray(
            R.array.authentication_session_durations
        ).asList()

        if(BuildConfig.DEBUG) {
            itemList = (listOf(mStringProvider.getString(R.string.time_period_fifteen_seconds)) + itemList)
        }

        mView.showAuthenticationSessionDurationsDialog(itemList)
    }


    override fun onAuthenticationSessionDurationItemPicked(authenticationSessionDuration: String) {
        val newAuthenticationSessionDuration = mModel.getAuthenticationSessionDurationTitleString(
            authenticationSessionDuration
        )
        val oldSettings = mView.getAppSettings()

        if(newAuthenticationSessionDuration == oldSettings.authenticationSessionDuration) {
            return
        }

        val onFinish: ((Settings, Settings) -> Unit) = { newSettings, _ ->
            mView.updateSettingWith(mModel.getItemForId(
                SettingsModel.SETTING_ID_AUTHENTICATION_SESSION_DURATION,
                newSettings
            ))
        }

        updateSettings(onFinish) {
            it.copy(authenticationSessionDuration = newAuthenticationSessionDuration)
        }
    }


    override fun onSignOutItemClicked() {
        mView.showSignOutConfirmationDialog()
    }


    override fun onSignOutConfirmed() {
        mView.showProgressBar()

        mModel.clearUserData {
            val onFinish: ((Settings, Settings) -> Unit) = { _, _ ->
                EventBus.getDefault().postSticky(UserEvent.signOut(this))

                mView.resetUser()
                mView.resetAppLockManager()
                mView.hideProgressBar()
                mView.finishActivity()
            }

            updateSettings(onFinish) {
                it.copy(
                    pinCode = PinCode.getEmptyPinCode(),
                    isFingerprintUnlockEnabled = false,
                    isForceAuthenticationOnAppStartupEnabled = false,
                    authenticationSessionDuration = AuthenticationSessionDurations.FIVE_MINUTES
                )
            }
        }
    }


    override fun onRestoreDefaultsItemClicked() {
        mView.showRestoreDefaultsConfirmationDialog()
    }


    override fun onRestoreDefaultsConfirmed() {
        val onFinish: ((Settings, Settings) -> Unit) = { newSettings, oldSettings ->
            val locale = mView.getLocale()

            DoubleFormatter.getInstance(locale).apply {
                setDecimalSeparator(newSettings.decimalSeparator.separator)
                setGroupingSeparator(newSettings.groupingSeparator.separator)
                setGroupingEnabled(newSettings.isGroupingEnabled)
            }

            mView.updateAdapterResources()

            if(oldSettings.theme.id != newSettings.theme.id) {
                mView.applyNewTheme(newSettings.theme)
            }

            loadSettings()

            EventBus.getDefault().postSticky(SettingsEvent.restoreDefaults(newSettings, this))
        }

        updateSettings(onFinish) {
            mSettingsFactory.getDefaultSettings().copy(pinCode = it.pinCode)
        }
    }


    override fun onStreamRealTimeDataItemClicked(setting: Setting) {
        val onFinish: ((Settings, Settings) -> Unit) = { newSettings, _ ->
            mView.updateSettingWith(mModel.getItemForId(
                SettingsModel.SETTING_ID_HIGHLIGHT_ORDERBOOK_REAL_TIME_UPDATES,
                newSettings
            ))
            mView.updateSettingWith(mModel.getItemForId(
                SettingsModel.SETTING_ID_HIGHLIGHT_NEW_TRADES_REAL_TIME_ADDITION,
                newSettings
            ))

            // No need to post sticky for now
            EventBus.getDefault().post(SettingsEvent.changeRealTimeDataStreamingState(
                newSettings,
                this
            ))
        }

        updateSettings(onFinish) {
            it.copy(isRealTimeDataStreamingEnabled = setting.isChecked)
        }
    }


    override fun onThemeItemClicked() {
        mView.launchThemesActivity()
    }


    override fun onThemePicked(theme: Theme) {
        val oldSettings = mView.getAppSettings()

        if(theme.id == oldSettings.theme.id) {
            return
        }

        val onFinish: ((Settings, Settings) -> Unit) = { newSettings, _ ->
            mView.updateSettingWith(mModel.getItemForId(SettingsModel.SETTING_ID_THEME, newSettings), false)
            mView.applyNewTheme(theme)
            mView.updateAdapterResources()
            mView.notifyDataSetChanged()

            EventBus.getDefault().postSticky(SettingsEvent.changeTheme(newSettings, this))
        }

        updateSettings(onFinish) {
            it.copy(theme = theme)
        }
    }


    override fun onAnimateChartsItemClicked(setting: Setting) {
        updateSettings {
            it.copy(shouldAnimateCharts = setting.isChecked)
        }
    }


    override fun onCandleStickStyleItemClicked(setting: Setting) {
        mView.showCandleStickStyleDialog(setting.tag as CandleStickTypes)
    }


    override fun onCandleStickStylePicked(style: String, type: CandleStickTypes) {
        val newCandleStickStyle = mModel.getCandleStickStyleForTitleString(style)
        val oldSettings = mView.getAppSettings()
        val oldCandleStickStyle = when(type) {
            CandleStickTypes.BULLISH -> oldSettings.bullishCandleStickStyle
            CandleStickTypes.BEARISH -> oldSettings.bearishCandleStickStyle
        }

        if(newCandleStickStyle == oldCandleStickStyle) {
            return
        }

        val onFinish: ((Settings, Settings) -> Unit) = { newSettings, _ ->
            mView.updateSettingWith(mModel.getItemForId(
                when(type) {
                    CandleStickTypes.BULLISH -> SettingsModel.SETTING_ID_BULLISH_CANDLE_STICK_STYLE
                    CandleStickTypes.BEARISH -> SettingsModel.SETTING_ID_BEARISH_CANDLE_STICK_STYLE
                },
                newSettings
            ))

        }

        updateSettings(onFinish) {
            when(type) {
                CandleStickTypes.BULLISH -> it.copy(bullishCandleStickStyle = newCandleStickStyle)
                CandleStickTypes.BEARISH -> it.copy(bearishCandleStickStyle = newCandleStickStyle)
            }
        }
    }


    override fun onZoomInOnPriceChartItemClicked(setting: Setting) {
        updateSettings {
            it.copy(isPriceChartZoomInEnabled = setting.isChecked)
        }
    }


    override fun onDepthChartLineStyleItemClicked() {
        mView.showDepthChartLineStyleDialog()
    }


    override fun onDepthChartLineStyleItemPicked(style: String) {
        val newDepthChartLineStyle = mModel.getDepthChartLineStyleForTitleString(style)
        val oldSettings = mView.getAppSettings()

        if(newDepthChartLineStyle == oldSettings.depthChartLineStyle) {
            return
        }

        val onFinish: ((Settings, Settings) -> Unit) = { newSettings, _ ->
            mView.updateSettingWith(mModel.getItemForId(
                SettingsModel.SETTING_ID_DEPTH_CHART_LINE_STYLE,
                newSettings
            ))
        }

        updateSettings(onFinish) {
            it.copy(depthChartLineStyle = newDepthChartLineStyle)
        }
    }


    override fun onHighlightOrderbookRealTimeUpdatesItemClicked(setting: Setting) {
        updateSettings {
            it.copy(isOrderbookRealTimeUpdatesHighlightingEnabled = setting.isChecked)
        }
    }


    override fun onHighlightNewTradesRealTimeAdditionItemClicked(setting: Setting) {
        updateSettings {
            it.copy(isNewTradesRealTimeAdditionHighlightingEnabled = setting.isChecked)
        }
    }


    override fun onGroupingItemClicked(setting: Setting) {
        val onFinish: ((Settings, Settings) -> Unit) = { newSettings, _ ->
            val locale = mView.getLocale()

            DoubleFormatter.getInstance(locale).apply {
                setGroupingEnabled(newSettings.isGroupingEnabled)
            }

            mView.updateSettingWith(mModel.getItemForId(
                SettingsModel.SETTING_ID_GROUPING_SEPARATOR,
                newSettings
            ))

            EventBus.getDefault().postSticky(SettingsEvent.changeGroupingState(newSettings, this))
        }

        updateSettings(onFinish) {
            it.copy(isGroupingEnabled = setting.isChecked)
        }
    }


    override fun onGroupingSeparatorItemClicked() {
        mView.showGroupingSeparatorDialog()
    }


    override fun onGroupingSeparatorPicked(separator: String) {
        val newGroupingSeparator = mModel.getGroupingSeparatorForTitleString(separator)

        if(newGroupingSeparator == mView.getAppSettings().groupingSeparator) {
            return
        }

        val onFinish: ((Settings, Settings) -> Unit) = { newSettings, _ ->
            val locale = mView.getLocale()

            DoubleFormatter.getInstance(locale).apply {
                setGroupingSeparator(newSettings.groupingSeparator.separator)
            }

            mView.updateSettingWith(mModel.getItemForId(
                SettingsModel.SETTING_ID_GROUPING_SEPARATOR,
                newSettings
            ))

            EventBus.getDefault().postSticky(SettingsEvent.changeGroupingSeparator(newSettings, this))

            if(newGroupingSeparator.separator == newSettings.decimalSeparator.separator) {
                val newDecimalSeparator = when(newGroupingSeparator) {
                    GroupingSeparators.PERIOD -> DecimalSeparators.COMMA
                    GroupingSeparators.COMMA -> DecimalSeparators.PERIOD

                    else -> throw IllegalStateException(
                        "Please provide an implementation for the separator \"$newGroupingSeparator\""
                    )
                }

                onDecimalSeparatorPicked(mModel.getDecimalSeparatorDescription(newDecimalSeparator))

                mView.showSeparatorNoteDialog(mModel.getDecimalSeparatorAutomaticChangeString(
                    newDecimalSeparator,
                    newGroupingSeparator
                ))
            }
        }

        updateSettings(onFinish) {
           it.copy(groupingSeparator = newGroupingSeparator)
        }
    }


    override fun onDecimalSeparatorItemClicked() {
        mView.showDecimalSeparatorsDialog()
    }


    override fun onDecimalSeparatorPicked(separator: String) {
        val newDecimalSeparator = mModel.getDecimalSeparatorForTitleString(separator)

        if(newDecimalSeparator == mView.getAppSettings().decimalSeparator) {
            return
        }

        val onFinish: ((Settings, Settings) -> Unit) = { newSettings, _ ->
            val locale = mView.getLocale()

            DoubleFormatter.getInstance(locale).apply {
                setDecimalSeparator(newSettings.decimalSeparator.separator)
            }

            mView.updateSettingWith(mModel.getItemForId(
                SettingsModel.SETTING_ID_DECIMAL_SEPARATOR,
                newSettings
            ))

            EventBus.getDefault().postSticky(SettingsEvent.changeDecimalSeparator(newSettings, this))

            if(newDecimalSeparator.separator == newSettings.groupingSeparator.separator) {
                val newGroupingSeparator = when(newDecimalSeparator) {
                    DecimalSeparators.PERIOD -> GroupingSeparators.COMMA
                    DecimalSeparators.COMMA -> GroupingSeparators.PERIOD
                }

                onGroupingSeparatorPicked(mModel.getGroupingSeparatorDescription(newGroupingSeparator))

                mView.showSeparatorNoteDialog(mModel.getGroupingSeparatorAutomaticChangeString(
                    newGroupingSeparator,
                    newDecimalSeparator
                ))
            }
        }

        updateSettings(onFinish) {
            it.copy(decimalSeparator = newDecimalSeparator)
        }
    }


    override fun onSoundsItemClicked(setting: Setting) {
        updateSettings {
            it.copy(isSoundEnabled = setting.isChecked)
        }
    }


    override fun onVibrationItemClicked(setting: Setting) {
        updateSettings {
            it.copy(isVibrationEnabled = setting.isChecked)
        }
    }


    override fun onPhoneLedItemClicked(setting: Setting) {
        updateSettings {
            it.copy(isPhoneLedEnabled = setting.isChecked)
        }
    }


    override fun onNotificationRingtoneItemClicked() {
        if(!mView.checkRingtonePermissions()) {
            return
        }

        mView.launchRingtonePickerActivity()
    }


    override fun onNotificationRingtonePicked(ringtone: String) {
        val onFinish: ((Settings, Settings) -> Unit) = { newSettings, _ ->
            mView.updateSettingWith(mModel.getItemForId(
                SettingsModel.SETTING_ID_NOTIFICATION_RINGTONE,
                newSettings
            ))
        }

        updateSettings(onFinish) {
            it.copy(notificationRingtone = ringtone)
        }
    }


    override fun onDeviceMetricsClicked() {
        mView.showDeviceMetricsDialog()
    }


}
package com.stocksexchange.android.ui.settings

import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.model.CandleStickTypes
import com.stocksexchange.android.model.Setting
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.theming.model.Theme
import java.util.*

interface SettingsContract {


    interface View {

        fun showToast(message: String)

        fun showProgressBar()

        fun hideProgressBar()

        fun showNoFingerprintsAvailableDialog()

        fun showDisableFingerprintUnlockDialog()

        fun showAuthenticationSessionDurationsDialog(items: List<String>)

        fun showFingerprintDialog()

        fun hideFingerprintDialog()

        fun showSignOutConfirmationDialog()

        fun showRestoreDefaultsConfirmationDialog()

        fun showCandleStickStyleDialog(type: CandleStickTypes)

        fun showDepthChartLineStyleDialog()

        fun showGroupingSeparatorDialog()

        fun showDecimalSeparatorsDialog()

        fun showSeparatorNoteDialog(content: String)

        fun hideMaterialDialog()

        fun showDeviceMetricsDialog()

        fun hideDeviceMetricsDialog()

        fun applyNewTheme(theme: Theme)

        fun checkRingtonePermissions(): Boolean

        fun launchThemesActivity()

        fun launchPinCodeChangeActivity()

        fun launchSecuritySettings()

        fun launchRingtonePickerActivity()

        fun updateSettingWith(setting: Setting, notifyAboutChange: Boolean = true)

        fun updateAdapterResources()

        fun notifyDataSetChanged()

        fun resetUser()

        fun finishActivity()

        fun updateSettings(settings: Settings)

        fun resetAppLockManager()

        fun setItems(items: MutableList<Any>)

        fun isDataSetEmpty(): Boolean

        fun getUser(): User?

        fun getAppSettings(): Settings

        fun getLocale(): Locale

    }


    interface ActionListener {

        fun onSettingSwitchClicked(setting: Setting, isChecked: Boolean)

        fun onSettingItemClicked(setting: Setting)

        fun onChangePinItemClicked()

        fun onPinCodeChanged()

        fun onFingerprintUnlockItemClicked(setting: Setting)

        fun onFingerprintDialogButtonClicked()

        fun onFingerprintUnlockConfirmed()

        fun onRegisterFingerprintConfirmed()

        fun onDisableFingerprintUnlockConfirmed()

        fun onForceAuthenticationOnAppStartItemClicked(setting: Setting)

        fun onAuthenticationSessionDurationItemClicked()

        fun onAuthenticationSessionDurationItemPicked(authenticationSessionDuration: String)

        fun onSignOutItemClicked()

        fun onSignOutConfirmed()

        fun onRestoreDefaultsItemClicked()

        fun onRestoreDefaultsConfirmed()

        fun onStreamRealTimeDataItemClicked(setting: Setting)

        fun onThemeItemClicked()

        fun onThemePicked(theme: Theme)

        fun onAnimateChartsItemClicked(setting: Setting)

        fun onCandleStickStyleItemClicked(setting: Setting)

        fun onCandleStickStylePicked(style: String, type: CandleStickTypes)

        fun onZoomInOnPriceChartItemClicked(setting: Setting)

        fun onDepthChartLineStyleItemClicked()

        fun onDepthChartLineStyleItemPicked(style: String)

        fun onHighlightOrderbookRealTimeUpdatesItemClicked(setting: Setting)

        fun onHighlightNewTradesRealTimeAdditionItemClicked(setting: Setting)

        fun onGroupingItemClicked(setting: Setting)

        fun onGroupingSeparatorItemClicked()

        fun onGroupingSeparatorPicked(separator: String)

        fun onDecimalSeparatorItemClicked()

        fun onDecimalSeparatorPicked(separator: String)

        fun onSoundsItemClicked(setting: Setting)

        fun onVibrationItemClicked(setting: Setting)

        fun onPhoneLedItemClicked(setting: Setting)

        fun onNotificationRingtoneItemClicked()

        fun onNotificationRingtonePicked(ringtone: String)

        fun onDeviceMetricsClicked()

    }


}
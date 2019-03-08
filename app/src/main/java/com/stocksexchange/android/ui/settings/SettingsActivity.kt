package com.stocksexchange.android.ui.settings

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.arthurivanets.adapster.model.BaseItem
import com.stocksexchange.android.*
import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.mappings.mapToSettingItem
import com.stocksexchange.android.mappings.mapToSettingItemList
import com.stocksexchange.android.model.*
import com.stocksexchange.android.ui.base.activities.BaseActivity
import com.stocksexchange.android.ui.themes.ThemesActivity
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.ui.auth.AuthenticationActivity
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.views.dialogs.DeviceMetricsDialog
import com.stocksexchange.android.ui.views.dialogs.FingerprintDialog
import com.stocksexchange.android.utils.helpers.isPermissionSetGranted
import kotlinx.android.synthetic.main.settings_activity_layout.*
import org.jetbrains.anko.configuration
import org.jetbrains.anko.intentFor
import java.io.Serializable
import java.util.*

class SettingsActivity : BaseActivity<SettingsPresenter>(), SettingsContract.View {


    companion object {

        private const val SAVED_STATE_ITEMS = "items"


        fun newInstance(context: Context): Intent {
            return context.intentFor<SettingsActivity>()
        }

    }


    private var mItems: MutableList<BaseItem<*, *, *>> = mutableListOf()

    private lateinit var mAdapter: SettingsRecyclerViewAdapter

    private var mMaterialDialog: MaterialDialog? = null

    private var mDeviceMetricsDialog: DeviceMetricsDialog? = null

    private var mFingerprintDialog: FingerprintDialog? = null




    override fun initPresenter(): SettingsPresenter = SettingsPresenter(this)


    override fun init() {
        super.init()

        initContentContainer()
        initToolbar()
        initRecyclerView()
    }


    private fun initContentContainer() {
        ThemingUtil.Settings.contentContainer(contentContainerRl, getAppTheme())
    }


    private fun initToolbar() {
        toolbar.setOnLeftButtonClickListener {
            onBackPressed()
        }

        ThemingUtil.Settings.toolbar(toolbar, getAppTheme())
    }


    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.disableChangeAnimations()

        mAdapter = SettingsRecyclerViewAdapter(this, mItems)
        mAdapter.setHasStableIds(true)
        mAdapter.setResources(SettingResources.newInstance(getSettings()))
        mAdapter.onSettingItemClickListener = { _, settingItem, _ ->
            mPresenter?.onSettingItemClicked(settingItem.itemModel)
        }
        mAdapter.onSwitchClickListener = { _, settingItem, _, isChecked ->
            mPresenter?.onSettingSwitchClicked(settingItem.itemModel, isChecked)
        }

        recyclerView.adapter = mAdapter
    }


    override fun showProgressBar() {
        toolbar.showProgressBar()
    }


    override fun hideProgressBar() {
        toolbar.hideProgressBar()
    }


    override fun showNoFingerprintsAvailableDialog() {
        val dialog = MaterialDialog.Builder(this)
            .title(R.string.settings_activity_no_fingerprints_available_dialog_title_text)
            .content(R.string.settings_activity_no_fingerprints_available_dialog_text)
            .positiveText(R.string.yes)
            .negativeText(R.string.no)
            .onPositive(mNoFingerprintsAvailableDialogPositiveDialogListener)
            .apply { ThemingUtil.Settings.dialogBuilder(this, getAppTheme()) }
            .build()

        mMaterialDialog = dialog
        mMaterialDialog?.show()
    }


    override fun showDisableFingerprintUnlockDialog() {
        val dialog = MaterialDialog.Builder(this)
            .title(R.string.settings_activity_disable_fingerprint_unlock_dialog_title_text)
            .content(R.string.settings_activity_disable_fingerprint_unlock_dialog_text)
            .positiveText(R.string.yes)
            .negativeText(R.string.no)
            .onPositive(mDisableFingerprintUnlockPositiveDialogListener)
            .apply { ThemingUtil.Settings.dialogBuilder(this, getAppTheme()) }
            .build()

        mMaterialDialog = dialog
        mMaterialDialog?.show()
    }


    override fun showAuthenticationSessionDurationsDialog(items: List<String>) {
        val dialog = MaterialDialog.Builder(this)
            .items(items)
            .itemsCallback(mAuthenticationSessionDurationsDialogListener)
            .apply { ThemingUtil.Settings.dialogBuilder(this, getAppTheme()) }
            .build()

        mMaterialDialog = dialog
        mMaterialDialog?.show()
    }


    override fun showFingerprintDialog() {
        val dialog = FingerprintDialog(
            this,
            FingerprintDialog.Modes.SETUP,
            getAppTheme(),
            mFingerprintDialogListener
        )
        dialog.setSubtitleText(getString(R.string.fingerprint_dialog_introduction_message))
        dialog.setButtonText(getString(R.string.action_cancel))
        dialog.setButtonListener {
            mPresenter?.onFingerprintDialogButtonClicked()
        }
        dialog.showSubtitle()
        dialog.startAuthentication()

        mFingerprintDialog = dialog
        mFingerprintDialog?.show()
    }


    override fun hideFingerprintDialog() {
        mFingerprintDialog?.dismiss()
        mFingerprintDialog = null
    }


    override fun showSignOutConfirmationDialog() {
        val dialog = MaterialDialog.Builder(this)
            .title(R.string.settings_activity_sign_out_dialog_title_text)
            .content(R.string.settings_activity_sign_out_dialog_text)
            .positiveText(R.string.yes)
            .onPositive(mSignOutConfirmationDialogListener)
            .negativeText(R.string.no)
            .apply { ThemingUtil.Settings.dialogBuilder(this, getAppTheme()) }
            .build()

        mMaterialDialog = dialog
        mMaterialDialog?.show()
    }


    override fun showRestoreDefaultsConfirmationDialog() {
        val dialog = MaterialDialog.Builder(this)
            .title(R.string.settings_activity_restore_defaults_dialog_title_text)
            .content(R.string.settings_activity_restore_defaults_dialog_text)
            .positiveText(R.string.yes)
            .onPositive(mRestoreDefaultsConfirmationDialogListener)
            .negativeText(R.string.no)
            .apply { ThemingUtil.Settings.dialogBuilder(this, getAppTheme()) }
            .build()

        mMaterialDialog = dialog
        mMaterialDialog?.show()
    }


    override fun showCandleStickStyleDialog(type: CandleStickTypes) {
        val dialog = MaterialDialog.Builder(this)
            .items(R.array.candle_stick_styles)
            .itemsCallback(CandleStickStyleDialogListener(type))
            .apply { ThemingUtil.Settings.dialogBuilder(this, getAppTheme()) }
            .build()

        mMaterialDialog = dialog
        mMaterialDialog?.show()
    }


    override fun showDepthChartLineStyleDialog() {
        val dialog = MaterialDialog.Builder(this)
            .items(R.array.depth_chart_line_styles)
            .itemsCallback(mDepthChartStyleDialogListener)
            .apply { ThemingUtil.Settings.dialogBuilder(this, getAppTheme()) }
            .build()

        mMaterialDialog = dialog
        mMaterialDialog?.show()
    }


    override fun showGroupingSeparatorDialog() {
        val dialog = MaterialDialog.Builder(this)
            .items(R.array.grouping_separators)
            .itemsCallback(mGroupingSeparatorDialogListener)
            .apply { ThemingUtil.Settings.dialogBuilder(this, getAppTheme()) }
            .build()

        mMaterialDialog = dialog
        mMaterialDialog?.show()
    }


    override fun showDecimalSeparatorsDialog() {
        val dialog = MaterialDialog.Builder(this)
            .items(R.array.decimal_separators)
            .itemsCallback(mDecimalSeparatorsDialogListener)
            .apply { ThemingUtil.Settings.dialogBuilder(this, getAppTheme()) }
            .build()

        mMaterialDialog = dialog
        mMaterialDialog?.show()
    }


    override fun showSeparatorNoteDialog(content: String) {
        val dialog = MaterialDialog.Builder(this)
            .title(R.string.note)
            .content(content)
            .positiveText(R.string.ok)
            .apply { ThemingUtil.Settings.dialogBuilder(this, getAppTheme()) }
            .build()

        mMaterialDialog = dialog
        mMaterialDialog?.show()
    }


    override fun hideMaterialDialog() {
        mMaterialDialog?.dismiss()
        mMaterialDialog = null
    }


    override fun showDeviceMetricsDialog() {
        val dialog = DeviceMetricsDialog(this)
        val displayMetrics = resources.displayMetrics
        val density = displayMetrics.density

        dialog.setDeviceName("${Build.MODEL} (${Build.PRODUCT})")
        dialog.setDensity(density)
        dialog.setDensityInDp(displayMetrics.densityDpi)
        dialog.setScreenSize(getScreenSize().title)
        dialog.setScreenWidthInPx(displayMetrics.widthPixels)
        dialog.setScreenWidthInDp(displayMetrics.widthPixels / density)
        dialog.setScreenHeightInPx(displayMetrics.heightPixels)
        dialog.setScreenHeightInDp(displayMetrics.heightPixels / density)
        dialog.setSmallestWidthInDp(configuration.smallestScreenWidthDp)

        ThemingUtil.Settings.deviceMetricsDialog(dialog, getAppTheme())

        mDeviceMetricsDialog = dialog
        mDeviceMetricsDialog?.show()
    }


    override fun hideDeviceMetricsDialog() {
        mDeviceMetricsDialog?.dismiss()
        mDeviceMetricsDialog = null
    }


    override fun applyNewTheme(theme: Theme) {
        setStatusBarColor(getStatusBarColor())
        setRecentAppsToolbarColor(getRecentAppsToolbarColor())

        with(ThemingUtil.Settings) {
            contentContainer(contentContainerRl, theme)
            toolbar(toolbar, theme)
        }
    }


    override fun checkRingtonePermissions(): Boolean {
        return checkPermissions(
            Constants.REQUEST_CODE_RINGTONE_PERMISSION,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        )
    }


    override fun launchThemesActivity() {
        startActivityForResult(ThemesActivity.newInstance(this), Constants.REQUEST_CODE_THEMES_ACTIVITY)
    }


    override fun launchPinCodeChangeActivity() {
        startActivityForResult(AuthenticationActivity.newInstance(
            this,
            PinCodeModes.CHANGE,
            TransitionAnimations.HORIZONTAL_SLIDING_ANIMATIONS,
            getSettings().theme
        ), Constants.REQUEST_CODE_AUTHENTICATION_ACTIVITY)
    }


    override fun launchSecuritySettings() {
        startActivity(Intent(android.provider.Settings.ACTION_SECURITY_SETTINGS))
    }


    override fun launchRingtonePickerActivity() {
        val intent = Intent()
        intent.action = RingtoneManager.ACTION_RINGTONE_PICKER
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, getString(R.string.ringtones))
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION)

        startActivityForResult(intent, Constants.REQUEST_CODE_RINGTONE_ACTIVITY)
    }


    override fun updateSettingWith(setting: Setting, notifyAboutChange: Boolean) {
        mAdapter.updateItemWith(
            mAdapter.getSettingIndex(setting),
            setting.mapToSettingItem(),
            notifyAboutChange
        )
    }


    override fun updateAdapterResources() {
        mAdapter.setResources(SettingResources.newInstance(getSettings()))
    }


    override fun notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged()
    }


    override fun resetUser() {
        AppController.INSTANCE.setUser(User.STUB_USER)
    }


    override fun finishActivity() {
        onBackPressed()
    }


    override fun updateSettings(settings: Settings) {
        AppController.INSTANCE.setSettings(settings)
    }


    override fun resetAppLockManager() {
        AppController.INSTANCE.getAppLockManager()?.reset()
    }


    override fun setItems(items: MutableList<Any>) {
        val settingItemList = items.mapToSettingItemList().toMutableList()

        mItems = settingItemList
        mAdapter.items = settingItemList
    }


    override fun isDataSetEmpty(): Boolean {
        return (mAdapter.itemCount == 0)
    }


    override fun getUser(): User? {
        return AppController.INSTANCE.getUser()
    }


    override fun getAppSettings(): Settings {
        return getSettings()
    }


    override fun getLocale(): Locale {
        return ctx.getLocale()
    }


    override fun getContentLayoutResourceId() = R.layout.settings_activity_layout


    override fun getTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.HORIZONTAL_SLIDING_ANIMATIONS
    }


    private val mNoFingerprintsAvailableDialogPositiveDialogListener = MaterialDialog.SingleButtonCallback { _, _ ->
        mPresenter?.onRegisterFingerprintConfirmed()
    }


    private val mDisableFingerprintUnlockPositiveDialogListener = MaterialDialog.SingleButtonCallback { _, _ ->
        mPresenter?.onDisableFingerprintUnlockConfirmed()
    }


    private val mAuthenticationSessionDurationsDialogListener = MaterialDialog.ListCallback { _, _, _, text ->
        mPresenter?.onAuthenticationSessionDurationItemPicked(text.toString())
    }


    private val mSignOutConfirmationDialogListener = MaterialDialog.SingleButtonCallback { _, _ ->
        mPresenter?.onSignOutConfirmed()
    }


    private val mRestoreDefaultsConfirmationDialogListener = MaterialDialog.SingleButtonCallback { _, _ ->
        mPresenter?.onRestoreDefaultsConfirmed()
    }


    private val mDepthChartStyleDialogListener = MaterialDialog.ListCallback { _, _, _, text ->
        mPresenter?.onDepthChartLineStyleItemPicked(text.toString())
    }


    private val mGroupingSeparatorDialogListener = MaterialDialog.ListCallback { _, _, _, text ->
        mPresenter?.onGroupingSeparatorPicked(text.toString())
    }


    private val mDecimalSeparatorsDialogListener = MaterialDialog.ListCallback { _, _, _, text ->
        mPresenter?.onDecimalSeparatorPicked(text.toString())
    }


    private val mFingerprintDialogListener: FingerprintDialog.Listener = object : FingerprintDialog.Listener {

        override fun onSuccess() {
            mPresenter?.onFingerprintUnlockConfirmed()
        }

    }


    private inner class CandleStickStyleDialogListener(val type: CandleStickTypes) : MaterialDialog.ListCallback {

        override fun onSelection(dialog: MaterialDialog, itemView: View,
                                 position: Int, text: CharSequence) {
            mPresenter?.onCandleStickStylePicked(text.toString(), type)
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(isPermissionSetGranted(grantResults)) {
            if(requestCode == Constants.REQUEST_CODE_RINGTONE_PERMISSION) {
                mPresenter?.onNotificationRingtoneItemClicked()
            }
        } else {
            showToast(getString(R.string.error_permissions_not_granted))
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != Activity.RESULT_OK) {
            return
        }

        when {
            (requestCode == Constants.REQUEST_CODE_RINGTONE_ACTIVITY) -> {
                val uri: Uri? = data!!.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)

                if(uri != null) {
                    mPresenter?.onNotificationRingtonePicked(uri.toString())
                }
            }

            (requestCode == Constants.REQUEST_CODE_THEMES_ACTIVITY) -> {
                mPresenter?.onThemePicked(data!!.getSerializableExtra(ThemesActivity.EXTRA_THEME) as Theme)
            }

            (requestCode == Constants.REQUEST_CODE_AUTHENTICATION_ACTIVITY) -> {
                mPresenter?.onPinCodeChanged()
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            mItems = (savedState.getSerializable(SAVED_STATE_ITEMS) as MutableList<BaseItem<*, *, *>>)
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        savedState.putSerializable(SAVED_STATE_ITEMS, (mItems as Serializable))
    }


}
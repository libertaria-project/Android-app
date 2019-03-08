package com.stocksexchange.android.ui.about

import android.content.Context
import android.content.Intent
import android.view.Gravity
import com.stocksexchange.android.BuildConfig
import com.stocksexchange.android.R
import com.stocksexchange.android.model.Separator
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.ui.base.activities.BaseActivity
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.views.popupmenu.PopupMenu
import com.stocksexchange.android.ui.views.popupmenu.PopupMenuItem
import com.stocksexchange.android.ui.views.popupmenu.PopupMenuItemData
import com.stocksexchange.android.ui.views.popupmenu.PopupMenuSeparatorItem
import com.stocksexchange.android.utils.handlers.BrowserHandler
import kotlinx.android.synthetic.main.about_activity_header_layout.*
import kotlinx.android.synthetic.main.about_activity_layout.*
import org.jetbrains.anko.intentFor
import org.koin.android.ext.android.get

class AboutActivity : BaseActivity<AboutPresenter>(), AboutContract.View {


    companion object {

        private const val POPUP_MENU_ITEM_TWITTER = 0
        private const val POPUP_MENU_ITEM_FACEBOOK = 1
        private const val POPUP_MENU_ITEM_TELEGRAM = 2
        private const val POPUP_MENU_ITEM_SEPARATOR = 3
        private const val POPUP_MENU_ITEM_TERMS_OF_USE = 4


        fun newInstance(context: Context): Intent {
            return context.intentFor<AboutActivity>()
        }

    }


    private var mPopupMenu: PopupMenu? = null




    override fun initPresenter(): AboutPresenter = AboutPresenter(this)


    override fun init() {
        super.init()

        initContentContainer()
        initToolbar()
        initHeader()
        initFeatures()
        initVisitOurWebsiteButton()
    }


    private fun initContentContainer() {
        ThemingUtil.About.contentContainer(contentContainerRl, getAppTheme())
    }


    private fun initToolbar() {
        toolbar.setOnLeftButtonClickListener {
            onBackPressed()
        }

        toolbar.setOnRightButtonClickListener {
            mPresenter?.onActionButtonClicked()
        }

        ThemingUtil.About.toolbar(toolbar, getAppTheme())
    }


    private fun initHeader() {
        appVersionTv.text = getString(
            R.string.about_activity_app_version_template,
            BuildConfig.VERSION_NAME
        )

        with(ThemingUtil.About) {
            val theme = getAppTheme()

            appName(appNameTv, theme)
            appVersion(appVersionTv, theme)
            description(descriptionTv, theme)
            separator(separatorView, theme)
        }
    }


    private fun initFeatures() {
        with(ThemingUtil.About) {
            val theme = getAppTheme()

            featureView(cryptoFeature, theme)
            featureView(auditFeature, theme)
            featureView(storageFeature, theme)
        }
    }


    private fun initVisitOurWebsiteButton() {
        visitOurWebsiteBtn.setOnClickListener {
            mPresenter?.onVisitOurWebsiteButtonClicked()
        }
        ThemingUtil.About.button(visitOurWebsiteBtn, getAppTheme())
    }


    override fun launchBrowser(url: String) {
        get<BrowserHandler>().launchBrowser(this, url, getAppTheme())
    }


    override fun showPopupMenu() {
        mPopupMenu = PopupMenu(this, Gravity.END or Gravity.TOP)
        mPopupMenu?.applyTheme(getAppTheme())
        mPopupMenu?.addItem(PopupMenuItem(PopupMenuItemData(
            id = POPUP_MENU_ITEM_TWITTER,
            title = getString(R.string.twitter)
        )))
        mPopupMenu?.addItem(PopupMenuItem(PopupMenuItemData(
            id = POPUP_MENU_ITEM_FACEBOOK,
            title = getString(R.string.facebook)
        )))
        mPopupMenu?.addItem(PopupMenuItem(PopupMenuItemData(
            id = POPUP_MENU_ITEM_TELEGRAM,
            title = getString(R.string.telegram)
        )))
        mPopupMenu?.addItem(PopupMenuSeparatorItem(Separator(
            id = POPUP_MENU_ITEM_SEPARATOR
        )))
        mPopupMenu?.addItem(PopupMenuItem(PopupMenuItemData(
            id = POPUP_MENU_ITEM_TERMS_OF_USE,
            title = getString(R.string.terms_of_use)
        )))
        mPopupMenu?.onItemClickListener = { item ->
            when(item.itemModel.id) {
                POPUP_MENU_ITEM_TWITTER -> mPresenter?.onTwitterMenuItemClicked()
                POPUP_MENU_ITEM_FACEBOOK -> mPresenter?.onFacebookMenuItemClicked()
                POPUP_MENU_ITEM_TELEGRAM -> mPresenter?.onTelegramMenuItemClicked()
                POPUP_MENU_ITEM_TERMS_OF_USE -> mPresenter?.onTermsOfUseMenuItemClicked()
            }
        }
        mPopupMenu?.horizontalOffset = -resources.getDimensionPixelSize(R.dimen.popup_menu_offset)
        mPopupMenu?.verticalOffset = (-toolbar.height + resources.getDimensionPixelSize(R.dimen.popup_menu_offset))
        mPopupMenu?.show(toolbar.getRightButtonIv())
    }


    override fun hidePopupMenu() {
        mPopupMenu?.dismiss()
    }


    override fun getContentLayoutResourceId() = R.layout.about_activity_layout


    override fun getTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.HORIZONTAL_SLIDING_ANIMATIONS
    }


}
package com.stocksexchange.android.ui.wallets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import com.stocksexchange.android.R
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.ui.base.activities.BaseFragmentActivity
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.extensions.overrideEnterTransition
import com.stocksexchange.android.ui.views.popupmenu.PopupMenu
import com.stocksexchange.android.ui.views.popupmenu.PopupMenuItem
import com.stocksexchange.android.ui.views.popupmenu.PopupMenuItemData
import com.stocksexchange.android.ui.wallets.fragment.WalletsFragment
import com.stocksexchange.android.ui.wallets.search.WalletsSearchActivity
import com.stocksexchange.android.utils.helpers.loginIfNecessary
import kotlinx.android.synthetic.main.wallets_activity_layout.*
import org.jetbrains.anko.intentFor
import org.koin.android.ext.android.get

class WalletsActivity : BaseFragmentActivity<WalletsFragment, WalletsActivityPresenter>(),
    WalletsActivityContract.View {


    companion object {

        const val TAG = "WalletsActivity"

        private const val EXTRA_TRANSITION_ANIMATIONS = "transition_animations"

        private const val SAVED_STATE_TRANSITION_ANIMATIONS_NAME = "transition_animations_name"
        private const val SAVED_STATE_SHOULD_SHOW_EMPTY_WALLETS = "should_show_empty_wallets"

        private const val POPUP_MENU_ITEM_SHOW_ZERO_BALANCES = 0
        private const val POPUP_MENU_ITEM_HIDE_ZERO_BALANCES = 1


        fun newInstance(context: Context, transitionAnimations: TransitionAnimations): Intent {
            return context.intentFor<WalletsActivity>(
                EXTRA_TRANSITION_ANIMATIONS to transitionAnimations
            )
        }

    }


    private lateinit var mTransitionAnimations: TransitionAnimations

    private var mShouldShowEmptyWallets: Boolean = true

    private var mPopupMenu: PopupMenu? = null




    override fun preViewInflation(): Boolean {
        return loginIfNecessary(
            this,
            newInstance(this, TransitionAnimations.KITKAT_SCALING_ANIMATIONS),
            get()
        )
    }


    override fun initPresenter(): WalletsActivityPresenter = WalletsActivityPresenter(this)


    override fun init() {
        super.init()

        initToolbar()
    }


    private fun initToolbar() {
        toolbar.setOnLeftButtonClickListener {
            onBackPressed()
        }
        toolbar.setOnPreRightButtonClickListener {
            mPresenter?.onPreRightButtonClicked()
        }
        toolbar.setOnRightButtonClickListener {
            mPresenter?.onRightButtonClicked()
        }

        ThemingUtil.Wallets.toolbar(toolbar, getAppTheme())
    }


    override fun postInit() {
        super.postInit()

        overrideEnterTransition(mTransitionAnimations)
    }


    override fun launchSearchActivity() {
        startActivity(WalletsSearchActivity.newInstance(this))
    }


    override fun showPopupMenu() {
        mPopupMenu = PopupMenu(this, Gravity.END or Gravity.TOP)
        mPopupMenu?.applyTheme(getAppTheme())

        if(mShouldShowEmptyWallets) {
            mPopupMenu?.addItem(PopupMenuItem(PopupMenuItemData(
                id = POPUP_MENU_ITEM_HIDE_ZERO_BALANCES,
                title = getString(R.string.action_hide_empty_wallets)
            )))
        } else {
            mPopupMenu?.addItem(PopupMenuItem(PopupMenuItemData(
                id = POPUP_MENU_ITEM_SHOW_ZERO_BALANCES,
                title = getString(R.string.action_show_empty_wallets)
            )))
        }

        mPopupMenu?.onItemClickListener = { item ->
            when(item.itemModel.id) {
                POPUP_MENU_ITEM_SHOW_ZERO_BALANCES,
                POPUP_MENU_ITEM_HIDE_ZERO_BALANCES -> {
                    mPresenter?.onEmptyWalletsFlagStateChanged(mShouldShowEmptyWallets)
                }
            }
        }
        mPopupMenu?.horizontalOffset = -resources.getDimensionPixelSize(R.dimen.popup_menu_offset)
        mPopupMenu?.verticalOffset = (-toolbar.height + resources.getDimensionPixelSize(R.dimen.popup_menu_offset))
        mPopupMenu?.show(toolbar.getRightButtonIv())
    }


    override fun hidePopupMenu() {
        mPopupMenu?.dismiss()
    }


    override fun updateEmptyWalletsFlag(shouldShowEmptyWallets: Boolean) {
        mShouldShowEmptyWallets = shouldShowEmptyWallets
    }


    override fun reloadWallets(shouldShowEmptyWallets: Boolean) {
        mFragment.onEmptyWalletsFlagChanged(shouldShowEmptyWallets)
    }


    override fun getActivityFragment(): WalletsFragment {
        return WalletsFragment.newStandardInstance()
    }


    override fun getContentLayoutResourceId(): Int = R.layout.wallets_activity_layout


    override fun getEnterTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.DEFAULT_ANIMATIONS
    }


    override fun getExitTransitionAnimations(): TransitionAnimations {
        return mTransitionAnimations
    }


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            with(savedState) {
                mTransitionAnimations = getString(SAVED_STATE_TRANSITION_ANIMATIONS_NAME)
                    ?.let { TransitionAnimations.valueOf(it) }
                    ?: TransitionAnimations.HORIZONTAL_SLIDING_ANIMATIONS

                mShouldShowEmptyWallets = getBoolean(SAVED_STATE_SHOULD_SHOW_EMPTY_WALLETS, true)
            }
        } else {
            with(intent) {
                mTransitionAnimations = (getSerializableExtra(EXTRA_TRANSITION_ANIMATIONS) as TransitionAnimations)
            }
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        with(savedState) {
            putSerializable(SAVED_STATE_TRANSITION_ANIMATIONS_NAME, mTransitionAnimations.name)
            putBoolean(SAVED_STATE_SHOULD_SHOW_EMPTY_WALLETS, mShouldShowEmptyWallets)
        }
    }


}
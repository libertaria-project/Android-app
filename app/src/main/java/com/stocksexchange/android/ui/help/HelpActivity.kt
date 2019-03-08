package com.stocksexchange.android.ui.help

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.stocksexchange.android.R
import com.stocksexchange.android.mappings.mapToHelpItemList
import com.stocksexchange.android.model.HelpItemModel
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.ui.about.AboutActivity
import com.stocksexchange.android.ui.base.activities.BaseActivity
import com.stocksexchange.android.ui.feedback.FeedbackActivity
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.decorators.VerticalSpacingItemDecorator
import com.stocksexchange.android.ui.utils.extensions.dimenInPx
import com.stocksexchange.android.ui.views.popupmenu.PopupMenu
import com.stocksexchange.android.ui.views.popupmenu.PopupMenuItem
import com.stocksexchange.android.ui.views.popupmenu.PopupMenuItemData
import com.stocksexchange.android.utils.handlers.BrowserHandler
import kotlinx.android.synthetic.main.help_activity_layout.*
import net.cachapa.expandablelayout.util.FastOutSlowInInterpolator
import org.jetbrains.anko.intentFor
import org.koin.android.ext.android.get
import java.io.Serializable

class HelpActivity : BaseActivity<HelpPresenter>(), HelpContract.View {


    companion object {

        private const val SAVED_STATE_ITEMS = "items"

        private const val POPUP_MENU_ITEM_FEEDBACK = 0
        private const val POPUP_MENU_ITEM_ABOUT = 1


        fun newInstance(context: Context): Intent {
            return context.intentFor<HelpActivity>()
        }

    }


    private var mItems: MutableList<HelpItem> = mutableListOf()

    private lateinit var mAdapter: HelpRecyclerViewAdapter

    private var mPopupMenu: PopupMenu? = null

    private var mMaterialDialog: MaterialDialog? = null




    override fun initPresenter(): HelpPresenter = HelpPresenter(this)


    override fun init() {
        super.init()

        initContentContainer()
        initToolbar()
        initRecyclerView()
    }


    private fun initContentContainer() {
        ThemingUtil.Help.contentContainer(contentContainerRl, getAppTheme())
    }


    private fun initToolbar() {
        toolbar.setOnLeftButtonClickListener {
            onBackPressed()
        }

        toolbar.setOnRightButtonClickListener {
            mPresenter?.onActionButtonClicked()
        }

        ThemingUtil.Help.toolbar(toolbar, getAppTheme())
    }


    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(VerticalSpacingItemDecorator(
            dimenInPx(R.dimen.recycler_view_divider_size),
            dimenInPx(R.dimen.card_view_elevation)
        ))
        recyclerView.setHasFixedSize(true)

        val resources = HelpItemResources.newInstance(
            resources.getInteger(R.integer.help_item_answer_tv_animation_duration),
            FastOutSlowInInterpolator(),
            getSettings()
        )

        mAdapter = HelpRecyclerViewAdapter(this, mItems)
        mAdapter.setResources(resources)
        mAdapter.onItemClickListener = onItemClickListener
        mAdapter.onActionButtonClickListener = onActionButtonClickListener

        recyclerView.adapter = mAdapter
    }


    override fun showPopupMenu() {
        mPopupMenu = PopupMenu(this, Gravity.END or Gravity.TOP)
        mPopupMenu?.applyTheme(getAppTheme())
        mPopupMenu?.addItem(PopupMenuItem(PopupMenuItemData(
            id = POPUP_MENU_ITEM_FEEDBACK,
            title = getString(R.string.feedback)
        )))
        mPopupMenu?.addItem(PopupMenuItem(PopupMenuItemData(
            id = POPUP_MENU_ITEM_ABOUT,
            title = getString(R.string.about)
        )))
        mPopupMenu?.onItemClickListener = { item ->
            when(item.itemModel.id) {
                POPUP_MENU_ITEM_FEEDBACK -> mPresenter?.onFeedbackMenuItemClicked()
                POPUP_MENU_ITEM_ABOUT -> mPresenter?.onAboutMenuItemClicked()
            }
        }
        mPopupMenu?.horizontalOffset = -resources.getDimensionPixelSize(R.dimen.popup_menu_offset)
        mPopupMenu?.verticalOffset = (-toolbar.height + resources.getDimensionPixelSize(R.dimen.popup_menu_offset))
        mPopupMenu?.show(toolbar.getRightButtonIv())
    }


    override fun hidePopupMenu() {
        mPopupMenu?.dismiss()
    }


    override fun showSocialMediaDialog() {
        val dialog = MaterialDialog.Builder(this)
            .items(R.array.social_media_types)
            .itemsCallback(mSocialMediaDialogListener)
            .apply { ThemingUtil.Help.dialogBuilder(this, getAppTheme()) }
            .build()

        mMaterialDialog = dialog
        mMaterialDialog?.show()
    }


    override fun hideSocialMediaDialog() {
        mMaterialDialog?.dismiss()
        mMaterialDialog = null
    }


    override fun launchFeedbackActivity() {
        startActivity(FeedbackActivity.newFeedbackInstance(this))
    }


    override fun launchAboutActivity() {
        startActivity(AboutActivity.newInstance(this))
    }


    override fun launchBrowser(url: String) {
        get<BrowserHandler>().launchBrowser(this, url, getAppTheme())
    }


    override fun finishActivity() {
        finish()
    }


    override fun setItems(items: List<HelpItemModel>) {
        val helpItemList = items.mapToHelpItemList().toMutableList()

        mItems = helpItemList
        mAdapter.items = helpItemList
    }


    override fun isDataSetEmpty(): Boolean {
        return (mAdapter.itemCount == 0)
    }


    override fun getContentLayoutResourceId() = R.layout.help_activity_layout


    override fun getTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.HORIZONTAL_SLIDING_ANIMATIONS
    }


    private val onItemClickListener: ((HelpItem.ViewHolder, HelpItem, Int) -> Unit) = { viewHolder, helpItem, _ ->
        val itemModel = helpItem.itemModel

        if(itemModel.isCollapsed()) {
            helpItem.expand(viewHolder, true)
        } else {
            helpItem.collapse(viewHolder, true)
        }

        itemModel.toggleState()
    }


    private val onActionButtonClickListener: ((HelpItem.ViewHolder, HelpItem, Int) -> Unit) = { _, helpItem, _ ->
        mPresenter?.onHelpItemActionButtonClicked(helpItem.itemModel)
    }


    private val mSocialMediaDialogListener = MaterialDialog.ListCallback { _, _, _, text ->
        mPresenter?.onSocialMediaPicked(text.toString())
    }


    @Suppress("UNCHECKED_CAST")
    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            mItems = (savedState.getSerializable(SAVED_STATE_ITEMS) as MutableList<HelpItem>)
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        savedState.putSerializable(SAVED_STATE_ITEMS, (mItems as Serializable))
    }


}
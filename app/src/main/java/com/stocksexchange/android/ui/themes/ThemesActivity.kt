package com.stocksexchange.android.ui.themes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stocksexchange.android.R
import com.stocksexchange.android.mappings.mapToThemeItem
import com.stocksexchange.android.mappings.mapToThemeItems
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.model.ThemeData
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.ui.base.activities.BaseActivity
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.decorators.HorizontalSpacingItemDecorator
import com.stocksexchange.android.ui.utils.extensions.dimenInPx
import com.stocksexchange.android.ui.utils.extensions.disableChangeAnimations
import kotlinx.android.synthetic.main.themes_activity_layout.*
import org.jetbrains.anko.intentFor
import java.io.Serializable

class ThemesActivity : BaseActivity<ThemesPresenter>(), ThemesContract.View {


    companion object {

        const val EXTRA_THEME = "theme"

        private const val RECYCLER_VIEW_COLUMN_COUNT = 2

        private const val SAVED_STATE_ITEMS = "items"


        fun newInstance(context: Context): Intent {
            return context.intentFor<ThemesActivity>()
        }

    }


    private var mItems: MutableList<ThemeItem> = mutableListOf()

    private lateinit var mAdapter: ThemesRecyclerViewAdapter




    override fun initPresenter(): ThemesPresenter = ThemesPresenter(this)


    override fun init() {
        super.init()

        initContentContainer()
        initToolbar()
        initRecyclerView()
    }


    private fun initContentContainer() {
        ThemingUtil.Themes.contentContainer(contentContainerRl, getAppTheme())
    }


    private fun initToolbar() {
        toolbar.setOnLeftButtonClickListener {
            onBackPressed()
        }

        ThemingUtil.Themes.toolbar(toolbar, getAppTheme())
    }


    private fun initRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, RECYCLER_VIEW_COLUMN_COUNT, RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.disableChangeAnimations()
        recyclerView.addItemDecoration(HorizontalSpacingItemDecorator(
            RECYCLER_VIEW_COLUMN_COUNT,
            dimenInPx(R.dimen.recycler_view_divider_size)
        ))

        mAdapter = ThemesRecyclerViewAdapter(this, getSettings(), mItems)
        mAdapter.onThemeItemClickListener = { _, themeItem, _ ->
            mPresenter?.onThemeClicked(themeItem.itemModel)
        }

        recyclerView.adapter = mAdapter
    }


    override fun updateThemeData(themeData: ThemeData) {
        mAdapter.updateItem(themeData.mapToThemeItem())
    }


    override fun setItems(items: MutableList<ThemeData>) {
        val themeItemList = items.mapToThemeItems().toMutableList()

        mItems = themeItemList
        mAdapter.items = mItems
    }


    override fun isDataSetEmpty(): Boolean {
        return (mAdapter.itemCount == 0)
    }


    override fun getDataSetSize(): Int {
        return mAdapter.itemCount
    }


    override fun getContentLayoutResourceId(): Int = R.layout.themes_activity_layout


    override fun getTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.HORIZONTAL_SLIDING_ANIMATIONS
    }


    override fun getThemeDataAt(position: Int): ThemeData? {
        return mAdapter.getItem(position)?.itemModel
    }


    override fun getAppSettings(): Settings {
        return getSettings()
    }


    private fun getSelectedTheme(): ThemeData? {
        for(item in mAdapter.items) {
            if(item.itemModel.isSelected) {
                return item.itemModel
            }
        }

        return null
    }


    override fun onBackPressed() {
        val selectedThemeData: ThemeData? = getSelectedTheme()

        if((selectedThemeData != null) && (selectedThemeData.theme.id != getAppTheme().id)) {
            val intent = Intent()
            intent.putExtra(EXTRA_THEME, selectedThemeData.theme)

            setResult(Activity.RESULT_OK, intent)
        }

        super.onBackPressed()
    }


    @Suppress("UNCHECKED_CAST")
    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            mItems = (savedState.getSerializable(SAVED_STATE_ITEMS) as MutableList<ThemeItem>)
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        savedState.putSerializable(SAVED_STATE_ITEMS, (mItems as Serializable))
    }


}
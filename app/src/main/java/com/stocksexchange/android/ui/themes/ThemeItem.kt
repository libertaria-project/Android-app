package com.stocksexchange.android.ui.themes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.Adapter
import com.arthurivanets.adapster.markers.ItemResources
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.model.Item
import com.arthurivanets.adapster.model.markers.Trackable
import com.stocksexchange.android.R
import com.stocksexchange.android.model.ThemeData
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.ui.base.adapters.recyclerview.BaseViewHolder
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.extensions.makeGone
import com.stocksexchange.android.ui.utils.extensions.makeVisible

class ThemeItem(itemModel: ThemeData) : BaseItem<ThemeData, ThemeItem.ViewHolder, ThemeResources>(itemModel), Trackable<Int> {


    companion object {

        const val MAIN_LAYOUT_ID = R.layout.theme_item_layout

    }




    override fun init(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
                      parent: ViewGroup, inflater: LayoutInflater, resources: ThemeResources?): ViewHolder {
        return ViewHolder(inflater.inflate(layout, parent, false))
    }


    override fun bind(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
                      viewHolder: ViewHolder, resources: ThemeResources?) {
        super.bind(adapter, viewHolder, resources)

        with(viewHolder) {
            mFirstColumnTv.text = resources!!.strings[ThemeResources.STRING_FIRST_COLUMN]

            mNameTv.text = itemModel.theme.name

            if(itemModel.isSelected) {
                mOverLayFl.makeVisible()
            } else {
                mOverLayFl.makeGone()
            }

            applyTheme(itemModel.theme)
        }
    }


    fun setOnItemClickListener(viewHolder: ViewHolder, position: Int,
                               listener: ((View, ThemeItem, Int) -> Unit)?) {
        viewHolder.itemView.setOnClickListener {
            listener?.invoke(it, this@ThemeItem, position)
        }
    }


    override fun getLayout(): Int = MAIN_LAYOUT_ID


    override fun getTrackKey(): Int = itemModel.theme.id


    class ViewHolder(itemView: View) : BaseViewHolder<ThemeData>(itemView) {

        val mBtcTabView = itemView.findViewById<View>(R.id.btcTabView)
        val mEsdtTabView = itemView.findViewById<View>(R.id.esdtTabView)

        val mTabIndicatorView = itemView.findViewById<View>(R.id.tabIndicatorView)

        val mToolbarTitleTv = itemView.findViewById<TextView>(R.id.titleTv)
        val mFirstColumnTv = itemView.findViewById<TextView>(R.id.firstColumnTv)
        val mSecondColumnTv = itemView.findViewById<TextView>(R.id.secondColumnTv)
        val mThirdColumnTv = itemView.findViewById<TextView>(R.id.thirdColumnTv)
        val mNameTv = itemView.findViewById<TextView>(R.id.nameTv)

        val mToolbarLeftBtnIv = itemView.findViewById<ImageView>(R.id.leftBtnIv)
        val mToolbarPreRightBtnIv = itemView.findViewById<ImageView>(R.id.preRightBtnIv)
        val mToolbarRightBtnIv = itemView.findViewById<ImageView>(R.id.rightBtnIv)

        val mProgressBarIv = itemView.findViewById<ImageView>(R.id.progressBarIv)

        val mOverLayFl = itemView.findViewById<FrameLayout>(R.id.overlayFl)
        val mSortPanelFl = itemView.findViewById<FrameLayout>(R.id.sortPanelFl)

        val mTabLayoutLl = itemView.findViewById<LinearLayout>(R.id.tabLayoutLl)
        val mContentContainerRl = itemView.findViewById<RelativeLayout>(R.id.contentContainerRl)

        val mToolbarRl = itemView.findViewById<RelativeLayout>(R.id.toolbarRl)


        override fun applyTheme(theme: Theme) {
            ThemingUtil.Main.contentContainer(mContentContainerRl, theme)
            ThemingUtil.Main.toolbarBackground(mToolbarRl, theme)
            ThemingUtil.Main.toolbarTitle(mToolbarTitleTv, theme)
            ThemingUtil.Main.toolbarIcon(mToolbarLeftBtnIv, theme)
            ThemingUtil.Main.toolbarIcon(mToolbarPreRightBtnIv, theme)
            ThemingUtil.Main.toolbarIcon(mToolbarRightBtnIv, theme)
            ThemingUtil.TabBar.tabsBackground(mTabLayoutLl, theme)
            ThemingUtil.TabBar.tabText(mBtcTabView, theme)
            ThemingUtil.TabBar.tabText(mEsdtTabView, theme)
            ThemingUtil.TabBar.tabIndicator(mTabIndicatorView, theme)
            ThemingUtil.SortPanel.background(mSortPanelFl, theme)
            ThemingUtil.SortPanel.selectedTitle(mFirstColumnTv, theme)
            ThemingUtil.SortPanel.unselectedTitle(mSecondColumnTv, theme)
            ThemingUtil.SortPanel.unselectedTitle(mThirdColumnTv, theme)
            ThemingUtil.Themes.Item.progressBar(mProgressBarIv, theme)
            ThemingUtil.Text.primary(mNameTv, theme)
        }

    }


}
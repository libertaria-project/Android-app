package com.stocksexchange.android.ui.views.popupmenu

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.annotation.ColorInt
import androidx.core.widget.PopupWindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.markers.ItemResources
import com.arthurivanets.adapster.model.BaseItem
import com.stocksexchange.android.R
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.ui.utils.extensions.getLayoutInflater
import com.stocksexchange.android.ui.utils.interfaces.Themable

/**
 * A custom popup menu.
 */
@SuppressLint("PrivateResource,RestrictedApi")
class PopupMenu(
    private val context: Context,
    private val dropdownGravity: Int
) : Themable<Theme> {


    // Model
    private var mPopupMenuItemMinWidth: Int = 0
    private var mPopupMenuItemMaxWidth: Int = 0

    private var mPopupMenuWidth: Int = ViewGroup.LayoutParams.WRAP_CONTENT

    /**
     * A horizontal offset for this popup, in pixels.
     */
    var horizontalOffset: Int = 0

    /**
     * A vertical offset for this popup, in pixels.
     */
    var verticalOffset: Int = 0

    private var mItems: MutableList<BaseItem<*, *, *>> = mutableListOf()

    // UI elements
    private lateinit var mAnchorView: View

    private var mPopupWindow: PopupWindow

    private var mAdapter: PopupRecyclerViewAdapter

    // Listeners
    var onItemClickListener: ((PopupMenuItem) -> Unit)? = null




    init {
        mPopupWindow = PopupWindow(context, null, 0, 0)
        mPopupWindow.isFocusable = true

        with(context.resources) {
            mPopupWindow.elevation = getDimension(R.dimen.popup_menu_elevation)

            mPopupMenuItemMinWidth = getDimensionPixelSize(R.dimen.popup_menu_item_min_width)
            mPopupMenuItemMaxWidth = getDimensionPixelSize(R.dimen.popup_menu_item_max_width)
        }

        mAdapter = PopupRecyclerViewAdapter(context, mItems)
        mAdapter.onItemClickListener = { _, item, _ ->
            onItemClickListener?.invoke(item)

            dismiss()
        }
    }


    @Suppress("UNCHECKED_CAST")
    private fun measureMenuWidth(): Int {
        val parent = FrameLayout(context)
        val layoutInflater = context.getLayoutInflater()
        val resources: PopupResources = mAdapter.resources!!

        val itemWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val itemHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)

        var item: BaseItem<Any, BaseItem.ViewHolder<Any>, ItemResources>
        var viewHolder: BaseItem.ViewHolder<*>

        var measuredItemWidth: Int
        var maxItemWidth: Int = mPopupMenuItemMinWidth

        for(i in 0 until mAdapter.itemCount) {
            item = (mAdapter.getItem(i)!! as BaseItem<Any, BaseItem.ViewHolder<Any>, ItemResources>)
            viewHolder = item.init(null, parent, layoutInflater, resources)

            item.bind(null, viewHolder, resources)
            viewHolder.itemView.measure(itemWidthMeasureSpec, itemHeightMeasureSpec)

            measuredItemWidth = viewHolder.itemView.measuredWidth

            if(measuredItemWidth >= mPopupMenuItemMaxWidth) {
                return mPopupMenuItemMaxWidth
            } else if(measuredItemWidth > maxItemWidth) {
                maxItemWidth = measuredItemWidth
            }
        }

        return maxItemWidth
    }


    private fun calculateMenuWidth() {
        val measuredMenuWidth = measureMenuWidth()
        val popupBackground = mPopupWindow.background
        val rectangle = Rect()

        mPopupMenuWidth = if(popupBackground != null) {
            popupBackground.getPadding(rectangle)
            (measuredMenuWidth + rectangle.left + rectangle.right)
        } else {
            measuredMenuWidth
        }
    }


    @Suppress("UNCHECKED_CAST")
    private fun measureMenuHeight(): Int {
        val parent = FrameLayout(context)
        val layoutInflater = context.getLayoutInflater()
        val resources: PopupResources = mAdapter.resources!!
        val itemWidthMeasureSpec: Int = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val maxMenuHeight: Int = mPopupWindow.getMaxAvailableHeight(mAnchorView, verticalOffset)
        var itemHeightMeasureSpec: Int

        var item: BaseItem<Any, BaseItem.ViewHolder<Any>, ItemResources>
        var viewHolder: BaseItem.ViewHolder<*>

        var itemView: View
        var childLayoutParams: ViewGroup.LayoutParams?
        var measuredHeight = 0

        for(i in 0 until mAdapter.itemCount) {
            item = (mAdapter.getItem(i)!! as BaseItem<Any, BaseItem.ViewHolder<Any>, ItemResources>)
            viewHolder = item.init(null, parent, layoutInflater, resources)

            item.bind(null, viewHolder, resources)

            itemView = viewHolder.itemView

            childLayoutParams = itemView.layoutParams

            if(childLayoutParams == null) {
                childLayoutParams = RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                itemView.layoutParams = childLayoutParams
            }

            itemHeightMeasureSpec = if(childLayoutParams.height > 0) {
                View.MeasureSpec.makeMeasureSpec(childLayoutParams.height, View.MeasureSpec.EXACTLY)
            } else {
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            }

            itemView.measure(itemWidthMeasureSpec, itemHeightMeasureSpec)

            measuredHeight += itemView.measuredHeight

            if(childLayoutParams is ViewGroup.MarginLayoutParams) {
                measuredHeight += (childLayoutParams.topMargin + childLayoutParams.bottomMargin)
            }

            if(measuredHeight >= maxMenuHeight) {
                return maxMenuHeight
            }
        }

        return measuredHeight
    }


    /**
     * Shows the popup menu.
     *
     * @param anchorView The view to attach the menu to
     */
    fun show(anchorView: View) {
        mAnchorView = anchorView
        PopupWindowCompat.setWindowLayoutType(mPopupWindow, WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL)

        val recyclerView: RecyclerView = (View.inflate(context, R.layout.popup_menu, null) as RecyclerView)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        mPopupWindow.contentView = recyclerView
        mPopupWindow.isOutsideTouchable = true
        mPopupWindow.width = mPopupMenuWidth
        mPopupWindow.height = measureMenuHeight()
        mPopupWindow.showAsDropDown(mAnchorView, horizontalOffset, verticalOffset, dropdownGravity)
    }


    /**
     * Dismisses the popup menu.
     */
    fun dismiss() {
        mPopupWindow.dismiss()
        mPopupWindow.contentView = null
    }


    /**
     * Adds an item to the menu list.
     *
     * @param item The item to add
     */
    fun addItem(item: BaseItem<*, *, *>) {
        mAdapter.addItem(item)
        calculateMenuWidth()
    }


    override fun applyTheme(theme: Theme) {
        mAdapter.setResources(PopupResources.newInstance(theme))
        ThemingUtil.Popup.menu(this, theme)
    }


    /**
     * Sets a color of the background.
     *
     * @param color The color to set
     */
    fun setBackgroundColor(@ColorInt color: Int) {
        mPopupWindow.setBackgroundDrawable(ColorDrawable(color))
    }


}
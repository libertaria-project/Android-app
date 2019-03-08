package com.stocksexchange.android.ui.utils.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * An item decorator for applying vertical spacing
 * for trades.
 */
class TradesSpacingItemDecorator(
    private val spacing: Int
) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if(parent.getChildAdapterPosition(view) != (parent.adapter!!.itemCount - 1)) {
            outRect.bottom = spacing
        }
    }


}
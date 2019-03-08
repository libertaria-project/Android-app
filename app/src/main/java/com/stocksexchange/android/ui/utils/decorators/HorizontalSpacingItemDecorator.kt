package com.stocksexchange.android.ui.utils.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * An item decorator for applying horizontal spacing
 * between [RecyclerView] items.
 */
open class HorizontalSpacingItemDecorator(
    private val spanCount: Int,
    private val horizontalSpacing: Int
) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)

        if((position % spanCount) == 0) {
            outRect.right = horizontalSpacing
        } else {
            outRect.left = horizontalSpacing
        }
    }


}
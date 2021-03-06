package com.stocksexchange.android.ui.base.adapters.recyclerview

import android.view.View
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.model.markers.Themable
import com.stocksexchange.android.theming.model.Theme

/**
 * A base view holder to extend from.
 */
abstract class BaseViewHolder<Data>(itemView: View) : BaseItem.ViewHolder<Data>(itemView), Themable<Theme> {


    override fun applyTheme(theme: Theme) {
        // Stub
    }


}
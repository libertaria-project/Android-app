package com.stocksexchange.android.ui.views.popupmenu

import com.arthurivanets.adapster.markers.ItemResources
import com.stocksexchange.android.theming.model.Theme

class PopupResources(val theme: Theme) : ItemResources {


    companion object {

        fun newInstance(theme: Theme): PopupResources {
            return PopupResources(theme)
        }

    }


}
package com.stocksexchange.android.ui.drawer

import android.content.Context
import android.view.ViewOutlineProvider
import com.arthurivanets.adapster.markers.ItemResources
import com.stocksexchange.android.R
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.ui.utils.ViewOutlineProviders
import com.stocksexchange.android.ui.utils.extensions.getStatusBarHeight

class NavigationDrawerResources(
    val absentUserIconId: Int,
    val profilePictureOutlineProvider: ViewOutlineProvider,
    val dimensions: List<Int>
) : ItemResources {


    companion object {

        const val DIMENSION_STATUS_BAR_HEIGHT = 0

        const val DIMENSION_PROFILE_PICTURE_MARK_SIZE = 1
        const val DIMENSION_PROFILE_PICTURE_ELEVATION = 2


        fun newInstance(context: Context): NavigationDrawerResources {
            val absentUserIconId = R.mipmap.ic_launcher_large
            val profilePictureOutlineProvider = ViewOutlineProviders.CIRCLE
            val dimensions = listOf(
                context.getStatusBarHeight(),
                context.resources.getDimensionPixelSize(R.dimen.navigation_drawer_header_profile_picture_mark_size),
                context.resources.getDimensionPixelSize(R.dimen.navigation_drawer_header_profile_picture_elevation)
            )

            return NavigationDrawerResources(
                absentUserIconId,
                profilePictureOutlineProvider,
                dimensions
            )
        }

    }


    lateinit var settings: Settings


}
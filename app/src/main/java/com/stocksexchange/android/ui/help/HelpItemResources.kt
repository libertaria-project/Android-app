package com.stocksexchange.android.ui.help

import android.view.animation.Interpolator
import com.arthurivanets.adapster.markers.ItemResources
import com.stocksexchange.android.model.Settings

data class HelpItemResources(
    val animationDuration: Int,
    val animationInterpolator: Interpolator,
    val settings: Settings
) : ItemResources {


    companion object {

        fun newInstance(animationDuration: Int,
                        interpolator: Interpolator, settings: Settings): HelpItemResources {
            return HelpItemResources(
                animationDuration,
                interpolator,
                settings
            )
        }

    }


}
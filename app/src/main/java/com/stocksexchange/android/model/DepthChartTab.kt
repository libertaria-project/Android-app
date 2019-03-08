package com.stocksexchange.android.model

import android.content.res.Resources
import com.stocksexchange.android.R

/**
 * A model class holding data for a depth chart tab.
 */
data class DepthChartTab(
    val position: Int,
    val level: Int
) {


    companion object {

        private const val ALL_ORDERS = Integer.MAX_VALUE

        private const val SECOND_TAB_ORDER_BY_TYPE_LIMIT_ADDITION = 15
        private const val THIRD_TAB_ORDER_BY_TYPE_LIMIT_ADDITION = (SECOND_TAB_ORDER_BY_TYPE_LIMIT_ADDITION + 20)


        /**
         * Retrieves a list of depth chart tabs for a depth level.
         *
         * @param depthLevel The depth level
         *
         * @return The list of tabs
         */
        fun getDepthChartTabsForDepthLevel(depthLevel: Int): List<DepthChartTab> {
            return listOf(
                DepthChartTab(0, depthLevel),
                DepthChartTab(1, (depthLevel + SECOND_TAB_ORDER_BY_TYPE_LIMIT_ADDITION)),
                DepthChartTab(2, (depthLevel + THIRD_TAB_ORDER_BY_TYPE_LIMIT_ADDITION)),
                DepthChartTab(3, ALL_ORDERS)
            )
        }

    }




    /**
     * Retrieves a title of the tab.
     *
     * @param resources The resources
     *
     * @return The title
     */
    fun getTitle(resources: Resources): String {
        return when(level) {
            ALL_ORDERS -> resources.getString(R.string.depth_chart_all_tab_title)
            else -> level.toString()
        }
    }


}
package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.SortPanelTheme

/**
 * A factory producing instances of the [SortPanelTheme] class.
 */
object SortPanelThemeFactory : BaseThemeFactory<SortPanelTheme>() {


    override fun getStubTheme(): SortPanelTheme {
        return SortPanelTheme(
            backgroundColor = -1,
            selectedTitleColor = -1,
            unselectedTitleColor = -1
        )
    }


    override fun getDeepTealTheme(): SortPanelTheme {
        return SortPanelTheme(
            backgroundColor = mColorProvider.getColor(R.color.deepTealSortPanelBackgroundColor),
            selectedTitleColor = mColorProvider.getColor(R.color.deepTealSortPanelSelectedTitleColor),
            unselectedTitleColor = mColorProvider.getColor(R.color.deepTealSortPanelUnselectedTitleColor)
        )
    }


    override fun getNightBlueTheme(): SortPanelTheme {
        return SortPanelTheme(
            backgroundColor = mColorProvider.getColor(R.color.nightBlueSortPanelBackgroundColor),
            selectedTitleColor = mColorProvider.getColor(R.color.nightBlueSortPanelSelectedTitleColor),
            unselectedTitleColor = mColorProvider.getColor(R.color.nightBlueSortPanelUnselectedTitleColor)
        )
    }


    override fun getDarkGreenTheme(): SortPanelTheme {
        return SortPanelTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkGreenSortPanelBackgroundColor),
            selectedTitleColor = mColorProvider.getColor(R.color.darkGreenSortPanelSelectedTitleColor),
            unselectedTitleColor = mColorProvider.getColor(R.color.darkGreenSortPanelUnselectedTitleColor)
        )
    }


    override fun getPitchBlackTheme(): SortPanelTheme {
        return SortPanelTheme(
            backgroundColor = mColorProvider.getColor(R.color.pitchBlackSortPanelBackgroundColor),
            selectedTitleColor = mColorProvider.getColor(R.color.pitchBlackSortPanelSelectedTitleColor),
            unselectedTitleColor = mColorProvider.getColor(R.color.pitchBlackSortPanelUnselectedTitleColor)
        )
    }


    override fun getGrayishBlueTheme(): SortPanelTheme {
        return SortPanelTheme(
            backgroundColor = mColorProvider.getColor(R.color.grayishBlueSortPanelBackgroundColor),
            selectedTitleColor = mColorProvider.getColor(R.color.grayishBlueSortPanelSelectedTitleColor),
            unselectedTitleColor = mColorProvider.getColor(R.color.grayishBlueSortPanelUnselectedTitleColor)
        )
    }


    override fun getBrightGrayTheme(): SortPanelTheme {
        return SortPanelTheme(
            backgroundColor = mColorProvider.getColor(R.color.brightGraySortPanelBackgroundColor),
            selectedTitleColor = mColorProvider.getColor(R.color.brightGraySortPanelSelectedTitleColor),
            unselectedTitleColor = mColorProvider.getColor(R.color.brightGraySortPanelUnselectedTitleColor)
        )
    }


    override fun getDarkSilverTheme(): SortPanelTheme {
        return SortPanelTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkSilverSortPanelBackgroundColor),
            selectedTitleColor = mColorProvider.getColor(R.color.darkSilverSortPanelSelectedTitleColor),
            unselectedTitleColor = mColorProvider.getColor(R.color.darkSilverSortPanelUnselectedTitleColor)
        )
    }


    override fun getBrowneeTheme(): SortPanelTheme {
        return SortPanelTheme(
            backgroundColor = mColorProvider.getColor(R.color.browneeSortPanelBackgroundColor),
            selectedTitleColor = mColorProvider.getColor(R.color.browneeSortPanelSelectedTitleColor),
            unselectedTitleColor = mColorProvider.getColor(R.color.browneeSortPanelUnselectedTitleColor)
        )
    }


    override fun getVioletTheme(): SortPanelTheme {
        return SortPanelTheme(
            backgroundColor = mColorProvider.getColor(R.color.violetSortPanelBackgroundColor),
            selectedTitleColor = mColorProvider.getColor(R.color.violetSortPanelSelectedTitleColor),
            unselectedTitleColor = mColorProvider.getColor(R.color.violetSortPanelUnselectedTitleColor)
        )
    }


    override fun getPurpleTheme(): SortPanelTheme {
        return SortPanelTheme(
            backgroundColor = mColorProvider.getColor(R.color.purpleSortPanelBackgroundColor),
            selectedTitleColor = mColorProvider.getColor(R.color.purpleSortPanelSelectedTitleColor),
            unselectedTitleColor = mColorProvider.getColor(R.color.purpleSortPanelUnselectedTitleColor)
        )
    }


    override fun getBlueZodiacTheme(): SortPanelTheme {
        return SortPanelTheme(
            backgroundColor = mColorProvider.getColor(R.color.blueZodiacSortPanelBackgroundColor),
            selectedTitleColor = mColorProvider.getColor(R.color.blueZodiacSortPanelSelectedTitleColor),
            unselectedTitleColor = mColorProvider.getColor(R.color.blueZodiacSortPanelUnselectedTitleColor)
        )
    }


    override fun getBubbleBlueTheme(): SortPanelTheme {
        return SortPanelTheme(
            backgroundColor = mColorProvider.getColor(R.color.bubbleBlueSortPanelBackgroundColor),
            selectedTitleColor = mColorProvider.getColor(R.color.bubbleBlueSortPanelSelectedTitleColor),
            unselectedTitleColor = mColorProvider.getColor(R.color.bubbleBlueSortPanelUnselectedTitleColor)
        )
    }


}
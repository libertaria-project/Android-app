package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.PopupMenuTheme

/**
 * A factory producing instances of the [PopupMenuTheme] class.
 */
object PopupMenuThemeFactory : BaseThemeFactory<PopupMenuTheme>() {


    override fun getStubTheme(): PopupMenuTheme {
        return PopupMenuTheme(
            backgroundColor = -1,
            titleColor = -1
        )
    }


    override fun getDeepTealTheme(): PopupMenuTheme {
        return PopupMenuTheme(
            backgroundColor = mColorProvider.getColor(R.color.deepTealPopupMenuBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.deepTealPopupMenuTitleColor)
        )
    }


    override fun getNightBlueTheme(): PopupMenuTheme {
        return PopupMenuTheme(
            backgroundColor = mColorProvider.getColor(R.color.nightBluePopupMenuBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.nightBluePopupMenuTitleColor)
        )
    }


    override fun getDarkGreenTheme(): PopupMenuTheme {
        return PopupMenuTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkGreenPopupMenuBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.darkGreenPopupMenuTitleColor)
        )
    }


    override fun getPitchBlackTheme(): PopupMenuTheme {
        return PopupMenuTheme(
            backgroundColor = mColorProvider.getColor(R.color.pitchBlackPopupMenuBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.pitchBlackPopupMenuTitleColor)
        )
    }


    override fun getGrayishBlueTheme(): PopupMenuTheme {
        return PopupMenuTheme(
            backgroundColor = mColorProvider.getColor(R.color.grayishBluePopupMenuBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.grayishBluePopupMenuTitleColor)
        )
    }


    override fun getBrightGrayTheme(): PopupMenuTheme {
        return PopupMenuTheme(
            backgroundColor = mColorProvider.getColor(R.color.brightGrayPopupMenuBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.brightGrayPopupMenuTitleColor)
        )
    }


    override fun getDarkSilverTheme(): PopupMenuTheme {
        return PopupMenuTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkSilverPopupMenuBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.darkSilverPopupMenuTitleColor)
        )
    }


    override fun getBrowneeTheme(): PopupMenuTheme {
        return PopupMenuTheme(
            backgroundColor = mColorProvider.getColor(R.color.browneePopupMenuBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.browneePopupMenuTitleColor)
        )
    }


    override  fun getVioletTheme(): PopupMenuTheme {
        return PopupMenuTheme(
            backgroundColor = mColorProvider.getColor(R.color.violetPopupMenuBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.violetPopupMenuTitleColor)
        )
    }


    override fun getPurpleTheme(): PopupMenuTheme {
        return PopupMenuTheme(
            backgroundColor = mColorProvider.getColor(R.color.purplePopupMenuBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.purplePopupMenuTitleColor)
        )
    }


    override fun getBlueZodiacTheme(): PopupMenuTheme {
        return PopupMenuTheme(
            backgroundColor = mColorProvider.getColor(R.color.blueZodiacPopupMenuBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.blueZodiacPopupMenuTitleColor)
        )
    }


    override fun getBubbleBlueTheme(): PopupMenuTheme {
        return PopupMenuTheme(
            backgroundColor = mColorProvider.getColor(R.color.bubbleBluePopupMenuBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.bubbleBluePopupMenuTitleColor)
        )
    }


}
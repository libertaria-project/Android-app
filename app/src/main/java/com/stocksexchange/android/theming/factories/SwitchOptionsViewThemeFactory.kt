package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.SwitchOptionsViewTheme

/**
 * A factory producing instances of the [SwitchOptionsViewTheme] class.
 */
object SwitchOptionsViewThemeFactory : BaseThemeFactory<SwitchOptionsViewTheme>() {


    override fun getStubTheme(): SwitchOptionsViewTheme {
        return SwitchOptionsViewTheme(
            titleTextColor = -1,
            switchColor = -1
        )
    }


    override fun getDeepTealTheme(): SwitchOptionsViewTheme {
        return SwitchOptionsViewTheme(
            titleTextColor = mColorProvider.getColor(R.color.deepTealSwitchOptionsViewTitleTextColor),
            switchColor = mColorProvider.getColor(R.color.deepTealSwitchOptionsViewSwitchColor)
        )
    }


    override fun getNightBlueTheme(): SwitchOptionsViewTheme {
        return SwitchOptionsViewTheme(
            titleTextColor = mColorProvider.getColor(R.color.nightBlueSwitchOptionsViewTitleTextColor),
            switchColor = mColorProvider.getColor(R.color.nightBlueSwitchOptionsViewSwitchColor)
        )
    }


    override fun getDarkGreenTheme(): SwitchOptionsViewTheme {
        return SwitchOptionsViewTheme(
            titleTextColor = mColorProvider.getColor(R.color.darkGreenSwitchOptionsViewTitleTextColor),
            switchColor = mColorProvider.getColor(R.color.darkGreenSwitchOptionsViewSwitchColor)
        )
    }


    override fun getPitchBlackTheme(): SwitchOptionsViewTheme {
        return SwitchOptionsViewTheme(
            titleTextColor = mColorProvider.getColor(R.color.pitchBlackSwitchOptionsViewTitleTextColor),
            switchColor = mColorProvider.getColor(R.color.pitchBlackSwitchOptionsViewSwitchColor)
        )
    }


    override fun getGrayishBlueTheme(): SwitchOptionsViewTheme {
        return SwitchOptionsViewTheme(
            titleTextColor = mColorProvider.getColor(R.color.grayishBlueSwitchOptionsViewTitleTextColor),
            switchColor = mColorProvider.getColor(R.color.grayishBlueSwitchOptionsViewSwitchColor)
        )
    }

    override fun getBrightGrayTheme(): SwitchOptionsViewTheme {
        return SwitchOptionsViewTheme(
            titleTextColor = mColorProvider.getColor(R.color.brightGraySwitchOptionsViewTitleTextColor),
            switchColor = mColorProvider.getColor(R.color.brightGraySwitchOptionsViewSwitchColor)
        )
    }


    override fun getDarkSilverTheme(): SwitchOptionsViewTheme {
        return SwitchOptionsViewTheme(
            titleTextColor = mColorProvider.getColor(R.color.darkSilverSwitchOptionsViewTitleTextColor),
            switchColor = mColorProvider.getColor(R.color.darkSilverSwitchOptionsViewSwitchColor)
        )
    }


    override fun getBrowneeTheme(): SwitchOptionsViewTheme {
        return SwitchOptionsViewTheme(
            titleTextColor = mColorProvider.getColor(R.color.browneeSwitchOptionsViewTitleTextColor),
            switchColor = mColorProvider.getColor(R.color.browneeSwitchOptionsViewSwitchColor)
        )
    }


    override fun getVioletTheme(): SwitchOptionsViewTheme {
        return SwitchOptionsViewTheme(
            titleTextColor = mColorProvider.getColor(R.color.violetSwitchOptionsViewTitleTextColor),
            switchColor = mColorProvider.getColor(R.color.violetSwitchOptionsViewSwitchColor)
        )
    }


    override fun getPurpleTheme(): SwitchOptionsViewTheme {
        return SwitchOptionsViewTheme(
            titleTextColor = mColorProvider.getColor(R.color.purpleSwitchOptionsViewTitleTextColor),
            switchColor = mColorProvider.getColor(R.color.purpleSwitchOptionsViewSwitchColor)
        )
    }


    override fun getBlueZodiacTheme(): SwitchOptionsViewTheme {
        return SwitchOptionsViewTheme(
            titleTextColor = mColorProvider.getColor(R.color.blueZodiacSwitchOptionsViewTitleTextColor),
            switchColor = mColorProvider.getColor(R.color.blueZodiacSwitchOptionsViewSwitchColor)
        )
    }


    override fun getBubbleBlueTheme(): SwitchOptionsViewTheme {
        return SwitchOptionsViewTheme(
            titleTextColor = mColorProvider.getColor(R.color.bubbleBlueSwitchOptionsViewTitleTextColor),
            switchColor = mColorProvider.getColor(R.color.bubbleBlueSwitchOptionsViewSwitchColor)
        )
    }


}
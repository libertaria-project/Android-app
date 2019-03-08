package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.DottedMapViewTheme

/**
 * A factory producing instances of the [DottedMapViewTheme] class.
 */
object DottedMapViewThemeFactory : BaseThemeFactory<DottedMapViewTheme>() {


    override fun getStubTheme(): DottedMapViewTheme {
        return DottedMapViewTheme(
            titleColor = -1,
            textColor = -1
        )
    }


    override fun getDeepTealTheme(): DottedMapViewTheme {
        return DottedMapViewTheme(
            titleColor = mColorProvider.getColor(R.color.deepTealDottedMapViewTitleColor),
            textColor = mColorProvider.getColor(R.color.deepTealDottedMapViewTextColor)
        )
    }


    override fun getNightBlueTheme(): DottedMapViewTheme {
        return DottedMapViewTheme(
            titleColor = mColorProvider.getColor(R.color.nightBlueDottedMapViewTitleColor),
            textColor = mColorProvider.getColor(R.color.nightBlueDottedMapViewTextColor)
        )
    }


    override fun getDarkGreenTheme(): DottedMapViewTheme {
        return DottedMapViewTheme(
            titleColor = mColorProvider.getColor(R.color.darkGreenDottedMapViewTitleColor),
            textColor = mColorProvider.getColor(R.color.darkGreenDottedMapViewTextColor)
        )
    }


    override fun getPitchBlackTheme(): DottedMapViewTheme {
        return DottedMapViewTheme(
            titleColor = mColorProvider.getColor(R.color.pitchBlackDottedMapViewTitleColor),
            textColor = mColorProvider.getColor(R.color.pitchBlackDottedMapViewTextColor)
        )
    }


    override fun getGrayishBlueTheme(): DottedMapViewTheme {
        return DottedMapViewTheme(
            titleColor = mColorProvider.getColor(R.color.grayishBlueDottedMapViewTitleColor),
            textColor = mColorProvider.getColor(R.color.grayishBlueDottedMapViewTextColor)
        )
    }


    override fun getBrightGrayTheme(): DottedMapViewTheme {
        return DottedMapViewTheme(
            titleColor = mColorProvider.getColor(R.color.brightGrayDottedMapViewTitleColor),
            textColor = mColorProvider.getColor(R.color.brightGrayDottedMapViewTextColor)
        )
    }


    override fun getDarkSilverTheme(): DottedMapViewTheme {
        return DottedMapViewTheme(
            titleColor = mColorProvider.getColor(R.color.darkSilverDottedMapViewTitleColor),
            textColor = mColorProvider.getColor(R.color.darkSilverDottedMapViewTextColor)
        )
    }


    override fun getBrowneeTheme(): DottedMapViewTheme {
        return DottedMapViewTheme(
            titleColor = mColorProvider.getColor(R.color.browneeDottedMapViewTitleColor),
            textColor = mColorProvider.getColor(R.color.browneeDottedMapViewTextColor)
        )
    }


    override fun getVioletTheme(): DottedMapViewTheme {
        return DottedMapViewTheme(
            titleColor = mColorProvider.getColor(R.color.violetDottedMapViewTitleColor),
            textColor = mColorProvider.getColor(R.color.violetDottedMapViewTextColor)
        )
    }


    override fun getPurpleTheme(): DottedMapViewTheme {
        return DottedMapViewTheme(
            titleColor = mColorProvider.getColor(R.color.purpleDottedMapViewTitleColor),
            textColor = mColorProvider.getColor(R.color.purpleDottedMapViewTextColor)
        )
    }


    override fun getBlueZodiacTheme(): DottedMapViewTheme {
        return DottedMapViewTheme(
            titleColor = mColorProvider.getColor(R.color.blueZodiacDottedMapViewTitleColor),
            textColor = mColorProvider.getColor(R.color.blueZodiacDottedMapViewTextColor)
        )
    }


    override fun getBubbleBlueTheme(): DottedMapViewTheme {
        return DottedMapViewTheme(
            titleColor = mColorProvider.getColor(R.color.bubbleBlueDottedMapViewTitleColor),
            textColor = mColorProvider.getColor(R.color.bubbleBlueDottedMapViewTextColor)
        )
    }


}
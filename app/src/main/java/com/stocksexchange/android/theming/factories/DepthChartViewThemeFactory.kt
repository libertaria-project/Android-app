package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.DepthChartViewTheme

/**
 * A factory producing instances of the [DepthChartViewTheme] class.
 */
object DepthChartViewThemeFactory : BaseThemeFactory<DepthChartViewTheme>() {


    override fun getStubTheme(): DepthChartViewTheme {
        return DepthChartViewTheme(
            backgroundColor = -1,
            progressBarColor = -1,
            infoViewColor = -1,
            infoFieldsDefaultTextColor = -1,
            axisGridColor = -1,
            highlighterColor = -1,
            positiveColor = -1,
            negativeColor = -1,
            neutralColor = -1,
            tabTextColor = -1,
            tabIndicatorColor = -1
        )
    }


    override fun getDeepTealTheme(): DepthChartViewTheme {
        return DepthChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.deepTealDepthChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.deepTealDepthChartViewProgressbarColor),
            infoViewColor = mColorProvider.getColor(R.color.deepTealDepthChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.deepTealDepthChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.deepTealDepthChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.deepTealDepthChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.deepTealDepthChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.deepTealDepthChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.deepTealDepthChartViewNeutralColor),
            tabTextColor = mColorProvider.getColor(R.color.deepTealDepthChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.deepTealDepthChartViewTabIndicatorColor)
        )
    }


    override fun getNightBlueTheme(): DepthChartViewTheme {
        return DepthChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.nightBlueDepthChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.nightBlueDepthChartViewProgressbarColor),
            infoViewColor = mColorProvider.getColor(R.color.nightBlueDepthChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.nightBlueDepthChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.nightBlueDepthChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.nightBlueDepthChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.nightBlueDepthChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.nightBlueDepthChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.nightBlueDepthChartViewNeutralColor),
            tabTextColor = mColorProvider.getColor(R.color.nightBlueDepthChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.nightBlueDepthChartViewTabIndicatorColor)
        )
    }


    override fun getDarkGreenTheme(): DepthChartViewTheme {
        return DepthChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkGreenDepthChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.darkGreenDepthChartViewProgressbarColor),
            infoViewColor = mColorProvider.getColor(R.color.darkGreenDepthChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.darkGreenDepthChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.darkGreenDepthChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.darkGreenDepthChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.darkGreenDepthChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.darkGreenDepthChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.darkGreenDepthChartViewNeutralColor),
            tabTextColor = mColorProvider.getColor(R.color.darkGreenDepthChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.darkGreenDepthChartViewTabIndicatorColor)
        )
    }


    override fun getPitchBlackTheme(): DepthChartViewTheme {
        return DepthChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.pitchBlackDepthChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.pitchBlackDepthChartViewProgressbarColor),
            infoViewColor = mColorProvider.getColor(R.color.pitchBlackDepthChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.pitchBlackDepthChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.pitchBlackDepthChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.pitchBlackDepthChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.pitchBlackDepthChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.pitchBlackDepthChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.pitchBlackDepthChartViewNeutralColor),
            tabTextColor = mColorProvider.getColor(R.color.pitchBlackDepthChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.pitchBlackDepthChartViewTabIndicatorColor)
        )
    }


    override fun getGrayishBlueTheme(): DepthChartViewTheme {
        return DepthChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.grayishBlueDepthChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.grayishBlueDepthChartViewProgressbarColor),
            infoViewColor = mColorProvider.getColor(R.color.grayishBlueDepthChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.grayishBlueDepthChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.grayishBlueDepthChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.grayishBlueDepthChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.grayishBlueDepthChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.grayishBlueDepthChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.grayishBlueDepthChartViewNeutralColor),
            tabTextColor = mColorProvider.getColor(R.color.grayishBlueDepthChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.grayishBlueDepthChartViewTabIndicatorColor)
        )
    }


    override fun getBrightGrayTheme(): DepthChartViewTheme {
        return DepthChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.brightGrayDepthChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.brightGrayDepthChartViewProgressbarColor),
            infoViewColor = mColorProvider.getColor(R.color.brightGrayDepthChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.brightGrayDepthChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.brightGrayDepthChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.brightGrayDepthChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.brightGrayDepthChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.brightGrayDepthChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.brightGrayDepthChartViewNeutralColor),
            tabTextColor = mColorProvider.getColor(R.color.brightGrayDepthChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.brightGrayDepthChartViewTabIndicatorColor)
        )
    }


    override fun getDarkSilverTheme(): DepthChartViewTheme {
        return DepthChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkSilverDepthChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.darkSilverDepthChartViewProgressbarColor),
            infoViewColor = mColorProvider.getColor(R.color.darkSilverDepthChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.darkSilverDepthChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.darkSilverDepthChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.darkSilverDepthChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.darkSilverDepthChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.darkSilverDepthChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.darkSilverDepthChartViewNeutralColor),
            tabTextColor = mColorProvider.getColor(R.color.darkSilverDepthChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.darkSilverDepthChartViewTabIndicatorColor)
        )
    }


    override fun getBrowneeTheme(): DepthChartViewTheme {
        return DepthChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.browneeDepthChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.browneeDepthChartViewProgressbarColor),
            infoViewColor = mColorProvider.getColor(R.color.browneeDepthChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.browneeDepthChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.browneeDepthChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.browneeDepthChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.browneeDepthChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.browneeDepthChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.browneeDepthChartViewNeutralColor),
            tabTextColor = mColorProvider.getColor(R.color.browneeDepthChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.browneeDepthChartViewTabIndicatorColor)
        )
    }


    override fun getVioletTheme(): DepthChartViewTheme {
        return DepthChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.violetDepthChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.violetDepthChartViewProgressbarColor),
            infoViewColor = mColorProvider.getColor(R.color.violetDepthChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.violetDepthChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.violetDepthChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.violetDepthChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.violetDepthChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.violetDepthChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.violetDepthChartViewNeutralColor),
            tabTextColor = mColorProvider.getColor(R.color.violetDepthChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.violetDepthChartViewTabIndicatorColor)
        )
    }


    override fun getPurpleTheme(): DepthChartViewTheme {
        return DepthChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.purpleDepthChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.purpleDepthChartViewProgressbarColor),
            infoViewColor = mColorProvider.getColor(R.color.purpleDepthChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.purpleDepthChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.purpleDepthChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.purpleDepthChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.purpleDepthChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.purpleDepthChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.purpleDepthChartViewNeutralColor),
            tabTextColor = mColorProvider.getColor(R.color.purpleDepthChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.purpleDepthChartViewTabIndicatorColor)
        )
    }


    override fun getBlueZodiacTheme(): DepthChartViewTheme {
        return DepthChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.blueZodiacDepthChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.blueZodiacDepthChartViewProgressbarColor),
            infoViewColor = mColorProvider.getColor(R.color.blueZodiacDepthChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.blueZodiacDepthChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.blueZodiacDepthChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.blueZodiacDepthChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.blueZodiacDepthChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.blueZodiacDepthChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.blueZodiacDepthChartViewNeutralColor),
            tabTextColor = mColorProvider.getColor(R.color.blueZodiacDepthChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.blueZodiacDepthChartViewTabIndicatorColor)
        )
    }


    override fun getBubbleBlueTheme(): DepthChartViewTheme {
        return DepthChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.bubbleBlueDepthChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.bubbleBlueDepthChartViewProgressbarColor),
            infoViewColor = mColorProvider.getColor(R.color.bubbleBlueDepthChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.bubbleBlueDepthChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.bubbleBlueDepthChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.bubbleBlueDepthChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.bubbleBlueDepthChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.bubbleBlueDepthChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.bubbleBlueDepthChartViewNeutralColor),
            tabTextColor = mColorProvider.getColor(R.color.bubbleBlueDepthChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.bubbleBlueDepthChartViewTabIndicatorColor)
        )
    }


}
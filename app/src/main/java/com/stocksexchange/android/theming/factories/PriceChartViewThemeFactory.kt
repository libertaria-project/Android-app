package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.PriceChartViewTheme

/**
 * A factory producing instances of the [PriceChartViewTheme] class.
 */
object PriceChartViewThemeFactory : BaseThemeFactory<PriceChartViewTheme>() {


    override fun getStubTheme(): PriceChartViewTheme {
        return PriceChartViewTheme(
            backgroundColor = -1,
            progressBarColor = -1,
            infoViewColor = -1,
            infoFieldsDefaultTextColor = -1,
            axisGridColor = -1,
            highlighterColor = -1,
            positiveColor = -1,
            negativeColor = -1,
            neutralColor = -1,
            volumeBarsColor = -1,
            candleStickShadowColor = -1,
            tabTextColor = -1,
            tabIndicatorColor = -1
        )
    }


    override fun getDeepTealTheme(): PriceChartViewTheme {
        return PriceChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.deepTealPriceChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.deepTealPriceChartViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.deepTealPriceChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.deepTealPriceChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.deepTealPriceChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.deepTealPriceChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.deepTealPriceChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.deepTealPriceChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.deepTealPriceChartViewNeutralColor),
            volumeBarsColor = mColorProvider.getColor(R.color.deepTealPriceChartViewVolumeBarsColor),
            candleStickShadowColor = mColorProvider.getColor(R.color.deepTealPriceChartViewCandleStickShadowColor),
            tabTextColor = mColorProvider.getColor(R.color.deepTealPriceChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.deepTealPriceChartViewTabIndicatorColor)
        )
    }


    override fun getNightBlueTheme(): PriceChartViewTheme {
        return PriceChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.nightBluePriceChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.nightBluePriceChartViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.nightBluePriceChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.nightBluePriceChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.nightBluePriceChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.nightBluePriceChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.nightBluePriceChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.nightBluePriceChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.nightBluePriceChartViewNeutralColor),
            volumeBarsColor = mColorProvider.getColor(R.color.nightBluePriceChartViewVolumeBarsColor),
            candleStickShadowColor = mColorProvider.getColor(R.color.nightBluePriceChartViewCandleStickShadowColor),
            tabTextColor = mColorProvider.getColor(R.color.nightBluePriceChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.nightBluePriceChartViewTabIndicatorColor)
        )
    }


    override fun getDarkGreenTheme(): PriceChartViewTheme {
        return PriceChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkGreenPriceChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.darkGreenPriceChartViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.darkGreenPriceChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.darkGreenPriceChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.darkGreenPriceChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.darkGreenPriceChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.darkGreenPriceChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.darkGreenPriceChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.darkGreenPriceChartViewNeutralColor),
            volumeBarsColor = mColorProvider.getColor(R.color.darkGreenPriceChartViewVolumeBarsColor),
            candleStickShadowColor = mColorProvider.getColor(R.color.darkGreenPriceChartViewCandleStickShadowColor),
            tabTextColor = mColorProvider.getColor(R.color.darkGreenPriceChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.darkGreenPriceChartViewTabIndicatorColor)
        )
    }


    override fun getPitchBlackTheme(): PriceChartViewTheme {
        return PriceChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.pitchBlackPriceChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.pitchBlackPriceChartViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.pitchBlackPriceChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.pitchBlackPriceChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.pitchBlackPriceChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.pitchBlackPriceChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.pitchBlackPriceChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.pitchBlackPriceChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.pitchBlackPriceChartViewNeutralColor),
            volumeBarsColor = mColorProvider.getColor(R.color.pitchBlackPriceChartViewVolumeBarsColor),
            candleStickShadowColor = mColorProvider.getColor(R.color.pitchBlackPriceChartViewCandleStickShadowColor),
            tabTextColor = mColorProvider.getColor(R.color.pitchBlackPriceChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.pitchBlackPriceChartViewTabIndicatorColor)
        )
    }


    override fun getGrayishBlueTheme(): PriceChartViewTheme {
        return PriceChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.grayishBluePriceChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.grayishBluePriceChartViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.grayishBluePriceChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.grayishBluePriceChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.grayishBluePriceChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.grayishBluePriceChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.grayishBluePriceChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.grayishBluePriceChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.grayishBluePriceChartViewNeutralColor),
            volumeBarsColor = mColorProvider.getColor(R.color.grayishBluePriceChartViewVolumeBarsColor),
            candleStickShadowColor = mColorProvider.getColor(R.color.grayishBluePriceChartViewCandleStickShadowColor),
            tabTextColor = mColorProvider.getColor(R.color.grayishBluePriceChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.grayishBluePriceChartViewTabIndicatorColor)
        )
    }


    override fun getBrightGrayTheme(): PriceChartViewTheme {
        return PriceChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.brightGrayPriceChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.brightGrayPriceChartViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.brightGrayPriceChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.brightGrayPriceChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.brightGrayPriceChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.brightGrayPriceChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.brightGrayPriceChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.brightGrayPriceChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.brightGrayPriceChartViewNeutralColor),
            volumeBarsColor = mColorProvider.getColor(R.color.brightGrayPriceChartViewVolumeBarsColor),
            candleStickShadowColor = mColorProvider.getColor(R.color.brightGrayPriceChartViewCandleStickShadowColor),
            tabTextColor = mColorProvider.getColor(R.color.brightGrayPriceChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.brightGrayPriceChartViewTabIndicatorColor)
        )
    }


    override fun getDarkSilverTheme(): PriceChartViewTheme {
        return PriceChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkSilverPriceChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.darkSilverPriceChartViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.darkSilverPriceChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.darkSilverPriceChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.darkSilverPriceChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.darkSilverPriceChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.darkSilverPriceChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.darkSilverPriceChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.darkSilverPriceChartViewNeutralColor),
            volumeBarsColor = mColorProvider.getColor(R.color.darkSilverPriceChartViewVolumeBarsColor),
            candleStickShadowColor = mColorProvider.getColor(R.color.darkSilverPriceChartViewCandleStickShadowColor),
            tabTextColor = mColorProvider.getColor(R.color.darkSilverPriceChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.darkSilverPriceChartViewTabIndicatorColor)
        )
    }


    override fun getBrowneeTheme(): PriceChartViewTheme {
        return PriceChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.browneePriceChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.browneePriceChartViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.browneePriceChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.browneePriceChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.browneePriceChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.browneePriceChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.browneePriceChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.browneePriceChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.browneePriceChartViewNeutralColor),
            volumeBarsColor = mColorProvider.getColor(R.color.browneePriceChartViewVolumeBarsColor),
            candleStickShadowColor = mColorProvider.getColor(R.color.browneePriceChartViewCandleStickShadowColor),
            tabTextColor = mColorProvider.getColor(R.color.browneePriceChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.browneePriceChartViewTabIndicatorColor)
        )
    }


    override fun getVioletTheme(): PriceChartViewTheme {
        return PriceChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.violetPriceChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.violetPriceChartViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.violetPriceChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.violetPriceChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.violetPriceChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.violetPriceChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.violetPriceChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.violetPriceChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.violetPriceChartViewNeutralColor),
            volumeBarsColor = mColorProvider.getColor(R.color.violetPriceChartViewVolumeBarsColor),
            candleStickShadowColor = mColorProvider.getColor(R.color.violetPriceChartViewCandleStickShadowColor),
            tabTextColor = mColorProvider.getColor(R.color.violetPriceChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.violetPriceChartViewTabIndicatorColor)
        )
    }


    override fun getPurpleTheme(): PriceChartViewTheme {
        return PriceChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.purplePriceChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.purplePriceChartViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.purplePriceChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.purplePriceChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.purplePriceChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.purplePriceChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.purplePriceChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.purplePriceChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.purplePriceChartViewNeutralColor),
            volumeBarsColor = mColorProvider.getColor(R.color.purplePriceChartViewVolumeBarsColor),
            candleStickShadowColor = mColorProvider.getColor(R.color.purplePriceChartViewCandleStickShadowColor),
            tabTextColor = mColorProvider.getColor(R.color.purplePriceChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.purplePriceChartViewTabIndicatorColor)
        )
    }


    override fun getBlueZodiacTheme(): PriceChartViewTheme {
        return PriceChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.blueZodiacPriceChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.blueZodiacPriceChartViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.blueZodiacPriceChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.blueZodiacPriceChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.blueZodiacPriceChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.blueZodiacPriceChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.blueZodiacPriceChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.blueZodiacPriceChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.blueZodiacPriceChartViewNeutralColor),
            volumeBarsColor = mColorProvider.getColor(R.color.blueZodiacPriceChartViewVolumeBarsColor),
            candleStickShadowColor = mColorProvider.getColor(R.color.blueZodiacPriceChartViewCandleStickShadowColor),
            tabTextColor = mColorProvider.getColor(R.color.blueZodiacPriceChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.blueZodiacPriceChartViewTabIndicatorColor)
        )
    }


    override fun getBubbleBlueTheme(): PriceChartViewTheme {
        return PriceChartViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.bubbleBluePriceChartViewBackgroundColor),
            progressBarColor = mColorProvider.getColor(R.color.bubbleBluePriceChartViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.bubbleBluePriceChartViewInfoViewColor),
            infoFieldsDefaultTextColor = mColorProvider.getColor(R.color.bubbleBluePriceChartViewInfoFieldsDefaultTextColor),
            axisGridColor = mColorProvider.getColor(R.color.bubbleBluePriceChartViewAxisGridColor),
            highlighterColor = mColorProvider.getColor(R.color.bubbleBluePriceChartViewHighlighterColor),
            positiveColor = mColorProvider.getColor(R.color.bubbleBluePriceChartViewPositiveColor),
            negativeColor = mColorProvider.getColor(R.color.bubbleBluePriceChartViewNegativeColor),
            neutralColor = mColorProvider.getColor(R.color.bubbleBluePriceChartViewNeutralColor),
            volumeBarsColor = mColorProvider.getColor(R.color.bubbleBluePriceChartViewVolumeBarsColor),
            candleStickShadowColor = mColorProvider.getColor(R.color.bubbleBluePriceChartViewCandleStickShadowColor),
            tabTextColor = mColorProvider.getColor(R.color.bubbleBluePriceChartViewTabTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.bubbleBluePriceChartViewTabIndicatorColor)
        )
    }


}
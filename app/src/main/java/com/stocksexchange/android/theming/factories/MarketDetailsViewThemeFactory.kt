package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.MarketDetailsViewTheme

/**
 * A factory producing instances of the [MarketDetailsViewTheme] class.
 */
object MarketDetailsViewThemeFactory : BaseThemeFactory<MarketDetailsViewTheme>() {


    override fun getStubTheme(): MarketDetailsViewTheme {
        return MarketDetailsViewTheme(
            backgroundColor = -1,
            itemTitleColor = -1,
            itemValueColor = -1,
            itemSeparatorColor = -1,
            positiveStatusColor = -1,
            negativeStatusColor = -1
        )
    }


    override fun getDeepTealTheme(): MarketDetailsViewTheme {
        return MarketDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.deepTealMarketDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.deepTealMarketDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.deepTealMarketDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.deepTealMarketDetailsViewItemSeparatorColor),
            positiveStatusColor = mColorProvider.getColor(R.color.deepTealMarketDetailsViewPositiveStatusColor),
            negativeStatusColor = mColorProvider.getColor(R.color.deepTealMarketDetailsViewNegativeStatusColor)
        )
    }


    override fun getNightBlueTheme(): MarketDetailsViewTheme {
        return MarketDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.nightBlueMarketDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.nightBlueMarketDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.nightBlueMarketDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.nightBlueMarketDetailsViewItemSeparatorColor),
            positiveStatusColor = mColorProvider.getColor(R.color.nightBlueMarketDetailsViewPositiveStatusColor),
            negativeStatusColor = mColorProvider.getColor(R.color.nightBlueMarketDetailsViewNegativeStatusColor)
        )
    }


    override fun getDarkGreenTheme(): MarketDetailsViewTheme {
        return MarketDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkGreenMarketDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.darkGreenMarketDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.darkGreenMarketDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.darkGreenMarketDetailsViewItemSeparatorColor),
            positiveStatusColor = mColorProvider.getColor(R.color.darkGreenMarketDetailsViewPositiveStatusColor),
            negativeStatusColor = mColorProvider.getColor(R.color.darkGreenMarketDetailsViewNegativeStatusColor)
        )
    }


    override fun getPitchBlackTheme(): MarketDetailsViewTheme {
        return MarketDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.pitchBlackMarketDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.pitchBlackMarketDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.pitchBlackMarketDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.pitchBlackMarketDetailsViewItemSeparatorColor),
            positiveStatusColor = mColorProvider.getColor(R.color.pitchBlackMarketDetailsViewPositiveStatusColor),
            negativeStatusColor = mColorProvider.getColor(R.color.pitchBlackMarketDetailsViewNegativeStatusColor)
        )
    }


    override fun getGrayishBlueTheme(): MarketDetailsViewTheme {
        return MarketDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.grayishBlueMarketDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.grayishBlueMarketDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.grayishBlueMarketDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.grayishBlueMarketDetailsViewItemSeparatorColor),
            positiveStatusColor = mColorProvider.getColor(R.color.grayishBlueMarketDetailsViewPositiveStatusColor),
            negativeStatusColor = mColorProvider.getColor(R.color.grayishBlueMarketDetailsViewNegativeStatusColor)
        )
    }


    override fun getBrightGrayTheme(): MarketDetailsViewTheme {
        return MarketDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.brightGrayMarketDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.brightGrayMarketDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.brightGrayMarketDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.brightGrayMarketDetailsViewItemSeparatorColor),
            positiveStatusColor = mColorProvider.getColor(R.color.brightGrayMarketDetailsViewPositiveStatusColor),
            negativeStatusColor = mColorProvider.getColor(R.color.brightGrayMarketDetailsViewNegativeStatusColor)
        )
    }

    override fun getDarkSilverTheme(): MarketDetailsViewTheme {
        return MarketDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkSilverMarketDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.darkSilverMarketDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.darkSilverMarketDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.darkSilverMarketDetailsViewItemSeparatorColor),
            positiveStatusColor = mColorProvider.getColor(R.color.darkSilverMarketDetailsViewPositiveStatusColor),
            negativeStatusColor = mColorProvider.getColor(R.color.darkSilverMarketDetailsViewNegativeStatusColor)
        )
    }


    override fun getBrowneeTheme(): MarketDetailsViewTheme {
        return MarketDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.browneeMarketDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.browneeMarketDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.browneeMarketDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.browneeMarketDetailsViewItemSeparatorColor),
            positiveStatusColor = mColorProvider.getColor(R.color.browneeMarketDetailsViewPositiveStatusColor),
            negativeStatusColor = mColorProvider.getColor(R.color.browneeMarketDetailsViewNegativeStatusColor)
        )
    }


    override fun getVioletTheme(): MarketDetailsViewTheme {
        return MarketDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.violetMarketDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.violetMarketDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.violetMarketDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.violetMarketDetailsViewItemSeparatorColor),
            positiveStatusColor = mColorProvider.getColor(R.color.violetMarketDetailsViewPositiveStatusColor),
            negativeStatusColor = mColorProvider.getColor(R.color.violetMarketDetailsViewNegativeStatusColor)
        )
    }


    override fun getPurpleTheme(): MarketDetailsViewTheme {
        return MarketDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.purpleMarketDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.purpleMarketDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.purpleMarketDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.purpleMarketDetailsViewItemSeparatorColor),
            positiveStatusColor = mColorProvider.getColor(R.color.purpleMarketDetailsViewPositiveStatusColor),
            negativeStatusColor = mColorProvider.getColor(R.color.purpleMarketDetailsViewNegativeStatusColor)
        )
    }


    override fun getBlueZodiacTheme(): MarketDetailsViewTheme {
        return MarketDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.blueZodiacMarketDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.blueZodiacMarketDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.blueZodiacMarketDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.blueZodiacMarketDetailsViewItemSeparatorColor),
            positiveStatusColor = mColorProvider.getColor(R.color.blueZodiacMarketDetailsViewPositiveStatusColor),
            negativeStatusColor = mColorProvider.getColor(R.color.blueZodiacMarketDetailsViewNegativeStatusColor)
        )
    }


    override fun getBubbleBlueTheme(): MarketDetailsViewTheme {
        return MarketDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.bubbleBlueMarketDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.bubbleBlueMarketDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.bubbleBlueMarketDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.bubbleBlueMarketDetailsViewItemSeparatorColor),
            positiveStatusColor = mColorProvider.getColor(R.color.bubbleBlueMarketDetailsViewPositiveStatusColor),
            negativeStatusColor = mColorProvider.getColor(R.color.bubbleBlueMarketDetailsViewNegativeStatusColor)
        )
    }


}
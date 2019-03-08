package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.OrderbookDetailsViewTheme

/**
 * A factory producing instances of the [OrderbookDetailsViewTheme] class.
 */
object OrderbookDetailsViewThemeFactory : BaseThemeFactory<OrderbookDetailsViewTheme>() {


    override fun getStubTheme(): OrderbookDetailsViewTheme {
        return OrderbookDetailsViewTheme(
            backgroundColor = -1,
            itemTitleColor = -1,
            itemValueColor = -1,
            itemSeparatorColor = -1,
            highestBidValueColor = -1,
            lowestAskValueColor = -1,
            progressBarColor = -1,
            infoViewCaptionColor = -1
        )
    }


    override fun getDeepTealTheme(): OrderbookDetailsViewTheme {
        return OrderbookDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.deepTealOrderbookDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.deepTealOrderbookDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.deepTealOrderbookDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.deepTealOrderbookDetailsViewItemSeparatorColor),
            highestBidValueColor = mColorProvider.getColor(R.color.deepTealOrderbookDetailsViewHighestBidValueColor),
            lowestAskValueColor = mColorProvider.getColor(R.color.deepTealOrderbookDetailsViewLowestAskValueColor),
            progressBarColor = mColorProvider.getColor(R.color.deepTealOrderbookDetailsViewProgressBarColor),
            infoViewCaptionColor = mColorProvider.getColor(R.color.deepTealOrderbookDetailsViewInfoViewCaptionColor)
        )
    }



    override fun getNightBlueTheme(): OrderbookDetailsViewTheme {
        return OrderbookDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.nightBlueOrderbookDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.nightBlueOrderbookDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.nightBlueOrderbookDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.nightBlueOrderbookDetailsViewItemSeparatorColor),
            highestBidValueColor = mColorProvider.getColor(R.color.nightBlueOrderbookDetailsViewHighestBidValueColor),
            lowestAskValueColor = mColorProvider.getColor(R.color.nightBlueOrderbookDetailsViewLowestAskValueColor),
            progressBarColor = mColorProvider.getColor(R.color.nightBlueOrderbookDetailsViewProgressBarColor),
            infoViewCaptionColor = mColorProvider.getColor(R.color.nightBlueOrderbookDetailsViewInfoViewCaptionColor)
        )
    }


    override fun getDarkGreenTheme(): OrderbookDetailsViewTheme {
        return OrderbookDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkGreenOrderbookDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.darkGreenOrderbookDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.darkGreenOrderbookDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.darkGreenOrderbookDetailsViewItemSeparatorColor),
            highestBidValueColor = mColorProvider.getColor(R.color.darkGreenOrderbookDetailsViewHighestBidValueColor),
            lowestAskValueColor = mColorProvider.getColor(R.color.darkGreenOrderbookDetailsViewLowestAskValueColor),
            progressBarColor = mColorProvider.getColor(R.color.darkGreenOrderbookDetailsViewProgressBarColor),
            infoViewCaptionColor = mColorProvider.getColor(R.color.darkGreenOrderbookDetailsViewInfoViewCaptionColor)
        )
    }


    override fun getPitchBlackTheme(): OrderbookDetailsViewTheme {
        return OrderbookDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.pitchBlackOrderbookDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.pitchBlackOrderbookDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.pitchBlackOrderbookDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.pitchBlackOrderbookDetailsViewItemSeparatorColor),
            highestBidValueColor = mColorProvider.getColor(R.color.pitchBlackOrderbookDetailsViewHighestBidValueColor),
            lowestAskValueColor = mColorProvider.getColor(R.color.pitchBlackOrderbookDetailsViewLowestAskValueColor),
            progressBarColor = mColorProvider.getColor(R.color.pitchBlackOrderbookDetailsViewProgressBarColor),
            infoViewCaptionColor = mColorProvider.getColor(R.color.pitchBlackOrderbookDetailsViewInfoViewCaptionColor)
        )
    }


    override fun getGrayishBlueTheme(): OrderbookDetailsViewTheme {
        return OrderbookDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.grayishBlueOrderbookDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.grayishBlueOrderbookDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.grayishBlueOrderbookDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.grayishBlueOrderbookDetailsViewItemSeparatorColor),
            highestBidValueColor = mColorProvider.getColor(R.color.grayishBlueOrderbookDetailsViewHighestBidValueColor),
            lowestAskValueColor = mColorProvider.getColor(R.color.grayishBlueOrderbookDetailsViewLowestAskValueColor),
            progressBarColor = mColorProvider.getColor(R.color.grayishBlueOrderbookDetailsViewProgressBarColor),
            infoViewCaptionColor = mColorProvider.getColor(R.color.grayishBlueOrderbookDetailsViewInfoViewCaptionColor)
        )
    }


    override fun getBrightGrayTheme(): OrderbookDetailsViewTheme {
        return OrderbookDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.brightGrayOrderbookDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.brightGrayOrderbookDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.brightGrayOrderbookDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.brightGrayOrderbookDetailsViewItemSeparatorColor),
            highestBidValueColor = mColorProvider.getColor(R.color.brightGrayOrderbookDetailsViewHighestBidValueColor),
            lowestAskValueColor = mColorProvider.getColor(R.color.brightGrayOrderbookDetailsViewLowestAskValueColor),
            progressBarColor = mColorProvider.getColor(R.color.brightGrayOrderbookDetailsViewProgressBarColor),
            infoViewCaptionColor = mColorProvider.getColor(R.color.brightGrayOrderbookDetailsViewInfoViewCaptionColor)
        )
    }


    override fun getDarkSilverTheme(): OrderbookDetailsViewTheme {
        return OrderbookDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkSilverOrderbookDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.darkSilverOrderbookDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.darkSilverOrderbookDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.darkSilverOrderbookDetailsViewItemSeparatorColor),
            highestBidValueColor = mColorProvider.getColor(R.color.darkSilverOrderbookDetailsViewHighestBidValueColor),
            lowestAskValueColor = mColorProvider.getColor(R.color.darkSilverOrderbookDetailsViewLowestAskValueColor),
            progressBarColor = mColorProvider.getColor(R.color.darkSilverOrderbookDetailsViewProgressBarColor),
            infoViewCaptionColor = mColorProvider.getColor(R.color.darkSilverOrderbookDetailsViewInfoViewCaptionColor)
        )
    }


    override fun getBrowneeTheme(): OrderbookDetailsViewTheme {
        return OrderbookDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.browneeOrderbookDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.browneeOrderbookDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.browneeOrderbookDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.browneeOrderbookDetailsViewItemSeparatorColor),
            highestBidValueColor = mColorProvider.getColor(R.color.browneeOrderbookDetailsViewHighestBidValueColor),
            lowestAskValueColor = mColorProvider.getColor(R.color.browneeOrderbookDetailsViewLowestAskValueColor),
            progressBarColor = mColorProvider.getColor(R.color.browneeOrderbookDetailsViewProgressBarColor),
            infoViewCaptionColor = mColorProvider.getColor(R.color.browneeOrderbookDetailsViewInfoViewCaptionColor)
        )
    }


    override fun getVioletTheme(): OrderbookDetailsViewTheme {
        return OrderbookDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.violetOrderbookDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.violetOrderbookDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.violetOrderbookDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.violetOrderbookDetailsViewItemSeparatorColor),
            highestBidValueColor = mColorProvider.getColor(R.color.violetOrderbookDetailsViewHighestBidValueColor),
            lowestAskValueColor = mColorProvider.getColor(R.color.violetOrderbookDetailsViewLowestAskValueColor),
            progressBarColor = mColorProvider.getColor(R.color.violetOrderbookDetailsViewProgressBarColor),
            infoViewCaptionColor = mColorProvider.getColor(R.color.violetOrderbookDetailsViewInfoViewCaptionColor)
        )
    }


    override fun getPurpleTheme(): OrderbookDetailsViewTheme {
        return OrderbookDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.purpleOrderbookDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.purpleOrderbookDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.purpleOrderbookDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.purpleOrderbookDetailsViewItemSeparatorColor),
            highestBidValueColor = mColorProvider.getColor(R.color.purpleOrderbookDetailsViewHighestBidValueColor),
            lowestAskValueColor = mColorProvider.getColor(R.color.purpleOrderbookDetailsViewLowestAskValueColor),
            progressBarColor = mColorProvider.getColor(R.color.purpleOrderbookDetailsViewProgressBarColor),
            infoViewCaptionColor = mColorProvider.getColor(R.color.purpleOrderbookDetailsViewInfoViewCaptionColor)
        )
    }


    override fun getBlueZodiacTheme(): OrderbookDetailsViewTheme {
        return OrderbookDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.blueZodiacOrderbookDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.blueZodiacOrderbookDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.blueZodiacOrderbookDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.blueZodiacOrderbookDetailsViewItemSeparatorColor),
            highestBidValueColor = mColorProvider.getColor(R.color.blueZodiacOrderbookDetailsViewHighestBidValueColor),
            lowestAskValueColor = mColorProvider.getColor(R.color.blueZodiacOrderbookDetailsViewLowestAskValueColor),
            progressBarColor = mColorProvider.getColor(R.color.blueZodiacOrderbookDetailsViewProgressBarColor),
            infoViewCaptionColor = mColorProvider.getColor(R.color.blueZodiacOrderbookDetailsViewInfoViewCaptionColor)
        )
    }


    override fun getBubbleBlueTheme(): OrderbookDetailsViewTheme {
        return OrderbookDetailsViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookDetailsViewBackgroundColor),
            itemTitleColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookDetailsViewItemTitleColor),
            itemValueColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookDetailsViewItemValueColor),
            itemSeparatorColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookDetailsViewItemSeparatorColor),
            highestBidValueColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookDetailsViewHighestBidValueColor),
            lowestAskValueColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookDetailsViewLowestAskValueColor),
            progressBarColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookDetailsViewProgressBarColor),
            infoViewCaptionColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookDetailsViewInfoViewCaptionColor)
        )
    }


}
package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.OrderbookViewTheme

/**
 * A factory producing instances of the [OrderbookViewTheme] class.
 */
object OrderbookViewThemeFactory : BaseThemeFactory<OrderbookViewTheme>() {


    override fun getStubTheme(): OrderbookViewTheme {
        return OrderbookViewTheme(
            backgroundColor = -1,
            headerTitleTextColor = -1,
            headerMoreButtonColor = -1,
            headerSeparatorColor = -1,
            buyPriceColor = -1,
            buyPriceHighlightColor = -1,
            buyOrderBackgroundColor = -1,
            buyOrderHighlightBackgroundColor = -1,
            sellPriceColor = -1,
            sellPriceHighlightColor = -1,
            sellOrderBackgroundColor = -1,
            sellOrderHighlightBackgroundColor = -1,
            amountColor = -1,
            progressBarColor = -1,
            infoViewColor = -1
        )
    }


    override fun getDeepTealTheme(): OrderbookViewTheme {
        return OrderbookViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.deepTealOrderbookViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.deepTealOrderbookViewHeaderTitleTextColor),
            headerMoreButtonColor = mColorProvider.getColor(R.color.deepTealOrderbookViewHeaderMoreButtonColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.deepTealOrderbookViewHeaderSeparatorColor),
            buyPriceColor = mColorProvider.getColor(R.color.deepTealOrderbookViewBuyPriceColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.deepTealOrderbookViewBuyPriceHighlightColor),
            buyOrderBackgroundColor = mColorProvider.getColor(R.color.deepTealOrderbookViewBuyOrderBackgroundColor),
            buyOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.deepTealOrderbookViewBuyOrderHighlightBackgroundColor),
            sellPriceColor = mColorProvider.getColor(R.color.deepTealOrderbookViewSellPriceColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.deepTealOrderbookViewSellPriceHighlightColor),
            sellOrderBackgroundColor = mColorProvider.getColor(R.color.deepTealOrderbookViewSellOrderBackgroundColor),
            sellOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.deepTealOrderbookViewSellOrderHighlightBackgroundColor),
            amountColor = mColorProvider.getColor(R.color.deepTealOrderbookViewAmountColor),
            progressBarColor = mColorProvider.getColor(R.color.deepTealOrderbookViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.deepTealOrderbookViewInfoViewColor)
        )
    }


    override fun getNightBlueTheme(): OrderbookViewTheme {
        return OrderbookViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.nightBlueOrderbookViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.nightBlueOrderbookViewHeaderTitleTextColor),
            headerMoreButtonColor = mColorProvider.getColor(R.color.nightBlueOrderbookViewHeaderMoreButtonColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.nightBlueOrderbookViewHeaderSeparatorColor),
            buyPriceColor = mColorProvider.getColor(R.color.nightBlueOrderbookViewBuyPriceColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.nightBlueOrderbookViewBuyPriceHighlightColor),
            buyOrderBackgroundColor = mColorProvider.getColor(R.color.nightBlueOrderbookViewBuyOrderBackgroundColor),
            buyOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.nightBlueOrderbookViewBuyOrderHighlightBackgroundColor),
            sellPriceColor = mColorProvider.getColor(R.color.nightBlueOrderbookViewSellPriceColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.nightBlueOrderbookViewSellPriceHighlightColor),
            sellOrderBackgroundColor = mColorProvider.getColor(R.color.nightBlueOrderbookViewSellOrderBackgroundColor),
            sellOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.nightBlueOrderbookViewSellOrderHighlightBackgroundColor),
            amountColor = mColorProvider.getColor(R.color.nightBlueOrderbookViewAmountColor),
            progressBarColor = mColorProvider.getColor(R.color.nightBlueOrderbookViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.nightBlueOrderbookViewInfoViewColor)
        )
    }


    override fun getDarkGreenTheme(): OrderbookViewTheme {
        return OrderbookViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkGreenOrderbookViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.darkGreenOrderbookViewHeaderTitleTextColor),
            headerMoreButtonColor = mColorProvider.getColor(R.color.darkGreenOrderbookViewHeaderMoreButtonColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.darkGreenOrderbookViewHeaderSeparatorColor),
            buyPriceColor = mColorProvider.getColor(R.color.darkGreenOrderbookViewBuyPriceColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.darkGreenOrderbookViewBuyPriceHighlightColor),
            buyOrderBackgroundColor = mColorProvider.getColor(R.color.darkGreenOrderbookViewBuyOrderBackgroundColor),
            buyOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.darkGreenOrderbookViewBuyOrderHighlightBackgroundColor),
            sellPriceColor = mColorProvider.getColor(R.color.darkGreenOrderbookViewSellPriceColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.darkGreenOrderbookViewSellPriceHighlightColor),
            sellOrderBackgroundColor = mColorProvider.getColor(R.color.darkGreenOrderbookViewSellOrderBackgroundColor),
            sellOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.darkGreenOrderbookViewSellOrderHighlightBackgroundColor),
            amountColor = mColorProvider.getColor(R.color.darkGreenOrderbookViewAmountColor),
            progressBarColor = mColorProvider.getColor(R.color.darkGreenOrderbookViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.darkGreenOrderbookViewInfoViewColor)
        )
    }


    override fun getPitchBlackTheme(): OrderbookViewTheme {
        return OrderbookViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.pitchBlackOrderbookViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.pitchBlackOrderbookViewHeaderTitleTextColor),
            headerMoreButtonColor = mColorProvider.getColor(R.color.pitchBlackOrderbookViewHeaderMoreButtonColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.pitchBlackOrderbookViewHeaderSeparatorColor),
            buyPriceColor = mColorProvider.getColor(R.color.pitchBlackOrderbookViewBuyPriceColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.pitchBlackOrderbookViewBuyPriceHighlightColor),
            buyOrderBackgroundColor = mColorProvider.getColor(R.color.pitchBlackOrderbookViewBuyOrderBackgroundColor),
            buyOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.pitchBlackOrderbookViewBuyOrderHighlightBackgroundColor),
            sellPriceColor = mColorProvider.getColor(R.color.pitchBlackOrderbookViewSellPriceColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.pitchBlackOrderbookViewSellPriceHighlightColor),
            sellOrderBackgroundColor = mColorProvider.getColor(R.color.pitchBlackOrderbookViewSellOrderBackgroundColor),
            sellOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.pitchBlackOrderbookViewSellOrderHighlightBackgroundColor),
            amountColor = mColorProvider.getColor(R.color.pitchBlackOrderbookViewAmountColor),
            progressBarColor = mColorProvider.getColor(R.color.pitchBlackOrderbookViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.pitchBlackOrderbookViewInfoViewColor)
        )
    }


    override fun getGrayishBlueTheme(): OrderbookViewTheme {
        return OrderbookViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.grayishBlueOrderbookViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.grayishBlueOrderbookViewHeaderTitleTextColor),
            headerMoreButtonColor = mColorProvider.getColor(R.color.grayishBlueOrderbookViewHeaderMoreButtonColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.grayishBlueOrderbookViewHeaderSeparatorColor),
            buyPriceColor = mColorProvider.getColor(R.color.grayishBlueOrderbookViewBuyPriceColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.grayishBlueOrderbookViewBuyPriceHighlightColor),
            buyOrderBackgroundColor = mColorProvider.getColor(R.color.grayishBlueOrderbookViewBuyOrderBackgroundColor),
            buyOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.grayishBlueOrderbookViewBuyOrderHighlightBackgroundColor),
            sellPriceColor = mColorProvider.getColor(R.color.grayishBlueOrderbookViewSellPriceColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.grayishBlueOrderbookViewSellPriceHighlightColor),
            sellOrderBackgroundColor = mColorProvider.getColor(R.color.grayishBlueOrderbookViewSellOrderBackgroundColor),
            sellOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.grayishBlueOrderbookViewSellOrderHighlightBackgroundColor),
            amountColor = mColorProvider.getColor(R.color.grayishBlueOrderbookViewAmountColor),
            progressBarColor = mColorProvider.getColor(R.color.grayishBlueOrderbookViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.grayishBlueOrderbookViewInfoViewColor)
        )
    }


    override fun getBrightGrayTheme(): OrderbookViewTheme {
        return OrderbookViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.brightGrayOrderbookViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.brightGrayOrderbookViewHeaderTitleTextColor),
            headerMoreButtonColor = mColorProvider.getColor(R.color.brightGrayOrderbookViewHeaderMoreButtonColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.brightGrayOrderbookViewHeaderSeparatorColor),
            buyPriceColor = mColorProvider.getColor(R.color.brightGrayOrderbookViewBuyPriceColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.brightGrayOrderbookViewBuyPriceHighlightColor),
            buyOrderBackgroundColor = mColorProvider.getColor(R.color.brightGrayOrderbookViewBuyOrderBackgroundColor),
            buyOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.brightGrayOrderbookViewBuyOrderHighlightBackgroundColor),
            sellPriceColor = mColorProvider.getColor(R.color.brightGrayOrderbookViewSellPriceColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.brightGrayOrderbookViewSellPriceHighlightColor),
            sellOrderBackgroundColor = mColorProvider.getColor(R.color.brightGrayOrderbookViewSellOrderBackgroundColor),
            sellOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.brightGrayOrderbookViewSellOrderHighlightBackgroundColor),
            amountColor = mColorProvider.getColor(R.color.brightGrayOrderbookViewAmountColor),
            progressBarColor = mColorProvider.getColor(R.color.brightGrayOrderbookViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.brightGrayOrderbookViewInfoViewColor)
        )
    }


    override fun getDarkSilverTheme(): OrderbookViewTheme {
        return OrderbookViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkSilverOrderbookViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.darkSilverOrderbookViewHeaderTitleTextColor),
            headerMoreButtonColor = mColorProvider.getColor(R.color.darkSilverOrderbookViewHeaderMoreButtonColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.darkSilverOrderbookViewHeaderSeparatorColor),
            buyPriceColor = mColorProvider.getColor(R.color.darkSilverOrderbookViewBuyPriceColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.darkSilverOrderbookViewBuyPriceHighlightColor),
            buyOrderBackgroundColor = mColorProvider.getColor(R.color.darkSilverOrderbookViewBuyOrderBackgroundColor),
            buyOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.darkSilverOrderbookViewBuyOrderHighlightBackgroundColor),
            sellPriceColor = mColorProvider.getColor(R.color.darkSilverOrderbookViewSellPriceColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.darkSilverOrderbookViewSellPriceHighlightColor),
            sellOrderBackgroundColor = mColorProvider.getColor(R.color.darkSilverOrderbookViewSellOrderBackgroundColor),
            sellOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.darkSilverOrderbookViewSellOrderHighlightBackgroundColor),
            amountColor = mColorProvider.getColor(R.color.darkSilverOrderbookViewAmountColor),
            progressBarColor = mColorProvider.getColor(R.color.darkSilverOrderbookViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.darkSilverOrderbookViewInfoViewColor)
        )
    }


    override fun getBrowneeTheme(): OrderbookViewTheme {
        return OrderbookViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.browneeOrderbookViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.browneeOrderbookViewHeaderTitleTextColor),
            headerMoreButtonColor = mColorProvider.getColor(R.color.browneeOrderbookViewHeaderMoreButtonColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.browneeOrderbookViewHeaderSeparatorColor),
            buyPriceColor = mColorProvider.getColor(R.color.browneeOrderbookViewBuyPriceColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.browneeOrderbookViewBuyPriceHighlightColor),
            buyOrderBackgroundColor = mColorProvider.getColor(R.color.browneeOrderbookViewBuyOrderBackgroundColor),
            buyOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.browneeOrderbookViewBuyOrderHighlightBackgroundColor),
            sellPriceColor = mColorProvider.getColor(R.color.browneeOrderbookViewSellPriceColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.browneeOrderbookViewSellPriceHighlightColor),
            sellOrderBackgroundColor = mColorProvider.getColor(R.color.browneeOrderbookViewSellOrderBackgroundColor),
            sellOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.browneeOrderbookViewSellOrderHighlightBackgroundColor),
            amountColor = mColorProvider.getColor(R.color.browneeOrderbookViewAmountColor),
            progressBarColor = mColorProvider.getColor(R.color.browneeOrderbookViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.browneeOrderbookViewInfoViewColor)
        )
    }


    override fun getVioletTheme(): OrderbookViewTheme {
        return OrderbookViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.violetOrderbookViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.violetOrderbookViewHeaderTitleTextColor),
            headerMoreButtonColor = mColorProvider.getColor(R.color.violetOrderbookViewHeaderMoreButtonColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.violetOrderbookViewHeaderSeparatorColor),
            buyPriceColor = mColorProvider.getColor(R.color.violetOrderbookViewBuyPriceColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.violetOrderbookViewBuyPriceHighlightColor),
            buyOrderBackgroundColor = mColorProvider.getColor(R.color.violetOrderbookViewBuyOrderBackgroundColor),
            buyOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.violetOrderbookViewBuyOrderHighlightBackgroundColor),
            sellPriceColor = mColorProvider.getColor(R.color.violetOrderbookViewSellPriceColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.violetOrderbookViewSellPriceHighlightColor),
            sellOrderBackgroundColor = mColorProvider.getColor(R.color.violetOrderbookViewSellOrderBackgroundColor),
            sellOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.violetOrderbookViewSellOrderHighlightBackgroundColor),
            amountColor = mColorProvider.getColor(R.color.violetOrderbookViewAmountColor),
            progressBarColor = mColorProvider.getColor(R.color.violetOrderbookViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.violetOrderbookViewInfoViewColor)
        )
    }


    override fun getPurpleTheme(): OrderbookViewTheme {
        return OrderbookViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.purpleOrderbookViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.purpleOrderbookViewHeaderTitleTextColor),
            headerMoreButtonColor = mColorProvider.getColor(R.color.purpleOrderbookViewHeaderMoreButtonColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.purpleOrderbookViewHeaderSeparatorColor),
            buyPriceColor = mColorProvider.getColor(R.color.purpleOrderbookViewBuyPriceColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.purpleOrderbookViewBuyPriceHighlightColor),
            buyOrderBackgroundColor = mColorProvider.getColor(R.color.purpleOrderbookViewBuyOrderBackgroundColor),
            buyOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.purpleOrderbookViewBuyOrderHighlightBackgroundColor),
            sellPriceColor = mColorProvider.getColor(R.color.purpleOrderbookViewSellPriceColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.purpleOrderbookViewSellPriceHighlightColor),
            sellOrderBackgroundColor = mColorProvider.getColor(R.color.purpleOrderbookViewSellOrderBackgroundColor),
            sellOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.purpleOrderbookViewSellOrderHighlightBackgroundColor),
            amountColor = mColorProvider.getColor(R.color.purpleOrderbookViewAmountColor),
            progressBarColor = mColorProvider.getColor(R.color.purpleOrderbookViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.purpleOrderbookViewInfoViewColor)
        )
    }


    override fun getBlueZodiacTheme(): OrderbookViewTheme {
        return OrderbookViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.blueZodiacOrderbookViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.blueZodiacOrderbookViewHeaderTitleTextColor),
            headerMoreButtonColor = mColorProvider.getColor(R.color.blueZodiacOrderbookViewHeaderMoreButtonColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.blueZodiacOrderbookViewHeaderSeparatorColor),
            buyPriceColor = mColorProvider.getColor(R.color.blueZodiacOrderbookViewBuyPriceColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.blueZodiacOrderbookViewBuyPriceHighlightColor),
            buyOrderBackgroundColor = mColorProvider.getColor(R.color.blueZodiacOrderbookViewBuyOrderBackgroundColor),
            buyOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.blueZodiacOrderbookViewBuyOrderHighlightBackgroundColor),
            sellPriceColor = mColorProvider.getColor(R.color.blueZodiacOrderbookViewSellPriceColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.blueZodiacOrderbookViewSellPriceHighlightColor),
            sellOrderBackgroundColor = mColorProvider.getColor(R.color.blueZodiacOrderbookViewSellOrderBackgroundColor),
            sellOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.blueZodiacOrderbookViewSellOrderHighlightBackgroundColor),
            amountColor = mColorProvider.getColor(R.color.blueZodiacOrderbookViewAmountColor),
            progressBarColor = mColorProvider.getColor(R.color.blueZodiacOrderbookViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.blueZodiacOrderbookViewInfoViewColor)
        )
    }


    override fun getBubbleBlueTheme(): OrderbookViewTheme {
        return OrderbookViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookViewHeaderTitleTextColor),
            headerMoreButtonColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookViewHeaderMoreButtonColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookViewHeaderSeparatorColor),
            buyPriceColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookViewBuyPriceColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookViewBuyPriceHighlightColor),
            buyOrderBackgroundColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookViewBuyOrderBackgroundColor),
            buyOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookViewBuyOrderHighlightBackgroundColor),
            sellPriceColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookViewSellPriceColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookViewSellPriceHighlightColor),
            sellOrderBackgroundColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookViewSellOrderBackgroundColor),
            sellOrderHighlightBackgroundColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookViewSellOrderHighlightBackgroundColor),
            amountColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookViewAmountColor),
            progressBarColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.bubbleBlueOrderbookViewInfoViewColor)
        )
    }


}
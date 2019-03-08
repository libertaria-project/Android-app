package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.TradesViewTheme
import com.stocksexchange.android.ui.views.marketpreview.trades.TradesView

/**
 * A factory producing instances of the [TradesViewTheme] class.
 */
object TradesViewThemeFactory : BaseThemeFactory<TradesViewTheme>() {


    override fun getStubTheme(): TradesViewTheme {
        return TradesViewTheme(
            backgroundColor = -1,
            headerTitleTextColor = -1,
            headerSeparatorColor = -1,
            buyHighlightBackgroundColor = -1,
            sellHighlightBackgroundColor = -1,
            buyPriceHighlightColor = -1,
            sellPriceHighlightColor = -1,
            buyPriceColor = -1,
            sellPriceColor = -1,
            amountColor = -1,
            tradeTimeColor = -1,
            progressBarColor = -1,
            infoViewColor = -1
        )
    }


    override fun getDeepTealTheme(): TradesViewTheme {
        return TradesViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.deepTealTradesViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.deepTealTradesViewHeaderTitleTextColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.deepTealTradesViewHeaderSeparatorColor),
            buyHighlightBackgroundColor = mColorProvider.getColor(R.color.deepTealTradesViewBuyHighlightBackgroundColor),
            sellHighlightBackgroundColor = mColorProvider.getColor(R.color.deepTealTradesViewSellHighlightBackgroundColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.deepTealTradesViewBuyPriceHighlightColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.deepTealTradesViewSellPriceHighlightColor),
            buyPriceColor = mColorProvider.getColor(R.color.deepTealTradesViewBuyPriceColor),
            sellPriceColor = mColorProvider.getColor(R.color.deepTealTradesViewSellPriceColor),
            amountColor = mColorProvider.getColor(R.color.deepTealTradesViewAmountColor),
            tradeTimeColor = mColorProvider.getColor(R.color.deepTealTradesViewTradeTimeColor),
            progressBarColor = mColorProvider.getColor(R.color.deepTealTradesViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.deepTealTradesViewInfoViewColor)
        )
    }


    override fun getNightBlueTheme(): TradesViewTheme {
        return TradesViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.nightBlueTradesViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.nightBlueTradesViewHeaderTitleTextColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.nightBlueTradesViewHeaderSeparatorColor),
            buyHighlightBackgroundColor = mColorProvider.getColor(R.color.nightBlueTradesViewBuyHighlightBackgroundColor),
            sellHighlightBackgroundColor = mColorProvider.getColor(R.color.nightBlueTradesViewSellHighlightBackgroundColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.nightBlueTradesViewBuyPriceHighlightColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.nightBlueTradesViewSellPriceHighlightColor),
            buyPriceColor = mColorProvider.getColor(R.color.nightBlueTradesViewBuyPriceColor),
            sellPriceColor = mColorProvider.getColor(R.color.nightBlueTradesViewSellPriceColor),
            amountColor = mColorProvider.getColor(R.color.nightBlueTradesViewAmountColor),
            tradeTimeColor = mColorProvider.getColor(R.color.nightBlueTradesViewTradeTimeColor),
            progressBarColor = mColorProvider.getColor(R.color.nightBlueTradesViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.nightBlueTradesViewInfoViewColor)
        )
    }


    override fun getDarkGreenTheme(): TradesViewTheme {
        return TradesViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkGreenTradesViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.darkGreenTradesViewHeaderTitleTextColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.darkGreenTradesViewHeaderSeparatorColor),
            buyHighlightBackgroundColor = mColorProvider.getColor(R.color.darkGreenTradesViewBuyHighlightBackgroundColor),
            sellHighlightBackgroundColor = mColorProvider.getColor(R.color.darkGreenTradesViewSellHighlightBackgroundColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.darkGreenTradesViewBuyPriceHighlightColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.darkGreenTradesViewSellPriceHighlightColor),
            buyPriceColor = mColorProvider.getColor(R.color.darkGreenTradesViewBuyPriceColor),
            sellPriceColor = mColorProvider.getColor(R.color.darkGreenTradesViewSellPriceColor),
            amountColor = mColorProvider.getColor(R.color.darkGreenTradesViewAmountColor),
            tradeTimeColor = mColorProvider.getColor(R.color.darkGreenTradesViewTradeTimeColor),
            progressBarColor = mColorProvider.getColor(R.color.darkGreenTradesViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.darkGreenTradesViewInfoViewColor)
        )
    }


    override fun getPitchBlackTheme(): TradesViewTheme {
        return TradesViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.pitchBlackTradesViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.pitchBlackTradesViewHeaderTitleTextColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.pitchBlackTradesViewHeaderSeparatorColor),
            buyHighlightBackgroundColor = mColorProvider.getColor(R.color.pitchBlackTradesViewBuyHighlightBackgroundColor),
            sellHighlightBackgroundColor = mColorProvider.getColor(R.color.pitchBlackTradesViewSellHighlightBackgroundColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.pitchBlackTradesViewBuyPriceHighlightColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.pitchBlackTradesViewSellPriceHighlightColor),
            buyPriceColor = mColorProvider.getColor(R.color.pitchBlackTradesViewBuyPriceColor),
            sellPriceColor = mColorProvider.getColor(R.color.pitchBlackTradesViewSellPriceColor),
            amountColor = mColorProvider.getColor(R.color.pitchBlackTradesViewAmountColor),
            tradeTimeColor = mColorProvider.getColor(R.color.pitchBlackTradesViewTradeTimeColor),
            progressBarColor = mColorProvider.getColor(R.color.pitchBlackTradesViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.pitchBlackTradesViewInfoViewColor)
        )
    }


    override fun getGrayishBlueTheme(): TradesViewTheme {
        return TradesViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.grayishBlueTradesViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.grayishBlueTradesViewHeaderTitleTextColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.grayishBlueTradesViewHeaderSeparatorColor),
            buyHighlightBackgroundColor = mColorProvider.getColor(R.color.grayishBlueTradesViewBuyHighlightBackgroundColor),
            sellHighlightBackgroundColor = mColorProvider.getColor(R.color.grayishBlueTradesViewSellHighlightBackgroundColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.grayishBlueTradesViewBuyPriceHighlightColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.grayishBlueTradesViewSellPriceHighlightColor),
            buyPriceColor = mColorProvider.getColor(R.color.grayishBlueTradesViewBuyPriceColor),
            sellPriceColor = mColorProvider.getColor(R.color.grayishBlueTradesViewSellPriceColor),
            amountColor = mColorProvider.getColor(R.color.grayishBlueTradesViewAmountColor),
            tradeTimeColor = mColorProvider.getColor(R.color.grayishBlueTradesViewTradeTimeColor),
            progressBarColor = mColorProvider.getColor(R.color.grayishBlueTradesViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.grayishBlueTradesViewInfoViewColor)
        )
    }


    override fun getBrightGrayTheme(): TradesViewTheme {
        return TradesViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.brightGrayTradesViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.brightGrayTradesViewHeaderTitleTextColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.brightGrayTradesViewHeaderSeparatorColor),
            buyHighlightBackgroundColor = mColorProvider.getColor(R.color.brightGrayTradesViewBuyHighlightBackgroundColor),
            sellHighlightBackgroundColor = mColorProvider.getColor(R.color.brightGrayTradesViewSellHighlightBackgroundColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.brightGrayTradesViewBuyPriceHighlightColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.brightGrayTradesViewSellPriceHighlightColor),
            buyPriceColor = mColorProvider.getColor(R.color.brightGrayTradesViewBuyPriceColor),
            sellPriceColor = mColorProvider.getColor(R.color.brightGrayTradesViewSellPriceColor),
            amountColor = mColorProvider.getColor(R.color.brightGrayTradesViewAmountColor),
            tradeTimeColor = mColorProvider.getColor(R.color.brightGrayTradesViewTradeTimeColor),
            progressBarColor = mColorProvider.getColor(R.color.brightGrayTradesViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.brightGrayTradesViewInfoViewColor)
        )
    }

    override fun getDarkSilverTheme(): TradesViewTheme {
        return TradesViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkSilverTradesViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.darkSilverTradesViewHeaderTitleTextColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.darkSilverTradesViewHeaderSeparatorColor),
            buyHighlightBackgroundColor = mColorProvider.getColor(R.color.darkSilverTradesViewBuyHighlightBackgroundColor),
            sellHighlightBackgroundColor = mColorProvider.getColor(R.color.darkSilverTradesViewSellHighlightBackgroundColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.darkSilverTradesViewBuyPriceHighlightColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.darkSilverTradesViewSellPriceHighlightColor),
            buyPriceColor = mColorProvider.getColor(R.color.darkSilverTradesViewBuyPriceColor),
            sellPriceColor = mColorProvider.getColor(R.color.darkSilverTradesViewSellPriceColor),
            amountColor = mColorProvider.getColor(R.color.darkSilverTradesViewAmountColor),
            tradeTimeColor = mColorProvider.getColor(R.color.darkSilverTradesViewTradeTimeColor),
            progressBarColor = mColorProvider.getColor(R.color.darkSilverTradesViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.darkSilverTradesViewInfoViewColor)
        )
    }


    override fun getBrowneeTheme(): TradesViewTheme {
        return TradesViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.browneeTradesViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.browneeTradesViewHeaderTitleTextColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.browneeTradesViewHeaderSeparatorColor),
            buyHighlightBackgroundColor = mColorProvider.getColor(R.color.browneeTradesViewBuyHighlightBackgroundColor),
            sellHighlightBackgroundColor = mColorProvider.getColor(R.color.browneeTradesViewSellHighlightBackgroundColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.browneeTradesViewBuyPriceHighlightColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.browneeTradesViewSellPriceHighlightColor),
            buyPriceColor = mColorProvider.getColor(R.color.browneeTradesViewBuyPriceColor),
            sellPriceColor = mColorProvider.getColor(R.color.browneeTradesViewSellPriceColor),
            amountColor = mColorProvider.getColor(R.color.browneeTradesViewAmountColor),
            tradeTimeColor = mColorProvider.getColor(R.color.browneeTradesViewTradeTimeColor),
            progressBarColor = mColorProvider.getColor(R.color.browneeTradesViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.browneeTradesViewInfoViewColor)
        )
    }


    override fun getVioletTheme(): TradesViewTheme {
        return TradesViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.violetTradesViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.violetTradesViewHeaderTitleTextColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.violetTradesViewHeaderSeparatorColor),
            buyHighlightBackgroundColor = mColorProvider.getColor(R.color.violetTradesViewBuyHighlightBackgroundColor),
            sellHighlightBackgroundColor = mColorProvider.getColor(R.color.violetTradesViewSellHighlightBackgroundColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.violetTradesViewBuyPriceHighlightColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.violetTradesViewSellPriceHighlightColor),
            buyPriceColor = mColorProvider.getColor(R.color.violetTradesViewBuyPriceColor),
            sellPriceColor = mColorProvider.getColor(R.color.violetTradesViewSellPriceColor),
            amountColor = mColorProvider.getColor(R.color.violetTradesViewAmountColor),
            tradeTimeColor = mColorProvider.getColor(R.color.violetTradesViewTradeTimeColor),
            progressBarColor = mColorProvider.getColor(R.color.violetTradesViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.violetTradesViewInfoViewColor)
        )
    }


    override fun getPurpleTheme(): TradesViewTheme {
        return TradesViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.purpleTradesViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.purpleTradesViewHeaderTitleTextColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.purpleTradesViewHeaderSeparatorColor),
            buyHighlightBackgroundColor = mColorProvider.getColor(R.color.purpleTradesViewBuyHighlightBackgroundColor),
            sellHighlightBackgroundColor = mColorProvider.getColor(R.color.purpleTradesViewSellHighlightBackgroundColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.purpleTradesViewBuyPriceHighlightColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.purpleTradesViewSellPriceHighlightColor),
            buyPriceColor = mColorProvider.getColor(R.color.purpleTradesViewBuyPriceColor),
            sellPriceColor = mColorProvider.getColor(R.color.purpleTradesViewSellPriceColor),
            amountColor = mColorProvider.getColor(R.color.purpleTradesViewAmountColor),
            tradeTimeColor = mColorProvider.getColor(R.color.purpleTradesViewTradeTimeColor),
            progressBarColor = mColorProvider.getColor(R.color.purpleTradesViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.purpleTradesViewInfoViewColor)
        )
    }


    override fun getBlueZodiacTheme(): TradesViewTheme {
        return TradesViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.blueZodiacTradesViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.blueZodiacTradesViewHeaderTitleTextColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.blueZodiacTradesViewHeaderSeparatorColor),
            buyHighlightBackgroundColor = mColorProvider.getColor(R.color.blueZodiacTradesViewBuyHighlightBackgroundColor),
            sellHighlightBackgroundColor = mColorProvider.getColor(R.color.blueZodiacTradesViewSellHighlightBackgroundColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.blueZodiacTradesViewBuyPriceHighlightColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.blueZodiacTradesViewSellPriceHighlightColor),
            buyPriceColor = mColorProvider.getColor(R.color.blueZodiacTradesViewBuyPriceColor),
            sellPriceColor = mColorProvider.getColor(R.color.blueZodiacTradesViewSellPriceColor),
            amountColor = mColorProvider.getColor(R.color.blueZodiacTradesViewAmountColor),
            tradeTimeColor = mColorProvider.getColor(R.color.blueZodiacTradesViewTradeTimeColor),
            progressBarColor = mColorProvider.getColor(R.color.blueZodiacTradesViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.blueZodiacTradesViewInfoViewColor)
        )
    }


    override fun getBubbleBlueTheme(): TradesViewTheme {
        return TradesViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.bubbleBlueTradesViewBackgroundColor),
            headerTitleTextColor = mColorProvider.getColor(R.color.bubbleBlueTradesViewHeaderTitleTextColor),
            headerSeparatorColor = mColorProvider.getColor(R.color.bubbleBlueTradesViewHeaderSeparatorColor),
            buyHighlightBackgroundColor = mColorProvider.getColor(R.color.bubbleBlueTradesViewBuyHighlightBackgroundColor),
            sellHighlightBackgroundColor = mColorProvider.getColor(R.color.bubbleBlueTradesViewSellHighlightBackgroundColor),
            buyPriceHighlightColor = mColorProvider.getColor(R.color.bubbleBlueTradesViewBuyPriceHighlightColor),
            sellPriceHighlightColor = mColorProvider.getColor(R.color.bubbleBlueTradesViewSellPriceHighlightColor),
            buyPriceColor = mColorProvider.getColor(R.color.bubbleBlueTradesViewBuyPriceColor),
            sellPriceColor = mColorProvider.getColor(R.color.bubbleBlueTradesViewSellPriceColor),
            amountColor = mColorProvider.getColor(R.color.bubbleBlueTradesViewAmountColor),
            tradeTimeColor = mColorProvider.getColor(R.color.bubbleBlueTradesViewTradeTimeColor),
            progressBarColor = mColorProvider.getColor(R.color.bubbleBlueTradesViewProgressBarColor),
            infoViewColor = mColorProvider.getColor(R.color.bubbleBlueTradesViewInfoViewColor)
        )
    }


}
package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.CardViewTheme

/**
 * A factory producing instances of the [CardViewTheme] class.
 */
object CardViewThemeFactory : BaseThemeFactory<CardViewTheme>() {


    override fun getStubTheme(): CardViewTheme {
        return CardViewTheme(
            backgroundColor = -1,
            primaryTextColor = -1,
            primaryDarkTextColor = -1
        )
    }


    override fun getDeepTealTheme(): CardViewTheme {
        return CardViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.deepTealCardViewBackgroundColor),
            primaryTextColor = mColorProvider.getColor(R.color.deepTealCardViewPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.deepTealCardViewPrimaryDarkTextColor)
        )
    }


    override fun getNightBlueTheme(): CardViewTheme {
        return CardViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.nightBlueCardViewBackgroundColor),
            primaryTextColor = mColorProvider.getColor(R.color.nightBlueCardViewPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.nightBlueCardViewDarkPrimaryTextColor)
        )
    }


    override fun getDarkGreenTheme(): CardViewTheme {
        return CardViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkGreenCardViewBackgroundColor),
            primaryTextColor = mColorProvider.getColor(R.color.darkGreenCardViewPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.darkGreenCardViewDarkPrimaryTextColor)
        )
    }


    override fun getPitchBlackTheme(): CardViewTheme {
        return CardViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.pitchBlackCardViewBackgroundColor),
            primaryTextColor = mColorProvider.getColor(R.color.pitchBlackCardViewPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.pitchBlackCardViewDarkPrimaryTextColor)
        )
    }


    override fun getGrayishBlueTheme(): CardViewTheme {
        return CardViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.grayishBlueCardViewBackgroundColor),
            primaryTextColor = mColorProvider.getColor(R.color.grayishBlueCardViewPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.grayishBlueCardViewDarkPrimaryTextColor)
        )
    }


    override fun getBrightGrayTheme(): CardViewTheme {
        return CardViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.brightGrayCardViewBackgroundColor),
            primaryTextColor = mColorProvider.getColor(R.color.brightGrayCardViewPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.brightGrayCardViewDarkPrimaryTextColor)
        )
    }


    override fun getDarkSilverTheme(): CardViewTheme {
        return CardViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkSilverCardViewBackgroundColor),
            primaryTextColor = mColorProvider.getColor(R.color.darkSilverCardViewPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.darkSilverCardViewDarkPrimaryTextColor)
        )
    }


    override fun getBrowneeTheme(): CardViewTheme {
        return CardViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.browneeCardViewBackgroundColor),
            primaryTextColor = mColorProvider.getColor(R.color.browneeCardViewPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.browneeCardViewDarkPrimaryTextColor)
        )
    }


    override fun getVioletTheme(): CardViewTheme {
        return CardViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.violetCardViewBackgroundColor),
            primaryTextColor = mColorProvider.getColor(R.color.violetCardViewPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.violetCardViewDarkPrimaryTextColor)
        )
    }


    override fun getPurpleTheme(): CardViewTheme {
        return CardViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.purpleCardViewBackgroundColor),
            primaryTextColor = mColorProvider.getColor(R.color.purpleCardViewPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.purpleCardViewDarkPrimaryTextColor)
        )
    }


    override fun getBlueZodiacTheme(): CardViewTheme {
        return CardViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.blueZodiacCardViewBackgroundColor),
            primaryTextColor = mColorProvider.getColor(R.color.blueZodiacCardViewPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.blueZodiacCardViewDarkPrimaryTextColor)
        )
    }


    override fun getBubbleBlueTheme(): CardViewTheme {
        return CardViewTheme(
            backgroundColor = mColorProvider.getColor(R.color.bubbleBlueCardViewBackgroundColor),
            primaryTextColor = mColorProvider.getColor(R.color.bubbleBlueCardViewPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.bubbleBlueCardViewDarkPrimaryTextColor)
        )
    }


}
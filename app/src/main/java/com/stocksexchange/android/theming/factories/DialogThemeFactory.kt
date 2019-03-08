package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.DialogTheme

/**
 * A factory producing instances of the [DialogTheme] class.
 */
object DialogThemeFactory : BaseThemeFactory<DialogTheme>() {


    override fun getStubTheme(): DialogTheme {
        return DialogTheme(
            backgroundColor = -1,
            titleColor = -1,
            textColor = -1,
            buttonColor = -1
        )
    }


    override fun getDeepTealTheme(): DialogTheme {
        return DialogTheme(
            backgroundColor = mColorProvider.getColor(R.color.deepTealDialogBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.deepTealDialogTitleColor),
            textColor = mColorProvider.getColor(R.color.deepTealDialogTextColor),
            buttonColor = mColorProvider.getColor(R.color.deepTealDialogButtonColor)
        )
    }


    override fun getNightBlueTheme(): DialogTheme {
        return DialogTheme(
            backgroundColor = mColorProvider.getColor(R.color.nightBlueDialogBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.nightBlueDialogTitleColor),
            textColor = mColorProvider.getColor(R.color.nightBlueDialogTextColor),
            buttonColor = mColorProvider.getColor(R.color.nightBlueDialogButtonColor)
        )
    }


    override fun getDarkGreenTheme(): DialogTheme {
        return DialogTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkGreenDialogBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.darkGreenDialogTitleColor),
            textColor = mColorProvider.getColor(R.color.darkGreenDialogTextColor),
            buttonColor = mColorProvider.getColor(R.color.darkGreenDialogButtonColor)
        )
    }


    override fun getPitchBlackTheme(): DialogTheme {
        return DialogTheme(
            backgroundColor = mColorProvider.getColor(R.color.pitchBlackDialogBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.pitchBlackDialogTitleColor),
            textColor = mColorProvider.getColor(R.color.pitchBlackDialogTextColor),
            buttonColor = mColorProvider.getColor(R.color.pitchBlackDialogButtonColor)
        )
    }


    override fun getGrayishBlueTheme(): DialogTheme {
        return DialogTheme(
            backgroundColor = mColorProvider.getColor(R.color.grayishBlueDialogBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.grayishBlueDialogTitleColor),
            textColor = mColorProvider.getColor(R.color.grayishBlueDialogTextColor),
            buttonColor = mColorProvider.getColor(R.color.grayishBlueDialogButtonColor)
        )
    }


    override fun getBrightGrayTheme(): DialogTheme {
        return DialogTheme(
            backgroundColor = mColorProvider.getColor(R.color.brightGrayDialogBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.brightGrayDialogTitleColor),
            textColor = mColorProvider.getColor(R.color.brightGrayDialogTextColor),
            buttonColor = mColorProvider.getColor(R.color.brightGrayDialogButtonColor)
        )
    }


    override fun getDarkSilverTheme(): DialogTheme {
        return DialogTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkSilverDialogBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.darkSilverDialogTitleColor),
            textColor = mColorProvider.getColor(R.color.darkSilverDialogTextColor),
            buttonColor = mColorProvider.getColor(R.color.darkSilverDialogButtonColor)
        )
    }


    override fun getBrowneeTheme(): DialogTheme {
        return DialogTheme(
            backgroundColor = mColorProvider.getColor(R.color.browneeDialogBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.browneeDialogTitleColor),
            textColor = mColorProvider.getColor(R.color.browneeDialogTextColor),
            buttonColor = mColorProvider.getColor(R.color.browneeDialogButtonColor)
        )
    }


    override fun getVioletTheme(): DialogTheme {
        return DialogTheme(
            backgroundColor = mColorProvider.getColor(R.color.violetDialogBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.violetDialogTitleColor),
            textColor = mColorProvider.getColor(R.color.violetDialogTextColor),
            buttonColor = mColorProvider.getColor(R.color.violetDialogButtonColor)
        )
    }


    override fun getPurpleTheme(): DialogTheme {
        return DialogTheme(
            backgroundColor = mColorProvider.getColor(R.color.purpleDialogBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.purpleDialogTitleColor),
            textColor = mColorProvider.getColor(R.color.purpleDialogTextColor),
            buttonColor = mColorProvider.getColor(R.color.purpleDialogButtonColor)
        )
    }


    override fun getBlueZodiacTheme(): DialogTheme {
        return DialogTheme(
            backgroundColor = mColorProvider.getColor(R.color.blueZodiacDialogBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.blueZodiacDialogTitleColor),
            textColor = mColorProvider.getColor(R.color.blueZodiacDialogTextColor),
            buttonColor = mColorProvider.getColor(R.color.blueZodiacDialogButtonColor)
        )
    }


    override fun getBubbleBlueTheme(): DialogTheme {
        return DialogTheme(
            backgroundColor = mColorProvider.getColor(R.color.bubbleBlueDialogBackgroundColor),
            titleColor = mColorProvider.getColor(R.color.bubbleBlueDialogTitleColor),
            textColor = mColorProvider.getColor(R.color.bubbleBlueDialogTextColor),
            buttonColor = mColorProvider.getColor(R.color.bubbleBlueDialogButtonColor)
        )
    }


}
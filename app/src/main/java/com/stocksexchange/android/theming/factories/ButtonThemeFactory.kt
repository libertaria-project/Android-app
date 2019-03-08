package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.ButtonTheme

/**
 * A factory producing instances of the [ButtonTheme] class.
 */
object ButtonThemeFactory : BaseThemeFactory<ButtonTheme>() {


    override fun getStubTheme(): ButtonTheme {
        return ButtonTheme(
            contentColor = -1,
            releasedStateBackgroundColor = -1,
            pressedStateBackgroundColor = -1
        )
    }


    override fun getDeepTealTheme(): ButtonTheme {
        return ButtonTheme(
            contentColor = mColorProvider.getColor(R.color.deepTealButtonContentColor),
            releasedStateBackgroundColor = mColorProvider.getColor(R.color.deepTealButtonReleasedStateBackgroundColor),
            pressedStateBackgroundColor = mColorProvider.getColor(R.color.deepTealButtonPressedStateBackgroundColor)
        )
    }


    override fun getNightBlueTheme(): ButtonTheme {
        return ButtonTheme(
            contentColor = mColorProvider.getColor(R.color.nightBlueButtonContentColor),
            releasedStateBackgroundColor = mColorProvider.getColor(R.color.nightBlueButtonReleasedStateBackgroundColor),
            pressedStateBackgroundColor = mColorProvider.getColor(R.color.nightBlueButtonPressedStateBackgroundColor)
        )
    }


    override fun getDarkGreenTheme(): ButtonTheme {
        return ButtonTheme(
            contentColor = mColorProvider.getColor(R.color.darkGreenButtonContentColor),
            releasedStateBackgroundColor = mColorProvider.getColor(R.color.darkGreenButtonReleasedStateBackgroundColor),
            pressedStateBackgroundColor = mColorProvider.getColor(R.color.darkGreenButtonPressedStateBackgroundColor)
        )
    }


    override fun getPitchBlackTheme(): ButtonTheme {
        return ButtonTheme(
            contentColor = mColorProvider.getColor(R.color.pitchBlackButtonContentColor),
            releasedStateBackgroundColor = mColorProvider.getColor(R.color.pitchBlackButtonReleasedStateBackgroundColor),
            pressedStateBackgroundColor = mColorProvider.getColor(R.color.pitchBlackButtonPressedStateBackgroundColor)
        )
    }


    override fun getGrayishBlueTheme(): ButtonTheme {
        return ButtonTheme(
            contentColor = mColorProvider.getColor(R.color.grayishBlueButtonContentColor),
            releasedStateBackgroundColor = mColorProvider.getColor(R.color.grayishBlueButtonReleasedStateBackgroundColor),
            pressedStateBackgroundColor = mColorProvider.getColor(R.color.grayishBlueButtonPressedStateBackgroundColor)
        )
    }


    override fun getBrightGrayTheme(): ButtonTheme {
        return ButtonTheme(
            contentColor = mColorProvider.getColor(R.color.brightGrayButtonContentColor),
            releasedStateBackgroundColor = mColorProvider.getColor(R.color.brightGrayButtonReleasedStateBackgroundColor),
            pressedStateBackgroundColor = mColorProvider.getColor(R.color.brightGrayButtonPressedStateBackgroundColor)
        )
    }


    override fun getDarkSilverTheme(): ButtonTheme {
        return ButtonTheme(
            contentColor = mColorProvider.getColor(R.color.darkSilverButtonContentColor),
            releasedStateBackgroundColor = mColorProvider.getColor(R.color.darkSilverButtonReleasedStateBackgroundColor),
            pressedStateBackgroundColor = mColorProvider.getColor(R.color.darkSilverButtonPressedStateBackgroundColor)
        )
    }


    override fun getBrowneeTheme(): ButtonTheme {
        return ButtonTheme(
            contentColor = mColorProvider.getColor(R.color.browneeButtonContentColor),
            releasedStateBackgroundColor = mColorProvider.getColor(R.color.browneeButtonReleasedStateBackgroundColor),
            pressedStateBackgroundColor = mColorProvider.getColor(R.color.browneeButtonPressedStateBackgroundColor)
        )
    }


    override fun getVioletTheme(): ButtonTheme {
        return ButtonTheme(
            contentColor = mColorProvider.getColor(R.color.violetButtonContentColor),
            releasedStateBackgroundColor = mColorProvider.getColor(R.color.violetButtonReleasedStateBackgroundColor),
            pressedStateBackgroundColor = mColorProvider.getColor(R.color.violetButtonPressedStateBackgroundColor)
        )
    }


    override fun getPurpleTheme(): ButtonTheme {
        return ButtonTheme(
            contentColor = mColorProvider.getColor(R.color.purpleButtonContentColor),
            releasedStateBackgroundColor = mColorProvider.getColor(R.color.purpleButtonReleasedStateBackgroundColor),
            pressedStateBackgroundColor = mColorProvider.getColor(R.color.purpleButtonPressedStateBackgroundColor)
        )
    }


    override fun getBlueZodiacTheme(): ButtonTheme {
        return ButtonTheme(
            contentColor = mColorProvider.getColor(R.color.blueZodiacButtonContentColor),
            releasedStateBackgroundColor = mColorProvider.getColor(R.color.blueZodiacButtonReleasedStateBackgroundColor),
            pressedStateBackgroundColor = mColorProvider.getColor(R.color.blueZodiacButtonPressedStateBackgroundColor)
        )
    }


    override fun getBubbleBlueTheme(): ButtonTheme {
        return ButtonTheme(
            contentColor = mColorProvider.getColor(R.color.bubbleBlueButtonContentColor),
            releasedStateBackgroundColor = mColorProvider.getColor(R.color.bubbleBlueButtonReleasedStateBackgroundColor),
            pressedStateBackgroundColor = mColorProvider.getColor(R.color.bubbleBlueButtonPressedStateBackgroundColor)
        )
    }


}
package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.NavigationDrawerTheme

/**
 * A factory producing instances of the [NavigationDrawerTheme] class.
 */
object NavigationDrawerFactory : BaseThemeFactory<NavigationDrawerTheme>() {


    override fun getStubTheme(): NavigationDrawerTheme {
        return NavigationDrawerTheme(
            backgroundColor = -1,
            itemColor = -1,
            signInButtonContentColor = -1,
            signInButtonReleasedStateBackgroundColor = -1,
            signInButtonPressedStateBackgroundColor = -1
        )
    }


    override fun getDeepTealTheme(): NavigationDrawerTheme {
        return NavigationDrawerTheme(
            backgroundColor = mColorProvider.getColor(R.color.deepTealNavigationDrawerBackgroundColor),
            itemColor = mColorProvider.getColor(R.color.deepTealNavigationDrawerItemColor),
            signInButtonContentColor = mColorProvider.getColor(R.color.deepTealNavigationDrawerSignInButtonContentColor),
            signInButtonReleasedStateBackgroundColor = mColorProvider.getColor(R.color.deepTealNavigationDrawerSignInButtonReleasedStateBackgroundColor),
            signInButtonPressedStateBackgroundColor = mColorProvider.getColor(R.color.deepTealNavigationDrawerSignInButtonPressedStateBackgroundColor)
        )
    }


    override fun getNightBlueTheme(): NavigationDrawerTheme {
        return NavigationDrawerTheme(
            backgroundColor = mColorProvider.getColor(R.color.nightBlueNavigationDrawerBackgroundColor),
            itemColor = mColorProvider.getColor(R.color.nightBlueNavigationDrawerItemColor),
            signInButtonContentColor = mColorProvider.getColor(R.color.nightBlueNavigationDrawerSignInButtonContentColor),
            signInButtonReleasedStateBackgroundColor = mColorProvider.getColor(R.color.nightBlueNavigationDrawerSignInButtonReleasedStateBackgroundColor),
            signInButtonPressedStateBackgroundColor = mColorProvider.getColor(R.color.nightBlueNavigationDrawerSignInButtonPressedStateBackgroundColor)
        )
    }


    override fun getDarkGreenTheme(): NavigationDrawerTheme {
        return NavigationDrawerTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkGreenNavigationDrawerBackgroundColor),
            itemColor = mColorProvider.getColor(R.color.darkGreenNavigationDrawerItemColor),
            signInButtonContentColor = mColorProvider.getColor(R.color.darkGreenNavigationDrawerSignInButtonContentColor),
            signInButtonReleasedStateBackgroundColor = mColorProvider.getColor(R.color.darkGreenNavigationDrawerSignInButtonReleasedStateBackgroundColor),
            signInButtonPressedStateBackgroundColor = mColorProvider.getColor(R.color.darkGreenNavigationDrawerSignInButtonPressedStateBackgroundColor)
        )
    }


    override fun getPitchBlackTheme(): NavigationDrawerTheme {
        return NavigationDrawerTheme(
            backgroundColor = mColorProvider.getColor(R.color.pitchBlackNavigationDrawerBackgroundColor),
            itemColor = mColorProvider.getColor(R.color.pitchBlackNavigationDrawerItemColor),
            signInButtonContentColor = mColorProvider.getColor(R.color.pitchBlackNavigationDrawerSignInButtonContentColor),
            signInButtonReleasedStateBackgroundColor = mColorProvider.getColor(R.color.pitchBlackNavigationDrawerSignInButtonReleasedStateBackgroundColor),
            signInButtonPressedStateBackgroundColor = mColorProvider.getColor(R.color.pitchBlackNavigationDrawerSignInButtonPressedStateBackgroundColor)
        )
    }


    override fun getGrayishBlueTheme(): NavigationDrawerTheme {
        return NavigationDrawerTheme(
            backgroundColor = mColorProvider.getColor(R.color.grayishBlueNavigationDrawerBackgroundColor),
            itemColor = mColorProvider.getColor(R.color.grayishBlueNavigationDrawerItemColor),
            signInButtonContentColor = mColorProvider.getColor(R.color.grayishBlueNavigationDrawerSignInButtonContentColor),
            signInButtonReleasedStateBackgroundColor = mColorProvider.getColor(R.color.grayishBlueNavigationDrawerSignInButtonReleasedStateBackgroundColor),
            signInButtonPressedStateBackgroundColor = mColorProvider.getColor(R.color.grayishBlueNavigationDrawerSignInButtonPressedStateBackgroundColor)
        )
    }


    override fun getBrightGrayTheme(): NavigationDrawerTheme {
        return NavigationDrawerTheme(
            backgroundColor = mColorProvider.getColor(R.color.brightGrayNavigationDrawerBackgroundColor),
            itemColor = mColorProvider.getColor(R.color.brightGrayNavigationDrawerItemColor),
            signInButtonContentColor = mColorProvider.getColor(R.color.brightGrayNavigationDrawerSignInButtonContentColor),
            signInButtonReleasedStateBackgroundColor = mColorProvider.getColor(R.color.brightGrayNavigationDrawerSignInButtonReleasedStateBackgroundColor),
            signInButtonPressedStateBackgroundColor = mColorProvider.getColor(R.color.brightGrayNavigationDrawerSignInButtonPressedStateBackgroundColor)
        )
    }


    override fun getDarkSilverTheme(): NavigationDrawerTheme {
        return NavigationDrawerTheme(
            backgroundColor = mColorProvider.getColor(R.color.darkSilverNavigationDrawerBackgroundColor),
            itemColor = mColorProvider.getColor(R.color.darkSilverNavigationDrawerItemColor),
            signInButtonContentColor = mColorProvider.getColor(R.color.darkSilverNavigationDrawerSignInButtonContentColor),
            signInButtonReleasedStateBackgroundColor = mColorProvider.getColor(R.color.darkSilverNavigationDrawerSignInButtonReleasedStateBackgroundColor),
            signInButtonPressedStateBackgroundColor = mColorProvider.getColor(R.color.darkSilverNavigationDrawerSignInButtonPressedStateBackgroundColor)
        )
    }


    override fun getBrowneeTheme(): NavigationDrawerTheme {
        return NavigationDrawerTheme(
            backgroundColor = mColorProvider.getColor(R.color.browneeNavigationDrawerBackgroundColor),
            itemColor = mColorProvider.getColor(R.color.browneeNavigationDrawerItemColor),
            signInButtonContentColor = mColorProvider.getColor(R.color.browneeNavigationDrawerSignInButtonContentColor),
            signInButtonReleasedStateBackgroundColor = mColorProvider.getColor(R.color.browneeNavigationDrawerSignInButtonReleasedStateBackgroundColor),
            signInButtonPressedStateBackgroundColor = mColorProvider.getColor(R.color.browneeNavigationDrawerSignInButtonPressedStateBackgroundColor)
        )
    }


    override fun getVioletTheme(): NavigationDrawerTheme {
        return NavigationDrawerTheme(
            backgroundColor = mColorProvider.getColor(R.color.violetNavigationDrawerBackgroundColor),
            itemColor = mColorProvider.getColor(R.color.violetNavigationDrawerItemColor),
            signInButtonContentColor = mColorProvider.getColor(R.color.violetNavigationDrawerSignInButtonContentColor),
            signInButtonReleasedStateBackgroundColor = mColorProvider.getColor(R.color.violetNavigationDrawerSignInButtonReleasedStateBackgroundColor),
            signInButtonPressedStateBackgroundColor = mColorProvider.getColor(R.color.violetNavigationDrawerSignInButtonPressedStateBackgroundColor)
        )
    }


    override fun getPurpleTheme(): NavigationDrawerTheme {
        return NavigationDrawerTheme(
            backgroundColor = mColorProvider.getColor(R.color.purpleNavigationDrawerBackgroundColor),
            itemColor = mColorProvider.getColor(R.color.purpleNavigationDrawerItemColor),
            signInButtonContentColor = mColorProvider.getColor(R.color.purpleNavigationDrawerSignInButtonContentColor),
            signInButtonReleasedStateBackgroundColor = mColorProvider.getColor(R.color.purpleNavigationDrawerSignInButtonReleasedStateBackgroundColor),
            signInButtonPressedStateBackgroundColor = mColorProvider.getColor(R.color.purpleNavigationDrawerSignInButtonPressedStateBackgroundColor)
        )
    }


    override fun getBlueZodiacTheme(): NavigationDrawerTheme {
        return NavigationDrawerTheme(
            backgroundColor = mColorProvider.getColor(R.color.blueZodiacNavigationDrawerBackgroundColor),
            itemColor = mColorProvider.getColor(R.color.blueZodiacNavigationDrawerItemColor),
            signInButtonContentColor = mColorProvider.getColor(R.color.blueZodiacNavigationDrawerSignInButtonContentColor),
            signInButtonReleasedStateBackgroundColor = mColorProvider.getColor(R.color.blueZodiacNavigationDrawerSignInButtonReleasedStateBackgroundColor),
            signInButtonPressedStateBackgroundColor = mColorProvider.getColor(R.color.blueZodiacNavigationDrawerSignInButtonPressedStateBackgroundColor)
        )
    }


    override fun getBubbleBlueTheme(): NavigationDrawerTheme {
        return NavigationDrawerTheme(
            backgroundColor = mColorProvider.getColor(R.color.bubbleBlueNavigationDrawerBackgroundColor),
            itemColor = mColorProvider.getColor(R.color.bubbleBlueNavigationDrawerItemColor),
            signInButtonContentColor = mColorProvider.getColor(R.color.bubbleBlueNavigationDrawerSignInButtonContentColor),
            signInButtonReleasedStateBackgroundColor = mColorProvider.getColor(R.color.bubbleBlueNavigationDrawerSignInButtonReleasedStateBackgroundColor),
            signInButtonPressedStateBackgroundColor = mColorProvider.getColor(R.color.bubbleBlueNavigationDrawerSignInButtonPressedStateBackgroundColor)
        )
    }


}
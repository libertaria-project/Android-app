package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.SwitchTheme

/**
 * A factory producing instances of the [SwitchTheme] class.
 */
object SwitchThemeFactory : BaseThemeFactory<SwitchTheme>() {


    override fun getStubTheme(): SwitchTheme {
        return SwitchTheme(
            pointerActivatedColor = -1,
            pointerDeactivatedColor = -1,
            backgroundActivatedColor = -1,
            backgroundDeactivatedColor = -1
        )
    }


    override fun getDeepTealTheme(): SwitchTheme {
        return SwitchTheme(
            pointerActivatedColor = mColorProvider.getColor(R.color.deepTealSwitchPointerActivatedColor),
            pointerDeactivatedColor = mColorProvider.getColor(R.color.deepTealSwitchPointerDeactivatedColor),
            backgroundActivatedColor = mColorProvider.getColor(R.color.deepTealSwitchBackgroundActivatedColor),
            backgroundDeactivatedColor = mColorProvider.getColor(R.color.deepTealSwitchBackgroundDeactivatedColor)
        )
    }


    override fun getNightBlueTheme(): SwitchTheme {
        return SwitchTheme(
            pointerActivatedColor = mColorProvider.getColor(R.color.nightBlueSwitchPointerActivatedColor),
            pointerDeactivatedColor = mColorProvider.getColor(R.color.nightBlueSwitchPointerDeactivatedColor),
            backgroundActivatedColor = mColorProvider.getColor(R.color.nightBlueSwitchBackgroundActivatedColor),
            backgroundDeactivatedColor = mColorProvider.getColor(R.color.nightBlueSwitchBackgroundDeactivatedColor)
        )
    }


    override fun getDarkGreenTheme(): SwitchTheme {
        return SwitchTheme(
            pointerActivatedColor = mColorProvider.getColor(R.color.darkGreenSwitchPointerActivatedColor),
            pointerDeactivatedColor = mColorProvider.getColor(R.color.darkGreenSwitchPointerDeactivatedColor),
            backgroundActivatedColor = mColorProvider.getColor(R.color.darkGreenSwitchBackgroundActivatedColor),
            backgroundDeactivatedColor = mColorProvider.getColor(R.color.darkGreenSwitchBackgroundDeactivatedColor)
        )
    }


    override fun getPitchBlackTheme(): SwitchTheme {
        return SwitchTheme(
            pointerActivatedColor = mColorProvider.getColor(R.color.pitchBlackSwitchPointerActivatedColor),
            pointerDeactivatedColor = mColorProvider.getColor(R.color.pitchBlackSwitchPointerDeactivatedColor),
            backgroundActivatedColor = mColorProvider.getColor(R.color.pitchBlackSwitchBackgroundActivatedColor),
            backgroundDeactivatedColor = mColorProvider.getColor(R.color.pitchBlackSwitchBackgroundDeactivatedColor)
        )
    }


    override fun getGrayishBlueTheme(): SwitchTheme {
        return SwitchTheme(
            pointerActivatedColor = mColorProvider.getColor(R.color.grayishBlueSwitchPointerActivatedColor),
            pointerDeactivatedColor = mColorProvider.getColor(R.color.grayishBlueSwitchPointerDeactivatedColor),
            backgroundActivatedColor = mColorProvider.getColor(R.color.grayishBlueSwitchBackgroundActivatedColor),
            backgroundDeactivatedColor = mColorProvider.getColor(R.color.grayishBlueSwitchBackgroundDeactivatedColor)
        )
    }


    override fun getBrightGrayTheme(): SwitchTheme {
        return SwitchTheme(
            pointerActivatedColor = mColorProvider.getColor(R.color.brightGraySwitchPointerActivatedColor),
            pointerDeactivatedColor = mColorProvider.getColor(R.color.brightGraySwitchPointerDeactivatedColor),
            backgroundActivatedColor = mColorProvider.getColor(R.color.brightGraySwitchBackgroundActivatedColor),
            backgroundDeactivatedColor = mColorProvider.getColor(R.color.brightGraySwitchBackgroundDeactivatedColor)
        )
    }


    override fun getDarkSilverTheme(): SwitchTheme {
        return SwitchTheme(
            pointerActivatedColor = mColorProvider.getColor(R.color.darkSilverSwitchPointerActivatedColor),
            pointerDeactivatedColor = mColorProvider.getColor(R.color.darkSilverSwitchPointerDeactivatedColor),
            backgroundActivatedColor = mColorProvider.getColor(R.color.darkSilverSwitchBackgroundActivatedColor),
            backgroundDeactivatedColor = mColorProvider.getColor(R.color.darkSilverSwitchBackgroundDeactivatedColor)
        )
    }


    override fun getBrowneeTheme(): SwitchTheme {
        return SwitchTheme(
            pointerActivatedColor = mColorProvider.getColor(R.color.browneeSwitchPointerActivatedColor),
            pointerDeactivatedColor = mColorProvider.getColor(R.color.browneeSwitchPointerDeactivatedColor),
            backgroundActivatedColor = mColorProvider.getColor(R.color.browneeSwitchBackgroundActivatedColor),
            backgroundDeactivatedColor = mColorProvider.getColor(R.color.browneeSwitchBackgroundDeactivatedColor)
        )
    }


    override fun getVioletTheme(): SwitchTheme {
        return SwitchTheme(
            pointerActivatedColor = mColorProvider.getColor(R.color.violetSwitchPointerActivatedColor),
            pointerDeactivatedColor = mColorProvider.getColor(R.color.violetSwitchPointerDeactivatedColor),
            backgroundActivatedColor = mColorProvider.getColor(R.color.violetSwitchBackgroundActivatedColor),
            backgroundDeactivatedColor = mColorProvider.getColor(R.color.violetSwitchBackgroundDeactivatedColor)
        )
    }


    override fun getPurpleTheme(): SwitchTheme {
        return SwitchTheme(
            pointerActivatedColor = mColorProvider.getColor(R.color.purpleSwitchPointerActivatedColor),
            pointerDeactivatedColor = mColorProvider.getColor(R.color.purpleSwitchPointerDeactivatedColor),
            backgroundActivatedColor = mColorProvider.getColor(R.color.purpleSwitchBackgroundActivatedColor),
            backgroundDeactivatedColor = mColorProvider.getColor(R.color.purpleSwitchBackgroundDeactivatedColor)
        )
    }


    override fun getBlueZodiacTheme(): SwitchTheme {
        return SwitchTheme(
            pointerActivatedColor = mColorProvider.getColor(R.color.blueZodiacSwitchPointerActivatedColor),
            pointerDeactivatedColor = mColorProvider.getColor(R.color.blueZodiacSwitchPointerDeactivatedColor),
            backgroundActivatedColor = mColorProvider.getColor(R.color.blueZodiacSwitchBackgroundActivatedColor),
            backgroundDeactivatedColor = mColorProvider.getColor(R.color.blueZodiacSwitchBackgroundDeactivatedColor)
        )
    }


    override fun getBubbleBlueTheme(): SwitchTheme {
        return SwitchTheme(
            pointerActivatedColor = mColorProvider.getColor(R.color.bubbleBlueSwitchPointerActivatedColor),
            pointerDeactivatedColor = mColorProvider.getColor(R.color.bubbleBlueSwitchPointerDeactivatedColor),
            backgroundActivatedColor = mColorProvider.getColor(R.color.bubbleBlueSwitchBackgroundActivatedColor),
            backgroundDeactivatedColor = mColorProvider.getColor(R.color.bubbleBlueSwitchBackgroundDeactivatedColor)
        )
    }


}
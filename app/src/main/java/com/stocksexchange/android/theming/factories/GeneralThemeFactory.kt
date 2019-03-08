package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.GeneralTheme

/**
 * A factory producing instances of the [GeneralTheme] class.
 */
object GeneralThemeFactory : BaseThemeFactory<GeneralTheme>() {


    override fun getStubTheme(): GeneralTheme {
        return GeneralTheme(
            primaryColor = -1,
            primaryLightColor = -1,
            primaryDarkColor = -1,
            primaryTextColor = -1,
            primaryDarkTextColor = -1,
            secondaryTextColor = -1,
            secondaryDarkTextColor = -1,
            accentColor = -1,
            darkAccentColor = -1,
            contentContainerColor = -1,
            contentContainerLightColor = -1,
            contentContainerTextColor = -1,
            tabIndicatorColor = -1,
            infoViewColor = -1,
            progressBarColor = -1,
            gradientStartColor = -1,
            gradientEndColor = -1,
            linkReleasedStateBackgroundColor = -1,
            linkPressedStateBackgroundColor = -1,
            feedbackCardViewColor = -1,
            feedbackFooterButtonColor = -1,
            settingsBackgroundColor = -1
        )
    }


    override fun getDeepTealTheme(): GeneralTheme {
        return GeneralTheme(
            primaryColor = mColorProvider.getColor(R.color.deepTealPrimaryColor),
            primaryLightColor = mColorProvider.getColor(R.color.deepTealPrimaryLightColor),
            primaryDarkColor = mColorProvider.getColor(R.color.deepTealPrimaryDarkColor),
            primaryTextColor = mColorProvider.getColor(R.color.deepTealPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.deepTealPrimaryDarkTextColor),
            secondaryTextColor = mColorProvider.getColor(R.color.deepTealSecondaryTextColor),
            secondaryDarkTextColor = mColorProvider.getColor(R.color.deepTealSecondaryDarkTextColor),
            accentColor = mColorProvider.getColor(R.color.deepTealAccentColor),
            darkAccentColor = mColorProvider.getColor(R.color.deepTealDarkAccentColor),
            contentContainerColor = mColorProvider.getColor(R.color.deepTealContentContainerColor),
            contentContainerLightColor = mColorProvider.getColor(R.color.deepTealContentContainerLightColor),
            contentContainerTextColor = mColorProvider.getColor(R.color.deepTealContentContainerTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.deepTealTabIndicatorColor),
            infoViewColor = mColorProvider.getColor(R.color.deepTealInfoViewColor),
            progressBarColor = mColorProvider.getColor(R.color.deepTealProgressBarColor),
            gradientStartColor = mColorProvider.getColor(R.color.deepTealGradientStartColor),
            gradientEndColor = mColorProvider.getColor(R.color.deepTealGradientEndColor),
            linkReleasedStateBackgroundColor = mColorProvider.getColor(R.color.deepTealLinkReleasedStateBackgroundColor),
            linkPressedStateBackgroundColor = mColorProvider.getColor(R.color.deepTealLinkPressedStateBackgroundColor),
            feedbackCardViewColor = mColorProvider.getColor(R.color.deepTealFeedbackCardViewColor),
            feedbackFooterButtonColor = mColorProvider.getColor(R.color.deepTealFeedbackFooterButtonColor),
            settingsBackgroundColor = mColorProvider.getColor(R.color.deepTealSettingsBackgroundColor)
        )
    }


    override fun getNightBlueTheme(): GeneralTheme {
        return GeneralTheme(
            primaryColor = mColorProvider.getColor(R.color.nightBluePrimaryColor),
            primaryLightColor = mColorProvider.getColor(R.color.nightBluePrimaryLightColor),
            primaryDarkColor = mColorProvider.getColor(R.color.nightBluePrimaryDarkColor),
            primaryTextColor = mColorProvider.getColor(R.color.nightBluePrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.nightBluePrimaryDarkTextColor),
            secondaryTextColor = mColorProvider.getColor(R.color.nightBlueSecondaryTextColor),
            secondaryDarkTextColor = mColorProvider.getColor(R.color.nightBlueSecondaryDarkTextColor),
            accentColor = mColorProvider.getColor(R.color.nightBlueAccentColor),
            darkAccentColor = mColorProvider.getColor(R.color.nightBlueDarkAccentColor),
            contentContainerColor = mColorProvider.getColor(R.color.nightBlueContentContainerColor),
            contentContainerLightColor = mColorProvider.getColor(R.color.nightBlueContentContainerLightColor),
            contentContainerTextColor = mColorProvider.getColor(R.color.nightBlueContentContainerTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.nightBlueTabIndicatorColor),
            infoViewColor = mColorProvider.getColor(R.color.nightBlueInfoViewColor),
            progressBarColor = mColorProvider.getColor(R.color.nightBlueProgressBarColor),
            gradientStartColor = mColorProvider.getColor(R.color.nightBlueGradientStartColor),
            gradientEndColor = mColorProvider.getColor(R.color.nightBlueGradientEndColor),
            linkReleasedStateBackgroundColor = mColorProvider.getColor(R.color.nightBlueLinkReleasedStateBackgroundColor),
            linkPressedStateBackgroundColor = mColorProvider.getColor(R.color.nightBlueLinkPressedStateBackgroundColor),
            feedbackCardViewColor = mColorProvider.getColor(R.color.nightBlueFeedbackCardViewColor),
            feedbackFooterButtonColor = mColorProvider.getColor(R.color.nightBlueFeedbackFooterButtonColor),
            settingsBackgroundColor = mColorProvider.getColor(R.color.nightBlueSettingsBackgroundColor)
        )
    }


    override fun getDarkGreenTheme(): GeneralTheme {
        return GeneralTheme(
            primaryColor = mColorProvider.getColor(R.color.darkGreenPrimaryColor),
            primaryLightColor = mColorProvider.getColor(R.color.darkGreenPrimaryLightColor),
            primaryDarkColor = mColorProvider.getColor(R.color.darkGreenPrimaryDarkColor),
            primaryTextColor = mColorProvider.getColor(R.color.darkGreenPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.darkGreenPrimaryDarkTextColor),
            secondaryTextColor = mColorProvider.getColor(R.color.darkGreenSecondaryTextColor),
            secondaryDarkTextColor = mColorProvider.getColor(R.color.darkGreenSecondaryDarkTextColor),
            accentColor = mColorProvider.getColor(R.color.darkGreenAccentColor),
            darkAccentColor = mColorProvider.getColor(R.color.darkGreenDarkAccentColor),
            contentContainerColor = mColorProvider.getColor(R.color.darkGreenContentContainerColor),
            contentContainerLightColor = mColorProvider.getColor(R.color.darkGreenContentContainerLightColor),
            contentContainerTextColor = mColorProvider.getColor(R.color.darkGreenContentContainerTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.darkGreenTabIndicatorColor),
            infoViewColor = mColorProvider.getColor(R.color.darkGreenInfoViewColor),
            progressBarColor = mColorProvider.getColor(R.color.darkGreenProgressBarColor),
            gradientStartColor = mColorProvider.getColor(R.color.darkGreenGradientStartColor),
            gradientEndColor = mColorProvider.getColor(R.color.darkGreenGradientEndColor),
            linkReleasedStateBackgroundColor = mColorProvider.getColor(R.color.darkGreenLinkReleasedStateBackgroundColor),
            linkPressedStateBackgroundColor = mColorProvider.getColor(R.color.darkGreenLinkPressedStateBackgroundColor),
            feedbackCardViewColor = mColorProvider.getColor(R.color.darkGreenFeedbackCardViewColor),
            feedbackFooterButtonColor = mColorProvider.getColor(R.color.darkGreenFeedbackFooterButtonColor),
            settingsBackgroundColor = mColorProvider.getColor(R.color.darkGreenSettingsBackgroundColor)
        )
    }


    override fun getPitchBlackTheme(): GeneralTheme {
        return GeneralTheme(
            primaryColor = mColorProvider.getColor(R.color.pitchBlackPrimaryColor),
            primaryLightColor = mColorProvider.getColor(R.color.pitchBlackPrimaryLightColor),
            primaryDarkColor = mColorProvider.getColor(R.color.pitchBlackPrimaryDarkColor),
            primaryTextColor = mColorProvider.getColor(R.color.pitchBlackPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.pitchBlackPrimaryDarkTextColor),
            secondaryTextColor = mColorProvider.getColor(R.color.pitchBlackSecondaryTextColor),
            secondaryDarkTextColor = mColorProvider.getColor(R.color.pitchBlackSecondaryDarkTextColor),
            accentColor = mColorProvider.getColor(R.color.pitchBlackAccentColor),
            darkAccentColor = mColorProvider.getColor(R.color.pitchBlackDarkAccentColor),
            contentContainerColor = mColorProvider.getColor(R.color.pitchBlackContentContainerColor),
            contentContainerLightColor = mColorProvider.getColor(R.color.pitchBlackContentContainerLightColor),
            contentContainerTextColor = mColorProvider.getColor(R.color.pitchBlackContentContainerTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.pitchBlackTabIndicatorColor),
            infoViewColor = mColorProvider.getColor(R.color.pitchBlackInfoViewColor),
            progressBarColor = mColorProvider.getColor(R.color.pitchBlackProgressBarColor),
            gradientStartColor = mColorProvider.getColor(R.color.pitchBlackGradientStartColor),
            gradientEndColor = mColorProvider.getColor(R.color.pitchBlackGradientEndColor),
            linkReleasedStateBackgroundColor = mColorProvider.getColor(R.color.pitchBlackLinkReleasedStateBackgroundColor),
            linkPressedStateBackgroundColor = mColorProvider.getColor(R.color.pitchBlackLinkPressedStateBackgroundColor),
            feedbackCardViewColor = mColorProvider.getColor(R.color.pitchBlackFeedbackCardViewColor),
            feedbackFooterButtonColor = mColorProvider.getColor(R.color.pitchBlackFeedbackFooterButtonColor),
            settingsBackgroundColor = mColorProvider.getColor(R.color.pitchBlackSettingsBackgroundColor)
        )
    }


    override fun getGrayishBlueTheme(): GeneralTheme {
        return GeneralTheme(
            primaryColor = mColorProvider.getColor(R.color.grayishBluePrimaryColor),
            primaryLightColor = mColorProvider.getColor(R.color.grayishBluePrimaryLightColor),
            primaryDarkColor = mColorProvider.getColor(R.color.grayishBluePrimaryDarkColor),
            primaryTextColor = mColorProvider.getColor(R.color.grayishBluePrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.grayishBluePrimaryDarkTextColor),
            secondaryTextColor = mColorProvider.getColor(R.color.grayishBlueSecondaryTextColor),
            secondaryDarkTextColor = mColorProvider.getColor(R.color.grayishBlueSecondaryDarkTextColor),
            accentColor = mColorProvider.getColor(R.color.grayishBlueAccentColor),
            darkAccentColor = mColorProvider.getColor(R.color.grayishBlueDarkAccentColor),
            contentContainerColor = mColorProvider.getColor(R.color.grayishBlueContentContainerColor),
            contentContainerLightColor = mColorProvider.getColor(R.color.grayishBlueContentContainerLightColor),
            contentContainerTextColor = mColorProvider.getColor(R.color.grayishBlueContentContainerTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.grayishBlueTabIndicatorColor),
            infoViewColor = mColorProvider.getColor(R.color.grayishBlueInfoViewColor),
            progressBarColor = mColorProvider.getColor(R.color.grayishBlueProgressBarColor),
            gradientStartColor = mColorProvider.getColor(R.color.grayishBlueGradientStartColor),
            gradientEndColor = mColorProvider.getColor(R.color.grayishBlueGradientEndColor),
            linkReleasedStateBackgroundColor = mColorProvider.getColor(R.color.grayishBlueLinkReleasedStateBackgroundColor),
            linkPressedStateBackgroundColor = mColorProvider.getColor(R.color.grayishBlueLinkPressedStateBackgroundColor),
            feedbackCardViewColor = mColorProvider.getColor(R.color.grayishBlueFeedbackCardViewColor),
            feedbackFooterButtonColor = mColorProvider.getColor(R.color.grayishBlueFeedbackFooterButtonColor),
            settingsBackgroundColor = mColorProvider.getColor(R.color.grayishBlueSettingsBackgroundColor)
        )
    }


    override fun getBrightGrayTheme(): GeneralTheme {
        return GeneralTheme(
            primaryColor = mColorProvider.getColor(R.color.brightGrayPrimaryColor),
            primaryLightColor = mColorProvider.getColor(R.color.brightGrayPrimaryLightColor),
            primaryDarkColor = mColorProvider.getColor(R.color.brightGrayPrimaryDarkColor),
            primaryTextColor = mColorProvider.getColor(R.color.brightGrayPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.brightGrayPrimaryDarkTextColor),
            secondaryTextColor = mColorProvider.getColor(R.color.brightGraySecondaryTextColor),
            secondaryDarkTextColor = mColorProvider.getColor(R.color.brightGraySecondaryDarkTextColor),
            accentColor = mColorProvider.getColor(R.color.brightGrayAccentColor),
            darkAccentColor = mColorProvider.getColor(R.color.brightGrayDarkAccentColor),
            contentContainerColor = mColorProvider.getColor(R.color.brightGrayContentContainerColor),
            contentContainerLightColor = mColorProvider.getColor(R.color.brightGrayContentContainerLightColor),
            contentContainerTextColor = mColorProvider.getColor(R.color.brightGrayContentContainerTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.brightGrayTabIndicatorColor),
            infoViewColor = mColorProvider.getColor(R.color.brightGrayInfoViewColor),
            progressBarColor = mColorProvider.getColor(R.color.brightGrayProgressBarColor),
            gradientStartColor = mColorProvider.getColor(R.color.brightGrayGradientStartColor),
            gradientEndColor = mColorProvider.getColor(R.color.brightGrayGradientEndColor),
            linkReleasedStateBackgroundColor = mColorProvider.getColor(R.color.brightGrayLinkReleasedStateBackgroundColor),
            linkPressedStateBackgroundColor = mColorProvider.getColor(R.color.brightGrayLinkPressedStateBackgroundColor),
            feedbackCardViewColor = mColorProvider.getColor(R.color.brightGrayFeedbackCardViewColor),
            feedbackFooterButtonColor = mColorProvider.getColor(R.color.brightGrayFeedbackFooterButtonColor),
            settingsBackgroundColor = mColorProvider.getColor(R.color.brightGraySettingsBackgroundColor)
        )
    }


    override fun getDarkSilverTheme(): GeneralTheme {
        return GeneralTheme(
            primaryColor = mColorProvider.getColor(R.color.darkSilverPrimaryColor),
            primaryLightColor = mColorProvider.getColor(R.color.darkSilverPrimaryLightColor),
            primaryDarkColor = mColorProvider.getColor(R.color.darkSilverPrimaryDarkColor),
            primaryTextColor = mColorProvider.getColor(R.color.darkSilverPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.darkSilverPrimaryDarkTextColor),
            secondaryTextColor = mColorProvider.getColor(R.color.darkSilverSecondaryTextColor),
            secondaryDarkTextColor = mColorProvider.getColor(R.color.darkSilverSecondaryDarkTextColor),
            accentColor = mColorProvider.getColor(R.color.darkSilverAccentColor),
            darkAccentColor = mColorProvider.getColor(R.color.darkSilverDarkAccentColor),
            contentContainerColor = mColorProvider.getColor(R.color.darkSilverContentContainerColor),
            contentContainerLightColor = mColorProvider.getColor(R.color.darkSilverContentContainerLightColor),
            contentContainerTextColor = mColorProvider.getColor(R.color.darkSilverContentContainerTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.darkSilverTabIndicatorColor),
            infoViewColor = mColorProvider.getColor(R.color.darkSilverInfoViewColor),
            progressBarColor = mColorProvider.getColor(R.color.darkSilverProgressBarColor),
            gradientStartColor = mColorProvider.getColor(R.color.darkSilverGradientStartColor),
            gradientEndColor = mColorProvider.getColor(R.color.darkSilverGradientEndColor),
            linkReleasedStateBackgroundColor = mColorProvider.getColor(R.color.darkSilverLinkReleasedStateBackgroundColor),
            linkPressedStateBackgroundColor = mColorProvider.getColor(R.color.darkSilverLinkPressedStateBackgroundColor),
            feedbackCardViewColor = mColorProvider.getColor(R.color.darkSilverFeedbackCardViewColor),
            feedbackFooterButtonColor = mColorProvider.getColor(R.color.darkSilverFeedbackFooterButtonColor),
            settingsBackgroundColor = mColorProvider.getColor(R.color.darkSilverSettingsBackgroundColor)
        )
    }


    override fun getBrowneeTheme(): GeneralTheme {
        return GeneralTheme(
            primaryColor = mColorProvider.getColor(R.color.browneePrimaryColor),
            primaryLightColor = mColorProvider.getColor(R.color.browneePrimaryLightColor),
            primaryDarkColor = mColorProvider.getColor(R.color.browneePrimaryDarkColor),
            primaryTextColor = mColorProvider.getColor(R.color.browneePrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.browneePrimaryDarkTextColor),
            secondaryTextColor = mColorProvider.getColor(R.color.browneeSecondaryTextColor),
            secondaryDarkTextColor = mColorProvider.getColor(R.color.browneeSecondaryDarkTextColor),
            accentColor = mColorProvider.getColor(R.color.browneeAccentColor),
            darkAccentColor = mColorProvider.getColor(R.color.browneeDarkAccentColor),
            contentContainerColor = mColorProvider.getColor(R.color.browneeContentContainerColor),
            contentContainerLightColor = mColorProvider.getColor(R.color.browneeContentContainerLightColor),
            contentContainerTextColor = mColorProvider.getColor(R.color.browneeContentContainerTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.browneeTabIndicatorColor),
            infoViewColor = mColorProvider.getColor(R.color.browneeInfoViewColor),
            progressBarColor = mColorProvider.getColor(R.color.browneeProgressBarColor),
            gradientStartColor = mColorProvider.getColor(R.color.browneeGradientStartColor),
            gradientEndColor = mColorProvider.getColor(R.color.browneeGradientEndColor),
            linkReleasedStateBackgroundColor = mColorProvider.getColor(R.color.browneeLinkReleasedStateBackgroundColor),
            linkPressedStateBackgroundColor = mColorProvider.getColor(R.color.browneeLinkPressedStateBackgroundColor),
            feedbackCardViewColor = mColorProvider.getColor(R.color.browneeFeedbackCardViewColor),
            feedbackFooterButtonColor = mColorProvider.getColor(R.color.browneeFeedbackFooterButtonColor),
            settingsBackgroundColor = mColorProvider.getColor(R.color.browneeSettingsBackgroundColor)
        )
    }


    override fun getVioletTheme(): GeneralTheme {
        return GeneralTheme(
            primaryColor = mColorProvider.getColor(R.color.violetPrimaryColor),
            primaryLightColor = mColorProvider.getColor(R.color.violetPrimaryLightColor),
            primaryDarkColor = mColorProvider.getColor(R.color.violetPrimaryDarkColor),
            primaryTextColor = mColorProvider.getColor(R.color.violetPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.violetPrimaryDarkTextColor),
            secondaryTextColor = mColorProvider.getColor(R.color.violetSecondaryTextColor),
            secondaryDarkTextColor = mColorProvider.getColor(R.color.violetSecondaryDarkTextColor),
            accentColor = mColorProvider.getColor(R.color.violetAccentColor),
            darkAccentColor = mColorProvider.getColor(R.color.violetDarkAccentColor),
            contentContainerColor = mColorProvider.getColor(R.color.violetContentContainerColor),
            contentContainerLightColor = mColorProvider.getColor(R.color.violetContentContainerLightColor),
            contentContainerTextColor = mColorProvider.getColor(R.color.violetContentContainerTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.violetTabIndicatorColor),
            infoViewColor = mColorProvider.getColor(R.color.violetInfoViewColor),
            progressBarColor = mColorProvider.getColor(R.color.violetProgressBarColor),
            gradientStartColor = mColorProvider.getColor(R.color.violetGradientStartColor),
            gradientEndColor = mColorProvider.getColor(R.color.violetGradientEndColor),
            linkReleasedStateBackgroundColor = mColorProvider.getColor(R.color.violetLinkReleasedStateBackgroundColor),
            linkPressedStateBackgroundColor = mColorProvider.getColor(R.color.violetLinkPressedStateBackgroundColor),
            feedbackCardViewColor = mColorProvider.getColor(R.color.violetFeedbackCardViewColor),
            feedbackFooterButtonColor = mColorProvider.getColor(R.color.violetFeedbackFooterButtonColor),
            settingsBackgroundColor = mColorProvider.getColor(R.color.violetSettingsBackgroundColor)
        )
    }


    override fun getPurpleTheme(): GeneralTheme {
        return GeneralTheme(
            primaryColor = mColorProvider.getColor(R.color.purplePrimaryColor),
            primaryLightColor = mColorProvider.getColor(R.color.purplePrimaryLightColor),
            primaryDarkColor = mColorProvider.getColor(R.color.purplePrimaryDarkColor),
            primaryTextColor = mColorProvider.getColor(R.color.purplePrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.purplePrimaryDarkTextColor),
            secondaryTextColor = mColorProvider.getColor(R.color.purpleSecondaryTextColor),
            secondaryDarkTextColor = mColorProvider.getColor(R.color.purpleSecondaryDarkTextColor),
            accentColor = mColorProvider.getColor(R.color.purpleAccentColor),
            darkAccentColor = mColorProvider.getColor(R.color.purpleDarkAccentColor),
            contentContainerColor = mColorProvider.getColor(R.color.purpleContentContainerColor),
            contentContainerLightColor = mColorProvider.getColor(R.color.purpleContentContainerLightColor),
            contentContainerTextColor = mColorProvider.getColor(R.color.purpleContentContainerTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.purpleTabIndicatorColor),
            infoViewColor = mColorProvider.getColor(R.color.purpleInfoViewColor),
            progressBarColor = mColorProvider.getColor(R.color.purpleProgressBarColor),
            gradientStartColor = mColorProvider.getColor(R.color.purpleGradientStartColor),
            gradientEndColor = mColorProvider.getColor(R.color.purpleGradientEndColor),
            linkReleasedStateBackgroundColor = mColorProvider.getColor(R.color.purpleLinkReleasedStateBackgroundColor),
            linkPressedStateBackgroundColor = mColorProvider.getColor(R.color.purpleLinkPressedStateBackgroundColor),
            feedbackCardViewColor = mColorProvider.getColor(R.color.purpleFeedbackCardViewColor),
            feedbackFooterButtonColor = mColorProvider.getColor(R.color.purpleFeedbackFooterButtonColor),
            settingsBackgroundColor = mColorProvider.getColor(R.color.purpleSettingsBackgroundColor)
        )
    }


    override fun getBlueZodiacTheme(): GeneralTheme {
        return GeneralTheme(
            primaryColor = mColorProvider.getColor(R.color.blueZodiacPrimaryColor),
            primaryLightColor = mColorProvider.getColor(R.color.blueZodiacPrimaryLightColor),
            primaryDarkColor = mColorProvider.getColor(R.color.blueZodiacPrimaryDarkColor),
            primaryTextColor = mColorProvider.getColor(R.color.blueZodiacPrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.blueZodiacPrimaryDarkTextColor),
            secondaryTextColor = mColorProvider.getColor(R.color.blueZodiacSecondaryTextColor),
            secondaryDarkTextColor = mColorProvider.getColor(R.color.blueZodiacSecondaryDarkTextColor),
            accentColor = mColorProvider.getColor(R.color.blueZodiacAccentColor),
            darkAccentColor = mColorProvider.getColor(R.color.blueZodiacDarkAccentColor),
            contentContainerColor = mColorProvider.getColor(R.color.blueZodiacContentContainerColor),
            contentContainerLightColor = mColorProvider.getColor(R.color.blueZodiacContentContainerLightColor),
            contentContainerTextColor = mColorProvider.getColor(R.color.blueZodiacContentContainerTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.blueZodiacTabIndicatorColor),
            infoViewColor = mColorProvider.getColor(R.color.blueZodiacInfoViewColor),
            progressBarColor = mColorProvider.getColor(R.color.blueZodiacProgressBarColor),
            gradientStartColor = mColorProvider.getColor(R.color.blueZodiacGradientStartColor),
            gradientEndColor = mColorProvider.getColor(R.color.blueZodiacGradientEndColor),
            linkReleasedStateBackgroundColor = mColorProvider.getColor(R.color.blueZodiacLinkReleasedStateBackgroundColor),
            linkPressedStateBackgroundColor = mColorProvider.getColor(R.color.blueZodiacLinkPressedStateBackgroundColor),
            feedbackCardViewColor = mColorProvider.getColor(R.color.blueZodiacFeedbackCardViewColor),
            feedbackFooterButtonColor = mColorProvider.getColor(R.color.blueZodiacFeedbackFooterButtonColor),
            settingsBackgroundColor = mColorProvider.getColor(R.color.blueZodiacSettingsBackgroundColor)
        )
    }


    override fun getBubbleBlueTheme(): GeneralTheme {
        return GeneralTheme(
            primaryColor = mColorProvider.getColor(R.color.bubbleBluePrimaryColor),
            primaryLightColor = mColorProvider.getColor(R.color.bubbleBluePrimaryLightColor),
            primaryDarkColor = mColorProvider.getColor(R.color.bubbleBluePrimaryDarkColor),
            primaryTextColor = mColorProvider.getColor(R.color.bubbleBluePrimaryTextColor),
            primaryDarkTextColor = mColorProvider.getColor(R.color.bubbleBluePrimaryDarkTextColor),
            secondaryTextColor = mColorProvider.getColor(R.color.bubbleBlueSecondaryTextColor),
            secondaryDarkTextColor = mColorProvider.getColor(R.color.bubbleBlueSecondaryDarkTextColor),
            accentColor = mColorProvider.getColor(R.color.bubbleBlueAccentColor),
            darkAccentColor = mColorProvider.getColor(R.color.bubbleBlueDarkAccentColor),
            contentContainerColor = mColorProvider.getColor(R.color.bubbleBlueContentContainerColor),
            contentContainerLightColor = mColorProvider.getColor(R.color.bubbleBlueContentContainerLightColor),
            contentContainerTextColor = mColorProvider.getColor(R.color.bubbleBlueContentContainerTextColor),
            tabIndicatorColor = mColorProvider.getColor(R.color.bubbleBlueTabIndicatorColor),
            infoViewColor = mColorProvider.getColor(R.color.bubbleBlueInfoViewColor),
            progressBarColor = mColorProvider.getColor(R.color.bubbleBlueProgressBarColor),
            gradientStartColor = mColorProvider.getColor(R.color.bubbleBlueGradientStartColor),
            gradientEndColor = mColorProvider.getColor(R.color.bubbleBlueGradientEndColor),
            linkReleasedStateBackgroundColor = mColorProvider.getColor(R.color.bubbleBlueLinkReleasedStateBackgroundColor),
            linkPressedStateBackgroundColor = mColorProvider.getColor(R.color.bubbleBlueLinkPressedStateBackgroundColor),
            feedbackCardViewColor = mColorProvider.getColor(R.color.bubbleBlueFeedbackCardViewColor),
            feedbackFooterButtonColor = mColorProvider.getColor(R.color.bubbleBlueFeedbackFooterButtonColor),
            settingsBackgroundColor = mColorProvider.getColor(R.color.bubbleBlueSettingsBackgroundColor)
        )
    }


}
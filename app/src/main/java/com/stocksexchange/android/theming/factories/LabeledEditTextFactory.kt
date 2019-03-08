package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.LabeledEditTextTheme

/**
 * A factory producing instances of the [LabeledEditTextTheme] class.
 */
object LabeledEditTextFactory : BaseThemeFactory<LabeledEditTextTheme>() {


    override fun getStubTheme(): LabeledEditTextTheme {
        return LabeledEditTextTheme(
            titleColor = -1,
            containerColor = -1,
            hintColor = -1,
            textColor = -1,
            cursorColor = -1,
            labelColor = -1
        )
    }


    override fun getDeepTealTheme(): LabeledEditTextTheme {
        return LabeledEditTextTheme(
            titleColor = mColorProvider.getColor(R.color.deepTealLabeledEditTextTitleColor),
            containerColor = mColorProvider.getColor(R.color.deepTealLabeledEditTextContainerColor),
            hintColor = mColorProvider.getColor(R.color.deepTealLabeledEditTextHintColor),
            textColor = mColorProvider.getColor(R.color.deepTealLabeledEditTextTextColor),
            cursorColor = mColorProvider.getColor(R.color.deepTealLabeledEditTextCursorColor),
            labelColor = mColorProvider.getColor(R.color.deepTealLabeledEditTextLabelColor)
        )
    }


    override fun getNightBlueTheme(): LabeledEditTextTheme {
        return LabeledEditTextTheme(
            titleColor = mColorProvider.getColor(R.color.nightBlueLabeledEditTextTitleColor),
            containerColor = mColorProvider.getColor(R.color.nightBlueLabeledEditTextContainerColor),
            hintColor = mColorProvider.getColor(R.color.nightBlueLabeledEditTextHintColor),
            textColor = mColorProvider.getColor(R.color.nightBlueLabeledEditTextTextColor),
            cursorColor = mColorProvider.getColor(R.color.nightBlueLabeledEditTextCursorColor),
            labelColor = mColorProvider.getColor(R.color.nightBlueLabeledEditTextLabelColor)
        )
    }


    override fun getDarkGreenTheme(): LabeledEditTextTheme {
        return LabeledEditTextTheme(
            titleColor = mColorProvider.getColor(R.color.darkGreenLabeledEditTextTitleColor),
            containerColor = mColorProvider.getColor(R.color.darkGreenLabeledEditTextContainerColor),
            hintColor = mColorProvider.getColor(R.color.darkGreenLabeledEditTextHintColor),
            textColor = mColorProvider.getColor(R.color.darkGreenLabeledEditTextTextColor),
            cursorColor = mColorProvider.getColor(R.color.darkGreenLabeledEditTextCursorColor),
            labelColor = mColorProvider.getColor(R.color.darkGreenLabeledEditTextLabelColor)
        )
    }


    override fun getPitchBlackTheme(): LabeledEditTextTheme {
        return LabeledEditTextTheme(
            titleColor = mColorProvider.getColor(R.color.pitchBlackLabeledEditTextTitleColor),
            containerColor = mColorProvider.getColor(R.color.pitchBlackLabeledEditTextContainerColor),
            hintColor = mColorProvider.getColor(R.color.pitchBlackLabeledEditTextHintColor),
            textColor = mColorProvider.getColor(R.color.pitchBlackLabeledEditTextTextColor),
            cursorColor = mColorProvider.getColor(R.color.pitchBlackLabeledEditTextCursorColor),
            labelColor = mColorProvider.getColor(R.color.pitchBlackLabeledEditTextLabelColor)
        )
    }


    override fun getGrayishBlueTheme(): LabeledEditTextTheme {
        return LabeledEditTextTheme(
            titleColor = mColorProvider.getColor(R.color.grayishBlueLabeledEditTextTitleColor),
            containerColor = mColorProvider.getColor(R.color.grayishBlueLabeledEditTextContainerColor),
            hintColor = mColorProvider.getColor(R.color.grayishBlueLabeledEditTextHintColor),
            textColor = mColorProvider.getColor(R.color.grayishBlueLabeledEditTextTextColor),
            cursorColor = mColorProvider.getColor(R.color.grayishBlueLabeledEditTextCursorColor),
            labelColor = mColorProvider.getColor(R.color.grayishBlueLabeledEditTextLabelColor)
        )
    }


    override fun getBrightGrayTheme(): LabeledEditTextTheme {
        return LabeledEditTextTheme(
            titleColor = mColorProvider.getColor(R.color.brightGrayLabeledEditTextTitleColor),
            containerColor = mColorProvider.getColor(R.color.brightGrayLabeledEditTextContainerColor),
            hintColor = mColorProvider.getColor(R.color.brightGrayLabeledEditTextHintColor),
            textColor = mColorProvider.getColor(R.color.brightGrayLabeledEditTextTextColor),
            cursorColor = mColorProvider.getColor(R.color.brightGrayLabeledEditTextCursorColor),
            labelColor = mColorProvider.getColor(R.color.brightGrayLabeledEditTextLabelColor)
        )
    }


    override fun getDarkSilverTheme(): LabeledEditTextTheme {
        return LabeledEditTextTheme(
            titleColor = mColorProvider.getColor(R.color.darkSilverLabeledEditTextTitleColor),
            containerColor = mColorProvider.getColor(R.color.darkSilverLabeledEditTextContainerColor),
            hintColor = mColorProvider.getColor(R.color.darkSilverLabeledEditTextHintColor),
            textColor = mColorProvider.getColor(R.color.darkSilverLabeledEditTextTextColor),
            cursorColor = mColorProvider.getColor(R.color.darkSilverLabeledEditTextCursorColor),
            labelColor = mColorProvider.getColor(R.color.darkSilverLabeledEditTextLabelColor)
        )
    }


    override fun getBrowneeTheme(): LabeledEditTextTheme {
        return LabeledEditTextTheme(
            titleColor = mColorProvider.getColor(R.color.browneeLabeledEditTextTitleColor),
            containerColor = mColorProvider.getColor(R.color.browneeLabeledEditTextContainerColor),
            hintColor = mColorProvider.getColor(R.color.browneeLabeledEditTextHintColor),
            textColor = mColorProvider.getColor(R.color.browneeLabeledEditTextTextColor),
            cursorColor = mColorProvider.getColor(R.color.browneeLabeledEditTextCursorColor),
            labelColor = mColorProvider.getColor(R.color.browneeLabeledEditTextLabelColor)
        )
    }


    override fun getVioletTheme(): LabeledEditTextTheme {
        return LabeledEditTextTheme(
            titleColor = mColorProvider.getColor(R.color.violetLabeledEditTextTitleColor),
            containerColor = mColorProvider.getColor(R.color.violetLabeledEditTextContainerColor),
            hintColor = mColorProvider.getColor(R.color.violetLabeledEditTextHintColor),
            textColor = mColorProvider.getColor(R.color.violetLabeledEditTextTextColor),
            cursorColor = mColorProvider.getColor(R.color.violetLabeledEditTextCursorColor),
            labelColor = mColorProvider.getColor(R.color.violetLabeledEditTextLabelColor)
        )
    }


    override fun getPurpleTheme(): LabeledEditTextTheme {
        return LabeledEditTextTheme(
            titleColor = mColorProvider.getColor(R.color.purpleLabeledEditTextTitleColor),
            containerColor = mColorProvider.getColor(R.color.purpleLabeledEditTextContainerColor),
            hintColor = mColorProvider.getColor(R.color.purpleLabeledEditTextHintColor),
            textColor = mColorProvider.getColor(R.color.purpleLabeledEditTextTextColor),
            cursorColor = mColorProvider.getColor(R.color.purpleLabeledEditTextCursorColor),
            labelColor = mColorProvider.getColor(R.color.purpleLabeledEditTextLabelColor)
        )
    }


    override fun getBlueZodiacTheme(): LabeledEditTextTheme {
        return LabeledEditTextTheme(
            titleColor = mColorProvider.getColor(R.color.blueZodiacLabeledEditTextTitleColor),
            containerColor = mColorProvider.getColor(R.color.blueZodiacLabeledEditTextContainerColor),
            hintColor = mColorProvider.getColor(R.color.blueZodiacLabeledEditTextHintColor),
            textColor = mColorProvider.getColor(R.color.blueZodiacLabeledEditTextTextColor),
            cursorColor = mColorProvider.getColor(R.color.blueZodiacLabeledEditTextCursorColor),
            labelColor = mColorProvider.getColor(R.color.blueZodiacLabeledEditTextLabelColor)
        )
    }


    override fun getBubbleBlueTheme(): LabeledEditTextTheme {
        return LabeledEditTextTheme(
            titleColor = mColorProvider.getColor(R.color.bubbleBlueLabeledEditTextTitleColor),
            containerColor = mColorProvider.getColor(R.color.bubbleBlueLabeledEditTextContainerColor),
            hintColor = mColorProvider.getColor(R.color.bubbleBlueLabeledEditTextHintColor),
            textColor = mColorProvider.getColor(R.color.bubbleBlueLabeledEditTextTextColor),
            cursorColor = mColorProvider.getColor(R.color.bubbleBlueLabeledEditTextCursorColor),
            labelColor = mColorProvider.getColor(R.color.bubbleBlueLabeledEditTextLabelColor)
        )
    }


}
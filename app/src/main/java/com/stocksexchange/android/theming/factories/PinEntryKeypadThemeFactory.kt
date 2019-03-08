package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.PinEntryKeypadTheme

/**
 * A factory producing instances of the [PinEntryKeypadTheme] class.
 */
object PinEntryKeypadThemeFactory : BaseThemeFactory<PinEntryKeypadTheme>() {


    override fun getStubTheme(): PinEntryKeypadTheme {
        return PinEntryKeypadTheme(
            digitButtonTextColor = -1,
            enabledButtonBackgroundColor = -1,
            disabledButtonBackgroundColor = -1,
            enabledFingerprintButtonForegroundColor = -1,
            enabledDeleteButtonForegroundColor = -1,
            disabledActionButtonForegroundColor = -1
        )
    }


    override fun getDeepTealTheme(): PinEntryKeypadTheme {
        return PinEntryKeypadTheme(
            digitButtonTextColor = mColorProvider.getColor(R.color.deepTealPinEntryKeypadDigitButtonTextColor),
            enabledButtonBackgroundColor = mColorProvider.getColor(R.color.deepTealPinEntryKeypadEnabledButtonBackgroundColor),
            disabledButtonBackgroundColor = mColorProvider.getColor(R.color.deepTealPinEntryKeypadDisabledButtonBackgroundColor),
            enabledFingerprintButtonForegroundColor = mColorProvider.getColor(R.color.deepTealPinEntryKeypadEnabledFingerprintButtonForegroundColor),
            enabledDeleteButtonForegroundColor = mColorProvider.getColor(R.color.deepTealPinEntryKeypadEnabledDeleteButtonForegroundColor),
            disabledActionButtonForegroundColor = mColorProvider.getColor(R.color.deepTealPinEntryKeypadDisabledActionButtonForegroundColor)
        )
    }


    override fun getNightBlueTheme(): PinEntryKeypadTheme {
        return PinEntryKeypadTheme(
            digitButtonTextColor = mColorProvider.getColor(R.color.nightBluePinEntryKeypadDigitButtonTextColor),
            enabledButtonBackgroundColor = mColorProvider.getColor(R.color.nightBluePinEntryKeypadEnabledButtonBackgroundColor),
            disabledButtonBackgroundColor = mColorProvider.getColor(R.color.nightBluePinEntryKeypadDisabledButtonBackgroundColor),
            enabledFingerprintButtonForegroundColor = mColorProvider.getColor(R.color.nightBluePinEntryKeypadEnabledFingerprintButtonForegroundColor),
            enabledDeleteButtonForegroundColor = mColorProvider.getColor(R.color.nightBluePinEntryKeypadEnabledDeleteButtonForegroundColor),
            disabledActionButtonForegroundColor = mColorProvider.getColor(R.color.nightBluePinEntryKeypadDisabledActionButtonForegroundColor)
        )
    }


    override fun getDarkGreenTheme(): PinEntryKeypadTheme {
        return PinEntryKeypadTheme(
            digitButtonTextColor = mColorProvider.getColor(R.color.darkGreenPinEntryKeypadDigitButtonTextColor),
            enabledButtonBackgroundColor = mColorProvider.getColor(R.color.darkGreenPinEntryKeypadEnabledButtonBackgroundColor),
            disabledButtonBackgroundColor = mColorProvider.getColor(R.color.darkGreenPinEntryKeypadDisabledButtonBackgroundColor),
            enabledFingerprintButtonForegroundColor = mColorProvider.getColor(R.color.darkGreenPinEntryKeypadEnabledFingerprintButtonForegroundColor),
            enabledDeleteButtonForegroundColor = mColorProvider.getColor(R.color.darkGreenPinEntryKeypadEnabledDeleteButtonForegroundColor),
            disabledActionButtonForegroundColor = mColorProvider.getColor(R.color.darkGreenPinEntryKeypadDisabledActionButtonForegroundColor)
        )
    }


    override fun getPitchBlackTheme(): PinEntryKeypadTheme {
        return PinEntryKeypadTheme(
            digitButtonTextColor = mColorProvider.getColor(R.color.pitchBlackPinEntryKeypadDigitButtonTextColor),
            enabledButtonBackgroundColor = mColorProvider.getColor(R.color.pitchBlackPinEntryKeypadEnabledButtonBackgroundColor),
            disabledButtonBackgroundColor = mColorProvider.getColor(R.color.pitchBlackPinEntryKeypadDisabledButtonBackgroundColor),
            enabledFingerprintButtonForegroundColor = mColorProvider.getColor(R.color.pitchBlackPinEntryKeypadEnabledFingerprintButtonForegroundColor),
            enabledDeleteButtonForegroundColor = mColorProvider.getColor(R.color.pitchBlackPinEntryKeypadEnabledDeleteButtonForegroundColor),
            disabledActionButtonForegroundColor = mColorProvider.getColor(R.color.pitchBlackPinEntryKeypadDisabledActionButtonForegroundColor)
        )
    }


    override fun getGrayishBlueTheme(): PinEntryKeypadTheme {
        return PinEntryKeypadTheme(
            digitButtonTextColor = mColorProvider.getColor(R.color.grayishBluePinEntryKeypadDigitButtonTextColor),
            enabledButtonBackgroundColor = mColorProvider.getColor(R.color.grayishBluePinEntryKeypadEnabledButtonBackgroundColor),
            disabledButtonBackgroundColor = mColorProvider.getColor(R.color.grayishBluePinEntryKeypadDisabledButtonBackgroundColor),
            enabledFingerprintButtonForegroundColor = mColorProvider.getColor(R.color.grayishBluePinEntryKeypadEnabledFingerprintButtonForegroundColor),
            enabledDeleteButtonForegroundColor = mColorProvider.getColor(R.color.grayishBluePinEntryKeypadEnabledDeleteButtonForegroundColor),
            disabledActionButtonForegroundColor = mColorProvider.getColor(R.color.grayishBluePinEntryKeypadDisabledActionButtonForegroundColor)
        )
    }


    override fun getBrightGrayTheme(): PinEntryKeypadTheme {
        return PinEntryKeypadTheme(
            digitButtonTextColor = mColorProvider.getColor(R.color.brightGrayPinEntryKeypadDigitButtonTextColor),
            enabledButtonBackgroundColor = mColorProvider.getColor(R.color.brightGrayPinEntryKeypadEnabledButtonBackgroundColor),
            disabledButtonBackgroundColor = mColorProvider.getColor(R.color.brightGrayPinEntryKeypadDisabledButtonBackgroundColor),
            enabledFingerprintButtonForegroundColor = mColorProvider.getColor(R.color.brightGrayPinEntryKeypadEnabledFingerprintButtonForegroundColor),
            enabledDeleteButtonForegroundColor = mColorProvider.getColor(R.color.brightGrayPinEntryKeypadEnabledDeleteButtonForegroundColor),
            disabledActionButtonForegroundColor = mColorProvider.getColor(R.color.brightGrayPinEntryKeypadDisabledActionButtonForegroundColor)
        )
    }


    override fun getDarkSilverTheme(): PinEntryKeypadTheme {
        return PinEntryKeypadTheme(
            digitButtonTextColor = mColorProvider.getColor(R.color.darkSilverPinEntryKeypadDigitButtonTextColor),
            enabledButtonBackgroundColor = mColorProvider.getColor(R.color.darkSilverPinEntryKeypadEnabledButtonBackgroundColor),
            disabledButtonBackgroundColor = mColorProvider.getColor(R.color.darkSilverPinEntryKeypadDisabledButtonBackgroundColor),
            enabledFingerprintButtonForegroundColor = mColorProvider.getColor(R.color.darkSilverPinEntryKeypadEnabledFingerprintButtonForegroundColor),
            enabledDeleteButtonForegroundColor = mColorProvider.getColor(R.color.darkSilverPinEntryKeypadEnabledDeleteButtonForegroundColor),
            disabledActionButtonForegroundColor = mColorProvider.getColor(R.color.darkSilverPinEntryKeypadDisabledActionButtonForegroundColor)
        )
    }


    override fun getBrowneeTheme(): PinEntryKeypadTheme {
        return PinEntryKeypadTheme(
            digitButtonTextColor = mColorProvider.getColor(R.color.browneePinEntryKeypadDigitButtonTextColor),
            enabledButtonBackgroundColor = mColorProvider.getColor(R.color.browneePinEntryKeypadEnabledButtonBackgroundColor),
            disabledButtonBackgroundColor = mColorProvider.getColor(R.color.browneePinEntryKeypadDisabledButtonBackgroundColor),
            enabledFingerprintButtonForegroundColor = mColorProvider.getColor(R.color.browneePinEntryKeypadEnabledFingerprintButtonForegroundColor),
            enabledDeleteButtonForegroundColor = mColorProvider.getColor(R.color.browneePinEntryKeypadEnabledDeleteButtonForegroundColor),
            disabledActionButtonForegroundColor = mColorProvider.getColor(R.color.browneePinEntryKeypadDisabledActionButtonForegroundColor)
        )
    }


    override fun getVioletTheme(): PinEntryKeypadTheme {
        return PinEntryKeypadTheme(
            digitButtonTextColor = mColorProvider.getColor(R.color.violetPinEntryKeypadDigitButtonTextColor),
            enabledButtonBackgroundColor = mColorProvider.getColor(R.color.violetPinEntryKeypadEnabledButtonBackgroundColor),
            disabledButtonBackgroundColor = mColorProvider.getColor(R.color.violetPinEntryKeypadDisabledButtonBackgroundColor),
            enabledFingerprintButtonForegroundColor = mColorProvider.getColor(R.color.violetPinEntryKeypadEnabledFingerprintButtonForegroundColor),
            enabledDeleteButtonForegroundColor = mColorProvider.getColor(R.color.violetPinEntryKeypadEnabledDeleteButtonForegroundColor),
            disabledActionButtonForegroundColor = mColorProvider.getColor(R.color.violetPinEntryKeypadDisabledActionButtonForegroundColor)
        )
    }


    override fun getPurpleTheme(): PinEntryKeypadTheme {
        return PinEntryKeypadTheme(
            digitButtonTextColor = mColorProvider.getColor(R.color.purplePinEntryKeypadDigitButtonTextColor),
            enabledButtonBackgroundColor = mColorProvider.getColor(R.color.purplePinEntryKeypadEnabledButtonBackgroundColor),
            disabledButtonBackgroundColor = mColorProvider.getColor(R.color.purplePinEntryKeypadDisabledButtonBackgroundColor),
            enabledFingerprintButtonForegroundColor = mColorProvider.getColor(R.color.purplePinEntryKeypadEnabledFingerprintButtonForegroundColor),
            enabledDeleteButtonForegroundColor = mColorProvider.getColor(R.color.purplePinEntryKeypadEnabledDeleteButtonForegroundColor),
            disabledActionButtonForegroundColor = mColorProvider.getColor(R.color.purplePinEntryKeypadDisabledActionButtonForegroundColor)
        )
    }


    override fun getBlueZodiacTheme(): PinEntryKeypadTheme {
        return PinEntryKeypadTheme(
            digitButtonTextColor = mColorProvider.getColor(R.color.blueZodiacPinEntryKeypadDigitButtonTextColor),
            enabledButtonBackgroundColor = mColorProvider.getColor(R.color.blueZodiacPinEntryKeypadEnabledButtonBackgroundColor),
            disabledButtonBackgroundColor = mColorProvider.getColor(R.color.blueZodiacPinEntryKeypadDisabledButtonBackgroundColor),
            enabledFingerprintButtonForegroundColor = mColorProvider.getColor(R.color.blueZodiacPinEntryKeypadEnabledFingerprintButtonForegroundColor),
            enabledDeleteButtonForegroundColor = mColorProvider.getColor(R.color.blueZodiacPinEntryKeypadEnabledDeleteButtonForegroundColor),
            disabledActionButtonForegroundColor = mColorProvider.getColor(R.color.blueZodiacPinEntryKeypadDisabledActionButtonForegroundColor)
        )
    }


    override fun getBubbleBlueTheme(): PinEntryKeypadTheme {
        return PinEntryKeypadTheme(
            digitButtonTextColor = mColorProvider.getColor(R.color.bubbleBluePinEntryKeypadDigitButtonTextColor),
            enabledButtonBackgroundColor = mColorProvider.getColor(R.color.bubbleBluePinEntryKeypadEnabledButtonBackgroundColor),
            disabledButtonBackgroundColor = mColorProvider.getColor(R.color.bubbleBluePinEntryKeypadDisabledButtonBackgroundColor),
            enabledFingerprintButtonForegroundColor = mColorProvider.getColor(R.color.bubbleBluePinEntryKeypadEnabledFingerprintButtonForegroundColor),
            enabledDeleteButtonForegroundColor = mColorProvider.getColor(R.color.bubbleBluePinEntryKeypadEnabledDeleteButtonForegroundColor),
            disabledActionButtonForegroundColor = mColorProvider.getColor(R.color.bubbleBluePinEntryKeypadDisabledActionButtonForegroundColor)
        )
    }


}
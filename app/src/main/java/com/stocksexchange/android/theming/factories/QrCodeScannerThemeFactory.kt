package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.R
import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.QrCodeScannerTheme

/**
 * A factory producing instances of the [QrCodeScannerTheme] class.
 */
object QrCodeScannerThemeFactory : BaseThemeFactory<QrCodeScannerTheme>() {


    override fun getStubTheme(): QrCodeScannerTheme{
        return QrCodeScannerTheme(
            borderColor = -1,
            tintColor = -1
        )
    }


    override fun getDeepTealTheme(): QrCodeScannerTheme {
        return QrCodeScannerTheme(
            borderColor = mColorProvider.getColor(R.color.deepTealQrCodeScannerBorderColor),
            tintColor = mColorProvider.getColor(R.color.deepTealQrCodeScannerTintColor)
        )
    }


    override fun getNightBlueTheme(): QrCodeScannerTheme {
        return QrCodeScannerTheme(
            borderColor = mColorProvider.getColor(R.color.nightBlueQrCodeScannerBorderColor),
            tintColor = mColorProvider.getColor(R.color.nightBlueQrCodeScannerTintColor)
        )
    }


    override fun getDarkGreenTheme(): QrCodeScannerTheme {
        return QrCodeScannerTheme(
            borderColor = mColorProvider.getColor(R.color.darkGreenQrCodeScannerBorderColor),
            tintColor = mColorProvider.getColor(R.color.darkGreenQrCodeScannerTintColor)
        )
    }


    override fun getPitchBlackTheme(): QrCodeScannerTheme {
        return QrCodeScannerTheme(
            borderColor = mColorProvider.getColor(R.color.pitchBlackQrCodeScannerBorderColor),
            tintColor = mColorProvider.getColor(R.color.pitchBlackQrCodeScannerTintColor)
        )
    }


    override fun getGrayishBlueTheme(): QrCodeScannerTheme {
        return QrCodeScannerTheme(
            borderColor = mColorProvider.getColor(R.color.grayishBlueQrCodeScannerBorderColor),
            tintColor = mColorProvider.getColor(R.color.grayishBlueQrCodeScannerTintColor)
        )
    }


    override fun getBrightGrayTheme(): QrCodeScannerTheme {
        return QrCodeScannerTheme(
            borderColor = mColorProvider.getColor(R.color.brightGrayQrCodeScannerBorderColor),
            tintColor = mColorProvider.getColor(R.color.brightGrayQrCodeScannerTintColor)
        )
    }


    override fun getDarkSilverTheme(): QrCodeScannerTheme {
        return QrCodeScannerTheme(
            borderColor = mColorProvider.getColor(R.color.darkSilverQrCodeScannerBorderColor),
            tintColor = mColorProvider.getColor(R.color.darkSilverQrCodeScannerTintColor)
        )
    }


    override fun getBrowneeTheme(): QrCodeScannerTheme {
        return QrCodeScannerTheme(
            borderColor = mColorProvider.getColor(R.color.browneeQrCodeScannerBorderColor),
            tintColor = mColorProvider.getColor(R.color.browneeQrCodeScannerTintColor)
        )
    }


    override fun getVioletTheme(): QrCodeScannerTheme {
        return QrCodeScannerTheme(
            borderColor = mColorProvider.getColor(R.color.violetQrCodeScannerBorderColor),
            tintColor = mColorProvider.getColor(R.color.violetQrCodeScannerTintColor)
        )
    }


    override fun getPurpleTheme(): QrCodeScannerTheme {
        return QrCodeScannerTheme(
            borderColor = mColorProvider.getColor(R.color.purpleQrCodeScannerBorderColor),
            tintColor = mColorProvider.getColor(R.color.purpleQrCodeScannerTintColor)
        )
    }


    override fun getBlueZodiacTheme(): QrCodeScannerTheme {
        return QrCodeScannerTheme(
            borderColor = mColorProvider.getColor(R.color.blueZodiacQrCodeScannerBorderColor),
            tintColor = mColorProvider.getColor(R.color.blueZodiacQrCodeScannerTintColor)
        )
    }


    override fun getBubbleBlueTheme(): QrCodeScannerTheme {
        return QrCodeScannerTheme(
            borderColor = mColorProvider.getColor(R.color.bubbleBlueQrCodeScannerBorderColor),
            tintColor = mColorProvider.getColor(R.color.bubbleBlueQrCodeScannerTintColor)
        )
    }


}
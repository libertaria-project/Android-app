package com.stocksexchange.android.theming.factories.base

import com.stocksexchange.android.factories.base.BaseFactory
import com.stocksexchange.android.utils.providers.ColorProvider
import org.koin.standalone.inject

/**
 * A base theme factory to extend from.
 */
abstract class BaseThemeFactory<Theme> : BaseFactory() {


    /**
     * A color provider used for fetching colors.
     */
    protected val mColorProvider: ColorProvider by inject()




    /**
     * Should return a stub theme.
     */
    abstract fun getStubTheme(): Theme


    /**
     * Should return a deep teal theme.
     */
    abstract fun getDeepTealTheme(): Theme


    /**
     * Should return a a night blue theme.
     */
    abstract fun getNightBlueTheme(): Theme


    /**
     * Should return a dark green theme.
     */
    abstract fun getDarkGreenTheme(): Theme


    /**
     * Should return a pitch black theme.
     */
    abstract fun getPitchBlackTheme(): Theme


    /**
     * Should return a grayish blue theme.
     */
    abstract fun getGrayishBlueTheme(): Theme


    /**
     * Should return a bright gray theme.
     */
    abstract fun getBrightGrayTheme(): Theme


    /**
     * Should return a dark silver theme.
     */
    abstract fun getDarkSilverTheme(): Theme


    /**
     * Should return a brownee theme.
     */
    abstract fun getBrowneeTheme(): Theme


    /**
     * Should return a violet theme.
     */
    abstract fun getVioletTheme(): Theme


    /**
     * Should return a purple theme.
     */
    abstract fun getPurpleTheme(): Theme


    /**
     * Should return a blue zodiac theme.
     */
    abstract fun getBlueZodiacTheme(): Theme


    /**
     * Should return a bubble blue theme.
     */
    abstract fun getBubbleBlueTheme(): Theme


}
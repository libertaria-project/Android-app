package com.stocksexchange.android.ui.themes

import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.model.ThemeData
import com.stocksexchange.android.theming.factories.ThemeFactory
import com.stocksexchange.android.ui.base.mvp.model.BaseModel
import org.koin.standalone.inject

class ThemesModel : BaseModel() {


    private val mThemeFactory: ThemeFactory by inject()




    fun getThemesData(settings: Settings): MutableList<ThemeData> {
        val themesData: MutableList<ThemeData> = mutableListOf()

        with(themesData) {
            add(ThemeData(mThemeFactory.getDeepTealTheme()))
            add(ThemeData(mThemeFactory.getNightBlueTheme()))
            add(ThemeData(mThemeFactory.getDarkGreenTheme()))
            add(ThemeData(mThemeFactory.getPitchBlackTheme()))
            add(ThemeData(mThemeFactory.getGrayishBlueTheme()))
            add(ThemeData(mThemeFactory.getBrightGrayTheme()))
            add(ThemeData(mThemeFactory.getDarkSilverTheme()))
            add(ThemeData(mThemeFactory.getBrowneeTheme()))
            add(ThemeData(mThemeFactory.getVioletTheme()))
            add(ThemeData(mThemeFactory.getPurpleTheme()))
            add(ThemeData(mThemeFactory.getBlueZodiacTheme()))
            add(ThemeData(mThemeFactory.getBubbleBlueTheme()))
            //add(ThemeData(mThemeFactory.getIndigoTheme()))
        }

        for(themeData in themesData) {
            if(themeData.theme.id == settings.theme.id) {
                themeData.isSelected = true
                break
            }
        }

        return themesData
    }


}
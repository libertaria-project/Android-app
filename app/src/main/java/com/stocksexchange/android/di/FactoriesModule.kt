package com.stocksexchange.android.di

import com.stocksexchange.android.factories.SettingsFactory
import com.stocksexchange.android.theming.factories.ThemeFactory
import org.koin.dsl.module.applicationContext

val factoriesModule = applicationContext {

    bean { SettingsFactory }
    bean { ThemeFactory }

}
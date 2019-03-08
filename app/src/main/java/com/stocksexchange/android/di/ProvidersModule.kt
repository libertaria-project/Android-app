package com.stocksexchange.android.di

import com.stocksexchange.android.utils.providers.*
import org.koin.dsl.module.applicationContext

val providersModule = applicationContext {

    bean { StringProvider(get()) }
    bean { ConnectionProvider(get()) }
    bean { CustomTabsProvider(get()) }
    bean { InfoViewProvider(get()) }
    bean { RingtoneProvider(get()) }
    bean { BooleanProvider(get()) }
    bean { ColorProvider(get()) }
    bean { FingerprintProvider(get()) }
    bean { DimensionProvider(get()) }

}
package com.stocksexchange.android.di

import com.stocksexchange.android.utils.handlers.*
import org.koin.dsl.module.applicationContext

val handlersModule = applicationContext {

    bean { BrowserHandler(get()) }
    bean { ClipboardHandler(get()) }
    bean { CredentialsHandler(get()) }
    bean { EmailHandler() }
    bean { PreferenceHandler(get()) }
    bean { QrCodeHandler() }
    bean { UserDataClearingHandler(get(), get(), get(), get(), get()) }
    bean { SharingHandler() }
    bean { ShortcutsHandler(get()) }

    factory { CoroutineHandler() }

}
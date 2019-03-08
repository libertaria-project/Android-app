package com.stocksexchange.android.services

import android.app.IntentService
import org.koin.standalone.KoinComponent

/**
 * A base service class with dependency injection support.
 */
abstract class BaseService(name: String) : IntentService(name), KoinComponent
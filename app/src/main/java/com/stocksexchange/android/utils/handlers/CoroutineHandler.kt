package com.stocksexchange.android.utils.handlers

import com.stocksexchange.android.utils.helpers.createBgLaunchCoroutine
import com.stocksexchange.android.utils.helpers.createUiLaunchCoroutine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

/**
 * A handler used for providing coroutines functionality.
 */
class CoroutineHandler {


    private val mScope = CoroutineScope(SupervisorJob())




    /**
     * Creates a launch coroutine on the main thread and immediately starts it.
     *
     * @param block The block of code to run inside the coroutine
     *
     * @return The coroutine job
     */
    fun createUiLaunchCoroutine(block: suspend (() -> Unit)) : Job {
        return mScope.createUiLaunchCoroutine { block() }
    }


    /**
     * Creates a launch coroutine on the background thread and immediately starts it.
     *
     * @param block The block of code to run inside the coroutine
     *
     * @return The coroutine job
     */
    fun createBgLaunchCoroutine(block: suspend (() -> Unit)) : Job {
        return mScope.createBgLaunchCoroutine { block() }
    }


    /**
     * Cancels all currently running children coroutines.
     */
    fun cancelChildren() {
        mScope.coroutineContext.cancelChildren()
    }


}
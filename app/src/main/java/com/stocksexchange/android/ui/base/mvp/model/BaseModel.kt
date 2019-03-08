package com.stocksexchange.android.ui.base.mvp.model

import androidx.annotation.CallSuper
import com.stocksexchange.android.utils.SavedState
import com.stocksexchange.android.utils.handlers.CoroutineHandler
import kotlinx.coroutines.Job
import org.koin.standalone.inject

/**
 * A base model class to build model classes on.
 */
abstract class BaseModel : Model {


    /**
     * A coroutine handler to provide coroutines functionality.
     */
    protected val mCoroutineHandler: CoroutineHandler by inject()




    @CallSuper
    override fun start() {
        // Stub
    }


    @CallSuper
    override fun stop() {
        mCoroutineHandler.cancelChildren()
    }


    /**
     * Runs [block] of code inside coroutine on the main thread.
     *
     * @param block The block of code to run
     */
    protected fun createUiLaunchCoroutine(block: suspend (() -> Unit)): Job {
        return mCoroutineHandler.createUiLaunchCoroutine(block)
    }


    /**
     * Runs [block] of code inside coroutine on the background thread.
     *
     * @param block The block of code to run
     */
    protected fun createBgLaunchCoroutine(block: suspend (() -> Unit)): Job {
        return mCoroutineHandler.createBgLaunchCoroutine(block)
    }


    override fun onRestoreState(savedState: SavedState) {
        // Stub
    }


    override fun onSaveState(savedState: SavedState) {
        // Stub
    }


}
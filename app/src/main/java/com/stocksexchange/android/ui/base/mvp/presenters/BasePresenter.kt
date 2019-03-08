package com.stocksexchange.android.ui.base.mvp.presenters

import androidx.annotation.CallSuper
import com.stocksexchange.android.ui.base.mvp.model.Model
import com.stocksexchange.android.utils.SavedState
import org.greenrobot.eventbus.EventBus
import org.koin.standalone.KoinComponent

/**
 * A base presenter to build presenters on.
 */
abstract class BasePresenter<out M, out V>(
    protected val mModel: M,
    protected val mView: V
) : Presenter, KoinComponent where
        M : Model,
        V : Any {


    override fun start() {
        mModel.start()

        if(canReceiveEvents()) {
            EventBus.getDefault().register(this)
        }
    }


    override fun stop() {
        mModel.stop()

        if(canReceiveEvents()) {
            EventBus.getDefault().unregister(this)
        }
    }


    /**
     * Should return whether the presenter can
     * can receive [EventBus] events or not.
     */
    protected open fun canReceiveEvents(): Boolean {
        return false
    }


    @CallSuper
    override fun onRestoreState(savedState: SavedState) {
        mModel.onRestoreState(savedState)
    }


    @CallSuper
    override fun onSaveState(savedState: SavedState) {
        mModel.onSaveState(savedState)
    }


    override fun toString(): String {
        return "${javaClass.simpleName}_${mModel.javaClass.simpleName}_${mView.javaClass.simpleName}"
    }


}
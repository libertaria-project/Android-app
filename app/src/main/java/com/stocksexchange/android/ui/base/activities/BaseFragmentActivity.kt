package com.stocksexchange.android.ui.base.activities

import androidx.annotation.CallSuper
import com.stocksexchange.android.R
import com.stocksexchange.android.ui.base.fragments.BaseFragment
import com.stocksexchange.android.ui.base.mvp.presenters.Presenter
import com.stocksexchange.android.ui.utils.interfaces.CanObserveNetworkStateChanges

/**
 * A base activity to extend from if the activity hosts the fragment.
 */
abstract class BaseFragmentActivity<F : BaseFragment<*>, P : Presenter> : BaseActivity<P>() {


    /**
     * A fragment that this activity holds.
     */
    protected lateinit var mFragment: F




    @CallSuper
    override fun init() {
        super.init()

        initFragment()
    }


    /**
     * Initializes and puts a fragment inside the fragment manager.
     */
    @Suppress("UNCHECKED_CAST")
    protected open fun initFragment() {
        val foundFragment = supportFragmentManager.findFragmentById(R.id.fragmentHolderFl)

        mFragment = if(foundFragment == null) {
            getActivityFragment()
        } else {
            (foundFragment as F)
        }

        mFragment.onSelected()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentHolderFl, mFragment)
            .commit()
    }


    /**
     * Should return a fragment that this activity will hold.
     */
    protected abstract fun getActivityFragment(): F


    override fun onConnected() {
        if(mFragment is CanObserveNetworkStateChanges) {
            (mFragment as CanObserveNetworkStateChanges).onNetworkConnected()
        }
    }


    override fun onDisconnected() {
        if(mFragment is CanObserveNetworkStateChanges) {
            (mFragment as CanObserveNetworkStateChanges).onNetworkDisconnected()
        }
    }


    override fun onBackPressed() {
        mFragment.onBackPressed()

        super.onBackPressed()
    }


}
package com.stocksexchange.android.ui.orders

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import com.stocksexchange.android.Constants
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.OrderTypes
import com.stocksexchange.android.model.OrderTabs
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.ui.base.activities.BaseViewPagerActivity
import com.stocksexchange.android.ui.orders.fragment.OrdersFragment
import com.stocksexchange.android.ui.orders.search.OrdersSearchActivity
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.extensions.overrideEnterTransition
import com.stocksexchange.android.ui.views.Toolbar
import com.stocksexchange.android.utils.helpers.loginIfNecessary
import kotlinx.android.synthetic.main.orders_activity_layout.*
import org.jetbrains.anko.intentFor
import org.koin.android.ext.android.get

class OrdersActivity : BaseViewPagerActivity<OrdersViewPagerAdapter, OrdersActivityPresenter>(),
    OrdersActivityContract.View {


    companion object {

        const val TAG = "OrdersActivity"

        private const val EXTRA_TRANSITION_ANIMATIONS = "transition_animations"

        private const val SAVED_STATE_TRANSITION_ANIMATIONS_NAME = "transition_animations_name"


        fun newInstance(context: Context, transitionAnimations: TransitionAnimations): Intent {
            return context.intentFor<OrdersActivity>(
                EXTRA_TRANSITION_ANIMATIONS to transitionAnimations
            )
        }

    }


    private lateinit var mTransitionAnimations: TransitionAnimations




    override fun preViewInflation(): Boolean {
        return loginIfNecessary(
            this,
            newInstance(this, TransitionAnimations.KITKAT_SCALING_ANIMATIONS),
            get()
        )
    }


    override fun initPresenter(): OrdersActivityPresenter = OrdersActivityPresenter(this)


    override fun initToolbar() {
        super.initToolbar()

        toolbar.setOnRightButtonClickListener {
            mPresenter?.onActionButtonClicked()
        }
    }


    override fun postInit() {
        super.postInit()

        overrideEnterTransition(mTransitionAnimations)
    }


    override fun populateAdapter() {
        with(mAdapter) {
            val fragment = getFragment(OrderTabs.ACTIVE.ordinal)

            val activeOrdersFragment = if(fragment == null) {
                OrdersFragment.newActiveInstance()
            } else {
                (fragment as OrdersFragment)
            }

            activeOrdersFragment.progressBarListener = progressBarListener

            addFragment(activeOrdersFragment)
            addFragment(getFragment(OrderTabs.COMPLETED.ordinal) ?: OrdersFragment.newCompletedInstance())
            addFragment(getFragment(OrderTabs.CANCELLED.ordinal) ?: OrdersFragment.newCancelledInstance())
        }
    }


    override fun initTabLayoutTabs() {
        with(mTabAnimator) {
            getTabAt(OrderTabs.ACTIVE.ordinal)?.setTitleText(getString(R.string.orders_activity_tab_active_orders_title))
            getTabAt(OrderTabs.COMPLETED.ordinal)?.setTitleText(getString(R.string.orders_activity_tab_completed_orders_title))
            getTabAt(OrderTabs.CANCELLED.ordinal)?.setTitleText(getString(R.string.orders_activity_tab_cancelled_orders_title))

            applyTheme(getAppTheme())
        }
    }


    override fun launchSearchActivity() {
        startActivity(OrdersSearchActivity.newInstance(
            when(tabLayout.selectedTabPosition) {
                OrderTabs.ACTIVE.ordinal -> OrderTypes.ACTIVE
                OrderTabs.COMPLETED.ordinal -> OrderTypes.COMPLETED
                else -> OrderTypes.CANCELLED
            },
            this
        ))
    }


    override fun getToolbarTitle(): String = getString(R.string.orders)


    override fun getToolbar(): Toolbar? = toolbar


    override fun getViewPager(): ViewPager = viewPager


    override fun getViewPagerAdapter(): OrdersViewPagerAdapter {
        return OrdersViewPagerAdapter(supportFragmentManager)
    }


    override fun getTabLayout(): TabLayout = tabLayout


    override fun getContentLayoutResourceId(): Int = R.layout.orders_activity_layout


    override fun getEnterTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.DEFAULT_ANIMATIONS
    }


    override fun getExitTransitionAnimations(): TransitionAnimations {
        return mTransitionAnimations
    }


    private val progressBarListener = object : OrdersFragment.ProgressBarListener {

        override fun showProgressBar() {
            toolbar.hideRightButton()
            toolbar.showProgressBar()
        }

        override fun hideProgressBar() {
            toolbar.hideProgressBar()
            toolbar.showRightButton()
        }

    }


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            with(savedState) {
                mTransitionAnimations = getString(SAVED_STATE_TRANSITION_ANIMATIONS_NAME)
                    ?.let { TransitionAnimations.valueOf(it) }
                    ?: TransitionAnimations.HORIZONTAL_SLIDING_ANIMATIONS
            }
        } else {
            with(intent) {
                mTransitionAnimations = (getSerializableExtra(EXTRA_TRANSITION_ANIMATIONS) as TransitionAnimations)
            }
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        savedState.putSerializable(SAVED_STATE_TRANSITION_ANIMATIONS_NAME, mTransitionAnimations.name)
    }


}
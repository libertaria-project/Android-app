package com.stocksexchange.android.ui.orders.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.OrderTypes
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.ui.base.activities.BaseSearchActivity
import com.stocksexchange.android.ui.orders.fragment.OrdersFragment
import com.stocksexchange.android.ui.views.SearchToolbar
import kotlinx.android.synthetic.main.orders_search_activity_layout.*
import org.jetbrains.anko.intentFor

class OrdersSearchActivity : BaseSearchActivity<OrdersFragment, OrdersSearchPresenter>(),
    OrdersSearchContract.View {


    companion object {

        private const val EXTRA_ORDER_TYPE = "order_type"

        private const val SAVED_STATE_ORDER_TYPE = "order_type"


        fun newInstance(orderType: OrderTypes, context: Context): Intent {
            return context.intentFor<OrdersSearchActivity>(
                EXTRA_ORDER_TYPE to orderType
            )
        }

    }


    private lateinit var mOrderType: OrderTypes




    override fun initPresenter(): OrdersSearchPresenter = OrdersSearchPresenter(this)


    override fun performSearch(query: String) {
        mFragment.onPerformSearch(query)
    }


    override fun cancelSearch() {
        mFragment.onCancelSearch()
    }


    override fun getInputType(): Int {
        return (super.getInputType() or InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS)
    }


    override fun getInputHint(): String {
        return getString(when(mOrderType) {
            OrderTypes.ACTIVE -> R.string.orders_search_activity_active_orders_hint
            OrderTypes.COMPLETED -> R.string.orders_search_activity_completed_orders_hint
            OrderTypes.CANCELLED -> R.string.orders_search_activity_cancelled_orders_hint
        })
    }


    override fun getSearchToolbar(): SearchToolbar {
        return searchToolbar
    }


    override fun getActivityFragment(): OrdersFragment {
        val fragment = OrdersFragment.newSearchInstance(mOrderType)
        fragment.progressBarListener = progressBarListener

        return fragment
    }


    override fun getContentLayoutResourceId(): Int = R.layout.orders_search_activity_layout


    override fun getTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.KITKAT_SCALING_ANIMATIONS
    }


    private val progressBarListener = object : OrdersFragment.ProgressBarListener {

        override fun showProgressBar() {
            searchToolbar.hideClearInputButton(false)
            searchToolbar.showProgressBar()
        }

        override fun hideProgressBar() {
            searchToolbar.hideProgressBar()
            searchToolbar.showClearInputButton(false)
        }

    }


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            mOrderType = (savedState.getSerializable(SAVED_STATE_ORDER_TYPE) as OrderTypes)
        } else {
            mOrderType = (intent.getSerializableExtra(SAVED_STATE_ORDER_TYPE) as? OrderTypes) ?: OrderTypes.ACTIVE
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        savedState.putSerializable(SAVED_STATE_ORDER_TYPE, mOrderType)
    }


}
package com.stocksexchange.android.ui.currencymarkets.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.stocksexchange.android.R
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator.Companion.VOLUME_DESCENDING_COMPARATOR
import com.stocksexchange.android.ui.base.activities.BaseSearchActivity
import com.stocksexchange.android.ui.currencymarkets.fragment.CurrencyMarketsFragment
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.views.SearchToolbar
import kotlinx.android.synthetic.main.currency_markets_search_activity_layout.*
import org.jetbrains.anko.intentFor

class CurrencyMarketsSearchActivity : BaseSearchActivity<CurrencyMarketsFragment, CurrencyMarketsSearchPresenter>(),
    CurrencyMarketsSearchContract.View {


    companion object {

        const val TAG = "CurrencyMarketsSearchActivity"

        private const val SAVED_STATE_COMPARATOR = "comparator"


        fun newInstance(context: Context): Intent {
            return context.intentFor<CurrencyMarketsSearchActivity>()
        }

    }


    // todo to be extracted to the settings in the future
    private var mComparator: CurrencyMarketComparator = VOLUME_DESCENDING_COMPARATOR




    override fun initPresenter(): CurrencyMarketsSearchPresenter = CurrencyMarketsSearchPresenter(this)


    override fun init() {
        super.init()

        initCurrencyMarketsSortPanel()
    }


    private fun initCurrencyMarketsSortPanel() {
        with(currencyMarketsSortPanel) {
            mOnSortPanelTitleClickListener = {
                mFragment.sort(it.comparator)
            }
            applyTheme(getAppTheme())
            setComparator(mComparator)

            ThemingUtil.CurrencyMarketsSearch.sortPanel(this, getAppTheme())
        }
    }


    override fun performSearch(query: String) {
        mFragment.onPerformSearch(query)
    }


    override fun cancelSearch() {
        mFragment.onCancelSearch()
    }


    override fun getInputHint(): String {
        return getString(R.string.currency_markets_search_activity_toolbar_query_input_hint)
    }


    override fun getSearchToolbar(): SearchToolbar {
        return searchToolbar
    }


    override fun getActivityFragment(): CurrencyMarketsFragment {
        return CurrencyMarketsFragment.newSearchInstance()
    }


    override fun getContentLayoutResourceId() = R.layout.currency_markets_search_activity_layout


    override fun getTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.KITKAT_SCALING_ANIMATIONS
    }


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            mComparator = savedState.getParcelable(SAVED_STATE_COMPARATOR)
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        savedState.putParcelable(SAVED_STATE_COMPARATOR, currencyMarketsSortPanel.getComparator())
    }


}
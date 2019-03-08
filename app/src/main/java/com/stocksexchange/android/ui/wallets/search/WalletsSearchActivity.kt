package com.stocksexchange.android.ui.wallets.search

import android.content.Context
import android.content.Intent
import android.text.InputType
import com.stocksexchange.android.R
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.ui.base.activities.BaseSearchActivity
import com.stocksexchange.android.ui.views.SearchToolbar
import com.stocksexchange.android.ui.wallets.fragment.WalletsFragment
import kotlinx.android.synthetic.main.wallets_search_activity.*
import org.jetbrains.anko.intentFor

class WalletsSearchActivity : BaseSearchActivity<WalletsFragment, WalletsSearchPresenter>(),
    WalletsSearchContract.View {


    companion object {

        fun newInstance(context: Context): Intent {
            return context.intentFor<WalletsSearchActivity>()
        }

    }




    override fun initPresenter(): WalletsSearchPresenter = WalletsSearchPresenter(this)


    override fun performSearch(query: String) {
        mFragment.onPerformSearch(query)
    }


    override fun cancelSearch() {
        mFragment.onCancelSearch()
    }


    override fun getInputHint(): String {
        return getString(R.string.wallets_search_activity_input_hint_text)
    }


    override fun getSearchToolbar(): SearchToolbar {
        return searchToolbar
    }


    override fun getActivityFragment(): WalletsFragment {
        return WalletsFragment.newSearchInstance()
    }


    override fun getContentLayoutResourceId(): Int = R.layout.wallets_search_activity


    override fun getTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.KITKAT_SCALING_ANIMATIONS
    }


}
package com.stocksexchange.android.ui.favoritecurrencymarkets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.stocksexchange.android.R
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator.Companion.VOLUME_DESCENDING_COMPARATOR
import com.stocksexchange.android.ui.base.activities.BaseFragmentActivity
import com.stocksexchange.android.ui.currencymarkets.fragment.CurrencyMarketsFragment
import com.stocksexchange.android.ui.currencymarkets.search.CurrencyMarketsSearchActivity
import com.stocksexchange.android.theming.ThemingUtil
import kotlinx.android.synthetic.main.favorite_currency_markets_activity.*
import org.jetbrains.anko.intentFor

class FavoriteCurrencyMarketsActivity : BaseFragmentActivity<CurrencyMarketsFragment, FavoriteCurrencyMarketsPresenter>(),
    FavoriteCurrencyMarketsContract.View {


    companion object {

        const val TAG = "FavoriteCurrencyMarketsActivity"

        private const val SAVED_STATE_COMPARATOR = "comparator"


        @JvmStatic
        fun newInstance(context: Context): Intent {
            return context.intentFor<FavoriteCurrencyMarketsActivity>()
        }

    }


    // todo to be extracted to the settings in the future
    private var mComparator: CurrencyMarketComparator = VOLUME_DESCENDING_COMPARATOR




    override fun initPresenter(): FavoriteCurrencyMarketsPresenter = FavoriteCurrencyMarketsPresenter(this)


    override fun init() {
        super.init()

        initContentContainer()
        initToolbar()
        initCurrencyMarketsSortPanel()
    }


    private fun initContentContainer() {
        ThemingUtil.Favorites.contentContainer(contentContainerRl, getAppTheme())
    }


    private fun initToolbar() {
        toolbar.setOnLeftButtonClickListener {
            onBackPressed()
        }

        toolbar.setOnRightButtonClickListener {
            mPresenter?.onActionButtonClicked()
        }

        ThemingUtil.Favorites.toolbar(toolbar, getAppTheme())
    }


    private fun initCurrencyMarketsSortPanel() {
        with(currencyMarketsSortPanel) {
            mOnSortPanelTitleClickListener = {
                mFragment.sort(it.comparator)
            }
            applyTheme(getAppTheme())
            setComparator(mComparator)

            ThemingUtil.Favorites.sortPanel(this, getAppTheme())
        }
    }


    override fun launchSearchActivity() {
        startActivity(CurrencyMarketsSearchActivity.newInstance(this))
    }


    override fun getActivityFragment(): CurrencyMarketsFragment {
        return CurrencyMarketsFragment.newFavoritesInstance()
    }


    override fun getContentLayoutResourceId(): Int = R.layout.favorite_currency_markets_activity


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
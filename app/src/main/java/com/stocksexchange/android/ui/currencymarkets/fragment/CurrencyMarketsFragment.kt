package com.stocksexchange.android.ui.currencymarkets.fragment

import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.mappings.mapToCurrencyMarketItem
import com.stocksexchange.android.mappings.mapToCurrencyMarketItemList
import com.stocksexchange.android.model.CurrencyMarketParameters
import com.stocksexchange.android.model.CurrencyMarketTypes
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator
import com.stocksexchange.android.ui.base.fragments.BaseListDataLoadingFragment
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.currencymarketpreview.CurrencyMarketPreviewActivity
import com.stocksexchange.android.ui.utils.diffcallbacks.CurrencyMarketsDiffCallback
import com.stocksexchange.android.ui.utils.extensions.ctx
import com.stocksexchange.android.ui.utils.extensions.dimenInPx
import com.stocksexchange.android.ui.utils.extensions.getSmallestScreenWidthInDp
import com.stocksexchange.android.ui.utils.extensions.isNetworkAvailable
import com.stocksexchange.android.ui.utils.interfaces.Searchable
import com.stocksexchange.android.ui.utils.interfaces.Sortable
import com.stocksexchange.android.ui.views.InfoView
import com.stocksexchange.android.utils.providers.InfoViewProvider
import kotlinx.android.synthetic.main.currency_markets_fragment_layout.view.*

class CurrencyMarketsFragment : BaseListDataLoadingFragment<
    CurrencyMarketsPresenter,
    List<CurrencyMarket>,
    CurrencyMarketItem,
    CurrencyMarketsRecyclerViewAdapter
>(), CurrencyMarketsContract.View, Sortable, Searchable {


    companion object {

        const val TAG = "CurrencyMarketsFragment"

        private const val SAVED_STATE_IS_DATA_SET_SORTED = "is_data_set_sorted"
        private const val SAVED_STATE_CURRENCY_MARKETS_PARAMS = "currency_markets_params"


        fun newBtcInstance() = newInstance(CurrencyMarketTypes.BTC)

        fun newUsdtInstance() = newInstance(CurrencyMarketTypes.USDT)

        fun newNxtInstance() = newInstance(CurrencyMarketTypes.NXT)

        fun newLtcInstance() = newInstance(CurrencyMarketTypes.LTC)

        fun newEthInstance() = newInstance(CurrencyMarketTypes.ETH)

        fun newUsdInstance() = newInstance(CurrencyMarketTypes.USD)

        fun newEurInstance() = newInstance(CurrencyMarketTypes.EUR)

        fun newJpyInstance() = newInstance(CurrencyMarketTypes.JPY)

        fun newRubInstance() = newInstance(CurrencyMarketTypes.RUB)

        fun newFavoritesInstance() = newInstance(CurrencyMarketTypes.FAVORITES)

        fun newSearchInstance() = newInstance(CurrencyMarketTypes.SEARCH)

        /**
         * Creates a new fragment with a particular type and returns it.
         *
         * @param currencyMarketType The currency market type
         *
         * @return The new instance of this fragment
         */
        fun newInstance(currencyMarketType: CurrencyMarketTypes): CurrencyMarketsFragment {
            val fragment = CurrencyMarketsFragment()

            fragment.mCurrencyMarketParams = fragment.mCurrencyMarketParams.copy(
                currencyMarketType = currencyMarketType
            )

            return fragment
        }

    }


    private var mIsDataSetSorted: Boolean = false

    private var mCurrencyMarketParams: CurrencyMarketParameters = CurrencyMarketParameters(
        "", CurrencyMarketTypes.BTC
    )

    private var mComparator: CurrencyMarketComparator? = null




    override fun initPresenter(): CurrencyMarketsPresenter = CurrencyMarketsPresenter(this)


    override fun init() {
        super.init()

        initContentContainer()
    }


    override fun initAdapter(): CurrencyMarketsRecyclerViewAdapter {
        return CurrencyMarketsRecyclerViewAdapter(ctx, getSettings(), mItems).apply {
            setBaseCurrencySymbolCharacterLimit(getBaseCurrencySymbolCharacterLimit())

            onItemClickListener = { _, item, _ ->
                mPresenter?.onCurrencyMarketItemClicked(item.itemModel)
            }
        }
    }


    private fun initContentContainer() {
        ThemingUtil.CurrencyMarkets.contentContainer(mRootView.contentContainerFl, getAppTheme())
    }


    override fun adjustView(view: View) {
        val canCenterView = (
            (mCurrencyMarketParams.currencyMarketType == CurrencyMarketTypes.FAVORITES)
            || (mCurrencyMarketParams.currencyMarketType== CurrencyMarketTypes.SEARCH)
        )
        val layoutParams: FrameLayout.LayoutParams

        when(view.id) {

            R.id.progressBar -> {
                layoutParams = (view.layoutParams as FrameLayout.LayoutParams)

                if(canCenterView) {
                    layoutParams.gravity = Gravity.CENTER
                    layoutParams.setMargins(0, 0, 0, 0)
                } else {
                    layoutParams.setMargins(0, dimenInPx(R.dimen.fragment_progress_bar_margin_top), 0, 0)
                }

                view.layoutParams = layoutParams
            }

            R.id.infoView -> {
                layoutParams = (view.layoutParams as FrameLayout.LayoutParams)

                if(canCenterView) {
                    layoutParams.gravity = Gravity.CENTER
                }

                view.layoutParams = layoutParams
            }

        }
    }


    override fun sort(payload: Any) {
        if((payload !is CurrencyMarketComparator) || (payload.id == mComparator?.id)) {
            return
        }

        mComparator = payload
        mIsDataSetSorted = false

        sortDataSetIfNecessary()
    }


    override fun sortDataSetIfNecessary() {
        if(mIsDataSetSorted || !isInitialized() || !isSelected() || isDataSourceEmpty()) {
            return
        }

        mAdapter.sort(mComparator)
        mIsDataSetSorted = true
    }


    override fun updateItemWith(item: CurrencyMarket, position: Int) {
        mAdapter.updateItemWith(position, item.mapToCurrencyMarketItem())
    }


    override fun launchCurrencyMarketPreviewActivity(currencyMarket: CurrencyMarket) {
        //startActivity(CurrencyMarketPreviewActivity.newInstance(ctx, TAG, currencyMarket))
        startActivity(CurrencyMarketPreviewActivity.newInstance(ctx, TAG, currencyMarket))
    }


    override fun addData(data: List<CurrencyMarket>) {
        if(isDataSourceEmpty()) {
            val currencyMarketList = data.mapToCurrencyMarketItemList()
                .toMutableList()
                .also { mItems = it }

            mAdapter.setItems(currencyMarketList, false)

            mIsDataSetSorted = false
            sortDataSetIfNecessary()
        } else {
            val currencyMarketItemList = if(mComparator != null) {
                data.sortedWith(mComparator!!)
            } else {
                data
            }.mapToCurrencyMarketItemList().toMutableList()

            mAdapter.setItems(
                currencyMarketItemList,
                CurrencyMarketsDiffCallback(mItems, currencyMarketItemList)
            )

            mItems = currencyMarketItemList
            mIsDataSetSorted = true
        }
    }


    override fun addCurrencyMarketChronologically(currencyMarket: CurrencyMarket) {
        mAdapter.addItem(
            mAdapter.getChronologicalPositionForCurrencyMarket(currencyMarket, mComparator),
            currencyMarket.mapToCurrencyMarketItem()
        )
    }


    override fun deleteCurrencyMarket(currencyMarket: CurrencyMarket) {
        mAdapter.deleteItem(currencyMarket.mapToCurrencyMarketItem())
    }


    override fun setSearchQuery(query: String) {
        mCurrencyMarketParams = mCurrencyMarketParams.copy(searchQuery = query)
    }


    override fun containsCurrencyMarket(currencyMarket: CurrencyMarket): Boolean {
        return mAdapter.contains(currencyMarket.mapToCurrencyMarketItem())
    }


    override fun shouldColorInfoViewIcon(): Boolean {
        val currencyMarketType = mCurrencyMarketParams.currencyMarketType

        return ((currencyMarketType == CurrencyMarketTypes.USD)
                || (currencyMarketType == CurrencyMarketTypes.EUR)
                || (currencyMarketType == CurrencyMarketTypes.JPY)
                || (currencyMarketType == CurrencyMarketTypes.RUB)
                || (currencyMarketType == CurrencyMarketTypes.SEARCH)
                || (currencyMarketType == CurrencyMarketTypes.FAVORITES))
    }


    override fun getMarketIndexForMarketId(id: Long): Int? {
        return mAdapter.getMarketIndexForMarketId(id)
    }


    override fun getItem(position: Int): CurrencyMarket? {
        return mAdapter.getItem(position)?.itemModel
    }


    override fun getCurrencyMarketsParameters(): CurrencyMarketParameters {
        return mCurrencyMarketParams
    }


    override fun getInfoViewIcon(infoViewProvider: InfoViewProvider): Drawable? {
        return infoViewProvider.getCurrencyMarketsIcon(mCurrencyMarketParams)
    }


    override fun getEmptyViewCaption(infoViewProvider: InfoViewProvider): String {
        return infoViewProvider.getCurrencyMarketsEmptyCaption(mCurrencyMarketParams)
    }


    private fun getBaseCurrencySymbolCharacterLimit(): Int {
        return if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val screenWidthInDp = ctx.getSmallestScreenWidthInDp()

            when {
                (screenWidthInDp < 450) -> 4
                (screenWidthInDp < 500) -> 6

                else -> -1
            }
        } else {
            -1
        }
    }


    override fun getSearchQuery(): String = mCurrencyMarketParams.searchQuery


    override fun getMainView(): View = mRootView.recyclerView


    override fun getInfoView(): InfoView = mRootView.infoView


    override fun getProgressBar(): ProgressBar = mRootView.progressBar


    override fun getRefreshProgressBar(): SwipeRefreshLayout = mRootView.swipeRefreshLayout


    override fun getContentLayoutResourceId() = R.layout.currency_markets_fragment_layout


    override fun onBackPressed() {
        mPresenter?.onBackPressed()
    }


    @Suppress("UNCHECKED_CAST")
    override fun onRestoreState(savedState: Bundle?) {
        savedState?.apply {
            mIsDataSetSorted = getBoolean(SAVED_STATE_IS_DATA_SET_SORTED, false)
            mCurrencyMarketParams = getParcelable(SAVED_STATE_CURRENCY_MARKETS_PARAMS)
        }

        super.onRestoreState(savedState)
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        savedState.putBoolean(SAVED_STATE_IS_DATA_SET_SORTED, mIsDataSetSorted)
        savedState.putParcelable(SAVED_STATE_CURRENCY_MARKETS_PARAMS, mCurrencyMarketParams)
    }


}
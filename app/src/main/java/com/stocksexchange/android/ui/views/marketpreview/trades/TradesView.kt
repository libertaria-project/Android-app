package com.stocksexchange.android.ui.views.marketpreview.trades

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.Trade
import com.stocksexchange.android.api.model.TradeParameters
import com.stocksexchange.android.mappings.mapToIdTradeActionItemsMap
import com.stocksexchange.android.model.DataActionItem
import com.stocksexchange.android.model.TradeData
import com.stocksexchange.android.model.TradeHeader
import com.stocksexchange.android.ui.utils.decorators.TradesSpacingItemDecorator
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.views.InfoView
import com.stocksexchange.android.ui.views.marketpreview.base.BaseMarketPreviewListView
import com.stocksexchange.android.ui.views.marketpreview.base.interfaces.BaseMarketPreviewListData
import com.stocksexchange.android.ui.views.marketpreview.trades.items.TradeHeaderItem
import com.stocksexchange.android.ui.views.marketpreview.trades.items.TradeItem
import kotlinx.android.synthetic.main.trades_view_layout.view.*
import java.io.Serializable

/**
 * A view representing history of trades of a particular market.
 */
class TradesView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseMarketPreviewListView<
    List<Trade>,
    Trade,
    TradeParameters,
    TradesRecyclerViewAdapter
>(context, attrs, defStyleAttr) {


    companion object {

        internal const val ATTRIBUTE_KEY_TRADES_LIMIT = "trades_limit"
        internal const val ATTRIBUTE_KEY_TRADE_TIME_COLOR = "trade_time_color"

        private const val DEFAULT_TRADES_LIMIT = 15

        private const val DEFAULT_TRADE_TIME_COLOR = Color.WHITE

        private const val HEADER_ID = 1000L

    }


    private var mTradesLimit: Int = 0
    private var mTradeTimeColor: Int = 0

    private var mTradesDataIdMap: Map<Long, TradeData> = mapOf()

    private var mTradeResources = TradeResources.getDefaultResources(context)

    private var mRecyclerViewDecorator = TradesSpacingItemDecorator(-1)




    override fun saveAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.TradesView, defStyleAttr, 0).run {
            try {
                with(mAttributes) {
                    save(ATTRIBUTE_KEY_IS_HIGHLIGHTING_ENABLED, getBoolean(R.styleable.TradesView_isHighlightingEnabled, DEFAULT_IS_HIGHLIGHTING_ENABLED))
                    save(ATTRIBUTE_KEY_RV_HEIGHT, getLayoutDimension(R.styleable.TradesView_rvHeight, DEFAULT_RV_HEIGHT))
                    save(ATTRIBUTE_KEY_RV_ITEM_BOTTOM_SPACING, getDimensionPixelSize(R.styleable.TradesView_rvItemBottomSpacing, dpToPx(DEFAULT_RV_ITEM_BOTTOM_SPACING_IN_DP)))
                    save(ATTRIBUTE_KEY_TRADES_LIMIT, getInteger(R.styleable.TradesView_tradesLimit, DEFAULT_TRADES_LIMIT))
                    save(ATTRIBUTE_KEY_PRICE_MAX_CHARS_LENGTH, getInteger(R.styleable.TradesView_priceMaxCharsLength, DEFAULT_PRICE_MAX_CHARS_LENGTH))
                    save(ATTRIBUTE_KEY_AMOUNT_MAX_CHARS_LENGTH, getInteger(R.styleable.TradesView_amountMaxCharsLength, DEFAULT_AMOUNT_MAX_CHARS_LENGTH))
                    save(ATTRIBUTE_KEY_PROGRESS_BAR_COLOR, getColor(R.styleable.TradesView_progressBarColor, DEFAULT_PROGRESS_BAR_COLOR))
                    save(ATTRIBUTE_KEY_INFO_VIEW_COLOR, getColor(R.styleable.TradesView_infoViewColor, DEFAULT_INFO_VIEW_COLOR))
                    save(ATTRIBUTE_KEY_HEADER_TITLE_TEXT_COLOR, getColor(R.styleable.TradesView_headerTitleTextColor, DEFAULT_HEADER_TITLE_TEXT_COLOR))
                    save(ATTRIBUTE_KEY_HEADER_SEPARATOR_COLOR, getColor(R.styleable.TradesView_headerSeparatorColor, DEFAULT_HEADER_SEPARATOR_COLOR))
                    save(ATTRIBUTE_KEY_BUY_HIGHLIGHT_BACKGROUND_COLOR, getColor(R.styleable.TradesView_buyTradeHighlightBackgroundColor, DEFAULT_BUY_HIGHLIGHT_BACKGROUND_COLOR))
                    save(ATTRIBUTE_KEY_SELL_HIGHLIGHT_BACKGROUND_COLOR, getColor(R.styleable.TradesView_sellTradeHighlightBackgroundColor, DEFAULT_SELL_HIGHLIGHT_BACKROUND_COLOR))
                    save(ATTRIBUTE_KEY_BUY_PRICE_HIGHLIGHT_COLOR, getColor(R.styleable.TradesView_buyTradePriceHighlightColor, DEFAULT_BUY_PRICE_HIGHLIGHT_COLOR))
                    save(ATTRIBUTE_KEY_SELL_PRICE_HIGHLIGHT_COLOR, getColor(R.styleable.TradesView_sellTradePriceHighlightColor, DEFAULT_SELL_PRICE_HIGHLIGHT_COLOR))
                    save(ATTRIBUTE_KEY_BUY_PRICE_COLOR, getColor(R.styleable.TradesView_buyTradeColor, DEFAULT_BUY_PRICE_COLOR))
                    save(ATTRIBUTE_KEY_SELL_PRICE_COLOR, getColor(R.styleable.TradesView_sellTradeColor, DEFAULT_SELL_PRICE_COLOR))
                    save(ATTRIBUTE_KEY_AMOUNT_COLOR, getColor(R.styleable.TradesView_tradeAmountColor, DEFAULT_AMOUNT_COLOR))
                    save(ATTRIBUTE_KEY_TRADE_TIME_COLOR, getColor(R.styleable.TradesView_tradeTimeColor, DEFAULT_TRADE_TIME_COLOR))
                    save(ATTRIBUTE_KEY_HIGHLIGHT_DURATION, getInteger(R.styleable.TradesView_tradeHighlightDuration, DEFAULT_HIGHLIGHT_DURATION))
                    save(ATTRIBUTE_KEY_INFO_VIEW_EMPTY_CAPTION, (getString(R.styleable.TradesView_infoViewEmptyCaption) ?: DEFAULT_INFO_VIEW_CAPTION))
                    save(ATTRIBUTE_KEY_INFO_VIEW_ERROR_CAPTION, (getString(R.styleable.TradesView_infoViewErrorCaption) ?: DEFAULT_INFO_VIEW_CAPTION))
                    save(ATTRIBUTE_KEY_INFO_VIEW_ICON, (getDrawable(R.styleable.TradesView_infoViewIcon) ?: context.getCompatDrawable(getDefaultInfoViewIconResourceId())))
                }
            } finally {
                recycle()
            }
        }
    }


    override fun initRecyclerView() {
        with(recyclerView) {
            disableAnimations()
            addItemDecoration(mRecyclerViewDecorator)

            layoutManager = mLayoutManager

            mAdapter = TradesRecyclerViewAdapter(context, mItems)
            mAdapter?.setHasStableIds(true)
            mAdapter?.setResources(mTradeResources)

            adapter = mAdapter
        }
    }


    override fun restoreAttributes() {
        super.restoreAttributes()

        with(mAttributes) {
            mTradesLimit = get(ATTRIBUTE_KEY_TRADES_LIMIT, DEFAULT_TRADES_LIMIT)
            mTradeTimeColor = get(ATTRIBUTE_KEY_TRADE_TIME_COLOR, DEFAULT_TRADE_TIME_COLOR)
        }
    }


    override fun applyAttributes() {
        super.applyAttributes()

        setTradesLimit(mTradesLimit)
        setTradeTimeColor(mTradeTimeColor)
    }


    override fun updateRecyclerViewItemDecorator() {
        with(recyclerView) {
            removeItemDecoration(mRecyclerViewDecorator)
            mRecyclerViewDecorator = TradesSpacingItemDecorator(mRvItemBottomSpacing)
            addItemDecoration(mRecyclerViewDecorator)
        }
    }


    override fun updateResources() {
        mTradeResources = TradeResources.newInstance(
            context,
            mPriceMaxCharsLength,
            mAmountMaxCharsLength,
            DEFAULT_STUB_TEXT,
            getColorsForResources()
        )

        mAdapter?.setResources(mTradeResources)
    }


    private fun getColorsForResources(): List<Int> {
        return listOf(
            mHeaderTitleTextColor,
            mHeaderSeparatorColor,
            mBuyBackgroundHighlightColor,
            mSellBackgroundHighlightColor,
            mBuyPriceHighlightColor,
            mSellPriceHighlightColor,
            mBuyPriceColor,
            mSellPriceColor,
            mAmountColor,
            mTradeTimeColor
        )
    }


    override fun truncateData(data: List<Trade>?): List<Trade>? {
        return if(data == null) {
            return data
        } else if(data.size > mDataParameters.count) {
            data.take(mDataParameters.count)
        } else {
            data
        }
    }


    override fun updateData(data: List<Trade>, dataActionItems: List<DataActionItem<Trade>>) {
        super.updateData(data, dataActionItems)

        val idTradeActionItemsMap = dataActionItems.mapToIdTradeActionItemsMap()

        bindDataInternal {
            if(mIsHighlightingEnabled) {
                if(idTradeActionItemsMap[it.id]?.action == DataActionItem.Actions.INSERT) {
                    (System.currentTimeMillis() + mHighlightDuration)
                } else {
                    BaseMarketPreviewListData.NO_TIMESTAMP
                }
            } else {
                BaseMarketPreviewListData.NO_TIMESTAMP
            }

        }
    }


    override fun bindDataInternal(getHighlightEndTimestamp: (Trade) -> Long) {
        if(isDataEmpty()) {
            return
        }

        val data = mData!!
        val tradesDataIdMap: MutableMap<Long, TradeData> = mutableMapOf()

        mItems.clear()
        mItems.add(TradeHeaderItem(TradeHeader(
            id = HEADER_ID,
            amountTitleText = resources.getString(R.string.amount),
            priceTitleText = resources.getString(R.string.price),
            timeTitleText = resources.getString(R.string.time)
        )))

        val stubBuyTrade = Trade.STUB_BUY_TRADE
        val stubSellTrade = Trade.STUB_SELL_TRADE
        var previousTrade = stubBuyTrade
        var trade: Trade?
        var tradeData: TradeData?

        for(i in 0 until mDataParameters.count) {
            trade = data.getOrNull(i) ?: if(i == 0) {
                stubBuyTrade
            } else if(previousTrade.isBuyTrade()) {
                stubSellTrade
            } else {
                stubBuyTrade
            }
            previousTrade = trade
            tradeData = TradeData(
                trade,
                if(mTradesDataIdMap.containsKey(trade.id)) {
                    mTradesDataIdMap[trade.id]?.highlightEndTimestamp
                        ?: BaseMarketPreviewListData.NO_TIMESTAMP
                } else {
                    getHighlightEndTimestamp(trade)
                }
            )

            tradesDataIdMap[trade.id] = tradeData
            mItems.add(TradeItem(tradeData))
        }

        mTradesDataIdMap = tradesDataIdMap
        mAdapter?.items = mItems
    }


    override fun clearData() {
        super.clearData()

        mTradesDataIdMap = emptyMap()
    }


    /**
     * Sets a limit of a number of trades to download.
     *
     * @param limit The limit to set
     */
    fun setTradesLimit(limit: Int) {
        mDataParameters = mDataParameters.copy(count = limit)
    }


    /**
     * Sets a color of the trade's time.
     *
     * @param color The color to set
     */
    fun setTradeTimeColor(@ColorInt color: Int) {
        mTradeTimeColor = color
        updateResources()
    }


    /**
     * Sets a market name of the trades data to load.
     *
     * @param marketName The market name to set
     */
    fun setMarketName(marketName: String) {
        mDataParameters = mDataParameters.copy(marketName = marketName)
    }


    override fun isDataEmpty(): Boolean {
        return (mData?.isEmpty() ?: true)
    }


    override fun getDefaultInfoViewIconResourceId(): Int {
        return R.drawable.ic_coin_exchange
    }


    override fun getLayoutResourceId(): Int = R.layout.trades_view_layout


    override fun getDefaultParameters(): TradeParameters {
        return TradeParameters.getDefaultParameters()
    }


    override fun getLayoutManager(): LinearLayoutManager = mLayoutManager


    override fun getProgressBar(): ProgressBar = progressBar


    override fun getInfoView(): InfoView = infoView


    override fun getMainView(): View = recyclerView


    private val mLayoutManager = object : LinearLayoutManager(context) {

        override fun canScrollVertically(): Boolean {
            return mCanRecyclerViewScroll
        }

    }


    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)

        if(state is SavedState) {
            setTradeTimeColor(state.tradeTimeColor)
            setData(state.data, true)
        }
    }


    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState()).apply {
            onSaveInstanceState(this)
        }
    }


    override fun onSaveInstanceState(savedState: BaseMarketPreviewSavedState<TradeParameters>) {
        super.onSaveInstanceState(savedState)

        if(savedState is SavedState) {
            with(savedState) {
                tradeTimeColor = mTradeTimeColor
                data = mData
            }
        }
    }


    private class SavedState : BaseMarketPreviewListBasedState<TradeParameters> {

        companion object {

            private const val KEY_TRADE_TIME_COLOR = "trade_time_color"
            private const val KEY_DATA = "data"


            @JvmField
            var CREATOR = object : Parcelable.Creator<SavedState> {

                override fun createFromParcel(parcel: Parcel): SavedState = SavedState(parcel)

                override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)

            }

        }


        internal var tradeTimeColor: Int = 0

        internal var data: List<Trade>? = null


        @Suppress("UNCHECKED_CAST")
        constructor(parcel: Parcel): super(parcel) {
            parcel.readBundle(CLASS_LOADER)?.run {
                tradeTimeColor = getInt(KEY_TRADE_TIME_COLOR, DEFAULT_TRADE_TIME_COLOR)
                data = (getSerializable(KEY_DATA) as? List<Trade>)
            }
        }


        constructor(superState: Parcelable?): super(superState)


        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)

            val bundle = Bundle(CLASS_LOADER).apply {
                putInt(KEY_TRADE_TIME_COLOR, tradeTimeColor)
                putSerializable(KEY_DATA, (data as? Serializable))
            }

            parcel.writeBundle(bundle)
        }

    }


}
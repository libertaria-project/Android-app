package com.stocksexchange.android.ui.views.marketpreview.orderbook

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.Orderbook
import com.stocksexchange.android.api.model.OrderbookOrder
import com.stocksexchange.android.api.model.OrderbookParameters
import com.stocksexchange.android.api.utils.truncate
import com.stocksexchange.android.mappings.mapToPriceOrderbookOrderActionItemsMap
import com.stocksexchange.android.model.DataActionItem
import com.stocksexchange.android.model.DataActionItem.Actions.INSERT
import com.stocksexchange.android.model.DataActionItem.Actions.UPDATE
import com.stocksexchange.android.model.OrderbookHeader
import com.stocksexchange.android.model.OrderbookOrderData
import com.stocksexchange.android.model.OrderbookOrderData.Companion.VOLUME_LEVEL_MAX_VALUE
import com.stocksexchange.android.model.OrderbookOrderTypes
import com.stocksexchange.android.ui.utils.decorators.OrderbookSpacingItemDecorator
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.views.InfoView
import com.stocksexchange.android.ui.views.marketpreview.base.BaseMarketPreviewListView
import com.stocksexchange.android.ui.views.marketpreview.base.interfaces.BaseMarketPreviewListData
import com.stocksexchange.android.ui.views.marketpreview.orderbook.items.OrderbookHeaderItem
import com.stocksexchange.android.ui.views.marketpreview.orderbook.items.OrderbookOrderItem
import kotlinx.android.synthetic.main.orderbook_view_layout.view.*

/**
 * A view representing an orderbook that shows current bidding and asking
 * prices as well as amounts.
 */
class OrderbookView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseMarketPreviewListView<
    Orderbook,
    OrderbookOrder,
    OrderbookParameters,
    OrderbookRecyclerViewAdapter
>(context, attrs, defStyleAttr) {


    companion object {

        internal const val ATTRIBUTE_KEY_SHOULD_HIDE_HEADER_MORE_BUTTON = "should_hide_header_more_button"
        internal const val ATTRIBUTE_KEY_RV_ITEM_HORIZONTAL_SPACING = "rv_item_horizontal_spacing"
        internal const val ATTRIBUTE_KEY_ORDERS_OF_TYPE_COUNT_LIMIT = "orders_of_type_count_limit"
        internal const val ATTRIBUTE_KEY_HEADER_MORE_BUTTON_COLOR = "header_more_button_color"
        internal const val ATTRIBUTE_KEY_BUY_ORDER_BACKGROUND_COLOR = "buy_order_background_color"
        internal const val ATTRIBUTE_KEY_SELL_ORDER_BACKGROUND_COLOR = "sell_order_background_color"

        private const val DEFAULT_SHOULD_HIDE_HEADER_MORE_BUTTON = false

        private const val DEFAULT_RV_ITEM_HORIZONTAL_SPACING_IN_DP = 4

        private const val DEFAULT_ORDERS_OF_TYPE_COUNT_LIMIT = 15

        private const val DEFAULT_HEADER_MORE_BUTTON_COLOR = Color.GRAY
        private const val DEFAULT_ORDER_BACKGROUND_COLOR = Color.TRANSPARENT

        private const val ORDERS_OF_TYPE_COUNT_NO_LIMIT = -1

        private const val RECYCLER_VIEW_COLUMN_COUNT = 2

        private const val RECYCLER_VIEW_BID_HEADER_POSITION = 0
        private const val RECYCLER_VIEW_ASK_HEADER_POSITION = 1

    }


    private var mShouldHideHeaderMoreButton: Boolean = false

    private var mRvItemHorizontalSpacing: Int = 0

    private var mOrdersOfTypeCountLimit: Int = 0

    private var mHeaderMoreButtonColor: Int = 0
    private var mBuyOrderBackgroundColor: Int = 0
    private var mSellOrderBackgroundColor: Int = 0

    private var mPriceOrderbookOrdersDataMap: Map<Double, OrderbookOrderData> = mapOf()

    private var mOrderbookResources = OrderbookResources.getDefaultResources(context)

    private var mRecyclerViewDecorator: OrderbookSpacingItemDecorator? = null




    override fun saveAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.OrderbookView, defStyleAttr, 0).run {
            try {
                with(mAttributes) {
                    save(ATTRIBUTE_KEY_IS_HIGHLIGHTING_ENABLED, getBoolean(R.styleable.OrderbookView_isHighlightingEnabled, DEFAULT_IS_HIGHLIGHTING_ENABLED))
                    save(ATTRIBUTE_KEY_SHOULD_HIDE_HEADER_MORE_BUTTON, getBoolean(R.styleable.OrderbookView_shouldHideHeaderMoreButton, DEFAULT_SHOULD_HIDE_HEADER_MORE_BUTTON))
                    save(ATTRIBUTE_KEY_RV_HEIGHT, getLayoutDimension(R.styleable.OrderbookView_rvHeight, DEFAULT_RV_HEIGHT))
                    save(ATTRIBUTE_KEY_RV_ITEM_HORIZONTAL_SPACING, getDimensionPixelSize(R.styleable.OrderbookView_rvItemHorizontalSpacing, dpToPx(DEFAULT_RV_ITEM_HORIZONTAL_SPACING_IN_DP)))
                    save(ATTRIBUTE_KEY_RV_ITEM_BOTTOM_SPACING, getDimensionPixelSize(R.styleable.OrderbookView_rvItemBottomSpacing, dpToPx(DEFAULT_RV_ITEM_BOTTOM_SPACING_IN_DP)))
                    save(ATTRIBUTE_KEY_ORDERS_OF_TYPE_COUNT_LIMIT, getInteger(R.styleable.OrderbookView_ordersOfTypeCountLimit, DEFAULT_ORDERS_OF_TYPE_COUNT_LIMIT))
                    save(ATTRIBUTE_KEY_PRICE_MAX_CHARS_LENGTH, getInteger(R.styleable.OrderbookView_priceMaxCharsLength, DEFAULT_PRICE_MAX_CHARS_LENGTH))
                    save(ATTRIBUTE_KEY_AMOUNT_MAX_CHARS_LENGTH, getInteger(R.styleable.OrderbookView_amountMaxCharsLength, DEFAULT_AMOUNT_MAX_CHARS_LENGTH))
                    save(ATTRIBUTE_KEY_PROGRESS_BAR_COLOR, getColor(R.styleable.OrderbookView_progressBarColor, DEFAULT_PROGRESS_BAR_COLOR))
                    save(ATTRIBUTE_KEY_INFO_VIEW_COLOR, getColor(R.styleable.OrderbookView_infoViewColor, DEFAULT_INFO_VIEW_COLOR))
                    save(ATTRIBUTE_KEY_HEADER_TITLE_TEXT_COLOR, getColor(R.styleable.OrderbookView_headerTitleTextColor, DEFAULT_HEADER_TITLE_TEXT_COLOR))
                    save(ATTRIBUTE_KEY_HEADER_MORE_BUTTON_COLOR, getColor(R.styleable.OrderbookView_headerMoreButtonColor, DEFAULT_HEADER_MORE_BUTTON_COLOR))
                    save(ATTRIBUTE_KEY_HEADER_SEPARATOR_COLOR, getColor(R.styleable.OrderbookView_headerSeparatorColor, DEFAULT_HEADER_SEPARATOR_COLOR))
                    save(ATTRIBUTE_KEY_BUY_HIGHLIGHT_BACKGROUND_COLOR, getColor(R.styleable.OrderbookView_buyOrderHighlightBackgroundColor, DEFAULT_BUY_HIGHLIGHT_BACKGROUND_COLOR))
                    save(ATTRIBUTE_KEY_SELL_HIGHLIGHT_BACKGROUND_COLOR, getColor(R.styleable.OrderbookView_sellOrderHighlightBackgroundColor, DEFAULT_SELL_HIGHLIGHT_BACKROUND_COLOR))
                    save(ATTRIBUTE_KEY_BUY_PRICE_HIGHLIGHT_COLOR, getColor(R.styleable.OrderbookView_bidOrderPriceHighlightColor, DEFAULT_BUY_PRICE_HIGHLIGHT_COLOR))
                    save(ATTRIBUTE_KEY_SELL_PRICE_HIGHLIGHT_COLOR, getColor(R.styleable.OrderbookView_askOrderPriceHighlightColor, DEFAULT_SELL_PRICE_HIGHLIGHT_COLOR))
                    save(ATTRIBUTE_KEY_BUY_PRICE_COLOR, getColor(R.styleable.OrderbookView_bidOrderPriceColor, DEFAULT_BUY_PRICE_COLOR))
                    save(ATTRIBUTE_KEY_SELL_PRICE_COLOR, getColor(R.styleable.OrderbookView_askOrderPriceColor, DEFAULT_SELL_PRICE_COLOR))
                    save(ATTRIBUTE_KEY_AMOUNT_COLOR, getColor(R.styleable.OrderbookView_orderAmountColor, DEFAULT_AMOUNT_COLOR))
                    save(ATTRIBUTE_KEY_BUY_ORDER_BACKGROUND_COLOR, getColor(R.styleable.OrderbookView_bidOrderBackgroundColor, DEFAULT_ORDER_BACKGROUND_COLOR))
                    save(ATTRIBUTE_KEY_SELL_ORDER_BACKGROUND_COLOR, getColor(R.styleable.OrderbookView_askOrderBackgroundColor, DEFAULT_ORDER_BACKGROUND_COLOR))
                    save(ATTRIBUTE_KEY_HIGHLIGHT_DURATION, getColor(R.styleable.OrderbookView_orderHighlightDuration, DEFAULT_HIGHLIGHT_DURATION))
                    save(ATTRIBUTE_KEY_INFO_VIEW_EMPTY_CAPTION, (getString(R.styleable.OrderbookView_infoViewEmptyCaption) ?: DEFAULT_INFO_VIEW_CAPTION))
                    save(ATTRIBUTE_KEY_INFO_VIEW_ERROR_CAPTION, (getString(R.styleable.OrderbookView_infoViewErrorCaption) ?: DEFAULT_INFO_VIEW_CAPTION))
                    save(ATTRIBUTE_KEY_INFO_VIEW_ICON, (getDrawable(R.styleable.OrderbookView_infoViewIcon) ?: context.getCompatDrawable(getDefaultInfoViewIconResourceId())))
                }
            } finally {
                recycle()
            }
        }
    }


    override fun initRecyclerView() {
        with(recyclerView) {
            disableAnimations()

            layoutManager = mLayoutManager

            mAdapter = OrderbookRecyclerViewAdapter(context, mItems)
            mAdapter?.setHasStableIds(true)
            mAdapter?.setResources(mOrderbookResources)

            adapter = mAdapter
        }
    }


    override fun restoreAttributes() {
        super.restoreAttributes()

        with(mAttributes) {
            mShouldHideHeaderMoreButton = get(ATTRIBUTE_KEY_SHOULD_HIDE_HEADER_MORE_BUTTON, DEFAULT_SHOULD_HIDE_HEADER_MORE_BUTTON)
            mRvItemHorizontalSpacing = get(ATTRIBUTE_KEY_RV_ITEM_HORIZONTAL_SPACING, dpToPx(DEFAULT_RV_ITEM_HORIZONTAL_SPACING_IN_DP))
            mOrdersOfTypeCountLimit = get(ATTRIBUTE_KEY_ORDERS_OF_TYPE_COUNT_LIMIT, DEFAULT_ORDERS_OF_TYPE_COUNT_LIMIT)
            mHeaderMoreButtonColor = get(ATTRIBUTE_KEY_HEADER_MORE_BUTTON_COLOR, DEFAULT_HEADER_MORE_BUTTON_COLOR)
            mBuyOrderBackgroundColor = get(ATTRIBUTE_KEY_BUY_ORDER_BACKGROUND_COLOR, DEFAULT_ORDER_BACKGROUND_COLOR)
            mSellOrderBackgroundColor = get(ATTRIBUTE_KEY_SELL_ORDER_BACKGROUND_COLOR, DEFAULT_ORDER_BACKGROUND_COLOR)
        }
    }


    override fun applyAttributes() {
        super.applyAttributes()

        setShouldHideHeaderMoreButton(mShouldHideHeaderMoreButton)
        setRecyclerViewHorizontalSpacing(mRvItemHorizontalSpacing)
        setOrdersOfTypeCountLimit(mOrdersOfTypeCountLimit)
        setHeaderMoreButtonColor(mHeaderMoreButtonColor)
        setBuyOrderBackgroundColor(mBuyOrderBackgroundColor)
        setSellOrderBackgroundColor(mSellOrderBackgroundColor)
    }


    private fun hasOrdersOfTypeCountLimit(): Boolean {
        return (mOrdersOfTypeCountLimit != ORDERS_OF_TYPE_COUNT_NO_LIMIT)
    }


    override fun updateRecyclerViewItemDecorator() {
        with(recyclerView) {
            if(mRecyclerViewDecorator != null) {
                removeItemDecoration(mRecyclerViewDecorator!!)
            }

            mRecyclerViewDecorator = object : OrderbookSpacingItemDecorator(
                RECYCLER_VIEW_COLUMN_COUNT,
                mRvItemHorizontalSpacing,
                mRvItemBottomSpacing
            ) {

                override fun isHeader(adapterPosition: Int, view: View): Boolean {
                    return ((adapterPosition == RECYCLER_VIEW_BID_HEADER_POSITION) ||
                            (adapterPosition == RECYCLER_VIEW_ASK_HEADER_POSITION))
                }

            }

            addItemDecoration(mRecyclerViewDecorator!!)
        }
    }


    override fun updateResources() {
        mOrderbookResources = OrderbookResources.newInstance(
            context,
            mShouldHideHeaderMoreButton,
            mPriceMaxCharsLength,
            mAmountMaxCharsLength,
            DEFAULT_STUB_TEXT,
            getColorsForResources()
        )

        mAdapter?.setResources(mOrderbookResources)
    }


    private fun getColorsForResources(): List<Int> {
        return listOf(
            mHeaderTitleTextColor,
            mHeaderMoreButtonColor,
            mHeaderSeparatorColor,
            mBuyBackgroundHighlightColor,
            mSellBackgroundHighlightColor,
            mBuyPriceHighlightColor,
            mSellPriceHighlightColor,
            mBuyPriceColor,
            mSellPriceColor,
            mAmountColor,
            mBuyOrderBackgroundColor,
            mSellOrderBackgroundColor
        )
    }


    override fun truncateData(data: Orderbook?): Orderbook? {
        if(!hasOrdersOfTypeCountLimit()) {
            return data
        }

        return data.truncate(mOrdersOfTypeCountLimit)
    }


    override fun updateData(data: Orderbook, dataActionItems: List<DataActionItem<OrderbookOrder>>) {
        super.updateData(data, dataActionItems)

        val priceOrderbookOrderActionItemsMap = dataActionItems.mapToPriceOrderbookOrderActionItemsMap()

        bindDataInternal {
            if(mIsHighlightingEnabled) {
                if(priceOrderbookOrderActionItemsMap.containsKey(it.price)) {
                    if(priceOrderbookOrderActionItemsMap[it.price]?.action in listOf(INSERT, UPDATE)) {
                        (System.currentTimeMillis() + mHighlightDuration)
                    } else {
                        BaseMarketPreviewListData.NO_TIMESTAMP
                    }
                } else if(mPriceOrderbookOrdersDataMap.containsKey(it.price)) {
                    mPriceOrderbookOrdersDataMap[it.price]?.highlightEndTimestamp ?: BaseMarketPreviewListData.NO_TIMESTAMP
                } else {
                    BaseMarketPreviewListData.NO_TIMESTAMP
                }
            } else {
                BaseMarketPreviewListData.NO_TIMESTAMP
            }
        }
    }


    override fun bindDataInternal(getHighlightEndTimestamp: (OrderbookOrder) -> Long) {
        if(isDataEmpty()) {
            return
        }

        val orderbook = mData!!
        val priceOrderbookOrdersDataMap: MutableMap<Double, OrderbookOrderData> = mutableMapOf()

        mItems.clear()
        mItems.add(OrderbookHeaderItem(OrderbookHeader(
            context.getString(R.string.action_bid),
            OrderbookOrderTypes.BID
        )))
        mItems.add(OrderbookHeaderItem(OrderbookHeader(
            context.getString(R.string.action_ask),
            OrderbookOrderTypes.ASK
        )))

        val stubOrder = OrderbookOrder.STUB_ORDERBOOK_ORDER
        val buyOrders = orderbook.buyOrders
        val sellOrders = orderbook.sellOrders
        val buyOrdersVolume = orderbook.buyOrdersVolume
        val sellOrdersVolume = orderbook.sellOrdersVolume
        var buyOrderVolume = 0.0
        var sellOrderVolume = 0.0
        var buyOrder: OrderbookOrder
        var sellOrder: OrderbookOrder
        var buyOrderData: OrderbookOrderData
        var sellOrderData: OrderbookOrderData

        val ordersCount = if(hasOrdersOfTypeCountLimit()) {
            mOrdersOfTypeCountLimit
        } else {
            orderbook.getLargestOrdersCount()
        }

        for(i in 0 until ordersCount) {
            buyOrder = buyOrders.getOrNull(i) ?: stubOrder
            sellOrder = sellOrders.getOrNull(i) ?: stubOrder

            buyOrderVolume += if(buyOrder.isStub()) 0.0 else buyOrder.amount
            sellOrderVolume += if(sellOrder.isStub()) 0.0 else sellOrder.amount

            buyOrderData = OrderbookOrderData(
                OrderbookOrderTypes.BID,
                buyOrder,
                Math.round(((buyOrderVolume / buyOrdersVolume) * VOLUME_LEVEL_MAX_VALUE)).toInt(),
                getHighlightEndTimestamp(buyOrder)
            )
            sellOrderData = OrderbookOrderData(
                OrderbookOrderTypes.ASK,
                sellOrder,
                Math.round(((sellOrderVolume / sellOrdersVolume) * VOLUME_LEVEL_MAX_VALUE)).toInt(),
                getHighlightEndTimestamp(sellOrder)
            )

            priceOrderbookOrdersDataMap[buyOrderData.order.price] = buyOrderData
            priceOrderbookOrdersDataMap[sellOrderData.order.price] = sellOrderData

            mItems.add(OrderbookOrderItem(buyOrderData))
            mItems.add(OrderbookOrderItem(sellOrderData))
        }

        mPriceOrderbookOrdersDataMap = priceOrderbookOrdersDataMap
        mAdapter?.items = mItems
    }


    override fun clearData() {
        super.clearData()

        mPriceOrderbookOrdersDataMap = emptyMap()
    }


    /**
     * Sets whether the more button of the header should be hidden or not.
     *
     * @param isHidden Whether to hide the more button or not
     */
    fun setShouldHideHeaderMoreButton(isHidden: Boolean) {
        mShouldHideHeaderMoreButton = isHidden
        updateResources()
    }


    /**
     * Sets a RecyclerView's horizontal spacing between items.
     *
     * @param spacing The spacing to set
     */
    fun setRecyclerViewHorizontalSpacing(spacing: Int) {
        mRvItemHorizontalSpacing = spacing
        updateRecyclerViewItemDecorator()
    }


    /**
     * Sets a limit for each order's type.
     *
     * @param limit The limit to set
     */
    fun setOrdersOfTypeCountLimit(limit: Int) {
        mOrdersOfTypeCountLimit = limit
    }


    /**
     * Sets a more button color of the header.
     *
     * @param color The color to set
     */
    fun setHeaderMoreButtonColor(@ColorInt color: Int) {
        mHeaderMoreButtonColor = color
        updateResources()
    }


    /**
     * Sets a color of the buy order's background.
     *
     * @param color The color to set
     */
    fun setBuyOrderBackgroundColor(@ColorInt color: Int) {
        mBuyOrderBackgroundColor = color
        updateResources()
    }


    /**
     * Sets a color of the sell order's background.
     *
     * @param color The color to set
     */
    fun setSellOrderBackgroundColor(@ColorInt color: Int) {
        mSellOrderBackgroundColor = color
        updateResources()
    }


    /**
     * Sets a market name of the orderbook data to load.
     *
     * @param marketName The market name to set
     */
    fun setMarketName(marketName: String) {
        mDataParameters = mDataParameters.copy(marketName = marketName)
    }


    /**
     * Sets a click listener of the more button of the header.
     *
     * @param listener The listener to set
     */
    fun setOnHeaderMoreButtonClickListener(listener: ((View, OrderbookHeaderItem, Int) -> Unit)) {
        mAdapter?.onHeaderMoreButtonClickListener = listener
    }


    override fun isDataEmpty(): Boolean {
        return (mData?.isEmpty() ?: true)
    }


    override fun getDefaultInfoViewIconResourceId(): Int {
        return R.drawable.ic_doc_with_two_columns
    }


    override fun getLayoutResourceId(): Int = R.layout.orderbook_view_layout


    override fun getDefaultParameters(): OrderbookParameters {
        return OrderbookParameters.getDefaultParameters()
    }


    override fun getLayoutManager(): LinearLayoutManager = mLayoutManager


    override fun getProgressBar(): ProgressBar = progressBar


    override fun getInfoView(): InfoView = infoView


    override fun getMainView(): View = recyclerView


    private val mLayoutManager = object : GridLayoutManager(
        context, RECYCLER_VIEW_COLUMN_COUNT, RecyclerView.VERTICAL, false
    ) {

        override fun canScrollVertically(): Boolean {
            return mCanRecyclerViewScroll
        }

    }


    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)

        if(state is SavedState) {
            setShouldHideHeaderMoreButton(state.shouldHideHeaderMoreButton)
            setRecyclerViewHorizontalSpacing(state.rvItemHorizontalSpacing)
            setOrdersOfTypeCountLimit(state.ordersOfTypeCountLimit)
            setHeaderMoreButtonColor(state.headerMoreButtonColor)
            setBuyOrderBackgroundColor(state.buyOrderBackgroundColor)
            setSellOrderBackgroundColor(state.sellOrderBackgroundColor)
            setData(state.data, true)
        }
    }


    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState()).apply {
            onSaveInstanceState(this)
        }
    }


    override fun onSaveInstanceState(savedState: BaseMarketPreviewSavedState<OrderbookParameters>) {
        super.onSaveInstanceState(savedState)

        if(savedState is SavedState) {
            with(savedState) {
                shouldHideHeaderMoreButton = mShouldHideHeaderMoreButton
                rvItemHorizontalSpacing = mRvItemHorizontalSpacing
                ordersOfTypeCountLimit = mOrdersOfTypeCountLimit
                headerMoreButtonColor = mHeaderMoreButtonColor
                buyOrderBackgroundColor = mBuyOrderBackgroundColor
                sellOrderBackgroundColor = mSellOrderBackgroundColor
                data = mData
            }
        }
    }


    private class SavedState : BaseMarketPreviewSavedState<OrderbookParameters> {

        companion object {

            private const val KEY_SHOULD_HIDE_HEADER_MORE_BUTTON = "should_hide_header_more_button"
            private const val KEY_RV_ITEM_HORIZONTAL_SPACING = "rv_item_horizontal_spacing"
            private const val KEY_ORDERS_OF_TYPE_COUNT_LIMIT = "orders_of_type_count_limit"
            private const val KEY_HEADER_MORE_BUTTON_COLOR = "header_more_button_color"
            private const val KEY_BUY_ORDER_BACKGROUND_COLOR = "buy_order_background_color"
            private const val KEY_SELL_ORDER_BACKGROUND_COLOR = "sell_order_background_color"
            private const val KEY_DATA = "data"


            @JvmField
            var CREATOR = object : Parcelable.Creator<SavedState> {

                override fun createFromParcel(parcel: Parcel): SavedState = SavedState(parcel)

                override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)

            }

        }


        internal var shouldHideHeaderMoreButton: Boolean = false

        internal var rvItemHorizontalSpacing: Int = 0

        internal var ordersOfTypeCountLimit: Int = 0

        internal var headerMoreButtonColor: Int = 0
        internal var buyOrderBackgroundColor: Int = 0
        internal var sellOrderBackgroundColor: Int = 0

        internal var data: Orderbook? = null


        constructor(parcel: Parcel): super(parcel) {
            parcel.readBundle(CLASS_LOADER)?.run {
                shouldHideHeaderMoreButton = getBoolean(KEY_SHOULD_HIDE_HEADER_MORE_BUTTON, DEFAULT_SHOULD_HIDE_HEADER_MORE_BUTTON)
                rvItemHorizontalSpacing = getInt(KEY_RV_ITEM_HORIZONTAL_SPACING, DEFAULT_RV_ITEM_HORIZONTAL_SPACING_IN_DP)
                ordersOfTypeCountLimit = getInt(KEY_ORDERS_OF_TYPE_COUNT_LIMIT, DEFAULT_ORDERS_OF_TYPE_COUNT_LIMIT)
                headerMoreButtonColor = getInt(KEY_HEADER_MORE_BUTTON_COLOR, DEFAULT_HEADER_MORE_BUTTON_COLOR)
                buyOrderBackgroundColor = getInt(KEY_BUY_ORDER_BACKGROUND_COLOR, DEFAULT_ORDER_BACKGROUND_COLOR)
                sellOrderBackgroundColor = getInt(KEY_SELL_ORDER_BACKGROUND_COLOR, DEFAULT_ORDER_BACKGROUND_COLOR)
                data = getParcelable(KEY_DATA)
            }
        }


        constructor(superState: Parcelable?): super(superState)


        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)

            val bundle = Bundle(CLASS_LOADER).apply {
                putBoolean(KEY_SHOULD_HIDE_HEADER_MORE_BUTTON, shouldHideHeaderMoreButton)
                putInt(KEY_RV_ITEM_HORIZONTAL_SPACING, rvItemHorizontalSpacing)
                putInt(KEY_ORDERS_OF_TYPE_COUNT_LIMIT, ordersOfTypeCountLimit)
                putInt(KEY_HEADER_MORE_BUTTON_COLOR, headerMoreButtonColor)
                putInt(KEY_BUY_ORDER_BACKGROUND_COLOR, buyOrderBackgroundColor)
                putInt(KEY_SELL_ORDER_BACKGROUND_COLOR, sellOrderBackgroundColor)
                putParcelable(KEY_DATA, data)
            }

            parcel.writeBundle(bundle)
        }

    }


}
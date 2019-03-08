package com.stocksexchange.android.ui.views.marketpreview.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.recyclerview.BaseRecyclerViewAdapter
import com.stocksexchange.android.ui.views.marketpreview.base.interfaces.BaseMarketPreviewListData
import com.stocksexchange.android.model.DataActionItem
import com.stocksexchange.android.ui.utils.extensions.dpToPx

/**
 * A base market preview list view to extend from to create custom views
 * with a RecyclerView in them.
 */
abstract class BaseMarketPreviewListView<
    Data : Any,
    DataItem : Any,
    Params: Parcelable,
    Adapter: BaseRecyclerViewAdapter<*, *>
> @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseMarketPreviewView<Data, DataItem, Params>(context, attrs, defStyleAttr) {


    companion object {

        internal const val ATTRIBUTE_KEY_IS_HIGHLIGHTING_ENABLED = "is_highlighting_enabled"
        internal const val ATTRIBUTE_KEY_RV_HEIGHT = "rv_height"
        internal const val ATTRIBUTE_KEY_RV_ITEM_BOTTOM_SPACING = "rv_item_bottom_spacing"
        internal const val ATTRIBUTE_KEY_PRICE_MAX_CHARS_LENGTH = "price_max_chars_length"
        internal const val ATTRIBUTE_KEY_AMOUNT_MAX_CHARS_LENGTH = "amount_max_chars_length"
        internal const val ATTRIBUTE_KEY_HEADER_TITLE_TEXT_COLOR = "header_title_text_color"
        internal const val ATTRIBUTE_KEY_HEADER_SEPARATOR_COLOR = "header_separator_color"
        internal const val ATTRIBUTE_KEY_BUY_HIGHLIGHT_BACKGROUND_COLOR = "buy_highlight_background_color"
        internal const val ATTRIBUTE_KEY_SELL_HIGHLIGHT_BACKGROUND_COLOR = "sell_highlight_background_color"
        internal const val ATTRIBUTE_KEY_BUY_PRICE_HIGHLIGHT_COLOR = "buy_price_highlight_color"
        internal const val ATTRIBUTE_KEY_SELL_PRICE_HIGHLIGHT_COLOR = "sell_price_highlight_color"
        internal const val ATTRIBUTE_KEY_BUY_PRICE_COLOR = "buy_price_color"
        internal const val ATTRIBUTE_KEY_SELL_PRICE_COLOR = "sell_price_color"
        internal const val ATTRIBUTE_KEY_AMOUNT_COLOR = "amount_color"
        internal const val ATTRIBUTE_KEY_HIGHLIGHT_DURATION = "highlight_duration"

        internal const val DEFAULT_IS_HIGHLIGHTING_ENABLED = true

        internal const val DEFAULT_RV_HEIGHT = ViewGroup.LayoutParams.WRAP_CONTENT
        internal const val DEFAULT_RV_ITEM_BOTTOM_SPACING_IN_DP = 1

        internal const val DEFAULT_PRICE_MAX_CHARS_LENGTH = 10
        internal const val DEFAULT_AMOUNT_MAX_CHARS_LENGTH = 10

        internal const val DEFAULT_HEADER_TITLE_TEXT_COLOR = Color.GRAY
        internal const val DEFAULT_HEADER_SEPARATOR_COLOR = Color.LTGRAY
        internal const val DEFAULT_BUY_HIGHLIGHT_BACKGROUND_COLOR = Color.GREEN
        internal const val DEFAULT_SELL_HIGHLIGHT_BACKROUND_COLOR = Color.RED
        internal const val DEFAULT_BUY_PRICE_HIGHLIGHT_COLOR = Color.GREEN
        internal const val DEFAULT_SELL_PRICE_HIGHLIGHT_COLOR = Color.RED
        internal const val DEFAULT_BUY_PRICE_COLOR = Color.GREEN
        internal const val DEFAULT_SELL_PRICE_COLOR = Color.RED
        internal const val DEFAULT_AMOUNT_COLOR = Color.WHITE

        internal const val DEFAULT_HIGHLIGHT_DURATION = 2000

        internal const val DEFAULT_STUB_TEXT = "- -"

    }


    protected var mIsHighlightingEnabled: Boolean = true
    protected var mCanRecyclerViewScroll: Boolean = true

    protected var mRvHeight: Int = 0
    protected var mRvItemBottomSpacing: Int = 0

    protected var mPriceMaxCharsLength: Int = 0
    protected var mAmountMaxCharsLength: Int = 0

    protected var mHeaderTitleTextColor: Int = 0
    protected var mHeaderSeparatorColor: Int = 0
    protected var mBuyBackgroundHighlightColor: Int = 0
    protected var mSellBackgroundHighlightColor: Int = 0
    protected var mBuyPriceHighlightColor: Int = 0
    protected var mSellPriceHighlightColor: Int = 0
    protected var mBuyPriceColor: Int = 0
    protected var mSellPriceColor: Int = 0
    protected var mAmountColor: Int = 0

    protected var mHighlightDuration: Int = 0

    protected var mItems: MutableList<BaseItem<*, *, *>> = mutableListOf()

    protected var mAdapter: Adapter? = null




    override fun init() {
        super.init()

        initRecyclerView()
    }


    protected abstract fun initRecyclerView()


    override fun restoreAttributes() {
        super.restoreAttributes()

        with(mAttributes) {
            mIsHighlightingEnabled = get(ATTRIBUTE_KEY_IS_HIGHLIGHTING_ENABLED, DEFAULT_IS_HIGHLIGHTING_ENABLED)
            mRvHeight = get(ATTRIBUTE_KEY_RV_HEIGHT, DEFAULT_RV_HEIGHT)
            mRvItemBottomSpacing = get(ATTRIBUTE_KEY_RV_ITEM_BOTTOM_SPACING, dpToPx(DEFAULT_RV_ITEM_BOTTOM_SPACING_IN_DP))
            mPriceMaxCharsLength = get(ATTRIBUTE_KEY_PRICE_MAX_CHARS_LENGTH, DEFAULT_PRICE_MAX_CHARS_LENGTH)
            mAmountMaxCharsLength = get(ATTRIBUTE_KEY_AMOUNT_MAX_CHARS_LENGTH, DEFAULT_AMOUNT_MAX_CHARS_LENGTH)
            mHeaderTitleTextColor = get(ATTRIBUTE_KEY_HEADER_TITLE_TEXT_COLOR, DEFAULT_HEADER_TITLE_TEXT_COLOR)
            mHeaderSeparatorColor = get(ATTRIBUTE_KEY_HEADER_SEPARATOR_COLOR, DEFAULT_HEADER_SEPARATOR_COLOR)
            mBuyBackgroundHighlightColor = get(ATTRIBUTE_KEY_BUY_HIGHLIGHT_BACKGROUND_COLOR, DEFAULT_BUY_HIGHLIGHT_BACKGROUND_COLOR)
            mSellBackgroundHighlightColor = get(ATTRIBUTE_KEY_SELL_HIGHLIGHT_BACKGROUND_COLOR, DEFAULT_SELL_HIGHLIGHT_BACKROUND_COLOR)
            mBuyPriceHighlightColor = get(ATTRIBUTE_KEY_BUY_PRICE_HIGHLIGHT_COLOR, DEFAULT_BUY_PRICE_HIGHLIGHT_COLOR)
            mSellPriceHighlightColor = get(ATTRIBUTE_KEY_SELL_PRICE_HIGHLIGHT_COLOR, DEFAULT_SELL_PRICE_HIGHLIGHT_COLOR)
            mBuyPriceColor = get(ATTRIBUTE_KEY_BUY_PRICE_COLOR, DEFAULT_BUY_PRICE_COLOR)
            mSellPriceColor = get(ATTRIBUTE_KEY_SELL_PRICE_COLOR, DEFAULT_SELL_PRICE_COLOR)
            mAmountColor = get(ATTRIBUTE_KEY_AMOUNT_COLOR, DEFAULT_AMOUNT_COLOR)
            mHighlightDuration = get(ATTRIBUTE_KEY_HIGHLIGHT_DURATION, DEFAULT_HIGHLIGHT_DURATION)
        }
    }


    override fun applyAttributes() {
        super.applyAttributes()

        setHighlightingEnabled(mIsHighlightingEnabled)
        setRecyclerViewHeight(mRvHeight)
        setRecyclerViewItemBottomSpacing(mRvItemBottomSpacing)
        setPriceMaxCharsLength(mPriceMaxCharsLength)
        setAmountMaxCharsLength(mAmountMaxCharsLength)
        setHeaderTitleTextColor(mHeaderTitleTextColor)
        setHeaderSeparatorColor(mHeaderSeparatorColor)
        setBuyHighlightBackgroundColor(mBuyBackgroundHighlightColor)
        setSellHighlightBackgroundColor(mSellBackgroundHighlightColor)
        setBuyPriceHighlightColor(mBuyPriceHighlightColor)
        setSellPriceHighlightColor(mSellPriceHighlightColor)
        setBuyPriceColor(mBuyPriceColor)
        setSellPriceColor(mSellPriceColor)
        setAmountColor(mAmountColor)
        setHighlightDuration(mHighlightDuration)
    }


    /**
     * Scrolls itself to the top.
     */
    fun scrollToTop() {
        (getMainView() as RecyclerView).scrollToPosition(0)
    }


    /**
     * Sets whether or not the highlighting of the items is enabled or not.
     *
     * @param isHighlightingEnabled Whether the highlighting is enabled or not
     */
    fun setHighlightingEnabled(isHighlightingEnabled: Boolean) {
        mIsHighlightingEnabled = isHighlightingEnabled
    }


    /**
     * Sets a height of the recycler view. Can be either one
     * of the [ViewGroup.LayoutParams] constants or a specific height.
     *
     * @param height The height to set
     */
    fun setRecyclerViewHeight(height: Int) {
        (getMainView() as RecyclerView).layoutParams.height = height

        mRvHeight = height
        mCanRecyclerViewScroll = (
            (height  == ViewGroup.LayoutParams.MATCH_PARENT) ||
            (height == ViewGroup.LayoutParams.WRAP_CONTENT)
        )
    }


    /**
     * Sets a RecyclerView's bottom spacing for each item.
     *
     * @param spacing The spacing to set
     */
    fun setRecyclerViewItemBottomSpacing(spacing: Int) {
        mRvItemBottomSpacing = spacing
        updateRecyclerViewItemDecorator()
    }


    /**
     * Should update recycler view's item decorator (if necessary).
     */
    protected open fun updateRecyclerViewItemDecorator() {
        // Stub
    }


    /**
     * Sets a maximum characters length for a price.
     *
     * @param priceMaxCharsLength The max length to set
     */
    fun setPriceMaxCharsLength(priceMaxCharsLength: Int) {
        mPriceMaxCharsLength = priceMaxCharsLength
        updateResources()
    }


    /**
     * Sets a maximum characters length for an amount.
     *
     * @param amountMaxCharsLength The max length to set
     */
    fun setAmountMaxCharsLength(amountMaxCharsLength: Int) {
        mAmountMaxCharsLength = amountMaxCharsLength
        updateResources()
    }


    /**
     * Should update the resources of the recycler view's adapter.
     */
    protected abstract fun updateResources()


    /**
     * Sets a text color of the header title.
     *
     * @param color The color to set
     */
    fun setHeaderTitleTextColor(@ColorInt color: Int) {
        mHeaderTitleTextColor = color
        updateResources()
    }


    /**
     * Sets a color of the header separator.
     *
     * @param color The color to set
     */
    fun setHeaderSeparatorColor(@ColorInt color: Int) {
        mHeaderSeparatorColor = color
        updateResources()
    }


    /**
     * Sets a color of the buy order's background highlight.
     *
     * @param color The color to set
     */
    fun setBuyHighlightBackgroundColor(@ColorInt color: Int) {
        mBuyBackgroundHighlightColor = color
        updateResources()
    }


    /**
     * Sets a color of the sell order's background highlight.
     *
     * @param color The color to set
     */
    fun setSellHighlightBackgroundColor(@ColorInt color: Int) {
        mSellBackgroundHighlightColor = color
        updateResources()
    }


    /**
     * Sets a color of the price of a buy order when it is highlighted.
     *
     * @param color The color to set
     */
    fun setBuyPriceHighlightColor(@ColorInt color: Int) {
        mBuyPriceHighlightColor = color
        updateResources()
    }


    /**
     * Sets a color of the price of a sell order when it is highlighted.
     *
     * @param color The color to set
     */
    fun setSellPriceHighlightColor(@ColorInt color: Int) {
        mSellPriceHighlightColor = color
        updateResources()
    }


    /**
     * Sets a color of the buy order's price.
     *
     * @param color The color to set
     */
    fun setBuyPriceColor(@ColorInt color: Int) {
        mBuyPriceColor = color
        updateResources()
    }


    /**
     * Sets a color of the sell order's price.
     *
     * @param color The color to set
     */
    fun setSellPriceColor(@ColorInt color: Int) {
        mSellPriceColor = color
        updateResources()
    }


    /**
     * Sets a color of the order's amount.
     *
     * @param color The color to set
     */
    fun setAmountColor(@ColorInt color: Int) {
        mAmountColor = color
        updateResources()
    }


    /**
     * Sets a duration of the highlight of the item.
     *
     * @param highlightDuration The duration to set
     */
    fun setHighlightDuration(highlightDuration: Int) {
        mHighlightDuration = highlightDuration
    }


    override fun updateData(data: Data, dataActionItems: List<DataActionItem<DataItem>>) {
        setData(data, false)
    }


    override fun bindData() {
        bindDataInternal {
            BaseMarketPreviewListData.NO_TIMESTAMP
        }
    }


    /**
     * An internal method used for binding data.
     *
     * @param getHighlightEndTimestamp The method to retrieve an ending
     * timestamp of when the highlighting should stop
     */
    protected abstract fun bindDataInternal(getHighlightEndTimestamp: (DataItem) -> Long)


    override fun clearData() {
        super.clearData()

        mAdapter?.clear()
        mItems.clear()
    }


    override fun isDataAlreadyBound(): Boolean {
        return mItems.isNotEmpty()
    }


    /**
     * Should return a layout manager for the RecyclerView.
     */
    abstract fun getLayoutManager(): LinearLayoutManager


    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)

        if(state is BaseMarketPreviewListBasedState<*>) {
            setHighlightingEnabled(state.isHighlightingEnabled)
            setRecyclerViewHeight(state.rvHeight)
            setRecyclerViewItemBottomSpacing(state.rvItemBottomSpacing)
            setPriceMaxCharsLength(state.priceMaxCharsLength)
            setAmountMaxCharsLength(state.amountMaxCharsLength)
            setHeaderTitleTextColor(state.headerTitleTextColor)
            setHeaderSeparatorColor(state.headerSeparatorColor)
            setBuyHighlightBackgroundColor(state.buyHighlightBackgroundColor)
            setSellHighlightBackgroundColor(state.sellHighlightBackgroundColor)
            setBuyPriceHighlightColor(state.buyPriceHighlightColor)
            setSellPriceHighlightColor(state.sellPriceHighlightColor)
            setBuyPriceColor(state.buyPriceColor)
            setSellPriceColor(state.sellPriceColor)
            setAmountColor(state.amountColor)
            setHighlightDuration(state.highlightDuration)
        }
    }


    override fun onSaveInstanceState(savedState: BaseMarketPreviewSavedState<Params>) {
        super.onSaveInstanceState(savedState)

        if(savedState is BaseMarketPreviewListBasedState) {
            with(savedState) {
                isHighlightingEnabled = mIsHighlightingEnabled
                rvHeight = mRvHeight
                rvItemBottomSpacing = mRvItemBottomSpacing
                priceMaxCharsLength = mPriceMaxCharsLength
                amountMaxCharsLength = mAmountMaxCharsLength
                headerTitleTextColor = mHeaderTitleTextColor
                headerSeparatorColor = mHeaderSeparatorColor
                buyHighlightBackgroundColor = mBuyBackgroundHighlightColor
                sellHighlightBackgroundColor = mSellBackgroundHighlightColor
                buyPriceHighlightColor = mBuyPriceHighlightColor
                sellPriceHighlightColor = mSellPriceHighlightColor
                buyPriceColor = mBuyPriceColor
                sellPriceColor = mSellPriceColor
                amountColor = mAmountColor
                highlightDuration = mHighlightDuration
            }
        }
    }


    abstract class BaseMarketPreviewListBasedState<Params: Parcelable> : BaseMarketPreviewSavedState<Params> {

        companion object {

            private const val KEY_IS_HIGHLIGHTING_ENABLED = "is_highlighting_enabled"
            private const val KEY_RV_HEIGHT = "rv_height"
            private const val KEY_RV_ITEM_BOTTOM_SPACING = "rv_item_bottom_spacing"
            private const val KEY_PRICE_MAX_CHARS_LENGTH = "price_max_chars_length"
            private const val KEY_AMOUNT_MAX_CHARS_LENGTH = "amount_max_chars_length"
            private const val KEY_HEADER_TITLE_TEXT_COLOR = "header_title_text_color"
            private const val KEY_HEADER_SEPARATOR_COLOR = "header_separator_color"
            private const val KEY_BUY_HIGHLIGHT_BACKGROUND_COLOR = "buy_highlight_background_color"
            private const val KEY_SELL_HIGHLIGHT_BACKGROUND_COLOR = "sell_highlight_background_color"
            private const val KEY_BUY_PRICE_HIGHLIGHT_COLOR = "buy_price_highlight_color"
            private const val KEY_SELL_PRICE_HIGHLIGHT_COLOR = "sell_price_highlight_color"
            private const val KEY_BUY_PRICE_COLOR = "buy_price_color"
            private const val KEY_SELL_PRICE_COLOR = "sell_price_color"
            private const val KEY_AMOUNT_COLOR = "amount_color"
            private const val KEY_HIGHLIGHT_DURATION = "highlight_duration"

        }


        internal var isHighlightingEnabled: Boolean = true

        internal var rvHeight: Int = 0
        internal var rvItemBottomSpacing: Int = 0

        internal var priceMaxCharsLength: Int = 0
        internal var amountMaxCharsLength: Int = 0

        internal var headerTitleTextColor: Int = 0
        internal var headerSeparatorColor: Int = 0
        internal var buyHighlightBackgroundColor: Int = 0
        internal var sellHighlightBackgroundColor: Int = 0
        internal var buyPriceHighlightColor: Int = 0
        internal var sellPriceHighlightColor: Int = 0
        internal var buyPriceColor: Int = 0
        internal var sellPriceColor: Int = 0
        internal var amountColor: Int = 0

        internal var highlightDuration: Int = 0


        constructor(parcel: Parcel): super(parcel) {
            parcel.readBundle(CLASS_LOADER)?.run {
                isHighlightingEnabled = getBoolean(KEY_IS_HIGHLIGHTING_ENABLED, DEFAULT_IS_HIGHLIGHTING_ENABLED)
                rvHeight = getInt(KEY_RV_HEIGHT, DEFAULT_RV_HEIGHT)
                rvItemBottomSpacing = getInt(KEY_RV_ITEM_BOTTOM_SPACING, DEFAULT_RV_ITEM_BOTTOM_SPACING_IN_DP)
                priceMaxCharsLength = getInt(KEY_PRICE_MAX_CHARS_LENGTH, DEFAULT_PRICE_MAX_CHARS_LENGTH)
                amountMaxCharsLength = getInt(KEY_AMOUNT_MAX_CHARS_LENGTH, DEFAULT_AMOUNT_MAX_CHARS_LENGTH)
                headerTitleTextColor = getInt(KEY_HEADER_TITLE_TEXT_COLOR, DEFAULT_HEADER_TITLE_TEXT_COLOR)
                headerSeparatorColor = getInt(KEY_HEADER_SEPARATOR_COLOR, DEFAULT_HEADER_SEPARATOR_COLOR)
                buyHighlightBackgroundColor = getInt(KEY_BUY_HIGHLIGHT_BACKGROUND_COLOR, DEFAULT_BUY_HIGHLIGHT_BACKGROUND_COLOR)
                sellHighlightBackgroundColor = getInt(KEY_SELL_HIGHLIGHT_BACKGROUND_COLOR, DEFAULT_SELL_HIGHLIGHT_BACKROUND_COLOR)
                buyPriceHighlightColor = getInt(KEY_BUY_PRICE_HIGHLIGHT_COLOR, DEFAULT_BUY_PRICE_HIGHLIGHT_COLOR)
                sellPriceHighlightColor = getInt(KEY_SELL_PRICE_HIGHLIGHT_COLOR, DEFAULT_SELL_PRICE_HIGHLIGHT_COLOR)
                buyPriceColor = getInt(KEY_BUY_PRICE_COLOR, DEFAULT_BUY_PRICE_COLOR)
                sellPriceColor = getInt(KEY_SELL_PRICE_COLOR, DEFAULT_SELL_PRICE_COLOR)
                amountColor = getInt(KEY_AMOUNT_COLOR, DEFAULT_AMOUNT_COLOR)
                highlightDuration = getInt(KEY_HIGHLIGHT_DURATION, DEFAULT_HIGHLIGHT_DURATION)
            }
        }


        constructor(superState: Parcelable?): super(superState)


        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)

            val bundle = Bundle(CLASS_LOADER).apply {
                putBoolean(KEY_IS_HIGHLIGHTING_ENABLED, isHighlightingEnabled)
                putInt(KEY_RV_HEIGHT, rvHeight)
                putInt(KEY_RV_ITEM_BOTTOM_SPACING, rvItemBottomSpacing)
                putInt(KEY_PRICE_MAX_CHARS_LENGTH, priceMaxCharsLength)
                putInt(KEY_AMOUNT_MAX_CHARS_LENGTH, amountMaxCharsLength)
                putInt(KEY_HEADER_TITLE_TEXT_COLOR, headerTitleTextColor)
                putInt(KEY_HEADER_SEPARATOR_COLOR, headerSeparatorColor)
                putInt(KEY_BUY_HIGHLIGHT_BACKGROUND_COLOR, buyHighlightBackgroundColor)
                putInt(KEY_SELL_HIGHLIGHT_BACKGROUND_COLOR, sellHighlightBackgroundColor)
                putInt(KEY_BUY_PRICE_HIGHLIGHT_COLOR, buyPriceHighlightColor)
                putInt(KEY_SELL_PRICE_HIGHLIGHT_COLOR, sellPriceHighlightColor)
                putInt(KEY_BUY_PRICE_COLOR, buyPriceColor)
                putInt(KEY_SELL_PRICE_COLOR, sellPriceColor)
                putInt(KEY_AMOUNT_COLOR, amountColor)
                putInt(KEY_HIGHLIGHT_DURATION, highlightDuration)
            }

            parcel.writeBundle(bundle)
        }

    }


}
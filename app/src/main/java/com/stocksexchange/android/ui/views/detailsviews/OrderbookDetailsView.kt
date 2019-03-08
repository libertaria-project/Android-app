package com.stocksexchange.android.ui.views.detailsviews

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorInt
import com.stocksexchange.android.R
import com.stocksexchange.android.model.OrderbookInfo
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.views.DottedMapView
import com.stocksexchange.android.ui.views.detailsviews.base.BaseDetailsView
import com.stocksexchange.android.utils.formatters.DoubleFormatter
import kotlinx.android.synthetic.main.orderbook_info_view_layout.view.*

/**
 * An info view representing details about a specific orderbook.
 */
class OrderbookDetailsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseDetailsView<OrderbookInfo>(context, attrs, defStyleAttr) {


    companion object {

        internal const val ATTRIBUTE_KEY_HIGHEST_BID_VALUE_COLOR = "highest_bid_value_color"
        internal const val ATTRIBUTE_KEY_LOWEST_ASK_VALUE_COLOR = "lowest_ask_value_color"
        internal const val ATTRIBUTE_KEY_BASE_CURRENCY_SYMBOL = "base_currency_symbol"
        internal const val ATTRIBUTE_KEY_EMPTY_PRICE_TEXT = "empty_price_text"
        internal const val ATTRIBUTE_KEY_EMPTY_VOLUME_TEXT = "empty_volume_text"

        private const val DEFAULT_HIGHEST_BID_VALUE_COLOR = Color.GREEN
        private const val DEFAULT_LOWEST_ASK_VALUE_COLOR = Color.RED

        private const val DEFAULT_BASE_CURRENCY_SYMBOL = ""
        private const val DEFAULT_EMPTY_PRICE_TEXT = ""
        private const val DEFAULT_EMPTY_VOLUME_TEXT = ""

    }


    private var mHighestBidValueColor: Int = 0
    private var mLowestAskValueColor: Int = 0

    private var mBaseCurrencySymbol: String = ""
    private var mEmptyPriceText: String = ""
    private var mEmptyVolumeText: String = ""

    private val mDoubleFormatter: DoubleFormatter = DoubleFormatter.getInstance(context.getLocale())




    override fun saveAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.OrderbookDetailsView, defStyleAttr, 0).run {
            try {
                with(mAttributes) {
                    save(ATTRIBUTE_KEY_ITEM_TITLE_COLOR, getInt(R.styleable.OrderbookDetailsView_itemTitleColor, DEFAULT_ITEM_TITLE_COLOR))
                    save(ATTRIBUTE_KEY_ITEM_VALUE_COLOR, getInt(R.styleable.OrderbookDetailsView_itemValueColor, DEFAULT_ITEM_VALUE_COLOR))
                    save(ATTRIBUTE_KEY_ITEM_SEPARATOR_COLOR, getInt(R.styleable.OrderbookDetailsView_itemSeparatorColor, DEFAULT_ITEM_SEPARATOR_COLOR))
                    save(ATTRIBUTE_KEY_HIGHEST_BID_VALUE_COLOR, getInt(R.styleable.OrderbookDetailsView_highestBidValueColor, DEFAULT_HIGHEST_BID_VALUE_COLOR))
                    save(ATTRIBUTE_KEY_LOWEST_ASK_VALUE_COLOR, getInt(R.styleable.OrderbookDetailsView_lowestAskValueColor, DEFAULT_LOWEST_ASK_VALUE_COLOR))
                    save(ATTRIBUTE_KEY_PROGRESS_BAR_COLOR, getInt(R.styleable.OrderbookDetailsView_progressBarColor, DEFAULT_PROGRESS_BAR_COLOR))
                    save(ATTRIBUTE_KEY_INFO_VIEW_CAPTION_COLOR, getInt(R.styleable.OrderbookDetailsView_infoViewCaptionColor, DEFAULT_INFO_VIEW_CAPTION_COLOR))
                    save(ATTRIBUTE_KEY_BASE_CURRENCY_SYMBOL, getString(R.styleable.OrderbookDetailsView_baseCurrencySymbol) ?: DEFAULT_BASE_CURRENCY_SYMBOL)
                    save(ATTRIBUTE_KEY_EMPTY_PRICE_TEXT, getString(R.styleable.OrderbookDetailsView_emptyPriceText) ?: DEFAULT_EMPTY_PRICE_TEXT)
                    save(ATTRIBUTE_KEY_EMPTY_VOLUME_TEXT, getString(R.styleable.OrderbookDetailsView_emptyVolumeText) ?: DEFAULT_EMPTY_VOLUME_TEXT)
                    save(ATTRIBUTE_KEY_INFO_VIEW_EMPTY_CAPTION, getString(R.styleable.OrderbookDetailsView_infoViewEmptyCaption) ?: DEFAULT_INFO_VIEW_CAPTION)
                    save(ATTRIBUTE_KEY_INFO_VIEW_ERROR_CAPTION, getString(R.styleable.OrderbookDetailsView_infoViewErrorCaption) ?: DEFAULT_INFO_VIEW_CAPTION)
                }
            } finally {
                recycle()
            }
        }
    }


    override fun restoreAttributes() {
        super.restoreAttributes()

        with(mAttributes) {
            mHighestBidValueColor = get(ATTRIBUTE_KEY_HIGHEST_BID_VALUE_COLOR, DEFAULT_HIGHEST_BID_VALUE_COLOR)
            mLowestAskValueColor = get(ATTRIBUTE_KEY_LOWEST_ASK_VALUE_COLOR, DEFAULT_LOWEST_ASK_VALUE_COLOR)
            mBaseCurrencySymbol = get(ATTRIBUTE_KEY_BASE_CURRENCY_SYMBOL, DEFAULT_BASE_CURRENCY_SYMBOL)
            mEmptyPriceText = get(ATTRIBUTE_KEY_EMPTY_PRICE_TEXT, DEFAULT_EMPTY_PRICE_TEXT)
            mEmptyVolumeText = get(ATTRIBUTE_KEY_EMPTY_VOLUME_TEXT, DEFAULT_EMPTY_VOLUME_TEXT)
        }
    }


    override fun applyAttributes() {
        super.applyAttributes()

        setHighestBidValueColor(mHighestBidValueColor)
        setLowestAskValueColor(mLowestAskValueColor)
        setBaseCurrencySymbol(mBaseCurrencySymbol)
        setEmptyPriceText(mEmptyPriceText)
        setEmptyVolumeText(mEmptyVolumeText)
    }


    /**
     * Sets a value color of the highest bid.
     *
     * @param color The color to set
     */
    fun setHighestBidValueColor(@ColorInt color: Int) {
        mHighestBidValueColor = color

        highestBidDmv.setValueColor(color)
    }


    /**
     * Sets a value color of the lowest ask.
     *
     * @param color The color to set
     */
    fun setLowestAskValueColor(@ColorInt color: Int) {
        mLowestAskValueColor = color

        lowestAskDmv.setValueColor(color)
    }


    /**
     * Sets a base currency symbol.
     *
     * @param symbol The base currency symbol
     */
    fun setBaseCurrencySymbol(symbol: String) {
        mBaseCurrencySymbol = symbol
    }


    /**
     * Sets a text to use when the value of the price is empty.
     *
     * @param text The text to set
     */
    fun setEmptyPriceText(text: String) {
        mEmptyPriceText = text
    }


    /**
     * Sets a text to use when the value of the volume is empty.
     *
     * @param text The text to set
     */
    fun setEmptyVolumeText(text: String) {
        mEmptyVolumeText = text
    }


    override fun updateData(data: OrderbookInfo) {
        if(isDataEmpty()) {
            setData(data, true)
            return
        }

        val oldData = mData!!
        setData(data, false)

        if(data.highestBid != oldData.highestBid) {
            highestBidDmv.setValueTextAnimated(getHighestBidString(data))
        }

        if(data.lowestAsk != oldData.lowestAsk) {
            lowestAskDmv.setValueTextAnimated(getLowestAskString(data))
        }

        if(data.buyVolume != oldData.buyVolume) {
            buyVolumeDmv.setValueTextAnimated(getBuyVolumeString(data))
        }

        if(data.sellVolume != oldData.sellVolume) {
            sellVolumeDmv.setValueTextAnimated(getSellVolumeString(data))
        }
    }


    override fun bindData() {
        if(isDataEmpty()) {
            return
        }

        val data = mData!!

        highestBidDmv.setValueText(getHighestBidString(data))
        lowestAskDmv.setValueText(getLowestAskString(data))
        buyVolumeDmv.setValueText(getBuyVolumeString(data))
        sellVolumeDmv.setValueText(getSellVolumeString(data))
    }


    override fun hasProgressBar(): Boolean = true


    override fun hasInfoView(): Boolean = true


    private fun getHighestBidString(data: OrderbookInfo): String {
        return if(data.hasHighestBid()) {
            mDoubleFormatter.formatFixedPrice(data.highestBid)
        } else {
            mEmptyPriceText
        }
    }


    private fun getLowestAskString(data: OrderbookInfo): String {
        return if(data.hasLowestAsk()) {
            mDoubleFormatter.formatFixedPrice(data.lowestAsk)
        } else {
            mEmptyPriceText
        }
    }


    private fun getBuyVolumeString(data: OrderbookInfo): String {
        return if(data.hasBuyVolume()) {
            "${mDoubleFormatter.formatAmount(data.buyVolume)} $mBaseCurrencySymbol"
        } else {
            mEmptyVolumeText
        }
    }


    private fun getSellVolumeString(data: OrderbookInfo): String {
        return if(data.hasSellVolume()) {
            "${mDoubleFormatter.formatAmount(data.sellVolume)} $mBaseCurrencySymbol"
        } else {
            mEmptyVolumeText
        }
    }


    override fun getLayoutResourceId(): Int = R.layout.orderbook_info_view_layout


    override fun getProgressBar(): ProgressBar? = progressBar


    override fun getInfoView(): TextView = infoViewTv


    override fun getMainView(): View = mainContainerLl


    override fun getDottedMapViewsArray(): Array<DottedMapView> {
        return arrayOf(
            highestBidDmv,
            lowestAskDmv,
            buyVolumeDmv,
            sellVolumeDmv
        )
    }


    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)

        if(state is SavedState) {
            setHighestBidValueColor(state.highestBidValueColor)
            setLowestAskValueColor(state.lowestAskValueColor)
            setBaseCurrencySymbol(state.baseCurrencySymbol)
            setEmptyPriceText(state.emptyPriceText)
            setEmptyVolumeText(state.emptyVolumeText)
        }
    }


    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState()).apply {
            onSaveInstanceState(this)
        }
    }


    override fun onSaveInstanceState(savedState: BaseDetailsSavedState<OrderbookInfo>) {
        super.onSaveInstanceState(savedState)

        if(savedState is SavedState) {
            with(savedState) {
                highestBidValueColor = mHighestBidValueColor
                lowestAskValueColor = mLowestAskValueColor
                baseCurrencySymbol = mBaseCurrencySymbol
                emptyPriceText = mEmptyPriceText
                emptyVolumeText = mEmptyVolumeText
            }
        }
    }


    private class SavedState : BaseDetailsSavedState<OrderbookInfo> {

        companion object {

            private const val KEY_HIGHEST_BID_VALUE_COLOR = "highest_bid_value_color"
            private const val KEY_LOWEST_ASK_VALUE_COLOR = "lowest_ask_value_color"
            private const val KEY_BASE_CURRENCY_SYMBOL = "base_currency_symbol"
            private const val KEY_EMPTY_PRICE_TEXT = "empty_price_text"
            private const val KEY_EMPTY_VOLUME_TEXT = "empty_volume_text"


            @JvmField
            var CREATOR : Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {

                override fun createFromParcel(parcel: Parcel): SavedState {
                    return SavedState(parcel)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }

            }

        }


        internal var highestBidValueColor: Int = 0
        internal var lowestAskValueColor: Int = 0

        internal var baseCurrencySymbol: String = ""
        internal var emptyPriceText: String = ""
        internal var emptyVolumeText: String = ""


        constructor(parcel: Parcel): super(parcel) {
            parcel.readBundle(CLASS_LOADER)?.run {
                highestBidValueColor = getInt(KEY_HIGHEST_BID_VALUE_COLOR, DEFAULT_HIGHEST_BID_VALUE_COLOR)
                lowestAskValueColor = getInt(KEY_LOWEST_ASK_VALUE_COLOR, DEFAULT_LOWEST_ASK_VALUE_COLOR)
                baseCurrencySymbol = getString(KEY_BASE_CURRENCY_SYMBOL, DEFAULT_BASE_CURRENCY_SYMBOL)
                emptyPriceText = getString(KEY_EMPTY_PRICE_TEXT, DEFAULT_EMPTY_PRICE_TEXT)
                emptyVolumeText = getString(KEY_EMPTY_VOLUME_TEXT, DEFAULT_EMPTY_VOLUME_TEXT)
            }
        }


        constructor(superState: Parcelable?): super(superState)


        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)

            val bundle = Bundle(CLASS_LOADER).apply {
                putInt(KEY_HIGHEST_BID_VALUE_COLOR, highestBidValueColor)
                putInt(KEY_LOWEST_ASK_VALUE_COLOR, lowestAskValueColor)
                putString(KEY_BASE_CURRENCY_SYMBOL, baseCurrencySymbol)
                putString(KEY_EMPTY_PRICE_TEXT, emptyPriceText)
                putString(KEY_EMPTY_VOLUME_TEXT, emptyVolumeText)
            }

            parcel.writeBundle(bundle)
        }

    }


}
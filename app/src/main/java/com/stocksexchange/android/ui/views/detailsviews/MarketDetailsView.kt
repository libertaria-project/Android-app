package com.stocksexchange.android.ui.views.detailsviews

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.ui.utils.extensions.getLocale
import com.stocksexchange.android.ui.views.DottedMapView
import com.stocksexchange.android.ui.views.detailsviews.base.BaseDetailsView
import com.stocksexchange.android.utils.extensions.truncate
import com.stocksexchange.android.utils.formatters.DoubleFormatter
import kotlinx.android.synthetic.main.market_info_view_layout.view.*

/**
 * An info view representing details about a specific market.
 */
class MarketDetailsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseDetailsView<CurrencyMarket>(context, attrs, defStyleAttr) {


    companion object {

        private const val ATTRIBUTE_KEY_CURRENCY_NAME_CHARACTER_LIMIT = "currency_name_character_limit"
        private const val ATTRIBUTE_KEY_POSITIVE_STATUS_COLOR = "positive_status_color"
        private const val ATTRIBUTE_KEY_NEGATIVE_STATUS_COLOR = "negative_status_color"
        private const val ATTRIBUTE_KEY_POSITIVE_STATUS_STRING = "positive_status_string"
        private const val ATTRIBUTE_KEY_NEGATIVE_STATUS_STRING = "negative_status_string"

        private const val DEFAULT_CURRENCY_NAME_CHARACTER_LIMIT = 17

        private const val DEFAULT_POSITIVE_STATUS_COLOR = Color.GREEN
        private const val DEFAULT_NEGATIVE_STATUS_COLOR = Color.RED

        private const val DEFAULT_POSITIVE_STATUS_STRING = ""
        private const val DEFAULT_NEGATIVE_STATUS_STRING = ""

    }


    private var mIsStatusActive: Boolean = false

    private var mCurrencyNameCharacterLimit: Int = 0

    private var mPositiveStatusColor: Int = 0
    private var mNegativeStatusColor: Int = 0

    private var mPositiveStatusString: String = ""
    private var mNegativeStatusString: String = ""

    private val mDoubleFormatter: DoubleFormatter = DoubleFormatter.getInstance(context.getLocale())




    override fun saveAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.MarketDetailsView, defStyleAttr, 0).run {
            try {
                with(mAttributes) {
                    save(ATTRIBUTE_KEY_CURRENCY_NAME_CHARACTER_LIMIT, getInteger(R.styleable.MarketDetailsView_currencyNameCharacterLimit, DEFAULT_CURRENCY_NAME_CHARACTER_LIMIT))
                    save(ATTRIBUTE_KEY_ITEM_TITLE_COLOR, getColor(R.styleable.MarketDetailsView_itemTitleColor, DEFAULT_ITEM_TITLE_COLOR))
                    save(ATTRIBUTE_KEY_ITEM_VALUE_COLOR, getColor(R.styleable.MarketDetailsView_itemValueColor, DEFAULT_ITEM_VALUE_COLOR))
                    save(ATTRIBUTE_KEY_ITEM_SEPARATOR_COLOR, getColor(R.styleable.MarketDetailsView_itemSeparatorColor, DEFAULT_ITEM_SEPARATOR_COLOR))
                    save(ATTRIBUTE_KEY_POSITIVE_STATUS_COLOR, getColor(R.styleable.MarketDetailsView_positiveStatusColor, DEFAULT_POSITIVE_STATUS_COLOR))
                    save(ATTRIBUTE_KEY_NEGATIVE_STATUS_COLOR, getColor(R.styleable.MarketDetailsView_negativeStatusColor, DEFAULT_NEGATIVE_STATUS_COLOR))
                    save(ATTRIBUTE_KEY_POSITIVE_STATUS_STRING, getString(R.styleable.MarketDetailsView_positiveStatusString) ?: DEFAULT_POSITIVE_STATUS_STRING)
                    save(ATTRIBUTE_KEY_NEGATIVE_STATUS_STRING, getString(R.styleable.MarketDetailsView_negativeStatusString) ?: DEFAULT_NEGATIVE_STATUS_STRING)
                }
            } finally {
                recycle()
            }
        }
    }


    override fun restoreAttributes() {
        super.restoreAttributes()

        with(mAttributes) {
            mCurrencyNameCharacterLimit = get(ATTRIBUTE_KEY_CURRENCY_NAME_CHARACTER_LIMIT, DEFAULT_CURRENCY_NAME_CHARACTER_LIMIT)
            mPositiveStatusColor = get(ATTRIBUTE_KEY_POSITIVE_STATUS_COLOR, DEFAULT_POSITIVE_STATUS_COLOR)
            mNegativeStatusColor = get(ATTRIBUTE_KEY_NEGATIVE_STATUS_COLOR, DEFAULT_NEGATIVE_STATUS_COLOR)
            mPositiveStatusString = get(ATTRIBUTE_KEY_POSITIVE_STATUS_STRING, DEFAULT_POSITIVE_STATUS_STRING)
            mNegativeStatusString = get(ATTRIBUTE_KEY_NEGATIVE_STATUS_STRING, DEFAULT_NEGATIVE_STATUS_STRING)
        }
    }


    override fun applyAttributes() {
        super.applyAttributes()

        setCurrencyNameCharacterLimit(mCurrencyNameCharacterLimit)
        setPositiveStatusColor(mPositiveStatusColor)
        setNegativeStatusColor(mNegativeStatusColor)
        setPositiveStatusString(mPositiveStatusString)
        setNegativeStatusString(mNegativeStatusString)
    }


    private fun setStatusActive(isActive: Boolean) {
        mIsStatusActive = isActive

        if(isActive) {
            statusDmv.setValueColor(mPositiveStatusColor)
        } else {
            statusDmv.setValueColor(mNegativeStatusColor)
        }
    }


    /**
     * Sets a limit of the currency name's length.
     *
     * @param limit The maximum number of characters that
     * the currency name can be
     */
    fun setCurrencyNameCharacterLimit(limit: Int) {
        mCurrencyNameCharacterLimit = limit
    }


    /**
     * Sets a positive color of the status.
     *
     * @param color The color to set
     */
    fun setPositiveStatusColor(@ColorInt color: Int) {
        mPositiveStatusColor = color

        if(mIsStatusActive) {
            statusDmv.setValueColor(color)
        }
    }


    /**
     * Sets a negative color of the status.
     *
     * @param color The color to set
     */
    fun setNegativeStatusColor(@ColorInt color: Int) {
        mNegativeStatusColor = color

        if(!mIsStatusActive) {
            statusDmv.setValueColor(color)
        }
    }


    /**
     * Sets a positive string of the status.
     *
     * @param string The string to set
     */
    fun setPositiveStatusString(string: String) {
        mPositiveStatusString = string
    }


    /**
     * Sets a negative string of the status.
     *
     * @param string The string to set
     */
    fun setNegativeStatusString(string: String) {
        mNegativeStatusString = string
    }


    override fun updateData(data: CurrencyMarket) {
        if(isDataEmpty()) {
            setData(data, true)
            return
        }

        val oldData = mData!!
        setData(data, false)

        if(data.dailyMinPrice != oldData.dailyMinPrice) {
            dailyMinPriceDmv.setValueTextAnimated(getDailyMinPriceString(data))
        }

        if(data.dailyMaxPrice != oldData.dailyMaxPrice) {
            dailyMaxPriceDmv.setValueTextAnimated(getDailyMaxPriceString(data))
        }

        if(data.dailyVolume != oldData.dailyVolume) {
            dailyVolumeDmv.setValueTextAnimated(getDailyVolumeString(data))
        }
    }


    override fun bindData() {
        if(isDataEmpty()) {
            return
        }

        val data = mData!!

        setStatusActive(data.isActive)

        statusDmv.setValueText(if(data.isActive) {
            mPositiveStatusString
        } else {
            mNegativeStatusString
        })

        if(mCurrencyNameCharacterLimit != -1) {
            baseCurrencyDmv.setValueText(data.baseCurrencyName.truncate(mCurrencyNameCharacterLimit))
            quoteCurrencyDmv.setValueText(data.quoteCurrencyName.truncate(mCurrencyNameCharacterLimit))
        } else {
            baseCurrencyDmv.setValueText(data.baseCurrencyName)
            quoteCurrencyDmv.setValueText(data.quoteCurrencyName)
        }

        dailyMinPriceDmv.setValueText(getDailyMinPriceString(data))
        dailyMaxPriceDmv.setValueText(getDailyMaxPriceString(data))
        dailyVolumeDmv.setValueText(getDailyVolumeString(data))

        buyFeeDmv.setValueText(mDoubleFormatter.formatFeePercent(data.buyFeePercent))
        sellFeeDmv.setValueText(mDoubleFormatter.formatFeePercent(data.sellFeePercent))
    }


    private fun getDailyMinPriceString(data: CurrencyMarket): String {
        return "${mDoubleFormatter.formatFixedPrice(data.dailyMinPrice)} ${data.quoteCurrencySymbol}"
    }


    private fun getDailyMaxPriceString(data: CurrencyMarket): String {
        return "${mDoubleFormatter.formatFixedPrice(data.dailyMaxPrice)} ${data.quoteCurrencySymbol}"
    }


    private fun getDailyVolumeString(data: CurrencyMarket): String {
        return "${mDoubleFormatter.formatDailyVolume(data.dailyVolume)} ${data.baseCurrencySymbol}"
    }


    override fun getLayoutResourceId(): Int = R.layout.market_info_view_layout


    override fun getMainView(): View = mainContainerLl


    override fun getDottedMapViewsArray(): Array<DottedMapView> {
        return arrayOf(
            statusDmv,
            baseCurrencyDmv,
            quoteCurrencyDmv,
            dailyMinPriceDmv,
            dailyMaxPriceDmv,
            dailyVolumeDmv,
            buyFeeDmv,
            sellFeeDmv
        )
    }


    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)

        if(state is SavedState) {
            setStatusActive(state.isStatusActive)
            setCurrencyNameCharacterLimit(state.currencyNameCharacterLimit)
            setPositiveStatusColor(state.positiveStatusColor)
            setNegativeStatusColor(state.negativeStatusColor)
            setPositiveStatusString(state.positiveStatusString)
            setNegativeStatusString(state.negativeStatusString)
        }
    }


    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState()).apply {
            onSaveInstanceState(this)
        }
    }


    override fun onSaveInstanceState(savedState: BaseDetailsSavedState<CurrencyMarket>) {
        super.onSaveInstanceState(savedState)

        if(savedState is SavedState) {
            with(savedState) {
                isStatusActive = mIsStatusActive
                currencyNameCharacterLimit = mCurrencyNameCharacterLimit
                positiveStatusColor = mPositiveStatusColor
                negativeStatusColor = mNegativeStatusColor
                positiveStatusString = mPositiveStatusString
                negativeStatusString = mNegativeStatusString
            }
        }
    }


    private class SavedState : BaseDetailsSavedState<CurrencyMarket> {

        companion object {

            private const val KEY_IS_STATUS_ACTIVE = "is_status_active"
            private const val KEY_CURRENCY_NAME_CHARACTER_LIMIT = "currency_name_character_limit"
            private const val KEY_POSITIVE_STATUS_COLOR = "positive_status_color"
            private const val KEY_NEGATIVE_STATUS_COLOR = "negative_status_color"
            private const val KEY_POSITIVE_STATUS_STRING = "positive_status_string"
            private const val KEY_NEGATIVE_STATUS_STRING = "negative_status_string"


            @JvmField
            var CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {

                override fun createFromParcel(parcel: Parcel): SavedState {
                    return SavedState(parcel)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }

            }

        }


        internal var isStatusActive: Boolean = false

        internal var currencyNameCharacterLimit: Int = 0

        internal var positiveStatusColor: Int = 0
        internal var negativeStatusColor: Int = 0

        internal var positiveStatusString: String = ""
        internal var negativeStatusString: String = ""


        constructor(parcel: Parcel): super(parcel) {
            parcel.readBundle(CLASS_LOADER)?.run {
                isStatusActive = getBoolean(KEY_IS_STATUS_ACTIVE, false)
                currencyNameCharacterLimit = getInt(KEY_CURRENCY_NAME_CHARACTER_LIMIT, DEFAULT_CURRENCY_NAME_CHARACTER_LIMIT)
                positiveStatusColor = getInt(KEY_POSITIVE_STATUS_COLOR, DEFAULT_POSITIVE_STATUS_COLOR)
                negativeStatusColor = getInt(KEY_NEGATIVE_STATUS_COLOR, DEFAULT_NEGATIVE_STATUS_COLOR)
                positiveStatusString = getString(KEY_POSITIVE_STATUS_STRING, DEFAULT_POSITIVE_STATUS_STRING)
                negativeStatusString = getString(KEY_NEGATIVE_STATUS_STRING, DEFAULT_NEGATIVE_STATUS_STRING)
            }
        }


        constructor(superState: Parcelable?): super(superState)


        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)

            val bundle = Bundle(CLASS_LOADER).apply {
                putBoolean(KEY_IS_STATUS_ACTIVE, isStatusActive)
                putInt(KEY_CURRENCY_NAME_CHARACTER_LIMIT, currencyNameCharacterLimit)
                putInt(KEY_POSITIVE_STATUS_COLOR, positiveStatusColor)
                putInt(KEY_NEGATIVE_STATUS_COLOR, negativeStatusColor)
                putString(KEY_POSITIVE_STATUS_STRING, positiveStatusString)
                putString(KEY_NEGATIVE_STATUS_STRING, negativeStatusString)
            }

            parcel.writeBundle(bundle)
        }

    }


}
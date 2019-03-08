package com.stocksexchange.android.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.stocksexchange.android.R
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator.Columns.*
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator.Companion.DAILY_PRICE_CHANGE_ASCENDING_COMPARATOR
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator.Companion.LAST_PRICE_ASCENDING_COMPARATOR
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator.Companion.VOLUME_ASCENDING_COMPARATOR
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator.Companion.NAME_ASCENDING_COMPARATOR
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.interfaces.Themable
import kotlinx.android.synthetic.main.currency_markets_sort_panel_layout.view.*

/**
 * Contains all the functionality related to sorting the currency markets.
 */
class CurrencyMarketsSortPanel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), Themable<Theme> {


    private var mSelectedTitleTv: SortPanelTextView? = null


    private val mOnTitleClickListener: ((View) -> Unit) = { view ->
        if(mSelectedTitleTv?.id == view.id) {
            mSelectedTitleTv?.switchComparator()
        } else {
            val newSelectedTitleTv = (view as SortPanelTextView)

            mSelectedTitleTv?.isSelected = false
            newSelectedTitleTv.isSelected = true

            mSelectedTitleTv = newSelectedTitleTv
        }

        mOnSortPanelTitleClickListener?.invoke(mSelectedTitleTv!!)
    }


    /**
     * A listener used for notifying whenever a title is clicked on a panel.
     */
    var mOnSortPanelTitleClickListener: ((SortPanelTextView) -> Unit)? = null




    init {
        View.inflate(context, R.layout.currency_markets_sort_panel_layout, this)

        nameTitleTv.comparator = NAME_ASCENDING_COMPARATOR
        nameTitleTv.setOnClickListener(mOnTitleClickListener)

        volumeTitleTv.comparator = VOLUME_ASCENDING_COMPARATOR
        volumeTitleTv.setOnClickListener(mOnTitleClickListener)

        lastPriceTitleTv.comparator = LAST_PRICE_ASCENDING_COMPARATOR
        lastPriceTitleTv.setOnClickListener(mOnTitleClickListener)

        dailyPriceChangeTitleTv.comparator = DAILY_PRICE_CHANGE_ASCENDING_COMPARATOR
        dailyPriceChangeTitleTv.setOnClickListener(mOnTitleClickListener)
    }


    /**
     * Selects the sort panel TextView according to the comparator.
     *
     * @param comparator The comparator to select the panel title for
     */
    fun setComparator(comparator: CurrencyMarketComparator) {
        if(mSelectedTitleTv?.comparator?.id == comparator.id) {
            return
        }

        val newSelectedTitleTv = getTitleTvForComparator(comparator)
        newSelectedTitleTv.comparator = comparator
        mOnTitleClickListener.invoke(newSelectedTitleTv)
    }


    override fun applyTheme(theme: Theme) {
        nameTitleTv.applyTheme(theme)
        volumeTitleTv.applyTheme(theme)
        lastPriceTitleTv.applyTheme(theme)
        dailyPriceChangeTitleTv.applyTheme(theme)

        ThemingUtil.SortPanel.separator(separatorTv, theme)
    }


    /**
     * Returns a currently selected comparator or [NAME_ASCENDING_COMPARATOR] if none is selected.
     *
     * @return The currently selected comparator
     */
    fun getComparator(): CurrencyMarketComparator {
        return mSelectedTitleTv?.comparator ?: NAME_ASCENDING_COMPARATOR
    }


    private fun getTitleTvForComparator(comparator: CurrencyMarketComparator): SortPanelTextView {
        return when(comparator.column) {

            NAME -> nameTitleTv
            VOLUME -> volumeTitleTv
            LAST_PRICE -> lastPriceTitleTv
            DAILY_PRICE_CHANGE -> dailyPriceChangeTitleTv

        }
    }


}
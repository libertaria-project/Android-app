package com.stocksexchange.android.ui.currencymarkets.fragment

import android.content.Context
import android.view.View
import com.arthurivanets.adapster.recyclerview.TrackableRecyclerViewAdapter
import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator
import com.stocksexchange.android.model.comparators.CurrencyMarketItemComparator

class CurrencyMarketsRecyclerViewAdapter(
    context: Context,
    settings: Settings,
    items: MutableList<CurrencyMarketItem>
) : TrackableRecyclerViewAdapter<Long, CurrencyMarketItem, CurrencyMarketItem.ViewHolder>(context, items) {


    private val mResources: CurrencyMarketResources = CurrencyMarketResources.newInstance(context, settings)


    /**
     * A listener used for notifying whenever a particular item is clicked.
     */
    var onItemClickListener: ((View, CurrencyMarketItem, Int) -> Unit)? = null




    override fun assignListeners(holder: CurrencyMarketItem.ViewHolder, position: Int, item: CurrencyMarketItem) {
        super.assignListeners(holder, position, item)

        item.setOnItemClickListener(holder, position, onItemClickListener)
    }


    /**
     * Sorts the data set using the given comparator and notifies
     * the adapter.
     *
     * @param comparator The comparator to sort the data set with
     */
    fun sort(comparator: CurrencyMarketComparator?) {
        if(comparator == null) {
            return
        }

        items.sortWith(CurrencyMarketItemComparator(comparator))
        notifyDataSetChanged()
    }


    /**
     * Sets a limit of the base currency symbol characters.
     *
     * @param baseCurrencySymbolCharacterLimit The limit to set
     */
    fun setBaseCurrencySymbolCharacterLimit(baseCurrencySymbolCharacterLimit: Int) {
        mResources.baseCurrencySymbolCharacterLimit = baseCurrencySymbolCharacterLimit
    }


    /**
     * Returns a currency market index for the specified id or null if
     * it is not present.
     *
     * @param id The id to get the market for
     *
     * @return The currency market index or null if it is absent
     */
    fun getMarketIndexForMarketId(id: Long): Int? {
        return items.indices.firstOrNull {
            items[it].itemModel.id == id
        }
    }


    /**
     * Retrieves a sorted position for the specified currency market.
     *
     * @param currencyMarket The currency market to get the position for
     * @param comparator The comparator to compare two currency markets
     *
     * @return The sorted position for the market
     */
    fun getChronologicalPositionForCurrencyMarket(currencyMarket: CurrencyMarket, comparator: CurrencyMarketComparator?): Int {
        if(comparator == null) {
            return itemCount
        }

        return items.indices.firstOrNull {
            comparator.compare(items[it].itemModel, currencyMarket) == -1
        } ?: itemCount
    }


    override fun getResources(): CurrencyMarketResources? {
        return mResources
    }


}
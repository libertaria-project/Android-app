package com.stocksexchange.android.ui.utils.diffcallbacks

import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.ui.currencymarkets.fragment.CurrencyMarketItem

/**
 * A diff utility callback for [CurrencyMarket] model class.
 */
class CurrencyMarketsDiffCallback(
    oldList: List<CurrencyMarketItem>,
    newList: List<CurrencyMarketItem>
) : BaseDiffCallback<CurrencyMarket, CurrencyMarketItem>(oldList, newList) {


    override fun areItemsTheSame(oldItem: CurrencyMarket, newItem: CurrencyMarket): Boolean {
        return (oldItem.id == newItem.id)
    }


}
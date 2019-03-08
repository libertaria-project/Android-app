package com.stocksexchange.android.ui.utils.diffcallbacks

import com.stocksexchange.android.model.Wallet
import com.stocksexchange.android.ui.wallets.fragment.WalletItem

/**
 * A diff utility callback for [Wallet] model class.
 */
class WalletsDiffCallback(
    oldList: List<WalletItem>,
    newList: List<WalletItem>
) : BaseDiffCallback<Wallet, WalletItem>(oldList, newList) {


    override fun areItemsTheSame(oldItem: Wallet, newItem: Wallet): Boolean {
        return (oldItem.currency.name == oldItem.currency.name)
    }


}
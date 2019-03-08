package com.stocksexchange.android.mappings

import com.stocksexchange.android.model.Wallet
import com.stocksexchange.android.ui.wallets.fragment.WalletItem

fun Wallet.mapToWalletItem(): WalletItem {
    return WalletItem(this)
}


fun List<Wallet>.mapToWalletItemList(): List<WalletItem> {
    return map { it.mapToWalletItem() }
}
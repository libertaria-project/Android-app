package com.stocksexchange.android.ui.wallets

interface WalletsActivityContract {


    interface View {

        fun showPopupMenu()

        fun hidePopupMenu()

        fun updateEmptyWalletsFlag(shouldShowEmptyWallets: Boolean)

        fun reloadWallets(shouldShowEmptyWallets: Boolean)

        fun launchSearchActivity()

    }


    interface ActionListener {

        fun onPreRightButtonClicked()

        fun onRightButtonClicked()

        fun onEmptyWalletsFlagStateChanged(shouldShowEmptyWallets: Boolean)

    }


}
package com.stocksexchange.android.ui.wallets.fragment

import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.api.model.WalletParameters
import com.stocksexchange.android.model.Wallet
import com.stocksexchange.android.ui.base.mvp.views.ListDataLoadingView

interface WalletsContract {


    interface View : ListDataLoadingView<List<Wallet>> {

        fun showDisabledFiatWalletsDialog()

        fun hideDisabledFiatWalletsDialog()

        fun launchDepositActivity(wallet: Wallet)

        fun launchWithdrawalActivity(wallet: Wallet)

        fun updateUser(user: User)

        fun getWalletParameters(): WalletParameters

    }


    interface ActionListener {

        fun onDepositButtonClicked(wallet: Wallet)

        fun onWithdrawButtonClicked(wallet: Wallet)

        fun onEmptyWalletsFlagChanged(shouldShowEmptyWallets: Boolean)

    }


}
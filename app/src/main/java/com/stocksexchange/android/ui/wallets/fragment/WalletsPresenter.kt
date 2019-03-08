package com.stocksexchange.android.ui.wallets.fragment

import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.api.model.WalletParameters
import com.stocksexchange.android.model.CurrencyMarketTypes
import com.stocksexchange.android.model.DataLoadingSources
import com.stocksexchange.android.model.Wallet
import com.stocksexchange.android.ui.base.mvp.presenters.BaseListDataLoadingPresenter

class WalletsPresenter(
    model: WalletsModel,
    view: WalletsContract.View
) : BaseListDataLoadingPresenter<
    WalletsModel,
    WalletsContract.View,
    List<Wallet>,
    WalletParameters
>(model, view), WalletsContract.ActionListener, WalletsModel.ActionListener {




    init {
        model.setActionListener(this)
    }


    constructor(view: WalletsContract.View): this(WalletsModel(), view)


    override fun stop() {
        super.stop()

        mView.hideDisabledFiatWalletsDialog()
    }


    override fun shouldLoadNewDataOnStart(): Boolean {
        return true
    }


    override fun getDataLoadingParams(): WalletParameters {
        return mView.getWalletParameters()
    }


    override suspend fun onUserLoaded(user: User) {
        mView.updateUser(user)
        mModel.loadCurrencies(user, getDataLoadingParams())
    }


    override fun onDepositButtonClicked(wallet: Wallet) {
        when(wallet.currency.name) {

            CurrencyMarketTypes.USD.name,
            CurrencyMarketTypes.EUR.name,
            CurrencyMarketTypes.JPY.name,
            CurrencyMarketTypes.RUB.name -> {
                mView.showDisabledFiatWalletsDialog()
                return
            }

        }

        mView.launchDepositActivity(wallet)
    }


    override fun onWithdrawButtonClicked(wallet: Wallet) {
        //view.launchWithdrawalActivity(wallet)
    }


    override fun onEmptyWalletsFlagChanged(shouldShowEmptyWallets: Boolean) {
        reloadData(DataLoadingSources.OTHER)
    }


    override fun toString(): String {
        return "${super.toString()}_${mView.getWalletParameters().walletMode.name}"
    }


}
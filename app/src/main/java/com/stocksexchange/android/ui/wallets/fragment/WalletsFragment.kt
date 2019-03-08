package com.stocksexchange.android.ui.wallets.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.View
import android.widget.ProgressBar
import com.afollestad.materialdialogs.MaterialDialog
import com.stocksexchange.android.AppController
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.api.model.WalletParameters
import com.stocksexchange.android.mappings.mapToWalletItemList
import com.stocksexchange.android.model.Wallet
import com.stocksexchange.android.model.WalletModes
import com.stocksexchange.android.ui.base.fragments.BaseListDataLoadingFragment
import com.stocksexchange.android.ui.deposit.DepositActivity
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.diffcallbacks.WalletsDiffCallback
import com.stocksexchange.android.ui.utils.extensions.ctx
import com.stocksexchange.android.ui.views.InfoView
import com.stocksexchange.android.utils.providers.InfoViewProvider
import kotlinx.android.synthetic.main.wallets_fragment_layout.view.*

class WalletsFragment : BaseListDataLoadingFragment<
    WalletsPresenter,
    List<Wallet>,
    WalletItem,
    WalletsRecyclerViewAdapter
>(), WalletsContract.View {


    companion object {

        private const val SAVED_STATE_WALLET_PARAMETERS = "wallet_parameters"


        fun newStandardInstance() = newInstance(WalletModes.STANDARD)

        fun newSearchInstance() = newInstance(WalletModes.SEARCH)

        fun newInstance(walletMode: WalletModes): WalletsFragment {
            val fragment = WalletsFragment()

            fragment.mWalletParameters = fragment.mWalletParameters.copy(
                walletMode = walletMode
            )

            return fragment
        }

    }


    private var mWalletParameters: WalletParameters = WalletParameters.getDefaultParameters()

    private var mMaterialDialog: MaterialDialog? = null




    override fun initPresenter(): WalletsPresenter = WalletsPresenter(this)


    override fun init() {
        super.init()

        initContentContainer()
    }


    override fun initAdapter(): WalletsRecyclerViewAdapter {
        return WalletsRecyclerViewAdapter(ctx, getSettings(), mItems).apply {
            onDepositButtonClickListener = { _, walletItem, _ ->
                mPresenter?.onDepositButtonClicked(walletItem.itemModel)
            }
            onWithdrawButtonClickListener = { _, walletItem, _ ->
                mPresenter?.onWithdrawButtonClicked(walletItem.itemModel)
            }
        }
    }


    private fun initContentContainer() {
        ThemingUtil.Wallets.contentContainer(mRootView.contentContainerFl, getAppTheme())
    }


    override fun addData(data: List<Wallet>) {
        val walletItemList = data.mapToWalletItemList().toMutableList()

        if(isDataSourceEmpty()) {
            mAdapter.items = walletItemList
        } else {
            mAdapter.setItems(walletItemList, WalletsDiffCallback(mItems, walletItemList))
        }

        mItems = walletItemList
    }


    override fun showDisabledFiatWalletsDialog() {
        val dialog = MaterialDialog.Builder(ctx)
            .title(R.string.note)
            .content(R.string.wallets_fragment_disabled_fiat_wallets_dialog_message)
            .positiveText(R.string.ok)
            .apply { ThemingUtil.Wallets.dialogBuilder(this, getAppTheme()) }
            .build()

        mMaterialDialog = dialog
        mMaterialDialog?.show()
    }


    override fun hideDisabledFiatWalletsDialog() {
        mMaterialDialog?.dismiss()
    }


    override fun launchDepositActivity(wallet: Wallet) {
        startActivity(DepositActivity.newInstance(ctx, wallet))
    }


    override fun launchWithdrawalActivity(wallet: Wallet) {
        //todo
    }


    override fun updateUser(user: User) {
        AppController.INSTANCE.setUser(user)
    }


    override fun setSearchQuery(query: String) {
        mWalletParameters = mWalletParameters.copy(searchQuery = query)
    }


    override fun getInfoViewIcon(infoViewProvider: InfoViewProvider): Drawable? {
        return infoViewProvider.getWalletsIcon(mWalletParameters)
    }


    override fun getEmptyViewCaption(infoViewProvider: InfoViewProvider): String {
        return infoViewProvider.getWalletsEmptyCaption(mWalletParameters)
    }


    override fun getSearchQuery(): String = mWalletParameters.searchQuery


    override fun getMainView(): View = mRootView.recyclerView


    override fun getInfoView(): InfoView = mRootView.infoView


    override fun getProgressBar(): ProgressBar = mRootView.progressBar


    override fun getRefreshProgressBar(): SwipeRefreshLayout = mRootView.swipeRefreshLayout


    override fun getWalletParameters(): WalletParameters {
        return mWalletParameters
    }


    override fun getContentLayoutResourceId(): Int = R.layout.wallets_fragment_layout


    /**
     * A callback to invoke whenever empty wallets flag is toggled.
     *
     * @param shouldShowEmptyWallets Whether to should empty wallets or not
     */
    fun onEmptyWalletsFlagChanged(shouldShowEmptyWallets: Boolean) {
        if(mWalletParameters.shouldShowEmptyWallets == shouldShowEmptyWallets) {
            return
        }

        mWalletParameters = mWalletParameters.copy(shouldShowEmptyWallets = shouldShowEmptyWallets)
        mPresenter?.onEmptyWalletsFlagChanged(shouldShowEmptyWallets)
    }


    override fun onRestoreState(savedState: Bundle?) {
        if(savedState != null) {
            mWalletParameters = savedState.getParcelable(SAVED_STATE_WALLET_PARAMETERS)
        }

        super.onRestoreState(savedState)
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        savedState.putParcelable(SAVED_STATE_WALLET_PARAMETERS, mWalletParameters)
    }


}
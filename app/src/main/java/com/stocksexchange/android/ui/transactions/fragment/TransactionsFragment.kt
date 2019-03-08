package com.stocksexchange.android.ui.transactions.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.View
import android.widget.ProgressBar
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.Transaction
import com.stocksexchange.android.api.model.TransactionOperations
import com.stocksexchange.android.api.model.TransactionParameters
import com.stocksexchange.android.api.model.TransactionStatuses
import com.stocksexchange.android.mappings.mapToTransactionItemList
import com.stocksexchange.android.model.SortTypes
import com.stocksexchange.android.model.TransactionModes
import com.stocksexchange.android.model.TransactionTypes
import com.stocksexchange.android.ui.base.fragments.BaseListDataLoadingFragment
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.diffcallbacks.TransactionsDiffCallback
import com.stocksexchange.android.ui.utils.extensions.ctx
import com.stocksexchange.android.ui.views.InfoView
import com.stocksexchange.android.utils.handlers.BrowserHandler
import com.stocksexchange.android.utils.providers.InfoViewProvider
import kotlinx.android.synthetic.main.transactions_fragment_layout.view.*
import org.koin.android.ext.android.get

class TransactionsFragment : BaseListDataLoadingFragment<
    TransactionsPresenter,
    List<Transaction>,
    TransactionItem,
    TransactionsRecyclerViewAdapter
>(), TransactionsContract.View {


    companion object {

        private const val SAVED_STATE_TRANSACTION_PARAMETERS = "transaction_parameters"


        fun newStandardInstance(type: TransactionTypes) = newInstance(TransactionModes.STANDARD, type)

        fun newSearchInstance(type: TransactionTypes) = newInstance(TransactionModes.SEARCH, type)

        fun newInstance(mode: TransactionModes, type: TransactionTypes): TransactionsFragment {
            val fragment = TransactionsFragment()

            fragment.mTransactionParameters = fragment.mTransactionParameters.copy(
                mode = mode,
                type = type,
                operation = type.getOperation()
            )

            return fragment
        }

    }


    private var mTransactionParameters = TransactionParameters.getDefaultParameters()




    override fun initPresenter(): TransactionsPresenter = TransactionsPresenter(this)


    override fun init() {
        super.init()

        initContentContainer()
    }


    override fun initAdapter(): TransactionsRecyclerViewAdapter {
        return TransactionsRecyclerViewAdapter(ctx, getSettings(), mItems).apply {
            setTransactionType(mTransactionParameters.type)

            onTransactionAddressClickListener = { _, transactionItem, _ ->
                mPresenter?.onTransactionAddressClicked(transactionItem.itemModel)
            }
            onTransactionIdClickListener = { _, transactionItem, _ ->
                mPresenter?.onTransactionIdClicked(transactionItem.itemModel)
            }
        }
    }


    private fun initContentContainer() {
        ThemingUtil.Transactions.contentContainer(mRootView.contentContainerFl, getAppTheme())
    }


    override fun addData(data: List<Transaction>) {
        val transactionItemList = data.mapToTransactionItemList().toMutableList()

        if(isDataSourceEmpty()) {
            mAdapter.items = transactionItemList
        } else {
            mAdapter.setItems(transactionItemList, TransactionsDiffCallback(mItems, transactionItemList))
        }

        mItems = transactionItemList
    }


    override fun launchBrowser(url: String) {
        get<BrowserHandler>().launchBrowser(ctx, url, getAppTheme())
    }


    override fun setSearchQuery(query: String) {
        mTransactionParameters = mTransactionParameters.copy(searchQuery = query)
    }


    override fun getInfoViewIcon(infoViewProvider: InfoViewProvider): Drawable? {
        return infoViewProvider.getTransactionsIcon(mTransactionParameters)
    }


    override fun getEmptyViewCaption(infoViewProvider: InfoViewProvider): String {
        return infoViewProvider.getTransactionsEmptyCaption(mTransactionParameters)
    }


    override fun getSearchQuery(): String = mTransactionParameters.searchQuery


    override fun getMainView(): View = mRootView.recyclerView


    override fun getInfoView(): InfoView = mRootView.infoView


    override fun getProgressBar(): ProgressBar = mRootView.progressBar


    override fun getRefreshProgressBar(): SwipeRefreshLayout = mRootView.swipeRefreshLayout


    override fun getTransactionParameters(): TransactionParameters {
        return mTransactionParameters
    }


    override fun getContentLayoutResourceId(): Int = R.layout.transactions_fragment_layout


    override fun onRestoreState(savedState: Bundle?) {
        savedState?.apply {
            mTransactionParameters = getParcelable(SAVED_STATE_TRANSACTION_PARAMETERS)
        }

        super.onRestoreState(savedState)
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        savedState.putParcelable(SAVED_STATE_TRANSACTION_PARAMETERS, mTransactionParameters)
    }


}
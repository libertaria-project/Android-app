package com.stocksexchange.android.ui.transactions.fragment

import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.Transaction
import com.stocksexchange.android.api.model.TransactionParameters
import com.stocksexchange.android.model.DataLoadingSources
import com.stocksexchange.android.model.DataTypes
import com.stocksexchange.android.ui.base.mvp.presenters.BaseListDataLoadingPresenter
import com.stocksexchange.android.utils.handlers.ClipboardHandler
import com.stocksexchange.android.utils.providers.StringProvider
import org.koin.standalone.inject

class TransactionsPresenter(
    model: TransactionsModel,
    view: TransactionsContract.View
) : BaseListDataLoadingPresenter<
    TransactionsModel,
    TransactionsContract.View,
    List<Transaction>,
    TransactionParameters
>(model, view), TransactionsContract.ActionListener, TransactionsModel.ActionListener {


    private val mStringProvider: StringProvider by inject()
    private val mClipboardHandler: ClipboardHandler by inject()




    init {
        model.setActionListener(this)
    }


    constructor(view: TransactionsContract.View): this(TransactionsModel(), view)


    override fun shouldLoadNewDataOnStart(): Boolean {
        return true
    }


    override fun getDataLoadingParams(): TransactionParameters {
        return mView.getTransactionParameters()
    }


    override fun onTransactionAddressClicked(transaction: Transaction) {
        copyTextToClipboard(transaction.address)
    }


    override fun onTransactionIdClicked(transaction: Transaction) {
        val transactionId = transaction.transactionId!!

        if(transaction.hasBlockExplorerUrl()) {
            mView.launchBrowser(transaction.blockExplorerUrl + transactionId)
        } else {
            copyTextToClipboard(transactionId)
        }
    }


    private fun copyTextToClipboard(text: String) {
        mClipboardHandler.copyText(text)

        mView.showToast(mStringProvider.getString(R.string.copied_to_clipboard))
    }


    override fun toString(): String {
        val params = mView.getTransactionParameters()
        val mode = params.mode.name
        val type = params.type.name

        return "${super.toString()}_${mode}_$type"
    }


}
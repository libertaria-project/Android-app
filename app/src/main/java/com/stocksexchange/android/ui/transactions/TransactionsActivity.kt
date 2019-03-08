package com.stocksexchange.android.ui.transactions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.stocksexchange.android.R
import com.stocksexchange.android.model.TransactionTypes
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.ui.base.activities.BaseFragmentActivity
import com.stocksexchange.android.ui.transactions.fragment.TransactionsFragment
import com.stocksexchange.android.ui.transactions.search.TransactionsSearchActivity
import com.stocksexchange.android.theming.ThemingUtil
import kotlinx.android.synthetic.main.transactions_activity_layout.*
import org.jetbrains.anko.intentFor

class TransactionsActivity : BaseFragmentActivity<TransactionsFragment, TransactionsActivityPresenter>(),
    TransactionsActivityContract.View {


    companion object {

        private const val EXTRA_TRANSACTION_TYPE = "transaction_type"

        private const val SAVED_STATE_TRANSACTION_TYPE = "transaction_type"


        fun newDepositsInstance(context: Context) = newInstance(context, TransactionTypes.DEPOSITS)

        fun newWithdrawalsInstance(context: Context) = newInstance(context, TransactionTypes.WITHDRAWALS)

        fun newInstance(context: Context, transactionType: TransactionTypes): Intent {
            return context.intentFor<TransactionsActivity>(
                EXTRA_TRANSACTION_TYPE to transactionType
            )
        }

    }


    private lateinit var mTransactionType: TransactionTypes




    override fun initPresenter(): TransactionsActivityPresenter = TransactionsActivityPresenter(this)


    override fun init() {
        super.init()

        initToolbar()
    }


    private fun initToolbar() {
        toolbar.setOnLeftButtonClickListener {
            onBackPressed()
        }

        toolbar.setTitleText(getString(when(mTransactionType) {
            TransactionTypes.DEPOSITS -> R.string.deposits
            TransactionTypes.WITHDRAWALS -> R.string.withdrawals
        }))

        toolbar.setOnRightButtonClickListener {
            mPresenter?.onRightButtonClicked()
        }

        ThemingUtil.Transactions.toolbar(toolbar, getAppTheme())
    }


    override fun launchSearchActivity() {
        startActivity(TransactionsSearchActivity.newInstance(this, mTransactionType))
    }


    override fun getActivityFragment(): TransactionsFragment {
        return TransactionsFragment.newStandardInstance(mTransactionType)
    }


    override fun getContentLayoutResourceId(): Int = R.layout.transactions_activity_layout


    override fun getTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.HORIZONTAL_SLIDING_ANIMATIONS
    }


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            mTransactionType = (savedState.getSerializable(SAVED_STATE_TRANSACTION_TYPE) as TransactionTypes)
        } else {
            mTransactionType = (intent.getSerializableExtra(EXTRA_TRANSACTION_TYPE) as? TransactionTypes) ?: TransactionTypes.DEPOSITS
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        savedState.putSerializable(SAVED_STATE_TRANSACTION_TYPE, mTransactionType)
    }


}
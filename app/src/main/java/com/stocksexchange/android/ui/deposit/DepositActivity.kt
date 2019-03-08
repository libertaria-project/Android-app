package com.stocksexchange.android.ui.deposit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.stocksexchange.android.R
import com.stocksexchange.android.model.TransitionAnimations
import com.stocksexchange.android.model.Wallet
import com.stocksexchange.android.ui.base.activities.BaseFragmentActivity
import com.stocksexchange.android.ui.deposit.fragment.DepositFragment
import com.stocksexchange.android.theming.ThemingUtil
import kotlinx.android.synthetic.main.deposit_activity_layout.*
import org.jetbrains.anko.intentFor

class DepositActivity : BaseFragmentActivity<DepositFragment, DepositActivityPresenter>(),
    DepositActivityContract.View {


    companion object {

        private const val EXTRA_WALLET = "wallet"

        private const val SAVED_STATE_WALLET = "wallet"


        fun newInstance(context: Context, wallet: Wallet): Intent {
            return context.intentFor<DepositActivity>(
                EXTRA_WALLET to wallet
            )
        }

    }


    private lateinit var mWallet: Wallet




    override fun initPresenter(): DepositActivityPresenter = DepositActivityPresenter(this)


    override fun init() {
        super.init()

        initToolbar()
        initFragmentHolder()
    }


    private fun initToolbar() {
        toolbar.setOnLeftButtonClickListener {
            onBackPressed()
        }

        ThemingUtil.Deposit.toolbar(toolbar, getAppTheme())
    }


    private fun initFragmentHolder() {
        ThemingUtil.Deposit.contentContainer(fragmentHolderFl, getAppTheme())
    }


    override fun getActivityFragment(): DepositFragment {
        return DepositFragment.newInstance(mWallet)
    }


    override fun getContentLayoutResourceId(): Int = R.layout.deposit_activity_layout


    override fun getTransitionAnimations(): TransitionAnimations {
        return TransitionAnimations.HORIZONTAL_SLIDING_ANIMATIONS
    }


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            mWallet = savedState.getParcelable(SAVED_STATE_WALLET)!!
        } else {
            mWallet = intent.getParcelableExtra(EXTRA_WALLET)
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        savedState.putParcelable(SAVED_STATE_WALLET, mWallet)
    }


}
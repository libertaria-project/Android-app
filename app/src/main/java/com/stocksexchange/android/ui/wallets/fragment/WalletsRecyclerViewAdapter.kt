package com.stocksexchange.android.ui.wallets.fragment

import android.content.Context
import android.view.View
import com.arthurivanets.adapster.recyclerview.TrackableRecyclerViewAdapter
import com.stocksexchange.android.model.Settings

class WalletsRecyclerViewAdapter(
    context: Context,
    settings: Settings,
    items: MutableList<WalletItem>
) : TrackableRecyclerViewAdapter<String, WalletItem, WalletItem.ViewHolder>(context, items) {


    private val mResources: WalletsResources = WalletsResources.newInstance(context, settings)

    /**
     * A listener used for notifying whenever the deposit button is clicked.
     */
    var onDepositButtonClickListener: ((View, WalletItem, Int) -> Unit)? = null

    /**
     * A listener used for notifying whenever the deposit button is clicked.
     */
    var onWithdrawButtonClickListener: ((View, WalletItem, Int) -> Unit)? = null




    override fun assignListeners(holder: WalletItem.ViewHolder, position: Int, item: WalletItem) {
        super.assignListeners(holder, position, item)

        with(item) {
            setOnDepositButtonClickListener(holder, position, onDepositButtonClickListener)
            setOnWithdrawButtonClickListener(holder, position, onWithdrawButtonClickListener)
        }

    }


    override fun getResources(): WalletsResources? {
        return mResources
    }


}
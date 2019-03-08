package com.stocksexchange.android.ui.wallets.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.Adapter
import com.arthurivanets.adapster.markers.ItemResources
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.model.Item
import com.arthurivanets.adapster.model.markers.Trackable
import com.stocksexchange.android.R
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.model.Wallet
import com.stocksexchange.android.ui.base.adapters.recyclerview.BaseViewHolder
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.views.DottedMapView

class WalletItem(itemModel: Wallet) : BaseItem<
    Wallet,
    WalletItem.ViewHolder,
    WalletsResources
>(itemModel), Trackable<String> {


    companion object {

        const val MAIN_LAYOUT_ID = R.layout.wallet_item_layout

    }




    override fun init(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?, parent: ViewGroup,
                      inflater: LayoutInflater, resources: WalletsResources?): ViewHolder {
        return ViewHolder(inflater.inflate(layout, parent, false)).apply {
            applyTheme(resources!!.settings.theme)
        }
    }


    override fun bind(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
                      viewHolder: ViewHolder, resources: WalletsResources?) {
        super.bind(adapter, viewHolder, resources)

        with(viewHolder) {
            mCurrencyNameTv.text = itemModel.currency.name
            mCurrencyLongNameTv.text = itemModel.currency.longName

            if(itemModel.currency.isActive && !itemModel.currency.isDepositingDisabled) {
                mDepositTvBtn.enable(true)
                mWithdrawTvBtn.enable(true)
            } else {
                mDepositTvBtn.disable(true)
                mWithdrawTvBtn.disable(true)
            }

            mAvailableBalanceDmv.setValueText(resources!!.formatter.formatBalance(itemModel.availableBalance))
            mBalanceInOrdersDmv.setValueText(resources.formatter.formatBalance(itemModel.balanceInOrders))

            //todo
            mWithdrawTvBtn.makeGone()
        }
    }


    fun setOnDepositButtonClickListener(viewHolder: ViewHolder, position: Int,
                                        listener: ((View, WalletItem, Int) -> Unit)?) {
        viewHolder.mDepositTvBtn.setOnClickListener {
            listener?.invoke(it, this@WalletItem, position)
        }
    }


    fun setOnWithdrawButtonClickListener(viewHolder: ViewHolder, position: Int,
                                         listener: ((View, WalletItem, Int) -> Unit)?) {
        viewHolder.mWithdrawTvBtn.setOnClickListener {
            listener?.invoke(it, this@WalletItem, position)
        }
    }


    override fun getLayout(): Int = MAIN_LAYOUT_ID


    override fun getTrackKey(): String = itemModel.currency.name


    class ViewHolder(itemView: View) : BaseViewHolder<Wallet>(itemView) {

        val mCurrencyNameTv: TextView = itemView.findViewById(R.id.currencyNameTv)
        val mCurrencyLongNameTv: TextView = itemView.findViewById(R.id.currencyLongNameTv)

        val mAvailableBalanceDmv: DottedMapView = itemView.findViewById(R.id.availableBalanceDmv)
        val mBalanceInOrdersDmv: DottedMapView = itemView.findViewById(R.id.balanceInOrdersDmv)

        val mDepositTvBtn: TextView = itemView.findViewById(R.id.depositTvBtn)
        val mWithdrawTvBtn: TextView = itemView.findViewById(R.id.withdrawTvBtn)

        val mCardView: CardView = itemView.findViewById(R.id.cardView)


        override fun applyTheme(theme: Theme) {
            with(ThemingUtil.Wallets.WalletItem) {
                cardView(mCardView, theme)
                currencyName(mCurrencyNameTv, theme)
                currencyLongName(mCurrencyLongNameTv, theme)
                dottedMap(mAvailableBalanceDmv, theme)
                dottedMap(mBalanceInOrdersDmv, theme)
                actionButton(mDepositTvBtn, theme)
                actionButton(mWithdrawTvBtn, theme)
            }
        }

    }


}
package com.stocksexchange.android.ui.views.marketpreview.trades.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.Adapter
import com.arthurivanets.adapster.markers.ItemResources
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.model.Item
import com.arthurivanets.adapster.model.markers.Trackable
import com.stocksexchange.android.R
import com.stocksexchange.android.model.TradeHeader
import com.stocksexchange.android.ui.base.adapters.recyclerview.BaseViewHolder
import com.stocksexchange.android.ui.views.marketpreview.trades.TradeResources

class TradeHeaderItem(itemModel: TradeHeader) : BaseItem<
    TradeHeader,
    TradeHeaderItem.ViewHolder,
    TradeResources
>(itemModel), Trackable<Long> {


    companion object {

        const val MAIN_LAYOUT_ID = R.layout.trade_header_item_layout

    }




    override fun init(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?, parent: ViewGroup,
                      inflater: LayoutInflater, resources: TradeResources?): ViewHolder {
        return ViewHolder(inflater.inflate(layout, parent, false)).apply {
            val colors = resources!!.colors

            amountTitleTv.setTextColor(colors[TradeResources.COLOR_HEADER_TITLE_TEXT])
            priceTitleTv.setTextColor(colors[TradeResources.COLOR_HEADER_TITLE_TEXT])
            timeTitleTv.setTextColor(colors[TradeResources.COLOR_HEADER_TITLE_TEXT])
            separatorView.setBackgroundColor(colors[TradeResources.COLOR_HEADER_SEPARATOR])
        }
    }


    override fun bind(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
                      viewHolder: ViewHolder, resources: TradeResources?) {
        super.bind(adapter, viewHolder, resources)

        with(viewHolder) {
            amountTitleTv.text = itemModel.amountTitleText
            priceTitleTv.text = itemModel.priceTitleText
            timeTitleTv.text = itemModel.timeTitleText
        }
    }


    override fun getLayout(): Int = MAIN_LAYOUT_ID


    override fun getTrackKey(): Long = itemModel.id


    class ViewHolder(itemView: View) : BaseViewHolder<TradeHeader>(itemView) {

        val separatorView: View = itemView.findViewById(R.id.separatorView)

        val amountTitleTv: TextView = itemView.findViewById(R.id.amountTitleTv)
        val priceTitleTv: TextView = itemView.findViewById(R.id.priceTitleTv)
        val timeTitleTv: TextView = itemView.findViewById(R.id.timeTitleTv)

    }


}
package com.stocksexchange.android.ui.views.marketpreview.trades.items

import android.graphics.Color
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
import com.stocksexchange.android.model.TradeData
import com.stocksexchange.android.ui.base.adapters.recyclerview.BaseViewHolder
import com.stocksexchange.android.ui.views.marketpreview.trades.TradeResources
import com.stocksexchange.android.utils.Timer

class TradeItem(itemModel: TradeData) : BaseItem<
    TradeData,
    TradeItem.ViewHolder,
    TradeResources
>(itemModel), Trackable<Long> {


    companion object {

        private const val MAIN_LAYOUT_ID = R.layout.trade_item_layout

    }




    override fun init(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?, parent: ViewGroup,
                      inflater: LayoutInflater, resources: TradeResources?): ViewHolder {
        return ViewHolder(inflater.inflate(layout, parent, false)).apply {
            val colors = resources!!.colors

            mAmountTv.setTextColor(colors[TradeResources.COLOR_TRADE_AMOUNT])
            mTimeTv.setTextColor(colors[TradeResources.COLOR_TRADE_TIME])
        }
    }


    override fun bind(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
                      viewHolder: ViewHolder, resources: TradeResources?) {
        super.bind(adapter, viewHolder, resources)

        with(viewHolder) {
            val trade = itemModel.trade
            val colors = resources!!.colors
            val doubleFormatter = resources.doubleFormatter
            val timeFormatter = resources.timeFormatter
            val defaultBackgroundColor = Color.TRANSPARENT
            val backgroundHighlightColor: Int
            val priceHighlightColor: Int
            val priceColor: Int

             if(trade.isBuyTrade()) {
                 backgroundHighlightColor = colors[TradeResources.COLOR_BUY_TRADE_BACKGROUND_HIGHLIGHT]
                 priceHighlightColor = colors[TradeResources.COLOR_BUY_TRADE_PRICE_HIGHLIGHT]
                 priceColor = colors[TradeResources.COLOR_BUY_TRADE_PRICE]
            } else {
                 backgroundHighlightColor = colors[TradeResources.COLOR_SELL_TRADE_BACKGROUND_HIGHLIGHT]
                 priceHighlightColor = colors[TradeResources.COLOR_SELL_TRADE_PRICE_HIGHLIGHT]
                 priceColor = colors[TradeResources.COLOR_SELL_TRADE_PRICE]
            }

            if(trade.isStub()) {
                val stubText = resources.stubText

                mAmountTv.text = stubText
                mPriceTv.text = stubText
                mTimeTv.text = stubText

                mPriceTv.setTextColor(priceColor)
            } else {
                val dehighlightViews = {
                    mPriceTv.setTextColor(priceColor)
                    itemView.setBackgroundColor(defaultBackgroundColor)
                }

                if(itemModel.shouldBeHighlighted()) {
                    val highlightViews = {
                        mPriceTv.setTextColor(priceHighlightColor)
                        itemView.setBackgroundColor(backgroundHighlightColor)
                    }

                    (itemView.tag as? Timer)?.cancel()

                    val highlightDuration = itemModel.getHighlightDuration()
                    val timer = object : Timer(highlightDuration, highlightDuration) {

                        override fun onStarted() {
                            highlightViews()
                        }

                        override fun onFinished() {
                            dehighlightViews()
                        }

                    }

                    timer.start()
                    itemView.tag = timer
                } else {
                    dehighlightViews()
                }

                mAmountTv.text = doubleFormatter.formatTradeAmount(trade.amount, resources.amountMaxCharsLength)
                mPriceTv.text = doubleFormatter.formatTradePrice(trade.price, resources.priceMaxCharsLength)
                mTimeTv.text = timeFormatter.formatTradeTime(trade.timestampInMillis)
            }
        }
    }


    override fun getLayout(): Int = MAIN_LAYOUT_ID


    override fun getTrackKey(): Long = itemModel.trade.id


    class ViewHolder(itemView: View) : BaseViewHolder<TradeData>(itemView) {

        val mAmountTv: TextView = itemView.findViewById(R.id.amountTv)
        val mPriceTv: TextView = itemView.findViewById(R.id.priceTv)
        val mTimeTv: TextView = itemView.findViewById(R.id.timeTv)

    }


}
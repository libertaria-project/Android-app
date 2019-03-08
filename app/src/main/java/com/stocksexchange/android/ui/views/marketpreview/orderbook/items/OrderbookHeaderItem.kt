package com.stocksexchange.android.ui.views.marketpreview.orderbook.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.Adapter
import com.arthurivanets.adapster.markers.ItemResources
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.model.Item
import com.arthurivanets.adapster.model.markers.Trackable
import com.stocksexchange.android.R
import com.stocksexchange.android.model.OrderbookHeader
import com.stocksexchange.android.model.OrderbookOrderTypes
import com.stocksexchange.android.ui.base.adapters.recyclerview.BaseViewHolder
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.views.marketpreview.orderbook.OrderbookResources

class OrderbookHeaderItem(itemModel: OrderbookHeader) : BaseItem<
    OrderbookHeader,
    OrderbookHeaderItem.ViewHolder,
    OrderbookResources
>(itemModel), Trackable<String> {


    companion object {

        const val BID_ITEM_LAYOUT_ID = R.layout.orderbook_header_bid_item_layout
        const val ASK_ITEM_LAYOUT_ID = R.layout.orderbook_header_ask_item_layout

    }




    override fun init(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?, parent: ViewGroup,
                      inflater: LayoutInflater, resources: OrderbookResources?): ViewHolder {
        return ViewHolder(inflater.inflate(layout, parent, false)).apply {
            if(resources!!.shouldHideHeaderMoreButton) {
                mMoreBtnIv.makeGone()

                with((mTitleTv.layoutParams as RelativeLayout.LayoutParams)) {
                    when(itemModel.type) {

                        OrderbookOrderTypes.BID -> {
                            removeRule(RelativeLayout.ALIGN_PARENT_LEFT)
                            removeRule(RelativeLayout.ALIGN_PARENT_START)
                        }

                        OrderbookOrderTypes.ASK -> {
                            removeRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                            removeRule(RelativeLayout.ALIGN_PARENT_END)
                        }

                    }

                    addRule(RelativeLayout.CENTER_HORIZONTAL)
                }
            }

            val colors = resources.colors

            mTitleTv.setTextColor(colors[OrderbookResources.COLOR_HEADER_TITLE_TEXT])
            mMoreBtnIv.setColor(colors[OrderbookResources.COLOR_HEADER_MORE_BUTTON])
            mSeparatorView.setBackgroundColor(colors[OrderbookResources.COLOR_HEADER_SEPARATOR])
        }
    }


    override fun bind(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
                      viewHolder: ViewHolder, resources: OrderbookResources?) {
        super.bind(adapter, viewHolder, resources)

        viewHolder.mTitleTv.text = itemModel.title
    }


    fun setOnMoreButtonClickListener(viewHolder: ViewHolder, position: Int,
                                     listener: ((View, OrderbookHeaderItem, Int) -> Unit)?) {
        viewHolder.mMoreBtnIv.setOnClickListener {
            listener?.invoke(it, this@OrderbookHeaderItem, position)
        }
    }


    override fun getLayout(): Int {
        return when(itemModel.type) {
            OrderbookOrderTypes.BID -> BID_ITEM_LAYOUT_ID
            OrderbookOrderTypes.ASK -> ASK_ITEM_LAYOUT_ID
        }
    }


    override fun getTrackKey(): String = itemModel.title


    class ViewHolder(itemView: View) : BaseViewHolder<OrderbookHeader>(itemView) {

        val mSeparatorView: View = itemView.findViewById(R.id.separatorView)

        val mTitleTv: TextView = itemView.findViewById(R.id.titleTv)

        val mMoreBtnIv: ImageView = itemView.findViewById(R.id.moreBtnIv)

    }


}
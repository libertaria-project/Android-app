package com.stocksexchange.android.ui.orders.fragment

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.Adapter
import com.arthurivanets.adapster.markers.ItemResources
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.model.Item
import com.arthurivanets.adapster.model.markers.Trackable
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.api.model.Order
import com.stocksexchange.android.api.model.OrderTypes
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.ui.base.adapters.recyclerview.BaseViewHolder
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.extensions.makeGone
import com.stocksexchange.android.ui.utils.extensions.makeVisible
import com.stocksexchange.android.ui.utils.text.CustomLinkMovementMethod
import com.stocksexchange.android.ui.utils.text.SelectorSpan
import com.stocksexchange.android.ui.views.DottedMapView

class OrderItem(itemModel: Order) : BaseItem<Order, OrderItem.ViewHolder, OrderResources>(itemModel), Trackable<Long> {


    companion object {

        const val MAIN_LAYOUT_ID = R.layout.order_item_layout

    }




    override fun init(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?, parent: ViewGroup,
                      inflater: LayoutInflater, resources: OrderResources?): ViewHolder {
        return ViewHolder(inflater.inflate(layout, parent, false)).apply {
            applyTheme(resources!!.settings.theme)
        }
    }


    override fun bind(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
                      viewHolder: ViewHolder, resources: OrderResources?) {
        super.bind(adapter, viewHolder, resources)

        with(viewHolder) {
            val theme = resources!!.settings.theme

            if(itemModel.isBuyTrade()) {
                mTypeTv.text = resources.strings[OrderResources.STRING_TYPE_BUY]
                ThemingUtil.Orders.OrderItem.buyButton(mTypeTv, theme)
            } else {
                mTypeTv.text = resources.strings[OrderResources.STRING_TYPE_SELL]
                ThemingUtil.Orders.OrderItem.sellButton(mTypeTv, theme)
            }

            when(resources.orderType) {

                OrderTypes.ACTIVE -> {
                    calculateId(viewHolder)
                    calculateAmount(viewHolder, resources)
                    calculateIncome(viewHolder, resources)

                    mResultDmv.makeGone()
                }

                OrderTypes.COMPLETED -> {
                    calculateId(viewHolder)
                    calculateAmount(viewHolder, resources)
                    calculateResult(viewHolder, resources)

                    mIncomeDmv.makeGone()
                }

                OrderTypes.CANCELLED -> {
                    calculateId(viewHolder)
                    calculateAmount(viewHolder, resources)

                    mResultDmv.makeGone()
                    mIncomeDmv.makeGone()
                }

            }

            val price = getPrice(resources.orderType)

            if(price != null) {
                mPriceDmv.setValueText("${resources.doubleFormatter.formatPrice(price)} ${itemModel.quoteCurrencySymbol}")
                mPriceDmv.makeVisible()
            } else {
                mPriceDmv.makeGone()
            }

            mDateDmv.setValueText(resources.timeFormatter.formatDate(itemModel.timestamp * 1000L))

            if(resources.orderType == OrderTypes.ACTIVE) {
                mCancelBtn.makeVisible()
                ThemingUtil.Orders.OrderItem.cancelButton(mCancelBtn, resources.settings.theme)
            } else {
                mCancelBtn.makeGone()
            }
        }
    }


    private fun calculateId(viewHolder: ViewHolder) {
        viewHolder.mIdDmv.setValueText(String.format("%d", itemModel.id))
    }


    private fun calculateAmount(viewHolder: ViewHolder, resources: OrderResources?) {
        val formatter = resources!!.doubleFormatter
        val amount = getAmount(resources.orderType)

        viewHolder.mAmountDmv.setValueText("${formatter.formatAmount(amount)} ${itemModel.baseCurrencySymbol}")
    }


    private fun calculateResult(viewHolder: ViewHolder, resources: OrderResources?) {
        val formatter = resources!!.doubleFormatter

        if(resources.orderType == OrderTypes.COMPLETED) {
            with(viewHolder) {
                if(itemModel.isBuyTrade()) {
                    mResultDmv.setTitleText(resources.strings[OrderResources.STRING_RESULT_SPENT])

                    // Temporary fix until the API is fixed
                    val result = (getAmount(resources.orderType) * (getPrice(resources.orderType) ?: 0.0))
                    mResultDmv.setValueText("${formatter.formatAmount(result)} ${itemModel.quoteCurrencySymbol}")
                } else {
                    mResultDmv.setTitleText(resources.strings[OrderResources.STRING_RESULT_RECEIVED])
                    mResultDmv.setValueText("${formatter.formatAmount(itemModel.sellAmount)} ${itemModel.quoteCurrencySymbol}")
                }
            }
        }
    }


    private fun calculateIncome(viewHolder: ViewHolder, resources: OrderResources?) {
        viewHolder.mIncomeDmv.setValueText(resources!!.doubleFormatter.formatAmount(itemModel.amount * itemModel.rate))
    }


    private fun getAmount(orderTypes: OrderTypes): Double {
        return when(orderTypes) {
            OrderTypes.COMPLETED, OrderTypes.CANCELLED -> itemModel.originalAmount

            else -> itemModel.amount
        }
    }


    private fun getPrice(orderTypes: OrderTypes): Double? {
        return when(orderTypes) {
            OrderTypes.ACTIVE -> itemModel.rate

            else -> {
                val ratesMapKeys = itemModel.ratesMap.keys

                if(ratesMapKeys.isEmpty()) null else ratesMapKeys.first()
            }
        }
    }


    fun setOnMarketNameClickListener(viewHolder: ViewHolder, resources: OrderResources?,
                                     position: Int, listener: ((View, OrderItem, CurrencyMarket?, Int) -> Unit)?) {
        val marketName = "${itemModel.baseCurrencySymbol} / ${itemModel.quoteCurrencySymbol}"

        val spannableString = SpannableString(marketName)
        val currencyMarket = resources!!.currencyMarkets[itemModel.marketName]

        spannableString.setSpan(
            getClickableSpanForEntry(resources, currencyMarket, position, listener),
            0, spannableString.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        viewHolder.mMarketTv.movementMethod = CustomLinkMovementMethod()
        viewHolder.mMarketTv.text = spannableString
    }


    fun setOnCancelBtnClickListener(viewHolder: ViewHolder, resources: OrderResources?, position: Int,
                                    listener: ((View, OrderItem, CurrencyMarket?, Int) -> Unit)?) {
        viewHolder.mCancelBtn.setOnClickListener {
            listener?.invoke(it, this@OrderItem, resources!!.currencyMarkets[itemModel.marketName], position)
        }
    }


    private fun getClickableSpanForEntry(resources: OrderResources?, currencyMarket: CurrencyMarket?,
                                         position: Int, listener: ((View, OrderItem, CurrencyMarket?, Int) -> Unit)?): SelectorSpan {
        return object : SelectorSpan(
            resources!!.settings.theme.generalTheme.linkReleasedStateBackgroundColor,
            resources.settings.theme.generalTheme.linkPressedStateBackgroundColor
        ) {

            override fun onClick(widget: View) {
                listener?.invoke(widget, this@OrderItem, currencyMarket, position)
            }

        }
    }


    override fun getLayout(): Int = MAIN_LAYOUT_ID


    override fun getTrackKey(): Long = itemModel.id


    class ViewHolder(itemView: View) : BaseViewHolder<Order>(itemView) {

        val mTypeTv: TextView = itemView.findViewById(R.id.typeTv)
        val mMarketTv: TextView = itemView.findViewById(R.id.marketTv)
        val mCancelBtn: TextView = itemView.findViewById(R.id.cancelBtn)

        val mIdDmv: DottedMapView = itemView.findViewById(R.id.idDmv)
        val mAmountDmv: DottedMapView = itemView.findViewById(R.id.amountDmv)
        val mPriceDmv: DottedMapView = itemView.findViewById(R.id.priceDmv)
        val mResultDmv: DottedMapView = itemView.findViewById(R.id.resultDmv)
        val mIncomeDmv: DottedMapView = itemView.findViewById(R.id.incomeDmv)
        val mDateDmv: DottedMapView = itemView.findViewById(R.id.dateDmv)

        val mTopBarFl: FrameLayout = itemView.findViewById(R.id.topBarFl)
        val mCardView: CardView = itemView.findViewById(R.id.cardView)


        override fun applyTheme(theme: Theme) {
            with(ThemingUtil.Orders.OrderItem) {
                cardView(mCardView, theme)

                title(mMarketTv, theme)

                dottedMap(mIdDmv, theme)
                dottedMap(mAmountDmv, theme)
                dottedMap(mPriceDmv, theme)
                dottedMap(mResultDmv, theme)
                dottedMap(mIncomeDmv, theme)
                dottedMap(mDateDmv, theme)
            }
        }

    }


}
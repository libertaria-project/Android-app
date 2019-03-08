package com.stocksexchange.android.ui.currencymarkets.fragment

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextSwitcher
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
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.ui.base.adapters.recyclerview.BaseViewHolder
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.extensions.loadAnimation
import com.stocksexchange.android.ui.utils.extensions.setTextAppearanceCompat
import com.stocksexchange.android.utils.extensions.truncate

class CurrencyMarketItem(itemModel: CurrencyMarket) : BaseItem<
    CurrencyMarket,
    CurrencyMarketItem.ViewHolder,
    CurrencyMarketResources
>(itemModel), Trackable<Long> {


    companion object {

        const val MAIN_LAYOUT_ID = R.layout.currency_market_item_layout

    }




    override fun init(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?, parent: ViewGroup,
                      inflater: LayoutInflater, resources: CurrencyMarketResources?): ViewHolder {
        return ViewHolder(inflater.inflate(layout, parent, false)).apply {
            mLastPriceTs.setFactory {
                val textView = TextView(itemView.context)
                textView.setTextAppearanceCompat(resources!!.lastPriceTvStyleResId)
                ThemingUtil.CurrencyMarkets.Item.lastPrice(textView, resources.settings.theme)

                val layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.gravity = Gravity.CENTER
                textView.layoutParams = layoutParams

                textView
            }
            mLastPriceTs.inAnimation = itemView.context.loadAnimation(R.anim.pop_in_animation)
            mLastPriceTs.outAnimation = itemView.context.loadAnimation(R.anim.pop_out_animation)

            applyTheme(resources!!.settings.theme)
        }
    }


    override fun bind(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
                      viewHolder: ViewHolder, resources: CurrencyMarketResources?) {
        super.bind(adapter, viewHolder, resources)

        with(viewHolder) {
            val theme = resources!!.settings.theme
            val formatter = resources.formatter
            val baseCurrencySymbol = itemModel.baseCurrencySymbol
            val baseCurrencySymbolCharacterLimit = resources.baseCurrencySymbolCharacterLimit
            val shouldTruncateCurrencySymbol = (baseCurrencySymbolCharacterLimit != -1)

            mBaseCurrencySymbolTv.text = if(shouldTruncateCurrencySymbol) {
                baseCurrencySymbol.truncate(baseCurrencySymbolCharacterLimit)
            } else {
                baseCurrencySymbol
            }

            mQuoteCurrencySymbolTv.text = itemModel.quoteCurrencySymbol

            mVolumeTv.text = resources.volumeTemplate.format(formatter.formatVolume(itemModel.volume))
            mLastPriceTs.setText(formatter.formatFixedPrice(itemModel.lastPrice))
            mDailyPriceChangeTv.text = formatter.formatDailyPriceChange(itemModel.dailyPriceChange)

            with(ThemingUtil.CurrencyMarkets.Item) {
                when {
                    (itemModel.dailyPriceChange > 0.0) -> {
                        positiveStatusBar(mStatusBarView, theme)
                        positiveDailyPriceChange(mDailyPriceChangeTv, theme)
                    }
                    (itemModel.dailyPriceChange < 0.0) -> {
                        negativeStatusBar(mStatusBarView, theme)
                        negativeDailyPriceChange(mDailyPriceChangeTv, theme)
                    }
                    else -> {
                        neutralStatusBar(mStatusBarView, theme)
                        neutralDailyPriceChange(mDailyPriceChangeTv, theme)
                    }
                }
            }
        }
    }


    fun setOnItemClickListener(viewHolder: ViewHolder, position: Int,
                               listener: ((View, CurrencyMarketItem, Int) -> Unit)?) {
        viewHolder.itemView.setOnClickListener {
            listener?.invoke(it, this@CurrencyMarketItem, position)
        }
    }


    override fun getLayout(): Int = MAIN_LAYOUT_ID


    override fun getTrackKey(): Long = itemModel.id


    class ViewHolder(itemView: View) : BaseViewHolder<CurrencyMarket>(itemView) {

        val mStatusBarView: View = itemView.findViewById(R.id.statusBarView)

        val mBaseCurrencySymbolTv: TextView = itemView.findViewById(R.id.baseCurrencySymbolTv)
        val mSeparatorTv: TextView = itemView.findViewById(R.id.separatorTv)
        val mQuoteCurrencySymbolTv: TextView = itemView.findViewById(R.id.quoteCurrencySymbolTv)
        val mVolumeTv: TextView = itemView.findViewById(R.id.volumeTv)
        val mDailyPriceChangeTv: TextView = itemView.findViewById(R.id.dailyPriceChangeTv)

        val mLastPriceTs: TextSwitcher = itemView.findViewById(R.id.lastPriceTs)

        val mCardView: CardView = itemView.findViewById(R.id.cardView)


        override fun applyTheme(theme: Theme) {
            with(ThemingUtil.CurrencyMarkets.Item) {
                cardView(mCardView, theme)
                baseCurrencySymbol(mBaseCurrencySymbolTv, theme)
                separator(mSeparatorTv, theme)
                quoteCurrencySymbol(mQuoteCurrencySymbolTv, theme)
                volume(mVolumeTv, theme)
            }
        }

    }


}
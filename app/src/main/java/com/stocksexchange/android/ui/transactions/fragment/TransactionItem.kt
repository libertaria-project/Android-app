package com.stocksexchange.android.ui.transactions.fragment

import android.text.SpannableString
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
import com.stocksexchange.android.api.model.Transaction
import com.stocksexchange.android.api.model.TransactionStatuses.*
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.model.TransactionTypes
import com.stocksexchange.android.ui.base.adapters.recyclerview.BaseViewHolder
import com.stocksexchange.android.ui.transactions.fragment.TransactionResources.Companion.STRING_APPROVED
import com.stocksexchange.android.ui.transactions.fragment.TransactionResources.Companion.STRING_AWAITING_APPROVAL
import com.stocksexchange.android.ui.transactions.fragment.TransactionResources.Companion.STRING_AWAITING_CONFIRMATIONS
import com.stocksexchange.android.ui.transactions.fragment.TransactionResources.Companion.STRING_CANCELLED_BY_ADMIN
import com.stocksexchange.android.ui.transactions.fragment.TransactionResources.Companion.STRING_CANCELLED_BY_USER
import com.stocksexchange.android.ui.transactions.fragment.TransactionResources.Companion.STRING_EMAIL_SENT
import com.stocksexchange.android.ui.transactions.fragment.TransactionResources.Companion.STRING_FINISHED
import com.stocksexchange.android.ui.transactions.fragment.TransactionResources.Companion.STRING_NOT_CONFIRMED
import com.stocksexchange.android.ui.transactions.fragment.TransactionResources.Companion.STRING_PROCESSING
import com.stocksexchange.android.ui.transactions.fragment.TransactionResources.Companion.STRING_WITHDRAWAL_ERROR
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.utils.text.CustomLinkMovementMethod
import com.stocksexchange.android.ui.utils.text.SelectorSpan
import com.stocksexchange.android.ui.views.DottedMapView
import com.stocksexchange.android.utils.extensions.lastDigitIndex
import com.stocksexchange.android.utils.extensions.truncate

class TransactionItem(itemModel: Transaction) : BaseItem<
    Transaction,
    TransactionItem.ViewHolder,
    TransactionResources
>(itemModel), Trackable<Long> {


    companion object {

        const val MAIN_LAYOUT_ID = R.layout.transaction_item_layout

    }


    private enum class Styles {

        GREEN,
        RED,
        BLUE

    }




    override fun init(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?, parent: ViewGroup,
                      inflater: LayoutInflater, resources: TransactionResources?): ViewHolder {
        return ViewHolder(inflater.inflate(layout, parent, false)).apply {
            applyTheme(resources!!.settings.theme)
        }
    }


    override fun bind(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
                      viewHolder: ViewHolder, resources: TransactionResources?) {
        super.bind(adapter, viewHolder, resources)

        val theme = resources!!.settings.theme

        with(viewHolder) {
            val strings = resources.strings
            val status = itemModel.status.toLowerCase()

            when {
                status.startsWith(FINISHED.value) -> fillOutStatusTv(viewHolder, theme, strings[STRING_FINISHED], Styles.GREEN)
                status.startsWith(AWAITING_CONFIRMATIONS.value) -> fillOutStatusTv(viewHolder, theme, strings[STRING_AWAITING_CONFIRMATIONS], Styles.BLUE)
                status.startsWith(EMAIL_SENT.value) -> fillOutStatusTv(viewHolder, theme, strings[STRING_EMAIL_SENT], Styles.BLUE)
                status.startsWith(CANCELLED_BY_USER.value) -> fillOutStatusTv(viewHolder, theme, strings[STRING_CANCELLED_BY_USER], Styles.RED)
                status.startsWith(AWAITING_APPROVAL.value) -> fillOutStatusTv(viewHolder, theme, strings[STRING_AWAITING_APPROVAL], Styles.BLUE)
                status.startsWith(APPROVED.value) -> fillOutStatusTv(viewHolder, theme, strings[STRING_APPROVED], Styles.BLUE)
                status.startsWith(NOT_CONFIRMED.value) -> fillOutStatusTv(viewHolder, theme, strings[STRING_NOT_CONFIRMED], Styles.BLUE)
                status.startsWith(PROCESSING.value) -> fillOutStatusTv(viewHolder, theme, strings[STRING_PROCESSING], Styles.BLUE)
                status.startsWith(WITHDRAWAL_ERROR.value) -> fillOutStatusTv(viewHolder, theme, strings[STRING_WITHDRAWAL_ERROR], Styles.RED)
                status.startsWith(CANCELLED_BY_ADMIN.value) -> fillOutStatusTv(viewHolder, theme, strings[STRING_CANCELLED_BY_ADMIN], Styles.RED)
            }

            mCurrencyTv.text = itemModel.currency

            mAmountDmv.setValueText(resources.doubleFormatter.formatAmount(itemModel.amount))

            fillOutFeeDotv(viewHolder, resources)

            mDateDmv.setValueText(resources.timeFormatter.formatDate(itemModel.timestamp * 1000L))
        }
    }


    private fun fillOutStatusTv(viewHolder: ViewHolder, theme: Theme,
                                text: String, style: Styles) {
        with(ThemingUtil.Transactions.TransactionItem) {
            when(style) {
                Styles.GREEN -> positiveStatusButton(viewHolder.mStatusTv, theme)
                Styles.RED -> negativeStatusButton(viewHolder.mStatusTv, theme)
                Styles.BLUE -> neutralStatusButton(viewHolder.mStatusTv, theme)
            }

            viewHolder.mStatusTv.text = text
        }
    }


    private fun fillOutFeeDotv(viewHolder: ViewHolder, resources: TransactionResources?) {
        with(viewHolder) {
            val lastDigitIndex = itemModel.fee.lastDigitIndex()

            val fee = itemModel.fee.substring(0, (lastDigitIndex + 1)).toDoubleOrNull()
            val feeCurrency = itemModel.fee.substring((lastDigitIndex + 1), itemModel.fee.length)

            if(fee != null) {
                mFeeDmv.setValueText("${resources!!.doubleFormatter.formatTransactionFee(fee)} $feeCurrency")
            }
        }
    }


    fun setOnTransactionAddressClickListener(viewHolder: ViewHolder, resources: TransactionResources?,
                                             position: Int, listener: ((View, TransactionItem, Int) -> Unit)?) {
        with(viewHolder) {
            if(resources!!.transactionType == TransactionTypes.WITHDRAWALS) {
                val spannableString = SpannableString(itemModel.address.truncate(15))

                spannableString.setSpan(
                    getClickableSpanForEntry(resources, position, listener),
                    0, spannableString.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                mAddressDmv.setValueMovementMethod(CustomLinkMovementMethod())
                mAddressDmv.setValueText(spannableString)
                mAddressDmv.makeVisible()
            } else {
                mAddressDmv.makeGone()
            }
        }
    }


    fun setOnTransactionIdClickListener(viewHolder: ViewHolder, resources: TransactionResources?,
                                        position: Int, listener: ((View, TransactionItem, Int) -> Unit)?) {
        with(viewHolder) {
            if(itemModel.hasTransactionId()) {
                val spannableString = SpannableString(itemModel.transactionId.truncate(15))

                spannableString.setSpan(
                    getClickableSpanForEntry(resources, position, listener),
                    0, spannableString.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                mTransactionIdDmv.setValueMovementMethod(CustomLinkMovementMethod())
                mTransactionIdDmv.setValueText(spannableString)
                mTransactionIdDmv.makeVisible()
            } else {
                mTransactionIdDmv.makeGone()
            }
        }
    }


    private fun getClickableSpanForEntry(resources: TransactionResources?, position: Int,
                                         listener: ((View, TransactionItem, Int) -> Unit)?): SelectorSpan {
        return object : SelectorSpan(
            resources!!.settings.theme.generalTheme.linkReleasedStateBackgroundColor,
            resources.settings.theme.generalTheme.linkPressedStateBackgroundColor
        ) {

            override fun onClick(widget: View) {
                listener?.invoke(widget, this@TransactionItem, position)
            }

        }
    }


    override fun getLayout(): Int = MAIN_LAYOUT_ID


    override fun getTrackKey(): Long = itemModel.id


    class ViewHolder(itemView: View) : BaseViewHolder<Transaction>(itemView) {

        val mStatusTv: TextView = itemView.findViewById(R.id.statusTv)
        val mCurrencyTv: TextView = itemView.findViewById(R.id.currencyTv)

        val mAmountDmv: DottedMapView = itemView.findViewById(R.id.amountDmv)
        val mFeeDmv: DottedMapView = itemView.findViewById(R.id.feeDmv)
        val mAddressDmv: DottedMapView = itemView.findViewById(R.id.addressDmv)
        val mTransactionIdDmv: DottedMapView = itemView.findViewById(R.id.transactionIdDmv)
        val mDateDmv: DottedMapView = itemView.findViewById(R.id.dateDmv)

        val mCardView: CardView = itemView.findViewById(R.id.cardView)


        override fun applyTheme(theme: Theme) {
            with(ThemingUtil.Transactions.TransactionItem) {
                cardView(mCardView, theme)
                title(mCurrencyTv, theme)
                dottedMap(mAmountDmv, theme)
                dottedMap(mFeeDmv, theme)
                dottedMap(mAddressDmv, theme)
                dottedMap(mTransactionIdDmv, theme)
                dottedMap(mDateDmv, theme)
            }
        }

    }


}
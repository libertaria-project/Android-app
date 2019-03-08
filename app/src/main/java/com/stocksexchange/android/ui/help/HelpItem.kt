package com.stocksexchange.android.ui.help

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.Adapter
import com.arthurivanets.adapster.markers.ItemResources
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.model.Item
import com.stocksexchange.android.R
import com.stocksexchange.android.model.HelpItemModel
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.ui.base.adapters.recyclerview.BaseViewHolder
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.extensions.makeGone
import com.stocksexchange.android.ui.utils.extensions.makeVisible
import net.cachapa.expandablelayout.ExpandableLayout

class HelpItem(itemModel: HelpItemModel) : BaseItem<HelpItemModel, HelpItem.ViewHolder, HelpItemResources>(itemModel) {


    companion object {

        const val MAIN_LAYOUT_ID = R.layout.help_item_layout

    }




    override fun init(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?, parent: ViewGroup,
                      inflater: LayoutInflater, resources: HelpItemResources?): ViewHolder {
        return ViewHolder(inflater.inflate(layout, parent, false)).apply {
            val animationDuration = resources!!.animationDuration
            val animationInterpolator = resources.animationInterpolator

            mAnswerTv.animate().apply {
                duration = animationDuration.toLong()
                interpolator = animationInterpolator
            }

            mAnswerEl.duration = animationDuration
            mAnswerEl.setInterpolator(animationInterpolator)

            applyTheme(resources.settings.theme)
        }
    }


    override fun bind(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
                      viewHolder: ViewHolder, resources: HelpItemResources?) {
        super.bind(adapter, viewHolder, resources)

        with(viewHolder) {
            mQuestionTv.text = itemModel.questionText
            mAnswerTv.text = itemModel.answerText

            if(itemModel.hasButtonText()) {
                mActionTvBtn.text = itemModel.buttonText!!
                mActionTvBtn.makeVisible()
            } else {
                mActionTvBtn.makeGone()
            }

            if(itemModel.isCollapsed()) {
                collapse(this, false)
            } else {
                expand(this, false)
            }
        }
    }


    /**
     * Expands this item.
     *
     * @param viewHolder The view holder
     * @param animate Whether to animate the expansion
     */
    fun expand(viewHolder: ViewHolder, animate: Boolean) {
        with(viewHolder) {
            if(animate) {
                mArrowIv.rotation = 0f
                mArrowIv.animate().rotation(180f).start()

                mAnswerEl.expand()
            } else {
                mArrowIv.rotation = 180f
                mAnswerEl.expand(false)
            }
        }
    }


    /**
     * Collapses this item.
     *
     * @param viewHolder The view holder
     * @param animate Whether to animate the expansion
     */
    fun collapse(viewHolder: ViewHolder, animate: Boolean) {
        with(viewHolder) {
            if(animate) {
                mArrowIv.rotation = 180f
                mArrowIv.animate().rotation(0f).start()

                mAnswerEl.collapse()
            } else {
                mArrowIv.rotation = 0f
                mAnswerEl.collapse(false)
            }
        }
    }


    fun setOnItemClickListener(viewHolder: ViewHolder, position: Int,
                               listener: ((HelpItem.ViewHolder, HelpItem, Int) -> Unit)?) {
        viewHolder.itemView.setOnClickListener {
            listener?.invoke(viewHolder, this@HelpItem, position)
        }
    }


    fun setOnActionButtonClickListener(viewHolder: ViewHolder, position: Int,
                                       listener: ((HelpItem.ViewHolder, HelpItem, Int) -> Unit)?) {
        viewHolder.mActionTvBtn.setOnClickListener {
            listener?.invoke(viewHolder, this@HelpItem, position)
        }
    }


    override fun getLayout(): Int = MAIN_LAYOUT_ID


    class ViewHolder(itemView: View) : BaseViewHolder<HelpItemModel>(itemView) {

        val mQuestionTv: TextView = itemView.findViewById(R.id.questionTv)
        val mAnswerTv: TextView = itemView.findViewById(R.id.answerTv)
        val mActionTvBtn: TextView = itemView.findViewById(R.id.actionTvBtn)

        val mArrowIv: ImageView = itemView.findViewById(R.id.arrowIv)

        val mAnswerEl: ExpandableLayout = itemView.findViewById(R.id.answerEl)

        val mCardView: CardView = itemView.findViewById(R.id.cardView)


        override fun applyTheme(theme: Theme) {
            with(ThemingUtil.Help.HelpItem) {
                cardView(mCardView, theme)
                question(mQuestionTv, theme)
                answer(mAnswerTv, theme)
                arrowIcon(mArrowIv, theme)
                actionButton(mActionTvBtn, theme)
            }
        }

    }


}
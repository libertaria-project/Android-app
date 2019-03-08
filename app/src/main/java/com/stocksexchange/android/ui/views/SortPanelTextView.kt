package com.stocksexchange.android.ui.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.AppCompatTextView
import com.stocksexchange.android.R
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator.Orders
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator.Orders.ASC
import com.stocksexchange.android.model.comparators.CurrencyMarketComparator.Orders.DESC
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.utils.interfaces.Themable

/**
 * A view holding functionality for sorting currency markets.
 */
class SortPanelTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), Themable<Theme> {


    companion object {

        private const val ANIMATION_DURATION = 300L

        private val INTERPOLATOR = LinearInterpolator()

    }


    private lateinit var mArrowDrawables: Map<Orders, Drawable?>

    private lateinit var mTheme: Theme

    private var mValueAnimator: ValueAnimator = ValueAnimator.ofInt(0, 10000)

    /**
     * Current comparator associated with this TextView.
     */
    lateinit var comparator: CurrencyMarketComparator




    init {
        mValueAnimator.addUpdateListener {
            getRightDrawable()?.level = (it.animatedValue as Int)
        }
        mValueAnimator.interpolator = INTERPOLATOR
        mValueAnimator.duration = ANIMATION_DURATION
    }



    override fun setSelected(isSelected: Boolean) {
        super.setSelected(isSelected)

        if(isSelected) {
            setTypefaceStyle(Typeface.BOLD)
            ThemingUtil.SortPanel.selectedTitle(this, mTheme)
            updateDrawable(false)
        } else {
            setTypefaceStyle(Typeface.NORMAL)
            ThemingUtil.SortPanel.unselectedTitle(this, mTheme)
            cancelAnimation()
            clearDrawable()
        }
    }


    override fun applyTheme(theme: Theme) {
        mTheme = theme

        mArrowDrawables = mapOf(
            ASC to context.getColoredCompatDrawable(R.drawable.arrow_down_rotation_drawable, theme.generalTheme.primaryTextColor),
            DESC to context.getColoredCompatDrawable(R.drawable.arrow_up_rotation_drawable, theme.generalTheme.primaryTextColor)
        )

        ThemingUtil.SortPanel.unselectedTitle(this, theme)
    }


    /**
     * Switches the comparator, that is, exchanges the ascending
     * version for the descending one or vice-versa as well as
     * updates the compound drawable.
     */
    fun switchComparator() {
        comparator = !comparator
        updateDrawable(true)
    }


    private fun updateDrawable(animate: Boolean) {
        val newRightDrawable = mArrowDrawables[comparator.order]

        if(animate) {
            cancelAnimation()

            mValueAnimator.removeAllListeners()
            mValueAnimator.addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    getRightDrawable()?.level = 0
                    setRightDrawable(newRightDrawable)
                }

            })
            mValueAnimator.start()
        } else {
            setRightDrawable(newRightDrawable)
        }
    }


    private fun cancelAnimation() {
        if(mValueAnimator.isRunning) {
            mValueAnimator.cancel()
        }
    }


}
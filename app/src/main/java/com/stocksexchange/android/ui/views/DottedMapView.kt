package com.stocksexchange.android.ui.views

import android.content.Context
import android.graphics.Color
import android.text.method.MovementMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.stocksexchange.android.R
import com.stocksexchange.android.ui.utils.extensions.crossFadeText
import com.stocksexchange.android.ui.utils.extensions.getDottedLineDrawable
import com.stocksexchange.android.ui.utils.extensions.spToPx
import kotlinx.android.synthetic.main.dotted_map_view_layout.view.*

/**
 * A map view with two text fields separated by a dotted drawable.
 */
class DottedMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {


    companion object {

        private const val DEFAULT_TEXT_SIZE = 14f
        private const val DEFAULT_TEXT_COLOR = Color.BLACK

        private const val DEFAULT_ANIMATION_DURATION = 300L

        private val DEFAULT_ANIMATION_INTERPOLATOR = LinearInterpolator()

    }




    init {
        View.inflate(context, R.layout.dotted_map_view_layout, this)

        context.theme.obtainStyledAttributes(attrs, R.styleable.DottedMapView, defStyleAttr, 0).run {
            try {
                // Title
                setTitleText(getString(R.styleable.DottedMapView_title) ?: "")
                setTitleSize(getDimension(R.styleable.DottedMapView_titleSize, spToPx(DEFAULT_TEXT_SIZE)))
                setTitleColor(getColor(R.styleable.DottedMapView_titleColor, DEFAULT_TEXT_COLOR))

                // Text
                setValueText(getString(R.styleable.DottedMapView_value) ?: "")
                setValueSize(getDimension(R.styleable.DottedMapView_valueSize, spToPx(DEFAULT_TEXT_SIZE)))
                setValueColor(getColor(R.styleable.DottedMapView_valueColor, DEFAULT_TEXT_COLOR))

                // Separator
                setSeparatorColor(getColor(R.styleable.DottedMapView_separatorColor, DEFAULT_TEXT_COLOR))
            } finally {
                recycle()
            }
        }
    }


    /**
     * Sets a text of the title.
     *
     * @param text The text to set
     */
    fun setTitleText(text: CharSequence) {
        titleTv.text = text
    }


    /**
     * Sets a text of the title by cross fading it with the previous one.
     * Primarily used for changing or updating the text.
     *
     * @param text The text to set
     * @param animationDuration The duration of the animation.
     * By default uses [DEFAULT_ANIMATION_DURATION].
     * @param animationInterpolator The interpolator to use.
     * By default uses [DEFAULT_ANIMATION_INTERPOLATOR].
     */
    fun setTitleTextAnimated(text: CharSequence, animationDuration: Long = DEFAULT_ANIMATION_DURATION,
                             animationInterpolator: Interpolator = DEFAULT_ANIMATION_INTERPOLATOR) {
        titleTv.crossFadeText(text, animationDuration, animationInterpolator)
    }


    /**
     * Sets a text size of the title.
     *
     * @param textSize The text size to set
     * @param unit The text size's unit. Default is SP.
     */
    fun setTitleSize(textSize: Float, unit: Int = TypedValue.COMPLEX_UNIT_PX) {
        titleTv.setTextSize(unit, textSize)
    }


    /**
     * Sets a color of the title.
     *
     * @param color The color to set
     */
    fun setTitleColor(color: Int) {
        titleTv.setTextColor(color)
    }


    /**
     * Returns [TextView] of the title.
     *
     * @return The text view of the title
     */
    fun getTitleTv(): TextView {
        return titleTv
    }


    /**
     * Sets a text of the value.
     *
     * @param text The text to set
     */
    fun setValueText(text: CharSequence) {
        valueTv.text = text
    }


    /**
     * Sets a text of the value by cross fading it with the previous one.
     * Primarily used for changing or updating the text.
     *
     * @param text The text to set
     * @param animationDuration The duration of the animation.
     * By default uses [DEFAULT_ANIMATION_DURATION].
     * @param animationInterpolator The interpolator to use.
     * By default uses [DEFAULT_ANIMATION_INTERPOLATOR].
     */
    fun setValueTextAnimated(text: CharSequence, animationDuration: Long = DEFAULT_ANIMATION_DURATION,
                             animationInterpolator: Interpolator = DEFAULT_ANIMATION_INTERPOLATOR) {
        valueTv.crossFadeText(text, animationDuration, animationInterpolator)
    }


    /**
     * Sets a text size of the value.
     *
     * @param textSize The text size to set
     * @param unit The text size's unit. Default is SP.
     */
    fun setValueSize(textSize: Float, unit: Int = TypedValue.COMPLEX_UNIT_PX) {
        valueTv.setTextSize(unit, textSize)
    }


    /**
     * Sets a color of the value.
     *
     * @param color The color to set
     */
    fun setValueColor(color: Int) {
        valueTv.setTextColor(color)
    }


    /**
     * Sets a movement method of the value.
     *
     * @param method The movement method to set
     */
    fun setValueMovementMethod(method: MovementMethod) {
        valueTv.movementMethod = method
    }


    /**
     * Returns [TextView] of the value.
     *
     * @return The text view of the value
     */
    fun getValueTv(): TextView {
        return valueTv
    }


    /**
     * Sets a color of the separator.
     *
     * @param color The color to set
     */
    fun setSeparatorColor(@ColorInt color: Int) {
        separatorIv.setImageDrawable(context.getDottedLineDrawable(color))
    }


    /**
     * Returns [ImageView] of the separator.
     *
     * @return The image view of the value
     */
    fun getSeparatorIv(): ImageView {
        return separatorIv
    }


}
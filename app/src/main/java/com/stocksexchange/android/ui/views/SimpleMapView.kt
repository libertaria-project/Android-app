package com.stocksexchange.android.ui.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.stocksexchange.android.R
import com.stocksexchange.android.ui.utils.extensions.setTypefaceStyle
import com.stocksexchange.android.ui.utils.extensions.spToPx
import kotlinx.android.synthetic.main.simple_map_view_layout.view.*

/**
 * A map view with two text fields separated by a colon.
 */
class SimpleMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    companion object {

        private const val DEFAULT_TEXT_SIZE = 14f
        private const val DEFAULT_TEXT_COLOR = Color.WHITE

    }




    init {
        View.inflate(context, R.layout.simple_map_view_layout, this)
        orientation = HORIZONTAL

        context.theme.obtainStyledAttributes(attrs, R.styleable.SimpleMapView, defStyleAttr, 0).run {
            try {
                // Title
                setTitleText(getString(R.styleable.SimpleMapView_title) ?: "")
                setTitleSize(getDimension(R.styleable.SimpleMapView_titleSize, spToPx(DEFAULT_TEXT_SIZE)))
                setTitleColor(getColor(R.styleable.SimpleMapView_titleColor, DEFAULT_TEXT_COLOR))

                // Text
                setValueText(getString(R.styleable.SimpleMapView_value) ?: "")
                setValueSize(getDimension(R.styleable.SimpleMapView_valueSize, spToPx(DEFAULT_TEXT_SIZE)))
                setValueColor(getColor(R.styleable.SimpleMapView_valueColor, DEFAULT_TEXT_COLOR))
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
    fun setTitleText(text: String) {
        titleTv.text = text
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
    fun setTitleColor(@ColorInt color: Int) {
        titleTv.setTextColor(color)
    }


    /**
     * Sets a typeface style of the title.
     *
     * @param style The style to set
     */
    fun setTitleTypfaceStyle(style: Int) {
        titleTv.setTypefaceStyle(style)
    }


    /**
     * Returns text of the title.
     *
     * @return The text of the title
     */
    fun getTitleText(): CharSequence {
        return titleTv.text
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
    fun setValueText(text: String) {
        valueTv.text = text
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
    fun setValueColor(@ColorInt color: Int) {
        valueTv.setTextColor(color)
    }


    /**
     * Sets a typeface style of the value.
     *
     * @param style The style to set
     */
    fun setValueTypefaceStyle(style: Int) {
        valueTv.setTypefaceStyle(style)
    }


    /**
     * Gets a value text.
     *
     * @return The value text
     */
    fun getValueText(): CharSequence {
        return valueTv.text
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
     * Sets a color of the title and the value.
     *
     * @param color The color to set
     */
    fun setTextColor(@ColorInt color: Int) {
        setTitleColor(color)
        setValueColor(color)
    }


}
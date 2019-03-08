package com.stocksexchange.android.ui.views

import android.content.Context
import android.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import com.stocksexchange.android.R
import com.stocksexchange.android.ui.utils.extensions.dimenInPx
import com.stocksexchange.android.ui.utils.extensions.setStrokeColor
import com.stocksexchange.android.ui.utils.extensions.spToPx
import com.stocksexchange.android.utils.helpers.containsBits
import kotlinx.android.synthetic.main.bordered_map_view_button_layout.view.*

/**
 * A map view with bordering functionality.
 */
class BorderedMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    companion object {

        private const val DEFAULT_TITLE = ""
        private const val DEFAULT_TEXT_SIZE = 14f
        private const val DEFAULT_TEXT_COLOR = Color.WHITE

        const val BORDER_NONE = 0
        const val BORDER_LEFT = 1
        const val BORDER_TOP = 2
        const val BORDER_RIGHT = 4
        const val BORDER_BOTTOM = 8
        const val BORDER_ALL = 15

    }




    init {
        View.inflate(context, R.layout.bordered_map_view_button_layout, this)
        isClickable = true

        context.theme.obtainStyledAttributes(attrs, R.styleable.BorderedMapView, defStyleAttr, 0).run {
            try {
                // Title
                setTitleText(getString(R.styleable.BorderedMapView_title) ?: DEFAULT_TITLE)
                setTitleSize(getDimension(R.styleable.BorderedMapView_titleSize, spToPx(DEFAULT_TEXT_SIZE)))
                setTitleColor(getColor(R.styleable.BorderedMapView_titleColor, DEFAULT_TEXT_COLOR))

                // Text
                setSubtitleText(getString(R.styleable.BorderedMapView_subtitle) ?: DEFAULT_TITLE)
                setSubtitleSize(getDimension(R.styleable.BorderedMapView_subtitleSize, spToPx(DEFAULT_TEXT_SIZE)))
                setSubtitleColor(getColor(R.styleable.BorderedMapView_subtitleColor, DEFAULT_TEXT_COLOR))

                // Borders
                setBorders(getInteger(R.styleable.BorderedMapView_borders, BORDER_NONE))
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
     * Enables or disables a multiline text of the title.
     *
     * @param isMultilineTextEnabled Whether to enable or disable the multiline text
     */
    fun setTitleMultilineTextEnabled(isMultilineTextEnabled: Boolean) {
        with(titleTv) {
            minLines = if(isMultilineTextEnabled) 0 else 1
            maxLines = if(isMultilineTextEnabled) Integer.MAX_VALUE else 1
        }
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
     * Sets a text of the subtitle.
     *
     * @param text The text to set
     */
    fun setSubtitleText(text: CharSequence) {
        subtitleTv.text = text
    }


    /**
     * Sets a text size of the subtitle.
     *
     * @param textSize The text size to set
     * @param unit The text size's unit. Default is SP.
     */
    fun setSubtitleSize(textSize: Float, unit: Int = TypedValue.COMPLEX_UNIT_PX) {
        subtitleTv.setTextSize(unit, textSize)
    }


    /**
     * Sets a color of the subtitle.
     *
     * @param color The color to set
     */
    fun setSubtitleColor(color: Int) {
        subtitleTv.setTextColor(color)
    }


    /**
     * Enables or disables a multiline text of the subtitle.
     *
     * @param isMultilineTextEnabled Whether to enable or disable the multiline text
     */
    fun setSubtitleMultilineTextEnabled(isMultilineTextEnabled: Boolean) {
        with(subtitleTv) {
            minLines = if(isMultilineTextEnabled) 0 else 1
            maxLines = if(isMultilineTextEnabled) Integer.MAX_VALUE else 1
        }
    }


    /**
     * Returns [TextView] of the subtitle.
     *
     * @return The text view of the subtitle
     */
    fun getSubtitleTv(): TextView {
        return subtitleTv
    }


    /**
     * Sets borders of this button.
     *
     * @param borders The borders to set
     */
    fun setBorders(borders: Int) {
        if(borders == BORDER_NONE) {
            return
        }

        setBackgroundResource(when {
            containsBits(borders, BORDER_ALL) -> R.drawable.bordered_map_view_all_borders_drawable
            containsBits(borders, BORDER_LEFT or BORDER_TOP or BORDER_BOTTOM) -> R.drawable.bordered_map_view_left_top_bottom_borders_drawable
            containsBits(borders, BORDER_TOP or BORDER_RIGHT or BORDER_BOTTOM) -> R.drawable.bordered_map_view_top_right_bottom_borders_drawable
            containsBits(borders, BORDER_BOTTOM or BORDER_TOP) -> R.drawable.bordered_map_view_vertical_borders_drawable
            containsBits(borders, BORDER_LEFT or BORDER_TOP) -> R.drawable.bordered_map_view_left_top_borders_drawable
            containsBits(borders, BORDER_TOP or BORDER_RIGHT) -> R.drawable.bordered_map_view_top_right_borders_drawable

            else -> R.drawable.bordered_map_view_bottom_border_drawable
        })
    }


    /**
     * Sets the color of the borders.
     *
     * @param color The color to set
     */
    fun setBordersColor(@ColorInt color: Int) {
        if(background == null) {
            return
        }

        background.setStrokeColor(
            dimenInPx(R.dimen.bordered_map_view_border_width),
            color
        )
    }


}
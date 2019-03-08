package com.stocksexchange.android.ui.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.stocksexchange.android.R
import com.stocksexchange.android.ui.utils.extensions.*
import kotlinx.android.synthetic.main.info_view_layout.view.*

/**
 * A view container showing an informational view
 * in case there is no data or error has occurred.
 */
class InfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    init {
        View.inflate(context, R.layout.info_view_layout, this)

        orientation = VERTICAL

        setHorizontalPadding(dimenInPx(R.dimen.info_view_horizontal_padding))
        setVerticalPadding(dimenInPx(R.dimen.info_view_vertical_padding))
    }


    /**
     * Shows the icon.
     */
    fun showIcon() {
        iconIv.makeVisible()
    }


    /**
     * Hides the icon.
     */
    fun hideIcon() {
        iconIv.makeGone()
    }


    /**
     * Sets a drawable of the icon.
     *
     * @param drawable The drawable to set
     */
    fun setIcon(drawable: Drawable?) {
        iconIv.setImageDrawable(drawable)
    }


    /**
     * Sets a color of the drawable.
     *
     * @param color The color to set
     */
    fun setIconColor(@ColorInt color: Int) {
        iconIv.setColor(color)
    }


    /**
     * Returns [ImageView] of the icon.
     *
     * @return The image view of the icon
     */
    fun getIconIv(): ImageView {
        return iconIv
    }


    /**
     * Shows the caption.
     */
    fun showCaption() {
        captionTv.makeVisible()
    }


    /**
     * Hides the caption.
     */
    fun hideCaption() {
        captionTv.makeGone()
    }


    /**
     * Sets a text of the caption
     *
     * @param text The text to set
     */
    fun setCaption(text: String) {
        captionTv.text = text
    }


    /**
     * Sets a color of the caption.
     *
     * @param color The color to set
     */
    fun setCaptionColor(@ColorInt color: Int) {
        captionTv.setTextColor(color)
    }


    /**
     * Returns [TextView] of the caption.
     *
     * @return The text view of the caption
     */
    fun getCaptionTv(): TextView {
        return captionTv
    }


    /**
     * Sets a color of the icon and the caption.
     *
     * @param color The color to set
     */
    fun setColor(@ColorInt color: Int) {
        setIconColor(color)
        setCaptionColor(color)
    }


}
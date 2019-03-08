package com.stocksexchange.android.ui.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.stocksexchange.android.R
import com.stocksexchange.android.ui.utils.extensions.dimenInPx
import com.stocksexchange.android.ui.utils.extensions.setColor
import com.stocksexchange.android.ui.utils.extensions.setPadding
import kotlinx.android.synthetic.main.feature_view_layout.view.*

/**
 * A view representing a feature with an icon and a title.
 */
class FeatureView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    companion object {

        private const val DEFAULT_TITLE = ""

        private const val DEFAULT_ICON_COLOR = Color.WHITE
        private const val DEFAULT_CAPTION_COLOR = Color.WHITE

    }




    init {
        View.inflate(context, R.layout.feature_view_layout, this)

        orientation = VERTICAL
        setPadding(dimenInPx(R.dimen.feature_view_padding))

        context.theme.obtainStyledAttributes(attrs, R.styleable.FeatureView, defStyleAttr, 0).run {
            try {
                // Values
                setIcon(getDrawable(R.styleable.FeatureView_icon))
                setCaption(getString(R.styleable.FeatureView_caption) ?: DEFAULT_TITLE)

                // Colors
                setIconColor(getColor(R.styleable.FeatureView_iconColor, DEFAULT_ICON_COLOR))
                setCaptionColor(getColor(R.styleable.FeatureView_captionColor, DEFAULT_CAPTION_COLOR))
            } finally {
                recycle()
            }
        }
    }


    /**
     * Sets a drawable icon.
     *
     * @param drawable The drawable to set
     */
    fun setIcon(drawable: Drawable?) {
        iconIv.setImageDrawable(drawable)
    }


    /**
     * Sets a color of the icon.
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
     * Sets a caption string.
     *
     * @param caption The caption to set
     */
    fun setCaption(caption: String) {
        captionTv.text = caption
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


}
package com.stocksexchange.android.ui.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.stocksexchange.android.R
import com.stocksexchange.android.ui.utils.extensions.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*

/**
 * A custom toolbar view.
 */
class Toolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {


    companion object {

        private const val DEFAULT_BUTTON_DRAWABLE_COLOR = Color.WHITE
        private const val DEFAULT_PROGRESS_BAR_COLOR = Color.WHITE
        private const val DEFAULT_TITLE_COLOR = Color.WHITE

        private const val DEFAULT_TITLE = ""

    }




    init {
        View.inflate(context, R.layout.toolbar_layout, this)

        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomToolbar, defStyleAttr, 0).run {
            try {
                // Colors
                setButtonDrawableColor(getColor(R.styleable.CustomToolbar_buttonDrawableColor, DEFAULT_BUTTON_DRAWABLE_COLOR))
                setProgressBarColor(getColor(R.styleable.CustomToolbar_progressBarColor, DEFAULT_PROGRESS_BAR_COLOR))
                setTitleColor(getColor(R.styleable.CustomToolbar_titleColor, DEFAULT_TITLE_COLOR))

                // Title
                setTitleText(getString(R.styleable.CustomToolbar_title) ?: DEFAULT_TITLE)

                // Drawables
                if(hasValue(R.styleable.CustomToolbar_leftButtonDrawable)) {
                    setLeftButtonDrawable(getDrawable(R.styleable.CustomToolbar_leftButtonDrawable))
                }

                if(hasValue(R.styleable.CustomToolbar_preRightButtonDrawable)) {
                    setPreRightButtonDrawable(getDrawable(R.styleable.CustomToolbar_preRightButtonDrawable))
                }

                if(hasValue(R.styleable.CustomToolbar_rightButtonDrawable)) {
                    setRightButtonDrawable(getDrawable(R.styleable.CustomToolbar_rightButtonDrawable))
                }
            } finally {
                recycle()
            }
        }
    }


    /**
     * Makes left button visible.
     */
    fun showLeftButton() {
        if(leftBtnIv.isVisible()) {
            return
        }

        leftBtnIv.makeVisible()
    }


    /**
     * Makes left button invisible.
     */
    fun hideLeftButton() {
        if(leftBtnIv.isGone()) {
            return
        }

        leftBtnIv.makeGone()
    }


    /**
     * Makes pre right button visible.
     */
    fun showPreRightButton() {
        if(preRightBtnIv.isVisible()) {
            return
        }

        preRightBtnIv.makeVisible()
    }


    /**
     * Makes pre right button invisible.
     */
    fun hidePreRightButton() {
        if(preRightBtnIv.isGone()) {
            return
        }

        preRightBtnIv.makeGone()
    }


    /**
     * Makes the right button visible.
     */
    fun showRightButton() {
        if(rightBtnIv.isVisible()) {
            return
        }

        rightBtnIv.makeVisible()
    }


    /**
     * Makes the right button invisible.
     */
    fun hideRightButton() {
        if(rightBtnIv.isGone()) {
            return
        }

        rightBtnIv.makeGone()
    }


    /**
     * Makes the progress bar visible.
     */
    fun showProgressBar() {
        if(progressBar.isVisible()) {
            return
        }

        progressBar.makeVisible()
    }


    /**
     * Makes the progress bar invisible.
     */
    fun hideProgressBar() {
        if(progressBar.isGone()) {
            return
        }

        progressBar.makeGone()
    }


    /**
     * Sets a color of the button drawables.
     *
     * @param color The color to set
     */
    fun setButtonDrawableColor(@ColorInt color: Int) {
        setLeftButtonDrawableColor(color)
        setPreRightButtonDrawableColor(color)
        setRightButtonDrawableColor(color)
    }


    /**
     * Sets a color of the left button's drawable.
     *
     * @param color The color to set
     */
    fun setLeftButtonDrawableColor(@ColorInt color: Int) {
        leftBtnIv.setColor(color)
    }


    /**
     * Sets a color of the pre right button's drawable.
     *
     * @param color The color to set
     */
    fun setPreRightButtonDrawableColor(@ColorInt color: Int) {
        preRightBtnIv.setColor(color)
    }


    /**
     * Sets a color of the right button's drawable.
     *
     * @param color The color to set
     */
    fun setRightButtonDrawableColor(@ColorInt color: Int) {
        rightBtnIv.setColor(color)
    }


    /**
     * Sets a color of the progress bar.
     *
     * @param color The color to set
     */
    fun setProgressBarColor(@ColorInt color: Int) {
        progressBar.setColor(color)
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
     * Sets a text of the title.
     *
     * @param text The text to set
     */
    fun setTitleText(text: String) {
        titleTv.text = text
    }


    /**
     * Sets a drawable of the left button.
     *
     * @param drawable The drawable to set
     * @param showIfHidden Whether to show the button if it is hidden.
     * Default is true.
     */
    fun setLeftButtonDrawable(drawable: Drawable?, showIfHidden: Boolean = true) {
        leftBtnIv.setImageDrawable(drawable)

        if(showIfHidden) {
            showLeftButton()
        }
    }


    /**
     * Sets a drawable of the pre right button.
     *
     * @param drawable The drawable to set
     * @param showIfHidden Whether to show the button if it is hidden.
     * Default is true.
     */
    fun setPreRightButtonDrawable(drawable: Drawable?, showIfHidden: Boolean = true) {
        preRightBtnIv.setImageDrawable(drawable)

        if(showIfHidden) {
            showPreRightButton()
        }
    }


    /**
     * Sets a drawable of the right button.
     *
     * @param drawable The drawable to set
     * @param showIfHidden Whether to show the button if it is hidden.
     * Default is true.
     */
    fun setRightButtonDrawable(drawable: Drawable?, showIfHidden: Boolean = true) {
        rightBtnIv.setImageDrawable(drawable)

        if(showIfHidden) {
            showRightButton()
        }
    }


    /**
     * Sets a click listener of the left button.
     *
     * @param listener The listener to set
     */
    fun setOnLeftButtonClickListener(listener: (View) -> Unit) {
        leftBtnIv.setOnClickListener(listener)
    }


    /**
     * Sets a click listener of the pre right button.
     *
     * @param listener The listener to set
     */
    fun setOnPreRightButtonClickListener(listener: (View) -> Unit) {
        preRightBtnIv.setOnClickListener(listener)
    }


    /**
     * Sets a click listener of the right button.
     *
     * @param listener The listener to set
     */
    fun setOnRightButtonClickListener(listener: (View) -> Unit) {
        rightBtnIv.setOnClickListener(listener)
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
     * Returns [ImageView] of the left button.
     *
     * @return The image view of the left button
     */
    fun getLeftButtonIv(): ImageView {
        return leftBtnIv
    }


    /**
     * Returns [ImageView] of the pre right button.
     *
     * @return The image view of the pre right button
     */
    fun getPreRightButtonIv(): ImageView {
        return preRightBtnIv
    }


    /**
     * Returns [ImageView] of the right button.
     *
     * @return The image view of the right button
     */
    fun getRightButtonIv(): ImageView {
        return rightBtnIv
    }


    /**
     * Returns progress bar.
     *
     * @return The progress bar
     */
    fun getProgressBar(): ProgressBar {
        return progressBar
    }


}
package com.stocksexchange.android.ui.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.SwitchCompat
import com.stocksexchange.android.R
import com.stocksexchange.android.ui.utils.extensions.setColor
import com.stocksexchange.android.ui.utils.extensions.spToPx
import kotlinx.android.synthetic.main.switch_options_view_layout.view.*

/**
 * A group view representing a switch and its two options.
 */
class SwitchOptionsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    companion object {

        private const val DEFAULT_TEXT_SIZE = 16f

        private const val DEFAULT_TEXT_COLOR = Color.WHITE
        private const val DEFAULT_SWITCH_COLOR = DEFAULT_TEXT_COLOR

    }


    private var mShouldNotifyListener: Boolean = false

    /**
     * A listener to invoke whenever an option is selected.
     */
    var onOptionChangeListener: OnOptionSelectionListener? = null




    init {
        orientation = HORIZONTAL

        View.inflate(context, R.layout.switch_options_view_layout, this)

        initSwitch()
        fetchAttributes(attrs, defStyleAttr)
    }


    private fun initSwitch() {
        switchView.setOnCheckedChangeListener { _, isChecked ->
            if(!mShouldNotifyListener) {
                return@setOnCheckedChangeListener
            }

            if(isChecked) {
                onOptionChangeListener?.onRightOptionSelected()
            } else {
                onOptionChangeListener?.onLeftOptionSelected()
            }
        }
    }


    private fun fetchAttributes(attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.SwitchOptionsView, defStyleAttr, 0).run {
            try {
                setLeftOptionTitleText(getString(R.styleable.SwitchOptionsView_leftOptionTitleText) ?: "")
                setRightOptionTitleText(getString(R.styleable.SwitchOptionsView_rightOptionTitleText) ?: "")

                setOptionsTitleTextSize(getDimension(R.styleable.SwitchOptionsView_optionsTitleTextSize, spToPx(DEFAULT_TEXT_SIZE)))
                setOptionsTitleTextColor(getColor(R.styleable.SwitchOptionsView_optionsTitleTextColor, DEFAULT_TEXT_COLOR))

                setSwitchColor(getColor(R.styleable.SwitchOptionsView_switchColor, DEFAULT_SWITCH_COLOR))
            } finally {
                recycle()
            }
        }
    }


    /**
     * Sets a text of the left option.
     *
     * @param text The text to set
     */
    fun setLeftOptionTitleText(text: String) {
        leftOptionTv.text = text
    }


    /**
     * Sets a text of the right option.
     *
     * @param text The text to set
     */
    fun setRightOptionTitleText(text: String) {
        rightOptionTv.text = text
    }


    /**
     * Sets a title text size of the options.
     *
     * @param textSize The text size to set
     * @param unit The text size's unit. Default is SP.
     */
    fun setOptionsTitleTextSize(textSize: Float, unit: Int = TypedValue.COMPLEX_UNIT_PX) {
        leftOptionTv.setTextSize(unit, textSize)
        rightOptionTv.setTextSize(unit, textSize)
    }


    /**
     * Sets a title text color of the options.
     *
     * @param color The color to set
     */
    fun setOptionsTitleTextColor(@ColorInt color: Int) {
        leftOptionTv.setTextColor(color)
        rightOptionTv.setTextColor(color)
    }


    /**
     * Sets a color of the switch.
     *
     * @param color The color to set
     */
    fun setSwitchColor(@ColorInt color: Int) {
        switchView.setColor(color)
    }


    /**
     * Makes the left option selected.
     *
     * @param shouldNotifyListener Whether to notify the listener or not
     */
    fun setLeftOptionSelected(shouldNotifyListener: Boolean = true) {
        mShouldNotifyListener = shouldNotifyListener
        switchView.isChecked = false
        mShouldNotifyListener = true
    }


    /**
     * Makes the right option selected.
     *
     * @param shouldNotifyListener Whether to notify the listener or not
     */
    fun setRightOptionSelected(shouldNotifyListener: Boolean = true) {
        mShouldNotifyListener = shouldNotifyListener
        switchView.isChecked = true
        mShouldNotifyListener = true
    }


    /**
     * Checks whether the left option is selected or not.
     *
     * @return true if selected; false otherwise
     */
    fun isLeftOptionSelected(): Boolean {
        return !isRightOptionSelected()
    }


    /**
     * Checks whether the right option is selected or not.
     *
     * @return true if selected; false otherwise
     */
    fun isRightOptionSelected(): Boolean {
        return switchView.isChecked
    }


    /**
     * Returns [SwitchCompat] of the switch.
     *
     * @return The switch compat of the switch
     */
    fun getSwitchView(): SwitchCompat {
        return switchView
    }


    /**
     * Returns [TextView] of the left option.
     *
     * @return The text view of the left option
     */
    fun getLeftOptionTv(): TextView {
        return leftOptionTv
    }


    /**
     * Returns [TextView] of the right option.
     *
     * @return The text view of the right option
     */
    fun getRightOptionTv(): TextView {
        return rightOptionTv
    }


    interface OnOptionSelectionListener {

        fun onLeftOptionSelected() {
            // Stub
        }

        fun onRightOptionSelected() {
            // Stub
        }

    }


}
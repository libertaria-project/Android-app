package com.stocksexchange.android.ui.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.KeyListener
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.stocksexchange.android.R
import com.stocksexchange.android.ui.utils.extensions.getContent
import com.stocksexchange.android.ui.utils.extensions.isEmpty
import com.stocksexchange.android.ui.utils.extensions.setCursorDrawable
import com.stocksexchange.android.ui.utils.extensions.spToPx
import kotlinx.android.synthetic.main.labeled_edit_text_layout.view.*

/**
 * An edit text with labeling functionality.
 */
class LabeledEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {


    companion object {

        private const val DEFAULT_TITLE = ""
        private const val DEFAULT_TEXT_SIZE = 14f
        private const val DEFAULT_TEXT_COLOR = Color.WHITE

    }




    init {
        View.inflate(context, R.layout.labeled_edit_text_layout, this)

        context.theme.obtainStyledAttributes(attrs, R.styleable.LabeledEditText, defStyleAttr, 0).run {
            try {
                // Title
                setTitleText(getString(R.styleable.LabeledEditText_title) ?: DEFAULT_TITLE)
                setTitleSize(getDimension(R.styleable.LabeledEditText_titleSize, spToPx(DEFAULT_TEXT_SIZE)))
                setTitleColor(getColor(R.styleable.LabeledEditText_titleColor, DEFAULT_TEXT_COLOR))

                // Input
                setInputText(getString(R.styleable.LabeledEditText_inputText) ?: DEFAULT_TITLE)
                setInputHint(getString(R.styleable.LabeledEditText_inputHint) ?: DEFAULT_TITLE)
                setInputSize(getDimension(R.styleable.LabeledEditText_inputSize, spToPx(DEFAULT_TEXT_SIZE)))
                setInputTextColor(getColor(R.styleable.LabeledEditText_inputTextColor, DEFAULT_TEXT_COLOR))
                setInputHintColor(getColor(R.styleable.LabeledEditText_inputHintColor, DEFAULT_TEXT_COLOR))

                // Label
                setLabelText(getString(R.styleable.LabeledEditText_label) ?: DEFAULT_TITLE)
                setLabelSize(getDimension(R.styleable.LabeledEditText_labelSize, spToPx(DEFAULT_TEXT_SIZE)))
                setLabelColor(getColor(R.styleable.LabeledEditText_labelColor, DEFAULT_TEXT_COLOR))

                // Error
                setErrorText(getString(R.styleable.LabeledEditText_error) ?: DEFAULT_TITLE)
                setErrorSize(getDimension(R.styleable.LabeledEditText_errorSize, spToPx(DEFAULT_TEXT_SIZE)))
                setErrorColor(getColor(R.styleable.LabeledEditText_errorColor, DEFAULT_TEXT_COLOR))
            } finally {
                recycle()
            }
        }
    }


    /**
     * Sets a background color of the input container.
     *
     * @param color The color to set
     */
    fun setInputContainerColor(@ColorInt color: Int) {
        inputContainerRl.setBackgroundColor(color)
    }


    /**
     * Returns [RelativeLayout] of the input container.
     *
     * @return The relative layout of the input container
     */
    fun getInputContainer(): RelativeLayout {
        return inputContainerRl
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
     * Returns [TextView] of the title.
     *
     * @return The text view of the title
     */
    fun getTitleTv(): TextView {
        return titleTv
    }


    /**
     * Sets a text of the input.
     *
     * @param text The text to set
     */
    fun setInputText(text: String) {
        inputEt.setText(text)
    }


    /**
     * Sets a hint of the input.
     *
     * @param hint The hint to set
     */
    fun setInputHint(hint: String) {
        inputEt.hint = hint
    }


    /**
     * Sets a text size of the input.
     *
     * @param textSize The text size to set
     * @param unit The text size's unit. Default is SP.
     */
    fun setInputSize(textSize: Float, unit: Int = TypedValue.COMPLEX_UNIT_PX) {
        inputEt.setTextSize(unit, textSize)
    }


    /**
     * Sets a text color of the input.
     *
     * @param color The color to set
     */
    fun setInputTextColor(@ColorInt color: Int) {
        inputEt.setTextColor(color)
    }


    /**
     * Sets a color of the input hint.
     *
     * @param color The color to set
     */
    fun setInputHintColor(@ColorInt color: Int) {
        inputEt.setHintTextColor(color)
    }


    /**
     * Sets an type of the input.
     *
     * @param inputType The type to set
     */
    fun setInputType(inputType: Int) {
        inputEt.inputType = inputType
    }


    /**
     * Sets a cursor drawable of the input.
     *
     * @param drawable The drawable to set
     */
    fun setInputCursorDrawable(drawable: Drawable?) {
        inputEt.setCursorDrawable(drawable)
    }


    /**
     * Sets input filters of the input.
     *
     * @param filters The filters to set
     */
    fun setInputFilters(filters: Array<InputFilter>) {
        inputEt.filters = filters
    }


    /**
     * Sets a key listener of the input.
     *
     * @param listener The listener to set
     */
    fun setKeyListener(listener: KeyListener) {
        inputEt.keyListener = listener
    }


    /**
     * Adds a text watcher listener of the input.
     *
     * @param listener The listener to add
     */
    fun addTextChangedListener(listener: TextWatcher) {
        inputEt.addTextChangedListener(listener)
    }


    /**
     * Checks whether the input is empty.
     *
     * @return true if empty; false otherwise
     */
    fun isInputEmpty(): Boolean {
        return inputEt.isEmpty()
    }


    /**
     * Retrieves the text of the input.
     *
     * @return The input's text
     */
    fun getInputText(): String {
        return inputEt.getContent()
    }


    /**
     * Returns [EditText] of the input.
     *
     * @return The edit text of the input
     */
    fun getInputEt(): EditText {
        return inputEt
    }


    /**
     * Sets a text of the label.
     *
     * @param text The text to set
     */
    fun setLabelText(text: String) {
        labelTv.text = text
    }


    /**
     * Sets a text size of the label.
     *
     * @param textSize The text size to set
     * @param unit The text size's unit. Default is SP.
     */
    fun setLabelSize(textSize: Float, unit: Int = TypedValue.COMPLEX_UNIT_PX) {
        labelTv.setTextSize(unit, textSize)
    }


    /**
     * Sets a color of the label.
     *
     * @param color The color to set
     */
    fun setLabelColor(@ColorInt color: Int) {
        labelTv.setTextColor(color)
    }


    /**
     * Returns [TextView] of the label.
     *
     * @return The text view of the label
     */
    fun getLabelTv(): TextView {
        return labelTv
    }


    /**
     * Sets a text of the error.
     *
     * @param text The text to set
     */
    fun setErrorText(text: String) {
        errorTv.text = text
    }


    /**
     * Sets a text size of the error.
     *
     * @param textSize The text size to set
     * @param unit The text size's unit. Default is SP.
     */
    fun setErrorSize(textSize: Float, unit: Int = TypedValue.COMPLEX_UNIT_PX) {
        errorTv.setTextSize(unit, textSize)
    }


    /**
     * Sets a text color of the error.
     *
     * @param color The color to set
     */
    fun setErrorColor(@ColorInt color: Int) {
        errorTv.setTextColor(color)
    }


    /**
     * Returns [TextView] of the error.
     *
     * @return The text view of the error
     */
    fun getErrorTv(): TextView {
        return errorTv
    }


}
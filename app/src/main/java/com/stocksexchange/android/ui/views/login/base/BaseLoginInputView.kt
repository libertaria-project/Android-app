package com.stocksexchange.android.ui.views.login.base

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import com.stocksexchange.android.model.Attributes
import com.stocksexchange.android.ui.utils.extensions.getCursorDrawable
import com.stocksexchange.android.ui.utils.extensions.setCursorDrawable

/**
 * A base login view to use to create views with inputs.
 */
@Suppress("LeakingThis")
abstract class BaseLoginInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    companion object {

        internal const val ATTRIBUTE_KEY_INPUT_VIEW_TEXT_COLOR = "input_view_text_color"
        internal const val ATTRIBUTE_KEY_INPUT_VIEW_HINT_TEXT_COLOR = "input_view_hint_text_color"
        internal const val ATTRIBUTE_KEY_INPUT_VIEW_CURSOR_COLOR = "input_view_cursor_color"
        internal const val ATTRIBUTE_KEY_HINT_TEXT_COLOR = "hint_text_color"
        internal const val ATTRIBUTE_KEY_HELP_BUTTON_TEXT_COLOR = "help_button_text_color"

        internal const val DEFAULT_INPUT_VIEW_TEXT_COLOR = Color.WHITE
        internal const val DEFAULT_INPUT_VIEW_HINT_TEXT_COLOR = Color.GRAY
        internal const val DEFAULT_INPUT_VIEW_CURSOR_COLOR = Color.WHITE
        internal const val DEFAULT_HINT_TEXT_COLOR = Color.WHITE
        internal const val DEFAULT_HELP_BUTTON_TEXT_COLOR = Color.WHITE

    }


    protected var mAttributes: Attributes = Attributes()




    init {
        orientation = VERTICAL

        View.inflate(context, getLayoutResourceId(), this)
        saveAttributes(attrs, defStyleAttr)
    }


    /**
     * Should save custom view's attributes (if any).
     *
     * @param attrs The attribute set to fetch from
     * @param defStyleAttr The style of the attributes
     */
    protected open fun saveAttributes(attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        // Stub
    }


    override fun onFinishInflate() {
        super.onFinishInflate()

        init()
        applyAttributes()

        mAttributes.recycle()
    }


    /**
     * Initializes the views.
     */
    @CallSuper
    protected open fun init() {
        // Stub
    }


    /**
     * Should apply the previously fetched attributes (if any).
     */
    @CallSuper
    protected open fun applyAttributes() {
        with(mAttributes) {
            setInputViewTextColor(get(ATTRIBUTE_KEY_INPUT_VIEW_TEXT_COLOR, DEFAULT_INPUT_VIEW_TEXT_COLOR))
            setInputViewHintTextColor(get(ATTRIBUTE_KEY_INPUT_VIEW_HINT_TEXT_COLOR, DEFAULT_INPUT_VIEW_HINT_TEXT_COLOR))
            setInputViewCursorColor(get(ATTRIBUTE_KEY_INPUT_VIEW_CURSOR_COLOR, DEFAULT_INPUT_VIEW_CURSOR_COLOR))
            setHintTextColor(get(ATTRIBUTE_KEY_HINT_TEXT_COLOR, DEFAULT_HINT_TEXT_COLOR))
            setHelpButtonTextColor(get(ATTRIBUTE_KEY_HELP_BUTTON_TEXT_COLOR, DEFAULT_HELP_BUTTON_TEXT_COLOR))
        }
    }


    /**
     * Sets a text color of the input views.
     *
     * @param color The color to set
     */
    fun setInputViewTextColor(@ColorInt color: Int) {
        getInputViewsArray().forEach {
            it.setTextColor(color)
        }
    }


    /**
     * Sets a text color of the hint of the input views.
     *
     * @param color The color to set
     */
    fun setInputViewHintTextColor(@ColorInt color: Int) {
        getInputViewsArray().forEach {
            it.setHintTextColor(color)
        }
    }


    /**
     * Sets a cursor color of the input views.
     *
     * @param color The color to set
     */
    fun setInputViewCursorColor(@ColorInt color: Int) {
        getInputViewsArray().forEach {
            it.setCursorDrawable(context.getCursorDrawable(color))
        }
    }


    /**
     * Sets a text color of the hint.
     *
     * @param color The color to set
     */
    fun setHintTextColor(@ColorInt color: Int) {
        if(!hasHint()) {
            return
        }

        getHintTv()?.setTextColor(color)
    }


    /**
     * Sets a text color of the help buttons.
     *
     * @param color The color to set
     */
    fun setHelpButtonTextColor(@ColorInt color: Int) {
        if(!hasHelpButtons()) {
            return
        }

        getHelpButtonsArray().forEach {
            it.setTextColor(color)
        }
    }


    /**
     * Should return whether the view has a hint or not.
     */
    protected abstract fun hasHint(): Boolean


    /**
     * Should return whether the view has any help buttons or not.
     */
    protected abstract fun hasHelpButtons(): Boolean


    /**
     * Should return a layout resource ID of the custom view.
     *
     * @return The layout resource ID
     */
    protected abstract fun getLayoutResourceId(): Int


    /**
     * Should return a reference to the hint if the view has one.
     */
    protected open fun getHintTv(): TextView? {
        return null
    }


    /**
     * Should return an array of help buttons if the view
     * has any.
     */
    protected open fun getHelpButtonsArray(): Array<TextView> {
        return arrayOf()
    }


    /**
     * Should return an array of input views the view contains.
     */
    protected abstract fun getInputViewsArray(): Array<EditText>


}
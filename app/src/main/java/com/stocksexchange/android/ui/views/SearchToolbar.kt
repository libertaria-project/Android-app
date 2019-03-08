package com.stocksexchange.android.ui.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.annotation.ColorInt
import com.stocksexchange.android.R
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.utils.interfaces.Recyclable
import com.stocksexchange.android.utils.managers.KeyboardManager
import kotlinx.android.synthetic.main.search_toolbar_layout.view.*

/**
 * A custom search toolbar.
 */
class SearchToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), Recyclable {


    companion object {

        private const val DEFAULT_HINT_TEXT = ""
        private const val DEFAULT_HINT_COLOR = Color.WHITE

        private const val DEFAULT_TEXT_COLOR = Color.WHITE

        private const val DEFAULT_BUTTON_DRAWABLE_COLOR = Color.WHITE
        private const val DEFAULT_PROGRESS_BAR_COLOR = Color.WHITE

        private const val KEYBOARD_SHOWING_DELAY = 150L

        private const val CLEAR_INPUT_BUTTON_ANIMATION_DURATION = 100L

    }


    private val mKeyboardManager: KeyboardManager = KeyboardManager.newInstance(context)

    private var mValueAnimator: ValueAnimator? = null




    init {
        View.inflate(context, R.layout.search_toolbar_layout, this)

        context.theme.obtainStyledAttributes(attrs, R.styleable.SearchToolbar, defStyleAttr, 0).run {
            try {
                // Hint
                setHintText(getString(R.styleable.SearchToolbar_hintText) ?: DEFAULT_HINT_TEXT)
                setHintColor(getColor(R.styleable.SearchToolbar_hintColor, DEFAULT_HINT_COLOR))

                // Text
                setTextColor(getColor(R.styleable.SearchToolbar_textColor, DEFAULT_TEXT_COLOR))

                // Drawables
                setLeftButtonDrawable(getDrawable(R.styleable.SearchToolbar_leftButtonDrawable))
                setClearInputButtonDrawable(getDrawable(R.styleable.SearchToolbar_clearInputButtonDrawable))
                setCursorDrawable(getDrawable(R.styleable.SearchToolbar_cursorDrawable))

                // Colors
                setButtonDrawableColor(getColor(R.styleable.SearchToolbar_buttonDrawableColor, DEFAULT_BUTTON_DRAWABLE_COLOR))
                setProgressBarColor(getColor(R.styleable.SearchToolbar_progressBarColor, DEFAULT_PROGRESS_BAR_COLOR))
            } finally {
                recycle()
            }
        }
    }


    /**
     * Shows the keyboard.
     *
     * @param shouldDelay Whether to delay the showing
     */
    fun showKeyboard(shouldDelay: Boolean) {
        queryInputEt.requestFocus()
        queryInputEt.postDelayed(
            { mKeyboardManager.showKeyboard(queryInputEt) },
            (if(shouldDelay) KEYBOARD_SHOWING_DELAY else 0L)
        )
    }


    /**
     * Hides the keyboard.
     */
    fun hideKeyboard() {
        queryInputEt.clearFocus()
        mKeyboardManager.hideKeyboard(queryInputEt)
    }


    /**
     * Makes the left button visible.
     */
    fun showLeftButtonIv() {
        if(leftBtnIv.isVisible()) {
            return
        }

        leftBtnIv.makeVisible()
    }


    /**
     * Makes the left button invisible.
     */
    fun hideLeftButtonIv() {
        if(leftBtnIv.isGone()) {
            return
        }

        leftBtnIv.makeGone()
    }


    /**
     * Makes the progress bar is visible.
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
     * Makes the clear input button visible.
     *
     * @param animate Whether to animate the process
     */
    fun showClearInputButton(animate: Boolean) {
        if(animate) {
            mValueAnimator?.cancel()

            clearInputBtnIv.setScale(0f)
            clearInputBtnIv.makeVisible()

            runClearInputButtonAnimation(ValueAnimator.ofFloat(0f, 1f))
        } else {
            clearInputBtnIv.makeVisible()
        }
    }


    /**
     * Makes the clear input button invisible.
     *
     * @param animate Whether to animate the process
     */
    fun hideClearInputButton(animate: Boolean) {
        if(animate) {
            mValueAnimator?.cancel()

            clearInputBtnIv.setScale(1f)

            val valueAnimator = ValueAnimator.ofFloat(1f, 0f)
            valueAnimator.addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator?) {
                    clearInputBtnIv.makeGone()
                }

            })

            runClearInputButtonAnimation(valueAnimator)
        } else {
            clearInputBtnIv.makeGone()
        }
    }


    private fun runClearInputButtonAnimation(valueAnimator: ValueAnimator) {
        with(valueAnimator) {
            mValueAnimator = this

            addUpdateListener {
                clearInputBtnIv.setScale(it.animatedValue as Float)
            }
            interpolator = LinearInterpolator()
            duration = CLEAR_INPUT_BUTTON_ANIMATION_DURATION
            start()
        }
    }


    /**
     * Adds a text watcher for the input.
     *
     * @param textWatcher The text watcher to add
     */
    fun addTextWatcher(textWatcher: TextWatcher) {
        queryInputEt.addTextChangedListener(textWatcher)
    }


    /**
     * Sets an editor action listener of the input.
     *
     * @param listener The listener to set
     */
    fun setOnEditorActionListener(listener: TextView.OnEditorActionListener) {
        queryInputEt.setOnEditorActionListener(listener)
    }


    /**
     * Sets a text of the hint.
     *
     * @param text The text to set
     */
    fun setHintText(text: String) {
        queryInputEt.hint = text
    }


    /**
     * Sets a color of the hint.
     *
     * @param color The color to set
     */
    fun setHintColor(@ColorInt color: Int) {
        queryInputEt.setHintTextColor(color)
    }


    /**
     * Sets a text of the input.
     *
     * @param text The text to set
     */
    fun setText(text: String) {
        queryInputEt.setText(text)
        queryInputEt.setSelection(text.length)
    }


    /**
     * Sets an input type of the input.
     *
     * @param inputType The input type to set
     */
    fun setInputType(inputType: Int) {
        queryInputEt.inputType = inputType
    }


    /**
     * Sets a text color of the input.
     *
     * @param color The color to set
     */
    fun setTextColor(@ColorInt color: Int) {
        queryInputEt.setTextColor(color)
    }


    /**
     * Sets a cursor drawable of the input.
     *
     * @param drawable The drawable to set
     */
    fun setCursorDrawable(drawable: Drawable?) {
        queryInputEt.setCursorDrawable(drawable)
    }


    /**
     * Returns [EditText] of the input.
     *
     * @return The edit text of the input
     */
    fun getInputEt(): EditText {
        return queryInputEt
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
            showLeftButtonIv()
        }
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
     * Returns [ImageView] of the left button.
     *
     * @return The image view of the left button
     */
    fun getLeftButtonIv(): ImageView {
        return leftBtnIv
    }


    /**
     * Sets a drawable of the clear input button.
     *
     * @param drawable The drawable to set
     * @param showIfHidden Whether to show the button if it is hidden.
     * Default is true.
     */
    fun setClearInputButtonDrawable(drawable: Drawable?, showIfHidden: Boolean = true) {
        clearInputBtnIv.setImageDrawable(drawable)

        if(showIfHidden && !queryInputEt.isEmpty()) {
            showClearInputButton(false)
        }
    }


    /**
     * Sets a color of the clear input button's drawable.
     *
     * @param color The color to set
     */
    fun setClearInputButtonDrawableColor(@ColorInt color: Int) {
        clearInputBtnIv.setColor(color)
    }


    /**
     * Returns [ImageView] of the clear input button.
     *
     * @return The image view of the clear input button
     */
    fun getClearInputButtonIv(): ImageView {
        return clearInputBtnIv
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
     * Sets a click listener of the clear input button.
     *
     * @param listener The listener to set
     */
    fun setOnClearInputButtonClickListener(listener: (View) -> Unit) {
        clearInputBtnIv.setOnClickListener(listener)
    }


    /**
     * Sets a color of the button drawables.
     *
     * @param color The color to set
     */
    fun setButtonDrawableColor(@ColorInt color: Int) {
        setLeftButtonDrawableColor(color)
        setClearInputButtonDrawableColor(color)
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
     * Returns progress bar.
     *
     * @return The progress bar
     */
    fun getProgressBar(): ProgressBar {
        return progressBar
    }


    override fun recycle() {
        mKeyboardManager.recycle()
        mValueAnimator?.end()
    }


}
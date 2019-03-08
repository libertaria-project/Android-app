package com.stocksexchange.android.ui.views

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.stocksexchange.android.R
import com.stocksexchange.android.ui.utils.extensions.*
import kotlinx.android.synthetic.main.pin_entry_keypad_layout.view.*

/**
 * A keypad used for entering pin code.
 */
class PinEntryKeypad @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), View.OnClickListener {


    companion object {

        private const val DEFAULT_DIGIT_BUTTON_TEXT_COLOR = Color.WHITE

        private val CLASS_LOADER = PinEntryKeypad::class.java.classLoader

    }


    private var mDigitButtonTextColor: Int = 0

    private var mEnabledDigitButtonBackgroundColor: Int = 0
    private var mDisabledDigitButtonBackgroundColor: Int = 0

    private var mEnabledFingerprintButtonBackgroundColor: Int = 0
    private var mDisabledFingerprintButtonBackgroundColor: Int = 0
    private var mEnabledFingerprintButtonForegroundColor: Int = 0
    private var mDisabledFingerprintButtonForegroundColor: Int = 0

    private var mEnabledDeleteButtonBackgroundColor: Int = 0
    private var mDisabledDeleteButtonBackgroundColor: Int = 0
    private var mEnabledDeleteButtonForegroundColor: Int = 0
    private var mDisabledDeleteButtonForegroundColor: Int = 0

    private lateinit var mDigitBtnTvs: Array<TextView>

    /**
     * A listener to invoke whenever a button is clicked.
     */
    var onButtonClickListener: OnButtonClickListener? = null




    init {
        View.inflate(context, R.layout.pin_entry_keypad_layout, this)

        initViews()
        fetchAttributes(attrs, defStyleAttr)
    }


    private fun initViews() {
        mDigitBtnTvs = arrayOf(
            oneDigitBtnTv,
            twoDigitBtnTv,
            threeDigitBtnTv,
            fourDigitBtnTv,
            fiveDigitBtnTv,
            sixDigitBtnTv,
            sevenDigitBtnTv,
            eightDigitBtnTv,
            nineDigitBtnTv,
            zeroDigitBtnTv
        )
        mDigitBtnTvs.forEach {
            it.setOnClickListener(this)
        }

        fingerprintActionBtnIv.setOnClickListener(this)
        fingerprintOverlayView.setOnClickListener(this)

        deleteActionBtnIv.setOnClickListener(this)
    }


    private fun fetchAttributes(attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.PinEntryKeypad, defStyleAttr, 0).run {
            try {
                setDigitButtonTextColor(getColor(R.styleable.PinEntryKeypad_digitButtonTextColor, DEFAULT_DIGIT_BUTTON_TEXT_COLOR))
            } finally {
                recycle()
            }
        }
    }


    private fun updateDigitButtonBackground(isEnabled: Boolean) {
        mDigitBtnTvs.forEach {
            it.background = context.getPinEntryDigitButtonDrawable(if(isEnabled) {
                mEnabledDigitButtonBackgroundColor
            } else {
                mDisabledDigitButtonBackgroundColor
            })
        }
    }


    private fun updateFingerprintButtonBackground() {
        if(fingerprintActionBtnIv.isEnabled) {
            fingerprintActionBtnIv.background = context.getPinEntryFingerprintButtonDrawable(
                backgroundColor = mEnabledFingerprintButtonBackgroundColor,
                foregroundColor = mEnabledFingerprintButtonForegroundColor
            )
        } else {
            fingerprintActionBtnIv.background = context.getPinEntryFingerprintButtonDrawable(
                backgroundColor = mDisabledFingerprintButtonBackgroundColor,
                foregroundColor = mDisabledFingerprintButtonForegroundColor
            )
        }
    }


    private fun updateDeleteButtonBackground() {
        if(deleteActionBtnIv.isEnabled) {
            deleteActionBtnIv.background = context.getPinEntryDeleteButtonDrawable(
                backgroundColor = mEnabledDeleteButtonBackgroundColor,
                foregroundColor = mEnabledDeleteButtonForegroundColor
            )
        } else {
            deleteActionBtnIv.background = context.getPinEntryDeleteButtonDrawable(
                backgroundColor = mDisabledDeleteButtonBackgroundColor,
                foregroundColor = mDisabledDeleteButtonForegroundColor
            )
        }
    }


    /**
     * Shows the fingerprint button.
     */
    fun showFingerprintButton() {
        if(!fingerprintButtonContainerFl.isVisible()) {
            fingerprintButtonContainerFl.makeVisible()
        }
    }


    /**
     * Hides the fingerprint button.
     */
    fun hideFingerprintButton() {
        if(!fingerprintButtonContainerFl.isGone()) {
            fingerprintButtonContainerFl.makeGone()
        }
    }


    /**
     * Shows the fingerprint overlay view.
     */
    fun showFingerprintOverlayView() {
        if(!fingerprintOverlayView.isVisible()) {
            fingerprintOverlayView.makeVisible()
        }
    }


    /**
     * Hides the fingerprint overlay view.
     */
    fun hideFingerprintOverlayView() {
        if(!fingerprintOverlayView.isGone()) {
            fingerprintOverlayView.makeGone()
        }
    }


    /**
     * Enables the fingerprint button.
     */
    fun enableFingerprintButton() {
        if(!fingerprintActionBtnIv.isEnabled) {
            fingerprintActionBtnIv.enable()

            updateFingerprintButtonBackground()
        }
    }


    /**
     * Disables the fingerprint button.
     */
    fun disableFingerprintButton() {
        if(fingerprintActionBtnIv.isEnabled) {
            fingerprintActionBtnIv.disable()

            updateFingerprintButtonBackground()
        }
    }


    /**
     * Enables the delete button.
     */
    fun enableDeleteButton() {
        if(!deleteActionBtnIv.isEnabled) {
            deleteActionBtnIv.enable()

            updateDeleteButtonBackground()
        }
    }


    /**
     * Disables the delete button.
     */
    fun disableDeleteButton() {
        if(deleteActionBtnIv.isEnabled) {
            deleteActionBtnIv.disable()

            updateDeleteButtonBackground()
        }
    }


    /**
     * Enables digit buttons.
     */
    fun enableDigitButtons() {
        mDigitBtnTvs.forEach {
            it.enable()
            updateDigitButtonBackground(true)
        }
    }


    /**
     * Disables digit buttons.
     */
    fun disableDigitButtons() {
        mDigitBtnTvs.forEach {
            it.disable()
            updateDigitButtonBackground(false)
        }
    }


    /**
     * Sets a text color of the digit buttons.
     *
     * @param color The color to set
     */
    fun setDigitButtonTextColor(@ColorInt color: Int) {
        mDigitButtonTextColor = color

        mDigitBtnTvs.forEach {
            it.setTextColor(color)
        }
    }


    /**
     * Sets colors of the digit buttons.
     *
     * @param enabledButtonBackgroundColor The background color to use when
     * the digit buttons are enabled
     * @param disabledButtonBackgroundColor The background color to use when
     * the digit buttons are disabled
     */
    fun setDigitButtonColors(@ColorInt enabledButtonBackgroundColor: Int,
                             @ColorInt disabledButtonBackgroundColor: Int) {
        mEnabledDigitButtonBackgroundColor = enabledButtonBackgroundColor
        mDisabledDigitButtonBackgroundColor = disabledButtonBackgroundColor

        updateDigitButtonBackground(mDigitBtnTvs.first().isEnabled)
    }


    /**
     * Sets colors of the fingerprint button.
     *
     * @param enabledButtonBackgroundColor The background color to use when
     * the fingerprint button is enabled
     * @param disabledButtonBackgroundColor The background color to use when
     * the fingerprint button is disabled
     * @param enabledButtonForegroundColor The foreground color to use when
     * the fingerprint button is enabled
     * @param disabledButtonForegroundColor The foreground color to use when
     * the fingerprint button is disabled
     */
    fun setFingerprintButtonColors(@ColorInt enabledButtonBackgroundColor: Int,
                                   @ColorInt disabledButtonBackgroundColor: Int,
                                   @ColorInt enabledButtonForegroundColor: Int,
                                   @ColorInt disabledButtonForegroundColor: Int) {
        mEnabledFingerprintButtonBackgroundColor = enabledButtonBackgroundColor
        mDisabledFingerprintButtonBackgroundColor = disabledButtonBackgroundColor
        mEnabledFingerprintButtonForegroundColor = enabledButtonForegroundColor
        mDisabledFingerprintButtonForegroundColor = disabledButtonForegroundColor

        updateFingerprintButtonBackground()
    }


    /**
     * Sets colors of the delete button.
     *
     * @param enabledButtonBackgroundColor The background color to use when
     * the delete button is enabled
     * @param disabledButtonBackgroundColor The background color to use when
     * the delete button is disabled
     * @param enabledButtonForegroundColor The foreground color to use when
     * the delete button is enabled
     * @param disabledButtonForegroundColor The foreground color to use when
     * the delete button is disabled
     */
    fun setDeleteButtonColors(@ColorInt enabledButtonBackgroundColor: Int,
                              @ColorInt disabledButtonBackgroundColor: Int,
                              @ColorInt enabledButtonForegroundColor: Int,
                              @ColorInt disabledButtonForegroundColor: Int) {
        mEnabledDeleteButtonBackgroundColor = enabledButtonBackgroundColor
        mDisabledDeleteButtonBackgroundColor = disabledButtonBackgroundColor
        mEnabledDeleteButtonForegroundColor = enabledButtonForegroundColor
        mDisabledDeleteButtonForegroundColor = disabledButtonForegroundColor

        updateDeleteButtonBackground()
    }


    override fun onClick(view: View) {
        when(view.id) {

            R.id.oneDigitBtnTv,
            R.id.twoDigitBtnTv,
            R.id.threeDigitBtnTv,
            R.id.fourDigitBtnTv,
            R.id.fiveDigitBtnTv,
            R.id.sixDigitBtnTv,
            R.id.sevenDigitBtnTv,
            R.id.eightDigitBtnTv,
            R.id.nineDigitBtnTv,
            R.id.zeroDigitBtnTv -> {
                onButtonClickListener?.onDigitButtonClicked(view.tag as String)
            }

            R.id.fingerprintActionBtnIv -> {
                onButtonClickListener?.onFingerprintButtonClicked()
            }

            R.id.fingerprintOverlayView -> {
                onButtonClickListener?.onFingerprintOverlayClicked()
            }

            R.id.deleteActionBtnIv -> {
                onButtonClickListener?.onDeleteButtonClicked()
            }

        }
    }


    interface OnButtonClickListener {

        fun onDigitButtonClicked(digit: String)

        fun onFingerprintButtonClicked()

        fun onFingerprintOverlayClicked()

        fun onDeleteButtonClicked()

    }


    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state.fetchProperState())

        if(state is SavedState) {
            setDigitButtonTextColor(state.digitButtonTextColor)
            setDigitButtonColors(
                state.enabledDigitButtonBackgroundColor,
                state.disabledDigitButtonBackgroundColor
            )
            setFingerprintButtonColors(
                state.enabledFingerprintButtonBackgroundColor,
                state.disabledFingerprintButtonBackgroundColor,
                state.enabledFingerprintButtonForegroundColor,
                state.disabledFingerprintButtonForegroundColor
            )
            setDeleteButtonColors(
                state.enabledDeleteButtonBackgroundColor,
                state.disabledDeleteButtonBackgroundColor,
                state.enabledDeleteButtonForegroundColor,
                state.disabledDeleteButtonForegroundColor
            )
        }
    }


    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState()).apply {
            digitButtonTextColor = mDigitButtonTextColor
            enabledDigitButtonBackgroundColor = mEnabledDigitButtonBackgroundColor
            disabledDigitButtonBackgroundColor = mDisabledDigitButtonBackgroundColor
            enabledFingerprintButtonBackgroundColor = mEnabledFingerprintButtonBackgroundColor
            disabledFingerprintButtonBackgroundColor = mDisabledFingerprintButtonBackgroundColor
            enabledFingerprintButtonForegroundColor = mEnabledFingerprintButtonForegroundColor
            disabledFingerprintButtonForegroundColor = mDisabledFingerprintButtonForegroundColor
            enabledDeleteButtonBackgroundColor = mEnabledDeleteButtonBackgroundColor
            disabledDeleteButtonBackgroundColor = mDisabledDeleteButtonBackgroundColor
            enabledDeleteButtonForegroundColor = mEnabledDeleteButtonForegroundColor
            disabledDeleteButtonForegroundColor = mDisabledDeleteButtonForegroundColor
        }
    }


    private class SavedState : BaseSavedState {

        companion object {

            private const val KEY_DIGIT_BUTTON_TEXT_COLOR = "digit_button_text_color"
            private const val KEY_ENABLED_DIGIT_BUTTON_BACKGROUND_COLOR = "enabled_digit_button_background_color"
            private const val KEY_DISABLED_DIGIT_BUTTON_BACKGROUND_COLOR = "disabled_digit_button_background_color"
            private const val KEY_ENABLED_FINGERPRINT_BUTTON_BACKGROUND_COLOR = "enabled_fingerprint_button_background_color"
            private const val KEY_DISABLED_FINGERPRINT_BUTTON_BACKGROUND_COLOR = "disabled_fingerprint_button_background_color"
            private const val KEY_ENABLED_FINGERPRINT_BUTTON_FOREGROUND_COLOR = "enabled_fingerprint_button_foreground_color"
            private const val KEY_DISABLED_FINGERPRINT_BUTTON_FOREGROUND_COLOR = "disabled_fingerprint_button_foreground_color"
            private const val KEY_ENABLED_DELETE_BUTTON_BACKGROUND_COLOR = "enabled_delete_button_background_color"
            private const val KEY_DISABLED_DELETE_BUTTON_BACKGROUND_COLOR = "disabled_delete_button_background_color"
            private const val KEY_ENABLED_DELETE_BUTTON_FOREGROUND_COLOR = "enabled_delete_button_foreground_color"
            private const val KEY_DISABLED_DELETE_BUTTON_FOREGROUND_COLOR = "disabled_delete_button_foreground_color"


            @JvmField
            var CREATOR : Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {

                override fun createFromParcel(parcel: Parcel): SavedState {
                    return SavedState(parcel)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }

            }

        }


        internal var digitButtonTextColor: Int = 0

        internal var enabledDigitButtonBackgroundColor: Int = 0
        internal var disabledDigitButtonBackgroundColor: Int = 0

        internal var enabledFingerprintButtonBackgroundColor: Int = 0
        internal var disabledFingerprintButtonBackgroundColor: Int = 0
        internal var enabledFingerprintButtonForegroundColor: Int = 0
        internal var disabledFingerprintButtonForegroundColor: Int = 0

        internal var enabledDeleteButtonBackgroundColor: Int = 0
        internal var disabledDeleteButtonBackgroundColor: Int = 0
        internal var enabledDeleteButtonForegroundColor: Int = 0
        internal var disabledDeleteButtonForegroundColor: Int = 0


        constructor(parcel: Parcel): super(parcel) {
            parcel.readBundle(CLASS_LOADER)?.run {
                digitButtonTextColor = getInt(KEY_DIGIT_BUTTON_TEXT_COLOR, DEFAULT_DIGIT_BUTTON_TEXT_COLOR)
                enabledDigitButtonBackgroundColor = getInt(KEY_ENABLED_DIGIT_BUTTON_BACKGROUND_COLOR, 0)
                disabledDigitButtonBackgroundColor = getInt(KEY_DISABLED_DIGIT_BUTTON_BACKGROUND_COLOR, 0)
                enabledFingerprintButtonBackgroundColor = getInt(KEY_ENABLED_FINGERPRINT_BUTTON_BACKGROUND_COLOR, 0)
                disabledFingerprintButtonBackgroundColor = getInt(KEY_DISABLED_FINGERPRINT_BUTTON_BACKGROUND_COLOR, 0)
                enabledFingerprintButtonForegroundColor = getInt(KEY_ENABLED_FINGERPRINT_BUTTON_FOREGROUND_COLOR, 0)
                disabledFingerprintButtonForegroundColor = getInt(KEY_DISABLED_FINGERPRINT_BUTTON_FOREGROUND_COLOR, 0)
                enabledDeleteButtonBackgroundColor = getInt(KEY_ENABLED_DELETE_BUTTON_BACKGROUND_COLOR, 0)
                disabledDeleteButtonBackgroundColor = getInt(KEY_DISABLED_DELETE_BUTTON_BACKGROUND_COLOR, 0)
                enabledDeleteButtonForegroundColor = getInt(KEY_ENABLED_DELETE_BUTTON_FOREGROUND_COLOR, 0)
                disabledDeleteButtonForegroundColor = getInt(KEY_DISABLED_DELETE_BUTTON_FOREGROUND_COLOR, 0)
            }
        }


        constructor(superState: Parcelable?): super(superState)


        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)

            val bundle = Bundle(CLASS_LOADER).apply {
                putInt(KEY_DIGIT_BUTTON_TEXT_COLOR, digitButtonTextColor)
                putInt(KEY_ENABLED_DIGIT_BUTTON_BACKGROUND_COLOR, enabledDigitButtonBackgroundColor)
                putInt(KEY_DISABLED_DIGIT_BUTTON_BACKGROUND_COLOR, disabledDigitButtonBackgroundColor)
                putInt(KEY_ENABLED_FINGERPRINT_BUTTON_BACKGROUND_COLOR, enabledFingerprintButtonBackgroundColor)
                putInt(KEY_DISABLED_FINGERPRINT_BUTTON_BACKGROUND_COLOR, disabledFingerprintButtonBackgroundColor)
                putInt(KEY_ENABLED_FINGERPRINT_BUTTON_FOREGROUND_COLOR, enabledFingerprintButtonForegroundColor)
                putInt(KEY_DISABLED_FINGERPRINT_BUTTON_FOREGROUND_COLOR, disabledFingerprintButtonForegroundColor)
                putInt(KEY_ENABLED_DELETE_BUTTON_BACKGROUND_COLOR, enabledDeleteButtonBackgroundColor)
                putInt(KEY_DISABLED_DELETE_BUTTON_BACKGROUND_COLOR, disabledDeleteButtonBackgroundColor)
                putInt(KEY_ENABLED_DELETE_BUTTON_FOREGROUND_COLOR, enabledDeleteButtonForegroundColor)
                putInt(KEY_DISABLED_DELETE_BUTTON_FOREGROUND_COLOR, disabledDeleteButtonForegroundColor)
            }

            parcel.writeBundle(bundle)
        }

    }


}
package com.stocksexchange.android.ui.views.dialogs

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputFilter
import android.view.WindowManager
import androidx.annotation.ColorInt
import com.stocksexchange.android.R
import com.stocksexchange.android.ui.utils.extensions.*
import kotlinx.android.synthetic.main.credentials_input_dialog_layout.*

/**
 * A dialog responsible for retrieving credentials from the user.
 */
class CredentialsInputDialog constructor(
    context: Context,
    listener: Listener,
    styleResourceId: Int = R.style.DefaultDialogTheme
) : Dialog(context, styleResourceId) {


    companion object {

        private const val PUBLIC_KEY_LENGTH = 40
        private const val SECRET_KEY_LENGTH = 64

    }


    private var mIsInitialized: Boolean = false

    private var mBackgroundColor: Int = 0
    private var mTextColor: Int = 0
    private var mEditTextHintColor: Int  = 0
    private var mEditTextBackgroundColor: Int = 0
    private var mButtonTextColor: Int = 0
    private var mErrorColor: Int = 0

    private var mPublicKeyEtCursorDrawable: Drawable? = null
    private var mSecretKeyEtCursorDrawable: Drawable? = null

    private var mListener: Listener = listener




    init {
        prefetchResources()
    }


    private fun prefetchResources() {
        mErrorColor = context.getCompatColor(R.color.colorFingerprintError)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.credentials_input_dialog_layout)

        preInit()
        init()

        mIsInitialized = true
    }


    private fun preInit() {
        window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }


    private fun init() {
        initPublicEt()
        initSecretEt()
        initCancelButton()
        initOkButton()

        setBackgroundColor(mBackgroundColor)
        setTextColor(mTextColor)
        setEditTextBackgroundColor(mEditTextBackgroundColor)
        setEditTextHintColor(mEditTextHintColor)
        setPublicKeyEtCursorDrawable(mPublicKeyEtCursorDrawable)
        setSecretKeyEtCursorDrawable(mSecretKeyEtCursorDrawable)
        setButtonTextColor(mButtonTextColor)
    }


    private fun initPublicEt() {
        publicKeyEt.filters = arrayOf(InputFilter.LengthFilter(PUBLIC_KEY_LENGTH))
    }


    private fun initSecretEt() {
        secretKeyEt.filters = arrayOf(InputFilter.LengthFilter(SECRET_KEY_LENGTH))
    }


    private fun initCancelButton() {
        cancelBtnTv.setOnClickListener {
            dismiss()
        }
    }


    private fun initOkButton() {
        okBtnTv.setOnClickListener {
            if(validate()) {
                mListener.onCredentialsEntered(publicKeyEt.getContent(), secretKeyEt.getContent())

                dismiss()
            }
        }
    }


    private fun validate(): Boolean {
        if(publicKeyEt.getContent().isEmpty()) {
            setError(context.getString(R.string.error_empty_public_key))
            return false
        }

        if(publicKeyEt.getContent().length != PUBLIC_KEY_LENGTH) {
            setError(context.getString(
                R.string.error_public_key_length,
                PUBLIC_KEY_LENGTH
            ))
            return false
        }

        if(secretKeyEt.getContent().isEmpty()) {
            setError(context.getString(R.string.error_empty_secret_key))
            return false
        }

        if(secretKeyEt.getContent().length != SECRET_KEY_LENGTH) {
            setError(context.getString(
                R.string.error_secret_key_length,
                SECRET_KEY_LENGTH
            ))
            return false
        }

        return true
    }


    private fun setError(error: String) {
        subtitleTv.setTextColor(mErrorColor)
        subtitleTv.text = error
    }


    /**
     * Sets a color of the background.
     *
     * @param color The color to set
     */
    fun setBackgroundColor(@ColorInt color: Int) {
        mBackgroundColor = color

        if(cardView != null) {
            cardView.setCardBackgroundColor(color)
        }
    }


    /**
     * Returns a color of the background.
     *
     * @return The background's color
     */
    fun getBackgroundColor(): Int {
        return mBackgroundColor
    }


    /**
     * Sets a color of the text.
     *
     * @param color The color to set
     */
    fun setTextColor(@ColorInt color: Int) {
        mTextColor = color

        if(titleTv != null) {
            titleTv.setTextColor(color)
        }

        if(subtitleTv != null) {
            subtitleTv.setTextColor(color)
        }

        if(publicKeyEt != null) {
            publicKeyEt.setTextColor(color)
        }

        if(secretKeyEt != null) {
            secretKeyEt.setTextColor(color)
        }
    }


    /**
     * Returns a color of the text.
     *
     * @return The text's color
     */
    fun getTextColor(): Int {
        return mTextColor
    }


    /**
     * Sets a background color of the EditText widgets.
     *
     * @param color The color to set
     */
    fun setEditTextBackgroundColor(@ColorInt color: Int) {
        mEditTextBackgroundColor = color

        if(publicKeyEt != null) {
            publicKeyEt.backgroundTintList = ColorStateList.valueOf(color)
        }

        if(secretKeyEt != null) {
            secretKeyEt.backgroundTintList = ColorStateList.valueOf(color)
        }
    }


    /**
     * Returns a background color of the EditText widgets.
     *
     * @return The background color
     */
    fun getEditTextsBackgroundTintColor(): Int {
        return mEditTextBackgroundColor
    }


    /**
     * Sets a hint color of the EditText widgets.
     *
     * @param color The color to set
     */
    fun setEditTextHintColor(@ColorInt color: Int) {
        mEditTextHintColor = color

        if(publicKeyEt != null) {
            publicKeyEt.setHintTextColor(color)
        }

        if(secretKeyEt != null) {
            secretKeyEt.setHintTextColor(color)
        }
    }


    /**
     * Returns a hint color of the EditText widgets.
     *
     * @return The hint color
     */
    fun getEditTextHintColor(): Int {
        return mEditTextHintColor
    }


    /**
     * Sets a cursor drawable of the public key.
     *
     * @param cursorDrawable The drawable to set
     */
    fun setPublicKeyEtCursorDrawable(cursorDrawable: Drawable?) {
        mPublicKeyEtCursorDrawable = cursorDrawable

        if(publicKeyEt != null) {
            publicKeyEt.setCursorDrawable(cursorDrawable)
        }
    }


    /**
     * Returns a cursor drawable of the public key.
     *
     * @return The cursor drawable
     */
    fun getPublicKeyEtCursorDrawable(): Drawable? {
        return mPublicKeyEtCursorDrawable
    }


    /**
     * Sets a cursor drawable of the secret key
     *
     * @param cursorDrawable The drawable to set
     */
    fun setSecretKeyEtCursorDrawable(cursorDrawable: Drawable?) {
        mSecretKeyEtCursorDrawable = cursorDrawable

        if(secretKeyEt != null) {
            secretKeyEt.setCursorDrawable(cursorDrawable)
        }
    }


    /**
     * Returns a cursor drawable of the secret key.
     *
     * @return The cursor drawable
     */
    fun getSecretKeyEtCursorDrawable(): Drawable? {
        return mSecretKeyEtCursorDrawable
    }


    /**
     * Sets a text color of the buttons.
     *
     * @param color The color to set
     */
    fun setButtonTextColor(@ColorInt color: Int) {
        mButtonTextColor = color

        if(cancelBtnTv != null) {
            cancelBtnTv.setTextColor(color)
        }

        if(okBtnTv != null) {
            okBtnTv.setTextColor(color)
        }
    }


    /**
     * Returns a text color of the buttons.
     *
     * @return The text color
     */
    fun getButtonTextColor(): Int {
        return mButtonTextColor
    }


    interface Listener {

        fun onCredentialsEntered(publicKey: String, secretKey: String)

    }


}
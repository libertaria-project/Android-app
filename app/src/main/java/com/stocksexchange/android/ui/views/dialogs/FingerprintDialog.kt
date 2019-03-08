package com.stocksexchange.android.ui.views.dialogs

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.core.os.CancellationSignal
import com.stocksexchange.android.R
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.ui.utils.extensions.*
import kotlinx.android.synthetic.main.fingerprint_dialog_layout.*
import java.security.KeyStore
import java.util.UUID
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

/**
 * A dialog responsible for authenticating user with a fingerprint.
 */
class FingerprintDialog constructor(
    context: Context,
    mode: Modes,
    theme: Theme,
    listener: Listener,
    styleResourceId: Int = R.style.DefaultDialogTheme
) : Dialog(context, styleResourceId), DialogInterface.OnDismissListener {


    companion object {

        private val KEY_NAME = UUID.randomUUID().toString()

        private const val SUCCESS_DELAY_IN_MILLIS = 1500L

        private const val ERROR_WITH_DETAILS_DELAY_IN_MILLIS = 3500L
        private const val ERROR_WITHOUT_DETAILS_DELAY_IN_MILLIS = 2500L

        private const val RESET_ERROR_DELAY_IN_MILLIS = 1600L

        private const val TOO_MANY_ATTEMPTS_ERROR_CODE = 7

    }


    private var mIsInitialized: Boolean = false
    private var mIsScanning: Boolean = false
    private var mIsSuccessPending: Boolean = false
    private var mIsErrorPending: Boolean = false

    private var mBackgroundColor: Int = 0
    private var mTextColor: Int = 0
    private var mStatusTextColor: Int = 0
    private var mButtonTextColor: Int = 0
    private var mSuccessColor: Int = 0
    private var mErrorColor: Int = 0

    private var mSubtitleText: String = ""
    private var mButtonText: String = ""

    private var mSuccessDrawable: Drawable? = null
    private var mErrorDrawable: Drawable? = null

    private var mLastFatalError: Errors? = null

    private val mMode: Modes = mode

    private val mTheme: Theme = theme

    private var mKeyStore: KeyStore? = null

    private var mCipher: Cipher? = null

    private var mCancellationSignal: CancellationSignal? = null

    private val mDelayedTasks: MutableList<Runnable> = mutableListOf()

    private var mListener: Listener = listener
    private var mButtonListener: ((View) -> Unit)? = null




    init {
        prefetchResources()
    }


    private fun prefetchResources() {
        mSuccessColor = context.getCompatColor(R.color.colorFingerprintSuccess)
        mErrorColor = context.getCompatColor(R.color.colorFingerprintError)

        mSuccessDrawable = context.getCompatDrawable(R.drawable.ic_fingerprint_success)
        mErrorDrawable = context.getCompatDrawable(R.drawable.ic_fingerprint_error)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fingerprint_dialog_layout)

        init()

        mIsInitialized = true
    }


    private fun init() {
        initSubtitle()
        initButton()

        window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        setOnDismissListener(this)

        runDelayedTasks()

        ThemingUtil.Dialogs.fingerprintDialog(this, mTheme)
    }


    private fun initSubtitle() {
        setSubtitleText(mSubtitleText)
    }


    private fun initButton() {
        setButtonText(mButtonText)
        setButtonListener(mButtonListener)
    }


    @SuppressWarnings("NewApi")
    private fun generateKey(): Boolean {
        mKeyStore = null
        val keyGenerator: KeyGenerator

        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore")
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        } catch(e: Exception) {
            return false
        }

        return try {
            mKeyStore?.load(null)

            val params = KeyGenParameterSpec.Builder(
                    KEY_NAME,
                    (KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            ).apply {
                setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                setUserAuthenticationRequired(true)
                setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            }.build()

            keyGenerator.init(params)
            keyGenerator.generateKey()

            true
        } catch(e: Exception) {
            false
        }
    }


    @SuppressWarnings("NewApi")
    private fun initCipher(): Boolean {
        if(!generateKey()) {
            return false
        }

        try {
            val keyAlgorithm = KeyProperties.KEY_ALGORITHM_AES
            val blockMode = KeyProperties.BLOCK_MODE_CBC
            val encryptionPadding = KeyProperties.ENCRYPTION_PADDING_PKCS7

            mCipher = Cipher.getInstance("$keyAlgorithm/$blockMode/$encryptionPadding")
        } catch(e: Exception) {
            return false
        }

        return try {
            mKeyStore?.load(null)
            mCipher!!.init(Cipher.ENCRYPT_MODE, mKeyStore!!.getKey(KEY_NAME, null) as SecretKey)

            true
        } catch(e: Exception) {
            false
        }
    }


    @SuppressWarnings("NewApi")
    private fun getCryptoObject(): FingerprintManagerCompat.CryptoObject? {
        return if(initCipher()) FingerprintManagerCompat.CryptoObject(mCipher!!) else null
    }


    private fun displayError(error: CharSequence, postResetRunnable: Boolean = true) {
        iconIv.setImageDrawable(mErrorDrawable)

        statusTv.setTextColor(mErrorColor)
        statusTv.text = error

        if(postResetRunnable) {
            removeRunnable(mResetErrorRunnable)
            postDelayed(mResetErrorRunnable, RESET_ERROR_DELAY_IN_MILLIS)
        }
    }


    private fun displayErrorAndFinish(error: Errors, errorText: CharSequence,
                                      delay: Long) {
        hideDescription()
        displayError(errorText, false)

        mLastFatalError = error

        postDelayed(
            mErrorRunnable,
            delay
        )
    }


    private fun postDelayed(action: Runnable, delay: Long) {
        cardView.postDelayed(action, delay)
    }


    private fun removeRunnable(runnable: Runnable) {
        cardView.removeCallbacks(runnable)
    }


    private fun delayTask(task: Runnable) {
        mDelayedTasks.add(task)
    }


    private fun runDelayedTasks() {
        mDelayedTasks.forEach {
            it.run()
        }

        mDelayedTasks.clear()
    }


    /**
     * Starts the process where user can touch the sensor to
     * perform authentication.
     */
    fun startAuthentication() {
        if(mIsScanning) {
            stopAuthentication()
        }

        val fingerprintManager = context.getFingerprintManager()
        val cryptoObject = getCryptoObject()

        if(cryptoObject == null) {
            if(mIsInitialized) {
                displayErrorAndFinish(
                    Errors.FAILED_TO_INITIALIZE_FINGERPRINT_SCANNER,
                    context.getString(R.string.error_fingerprint_initialization_failed),
                    ERROR_WITHOUT_DETAILS_DELAY_IN_MILLIS
                )
            } else {
                delayTask(Runnable {
                    displayErrorAndFinish(
                        Errors.FAILED_TO_INITIALIZE_FINGERPRINT_SCANNER,
                        context.getString(R.string.error_fingerprint_initialization_failed),
                        ERROR_WITHOUT_DETAILS_DELAY_IN_MILLIS
                    )
                })
            }

            return
        }

        mCancellationSignal = CancellationSignal()
        fingerprintManager.authenticate(cryptoObject, 0, mCancellationSignal, mFingerprintListener, null)

        mIsScanning = true
    }


    /**
     * Stops the authentication process by cleaning up resources.
     */
    fun stopAuthentication() {
        if(mCancellationSignal != null) {
            mIsScanning = false

            mCancellationSignal?.cancel()
            mCancellationSignal = null
        }
    }


    /**
     * Makes the subtitle visible.
     */
    fun showSubtitle() {
        if(mIsInitialized) {
            subtitleTv.makeVisible()
        } else {
            delayTask(Runnable {
                subtitleTv.makeVisible()
            })
        }
    }


    /**
     * Hides the subtitle invisible.
     */
    fun hideSubtitle() {
        if(mIsInitialized) {
            subtitleTv.makeGone()
        } else {
            delayTask(Runnable {
                subtitleTv.makeGone()
            })
        }
    }


    /**
     * Makes the description visible.
     */
    fun showDescription() {
        if(mIsInitialized) {
            descriptionTv.makeVisible()
        } else {
            delayTask(Runnable {
                descriptionTv.makeVisible()
            })
        }
    }


    /**
     * Makes the description invisible.
     */
    fun hideDescription() {
        if(mIsInitialized) {
            descriptionTv.makeGone()
        } else {
            delayTask(Runnable {
                descriptionTv.makeGone()
            })
        }
    }


    /**
     * Sets a color of the background.
     *
     * @param color The color to set
     */
    fun setBackgroundColor(@ColorInt color: Int) {
        mBackgroundColor = color

        cardView.setCardBackgroundColor(color)
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

        titleTv.setTextColor(color)
        subtitleTv.setTextColor(color)
        descriptionTv.setTextColor(color)
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
     * Sets a color of the status.
     *
     * @param color The color to set
     */
    fun setStatusTextColor(@ColorInt color: Int) {
        mStatusTextColor = color

        statusTv.setTextColor(color)
    }


    /**
     * Returns a color of the status.
     *
     * @return The status's color
     */
    fun getStatusTextColor(): Int {
        return mStatusTextColor
    }


    /**
     * Sets a text color of the button.
     *
     * @param color The color to set
     */
    fun setButtonTextColor(@ColorInt color: Int) {
        mButtonTextColor = color

        buttonTv.setTextColor(color)
    }


    /**
     * Returns a text color of the button.
     *
     * @return The button's text color
     */
    fun getButtonTextColor(): Int {
        return mButtonTextColor
    }


    /**
     * Sets a text of the subtitle.
     *
     * @param text The text to set
     */
    fun setSubtitleText(text: String) {
        mSubtitleText = text

        if(subtitleTv != null) {
            subtitleTv.text = text
        }
    }


    /**
     * Sets a text of the button.
     *
     * @param text The text to set
     */
    fun setButtonText(text: String) {
        mButtonText = text

        if(buttonTv != null) {
            buttonTv.text = text
        }
    }


    /**
     * Sets a listener for the button.
     *
     * @param listener The listener to set
     */
    fun setButtonListener(listener: ((View) -> Unit)?) {
        mButtonListener = listener

        if(buttonTv != null) {
            buttonTv.setOnClickListener(listener)
        }
    }


    override fun onDismiss(dialog: DialogInterface?) {
        if(mIsSuccessPending) {
            removeRunnable(mSuccessRunnable)
            onSuccess()
        }

        if(mIsErrorPending) {
            removeRunnable(mErrorRunnable)
            onError(mLastFatalError!!)
        }

        stopAuthentication()
    }


    private fun onSuccess() {
        if(!mIsScanning) {
            return
        }

        mListener.onSuccess()
        dismiss()
    }


    private fun onError(error: Errors) {
        if(!mIsScanning) {
            return
        }

        mListener.onError(error)
        dismiss()
    }


    @SuppressWarnings("NewApi")
    private val mFingerprintListener: FingerprintManagerCompat.AuthenticationCallback =
        object : FingerprintManagerCompat.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                removeRunnable(mResetErrorRunnable)

                if(errorCode == TOO_MANY_ATTEMPTS_ERROR_CODE) {
                    setSubtitleText(context.getString(
                        R.string.error_fingerprint_too_many_failed_attempts_template,
                        context.getString(
                        if(mMode == Modes.SETUP) {
                            R.string.fingerprint_dialog_setup_attempts_used_up_message
                        } else {
                            R.string.fingerprint_dialog_scan_attempts_used_up_message
                        }
                    )))
                    showSubtitle()
                    displayErrorAndFinish(Errors.TOO_MANY_ATTEMPTS, errString, ERROR_WITH_DETAILS_DELAY_IN_MILLIS)
                } else {
                    displayErrorAndFinish(Errors.UNDEFINED_ERROR, errString, ERROR_WITHOUT_DETAILS_DELAY_IN_MILLIS)
                }

                mIsErrorPending = true
            }


            override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
                displayError(helpString)
            }


            override fun onAuthenticationFailed() {
                displayError(context.getString(R.string.error_fingerprint_not_recognized))
            }


            override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult) {
                removeRunnable(mResetErrorRunnable)

                iconIv.setImageDrawable(mSuccessDrawable)

                statusTv.setTextColor(mSuccessColor)
                statusTv.text = context.getString(R.string.fingerprint_dialog_success_status_text)

                postDelayed(
                    mSuccessRunnable,
                    SUCCESS_DELAY_IN_MILLIS
                )

                mIsSuccessPending = true
            }

        }


    private val mResetErrorRunnable: Runnable = Runnable {
        statusTv.setTextColor(mStatusTextColor)
        statusTv.text = context.getString(R.string.fingerprint_dialog_default_status_text)

        iconIv.setImageResource(R.mipmap.ic_fingerprint)
    }


    private val mSuccessRunnable: Runnable = Runnable {
        onSuccess()
    }


    private val mErrorRunnable: Runnable = Runnable {
        onError(mLastFatalError!!)
    }


    enum class Modes {

        SETUP,
        SCAN

    }


    enum class Errors {

        FAILED_TO_INITIALIZE_FINGERPRINT_SCANNER,
        TOO_MANY_ATTEMPTS,
        UNDEFINED_ERROR

    }


    interface Listener {

        fun onSuccess() {
            // Stub
        }

        fun onError(error: Errors) {
            // Stub
        }

    }


}
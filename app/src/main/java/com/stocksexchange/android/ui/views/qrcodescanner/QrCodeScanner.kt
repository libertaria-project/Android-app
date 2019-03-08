package com.stocksexchange.android.ui.views.qrcodescanner

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.stocksexchange.android.ui.utils.extensions.checkPermission
import org.jetbrains.anko.forEachChild
import timber.log.Timber

/**
 * A view container for scanning QR codes.
 */
class QrCodeScanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {


    private var mIsSurfaceAvailable = false
    private var mIsStartRequested = false

    private var mBarcodeCallback: ((Barcode) -> Unit)? = null
    private val mSurfaceCallback = object: SurfaceHolder.Callback {

        override fun surfaceCreated(holder: SurfaceHolder) {
            mIsSurfaceAvailable = true

            startCameraSource()
            requestLayout()
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            // Do nothing
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            mIsSurfaceAvailable = false
        }

    }

    private val mSurfaceView = SurfaceView(context)
    private var mCameraSource: CameraSource? = null




    init {
        val holder = mSurfaceView.holder
        holder.addCallback(mSurfaceCallback)

        addView(mSurfaceView)
    }


    /**
     * Starts the scanner.
     */
    fun start() {
        if(!isCameraPermissionGranted()) {
            return
        }

        mIsStartRequested = true
        startCameraSource()
    }


    /**
     * Stops the scanner.
     */
    fun stop() {
        if(!isCameraPermissionGranted()) {
            return
        }

        mCameraSource?.stop()
    }


    /**
     * Releases the scanner.
     */
    fun release() {
        if(!isCameraPermissionGranted()) {
            return
        }

        releaseCameraSource()
    }


    /**
     * A callback to be invoked whenever a QR code
     * has been detected.
     *
     * @param handler The handle to invoke
     */
    fun onBarcodeDetected(handler: (Barcode) -> Unit) {
        mBarcodeCallback = handler
    }


    /**
     * A callback to invoke whenever a camera permissions has been
     * granted.
     */
    fun onCameraPermissionGranted() {
        createCameraSource()
        requestLayout()
    }



    @SuppressLint("MissingPermission")
    private fun startCameraSource() {
        if(mIsStartRequested && mIsSurfaceAvailable) {
            try {
                mCameraSource?.start(mSurfaceView.holder)
            } catch(throwable: Throwable) {
                Timber.e(throwable, "An error occurred while starting the camera source. Error: $throwable")

                releaseCameraSource()
            }

            mIsStartRequested = false
        }
    }


    private fun releaseCameraSource() {
        try {
            mCameraSource?.release()
        } catch(throwable: Throwable) {
            Timber.e(throwable, "An error occurred while release the camera source. Error: $throwable")
        }
    }


    private fun onBarcodeDetected(barcode: Barcode) {
        (context as? Activity)?.runOnUiThread {
            mBarcodeCallback?.invoke(barcode)
        }
    }


    private fun createCameraSource() {
        val barcodeDetector = BarcodeDetector.Builder(context).build()
        val processor = MultiProcessor.Builder(BarcodeTrackerFactory(::onBarcodeDetected)).build()
        barcodeDetector.setProcessor(processor)

        mCameraSource = CameraSource.Builder(context.applicationContext, barcodeDetector)
            .setFacing(CameraSource.CAMERA_FACING_BACK)
            .setRequestedPreviewSize(1600, 1024)
            .setRequestedFps(15.0f)
            .setAutoFocusEnabled(true)
            .build()
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val layoutWidth = (right - left)
        val layoutHeight = (bottom - top)

        // Calculating surface view layout to avoid deformed picture
        val (width, height) = mCameraSource
            ?.takeUnless { it.previewSize == null }
            ?.let { cameraSource ->
                val (previewWidth, previewHeight) = with(cameraSource.previewSize) { height to width }

                return@let if((previewHeight * layoutWidth / previewWidth) > layoutHeight) {
                    layoutWidth to (previewHeight * layoutWidth / previewWidth)
                } else (previewWidth * layoutHeight / previewHeight) to layoutHeight
            } ?: (layoutWidth to layoutHeight)

        forEachChild {
            it.layout(0, 0, width, height)
        }

        startCameraSource()
    }


    private fun isCameraPermissionGranted(): Boolean {
        return context.checkPermission(Manifest.permission.CAMERA)
    }


}
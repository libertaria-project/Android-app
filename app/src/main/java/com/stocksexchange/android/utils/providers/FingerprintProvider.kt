package com.stocksexchange.android.utils.providers

import android.content.Context
import com.stocksexchange.android.ui.utils.extensions.getFingerprintManager

/**
 * A helper class used for providing fingerprint related functionality.
 */
class FingerprintProvider(context: Context) {


    private val mContext: Context = context.applicationContext




    /**
     * Checks whether the device has a fingerprint.
     *
     * @return true if present; false otherwise
     */
    fun isHardwareAvailable(): Boolean {
        return mContext.getFingerprintManager().isHardwareDetected
    }


    /**
     * Checks whether there is at least one fingerprint enrolled.
     *
     * @return true if enrolled; false otherwise
     */
    fun hasEnrolledFingerprints(): Boolean {
        return mContext.getFingerprintManager().hasEnrolledFingerprints()
    }


}
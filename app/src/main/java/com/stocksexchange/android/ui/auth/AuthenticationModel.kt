package com.stocksexchange.android.ui.auth

import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.repositories.settings.SettingsRepository
import com.stocksexchange.android.ui.base.mvp.model.BaseModel
import com.stocksexchange.android.utils.EncryptionUtil
import com.stocksexchange.android.utils.SavedState
import com.stocksexchange.android.utils.handlers.PreferenceHandler
import com.stocksexchange.android.utils.helpers.tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.standalone.inject

class AuthenticationModel : BaseModel() {


    companion object {

        const val MAX_INVALID_PIN_CODE_ATTEMPTS_NUMBER = 5

        const val TIMER_DURATION = 30_000L
        const val TIMER_INTERVAL = 1_000L
        const val TIMER_MIN_FINISH_TIME = 1_000L

        private val CLASS = AuthenticationModel::class.java

        private val SAVED_STATE_ARE_FINGERPRINT_ATTEMPTS_USED_UP = tag(CLASS, "are_fingerprint_attempts_used_up")
        private val SAVED_STATE_INVALID_PIN_CODE_ATTEMPTS_NUMBER = tag(CLASS, "invalid_pin_code_attempts_number")
        private val SAVED_STATE_ALLOW_AUTH_TIMESTAMP = tag(CLASS, "allow_auth_timestamp")

    }


    private var mAreFingerprintAttemptsUsedUp: Boolean = false

    private var mInvalidPinCodeAttemptsNumber: Int = 0

    private var mAllowAuthTimestamp: Long = 0L

    private val mPreferenceHandler: PreferenceHandler by inject()

    private val mSettingsRepository: SettingsRepository by inject()




    /**
     * Initializes the authentication variables.
     *
     * @param onFinish The callback to invoke when initialization
     * has been finished
     */
    fun initAuthVariables(onFinish: (() -> Unit)) {
        createBgLaunchCoroutine {
            mAreFingerprintAttemptsUsedUp = if(mPreferenceHandler.hasFingerprintAttemptsUsedUp()) {
                EncryptionUtil.getInstance().decrypt(mPreferenceHandler.areFingerprintAttemptsUsedUp())
                    .takeIf { it.isNotBlank() }
                    ?.toBoolean()
                    ?: true
            } else {
                false
            }

            mInvalidPinCodeAttemptsNumber = if(mPreferenceHandler.hasInvalidPinCodeAttemptsNumber()) {
                EncryptionUtil.getInstance().decrypt(mPreferenceHandler.getInvalidPinCodeAttemptsNumber())
                    .takeIf { it.isNotBlank() }
                    ?.toInt()
                    ?: MAX_INVALID_PIN_CODE_ATTEMPTS_NUMBER
            } else {
                0
            }

            mAllowAuthTimestamp = if(mPreferenceHandler.hasAllowAuthTime()) {
                EncryptionUtil.getInstance().decrypt(mPreferenceHandler.getAllowAuthTimestamp())
                    .takeIf { it.isNotBlank() }
                    ?.toLong()
                    ?: generateNewAllowAuthTimestamp()
            } else {
                0L
            }

            withContext(Dispatchers.Main) {
                onFinish()
            }
        }
    }


    /**
     * Saves the last authentication timestamp.
     *
     * @param lastAuthTimestamp The last authentication timestamp to save
     * @param onFinish The callback to invoke on finish
     */
    fun saveLastAuthTimestamp(lastAuthTimestamp: Long, onFinish: () -> Unit) {
        createBgLaunchCoroutine {
            mPreferenceHandler.saveLastAuthTimestamp(EncryptionUtil.getInstance().encrypt(
                lastAuthTimestamp.toString()
            ))

            withContext(Dispatchers.Main) {
                onFinish()
            }
        }
    }


    /**
     * Deletes allow authentication timestamp.
     */
    fun deleteAllowAuthTimestamp() {
        mPreferenceHandler.removeAllowAuthTimestamp()
    }


    /**
     * Updates a timestamp of when to allow authentication to be performed.
     */
    fun updateAllowAuthTimestamp() {
        mAllowAuthTimestamp = generateNewAllowAuthTimestamp()

        createBgLaunchCoroutine {
            mPreferenceHandler.saveAllowAuthTimestamp(EncryptionUtil.getInstance().encrypt(
                mAllowAuthTimestamp.toString()
            ))
        }
    }


    /**
     * Increases the invalid pin code attempts number by one and saves
     * it to the storage.
     */
    fun increaseInvalidPinCodeAttemptsNumber() {
        mInvalidPinCodeAttemptsNumber++

        createBgLaunchCoroutine {
            mPreferenceHandler.saveInvalidPinCodeAttemptsNumber(EncryptionUtil.getInstance().encrypt(
                mInvalidPinCodeAttemptsNumber.toString()
            ))
        }
    }


    /**
     * Resets authentication related data.
     */
    fun resetAuthData() {
        mAreFingerprintAttemptsUsedUp = false
        mInvalidPinCodeAttemptsNumber = 0

        mPreferenceHandler.removeFingerprintAttemptsUsedUp()
        mPreferenceHandler.removeInvalidPinCodeAttemptsNumber()
    }


    private fun generateNewAllowAuthTimestamp(): Long {
        return (System.currentTimeMillis() + TIMER_DURATION)
    }


    /**
     * Updates the settings.
     *
     * @param settings The settings to update
     * @param onFinish The callback to invoke when updating has been finished
     */
    fun updateSettings(settings: Settings, onFinish: () -> Unit) {
        createUiLaunchCoroutine {
            mSettingsRepository.save(settings)

            onFinish()
        }
    }


    /**
     * Sets a flag specifying whether fingerprint attempts are used up or not as
     * well as saves it to the storage.
     *
     * @param areFingerprintAttemptsUsedUp The flag itself
     */
    fun setFingerprintAttemptsUsedUp(areFingerprintAttemptsUsedUp: Boolean) {
        mAreFingerprintAttemptsUsedUp = areFingerprintAttemptsUsedUp

        createBgLaunchCoroutine {
            mPreferenceHandler.saveFingerprintAttemptsUsedUp(EncryptionUtil.getInstance().encrypt(
                areFingerprintAttemptsUsedUp.toString()
            ))
        }
    }


    /**
     * Returns a flag specifying whether fingerprint attempts are used up or not.
     *
     * @return true if used up; false otherwise
     */
    fun areFingerprintAttemptsUsedUp(): Boolean {
        return mAreFingerprintAttemptsUsedUp
    }


    /**
     * Returns a number of invalid pin code attempts.
     *
     * @return The number of invalid pin code attempts
     */
    fun getInvalidPinCodeAttemptsNumber(): Int {
        return mInvalidPinCodeAttemptsNumber
    }


    /**
     * Returns a timestamp of when to allow authentication to be performed.
     *
     * @return The timestamp
     */
    fun getAllowAuthTimestamp(): Long {
        return mAllowAuthTimestamp
    }


    override fun onRestoreState(savedState: SavedState) {
        super.onRestoreState(savedState)

        with(savedState) {
            mAreFingerprintAttemptsUsedUp = get(SAVED_STATE_ARE_FINGERPRINT_ATTEMPTS_USED_UP, false)
            mInvalidPinCodeAttemptsNumber = get(SAVED_STATE_INVALID_PIN_CODE_ATTEMPTS_NUMBER, 0)
            mAllowAuthTimestamp = get(SAVED_STATE_ALLOW_AUTH_TIMESTAMP, 0L)
        }
    }


    override fun onSaveState(savedState: SavedState) {
        super.onSaveState(savedState)

        with(savedState) {
            save(SAVED_STATE_ARE_FINGERPRINT_ATTEMPTS_USED_UP, mAreFingerprintAttemptsUsedUp)
            save(SAVED_STATE_INVALID_PIN_CODE_ATTEMPTS_NUMBER, mInvalidPinCodeAttemptsNumber)
            save(SAVED_STATE_ALLOW_AUTH_TIMESTAMP, mAllowAuthTimestamp)
        }
    }


}
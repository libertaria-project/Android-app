package com.stocksexchange.android.utils

import com.stocksexchange.android.BuildConfig
import com.tozny.crypto.android.AesCbcWithIntegrity

/**
 * A utility class used for encryption related functionality.
 */
class EncryptionUtil private constructor() {


    companion object {

        @Volatile
        private var INSTANCE: EncryptionUtil? = null


        fun getInstance(): EncryptionUtil {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: EncryptionUtil()
            }
        }

    }


    private var mSecretKeys: AesCbcWithIntegrity.SecretKeys? = null




    init {
        initSecretKeysIfNecessary()
    }


    private fun initSecretKeysIfNecessary() {
        if(mSecretKeys != null) {
            return
        }

        try {
            mSecretKeys = AesCbcWithIntegrity.keys(BuildConfig.ENCRYPTION_KEY)
        } catch(exception: Exception) {
            exception.printStackTrace()
        }
    }


    /**
     * Encrypts the given text data and returns it.
     *
     * @param textData The text data to encrypt
     *
     * @return The encrypted text data
     */
    fun encrypt(textData: String): String {
        if(textData.isEmpty()) {
            return ""
        }

        initSecretKeysIfNecessary()

        return try {
            AesCbcWithIntegrity.encrypt(textData, mSecretKeys).toString()
        } catch(e: Exception) {
            ""
        }
    }


    /**
     * Decrypts the given encrypted text data and returns it.
     *
     * @param encryptedTextData The encrypted text data to decrypt
     *
     * @return The decrypted text data
     */
    fun decrypt(encryptedTextData: String): String {
        if(encryptedTextData.isEmpty()) {
            return ""
        }

        initSecretKeysIfNecessary()

        return try {
            AesCbcWithIntegrity.decryptString(
                AesCbcWithIntegrity.CipherTextIvMac(encryptedTextData),
                mSecretKeys
            )
        } catch(e: Exception) {
            ""
        }
    }


}
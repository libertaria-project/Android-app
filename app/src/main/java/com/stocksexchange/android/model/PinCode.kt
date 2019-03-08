package com.stocksexchange.android.model

/**
 * A data class representing a pin code.
 */
data class PinCode(val code: String) {


    companion object {

        fun getEmptyPinCode(): PinCode = PinCode("")

    }




    /**
     * Checks whether the pin code is not empty.
     *
     * @return true if not empty; false otherwise
     */
    fun hasCode(): Boolean = code.isNotBlank()


}
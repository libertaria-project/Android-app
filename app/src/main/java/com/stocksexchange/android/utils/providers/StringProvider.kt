package com.stocksexchange.android.utils.providers

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.newapi.UserAdmissionErrors
import com.stocksexchange.android.model.LoginProcessPhases
import com.stocksexchange.android.model.PinCodeModes
import com.stocksexchange.android.ui.utils.extensions.createContextWithLocale
import java.util.Locale

/**
 * A helper class used for providing string related functionality.
 */
class StringProvider(context: Context) {


    private val context: Context = context.applicationContext




    /**
     * Gets a string specified by the resource id.
     *
     * @param id The id to get the string for
     *
     * @return The resolved string
     */
    fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }


    /**
     * Gets a string specified by the resource id.
     *
     * @param id The id to get the string for
     * @param locale The locale of the string
     *
     * @return The resolved string
     */
    fun getStringByLocale(@StringRes id: Int, locale: Locale): String {
        return context.createContextWithLocale(locale).getString(id)
    }


    /**
     * Gets a string specified by the resource id with an argument.
     *
     * @param id The id to get the string for
     * @param arg The argument to provide for formatting
     *
     * @return The resolved string
     */
    fun getString(@StringRes id: Int, arg: String): String {
        return context.getString(id, arg)
    }


    /**
     * Gets a string specified by the resource id.
     *
     * @param id The id to get the string for
     * @param locale The locale of the string
     *
     * @return The resolved string
     */
    fun getStringByLocale(@StringRes id: Int, arg: String, locale: Locale): String {
        return context.createContextWithLocale(locale).getString(id, arg)
    }


    /**
     * Gets a string specified by the resource ID with two arguments.
     *
     * @param id The id to get the string for
     * @param arg1 The first argument to provide for formatting
     * @param arg2 The second argument to provide for formatting
     *
     * @return The resolved string
     */
    fun getString(@StringRes id: Int, arg1: String, arg2: String): String {
        return context.getString(id, arg1, arg2)
    }


    /**
     * Gets a string specified by the resource ID with two arguments.
     *
     * @param id The id to get the string for
     * @param arg1 The first argument to provide for formatting
     * @param arg2 The second argument to provide for formatting
     * @param locale The locale of the string
     *
     * @return The resolved string
     */
    fun getStringByLocale(@StringRes id: Int, arg1: String, arg2: String,
                          locale: Locale): String {
        return context.createContextWithLocale(locale).getString(id, arg1, arg2)
    }


    /**
     * Gets a string specified by the resource ID with the specified arguments.
     *
     * @param id The id to get the string for
     * @param args The arguments for the string
     *
     * @return The resolved string
     */
    fun getString(@StringRes id: Int, args: Array<String>): String {
        return context.getString(id, *args)
    }


    /**
     * Gets a string specified by the resource ID with the specified arguments.
     *
     * @param id The id to get the string for
     * @param args The arguments for the string
     * @param locale The locale of the string
     *
     * @return The resolved string
     */
    fun getStringByLocale(@StringRes id: Int, args: Array<String>, locale: Locale): String {
        return context.createContextWithLocale(locale).getString(id, *args)
    }


    /**
     * Gets a quantity string specified by the resource ID with two arguments.
     *
     * @param id The id to get the plural string for
     * @param quantity The quantity for the plural string
     * @param count The argument for the formatting character
     *
     * @return The resolved string
     */
    fun getQuantityString(@PluralsRes id: Int, quantity: Int, count: Int): String {
        return context.resources.getQuantityString(id, quantity, count)
    }


    /**
     * Gets a quantity string specified by the resource ID with two arguments.
     *
     * @param id The id to get the plural string for
     * @param quantity The quantity for the plural string
     * @param count The argument for the formatting character
     * @param locale The locale of the string
     *
     * @return The resolved string
     */
    fun getQuantityString(@PluralsRes id: Int, quantity: Int, count: Int,
                          locale: Locale): String {
        return context.createContextWithLocale(locale).resources.getQuantityString(id, quantity, count)
    }


    /**
     * Gets a string array specified by the resource ID.
     *
     * @param id The id to get the string array for
     *
     * @return The resolved string
     */
    fun getStringArray(@ArrayRes id: Int): Array<String> {
        return context.resources.getStringArray(id)
    }


    /**
     * Gets a string array specified by the resource ID.
     *
     * @param id The id to get the string array for
     * @param locale The locale of the string array
     *
     * @return The resolved string
     */
    fun getStringArray(@ArrayRes id: Int, locale: Locale): Array<String> {
        return context.createContextWithLocale(locale).resources.getStringArray(id)
    }


    /**
     * Gets a network connection check string.
     */
    fun getNetworkCheckMessage(): String = getString(R.string.error_check_network_connection)


    /**
     * Gets a user not found string.
     */
    fun getUserNotFoundMessage(): String = getString(R.string.error_user_not_found)


    /**
     * Gets too many requests string.
     */
    fun getTooManyRequestsMessage(): String = getString(R.string.error_too_many_requests)


    /**
     * Gets server unresponsive string.
     */
    fun getServerUnresponsiveMessage(): String = getString(R.string.error_server_not_responding)


    /**
     * Gets something went wrong string.
     */
    fun getSomethingWentWrongMessage(): String = getString(R.string.error_something_went_wrong)


    /**
     * Retrieves a message based on the pin code mode.
     *
     * @param pinCodeMode The pin code mode to return the message string for
     *
     * @return The message string
     */
    fun getMessageForPinCodeMode(pinCodeMode: PinCodeModes): String {
        return context.getString(when(pinCodeMode) {
            PinCodeModes.CREATION -> R.string.authentication_activity_hint_create_pin
            PinCodeModes.CONFIRMATION -> R.string.authentication_activity_hint_confirm_pin
            PinCodeModes.ENTER, PinCodeModes.CHANGE -> R.string.authentication_activity_hint_enter_pin
        })
    }


    /**
     * Retrieves a text for the main button based on the phase of the login process.
     *
     * @param loginProcessPhase The phase of the login process
     *
     * @return The main button text
     */
    fun getMainButtonTextForLoginProcessPhase(loginProcessPhase: LoginProcessPhases): String {
        return context.getString(when(loginProcessPhase) {
            LoginProcessPhases.AWAITING_CREDENTIALS -> R.string.login_main_btn_log_in_text
            LoginProcessPhases.AWAITING_CONFIRMATION -> R.string.login_main_btn_submit_text
        })
    }


}
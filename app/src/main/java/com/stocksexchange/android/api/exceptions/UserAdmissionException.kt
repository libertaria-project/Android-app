package com.stocksexchange.android.api.exceptions

import com.stocksexchange.android.api.model.newapi.UserAdmissionErrors

/**
 * Gets thrown whenever an error or errors appeared
 * during user admission processes (login and registration).
 */
class UserAdmissionException(
    private val errorMessages: List<String> = listOf(),
    message: String = "",
    throwable: Throwable? = null
) : Exception(message, throwable) {


    /**
     * A field that returns a type of the error.
     */
    val error: UserAdmissionErrors
        get() = when {
            UserAdmissionErrors.EMAIL_TAKEN.message in errorMessages -> UserAdmissionErrors.EMAIL_TAKEN
            UserAdmissionErrors.PASSWORD_LENGTH.message in errorMessages -> UserAdmissionErrors.PASSWORD_LENGTH
            UserAdmissionErrors.PASSWORD_COMMON.message in errorMessages -> UserAdmissionErrors.PASSWORD_COMMON

            else -> UserAdmissionErrors.UNKNOWN
        }


    /**
     * A field that returns a list of errors.
     */
    val errors: List<UserAdmissionErrors>
        get() = mutableListOf<UserAdmissionErrors>().apply {
            for(errorMessage in errorMessages) {
                add(when(errorMessage) {
                    UserAdmissionErrors.EMAIL_TAKEN.message -> UserAdmissionErrors.EMAIL_TAKEN
                    UserAdmissionErrors.PASSWORD_LENGTH.message -> UserAdmissionErrors.PASSWORD_LENGTH
                    UserAdmissionErrors.PASSWORD_COMMON.message -> UserAdmissionErrors.PASSWORD_COMMON

                    else -> UserAdmissionErrors.UNKNOWN
                })
            }
        }


    /**
     * A field that returns a list of login errors.
     */
    val loginErrors: List<UserAdmissionErrors>
        get() = mutableListOf<UserAdmissionErrors>().apply {
            for(error in errors) {
                if(error in UserAdmissionErrors.LOGIN_ERRORS) {
                    add(error)
                }
            }
        }


    /**
     * A field that returns a list of registration errors.
     */
    val registrationErrors: List<UserAdmissionErrors>
        get() = mutableListOf<UserAdmissionErrors>().apply {
            for(error in errors) {
                if(error in UserAdmissionErrors.REGISTRATION_ERRORS) {
                    add(error)
                }
            }
        }


}
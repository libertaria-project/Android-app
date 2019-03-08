package com.stocksexchange.android.ui.splash

import com.stocksexchange.android.api.exceptions.InvalidCredentialsException
import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.factories.SettingsFactory
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.repositories.settings.SettingsRepository
import com.stocksexchange.android.repositories.users.UsersRepository
import com.stocksexchange.android.ui.base.mvp.model.BaseModel
import com.stocksexchange.android.utils.handlers.UserDataClearingHandler
import org.koin.standalone.inject

class SplashModel : BaseModel() {


    private val mSettingsFactory: SettingsFactory by inject()
    private val mSettingsRepository: SettingsRepository by inject()
    private val mUsersRepository: UsersRepository by inject()
    private val mUserDataClearingHandler: UserDataClearingHandler by inject()




    fun initSettings(onFinish: (Settings) -> Unit) {
        createUiLaunchCoroutine {
            val result = mSettingsRepository.get()
            val settings: Settings

            if(result.isErroneous()) {
                settings = mSettingsFactory.getDefaultSettings()

                mSettingsRepository.save(settings)
            } else {
                settings = result.getSuccessfulResultValue()
            }

            onFinish(settings)
        }
    }


    fun updateUserIfNecessary(onFinish: (User?) -> Unit) {
        createUiLaunchCoroutine {
            if(!mUsersRepository.hasSignedInUser()) {
                onFinish(null)
                return@createUiLaunchCoroutine
            }

            val userResult = mUsersRepository.getSignedInUser()

            if(userResult.isErroneous()) {
                if(userResult.getErroneousResultValue() is InvalidCredentialsException) {
                    // Logging out the user since his credentials have been changed
                    mUserDataClearingHandler.clearUserData {
                        onFinish(null)
                    }
                } else {
                    onFinish(null)
                }

                return@createUiLaunchCoroutine
            }

            onFinish(userResult.getSuccessfulResultValue())
        }
    }


}
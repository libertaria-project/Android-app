package com.stocksexchange.android.api.model.newapi

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.stocksexchange.android.api.exceptions.ApiException
import com.stocksexchange.android.api.exceptions.InvalidParametersException
import com.stocksexchange.android.api.exceptions.UserAdmissionException
import com.stocksexchange.android.api.utils.fromJson
import com.stocksexchange.android.api.utils.toResult
import com.stocksexchange.android.model.Result
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.HttpException

/**
 * A wrapper for API responses.
 */
data class ApiResponse<Data>(
    @SerializedName(JSON_FIELD_KEY_SUCCESS) val isSuccess: Boolean = false,
    @SerializedName(JSON_FIELD_KEY_DATA) val data: Data? = null,
    @SerializedName(JSON_FIELD_KEY_MESSAGE) val errorMessage: String = ""
) {


    companion object {

        internal const val JSON_FIELD_KEY_SUCCESS = "success"
        internal const val JSON_FIELD_KEY_DATA = "data"
        internal const val JSON_FIELD_KEY_MESSAGE = "message"

    }


}


/**
 * An extension function to extract call's data from the [ApiResponse] object
 * and package it inside an instance of [Result] class with the ability
 * to apply a transformation operation.
 *
 * @param error The function to return an appropriate exception in case
 * an error has occurred. By default, returns [ApiException].
 * @param transform The transformation function to convert data from
 * the intermediate type to the ultimate one
 *
 * @return The data or error packaged inside [Result] class
 */
fun <In : ApiResponse<Inter>, Inter : Any, Out : Any> Call<In>.extractResult(
    error: ((ApiResponse<Inter>) -> Throwable) = { ApiException(it.toString()) },
    transform: ((Inter) -> Out)
): Result<Out> {
    val response = toResult()

    return when(response) {
        is Result.Success -> response.value.takeIf { it.isSuccess && (it.data != null) }
            ?.let { Result.Success(transform(it.data!!)) }
            ?: Result.Failure(error(response.value))
        is Result.Failure -> response
    }
}


/**
 * Similar to the extractResult(transform) but does not have an intermediate type
 * to convert from.
 *
 * @param error The function to return an appropriate exception in case
 * an error has occurred. By default, returns [ApiException].
 *
 * @return The data or error packaged inside [Result] class
 */
fun <In : ApiResponse<Out>, Out : Any> Call<In>.extractResultDirectly(
    error: ((ApiResponse<Out>) -> Throwable) = { ApiException(it.toString()) }
): Result<Out> {
    return extractResult(error) { inter -> inter }
}


fun Call<ResponseBody>.extractSignInResponse(gson: Gson): Result<SignInConfirmation> {
    val response = toResult()

    return wrapUserAdmissionProcessingCode {
        when(response) {
            is Result.Success -> handleSignInSuccessfulResponse(response, gson)
            is Result.Failure -> handleUserAdmissionErroneousResponse(response, gson)
        }
    }
}


fun Call<ResponseBody>.extractSignInConfirmationResponse(gson: Gson): Result<SignInConfirmationResponse> {
    val response = toResult()

    return wrapUserAdmissionProcessingCode {
        when(response) {
            is Result.Success -> handleSignInConfirmationSuccessfulResponse(response, gson)
            is Result.Failure -> handleUserAdmissionErroneousResponse(response, gson)
        }
    }
}


private fun handleSignInSuccessfulResponse(
    response: Result.Success<ResponseBody>,
    gson: Gson
): Result<SignInConfirmation> {
    val returnDefaultErrorResult: (() -> Result.Failure) = {
        Result.Failure(ApiException.badResponse())
    }
    val dataJsonObject = parseSuccessfulResponse(response, gson) ?: return returnDefaultErrorResult()
    val confirmation = SignInConfirmation.newInstance(dataJsonObject)

    return if(confirmation != null) {
        Result.Success(confirmation)
    } else {
        returnDefaultErrorResult()
    }
}


private fun handleSignInConfirmationSuccessfulResponse(
    response: Result.Success<ResponseBody>,
    gson: Gson
) : Result<SignInConfirmationResponse> {
    val returnDefaultErrorResult: (() -> Result.Failure) = {
        Result.Failure(ApiException.badResponse())
    }
    val dataJsonObject = parseSuccessfulResponse(response, gson) ?: return returnDefaultErrorResult()
    val oauthCredentials = OAuthCredentials.newInstance(dataJsonObject)

    return if(oauthCredentials != null) {
        Result.Success(SignInConfirmationResponse(oauthCredentials = oauthCredentials))
    } else {
        val confirmation = SignInConfirmation.newInstance(dataJsonObject)

        if(confirmation != null) {
            Result.Success(SignInConfirmationResponse(confirmation = confirmation))
        } else {
            returnDefaultErrorResult()
        }
    }
}


private fun parseSuccessfulResponse(
    response: Result.Success<ResponseBody>,
    gson: Gson
) : JsonObject? {
    val jsonObject = gson.fromJson<JsonObject>(response.value.string())

    return if(jsonObject.has(ApiResponse.JSON_FIELD_KEY_DATA)) {
        jsonObject.get(ApiResponse.JSON_FIELD_KEY_DATA).asJsonObject
    } else {
        null
    }
}


private fun <T : Any> handleUserAdmissionErroneousResponse(
    response: Result.Failure,
    gson: Gson
): Result<T> {
    if((response.exception !is HttpException) ||
        (response.exception.response()?.errorBody() !is ResponseBody)) {
        return response
    }

    val returnDefaultErrorResult: (() -> Result.Failure) = {
        Result.Failure(ApiException.badResponse(response.exception))
    }

    val responseBody = (response.exception.response().errorBody() as ResponseBody)
    val jsonObject = gson.fromJson<JsonObject>(responseBody.string())

    if(!jsonObject.has(ApiResponse.JSON_FIELD_KEY_MESSAGE)) {
        return returnDefaultErrorResult()
    }

    val messageJsonElement = jsonObject.get(ApiResponse.JSON_FIELD_KEY_MESSAGE)

    if(!messageJsonElement.isJsonArray &&
        !(messageJsonElement.isJsonPrimitive && messageJsonElement.asJsonPrimitive.isString)) {
        return returnDefaultErrorResult()
    }

    return Result.Failure(if(messageJsonElement.isJsonArray) {
        val errorMessages = mutableListOf<String>()
        val errorsJsonArray = messageJsonElement.asJsonArray

        for(errorJsonElement in errorsJsonArray) {
            if(!errorJsonElement.isJsonPrimitive || !errorJsonElement.asJsonPrimitive.isString) {
                continue
            }

            errorMessages.add(errorJsonElement.asJsonPrimitive.asString)
        }

        UserAdmissionException(errorMessages)
    } else {
        InvalidParametersException(messageJsonElement.asJsonPrimitive.asString)
    })
}


private fun <T : Any> wrapUserAdmissionProcessingCode(
    block: () -> Result<T>
): Result<T> {
    return try {
        block()
    } catch(throwable: Throwable) {
        Result.Failure(throwable)
    }
}
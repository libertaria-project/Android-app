package com.stocksexchange.android.api.utils

import com.stocksexchange.android.api.exceptions.ApiException
import com.stocksexchange.android.api.model.ApiResponse
import com.stocksexchange.android.model.Result
import retrofit2.Call
import retrofit2.HttpException
import java.io.IOException

/**
 * An extension function to fetch the call's result by executing it
 * synchronously and package it inside an instance of [Result] class.
 *
 * @return The body or an error packaged inside [Result] class
 */
fun <T : Any> Call<T>.toResult(): Result<T> {
    return try {
        val response = execute()

        if(response.isSuccessful) {
            val body = response.body()

            if(body != null) {
                Result.Success(body)
            } else {
                Result.Failure(NullPointerException("Response body is null."))
            }
        } else {
            Result.Failure(HttpException(response))
        }
    } catch(exception: IOException) {
        Result.Failure(exception)
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
        is Result.Success -> response.value.takeIf { (it.success == 1) && (it.data != null) }
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
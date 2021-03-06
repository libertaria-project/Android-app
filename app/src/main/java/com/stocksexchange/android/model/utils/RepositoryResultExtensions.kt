package com.stocksexchange.android.model.utils

import com.stocksexchange.android.model.RepositoryResult
import timber.log.Timber


/**
 * Logs the repository result.
 *
 * @param key The key of the log
 *
 * @return The repository result
 */
fun <T : Any> RepositoryResult<T>.log(key: String): RepositoryResult<T> {
    Timber.i("$key = $this")

    return this
}


/**
 * A callback to invoke if the repository result is successful.
 *
 * @param block The block of code to run on success
 *
 * @return The repository result
 */
suspend fun <T : Any> RepositoryResult<T>.onSuccess(block: suspend (T) -> Unit): RepositoryResult<T> {
    if(isSuccessful()) {
        block(getSuccessfulResultValue())
    }

    return this
}


/**
 * A callback to invoke if the repository result is unsuccessful.
 *
 * @param block The block of code to run on failure
 *
 * @return The repository result
 */
suspend fun <T : Any> RepositoryResult<T>.onFailure(block: suspend (Throwable) -> Unit): RepositoryResult<T> {
    if(isErroneous()) {
        block(getErroneousResultValue())
    }

    return this
}
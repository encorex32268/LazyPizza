package com.lihan.lazypizza.core.domain.util

interface RootError

sealed interface Result<out D, out E: RootError> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E: RootError>(val error: E) : Result<Nothing, E>
}

inline fun <T, E: RootError, R> Result<T, E>.map(transform: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Error -> Result.Error(error)
    }
}

inline fun <T, E: RootError> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    if (this is Result.Success) {
        action(data)
    }
    return this
}

inline fun <T, E: RootError> Result<T, E>.onFailure(action: (E) -> Unit): Result<T, E> {
    if (this is Result.Error) {
        action(error)
    }
    return this
}
package br.com.contacorrente.base

import br.com.contacorrente.model.ErrorStatus
import com.google.gson.Gson

open class CustomState<out T> {
    data class Success<T>(val data: T) : CustomState<T>()
    data class Error(val errorMessage: String) : CustomState<Nothing>()
    data class ApiError<T>(val errorStatusJson: String) : CustomState<T>()
    object Loading : CustomState<Nothing>()
}

inline fun <reified T> CustomState<T>.doIfLoading(callback: () -> Unit) {
    if (this is CustomState.Loading) {
        callback()
    }
}

inline fun <reified T> CustomState<T>.doIfSuccess(callback: (value: T) -> Unit) {
    if (this is CustomState.Success) {
        callback(data)
    }
}

inline fun <reified T> CustomState<T>.doIfError(callback: (error: String) -> Unit) {
    if (this is CustomState.Error) {
        callback(errorMessage)
    }
}

inline fun <reified T> CustomState<T>.doIfApiError(callback: (error: ErrorStatus) -> Unit) {
    if (this is CustomState.ApiError) {
        callback(Gson().fromJson(errorStatusJson, ErrorStatus::class.java))
    }
}

inline fun <reified T, reified R> CustomState<T>.collect(transform: (T) -> R): CustomState<R> {
    return when (this) {
        is CustomState.Success -> CustomState.Success(transform(data))
        is CustomState.Error -> this
        is CustomState.ApiError -> CustomState.ApiError(errorStatusJson)
        else -> CustomState.Loading
    }
}
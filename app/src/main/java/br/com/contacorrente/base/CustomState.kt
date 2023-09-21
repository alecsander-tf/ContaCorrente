package br.com.contacorrente.base

open class CustomState<out T> {
    object Loading : CustomState<Nothing>()
    data class Success<T>(val data: T) : CustomState<T>()
    data class Error<T>(val errorMessage: String) : CustomState<T>()
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
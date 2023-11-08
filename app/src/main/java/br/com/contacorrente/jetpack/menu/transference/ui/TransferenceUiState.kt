package br.com.contacorrente.jetpack.menu.transference.ui

data class TransferenceUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val onError: Boolean = false,

    val isDestinationOnError: Boolean = false,
    val isValueOnError: Boolean = false,
    val isPasswordOnError: Boolean = false,
)
package br.com.contacorrente.jetpack.login.ui

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "", // API error
    val onError: Boolean = false, // API error
    val isLoggedIn: Boolean = false,
    val isEmailOnError: Boolean = false, // Field error
    val isPasswordOnError: Boolean = false, // Field error
)
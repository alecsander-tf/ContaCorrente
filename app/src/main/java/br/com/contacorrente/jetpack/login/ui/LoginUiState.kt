package br.com.contacorrente.jetpack.login.ui

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val isLoggedIn: Boolean = false
)
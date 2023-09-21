package br.com.contacorrente.jetpack.login.ui

data class LoginUiState(
    val user: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
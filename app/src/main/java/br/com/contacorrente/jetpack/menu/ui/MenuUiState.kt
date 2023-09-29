package br.com.contacorrente.jetpack.menu.ui

data class MenuUiState(
    val isLoading: Boolean = false,
    val userName: String = "",
    val userBalance: String = "",
    val isRefreshing: Boolean = false
)
